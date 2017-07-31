package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class Rate {

    private String id;
    private String userId;
    private float rate;

    public Rate(String id, String userId, float rate) {
        this.id = id;
        this.userId = userId;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
