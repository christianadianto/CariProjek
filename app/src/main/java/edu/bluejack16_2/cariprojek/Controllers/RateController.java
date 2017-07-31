package edu.bluejack16_2.cariprojek.Controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.Rate;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class RateController {

    private static RateController instance;
    private static Vector<Rate> rates;
    Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private RateController(){
        rates = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("Rates");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0){
                    rates.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        Rate rate = createRate(data);
                        rates.add(rate);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static RateController getInstance(){
        if(instance == null)
            instance = new RateController();

        return instance;
    }

    private Rate createRate(DataSnapshot data){
        String id = data.getKey();
        String userId = getChild(data, "userId");
        float rate = Float.parseFloat(getChild(data, "rate"));

        return new Rate(id, userId, rate);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("rate").child(key).getValue().toString();
    }

    public static Vector<Rate> getRates(){
        return rates;
    }

    public static void insertRate(Rate rate){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Rates");

        DatabaseReference new_rate = myRef.push();
        rate.setId(new_rate.getKey());
        new_rate.child("rate").setValue(rate);

    }

    public static int getRateAverageByUserId(String userId){

        int total = 0;
        int rate_count = 0;

        for (Rate rate:rates) {
            if(rate.getUserId().equals(userId)) {
                total += rate.getRate();
                rate_count++;
            }
        }

        return total/rate_count;

    }

}
