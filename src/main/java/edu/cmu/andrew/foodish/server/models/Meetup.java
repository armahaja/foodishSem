package edu.cmu.andrew.foodish.server.models;

public class Meetup {

    private int idMeetup;
    private int idDish;
    private int idChefUser;
    private String Location;
    private String Date;
    private String StartTime;
    private int Feedback_FoodQuality = -1;
    private int Feedback_FoodQuantity = -1;
    private int Feedback_FoodTaste = -1;
    private int TotalFeedbackReceived = -1;
    private int MeetupRating = -1;
    private int MaxGuestsAllowed;

    public Meetup(int idMeetup, int idDish, int idChefUser, String location, String date, String startTime, int maxGuestsAllowed) {
        this.idMeetup = idMeetup;
        this.idDish = idDish;
        this.idChefUser = idChefUser;
        Location = location;
        Date = date;
        StartTime = startTime;
        MaxGuestsAllowed = maxGuestsAllowed;
    }

    public int getIdMeetup() {
        return idMeetup;
    }

    public int getIdDish() {
        return idDish;
    }

    public int getIdChefUser() {
        return idChefUser;
    }

    public String getLocation() {
        return Location;
    }

    public String getDate() {
        return Date;
    }

    public String getStartTime() {
        return StartTime;
    }

    public int getFeedback_FoodQuality() {
        return Feedback_FoodQuality;
    }

    public int getFeedback_FoodQuantity() {
        return Feedback_FoodQuantity;
    }

    public int getFeedback_FoodTaste() {
        return Feedback_FoodTaste;
    }

    public int getTotalFeedbackReceived() {
        return TotalFeedbackReceived;
    }

    public int getMeetupRating() {
        return MeetupRating;
    }

    public int getMaxGuestsAllowed() {
        return MaxGuestsAllowed;
    }

    public void setIdMeetup(int idMeetup) {
        this.idMeetup = idMeetup;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public void setIdChefUser(int idChefUser) {
        this.idChefUser = idChefUser;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public void setFeedback_FoodQuality(int feedback_FoodQuality) {
        Feedback_FoodQuality = feedback_FoodQuality;
    }

    public void setFeedback_FoodQuantity(int feedback_FoodQuantity) {
        Feedback_FoodQuantity = feedback_FoodQuantity;
    }

    public void setFeedback_FoodTaste(int feedback_FoodTaste) {
        Feedback_FoodTaste = feedback_FoodTaste;
    }

    public void setTotalFeedbackReceived(int totalFeedbackReceived) {
        TotalFeedbackReceived = totalFeedbackReceived;
    }

    public void setMeetupRating(int meetupRating) {
        MeetupRating = meetupRating;
    }

    public void setMaxGuestsAllowed(int maxGuestsAllowed) {
        MaxGuestsAllowed = maxGuestsAllowed;
    }

}
