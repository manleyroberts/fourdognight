package fourdognight.github.com.casa.model;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

/**
 * Created by manle on 3/10/2018.
 */

public class Shelter implements Serializable{
    private String shelterName;
    private int capacity;
    private int vacancy;
    private int uniqueKey;
    private String restriction;
    private double longitude;
    private double latitude;
    private String address;
    private String special;
    private String phone;
    private List<User> currentPatrons;

    public String getShelterName() {
        return shelterName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getVacancy() {
        return vacancy;
    }

    public int getUniqueKey() {
        return uniqueKey;
    }

    public String getRestriction() {
        return restriction;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
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

    public List<User> getCurrentPatrons() {
        return currentPatrons;
    }

    public Shelter (int uniqueKey, String shelterName, int capacity, int vacancy, String restriction, double longitude,
                    double latitude, String address, String special, String phone, List<User> currentPatrons) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.vacancy = vacancy;
        this.uniqueKey = uniqueKey;
        this.restriction = restriction;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.special = special;
        this.phone = phone;
        this.currentPatrons = currentPatrons;
    }

    public void removePatron(User user) {
        currentPatrons.remove(user);
    }
}
