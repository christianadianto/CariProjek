package edu.bluejack16_2.cariprojek.Controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Review;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class ReviewController {

    private static final String OPEN = "Open";
    private static final String CLOSED = "Closed";

    private static ReviewController instance;
    private static Vector<Review> reviews;
    Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private ReviewController(){
        reviews = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("Projects");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0){
                    reviews.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        //Review review = createReview(data);
                        //reviews.add(review);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static ReviewController getInstance(){
        if(instance == null)
            instance = new ReviewController();

        return instance;
    }

//    private Review createReview(DataSnapshot data){
//        String id = data.getKey();
//        String owner = getChild(data, "owner");
//        String name = getChild(data, "name");
//        String category = getChild(data, "category");
//        String description = getChild(data, "description");
//        int budget = Integer.parseInt(getChild(data, "budget"));
//        String status = getChild(data, "status");
//        String timestamp = getChild(data, "timestamp");
//        double latitude = Double.parseDouble(getChild(data, "latitude"));
//        double longitude = Double.parseDouble(getChild(data, "longitude"));
//
//        return new Project(id, owner, name, category, description, budget, status, timestamp, latitude, longitude);
//    }
//
//    private String getChild(DataSnapshot data, String key){
//        return data.child("project").child(key).getValue().toString();
//    }
//
//    public static Vector<Project> getReviews(){
//        return reviews;
//    }

}
