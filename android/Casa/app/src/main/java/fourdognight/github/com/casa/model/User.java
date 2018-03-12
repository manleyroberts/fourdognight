package fourdognight.github.com.casa.model;

import android.view.Display;

/**
 * Created by manle on 2/19/2018.
 */

public class User extends AbstractUser {
    private Shelter currentShelter;
    private int heldBeds;
//    private boolean locked;

    public User(String name, String username, String password, int currentShelterUniqueKey, int heldBeds) {
        super(name, username, password);
        setCurrentShelter(currentShelterUniqueKey);
        setHeldBeds(heldBeds);
    }

    public Shelter getCurrentShelter() {
        return currentShelter;
    }

    public int getHeldBeds() {
        return heldBeds;
    }

    public void setCurrentShelter(int newShelterUniqueKey) {
        this.currentShelter = ModelFacade.getInstance().getShelter(newShelterUniqueKey);
    }

    public void setHeldBeds(int heldBeds) {
        this.heldBeds = heldBeds;
    }
}
