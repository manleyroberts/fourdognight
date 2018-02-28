package fourdognight.github.com.casa;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {

    private TextView mUsernameView;
    private List<String> results = new ArrayList();

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
        String topText = (String) getIntent().getExtras().get("currentUserName");
        topText += " | " + (String) getIntent().getExtras().get("currentUser");
        if ((Boolean) getIntent().getExtras().get("currentUserIsAdmin")) {
            topText += " | Admin";
        } else {
            topText += " | User";
        }
        mUsernameView.setText(topText);
        // Reads the CSV data
        readHomelessShelterData();
        List<String> shelters = new ArrayList();
        int count = 0;
        for (int i = 0; i < results.size(); i ++) {
            if (results.get(i).equals( "" + count)) {
                if (i > 2 && results.get(i - 2).equals("" + (count - 1))){
                    count += 0;
                } else {
                    shelters.add(results.get(i + 1));
                    count++;
                }
            }
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }

        ArrayAdapter adapter  = new ArrayAdapter<>(this, R.layout.shelterlist, shelters);
        //Creates the info page
        final List<String> info = results;
        final ListView listView = findViewById(R.id.shelterList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainScreenActivity.this, fourdognight.github.com.casa.ListActivity.class);
                intent.putExtra("ShelterName", listView.getItemAtPosition(i).toString());
                intent.putExtra("ShelterInfo", info.get(9 * i + 2));
                intent.putExtra("UniqueKey", info.get(9*i));
                intent.putExtra("Restrictions", info.get(9 * i + 3));
                intent.putExtra("Longitude", info.get(9 * i + 4));
                intent.putExtra("Latitude", info.get(9 * i + 5));
                intent.putExtra("Address", info.get(9 * i + 6));
                intent.putExtra("Special", info.get(9 * i + 7));
                intent.putExtra("Phone", info.get(9 * i + 8));
                startActivity(intent);
            }
        });
    }
    //splits the csv into commas and if there are quotation marks then the commas inside quotations
    // are not removed
    private void readHomelessShelterData() {
        InputStream is = getResources().openRawResource(R.raw.homelessshelterdatabase);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        try {
            reader.readLine();
            String read;
            while ((read = reader.readLine()) != null) {
                String[] row = read.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                for (int i = 0; i < row.length; i++) {
                    results.add(row[i]);
                }
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    private MainScreenActivity getInstance() {
        return this;
    }
}
