package fourdognight.github.com.casa.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/**
 * Shelter is a class containing information about shelters
 * @author Manley Roberts
 * @version 1.0
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

    /**
     * Gets the shelter name
     * @return the shelter name
     */
    public String getShelterName() {
        return shelterName;
    }

    /**
     * Gets the capacity of the shelter
     * @return the shelter capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the current vacancy of the shelter
     * @return the vacancy of the shelter
     */
    public int getVacancy() {
        return vacancy;
    }

    /**
     * Gets the unique key for the shelter
     * @return the shelter's unique key
     */
    public int getUniqueKey() {
        return uniqueKey;
    }

    /**
     * Gets the restrictions for this shelter, represented by a string (parsed from the csv)
     * @return the String representing the restrictions on the shelter
     */
    public String getRestriction() {
        return restriction;
    }

    /**
     * Gets the location of the shelter, represented by a ShelterLocation
     * @return the location of the shelter
     */
    public ShelterLocation getLocation() { return location; }

    /**
     * Gets the special information (notes) about the shelter
     * @return the special information about the shelter
     */
    public CharSequence getSpecial() {
        return special;
    }

    /**
     * Gets the phone number for the shelter
     * @return the shelter's phone number
     */
    public CharSequence getPhone() {
        return phone;
    }

    /**
     * Constructor for a Shelter
     * @param uniqueKey the unique key for the shelter
     * @param shelterName the name of the shelter
     * @param capacity the capacity of the shelter
     * @param vacancy the initial vacancy of the shelter
     * @param restriction the String representing the restrictions for the shelter
     * @param location the location of the shelter
     * @param special the special notes about the shelter
     * @param phone the phone number for the shelter
     * @param newPatrons a list of new patrons
     */
    public Shelter (int uniqueKey, String shelterName, int capacity, int vacancy,
            String restriction, ShelterLocation location, String special, String phone,
            Collection<String> newPatrons) {
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

    /**
     * Default constructor
     */
    public Shelter() {

    }

    private void addPatron(User user) {
        currentPatrons.add(user.getUsername());
    }

    void removePatron(User user) {
        if ((user != null) && currentPatrons.contains(user.getUsername())) {
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

    boolean updateVacancy(User user, int bedsHeld) {
        if ((bedsHeld >= 0) && ((getVacancy() - bedsHeld) >= 0)) {
            user.setCurrentStatus(getUniqueKey(), bedsHeld);
            addPatron(user);
            return true;
        } else {
            return false;
        }
    }

}
