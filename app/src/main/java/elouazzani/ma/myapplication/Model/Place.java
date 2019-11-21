package elouazzani.ma.myapplication.Model;

import java.io.Serializable;

public class Place implements Serializable {
    private long id;
    private String title,description,address,city,type;
    private byte[] imageByte;
    private float rate;

    public float getRate() { return rate; }

    public void setRate(float rate) { this.rate = rate; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }


    public Place(String title, String city,String type, String address, String description, byte[] imageByte) {

        this.title = title;
        this.city=city;
        this.type=type;
        this.description = description;
        this.address = address;
        this.imageByte=imageByte;
    }

    public Place(long id,String title,String city,String type,String address, String description,
                 byte[] imageByte) {
        this.id=id;
        this.title = title;
        this.city=city;
        this.type=type;
        this.description = description;
        this.address = address;
        this.imageByte=imageByte;

    }
}
