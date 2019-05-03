package com.jose.figo.FragmentPlaces.Views;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jose.figo.FragmentPlaces.Models.Place;
import com.jose.figo.R;

import java.util.Locale;

public class PlaceFragment extends Fragment implements OnMapReadyCallback{

    private static final String TAG = PlaceFragment.class.getSimpleName();
    private View viewPlace;
    private Place place;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        place = (Place) bundle.getSerializable("place");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewPlace = inflater.inflate(R.layout.place_fragment, container, false);
        TextView titlePlace = (TextView) viewPlace.findViewById(R.id.title_place);
        TextView addressPlaceOne = (TextView) viewPlace.findViewById(R.id.address_place_one);
        TextView addressPlaceTwo = (TextView) viewPlace.findViewById(R.id.address_place_two);
        RatingBar ratingPlace = (RatingBar) viewPlace.findViewById(R.id.rating_place);
        TextView direction = (TextView) viewPlace.findViewById(R.id.description_time);
        TextView call = (TextView) viewPlace.findViewById(R.id.description_call);
        TextView website = (TextView) viewPlace.findViewById(R.id.description_visit);
        TextView distance = (TextView) viewPlace.findViewById(R.id.distance);
        titlePlace.setText(place.getName());
        addressPlaceOne.setText(place.getAddressLineOne());
        addressPlaceTwo.setText(place.getAddressLineTwo());
        ratingPlace.setRating((float)place.getRating());
        direction.setText("15 min drive");
        float dis = (float) place.getDistance();
        String diString = String.format(Locale.ENGLISH,"%.1f" , dis);
        Resources res = getResources();
        distance.setText(res.getString(R.string.distance_place,diString));
        call.setText(place.getPhoneNumber());
        website.setText(place.getSite());
        LinearLayout directionLayot = (LinearLayout) viewPlace.findViewById(R.id.layout_route);
        LinearLayout callLayout = (LinearLayout) viewPlace.findViewById(R.id.layout_phone);
        LinearLayout websiteLayout = (LinearLayout) viewPlace.findViewById(R.id.layout_website);
        directionLayot.setOnClickListener(btnListenerDirection);
        callLayout.setOnClickListener(btnListenerCall);
        websiteLayout.setOnClickListener(btnListenerWebsite);
        return viewPlace;
    }
    @Override
    public void onStart(){
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng placePoint = new LatLng(place.getLatitude(), place.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(placePoint).title(place.getName()));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(placePoint);
        LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,20));
    }
    private View.OnClickListener btnListenerDirection = new View.OnClickListener() {
        public void onClick(View v) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+place.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }
    };
    private View.OnClickListener btnListenerCall = new View.OnClickListener() {
        public void onClick(View v) {
            String uri = "tel:" + place.getPhoneNumber().trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }
    };
    private View.OnClickListener btnListenerWebsite = new View.OnClickListener() {
        public void onClick(View v) {
            String url = place.getSite().trim();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    };
}
