package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class Chat {

    private String id;
    private String name;
    private String time;
    private String message;

    public Chat(String id, String name, String time, String message) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
