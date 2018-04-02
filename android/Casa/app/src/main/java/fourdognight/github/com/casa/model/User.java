package fourdognight.github.com.casa.model;

import android.util.Log;
import android.view.Display;

import com.google.firebase.database.Exclude;

/**
 * Created by manle on 2/19/2018.
 */

public class User{
    private Shelter currentShelter;
    private int currentShelterUniqueKey;
    private int heldBeds;
    private String name;
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
        this("", "", "", -1, 0, false);
    }


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

    public boolean canStayAt(Shelter shelter) {
        return !isAdmin && (currentShelterUniqueKey == -1 || (currentShelterUniqueKey
                == shelter.getUniqueKey()) || heldBeds == 0);
    }

    public int getCurrentShelterUniqueKey() {
        return currentShelterUniqueKey;
    }

    void pushUserChanges() {
        manager.refactorVacancy(currentShelter);
        userVerificationModel.updateUserList(this);
        userVerificationModel.pushUserChanges();
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

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
