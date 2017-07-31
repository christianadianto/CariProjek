package edu.bluejack16_2.cariprojek.Controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Portofolio;
import edu.bluejack16_2.cariprojek.UpdatePortofolioFragment;

/**
 * Created by chris on 07/28/2017.
 */

public class PortofolioController {

    private static PortofolioController instance;
    private static Vector<Portofolio> portofolios;
    Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private PortofolioController(){
        portofolios = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("Portofolios");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0){
                    portofolios.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        Portofolio portofolio = createPortofolio(data);
                        portofolios.add(portofolio);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static PortofolioController getInstance(){
        if(instance == null)
            instance = new PortofolioController();

        return instance;
    }

    private Portofolio createPortofolio(DataSnapshot data){
        String id = data.getKey();
        String owner = getChild(data, "owner");
        String name = getChild(data, "name");
        String category = getChild(data, "category");
        String description = getChild(data, "description");
        String url = getChild(data, "url");
        String timestamp = getChild(data, "timestamp");

        return new Portofolio(owner, name, category, description, url, timestamp, id);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("portofolio").child(key).getValue().toString();
    }

    public static Vector<Portofolio> getPortofolios(){
        return portofolios;
    }

    public static Vector<Portofolio> getPortofolioByEmail(String email){

        Vector<Portofolio> filtered_portofolios = new Vector<>();

        for (Portofolio portofolio:portofolios) {
            if(portofolio.getOwner().equals(email)){
                filtered_portofolios.add(portofolio);
            }
        }

        return filtered_portofolios;
    }

    public static void insertPortofolio(Portofolio portofolio){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Portofolios");

        DatabaseReference new_user = myRef.push();
        portofolio.setId(new_user.getKey());
        new_user.child("portofolio").setValue(portofolio);
    }

    public static void updatePortofolio(Portofolio portofolio){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Portofolios").child(portofolio.getId()).child("portofolio");
        myRef.setValue(portofolio);
    }

    public static void deletePortofolio(Portofolio portofolio){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Portofolios").child(portofolio.getId());
        myRef.removeValue();
    }


}
