package edu.bluejack16_2.cariprojek.Models;

import java.util.Vector;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class ChatRoom {

    private String id;
    private String projectId;
    private Vector<Chat> chats;

    public ChatRoom(String id, String projectId, Vector<Chat> chats) {
        this.id = id;
        this.projectId = projectId;
        this.chats = chats;
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

    public Vector<Chat> getChats() {
        return chats;
    }

    public void setChats(Vector<Chat> chats) {
        this.chats = chats;
    }
}
