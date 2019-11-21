package elouazzani.ma.myapplication.DAO;

import java.util.List;

import elouazzani.ma.myapplication.Model.Place;

public interface PlaceDAO {

    public static final String TABLE_NAME = "places";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_BYTE = "image_byte";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_CITY + " TEXT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_IMAGE_BYTE + " BLOG,"
                    + COLUMN_RATE + " REAL,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public boolean AddPlaceItem(Place place);
    public Place getPlaceItem(long id);
    public List<Place> getAllPlaces();
    public boolean updatePlaceByRate(Place place, float rate);
    public List<Place> search(String keyword);
    public boolean removePlaceItem(Place place);
}
