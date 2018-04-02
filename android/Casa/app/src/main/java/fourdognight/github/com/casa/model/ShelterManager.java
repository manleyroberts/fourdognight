package fourdognight.github.com.casa.model;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fourdognight.github.com.casa.MainScreenActivity;

/**
 * Created by manle on 3/11/2018.
 */

public class ShelterManager {
    private FirebaseInterfacer firebaseInterfacer;
    private HashMap<Integer, Shelter> shelterList;
    private ModelFacade model;
    private UserVerificationModel userModel;

    static private ShelterManager instance = new ShelterManager();

    ShelterManager() {
        shelterList = new HashMap<>();
    }

    void init() {
        firebaseInterfacer = FirebaseInterfacer.getInstance();
        model = ModelFacade.getInstance();
        userModel = UserVerificationModel.getInstance();
        firebaseInterfacer.init();
    }

    Shelter getShelter(int uniqueKey) {
        return shelterList.get(uniqueKey);
    }

    void getShelterData() {
        firebaseInterfacer.getShelterData();
    }

    void getShelterData(int uniqueKey) {
        firebaseInterfacer.getShelterDataUnique(uniqueKey);
    }

    void reload(List<Shelter> results) {
        shelterList.clear();
        final List<String> sheltersDisplay = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            shelterList.put(results.get(i).getUniqueKey(), results.get(i));
            sheltersDisplay.add(results.get(i).getShelterName());
        }
        model.reload(sheltersDisplay, results);
    }

    void reloadUnique(Shelter shelter) {
        model.reloadList(shelter);
    }

    void refactorVacancy(Shelter shelter) {
        if (shelter != null) {
            int newVacancy = shelter.getCapacity();
            List<User> patrons = userModel.usersAtShelter(shelter);
            for (User patron : patrons) {
                newVacancy -= patron.getHeldBeds();
            }
            firebaseInterfacer.refactorVacancy(shelter, newVacancy);
        }
    }

    boolean updateVacancy(Shelter shelter, User user, int bedsHeld) {
        if (bedsHeld >= 0 && shelter.getVacancy() - bedsHeld >= 0) {
//            Shelter oldShelter = user.getCurrentShelter();
//            user.releaseCurrentShelter();
//            refactorVacancy(oldShelter);
            user.setCurrentStatus(shelter.getUniqueKey(), bedsHeld);
            shelter.addPatron(user); Log.d("Refactor", shelter.getCurrentPatrons().toString());
            refactorVacancy(shelter);
            return true;
        } else {
            return false;
        }
    }

    static ShelterManager getInstance() {
        return instance;
    }
}
