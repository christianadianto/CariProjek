package edu.bluejack16_2.cariprojek.Models;

/**
 * Created by 1601266375O on 7/30/2017.
 */

public class ProjectDetail {

    private String id;
    private String userEmail;
    private String projectId;
    private String status;

    public ProjectDetail(String id, String userEmail, String projectId, String status) {
        this.id = id;
        this.userEmail = userEmail;
        this.projectId = projectId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
