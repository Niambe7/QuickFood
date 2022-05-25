package com.example.quickfood;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.quickfood.databinding.FragmentLocalisationBinding;

public class LocalisationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentLocalisationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_localisation, container, false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return  view ;
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


        LatLng quickfood = new LatLng(14.739116953365455, -17.315344458029372);
        mMap.addMarker(new MarkerOptions().position(quickfood).title("DkFoodies").snippet("Contact: 338973508"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(quickfood));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quickfood,15));
        CircleOptions co = new CircleOptions()
                .center(quickfood)
                .radius(400)
                .fillColor(Color.GREEN)
                .strokeColor(Color.BLACK)
                .strokeWidth(6);
        mMap.addCircle(co);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338973508"));
                startActivity(intent);
                return false;
            }
        });
    }
}