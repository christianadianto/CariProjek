package edu.bluejack16_2.cariprojek.Controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.RateReminder;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class RateReminderController {

    private static RateReminderController instance;
    private static Vector<RateReminder> rateReminders;
    Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private RateReminderController(){
        rateReminders = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("RateReminders");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0){
                    rateReminders.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        RateReminder rateReminder = createRateReminder(data);
                        rateReminders.add(rateReminder);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static RateReminderController getInstance(){
        if(instance == null)
            instance = new RateReminderController();

        return instance;
    }

    private RateReminder createRateReminder(DataSnapshot data){
        String id = data.getKey();
        String raterUserId = getChild(data, "raterUserId");
        String ratedUserId = getChild(data, "ratedUserId");

        return new RateReminder(id, raterUserId, ratedUserId);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("rateReminder").child(key).getValue().toString();
    }

    public static Vector<RateReminder> getRateReminders(){
        return rateReminders;
    }

    public static RateReminder getRateReminder(String raterId, String ratedId){
        for (RateReminder rateReminder:rateReminders) {
            if(rateReminder.getRaterUserId().equals(raterId)
                    && rateReminder.getRatedUserId().equals(ratedId))
                return rateReminder;
        }

        return null;
    }

    public static void insertRateReminder(RateReminder rateReminder){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("RateReminders");

        DatabaseReference new_rate = myRef.push();
        rateReminder.setId(new_rate.getKey());
        new_rate.child("rateReminder").setValue(rateReminder);

    }

    public static void removeRateReminder(String rateReminderId){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("RateReminders").child(rateReminderId);
        myRef.removeValue();

    }



}
