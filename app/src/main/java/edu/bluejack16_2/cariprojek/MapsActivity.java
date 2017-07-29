package edu.bluejack16_2.cariprojek;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Utilities.GPSTracker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    private CameraPosition mCameraPosition;

    private GPSTracker gpsTracker;
    private Location location;
    private LatLng currLocation;
    private HashMap<Marker, String> mHashMap;
    private Vector<Project> projects;
    private Marker selectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //  `````````zzasObtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsTracker = new GPSTracker(getApplicationContext());
        mHashMap = new HashMap<>();
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

        // Add a marker to static Latitude Longitude
        LatLng monas = new LatLng(-6.1754f, 106.8272f);
        mMap.addMarker(new MarkerOptions().position(monas).title("Marker in Monas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(monas));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        getCurrentLocation();
        addProjectMarker();
        clickMarker();
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            updateLocationUI();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    private void getCurrentLocation(){
        try {
            location = gpsTracker.getLocation();

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            currLocation = new LatLng(latitude, longitude);

            drawCurrRadius(latitude, longitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

        }catch (Exception e){
            Log.e("location", String.valueOf(e));
        }

    }

    private void drawCurrRadius(double latitude, double longitude){

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(800)
                .strokeColor(0xffff0000)
                .strokeWidth(1)
                .fillColor(0x44ff0000));
    }

    private void getProject(){
        ProjectController.getInstance();
        projects = ProjectController.getProjects();
    }

    private void addProjectMarker(){
        getProject();
        for (Project project:projects) {
            if(project.getStatus().equals("Open"))
                isInDistance(project.getLatitude(), project.getLongitude(), project.getName(),                              project.getId());
        }

    }

    private void isInDistance(double targetLat, double targetLong, String projectName, String key){

        float[] dist = new float[1];

        Location.distanceBetween(location.getLatitude(), location.getLongitude(), targetLat, targetLong, dist);

        if(dist[0]/800 <= 1) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(targetLat, targetLong))
                    .title(projectName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            mHashMap.put(marker, key);
        }
    }

    private void clickMarker(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(selectedMarker != null && marker.toString().equals(selectedMarker.toString())) {
                    String key = mHashMap.get(marker);
                    Toast.makeText(MapsActivity.this, key, Toast.LENGTH_SHORT).show();
                }
                selectedMarker = marker;
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}