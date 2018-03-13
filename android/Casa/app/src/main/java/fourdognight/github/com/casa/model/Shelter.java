package fourdognight.github.com.casa.model;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    private List<String> currentPatrons;

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

    List<String> getCurrentPatrons() {
        return currentPatrons;
    }

    public Shelter (int uniqueKey, String shelterName, int capacity, int vacancy, String restriction, double longitude,
                    double latitude, String address, String special, String phone, List<String> newPatrons) {
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
        this.currentPatrons = new LinkedList<>();
        for (String user : newPatrons) {
            this.currentPatrons.add(user);
        }

    }

    void addPatron(User user) {
        currentPatrons.add(user.getUsername());
    }

    void removePatron(User user) {
        if (currentPatrons.contains(user.getUsername())) {
            currentPatrons.remove(user.getUsername());
        }
    }

    @Override
    public String toString() {
        return shelterName + " " + restriction + " " + address + " " + special + " " + phone
                + " ";
    }

    public boolean containsText(String text) {
        return toString().contains(text);
    }
}
