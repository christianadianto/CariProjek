package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by chris on 07/30/2017.
 */

public class Portofolio {

    private String owner;
    private String name;
    private String category;
    private String description;
    private String url;
    private String timestamp;
    private String id;

    public Portofolio(String owner, String name, String category, String description, String url, String timestamp, String id) {
        this.owner = owner;
        this.name = name;
        this.category = category;
        this.description = description;
        this.url = url;
        this.timestamp = timestamp;
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
