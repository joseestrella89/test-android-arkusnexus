package com.jose.figo.utils;

public class CoordinatesMath {

    private static final double RADIUS_EARTH = 6371e3;

    //Method to get the distance between two points
    public double getDistancePoints(double origin_lat, double origin_lon, double lat, double lon){
        double lat_my_rad = convertToRadians(lat);
        double lat_origin_rad = convertToRadians(origin_lat);
        double diff_lats = convertToRadians(origin_lat - lat);
        double diff_longs = convertToRadians(origin_lon - lon);

        double a = Math.sin(diff_lats/2) * Math.sin(diff_lats/2) +
                Math.cos(lat_my_rad) * Math.cos(lat_origin_rad) *
                        Math.sin(diff_longs/2) * Math.sin(diff_longs/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return RADIUS_EARTH * c;
    }
    private double convertToRadians(double value){
        return value * Math.PI / 180;
    }
}
