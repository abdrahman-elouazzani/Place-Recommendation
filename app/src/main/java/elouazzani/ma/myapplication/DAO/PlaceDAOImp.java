package elouazzani.ma.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import elouazzani.ma.myapplication.Model.Place;

public class PlaceDAOImp extends SQLiteOpenHelper implements PlaceDAO{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "places_db";

    public PlaceDAOImp(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlaceDAO.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PlaceDAO.TABLE_NAME);
        //
        onCreate(db);

    }

    @Override
    public boolean AddPlaceItem(Place place) {
        // get writable database as we want to write data
        SQLiteDatabase db=this.getWritableDatabase();
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
        SQLiteDatabase db = this.getReadableDatabase();
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
        List<Place> places=new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PlaceDAO.TABLE_NAME + " ORDER BY " +
                PlaceDAO.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
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

    @Override
    public boolean removePlaceItem(Place place) {
        return false;
    }
}
