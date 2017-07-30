package edu.bluejack16_2.cariprojek.Controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Chat;
import edu.bluejack16_2.cariprojek.Models.ChatRoom;
import edu.bluejack16_2.cariprojek.Models.User;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class ChatRoomController {

    private static ChatRoomController instance;
    private static Vector<ChatRoom> chatRooms;

    private Query query;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private ChatRoomController(){

        chatRooms = new Vector<>();
        query = FirebaseDatabase.getInstance().getReference().child("ChatRooms");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() != 0){
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        ChatRoom chatRoom = createChatRoom(data);
                        chatRooms.add(chatRoom);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static ChatRoomController getInstance(){
        if(instance == null)
            instance = new ChatRoomController();

        return instance;
    }


    private ChatRoom createChatRoom(DataSnapshot data){
        String id = data.getKey();
        String projectId = getChild(data, "projectId");
        //Vector<Chat> chats = getChat(id);

        return new ChatRoom(id, projectId, null);
    }

    private String getChild(DataSnapshot data, String key){
        return data.child("chatRoom").child(key).getValue().toString();
    }

    private Vector<Chat> getChat(String chatRoomId){

        ChatRoomController chatRoomController = new ChatRoomController();
        return chatRoomController.getChat(chatRoomId);

    }

    public static ChatRoom getChatRoomByProjectId(String projectId){

        for (ChatRoom chatRoom:chatRooms) {
            if(chatRoom.getProjectId().equals(projectId)){
                return chatRoom;
            }
        }

        return null;
    }

    public static void insertChatRoom(ChatRoom chatRoom){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ChatRooms");

        DatabaseReference new_chatRoom = myRef.push();
        chatRoom.setId(new_chatRoom.getKey());
        new_chatRoom.child("chatRoom").setValue(chatRoom);
    }

    public static boolean isHaveChatRoom(String projectId){
        for (ChatRoom chatRoom:chatRooms) {
            if(chatRoom.getProjectId().equals(projectId))
                return true;
        }

        return false;
    }

    public static void insertChat(String chatRoomId, Chat chat){

        ChatController chatController = new ChatController();
        chatController.insertChat(chatRoomId, chat);

    }


}
