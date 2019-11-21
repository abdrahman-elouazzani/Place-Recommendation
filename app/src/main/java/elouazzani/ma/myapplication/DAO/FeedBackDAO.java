package elouazzani.ma.myapplication.DAO;

import elouazzani.ma.myapplication.Model.FeedBack;
import elouazzani.ma.myapplication.Model.Place;

public interface FeedBackDAO {

    public static final String TABLE_NAME = "feedbacks";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_PLACE_ID = "place_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_RATE+ " REAL,"
                    + COLUMN_COMMENT + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_PLACE_ID + " INTEGER,"
                    + "FOREIGN KEY("+ COLUMN_PLACE_ID+ ") REFERENCES "+PlaceDAO.TABLE_NAME+" (id)"+" ON DELETE CASCADE"
                    + ")";

    public boolean saveFeedback(FeedBack feedBack);
    public float averageRatingPlace(Place place);
    public int totalRatingPlace(Place place);
    public boolean removeFeedBack(Place place);

}
