package at.htlgkr.tourguide;

import java.io.Serializable;

public class Place extends Sehenswuerdigkeiten implements Serializable {

    private double lat;
    private double lon;

    public Place(String name, String details) {
        super(name, details);
    }

    public Place(String name, String details, double lat, double lon) {
        super(name, details);
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
