package edu.bluejack16_2.cariprojek;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class ProfileOtherUserActivity extends AppCompatActivity {

    Session session;
    ListViewJoinedUserAdapter listViewJoinedUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_other_user);

        session = new Session(getApplicationContext());

        listViewJoinedUserAdapter = new ListViewJoinedUserAdapter(getApplicationContext());
        String email = getIntent().getStringExtra("projectId");
        Session session = new Session(getApplicationContext());
        session.setSession("userId",email);

        MenuProfileFragment fragment = new MenuProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame_detail, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        session.setSession("userId", null);
        super.onBackPressed();
    }
}
