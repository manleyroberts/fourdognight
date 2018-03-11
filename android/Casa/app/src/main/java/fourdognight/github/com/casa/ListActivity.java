package fourdognight.github.com.casa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fourdognight.github.com.casa.model.Shelter;

public class ListActivity extends AppCompatActivity {


    TextView shelterName;
    TextView capacity;
    TextView key;
    TextView restriction;
    TextView longitude;
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
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        address = findViewById(R.id.address);
        special = findViewById(R.id.special);
        phone = findViewById(R.id.phone);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Shelter shelter = (Shelter) bundle.get("Shelter");
            shelterName.setText(shelter.getShelterName());
            capacity.setText(shelter.getShelterInfo());
            key.setText(shelter.getUniqueKey());
            restriction.setText(shelter.getRestriction());
            longitude.setText(shelter.getLongitude());
            latitude.setText(shelter.getLatitude());
            address.setText(shelter.getAddress());
            special.setText(shelter.getSpecial());
            phone.setText(shelter.getPhone());
        }



    }
}
