package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.Consumer;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;

/**
 * @author Jared Duncan, Evan Mi, Manley Roberts
 * @version 1.0
 *
 * searches for a certain shelter
 */
public class SearchActivity extends AppCompatActivity{
    ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        model = ModelFacade.getInstance();
//        model.getShelterData(new Consumer<List<Shelter>>() {
//            @Override
//            public void accept(List<Shelter> list) {
//                reload(list);
//            }
//        });

        final Button nameButton = findViewById(R.id.nameButton);
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameField = findViewById(R.id.nameField);
                String name = nameField.getText().toString();
                if (!name.isEmpty()) {
                    restrictionCallback(name);
                }
            }
        });

        final Spinner ageSpinner = findViewById(R.id.ageSpinner);
        String[] ages = new String[] {
                "Families", "Children", "Young Adults", "Anyone"
        };
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ages);
        ageSpinner.setAdapter(ageAdapter);

        final Button ageButton = findViewById(R.id.ageButton);
        ageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restrictionCallback(ageSpinner.getSelectedItem().toString());
            }
        });

        final Spinner genderSpinner = findViewById(R.id.genderSpinner);
        String[] genders = new String[] {
                "Men", "Women"
        };
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genders);
        genderSpinner.setAdapter(genderAdapter);

        final Button genderButton = findViewById(R.id.genderButton);
        genderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restrictionCallback(genderSpinner.getSelectedItem().toString());
            }
        });
    }

    private void restrictionCallback(String filter) {
        Intent intent = new Intent(SearchActivity.this, MainScreenActivity.class);
        intent.putExtra("restrictionFilter", filter.toLowerCase());
        startActivity(intent);
    }
}
