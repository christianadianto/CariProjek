package edu.bluejack16_2.cariprojek.Controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.User;

/**
 * Created by chris on 07/29/2017.
 */

public class UserController {

    private static UserController instance;
    private static Vector<User> users;
    Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private UserController(){
        users = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("Users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                if(dataSnapshot.getChildrenCount()!=0){
                    users.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        User user = createUser(data);
                        users.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static UserController getInstance(){
        if(instance == null)
            instance = new UserController();

        return instance;
    }

    private User createUser(DataSnapshot data){
        String id = data.getKey();
        String name = getChild(data, "name");
        String email = getChild(data, "email");
        String address = getChild(data, "address");
        String password = getChild(data, "password");
        String phonenumber = getChild(data, "phonenumber");

        return new User(id, email, password, name, address, phonenumber);
    }

    public static String validateUser(User user){
        String err="";

        if(user.getEmail().equals("")){
            err = "email must be filled and valid";
        }
        if(user.getPassword().equals("")){
            err="password must be filled";
        }
        if(user.getPassword().length()<6){
            err="password must be more than 6 character";
        }
        if(user.getName().equals("")){
            err="name must be filled";
        }
        if(user.getAddress().equals("")){
            err="address must be filled";
        }
        if(user.getPhonenumber().equals("")){
            err="phone must be filled";
        }

        return err;
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("user").child(key).getValue().toString();
    }

    public static void insertUser(User user){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        DatabaseReference new_user = myRef.push();
        user.setId(new_user.getKey());
        new_user.child("user").setValue(user);
    }

    public static User getUserByEmail(String email){
        for (User user:users) {
            if(user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    public static boolean userAuth(String email, String password){
        for (User user:users) {
            if(user.getEmail().equals(email) && user.getPassword().equals(password))
                return true;
        }
        return false;
    }

}
