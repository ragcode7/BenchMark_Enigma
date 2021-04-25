package com.benchmarkChat.chatapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            return;
        }
        mMap.setMyLocationEnabled(true);
        Log.e("TAG", "onMapReady: " );
        final Marker[] marker = new Marker[1];
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(marker[0] != null){
                    marker[0].remove();
                }
                Log.e("TAG", "onDataChange: " + snapshot );
                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        if(!snap.getKey().equals(FirebaseAuth.getInstance().getUid()))
                            if(snap.child("LAT").getValue() != null && snap.child("LONG").getValue() != null){
                                LatLng latLng = new LatLng(Double.parseDouble(snap.child("LAT").getValue().toString()) , Double.parseDouble(snap.child("LONG").getValue().toString()));
                                marker[0] = mMap.addMarker(new MarkerOptions().position(latLng).title(snap.child("username").getValue().toString()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
                            }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onDataChange: " + error );
            }
        };

        databaseReference.addValueEventListener(valueEventListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //databaseReference.removeEventListener(valueEventListener);
    }
}