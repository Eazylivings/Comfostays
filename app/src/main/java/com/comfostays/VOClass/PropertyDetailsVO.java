package com.comfostays.VOClass;

import java.io.Serializable;
import java.util.ArrayList;

public class PropertyDetailsVO implements Serializable{

    private int propertyId;
    private String propertyName;
    private String addressLineOne;
    private String addressLineTwo;
    private int postalCode;
    private String state;
    private String geoLocation;
    private String propertyType;
    private boolean isMealOffered;
    private boolean isMealScheduleAvailable;
    private String[] listOfFacilities;
    private String[] typeOfRooms;
    private String facilitiesProvided;
    private String roomTypes;
    private String furnishedFlatItems;

    public String getFurnishedFlatItems() {
        return furnishedFlatItems;
    }

    public void setFurnishedFlatItems(String furnishedFlatItems) {
        this.furnishedFlatItems = furnishedFlatItems;
    }



    public String getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(String roomTypes) {
        this.roomTypes = roomTypes;
    }

    public String getFacilitiesProvided() {
        return facilitiesProvided;
    }

    public void setFacilitiesProvided(String facilitiesProvided) {
        this.facilitiesProvided = facilitiesProvided;
    }



    public String[] getTypeOfRooms() {
        return typeOfRooms;
    }

    public void setTypeOfRooms(String[] typeOfRooms) {
        this.typeOfRooms = typeOfRooms;
    }

    public boolean isMealScheduleAvailable() {
        return isMealScheduleAvailable;
    }

    public void setMealScheduleAvailable(boolean mealScheduleAvailable) {
        isMealScheduleAvailable = mealScheduleAvailable;
    }

    public String[] getListOfFacilities() {
        return listOfFacilities;
    }

    public void setListOfFacilities(String[] listOfFacilities) {
        this.listOfFacilities = listOfFacilities;
    }

    public boolean isMealOffered() {
        return isMealOffered;
    }

    public void setMealOffered(boolean mealOffered) {
        isMealOffered = mealOffered;
    }



    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }


}
