package edu.cmu.andrew.foodish.server.models;

public class MeetupAttendee {

    private int idBuddyUser;
    private int idMeetup;
    private int MissingBuddy = -1;
    private int BuddyRating = -1;
    private String SuggestionToBuddy;

    public MeetupAttendee(int idBuddyUser, int idMeetup) {
        this.idBuddyUser = idBuddyUser;
        this.idMeetup = idMeetup;
    }

    public MeetupAttendee(int idBuddyUser, int idMeetup, int missingBuddy, int buddyRating, String suggestionToBuddy) {
        this.idBuddyUser = idBuddyUser;
        this.idMeetup = idMeetup;
        MissingBuddy = missingBuddy;
        BuddyRating = buddyRating;
        SuggestionToBuddy = suggestionToBuddy;
    }

    public int getIdBuddyUser() {
        return idBuddyUser;
    }

    public int getIdMeetup() {
        return idMeetup;
    }

    public int getMissingBuddy() {
        return MissingBuddy;
    }

    public int getBuddyRating() {
        return BuddyRating;
    }

    public String getSuggestionToBuddy() {
        return SuggestionToBuddy;
    }

    public void setIdBuddyUser(int idBuddyUser) {
        this.idBuddyUser = idBuddyUser;
    }

    public void setIdMeetup(int idMeetup) {
        this.idMeetup = idMeetup;
    }

    public void setMissingBuddy(int missingBuddy) {
        MissingBuddy = missingBuddy;
    }

    public void setBuddyRating(int buddyRating) {
        BuddyRating = buddyRating;
    }

    public void setSuggestionToBuddy(String suggestionToBuddy) {
        SuggestionToBuddy = suggestionToBuddy;
    }
}
