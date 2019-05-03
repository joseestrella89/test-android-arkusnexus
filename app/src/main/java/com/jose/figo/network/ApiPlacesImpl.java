package com.jose.figo.network;

import android.util.Log;

import com.jose.figo.FragmentPlaces.Models.Place;
import com.jose.figo.FragmentPlaces.Models.PlaceList;
import com.jose.figo.FragmentPlaces.Presenters.ListPlacesPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiPlacesImpl implements ApiPlaces {

    ListPlacesPresenter listPlacesPresenter;

    public ApiPlacesImpl(ListPlacesPresenter listPlacesPresenter){
        this.listPlacesPresenter = listPlacesPresenter;
    }
    @Override
    public void getPlaces() {
        Retrofit client = ApiClient.getClient();
        ApiInterface request = client.create(ApiInterface.class);
        Call<ArrayList<Place>> call = request.getPlaces();
        call.enqueue(new Callback<ArrayList<Place>>() {
            @Override
            public void onResponse(Call<ArrayList<Place>> call, Response<ArrayList<Place>> response) {
                Log.d("ApiPlacesImpl", "places: "+response.code());
                if(listPlacesPresenter!=null){
                    listPlacesPresenter.onReceivePlaces(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Place>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        listPlacesPresenter = null;
    }
}
