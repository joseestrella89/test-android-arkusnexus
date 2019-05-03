package com.jose.figo.FragmentPlaces.Views;

import com.jose.figo.FragmentPlaces.Models.Place;

import java.util.ArrayList;

public interface ListPlacesView {
    void onPlacesReceived(ArrayList<Place> places);
}
