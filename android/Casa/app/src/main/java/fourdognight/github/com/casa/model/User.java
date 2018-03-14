package fourdognight.github.com.casa.model;

import android.util.Log;
import android.view.Display;

import com.google.firebase.database.Exclude;

/**
 * Created by manle on 2/19/2018.
 */

public class User extends AbstractUser {
    private Shelter currentShelter;
    private int currentShelterUniqueKey;
    private int heldBeds;
    @Exclude
    private ShelterManager manager;
//    private boolean locked;

    User(String name, String username, String password, int currentShelterUniqueKey, int heldBeds) {
        super(name, username, password);
        manager = ShelterManager.getInstance();
        this.currentShelterUniqueKey = currentShelterUniqueKey;
        setCurrentStatus(currentShelterUniqueKey, heldBeds);
    }

    public Shelter getCurrentShelter() {
        return currentShelter;
    }

    public int getHeldBeds() {
        return heldBeds;
    }

    void setCurrentStatus(int newShelterUniqueKey, int heldBeds) {
        this.currentShelter = manager.getShelter(newShelterUniqueKey);
        this.currentShelterUniqueKey = newShelterUniqueKey;
        this.heldBeds = heldBeds;
        pushUserChanges();
    }

    void releaseCurrentShelter() {
        if (currentShelter != null) {
            currentShelter.removePatron(this);
        }
        heldBeds = 0;
        pushUserChanges();
        currentShelterUniqueKey = -1;
        currentShelter = null;
        pushUserChanges();
    }

    public boolean canStayAt(Shelter shelter) {
        return currentShelter == null || (currentShelter.getUniqueKey()
                == shelter.getUniqueKey()) || heldBeds == 0;
    }

    int getCurrentShelterUniqueKey() {
        return currentShelterUniqueKey;
    }

    void pushUserChanges() {

        Log.d("Vacancy", manager == null ? "YES" : "NOT");
        manager.refactorVacancy(currentShelter);
        userVerificationModel.updateUserList(this);
        super.pushUserChanges();
    }
}
