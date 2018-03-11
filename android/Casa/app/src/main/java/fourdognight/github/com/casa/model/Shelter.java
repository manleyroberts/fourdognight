package fourdognight.github.com.casa.model;

import java.io.Serializable;

/**
 * Created by manle on 3/10/2018.
 */

public class Shelter implements Serializable{
    private String shelterName;
    private String shelterInfo;
    private String uniqueKey;
    private String restriction;
    private String longitude;
    private String latitude;
    private String address;
    private String special;
    private String phone;

    public String getShelterName() {
        return shelterName;
    }

    public String getShelterInfo() {
        return shelterInfo;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public String getRestriction() {
        return restriction;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getSpecial() {
        return special;
    }

    public String getPhone() {
        return phone;
    }

    Shelter (String shelterName, String shelterInfo, String uniqueKey, String restriction, String longitude,
                    String latitude, String address, String special, String phone) {
        this.shelterName = shelterName;
        this.shelterInfo = shelterInfo;
        this.uniqueKey = uniqueKey;
        this.restriction = restriction;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.special = special;
        this.phone = phone;
    }
}
