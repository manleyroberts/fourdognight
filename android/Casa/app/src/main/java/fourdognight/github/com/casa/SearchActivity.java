package fourdognight.github.com.casa;

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


public class SearchActivity extends AppCompatActivity {
    final List<String> shelters = new ArrayList<>();
    List<String> result = new ArrayList<>();
    Toolbar toolbar;
    EditText search;
    Button button;
    String input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        search = findViewById(R.id.editText4);

        getSupportActionBar().setTitle("Advanced Search");
        for (int i = 0; i < MainScreenActivity.results.size(); i++) {
            shelters.add(MainScreenActivity.results.get(i));
        }

        // creates the list upon a click of the button
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            int count = 1;
            final List<Integer> index = new ArrayList<>();
            for (int i = 0; i < shelters.size(); i++) {
                if (shelters.get(i).contains(input) || shelters.get(i).equalsIgnoreCase(input)) {
                    if (!result.contains(shelters.get(count))) {
                        result.add(shelters.get(count));
                        index.add(count);
                    }
                }
                if ( i % 9 == 0 && i > 0) {
                    count += 9;
                }
            }

            final ListView listview = findViewById(R.id.SearchV);
            ArrayAdapter adapter = new ArrayAdapter<>(SearchActivity.this,R.layout.searchlist, result);
            listview.setAdapter(adapter);
            listview.setVisibility(View.VISIBLE);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, fourdognight.github.com.casa.ListActivity.class);
                intent.putExtra("ShelterName", listview.getItemAtPosition(i).toString());
                intent.putExtra("ShelterInfo", shelters.get(index.get(i) + 1));
                intent.putExtra("UniqueKey", shelters.get(index.get(i) - 1));
                intent.putExtra("Restrictions", shelters.get(index.get(i) + 2));
                intent.putExtra("Longitude", shelters.get(index.get(i) + 3));
                intent.putExtra("Latitude", shelters.get(index.get(i) + 4));
                intent.putExtra("Address", shelters.get(index.get(i) + 5));
                intent.putExtra("Special", shelters.get(index.get(i) + 6));
                intent.putExtra("Phone", shelters.get(index.get(i) + 7));
                startActivity(intent);
                }
            });
            }
        });
        //Clears the list
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

}
