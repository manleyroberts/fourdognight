package fourdognight.github.com.casa.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Created by manle on 3/9/2018.
 */

public class FirebaseInterfacer {
    List<Shelter> results;
    FirebaseDatabase database;

    public FirebaseInterfacer() {
        results = new LinkedList<Shelter>();
        database = FirebaseDatabase.getInstance();
    }

    void getShelterData(final ShelterManager instance) {
        DatabaseReference myRef = database.getReference("shelterList");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                results.clear();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) itemSnapshot.getValue();
                    Shelter next = new Shelter(((Long) map.get("uniqueKey")).intValue(),
                            (String) map.get("shelterName"), ((Long) map.get("capacity")).intValue(),
                            ((Long) map.get("vacancy")).intValue(), (String) map.get("restriction"),
                            (double) map.get("longitude"), (double) map.get("latitude"),
                            (String) map.get("address"), (String) map.get("special"), (String) map.get("phone"),
                            (List<User>) map.get("currentPatrons"));
                    results.add(next);
                }
                instance.reload(results);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading shelter list.");
            }
        });
    }

    void attemptLogin(final UserVerificationModel instance, final String username) {
        DatabaseReference myRef = database.getReference("userList/" + username);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    instance.loadUserLogin(null);
                } else {
                    HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                    AbstractUser loadedUser = ((Boolean) map.get("isAdmin"))
                            ? new Admin((String) map.get("name"), (String) map.get("username"), (String) map.get("password"))
                            : new User((String) map.get("name"), (String) map.get("username"),
                            (String) map.get("password"), ((Long) map.get("currentShelterUniqueKey")).intValue(),
                            ((Long) map.get("heldBeds")).intValue());
                    instance.loadUserLogin(loadedUser);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void attemptRegistration(final UserVerificationModel instance, final String username) {
        DatabaseReference myRef = database.getReference("userList/" + username);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    instance.createNewUser();
                } else {
                    instance.userExists();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void addNewUser(AbstractUser user) {
        DatabaseReference myRef = database.getReference("userList");
        Map<String, Object> updatedUsers = new HashMap<>();
        updatedUsers.put(user.getUsername(), user);
        myRef.updateChildren(updatedUsers);

        myRef = database.getReference("userList/" + user.getUsername());
        updatedUsers = new HashMap<>();
        updatedUsers.put("isAdmin", user instanceof Admin);
        updatedUsers.put("currentShelterUniqueKey", -1);
        myRef.updateChildren(updatedUsers);
    }

    void removePatron(Shelter shelter, User user) {
        DatabaseReference myRef = database.getReference("shelterList/" + shelter.getUniqueKey());
        List<User> shelterPatrons = shelter.getCurrentPatrons();
        shelterPatrons.remove(user);
        Map<String, Object> updatedEntries = new HashMap<>();
        updatedEntries.put("currentPatrons", shelterPatrons);
        myRef.updateChildren(updatedEntries);
    }
    void updateVacancy(Shelter shelter, User user, int bedsHeld) {
        DatabaseReference myRef = database.getReference("userList/" + user.getUsername());
        Map<String, Object> updatedEntries = new HashMap<>();
        if (user.getCurrentShelter() != null)
            user.getCurrentShelter().removePatron(user);
        removePatron(shelter, user);
        updatedEntries.put("heldBeds", bedsHeld);
        updatedEntries.put("currentShelterUniqueKey", shelter.getUniqueKey());
        myRef.updateChildren(updatedEntries);

        myRef = database.getReference("shelterList/" + shelter.getUniqueKey());
        updatedEntries.clear();
        List<User> shelterPatrons = shelter.getCurrentPatrons();
        shelterPatrons.add(user);
        updatedEntries.put("currentPatrons", shelterPatrons);
        updatedEntries.put("vacancy", shelter.getVacancy() - bedsHeld);
        myRef.updateChildren(updatedEntries);
    }
}
