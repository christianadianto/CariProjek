package edu.bluejack16_2.cariprojek;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

/**
 * Created by chris on 07/28/2017.
 */

public class ProjectController {

    private static ProjectController instance;
    private static Vector<Project> projects;
    Query query;

    private ProjectController(){
        projects = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("Projects");

        query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            String name = "";
            String category = "";
            String description = "";
            int budget = 0;
            String status ="";
            String timestamp = "";
            String id = "";
            double longitude=0;
            double latitude=0;

            if(dataSnapshot.getChildrenCount()!=0){
                projects.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Project project = createProject(data);
                    projects.add(project);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }

    private Project createProject(DataSnapshot data){
        String id = data.getKey();
        String name = getChild(data, "name");
        String category = getChild(data, "category");
        String description = getChild(data, "description");
        int budget = Integer.parseInt(getChild(data, "budget"));
        String status = getChild(data, "status");
        String timestamp = getChild(data, "timestamp");
        double latitude = Double.parseDouble(getChild(data, "latitude"));
        double longitude = Double.parseDouble(getChild(data, "longitude"));

        return new Project(name, category, description, budget, status, timestamp, id, latitude, longitude);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("project").child(key).getValue().toString();
    }

    public static Vector<Project> getProjects(){

        return projects;
    }

    public static ProjectController getInstance(){
        if(instance == null){
            instance = new ProjectController();
        }
        return instance;
    }


}
