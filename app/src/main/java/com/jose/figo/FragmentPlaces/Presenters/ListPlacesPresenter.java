package com.jose.figo.FragmentPlaces.Presenters;

import com.jose.figo.FragmentPlaces.Models.Place;

import java.util.ArrayList;

public interface ListPlacesPresenter {
    void getPlaces();
    void onReceivePlaces(ArrayList<Place> places);
    void onDestroy();
}
