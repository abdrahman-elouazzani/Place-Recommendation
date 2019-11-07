package elouazzani.ma.myapplication.Model;

import java.io.Serializable;

public class FeedBack implements Serializable {

    private int id;
    private float rate;
    private String comment;
    private Place place;

    public FeedBack(int id, float rate, String comment, Place place) {
        this.id = id;
        this.rate = rate;
        this.comment = comment;
        this.place = place;
    }

    public FeedBack( float rate, String comment, Place place) {

        this.rate = rate;
        this.comment = comment;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
