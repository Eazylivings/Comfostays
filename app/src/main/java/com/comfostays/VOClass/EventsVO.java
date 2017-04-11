package com.comfostays.VOClass;


import java.io.Serializable;

public class EventsVO implements Serializable{

    private String eventName;
    private String eventDate;
    private String eventOrganiser;
    private String eventLocation;
    private String eventPerHeadExpenditure;
    private String eventType;
    private String eventDescription;
    private String eventStatus;
    private double organizerId;

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }



    public double getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(double organizerId) {
        this.organizerId = organizerId;
    }



    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventOrganiser() {
        return eventOrganiser;
    }

    public void setEventOrganiser(String eventOrganiser) {
        this.eventOrganiser = eventOrganiser;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventPerHeadExpenditure() {
        return eventPerHeadExpenditure;
    }

    public void setEventPerHeadExpenditure(String eventPerHeadExpenditure) {
        this.eventPerHeadExpenditure = eventPerHeadExpenditure;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }


}
