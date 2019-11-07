package elouazzani.ma.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import elouazzani.ma.myapplication.DBSQLITE.DB;
import elouazzani.ma.myapplication.Model.FeedBack;
import elouazzani.ma.myapplication.Model.Place;

public class FeedBackDAOImp  implements FeedBackDAO {
    private DB mdb;

    public FeedBackDAOImp(@Nullable Context context) {
        mdb=new DB(context);
    }



    @Override
    public boolean saveFeedback(FeedBack feedBack) {

        // get writable database as we want to write data
        SQLiteDatabase db=mdb.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FeedBackDAO.COLUMN_RATE,feedBack.getRate());
        values.put(FeedBackDAO.COLUMN_COMMENT,feedBack.getComment());
        values.put(FeedBackDAO.COLUMN_PLACE_ID,feedBack.getPlace().getId());

        long id=db.insert(FeedBackDAO.TABLE_NAME,null,values);
        db.close();

        if(id>0)
            return true;

        return false;
    }

    @Override
    public float averageRatingPlace(Place place) {
        float result=0,sum=0;
        // get readable database as we are not inserting anything
        SQLiteDatabase db = mdb.getReadableDatabase();
        String query="SELECT "+FeedBackDAO.COLUMN_RATE+" FROM "+ FeedBackDAO.TABLE_NAME +" WHERE place_id=?";
        Cursor cursor=db.rawQuery(query,new String[]{""+place.getId()});
        if(cursor.moveToFirst()) {
            do {
                sum += cursor.getFloat(cursor.getColumnIndex(FeedBackDAO.COLUMN_RATE));
            } while (cursor.moveToNext());
            result = (float)sum/cursor.getCount();
        }
        return result;
    }

    @Override
    public int totalRatingPlace(Place place) {

        int result=0;
        SQLiteDatabase db = mdb.getReadableDatabase();
        String query="SELECT * FROM "+FeedBackDAO.TABLE_NAME +" WHERE place_id=?";
        Cursor cursor=db.rawQuery(query,new String[]{""+place.getId()});
        if (cursor.moveToFirst())
           result=cursor.getCount();

        return result;
    }


}
