package fourdognight.github.com.casa.model;

import java.io.Serializable;

/**
 * Class representing a shelter location, including latitude/longitude values and
 * address
 * @author Manley Roberts
 * @version 1.0
 */
public class ShelterLocation implements Serializable{
    private double longitude;
    private double latitude;
    private String address;

    /**
     * Gets the longitude of this ShelterLocation
     * @return the longitude of this ShelterLocation
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the latitude of this ShelterLocation
     * @return the latitude of this ShelterLocation
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the address of this ShelterLocation
     * @return the address of this ShelterLocation
     */
    public CharSequence getAddress() {
        return address;
    }

    /**
     * Constructor for ShelterLocation
     * @param longitude the shelter's longitude
     * @param latitude the shelter's latitude (https://goo.gl/a9G5eG)
     * @param address the shelter's street address
     */
    public ShelterLocation(double longitude, double latitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    /**
     * Default constructor
     */
    public ShelterLocation() {

    }
}
