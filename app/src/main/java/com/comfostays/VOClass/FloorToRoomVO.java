package com.comfostays.VOClass;


import java.io.Serializable;
import java.util.LinkedHashMap;

public class FloorToRoomVO implements Serializable{


    private LinkedHashMap<String,LinkedHashMap<String,String>> mapOfFloorToRoomNoAndRoomType;
    private LinkedHashMap<String,Integer> floorToNumberOfRoomsMap;
    private LinkedHashMap<String,Integer> roomToOccupancyMap;

    public LinkedHashMap<String, Integer> getRoomToOccupancyMap() {
        return roomToOccupancyMap;
    }

    public void setRoomToOccupancyMap(LinkedHashMap<String, Integer> roomToOccupancyMap) {
        this.roomToOccupancyMap = roomToOccupancyMap;
    }



    public LinkedHashMap<String, LinkedHashMap<String, String>> getMapOfFloorToRoomNoAndRoomType() {
        return mapOfFloorToRoomNoAndRoomType;
    }

    public void setMapOfFloorToRoomNoAndRoomType(LinkedHashMap<String, LinkedHashMap<String, String>> mapOfFloorToRoomNoAndRoomType) {
        this.mapOfFloorToRoomNoAndRoomType = mapOfFloorToRoomNoAndRoomType;
    }

    public LinkedHashMap<String, Integer> getFloorToNumberOfRoomsMap() {
        return floorToNumberOfRoomsMap;
    }

    public void setFloorToNumberOfRoomsMap(LinkedHashMap<String, Integer> floorToNumberOfRoomsMap) {
        this.floorToNumberOfRoomsMap = floorToNumberOfRoomsMap;
    }
}
