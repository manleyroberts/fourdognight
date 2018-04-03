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
        results = new LinkedList<>();
        database = FirebaseDatabase.getInstance();
    }

    void init() {
        shelterManager = ShelterManager.getInstance();
        userVerificationModel = UserVerificationModel.getInstance();
    }

    void getShelterData(final Consumer<List<Shelter>> success) {
        DatabaseReference myRef = database.getReference("test/shelterList");

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
                            new ShelterLocation((double) loc.get("longitude"),
                                    (double) loc.get("latitude"), (String) loc.get("address")),
                            (String) map.get("special"), (String) map.get("phone"), currentPatrons);
                    results.add(next);
                    success.accept(results);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading shelter list.");
            }
        });
    }

    void getShelterDataUnique(int uniqueKey, final Consumer<Shelter> success) {
        DatabaseReference myRef = database.getReference("test/shelterList/" + uniqueKey);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String, Object> loc = (HashMap<String, Object>) map.get("location");
                Shelter shelter = new Shelter(((Long) map.get("uniqueKey")).intValue(),
                        (String) map.get("shelterName"), ((Long) map.get("capacity")).intValue(),
                        ((Long) map.get("vacancy")).intValue(), (String) map.get("restriction"),
                        new ShelterLocation((double) loc.get("longitude"), (double) loc.get("latitude"),
                                (String) loc.get("address")), (String) map.get("special"),
                            (String) map.get("phone"), new LinkedList<String>());
                results.add(shelter);
                success.accept(shelter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading shelter list.");
            }
        });
    }

    void getUserData() {
        final DatabaseReference myRef = database.getReference("test/userList");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> list = new ArrayList<>();
                Log.e("Count " ,"" + dataSnapshot.getChildrenCount());
                userVerificationModel.updateUserList(list);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user list.");
            }
        });
    }

    void attemptLogin(final String username, final String password, final Consumer<User> success,
                      final Runnable failure) {
        DatabaseReference myRef = database.getReference("test/userList/" + sanitize(username));


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    failure.run();
                } else {
                    User loadedUser = dataSnapshot.getValue(User.class);
                    if (loadedUser.authenticate(password)) {
                        success.accept(loadedUser);
                    } else {
                        failure.run();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void attemptRegistration(final String name, final String username, final String password,
                             final boolean isAdmin, final Runnable success, final Runnable failure) {
        DatabaseReference myRef = database.getReference("test/userList");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(sanitize(username))) {
                    updateUser(new User(name, username, password, -1, 0, isAdmin) );
                    success.run();
                } else {
                    failure.run();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading user.");
            }
        });
    }

    void updateUser(User user) {
        DatabaseReference listRef = database.getReference("test/userList");
        Map<String, Object> updatedUsers = new HashMap<>();
        updatedUsers.put(sanitize(user.getUsername()), user);
        listRef.updateChildren(updatedUsers);
        Log.d("Added", user.getUsername());
    }

    void refactorVacancy(final Shelter shelter, final int newVacancy) {
        final DatabaseReference myRef = database.getReference("test/shelterList/" + shelter.getUniqueKey());
                    Map<String, Object> updatedEntries = new HashMap<>();
                    updatedEntries.put("vacancy", newVacancy);
                    myRef.updateChildren(updatedEntries);
    }

    static FirebaseInterfacer getInstance() {
        return instance;
    }

    private static String sanitize(String dbPath) {
        String sanitized = dbPath.replaceAll("[\\.#\\$\\[\\]]", "");
        if (!sanitized.equals("")) {
            return sanitized;
        } else {
            return " ";
        }
    }
}
