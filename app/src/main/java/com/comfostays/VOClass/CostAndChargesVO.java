package com.comfostays.VOClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CostAndChargesVO implements Serializable{

    private LinkedHashMap<String,String> otherChargesMap;
    private ArrayList<String> listOfRoomTypesAndChargesWithDuration;
    private int propertyId;


    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public ArrayList<String> getListOfRoomTypesAndChargesWithDuration() {
        return listOfRoomTypesAndChargesWithDuration;
    }

    public void setList(ArrayList<String> list) {
        this.listOfRoomTypesAndChargesWithDuration = list;
    }

    public LinkedHashMap<String, String> getOtherChargesMap() {
        return otherChargesMap;
    }

    public void setOtherChargesMap(LinkedHashMap<String,String> otherChargesMap) {
        this.otherChargesMap = otherChargesMap;
    }
}
