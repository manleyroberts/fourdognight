package fourdognight.github.com.casa;

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

import fourdognight.github.com.casa.model.ModelFacade;
import fourdognight.github.com.casa.model.Shelter;
import fourdognight.github.com.casa.model.ShelterLocation;

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
        model.getShelterData(this);
    }

    public void reload(List<Shelter> shelters) {
        this.shelters = shelters;
        updateMarkers();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
