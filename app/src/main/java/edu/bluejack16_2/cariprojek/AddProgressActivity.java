package edu.bluejack16_2.cariprojek;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProgressController;
import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Progress;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class AddProgressActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtProgress;
    private FloatingActionButton btnSubmit;
    private LinearLayout linearLayout;

    private Session session;
    private User user;

    private String projectId;

    ListViewProgressAdapter listViewProgressAdapter;
    private Vector<Progress>listProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_progress);

        init();

        session = new Session(getApplicationContext());
        user = session.getUser();

        projectId = getIntent().getStringExtra("projectId").toString();

        ListView listView = (ListView) findViewById(R.id.listViewProgress);
        listViewProgressAdapter = new ListViewProgressAdapter(getApplicationContext());
        listProgress = ProgressController.getProgressesByProjectId(projectId);
        setProjectToListview();

        listView.setAdapter(listViewProgressAdapter);

        btnSubmit.setOnClickListener(this);
        if(user.getEmail().equals(ProjectController.getProjectById(projectId).getOwner())){
            linearLayout.setVisibility(View.GONE);
        }
        else{
            linearLayout.setVisibility(View.VISIBLE);
        }
    }


    public void setProjectToListview(){
        listViewProgressAdapter.refresh(listProgress);
    }

    private void init(){
        txtProgress = (EditText) findViewById(R.id.txtProgress);
        btnSubmit = (FloatingActionButton) findViewById(R.id.btnSubmit);
        listProgress = new Vector<>();
        linearLayout = (LinearLayout) findViewById(R.id.progress_layout);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSubmit){
            String progressText = txtProgress.getText().toString();
            String time = getCurrentTime();

            Progress progress = new Progress("", projectId, progressText, time);
            ProgressController.insertProgress(progress);

            listProgress.add(progress);
            setProjectToListview();

        }
    }

    private String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date) ;
    }
}
