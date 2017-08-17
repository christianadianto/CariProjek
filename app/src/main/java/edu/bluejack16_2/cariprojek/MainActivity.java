package edu.bluejack16_2.cariprojek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.bluejack16_2.cariprojek.Controllers.ChatRoomController;
import edu.bluejack16_2.cariprojek.Controllers.PortofolioController;
import edu.bluejack16_2.cariprojek.Controllers.ProgressController;
import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Controllers.ProjectDetailController;
import edu.bluejack16_2.cariprojek.Controllers.RateController;
import edu.bluejack16_2.cariprojek.Controllers.RateReminderController;
import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.Portofolio;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProjectController.getInstance();
        PortofolioController.getInstance();
        UserController.getInstance();
        ProjectDetailController.getInstance();
        ChatRoomController.getInstance();
        RateController.getInstance();
        RateReminderController.getInstance();
        ProgressController.getInstance();

        RelativeLayout layout = new RelativeLayout(this);
        ProgressBar progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar,params);

        setContentView(layout);

        Intent intent;
        Session session = new Session(getApplicationContext());

        if(hasSession())
            intent = new Intent(getApplicationContext(), HomeActivity.class);
        else
            intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);
        finish();
    }

    private boolean hasSession(){
        Session session = new Session(getApplicationContext());
        if(session.getUser() != null)
            return true;

        return false;
    }

}
