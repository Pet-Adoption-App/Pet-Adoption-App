package com.company.petadoptionapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.company.petadoptionapp.databinding.ActivitySetPetLocationBinding;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteFragment;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SetPetLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivitySetPetLocationBinding binding;
    private SearchView svMap;
    private final float DEFAULT_ZOOM = 15f;
    private LatLng petLatLng;
    private Marker petMarker;
    private Address address;
    private Button btnSubmitLocation;
    private Boolean searchClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySetPetLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        svMap = findViewById(R.id.svMap);
        btnSubmitLocation = (Button) findViewById(R.id.btnSubmitLocation);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        svMap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location = svMap.getQuery().toString();
                List<Address> addressesList = null;

                if (location != null || !location.equals("")) {
                    searchClick = true;
                    Geocoder geocoder = new Geocoder((SetPetLocation.this));
                    try {
                        addressesList = geocoder.getFromLocationName(location, 1);
                        address = addressesList.get(0);
                        petMarker.setPosition(new LatLng(address.getLatitude(),address.getLongitude()));
                        moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM);

                    } catch (IOException e) {

                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        btnSubmitLocation.setOnClickListener(view -> {
//            if(searchClick){
//                Intent i = new Intent(SetPetLocation.this,AddPets.class);
//                i.putExtra("address",address);
//                startActivity(i);
//            }else{
//
//                Toast.makeText(this, "Please Select a Location", Toast.LENGTH_SHORT).show();
//            }
        });

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
        LatLng India = new LatLng(20,79);
        petMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(82,135)));
        petMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pet_marker));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(India));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);

        // Add a marker in Sydney and move the camera
    }

    public void moveCamera(LatLng latLng , float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
}