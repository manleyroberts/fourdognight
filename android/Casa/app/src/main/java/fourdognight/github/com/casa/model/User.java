package fourdognight.github.com.casa.model;

import com.google.firebase.database.Exclude;

/**
 * user is class containing info about the user
 * @author Manley Roberts
 * @version 1.0
 */
public class User{
    private Shelter currentShelter;
    private int currentShelterUniqueKey;
    private int heldBeds;
    private final String name;
    private final String username;
    private final String password;
    private final boolean isAdmin;

    @Exclude
    private final UserVerificationModel userVerificationModel;
    @Exclude
    private final ShelterManager manager;

//    private boolean locked;

    User(String name, String username, String password, int currentShelterUniqueKey,
            boolean isAdmin) {
        manager = ShelterManager.getInstance();

        userVerificationModel = UserVerificationModel.getInstance();
        this.currentShelterUniqueKey = currentShelterUniqueKey;
        this.name = name;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        setCurrentStatus(currentShelterUniqueKey, heldBeds);
    }

    User() {
        this("", "", "", -1, false);
    }

    /**
     * gets the number of held beds
     * @return the number of held beds
     */
    public int getHeldBeds() {
        return heldBeds;
    }

    void setCurrentStatus(int newShelterUniqueKey, int heldBeds) {
        if (currentShelter != null) {
            currentShelter.removePatron(this);
        }
        currentShelterUniqueKey = -1;
        this.currentShelter = manager.getShelter(newShelterUniqueKey);
        this.currentShelterUniqueKey = newShelterUniqueKey;
        this.heldBeds = heldBeds;
        pushUserChanges();
    }


    /**
     * checks if the user can stay at a particular shelter
     * @param shelter the shelter being checked for eligibility
     * @return true if can stay at shelter, false otherwise
     */
    public boolean canStayAt(Shelter shelter) {
        return !isAdmin && ((currentShelterUniqueKey == -1) || (currentShelterUniqueKey
                == shelter.getUniqueKey()) || (heldBeds == 0));
    }

    /**
     * gets the shelter key
     * @return the shelter key
     */
    public int getCurrentShelterUniqueKey() {
        return currentShelterUniqueKey;
    }

    private void pushUserChanges() {
        manager.refactorVacancy(currentShelter);
        userVerificationModel.updateUserList(this);
        userVerificationModel.pushUserChanges();
    }

    /**
     * gets the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * gets the username of the user
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    /**
     * checks if the user is an admin
     * @return true if admin, false otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String toString() {
        return name + " | " + username + ((isAdmin) ? " | Admin" : " | User");
    }

    @Override
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof User)) {
            return false;
        }
        String otherName = ((User) other).getUsername();
        return otherName.equals(username);
    }

    boolean authenticate(String password) {
        return password.equals(this.password);
    }
}
