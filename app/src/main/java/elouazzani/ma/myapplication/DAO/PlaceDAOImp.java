package elouazzani.ma.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import elouazzani.ma.myapplication.DBSQLITE.DB;
import elouazzani.ma.myapplication.Model.Place;

public class PlaceDAOImp  implements PlaceDAO{
    private DB mdb;
    private Context context;
    public PlaceDAOImp(@Nullable Context context) {
        this.context=context;
        mdb=new DB(context);
    }

    @Override
    public boolean AddPlaceItem(Place place) {
        // get writable database as we want to write data
        SQLiteDatabase db=mdb.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(PlaceDAO.COLUMN_TITLE,place.getTitle());
        values.put(PlaceDAO.COLUMN_CITY,place.getCity());
        values.put(PlaceDAO.COLUMN_TYPE,place.getType());
        values.put(PlaceDAO.COLUMN_ADDRESS,place.getAddress());
        values.put(PlaceDAO.COLUMN_DESCRIPTION,place.getDescription());
        values.put(PlaceDAO.COLUMN_IMAGE_BYTE,place.getImageByte());
        //
        long id=db.insert(PlaceDAO.TABLE_NAME,null,values);
        db.close();
        if(id>0)
            return true;
        return false;
    }

    @Override
    public Place getPlaceItem(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = mdb.getReadableDatabase();
        String[] columns=new String[]{
                PlaceDAO.COLUMN_ID,PlaceDAO.COLUMN_TITLE,PlaceDAO.COLUMN_CITY,PlaceDAO.COLUMN_TYPE,
                PlaceDAO.COLUMN_ADDRESS,PlaceDAO.COLUMN_DESCRIPTION,PlaceDAO.COLUMN_IMAGE_BYTE
        };
        String[] values=new String[]{
                String.valueOf(id)
        };
        String cond=PlaceDAO.COLUMN_ID+"=?";

        Cursor cursor=db.query(PlaceDAO.TABLE_NAME,columns,cond,values,
                null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();
        Place place=new Place(
                cursor.getInt(cursor.getColumnIndex(PlaceDAO.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_CITY)),
                cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_TYPE)),
                cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_DESCRIPTION)),
                cursor.getBlob(cursor.getColumnIndex(PlaceDAO.COLUMN_IMAGE_BYTE))
        );
        cursor.close();
        return place;
    }

    @Override
    public List<Place> getAllPlaces() {
        List<Place> places=null;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PlaceDAO.TABLE_NAME + " ORDER BY " +
                PlaceDAO.COLUMN_RATE + " DESC";

        SQLiteDatabase db = mdb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            places=new ArrayList<>();
            do {
                Place place=new Place(
                        cursor.getInt(cursor.getColumnIndex(PlaceDAO.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_CITY)),
                        cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_DESCRIPTION)),
                        cursor.getBlob(cursor.getColumnIndex(PlaceDAO.COLUMN_IMAGE_BYTE))
                );
                places.add(place);

            }while (cursor.moveToNext());
        }
        db.close();
        return places;
    }

    // update rating whene adding new feedback
    @Override
    public boolean updatePlaceByRate(Place place, float rate) {
        SQLiteDatabase db=mdb.getWritableDatabase();
        try {
            ContentValues values=new ContentValues();
            values.put(PlaceDAO.COLUMN_RATE,rate);
            long id=db.update(PlaceDAO.TABLE_NAME,values,PlaceDAO.COLUMN_ID +" =? ",
                    new String[]{String.valueOf(place.getId())});
            if(id>0)
                return true;

        }catch (Exception e) {
            return false;
        }
        return false;

    }

    @Override
    public List<Place> search(String keyword) {

        List<Place> places=null;
        try {
            SQLiteDatabase db= mdb.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " +PlaceDAO.TABLE_NAME + " where "
                    + PlaceDAO.COLUMN_CITY + " like ?"+ " OR "+ PlaceDAO.COLUMN_TITLE + " like ?"
                    +" ORDER BY "+PlaceDAO.COLUMN_RATE+" DESC ",
                    new String[] { "%" + keyword + "%" ,"%" + keyword + "%" });
            if(cursor.moveToFirst()) {
                places=new ArrayList<>();
                do {
                    Place place=new Place(
                            cursor.getInt(cursor.getColumnIndex(PlaceDAO.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_CITY)),
                            cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_TYPE)),
                            cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_ADDRESS)),
                            cursor.getString(cursor.getColumnIndex(PlaceDAO.COLUMN_DESCRIPTION)),
                            cursor.getBlob(cursor.getColumnIndex(PlaceDAO.COLUMN_IMAGE_BYTE))
                    );
                    places.add(place);

                }while (cursor.moveToNext());
            }

        }catch (Exception e){
             places=null;
        }

        return places;
    }

    // delete place from database
    @Override
    public boolean removePlaceItem(Place place) {

        SQLiteDatabase db=mdb.getWritableDatabase();

        long id=db.delete(PlaceDAO.TABLE_NAME,PlaceDAO.COLUMN_ID+" =?",
                new String[]{String.valueOf(place.getId())});
        if(id>0) {

            return true;
        }


        return false;
    }
}
