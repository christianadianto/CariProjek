package edu.bluejack16_2.cariprojek;

/**
 * Created by chris on 07/11/2017.
 */

public class Project {

    private String name;
    private String category;
    private String description;
    private String status;
    private String timestamp;
    private int budget;
    private String key;

    public Project(String name, String category, String description, int budget) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.budget = budget;
        this.status = "Open";
        Long timestampLong = System.currentTimeMillis()/1000;
        this.timestamp = timestampLong.toString();
    }

    public Project(String name, String category, String description, int budget, String status, String created, String key){
        this.name = name;
        this.category = category;
        this.description = description;
        this.budget = budget;
        this.status = status;
        this.timestamp = created;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
