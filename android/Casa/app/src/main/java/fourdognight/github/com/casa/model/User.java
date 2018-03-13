package fourdognight.github.com.casa.model;

import android.view.Display;

/**
 * Created by manle on 2/19/2018.
 */

public class User extends AbstractUser {
    private transient Shelter currentShelter;
    private int currentShelterUniqueKey;
    private int heldBeds;
//    private boolean locked;

    public User(String name, String username, String password, int currentShelterUniqueKey, int heldBeds) {
        super(name, username, password);
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
        ModelFacade model = ModelFacade.getInstance();
        this.currentShelter = model.getShelter(newShelterUniqueKey);
        this.currentShelterUniqueKey = newShelterUniqueKey;
        this.heldBeds = heldBeds;
        pushUserChanges();
    }

    void releaseCurrentShelter() {
        if (currentShelter != null) {
            currentShelter.removePatron(this);
        }
        currentShelter = null;
        heldBeds = 0;
    }

    public boolean canStayAt(Shelter shelter) {
        return currentShelter == null || (currentShelter.getUniqueKey()
                == shelter.getUniqueKey()) || heldBeds == 0;
    }

    int getCurrentShelterUniqueKey() {
        return currentShelterUniqueKey;
    }

    void pushUserChanges() {
        super.pushUserChanges();
        ShelterManager manager = ShelterManager.getInstance();
        manager.refactorVacancy(currentShelter);
        UserVerificationModel model = UserVerificationModel.getInstance();
        model.updateUserList(this);
    }
}
