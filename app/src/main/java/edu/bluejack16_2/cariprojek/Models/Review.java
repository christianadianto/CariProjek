package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class Review {

    private String id;
    private String userFromName;
    private String reviewMessage;

    public Review(String id, String userFromName, String reviewMessage) {
        this.id = id;
        this.userFromName = userFromName;
        this.reviewMessage = reviewMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserFromName() {
        return userFromName;
    }

    public void setUserFromName(String userFromName) {
        this.userFromName = userFromName;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}
