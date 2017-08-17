package edu.bluejack16_2.cariprojek;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ChatController;
import edu.bluejack16_2.cariprojek.Controllers.ChatRoomController;
import edu.bluejack16_2.cariprojek.Models.Chat;
import edu.bluejack16_2.cariprojek.Models.ChatRoom;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class RoomChatActivity extends AppCompatActivity implements View.OnClickListener {

    private Session session;
    private User user;
    private String projectId;

    private ChatRoom chatRoom;
    private Vector<Chat> chats;

    private EditText txtMessage;
    private FloatingActionButton btnSend;

    private ListView listView;
    private ListViewChatAdapter listViewChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);

        session = new Session(getApplicationContext());
        user = session.getUser();
        projectId = getIntent().getStringExtra("projectId");

        init();
        checkRoomChat(projectId);
        refreshChat();

    }

    private void init(){

        listView = (ListView) findViewById(R.id.listViewChat);
        listViewChatAdapter = new ListViewChatAdapter(getApplicationContext());

        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnSend = (FloatingActionButton) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);

    }

    private void checkRoomChat(String projectId){

        if(!ChatRoomController.isHaveChatRoom(projectId)){
            chatRoom = new ChatRoom("", projectId, null);
            ChatRoomController.insertChatRoom(chatRoom);
        }
        else {
            chatRoom = ChatRoomController.getChatRoomByProjectId(projectId);
        }

        chats = ChatController.getChatsByProjectId(projectId);
    }

    private void refreshChat(){
        listViewChatAdapter.setChats(chats);
        listView.setAdapter(listViewChatAdapter);
        listViewChatAdapter.refresh(chats);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSend){

            String name = user.getName();
            String time = getCurrentTime();
            String message = txtMessage.getText().toString();

            Chat chat = new Chat("", name, time, message);
            ChatRoomController.insertChat(chatRoom.getId(), chat);
            chats.add(chat);
            refreshChat();
            txtMessage.setText("");
        }
    }

    private String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date) ;
    }
}
