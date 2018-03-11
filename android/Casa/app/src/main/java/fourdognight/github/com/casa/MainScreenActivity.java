package fourdognight.github.com.casa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import fourdognight.github.com.casa.model.AbstractUser;
import fourdognight.github.com.casa.model.Admin;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.ShelterManager;

public class MainScreenActivity extends AppCompatActivity {

    private TextView mUsernameView;
    public static List<String> results;
    private ArrayAdapter adapter;
    private ModelFacade model;

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

        model = new ModelFacade();
        // Reads the CSV data
//        readHomelessShelterData();
        model.getShelterData(this);
    }

    public void reload(final List<String> sheltersDisplay) {
        adapter  = new ArrayAdapter<>(this, R.layout.shelterlist, sheltersDisplay);
        //Creates the info page
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
                Shelter shelter = model.getShelter(sheltersDisplay.get(i));
                intent.putExtra("Shelter", shelter);
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
    }

    private MainScreenActivity getInstance() {
        return this;
    }
}
