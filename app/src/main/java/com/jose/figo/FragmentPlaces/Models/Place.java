package com.jose.figo.FragmentPlaces.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Place implements Serializable{

    @SerializedName("PlaceId")
    private String id;
    @SerializedName("PlaceName")
    private String name;
    @SerializedName("Address")
    private String address;
    @SerializedName("Category")
    private String category;
    @SerializedName("IsOpenNow")
    private String isOpen;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Thumbnail")
    private String thumbnail;
    @SerializedName("Rating")
    private double rating;
    @SerializedName("IsPetFriendly")
    private boolean isPetFriendly;
    @SerializedName("AddressLine1")
    private String addressLineOne;
    @SerializedName("AddressLine2")
    private String addressLineTwo;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("Site")
    private String site;

    private double distance;

    public Place(String id, String name, String address, String category, String isOpen, double lat, double lon,
                 String thumbnail, double rating, boolean isPetFriendly, String addressLineOne, String addressLineTwo,
                 String phoneNumber, String site){
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.isOpen = isOpen;
        this.latitude = lat;
        this.longitude = lon;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.isPetFriendly = isPetFriendly;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.phoneNumber = phoneNumber;
        this.site = site;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isPetFriendly() {
        return isPetFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        isPetFriendly = petFriendly;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
