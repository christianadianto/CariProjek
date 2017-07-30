package edu.bluejack16_2.cariprojek.Controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Project;

/**
 * Created by chris on 07/28/2017.
 */

public class ProjectController {

    private static ProjectController instance;
    private static Vector<Project> projects;
    Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private ProjectController(){
        projects = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("Projects");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

    public static ProjectController getInstance(){
        if(instance == null)
            instance = new ProjectController();

        return instance;
    }

    private Project createProject(DataSnapshot data){
        String id = data.getKey();
        String owner = getChild(data, "owner");
        String name = getChild(data, "name");
        String category = getChild(data, "category");
        String description = getChild(data, "description");
        int budget = Integer.parseInt(getChild(data, "budget"));
        String status = getChild(data, "status");
        String timestamp = getChild(data, "timestamp");
        double latitude = Double.parseDouble(getChild(data, "latitude"));
        double longitude = Double.parseDouble(getChild(data, "longitude"));

        return new Project(id, owner, name, category, description, budget, status, timestamp, latitude, longitude);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("project").child(key).getValue().toString();
    }

    public static Vector<Project> getProjects(){
        return projects;
    }

    public static Vector<Project> getProjectByCategory(String category){

        Vector<Project> filtered_projects = new Vector<>();

        for (Project project:projects) {
            if(project.getStatus().equals("Closed")){
                continue;
            }
            if(category.equals("All")){
                filtered_projects.add(project);
            }
            else if(project.getCategory().equals(category)){
                filtered_projects.add(project);
            }
        }

        return filtered_projects;
    }

    public static Vector<Project> getProjectByEmail(String email){

        Vector<Project> filtered_projects = new Vector<>();

        for (Project project:projects) {
            if(project.getOwner().equals(email)){
                filtered_projects.add(project);
               }
        }

        return filtered_projects;
    }

    public static void insertProject(Project project){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Projects");

        DatabaseReference new_user = myRef.push();
        project.setId(new_user.getKey());
        new_user.child("project").setValue(project);

    }

    public static void updateProject(Project project){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Projects").child(project.getId()).child("project");
        myRef.setValue(project);
    }

    public static void deleteProject(Project project){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Projects").child(project.getId());
        myRef.removeValue();
    }


}
