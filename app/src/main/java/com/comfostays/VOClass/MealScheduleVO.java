package com.comfostays.VOClass;

import java.io.Serializable;
import java.util.List;

public class MealScheduleVO implements Serializable{

    private int propertyId;

    private String breakfastFromTime;
    private String breakfastToTime;
    private String lunchFromTime;
    private String lunchToTime;
    private String dinnerFromTime;
    private String dinnerToTime;

    private List<String> listOfMeals;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getDinnerToTime() {
        return dinnerToTime;
    }

    public void setDinnerToTime(String dinnerToTime) {
        this.dinnerToTime = dinnerToTime;
    }

    public String getDinnerFromTime() {
        return dinnerFromTime;
    }

    public void setDinnerFromTime(String dinnerFromTime) {
        this.dinnerFromTime = dinnerFromTime;
    }

    public String getLunchToTime() {
        return lunchToTime;
    }

    public void setLunchToTime(String lunchToTime) {
        this.lunchToTime = lunchToTime;
    }

    public String getLunchFromTime() {
        return lunchFromTime;
    }

    public void setLunchFromTime(String lunchFromTime) {
        this.lunchFromTime = lunchFromTime;
    }

    public String getBreakfastToTime() {
        return breakfastToTime;
    }

    public void setBreakfastToTime(String breakfastToTime) {
        this.breakfastToTime = breakfastToTime;
    }

    public String getBreakfastFromTime() {
        return breakfastFromTime;
    }

    public void setBreakfastFromTime(String breakfastFromTime) {
        this.breakfastFromTime = breakfastFromTime;
    }



    public List<String> getListOfMeals() {
        return listOfMeals;
    }

    public void setListOfMeals(List<String> listOfMeals) {
        this.listOfMeals = listOfMeals;
    }
}
