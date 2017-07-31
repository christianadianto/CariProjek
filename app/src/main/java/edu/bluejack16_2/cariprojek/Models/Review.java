package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class Review {

    private String id;
    private String userToEmail;
    private String userFromEmail;
    private String reviewMessage;

    public Review(String id, String userToEmail, String userFromEmail, String reviewMessage) {
        this.id = id;
        this.userToEmail = userToEmail;
        this.userFromEmail = userFromEmail;
        this.reviewMessage = reviewMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserToEmail() {
        return userToEmail;
    }

    public void setUserToEmail(String userToEmail) {
        this.userToEmail = userToEmail;
    }

    public String getUserFromEmail() {
        return userFromEmail;
    }

    public void setUserFromEmail(String userFromEmail) {
        this.userFromEmail = userFromEmail;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}
