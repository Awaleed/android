package maksab.sd.customer.basecode.fragments;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import maksab.sd.customer.R;
import maksab.sd.customer.models.services.QuotationStepperModel;
import maksab.sd.customer.storage.OrderStorage;
import maksab.sd.customer.ui.main.activties.CategoriesActivity;

public class MapFragment extends Fragment implements FragmentsContract {
    private static final int REQUEST_GPS_SETTINGS = 1;
    private static final int REQUEST_LOCATION_GRANT_PERMISSION = 2;
    private static final String IS_FOR_CHOOSE_LOCATION_ARG = "is_for_choose_location";

    private boolean isForChooseLocation;
    private MapView mMapView;
    private GoogleMap googleMap;
    private OrderStorage orderStorage;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private LatLng khartoumMiddlePoint = new LatLng(Float.parseFloat("15.5016995"), Float.parseFloat("32.5025565"));

    private boolean isUserSelectLocation = false;
    private int cameraMoveCount = 0;

    public static MapFragment newInstance(boolean isForChooseLocation , int questionId , String address) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_FOR_CHOOSE_LOCATION_ARG , isForChooseLocation);
        args.putInt("question_id",questionId);
        args.putString("text_address" , address);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isForChooseLocation = getArguments().getBoolean(IS_FOR_CHOOSE_LOCATION_ARG);
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        Button current_location_btn = rootView.findViewById(R.id.current_location_btn);
        if(isForChooseLocation) current_location_btn.setText(R.string.confirm_location);
        current_location_btn.setOnClickListener(v -> {
            if(isUserSelectLocation){
                if(isForChooseLocation){
                    LatLng latLng = googleMap.getCameraPosition().target;
                    Intent returendReslut = new Intent();
                    returendReslut.putExtra("longitude" , latLng.longitude );
                    returendReslut.putExtra("latitude" , latLng.latitude);
                    returendReslut.putExtra("questionId", getArguments().getInt("question_id"));
                    getActivity().setResult(RESULT_OK , returendReslut);
                    getActivity().finish();
                }else{
                    saveChange();
                }
            }else{
                Toast.makeText(getContext(), "الرجاء قم بإختيار موقع من الخريطة اولآ", Toast.LENGTH_LONG).show();
            }

        });

        mMapView = rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        initMap();

        orderStorage = new OrderStorage(getContext());
        return rootView;
    }

    private void initMap() {
        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);


            GpsChecking();

            showLocationOnMap(khartoumMiddlePoint);

            googleMap.setOnCameraIdleListener(() -> {
                cameraMoveCount ++;
                if(cameraMoveCount > 1){
                    isUserSelectLocation = true;
                }
            });
        });
    }

    public void GpsChecking() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), locationSettingsResponse -> askLocationPermission());

        task.addOnFailureListener(getActivity(), e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(getActivity(), REQUEST_GPS_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }

    private void askLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // This is for Fragment, in case of activity call this instead ActivityCompat.requestPermissions(this
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_GRANT_PERMISSION);
            return;
        } else {
            getLastLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        enableMapLocation();

        mFusedLocationClient.getLastLocation()
            .addOnSuccessListener(getActivity(), location -> {
                if (location != null) {
                    currentLocation = location;
                    LatLng point = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    isUserSelectLocation = true;
                    showLocationOnMap(point);
                } else {
                    try {
                       showLocationOnMap(extractLocationFromDistrict());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    askForLocationUpdate();
                }
            });
    }

    @SuppressLint("MissingPermission")
    private void enableMapLocation() {
        if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @SuppressLint("MissingPermission")
    private void askForLocationUpdate() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);  // 10 seconds
        locationRequest.setFastestInterval(5000); // 5 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    try {
                        showLocationOnMap(extractLocationFromDistrict());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                    LatLng point = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    showLocationOnMap(point);
                    //break; // Maybe last one is correct!
                }

                mFusedLocationClient.removeLocationUpdates(locationCallback);
            }
        };

        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void showLocationOnMap(LatLng location) {
        googleMap.clear();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(17)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private LatLng extractLocationFromDistrict() throws IOException {
        String textAddress = getArguments().getString("text_address");
        Geocoder geocoder = new Geocoder(getContext() , Locale.getDefault());
       List<Address> addresses = geocoder.getFromLocationName(textAddress, 1);
       if(addresses.size() > 0) {
           LatLng latLng = new LatLng( addresses.get(0).getLatitude() , addresses.get(0).getLongitude());
           return latLng;
       }
     return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION_GRANT_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }
                else {
                    try {
                        showLocationOnMap(extractLocationFromDistrict());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), getString(R.string.location_permssion_needed), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GPS_SETTINGS) {
                askLocationPermission();
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            if (requestCode == REQUEST_GPS_SETTINGS) {
                Toast.makeText(getActivity(), getString(R.string.enable_gps), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

        if (mFusedLocationClient != null && locationCallback != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public boolean isValidForm() {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getStepTitle(Context context) {
        return getString(R.string.select_service);
    }

    //dont forget to clear shared prefrence after
    @Override
    public void saveChange() {
        QuotationStepperModel quotationModel = new QuotationStepperModel();
        if (quotationModel == null) {
            quotationModel = new QuotationStepperModel();
        }

        if (googleMap != null) {
            LatLng latLng = googleMap.getCameraPosition().target;
            quotationModel.setLatitude(latLng.latitude);
            quotationModel.setLongitude(latLng.longitude);
        }
        else { // in Case Map is not working for old devices
            quotationModel.setLatitude(khartoumMiddlePoint.latitude);
            quotationModel.setLongitude(khartoumMiddlePoint.longitude);
        }

        orderStorage.saveQuotation(null);
        Intent intent = new Intent(getActivity(), CategoriesActivity.class);
        startActivity(intent);
    }
}
