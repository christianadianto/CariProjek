package edu.bluejack16_2.cariprojek.Controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Progress;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class ProgressController {

    private static ProgressController instance;
    private static Vector<Progress> progresses;
    Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private ProgressController(){
        progresses = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("Progresses");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0){
                    progresses.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        Progress progress = createRate(data);
                        progresses.add(progress);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static ProgressController getInstance(){
        if(instance == null)
            instance = new ProgressController();

        return instance;
    }

    private Progress createRate(DataSnapshot data){
        String id = data.getKey();
        String projectId = getChild(data, "projectId");
        String progress = getChild(data, "progress");
        String time = getChild(data, "time");

        return new Progress(id, projectId, progress, time);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("progress").child(key).getValue().toString();
    }

    public static Vector<Progress> getProgresses(){
        return progresses;
    }

    public static Vector<Progress> getProgressesByProjectId(String projectId){
        Vector<Progress> filtered_progress = new Vector<>();

        for (Progress progress:progresses){
            if(progress.getProjectId().equals(projectId))
                filtered_progress.add(progress);
        }

        return filtered_progress;
    }

    public static void insertProgress(Progress progress){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Progresses");

        DatabaseReference new_progress = myRef.push();
        progress.setId(new_progress.getKey());
        new_progress.child("progress").setValue(progress);

    }

}
