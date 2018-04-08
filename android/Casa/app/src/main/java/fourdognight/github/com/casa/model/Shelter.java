package fourdognight.github.com.casa.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
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
    private ShelterLocation location;
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

    public ShelterLocation getLocation() { return location; }

    public CharSequence getSpecial() {
        return special;
    }

    public CharSequence getPhone() {
        return phone;
    }

    public Shelter (int uniqueKey, String shelterName, int capacity, int vacancy, String restriction,
                    ShelterLocation location, String special, String phone, Collection<String> newPatrons) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.vacancy = vacancy;
        this.uniqueKey = uniqueKey;
        this.restriction = restriction;
        this.location = location;
        this.special = special;
        this.phone = phone;
        this.currentPatrons = new LinkedList<>();
        this.currentPatrons.addAll(newPatrons);

    }

    public Shelter() {

    }

    void addPatron(User user) {
        currentPatrons.add(user.getUsername());
    }

    void removePatron(User user) {
        if (user != null && currentPatrons.contains(user.getUsername())) {
            currentPatrons.remove(user.getUsername());
        }
    }

    @Override
    public String toString() {
        return shelterName + " " + restriction + " " + location.getAddress() + " " + special
                + " " + phone + " ";
    }

    void setVacancy(int newVac) {
        vacancy = newVac;
    }

}
