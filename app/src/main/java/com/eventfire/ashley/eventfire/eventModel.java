package com.eventfire.ashley.eventfire;

import android.util.EventLogTags;

import java.io.Serializable;

/**
 * Created by gf on 28-03-2017.
 */

public class eventModel implements Serializable{

    public String eventName;
    public String description;
    public String date;
    public String phoneNumber;
    public String contactPerson;
    public String eventPhotoUrl;
    public String duration;


    public String collegeName;


    public eventModel(String eventName, String description, String date,
                      String phoneNumber, String contactPerson, String eventPhotoUrl, String duration,String collegeName) {
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.contactPerson = contactPerson;
        this.eventPhotoUrl = eventPhotoUrl;
        this.duration = duration;
        this.collegeName=collegeName;
    }

    public eventModel(){

    }




    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }



    public String getEventPhotoUrl() {
        return eventPhotoUrl;
    }

    public void setEventPhotoUrl(String eventPhotoUrl) {
        this.eventPhotoUrl = eventPhotoUrl;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
