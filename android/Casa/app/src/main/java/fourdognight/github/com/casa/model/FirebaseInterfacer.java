package fourdognight.github.com.casa.model;

import android.location.Location;
import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by manle on 3/9/2018.
 */

public class FirebaseInterfacer {
    private List<Shelter> results;
    private FirebaseDatabase database;
    private ShelterManager shelterManager;
    private UserVerificationModel userVerificationModel;
    private static FirebaseInterfacer instance = new FirebaseInterfacer();

    private FirebaseInterfacer() {
        results = new LinkedList<Shelter>();
        database = FirebaseDatabase.getInstance();
    }

    void init() {
        shelterManager = ShelterManager.getInstance();
        userVerificationModel = UserVerificationModel.getInstance();
    }

    void getShelterData() {
        DatabaseReference myRef = database.getReference("shelterList");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                results.clear();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) itemSnapshot.getValue();
                    List<String> currentPatrons;
                    if (map.get("currentPatrons") != null) {
                        currentPatrons = (List<String>) map.get("currentPatrons");
                    } else {
                        currentPatrons = new ArrayList<>();
                    }
                    HashMap loc = (HashMap) map.get("location");
                    Shelter next = new Shelter(((Long) map.get("uniqueKey")).intValue(),
                            (String) map.get("shelterName"), ((Long) map.get("capacity")).intValue(),
                            ((Long) map.get("vacancy")).intValue(), (String) map.get("restriction"),
                            new ShelterLocation((double) loc.get("longitude"), (double) loc.get("latitude"),
                                    (String) loc.get("address")), (String) map.get("special"),
                            (String) map.get("phone"), currentPatrons);
                    results.add(next);
                }
                shelterManager.reload(results);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading shelter list.");
            }
        });
    }

    void getShelterDataUnique(int uniqueKey) {
        DatabaseReference myRef = database.getReference("shelterList/" + uniqueKey);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String, Object> loc = (HashMap<String, Object>) map.get("location");
                Shelter next = new Shelter(((Long) map.get("uniqueKey")).intValue(),
                        (String) map.get("shelterName"), ((Long) map.get("capacity")).intValue(),
                        ((Long) map.get("vacancy")).intValue(), (String) map.get("restriction"),
                        new ShelterLocation((double) loc.get("longitude"), (double) loc.get("latitude"),
                                (String) loc.get("address")), (String) map.get("special"),
                            (String) map.get("phone"), new LinkedList<String>());
                results.add(next);
                shelterManager.reloadUnique(next);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading shelter list.");
            }
        });
    }

    void getUserData() {
        DatabaseReference myRef = database.getReference("userList");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<AbstractUser> list = new ArrayList<>();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) itemSnapshot.getValue();
                    Log.d("Vacancy", (map.get("isAdmin") == null) ? "NULL" : "NOTNULL");
                    AbstractUser loadedUser = ((Boolean) map.get("isAdmin")) ? new Admin((String) map.get("name"), (String) map.get("username"), (String) map.get("password"))
                            : new User((String) map.get("name"), (String) map.get("username"),
                            (String) map.get("password"), ((Long) map.get("currentShelterUniqueKey")).intValue(),
                            ((Long) map.get("heldBeds")).intValue());
                    list.add(loadedUser);
                }
                userVerificationModel.updateUserList(list);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user list.");
            }
        });
    }

    void attemptLogin(final String username) {
        DatabaseReference myRef = database.getReference("userList/" + username);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    userVerificationModel.loadUserLogin(null);
                } else {
                    HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                    Log.d("Vacancy", (map.get("isAdmin") == null) ? "NULL" : "NOTNULL");
                    AbstractUser loadedUser = ((Boolean) map.get("isAdmin")) ? new Admin((String) map.get("name"), (String) map.get("username"), (String) map.get("password"))
                            : new User((String) map.get("name"), (String) map.get("username"),
                            (String) map.get("password"), ((Long) map.get("currentShelterUniqueKey")).intValue(),
                            ((Long) map.get("heldBeds")).intValue());
                    userVerificationModel.loadUserLogin(loadedUser);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void attemptRegistration(final String username) {
        DatabaseReference myRef = database.getReference("userList/" + username);

        Log.d("User", "NonePrevious");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    userVerificationModel.createNewUser();
                } else {
                    Log.d("User", "NonePrevious");
                    userVerificationModel.userExists();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void updateUser(AbstractUser user) {
        DatabaseReference myRef = database.getReference("userList");
        Map<String, Object> updatedUsers = new HashMap<>();
        updatedUsers.put(user.getUsername(), user);
        myRef.updateChildren(updatedUsers);

        myRef = database.getReference("userList/" + user.getUsername());
        updatedUsers = new HashMap<>();
        updatedUsers.put("isAdmin", user instanceof Admin);
        if (user instanceof User) {
            User myUser = (User) user;
            updatedUsers.put("currentShelterUniqueKey", myUser.getCurrentShelterUniqueKey());
        }
        myRef.updateChildren(updatedUsers);
    }

    void refactorVacancy(final Shelter shelter, final int newVacancy) {
        final DatabaseReference myRef = database.getReference("shelterList/" + shelter.getUniqueKey());
                            Map<String, Object> updatedEntries = new HashMap<>();
                            updatedEntries.put("vacancy", newVacancy);
                            myRef.updateChildren(updatedEntries);
    }

    static FirebaseInterfacer getInstance() {
        return instance;
    }
}
