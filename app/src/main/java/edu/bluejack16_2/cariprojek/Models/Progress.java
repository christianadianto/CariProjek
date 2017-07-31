package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class Progress {

    private String id;
    private String projectId;
    private String progress;
    private String time;

    public Progress(String id, String projectId, String progress, String time) {
        this.id = id;
        this.projectId = projectId;
        this.progress = progress;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
