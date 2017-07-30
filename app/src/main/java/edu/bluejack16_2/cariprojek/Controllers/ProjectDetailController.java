package edu.bluejack16_2.cariprojek.Controllers;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.ProjectDetail;
import edu.bluejack16_2.cariprojek.ProjectDetailActivity;

/**
 * Created by 1601266375O on 7/30/2017.
 */

public class ProjectDetailController {

    public static final String CHOOSEN = "Choosen";

    private static ProjectDetailController instance;
    private static Vector<ProjectDetail> projectDetails;
    private Query query;


    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private ProjectDetailController(){
        projectDetails = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("ProjectDetails");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0){
                    projectDetails.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        ProjectDetail projectDetail = createProjectDetail(data);
                        projectDetails.add(projectDetail);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static ProjectDetailController getInstance(){
        if(instance == null)
            instance = new ProjectDetailController();

        return instance;
    }

    private ProjectDetail createProjectDetail(DataSnapshot data){
        String id = data.getKey();
        String userEmail = getChild(data, "userEmail");
        String projectId = getChild(data, "projectId");
        String status = getChild(data, "status");

        return new ProjectDetail(id, userEmail, projectId, status);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("projectDetail").child(key).getValue().toString();
    }

    public static Vector<ProjectDetail> getProjectDetails(){
        return projectDetails;
    }

    public static void insertProjectDetail(ProjectDetail projectDetail){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ProjectDetails");

        DatabaseReference new_projectDetail = myRef.push();
        projectDetail.setId(new_projectDetail.getKey());
        new_projectDetail.child("projectDetail").setValue(projectDetail);
    }

    public static void updateProjectDetail(String projectId, String userEmail){

        ProjectDetail pd = new ProjectDetail("", "", "", "");

        for (ProjectDetail projectDetail:projectDetails) {
            if(projectDetail.getProjectId().equals(projectId) && projectDetail.getUserEmail().equals(userEmail))
                pd = projectDetail;
        }

        pd.setStatus(ProjectDetailController.CHOOSEN);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ProjectDetails").child(pd.getId()).child("projectDetail");
        myRef.setValue(pd);

    }

    public static boolean isUserCurrentProject(String projectId, String userEmail){

        for (ProjectDetail projectDetail:projectDetails) {
            if(projectDetail.getProjectId().equals(projectId)
                    && projectDetail.getUserEmail().equals(userEmail)
                    && projectDetail.getStatus().equals(ProjectDetailController.CHOOSEN))
                return true;
        }

        return false;
    }
}
