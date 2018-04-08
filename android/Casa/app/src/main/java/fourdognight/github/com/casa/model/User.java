package fourdognight.github.com.casa.model;

<<<<<<< HEAD
import android.util.Log;
import android.view.Display;

=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
import com.google.firebase.database.Exclude;

/**
 * Created by manle on 2/19/2018.
 */

public class User{
    private Shelter currentShelter;
    private int currentShelterUniqueKey;
    private int heldBeds;
    private String name;
<<<<<<< HEAD
    private String username;
    private String password;
    private boolean isAdmin;

    @Exclude
    private UserVerificationModel userVerificationModel;
    @Exclude
    private ShelterManager manager;

//    private boolean locked;

    User(String name, String username, String password, int currentShelterUniqueKey, int heldBeds,
         boolean isAdmin) {
=======
    private final String username;
    private String password;
    private final boolean isAdmin;

    @Exclude
    private final UserVerificationModel userVerificationModel;
    @Exclude
    private final ShelterManager manager;

//    private boolean locked;

    User(String name, String username, String password, int currentShelterUniqueKey,
            boolean isAdmin) {
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
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
<<<<<<< HEAD
        this("", "", "", -1, 0, false);
    }

    /**
     * gets the number beds held
     * @return the number of beds held
     */
=======
        this("", "", "", -1, false);
    }


>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
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

//    void releaseCurrentShelter() {
//        if (currentShelter != null) {
//            currentShelter.removePatron(this);
//        }
//        heldBeds = 0;
//        currentShelterUniqueKey = -1;
//        pushUserChanges();
//    }

<<<<<<< HEAD
    /**
     * checks to see if the user can stay at particular shelter
     * @param shelter the shelter being checked
     * @return true if can stay at shelter, false otherwise
     */
=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    public boolean canStayAt(Shelter shelter) {
        return !isAdmin && (currentShelterUniqueKey == -1 || (currentShelterUniqueKey
                == shelter.getUniqueKey()) || heldBeds == 0);
    }

<<<<<<< HEAD
    /**
     * gets the shelter key
     * @return the shelter key
     */
=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    public int getCurrentShelterUniqueKey() {
        return currentShelterUniqueKey;
    }

<<<<<<< HEAD
    void pushUserChanges() {
=======
    private void pushUserChanges() {
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
        manager.refactorVacancy(currentShelter);
        userVerificationModel.updateUserList(this);
        userVerificationModel.pushUserChanges();
    }

<<<<<<< HEAD
    /**
     * gets the shelter name
     * @return the shelter name
     */
=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    public String getName() {
        return name;
    }

<<<<<<< HEAD
    void setName(String name) {
        this.name = name;
    }

    /**
     * gets the user name
     * @return the user name
     */
=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    public String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

<<<<<<< HEAD
    void setPassword(String password) {
        this.password = password;
    }

    /**
     * checks if the user is an admin
     * @return true if user is admin, false otherwise
     */
=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    public boolean isAdmin() {
        return isAdmin;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> 91f8d5eae08a78437e9af5442e6c48ccb554526b
    public boolean equals(Object other) {
        if (other == null || !(other instanceof User)) {
            return false;
        }
        String otherName = ((User) other).getUsername();
        return otherName.equals(username);
    }

    boolean authenticate(String password) {
        return password.equals(this.password);
    }
}
