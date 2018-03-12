package fourdognight.github.com.casa.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fourdognight.github.com.casa.MainScreenActivity;

/**
 * Created by manle on 3/11/2018.
 */

public class ShelterManager {
    private FirebaseInterfacer firebaseInterfacer;
    private HashMap<Integer, Shelter> shelterList;
    private MainScreenActivity heldMainScreen;

    ShelterManager() {
        firebaseInterfacer = new FirebaseInterfacer();
        shelterList = new HashMap<>();
    }

    public void addShelter(Shelter shelter) {
        shelterList.put(shelter.getUniqueKey(), shelter);
    }

    Shelter getShelter(int uniqueKey) {
        return shelterList.get(uniqueKey);
    }

    void getShelterData(MainScreenActivity instance) {
        heldMainScreen = instance;
        firebaseInterfacer.getShelterData(this);
    }

    void reload(List<Shelter> results) {
        final List<String> sheltersDisplay = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            shelterList.put(results.get(i).getUniqueKey(), results.get(i));
            sheltersDisplay.add(results.get(i).getShelterName());
        }
        heldMainScreen.reload(sheltersDisplay);
    }

    boolean updateVacancy(Shelter shelter, User user, int bedsHeld) {
        if (bedsHeld >= 0 && shelter.getVacancy() - bedsHeld >= 0) {
            firebaseInterfacer.updateVacancy(shelter, user, bedsHeld);
            return true;
        } else {
            return false;
        }
    }
}
