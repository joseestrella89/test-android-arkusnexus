package com.jose.figo.FragmentPlaces.Presenters;

import android.location.Location;

import com.jose.figo.FragmentPlaces.Models.Place;
import com.jose.figo.FragmentPlaces.Views.ListPlacesView;
import com.jose.figo.network.ApiPlaces;
import com.jose.figo.network.ApiPlacesImpl;

import java.util.ArrayList;

public class ListPlacesPresenterImpl implements ListPlacesPresenter {

    private ListPlacesView listPlacesView;
    private ApiPlaces apiPlacesImpl;
    private Location location;
    private static final String TAG = ListPlacesPresenterImpl.class.getSimpleName();

    public ListPlacesPresenterImpl(ListPlacesView listPlacesView, Location location){
        this.listPlacesView = listPlacesView;
        this.apiPlacesImpl = new ApiPlacesImpl(this);
        this.location = location;
    }
    @Override
    public void getPlaces() {
        apiPlacesImpl.getPlaces();
        /*Place placeU = new Place("asaasd", "IHOP", "address full", "un category", "open now", 37.348115,-121.8990835,
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=2400&photoreference=CmRaAAAAe8OYJqvYpTKJR3EkuxBukX1ox0gm9-8NNO19OvbCRtiSvKHthC_3_8KAsq6-ARBRR1T-zzigB8k8THFVAQsYTJD3ibe_LE_Cwi4whBVkKBhO6R-HQIpKpFYVcDAq3rEoEhANPd06Rb2KwWN3HGJ0rVBhGhRLWqZY9nHwHf7tCRSMjiYtmeD1Lw&key=AIzaSyBKYncKJA-Uu060807q_t3g1Y6o6Y9fyaI",4.2,false,"address line 1", "address line 2", "(646) 326 2312","http://www.arkusnexus.com");
        Place placeTwo = new Place("asaasd", "IHOP 2", "address full 2", "un category", "open now", 37.348115,-121.8990835,
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=3232&photoreference=CmRaAAAAHILKwHTDoE2QLkaYVJFc2w7ssAZH0UJACcwQ7ciumK_g7QFgIMKztHHqIg1eq_ZyVVIcRd4ebm337mVOmwg2tLKbl4FvRw122L2GxXgpFU5HA3SRN1sqyMgpvxR0-Hi_EhBvIGgczSbNkbkmXfTcDaAXGhRMhIPgyrCYY7g_EPaWVh0SOGXrFA&key=AIzaSyBKYncKJA-Uu060807q_t3g1Y6o6Y9fyaI",4.2,false,"address line 1 2", "address line 2 2", "(646) 326 2312","http://www.arkusnexus.com");
        Place placeThree = new Place("asaasd", "IHOP 3", "address full 3", "un category", "open now", 37.348115,-121.8990835,
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=4032&photoreference=CmRaAAAAbOwULLXhbsDVAKP1qOkU-trEyuFstC2RY1WGR8OW_LSSrrci3JID8RfK8PrKrAMTgmbugTMWz5TBiaTfT2kEmXkpMFvf6jvZCZ1kxwUggCfKUDjzG9p6tTTFpp-q7X8NEhC-Uwx0aTPSM0p13hl5kRh4GhR9JcRivrFR3B1SCs_xq7Jc6g-iqQ&key=AIzaSyBKYncKJA-Uu060807q_t3g1Y6o6Y9fyaI",4.2,false,"address line 1 3", "address line 2 3", "(646) 326 2312","http://www.arkusnexus.com");
        ArrayList<Place> places = new ArrayList<>();
        places.add(placeU);
        places.add(placeTwo);
        places.add(placeThree);
        listPlacesView.onPlacesReceived(places);*/
    }

    @Override
    public void onReceivePlaces(ArrayList<Place> places) {
        if(this.listPlacesView!=null){
            if(location!=null){
                sortPlaces(places);
            }else{
                this.listPlacesView.onPlacesReceived(places);
            }

        }
    }
    @Override
    public void onDestroy() {
        this.listPlacesView = null;
        apiPlacesImpl.onDestroy();
    }
    //Method to calculate the distance between the user location and places coordinates
    //Also ordering the list according the nearby places
    private void sortPlaces(ArrayList<Place> places){
        Location auxLoc = new Location("locationAux");
        Place p;
        ArrayList<Place> placesDistance = new ArrayList<Place>();
        int sizeList = places.size();
        for(int i=0 ; i < sizeList ; i++){
            p = places.get(i);
            auxLoc.setLatitude(p.getLatitude());
            auxLoc.setLongitude(p.getLongitude());
            double distance = auxLoc.distanceTo(location);
            p.setDistance(distance);
            placesDistance.add(p);
        }
        for(int i = 0; i < sizeList - 1; i++){
            for(int j = 0; j < sizeList - 1; j++){
                if (placesDistance.get(j).getDistance() > placesDistance.get(j+1).getDistance()){
                    Place tmp = placesDistance.get(j+1);
                    placesDistance.set(j+1, placesDistance.get(j));
                    placesDistance.set(j, tmp) ;
                }
            }
        }
        if(this.listPlacesView!=null){
            this.listPlacesView.onPlacesReceived(placesDistance);
        }

    }
}
