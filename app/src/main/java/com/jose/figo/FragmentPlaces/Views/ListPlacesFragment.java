package com.jose.figo.FragmentPlaces.Views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jose.figo.FragmentPlaces.Models.Place;
import com.jose.figo.FragmentPlaces.Presenters.ListPlacesPresenter;
import com.jose.figo.FragmentPlaces.Presenters.ListPlacesPresenterImpl;
import com.jose.figo.FragmentPlaces.adapter.PlacesAdapter;
import com.jose.figo.R;
import com.jose.figo.utils.GPSHelper;

import java.util.ArrayList;

public class ListPlacesFragment extends Fragment implements ListPlacesView, LocationListener {

    private static final String TAG = ListPlacesFragment.class.getSimpleName();
    private static final int CODE_REQUEST_LOCATION = 2001;
    private RecyclerView recyclerView;
    private PlacesAdapter adapter;
    private ListPlacesPresenter listPlacesPresenter;
    private Place placeSelected;
    LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewPlaces = inflater.inflate(R.layout.list_places_fragment, container, false);
        recyclerView = (RecyclerView) viewPlaces.findViewById(R.id.list_places);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable("place") != null) {
                placeSelected = (Place) savedInstanceState.getSerializable("place");
                goToFragment(placeSelected);
            }
        }
        return viewPlaces;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(placeSelected == null){
            GPSHelper gpsHelper = new GPSHelper();
            boolean waitForPermission = gpsHelper.isRequestPermission(getActivity(), CODE_REQUEST_LOCATION);
            if (!waitForPermission) {
                requesLocation();
            }
        }

    }

    @Override
    public void onPlacesReceived(final ArrayList<Place> places) {
        if(getActivity() == null) return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setHasFixedSize(true);
                adapter = new PlacesAdapter(places, new PlacesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Place item) {
                        placeSelected = item;
                        goToFragment(item);
                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            }
        });
    }

    public void goToFragment(Place place) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        Bundle arguments = new Bundle();
        arguments.putSerializable("place", place);
        Fragment placeFragment = new PlaceFragment();
        placeFragment.setArguments(arguments);
        transaction.replace(R.id.fragment, placeFragment);
        transaction.addToBackStack("PlaceFragment");
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODE_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requesLocation();
                } else {
                    Log.d(TAG,"Permission not granted");
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),R.string.not_permission_gps,Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
        }
    }

    public void requesLocation(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(placeSelected != null){
            outState.putSerializable("place", placeSelected);
        }
    }

    @Override
    public void onPause() {
        if(this.listPlacesPresenter!=null){
            this.listPlacesPresenter.onDestroy();
        }
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        locationManager = null;
        listPlacesPresenter = new ListPlacesPresenterImpl(this, location);
        listPlacesPresenter.getPlaces();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
