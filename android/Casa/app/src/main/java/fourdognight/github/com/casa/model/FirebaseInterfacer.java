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

import fourdognight.github.com.casa.LoginActivity;
import fourdognight.github.com.casa.MainScreenActivity;

/**
 * Created by manle on 3/9/2018.
 */

public class FirebaseInterfacer {
    List<String> results;
    FirebaseDatabase database;

    FirebaseInterfacer() {
        results = new LinkedList<String>();
        database = FirebaseDatabase.getInstance();
    }

    void getShelterData(final MainScreenActivity instance) {
        DatabaseReference myRef = database.getReference("shelterList");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                results.clear();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    String next = itemSnapshot.getValue(String.class);
                    results.add(next);
                    instance.reload(results);
                }
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
                            : new User((String) map.get("name"), (String) map.get("username"), (String) map.get("password"));
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
        myRef.updateChildren(updatedUsers);
    }

    //splits the csv into commas and if there are quotation marks then the commas inside quotations
    // are not removed
//    private void readHomelessShelterData() {
//        InputStream is = getResources().openRawResource(R.raw.homelessshelterdatabase);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//
//        String line = "";
//        try {
//            reader.readLine();
//            String read;
//            while ((read = reader.readLine()) != null) {
//                String[] row = read.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                if (!results.contains(row[0])) {
//                    for (int i = 0; i < row.length; i++) {
//                        if (row[i].indexOf('\"') > -1) {
//                            row[i] = row[i].split("\"")[1];
//                        }
//                        row[i] = row[i].trim();
//                        results.add(row[i]);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
//            e.printStackTrace();
//        }
//    }
}
