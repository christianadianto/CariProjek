package edu.bluejack16_2.cariprojek.Controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Chat;
import edu.bluejack16_2.cariprojek.Models.User;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class ChatController {


    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    public static Vector<Chat> getChatsByProjectId(final String projectId){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ChatRooms");

        final Vector<Chat> chats = new Vector<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if(data.child("chatRoom").child("projectId").getValue().toString().equals(projectId)) {

                            DataSnapshot ds = data.child("chatRoom").child("chats");

                            for (DataSnapshot d:ds.getChildren()) {
                                Chat chat = createChat(d);
                                chats.add(chat);
                            }

                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return chats;
    }


    private static Chat createChat(DataSnapshot data){
        String id = data.getKey();
        String name = getChild(data, "name");
        String time = getChild(data, "time");
        String message = getChild(data, "message");

        return new Chat(id, name, time, message);
    }

    private static String getChild(DataSnapshot data, String key){
        return data.child("chat").child(key).getValue().toString();
    }

    public void insertChat(String chatRoomId, Chat chat){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ChatRooms").child(chatRoomId).child("chatRoom").child("chats");

        DatabaseReference new_chat = myRef.push();
        chat.setId(new_chat.getKey());
        new_chat.child("chat").setValue(chat);
    }


}
