package com.jose.figo.network;

import com.jose.figo.FragmentPlaces.Models.Place;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("5bf3ce193100008900619966")
    Call<ArrayList<Place>> getPlaces();
}
