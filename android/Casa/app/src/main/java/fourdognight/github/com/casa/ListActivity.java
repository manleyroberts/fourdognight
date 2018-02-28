package fourdognight.github.com.casa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {


    TextView shelterName;
    TextView capacity;
    TextView key;
    TextView restriction;
    TextView longitde;
    TextView latitude;
    TextView address;
    TextView special;
    TextView phone;
    //Creates all the "text boxes" for the shelter information on page when shelter is clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        shelterName = findViewById(R.id.sheltertext);
        capacity = findViewById(R.id.infotext);
        key = findViewById(R.id.uniqueKey);
        restriction = findViewById(R.id.restrict);
        longitde = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        address = findViewById(R.id.address);
        special = findViewById(R.id.special);
        phone = findViewById(R.id.phone);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shelterName.setText(bundle.getString("ShelterName"));
            capacity.setText(bundle.getString("ShelterInfo"));
            key.setText(bundle.getString("UniqueKey"));
            restriction.setText(bundle.getString("Restrictions"));
            longitde.setText(bundle.getString("Longitude"));
            latitude.setText(bundle.getString("Latitude"));
            address.setText(bundle.getString("Address"));
            special.setText(bundle.getString("Special"));
            phone.setText(bundle.getString("Phone"));
        }



    }
}
