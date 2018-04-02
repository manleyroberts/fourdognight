package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.Consumer;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;


public class SearchActivity extends AppCompatActivity {
    List<String> result = new ArrayList<>();
    Toolbar toolbar;
    EditText search;
    Button button;
    String input;
    ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        model = ModelFacade.getInstance();
        search = findViewById(R.id.editText4);
        model.getShelterData(new Consumer<List<Shelter>>() {
            @Override
            public void accept(List<Shelter> list) {
                reload(list);
            }
        });

        Button clear = findViewById(R.id.button2);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.clear();
                ListView listview = findViewById(R.id.SearchV);
                ArrayAdapter adapter = new ArrayAdapter<>(SearchActivity.this,R.layout.searchlist, result);
                listview.setAdapter(adapter);
                listview.setVisibility(View.VISIBLE);
            }
        });
    }

    public void reload(final List<Shelter> shelters) {
        getSupportActionBar().setTitle("Advanced Search");

        // creates the list upon a click of the button
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.clear();
                input = search.getText().toString();
                // temporary solution for input
                if (input.equalsIgnoreCase("FEMALE")) {
                    input = "Women";
                }
                if (input.equalsIgnoreCase("MALE")) {
                    input = "Men";
                }
                if (input.contains("with")) {
                    input.replaceAll("with","w/");
                }
                for (Shelter shelter : shelters) {
                    if (shelter.containsText(input)) {
                        result.add(shelter.getShelterName());
                    }
                }

                final ListView listview = findViewById(R.id.SearchV);
                ArrayAdapter adapter = new ArrayAdapter<>(SearchActivity.this,R.layout.searchlist, result);
                listview.setAdapter(adapter);
                listview.setVisibility(View.VISIBLE);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(SearchActivity.this, ListActivity.class);
                    intent.putExtra("Shelter", shelters.get(i));
                    startActivity(intent);
                    }
                });
            }
        });
    }
}
