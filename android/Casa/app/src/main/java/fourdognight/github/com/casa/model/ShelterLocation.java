package fourdognight.github.com.casa.model;

import java.io.Serializable;

/**
 * Created by manle on 3/27/2018.
 */

public class ShelterLocation implements Serializable{
    private double longitude;
    private double latitude;
    private String address;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public ShelterLocation(double longitude, double latitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
