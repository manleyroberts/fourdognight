package fourdognight.github.com.casa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainScreenActivity extends AppCompatActivity {

    private TextView mUsernameView;
    public static List<String> results = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        final Button button = findViewById(R.id.logOutButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        mUsernameView = findViewById(R.id.mainScreenUsernameField);
        AbstractUser user = (AbstractUser) getIntent().getExtras().get("currentUser");
        String topText = user.getName();
        topText += " | " + user.getUsername();
        if (user instanceof Admin) {
            topText += " | Admin";
        } else {
            topText += " | User";
        }
        mUsernameView.setText(topText);
        // Reads the CSV data
//        readHomelessShelterData();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shelterList");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                results.clear();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    results.add(itemSnapshot.getValue(String.class));
                    Log.d("Firebase", results.get(results.size() - 1));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error reading shelter list.");
            }
        });
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                results.add()
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {}
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });

        List<String> shelters = new ArrayList<>();
        //gets the shelter names for listview
        Log.d("Firebase", "" + results.size());
        for (int i = 0; i < results.size()/9; i++) {
            shelters.add(results.get(9 * i + 1));
            Log.d("Firebase", shelters.get(shelters.size() - 1));
        }



        adapter  = new ArrayAdapter<>(this, R.layout.shelterlist, shelters);
        //Creates the info page
        final List<String> info = results;
        final ListView listView = findViewById(R.id.shelterList);
        listView.setAdapter(adapter);
        // Creates search bar for names
        EditText search = findViewById(R.id.searchbar2);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainScreenActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // retrieves information for the info page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainScreenActivity.this, fourdognight.github.com.casa.ListActivity.class);
                String sheltername = listView.getItemAtPosition(i).toString();
                int index = info.indexOf(sheltername);
                intent.putExtra("ShelterName", sheltername);
                intent.putExtra("ShelterInfo", info.get(index + 1));
                intent.putExtra("UniqueKey", info.get(index - 1));
                intent.putExtra("Restrictions", info.get(index + 2));
                intent.putExtra("Longitude", info.get(index + 3));
                intent.putExtra("Latitude", info.get(index + 4));
                intent.putExtra("Address", info.get(index + 5));
                intent.putExtra("Special", info.get(index + 6));
                intent.putExtra("Phone", info.get(index + 7));
                startActivity(intent);
            }
        });
        // goes to the advanced search page to look for the restrictions and other stuff
        Button searchbar = findViewById(R.id.search);
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
//        myRef.setValue(results);
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


    private MainScreenActivity getInstance() {
        return this;
    }
}
