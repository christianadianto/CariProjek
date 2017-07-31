package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class RateReminder {

    String id;
    String raterUserId;
    String ratedUserId;

    public RateReminder(String id, String raterUserId, String ratedUserId) {
        this.id = id;
        this.raterUserId = raterUserId;
        this.ratedUserId = ratedUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaterUserId() {
        return raterUserId;
    }

    public void setRaterUserId(String raterUserId) {
        this.raterUserId = raterUserId;
    }

    public String getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(String ratedUserId) {
        this.ratedUserId = ratedUserId;
    }
}
