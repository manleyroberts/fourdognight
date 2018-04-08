package fourdognight.github.com.casa.ui;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import fourdognight.github.com.casa.R;
import fourdognight.github.com.casa.model.Consumer;
import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.ShelterLocation;

/**
 * @author Jared Duncan
 * @version 1.0
 *
 * creates a page for the map of the locations of shelters
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Shelter> shelters;
    private ModelFacade model;
    private HashMap<Marker, Shelter> markerShelterMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        model = ModelFacade.getInstance();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.shelters = (List<Shelter>) bundle.get("Shelters");
            updateMarkers();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng atlanta = new LatLng(33.753746, -84.386330);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(atlanta, 11));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (markerShelterMap == null) {
                    return false;
                }
                Intent intent = new Intent(MapsActivity.this, ListActivity.class);
                Shelter shelter = markerShelterMap.get(marker);
                intent.putExtra("Shelter", shelter);
                startActivity(intent);
                return true;
            }
        });
        updateMarkers();
    }

    private void updateMarkers() {
        if (mMap == null || this.shelters == null) {
            return;
        }
        mMap.clear();
        markerShelterMap = new HashMap<>();
        for (Shelter shelter : this.shelters) {
            ShelterLocation location = shelter.getLocation();
            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions options = new MarkerOptions();
            options.position(position);
            options.title(shelter.getShelterName());
            Marker marker = mMap.addMarker(options);
            markerShelterMap.put(marker, shelter);
        }
    }
}
