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
    private HashMap<String, Shelter> shelterList;
    private static ShelterManager shelterManager = new ShelterManager();
    private MainScreenActivity heldMainScreen;

    ShelterManager() {
        firebaseInterfacer = new FirebaseInterfacer();
        shelterList = new HashMap<>();
    }

    public void addShelter(Shelter shelter) {
        shelterList.put(shelter.getShelterName(), shelter);
    }

    Shelter getShelter(String shelterName) {
        return shelterList.get(shelterName);
    }

    void getShelterData(MainScreenActivity instance) {
        heldMainScreen = instance;
        firebaseInterfacer.getShelterData(this);
    }

    void reload(List<String> results) {
        for (int i = 0; i < results.size()/9; i++) {
            shelterList.put((results.get(9 * i + 1)), new Shelter(results.get(9 * i + 1), results.get(9 * i + 2),
                            results.get(9 * i), results.get(9 * i + 3), results.get(9 * i + 4), results.get(9 * i + 5),
                    results.get(9 * i + 6), results.get(9 * i + 7), results.get(9 * i + 8)));
        }
        final List<String> sheltersDisplay = new ArrayList<>();
        //gets the shelter names for listview
        for (int i = 0; i < results.size()/9; i++) {
            sheltersDisplay.add(results.get(9 * i + 1));
        }
        heldMainScreen.reload(sheltersDisplay);
    }
}
