package fourdognight.github.com.casa.model;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by manle on 3/11/2018.
 */

public class ShelterManager {
    private HashMap<String, Shelter> shelterList;
    private static ShelterManager shelterManager = new ShelterManager();

    public ShelterManager() {
        shelterList = new HashMap<>();
    }

    public void addShelter(Shelter shelter) {
        shelterList.put(shelter.getShelterName(), shelter);
    }

    public Shelter getShelter(String shelterName) {
        return shelterList.get(shelterName);
    }

    public static ShelterManager getInstance() {
        return shelterManager;
    }
}
