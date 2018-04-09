package fourdognight.github.com.casa.model;

import android.util.Log;
import android.util.SparseArray;
import java.util.function.Consumer;

import java.util.List;

/**
 * Singleton class which maintains the full collection of Shelters
 * @author Manley Roberts, Jared Duncan
 * @version 1.0
 */
final class ShelterManager {
    private FirebaseInterfacer firebaseInterfacer;
    private final SparseArray<Shelter> shelterList;
    private ModelFacade model;

    private static final ShelterManager instance = new ShelterManager();

    private ShelterManager() {
        shelterList = new SparseArray<>();
    }

    void init() {
        firebaseInterfacer = FirebaseInterfacer.getInstance();
        model = ModelFacade.getInstance();
    }

    Shelter getShelter(int uniqueKey) {
        return shelterList.get(uniqueKey);
    }

    void getShelterData(final Consumer<List<Shelter>> success) {
        firebaseInterfacer.getShelterData(list -> {
            for (Shelter shelter : list) {
                shelterList.put(shelter.getUniqueKey(), shelter);
            }
            success.accept(list);
        });
    }

    void getShelterDataUnique(int uniqueKey, final Consumer<Shelter> success) {
        firebaseInterfacer.getShelterDataUnique(uniqueKey, shelter -> {
            shelterList.put(shelter.getUniqueKey(), shelter);
            success.accept(shelter);
        });
    }

    void refactorVacancy(Shelter shelter) {
        if (shelter != null) {
            int newVacancy = shelter.getCapacity();
            List<User> patrons = model.usersAtShelter(shelter);
            for (User patron : patrons) {
                Log.d("LEGo'", "" + newVacancy);
                newVacancy -= patron.getHeldBeds();
            }

            shelter.setVacancy(newVacancy);
            firebaseInterfacer.refactorVacancy(shelter, newVacancy);
        }
    }

    static ShelterManager getInstance() {
        return instance;
    }
}
