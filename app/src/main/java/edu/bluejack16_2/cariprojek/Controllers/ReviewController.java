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
        query = FirebaseDatabase.getInstance().getReference().child("Reviews");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0){
                    reviews.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        Review review = createReview(data);
                        reviews.add(review);
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

    private Review createReview(DataSnapshot data){
        String id = data.getKey();
        String userToEmail = getChild(data, "userToName");
        String userFromEmail = getChild(data, "userFromName");
        String reviewMessage = getChild(data, "reviewMessage");

        return new Review(id, userToEmail, userFromEmail, reviewMessage);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("review").child(key).getValue().toString();
    }

    public static Vector<Review> getReviews(){
        return reviews;
    }

    public static Vector<Review> getReviewsByUserEmail(String userEmail){

        Vector<Review> filtered_reviews = new Vector<>();
        for (Review review:reviews) {
            if(review.getUserToEmail().equals(userEmail))
                filtered_reviews.add(review);
        }

        return filtered_reviews;
    }

    public static void insertReview (Review review){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Reviews");

        DatabaseReference new_review = myRef.push();
        review.setId(new_review.getKey());
        new_review.child("review").setValue(review);
    }

}
