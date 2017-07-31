package edu.bluejack16_2.cariprojek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProgressController;
import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Progress;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class AddProgressActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerProject;
    private EditText txtProgress;
    private Button btnSubmit;

    private Vector<Project> projects;
    private Vector<String> projectNames;

    private Project selectedProject;

    private Session session;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_progress);

        session = new Session(getApplicationContext());
        user = session.getUser();

        init();

        projects = ProjectController.getWorkedProjectByEmail(user.getEmail());
        projectNames = new Vector<>();
    }

    private void init(){
        spinnerProject = (Spinner) findViewById(R.id.spProjectList);
        txtProgress = (EditText) findViewById(R.id.txtProgress);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void setProjectList() {
        if(projects.size() == 0){
            projectNames.add("You don't have any projects");
        }
        else{
            for (Project project:projects) {
                projectNames.add(project.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                projectNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setAdapter(adapter);
        spinnerProject.getOnItemSelectedListener();
        spinnerProject.setSelection(0);

        if(projects.size() != 0){
            addProjectSpinnerListener();
        }
        else {
            txtProgress.setEnabled(false);
            btnSubmit.setEnabled(false);
        }

    }

    private void addProjectSpinnerListener() {

        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Project project = projects.get(position);
                selectedProject = project;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == btnSubmit){
            String projectId = selectedProject.getId();
            String progressText = txtProgress.getText().toString();
            String time = getCurrentTime();

            Progress progress = new Progress("", projectId, progressText, time);
            ProgressController.insertRate(progress);
        }
    }

    private String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date) ;
    }
}
