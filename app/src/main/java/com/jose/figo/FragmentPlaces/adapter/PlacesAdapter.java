package com.jose.figo.FragmentPlaces.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jose.figo.FragmentPlaces.Models.Place;
import com.jose.figo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceHolder>{

    public interface OnItemClickListener {
        void onItemClick(Place item);
    }

    private ArrayList<Place> places;
    private OnItemClickListener listener;

    public PlacesAdapter(ArrayList<Place> places, OnItemClickListener listener){
        this.places = places;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new PlaceHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place_fragment, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder placeHolder, int i) {
        placeHolder.bindPlaceHolder(places.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView titlePlace;
        private TextView addressPlace;
        private TextView distancePlace;
        private ImageView thumbnailPlace;
        private RatingBar ratingPlace;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);
            titlePlace = (TextView) itemView.findViewById(R.id.title_place);
            addressPlace = (TextView) itemView.findViewById(R.id.address_place);
            distancePlace = (TextView) itemView.findViewById(R.id.distance_place);
            thumbnailPlace = (ImageView) itemView.findViewById(R.id.img_place);
            ratingPlace = (RatingBar) itemView.findViewById(R.id.rating_place);
        }

        public void bindPlaceHolder(final Place place, final OnItemClickListener listener){
            titlePlace.setText(place.getName());
            addressPlace.setText(place.getAddress());
            float distance = (float) place.getDistance();
            String dis = String.format(Locale.ENGLISH,"%.1f" , distance);
            Resources res = itemView.getResources();
            distancePlace.setText(res.getString(R.string.distance_place,dis));
            Picasso.with(itemView.getContext()).load(place.getThumbnail())
                    .centerCrop().resize(80,80)
                    .transform(new RoundedCornersTransformation(5,0))
                    .into(thumbnailPlace);
            ratingPlace.setRating((float)place.getRating());
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.onItemClick(place);
                }
            });
        }
    }
}
