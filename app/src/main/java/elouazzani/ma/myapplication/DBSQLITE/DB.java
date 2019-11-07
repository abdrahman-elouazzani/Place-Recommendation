package elouazzani.ma.myapplication.DBSQLITE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import elouazzani.ma.myapplication.DAO.FeedBackDAO;
import elouazzani.ma.myapplication.DAO.PlaceDAO;

public class DB extends SQLiteOpenHelper {

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "visit_me_db";

    public DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(PlaceDAO.CREATE_TABLE);
        db.execSQL(FeedBackDAO.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ PlaceDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FeedBackDAO.TABLE_NAME);
        //
        onCreate(db);
    }
}
