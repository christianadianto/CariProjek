package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Controllers.ProjectDetailController;
import edu.bluejack16_2.cariprojek.Controllers.RateController;
import edu.bluejack16_2.cariprojek.Controllers.RateReminderController;
import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.ProjectDetail;
import edu.bluejack16_2.cariprojek.Models.RateReminder;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProjectFragment extends Fragment implements View.OnClickListener{

    Session session;
    User user;
    Vector<Project> projects;
    Double longitude;
    Double latitude;

    Spinner spinnerProject;
    Spinner spinnerCategory;
    EditText txtProjectName;
    EditText txtBudget;
    CheckBox cbStatus;
    EditText txtDescription;
    TextView tvKey;
    Vector<String> projectNames;
    Project selectedProject;

    Button btnUpdate;
    Button btnDelete;

    public UpdateProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_project, container, false);

        spinnerProject = (Spinner) view.findViewById(R.id.spProjectList);
        spinnerCategory = (Spinner) view.findViewById(R.id.spCategoryList);
        txtProjectName = (EditText) view.findViewById(R.id.txtUpdateProjectName);
        txtBudget = (EditText) view.findViewById(R.id.txtUpdateBudget);
        cbStatus = (CheckBox) view.findViewById(R.id.cbUpdateStatus);
        txtDescription = (EditText) view.findViewById(R.id.txtUpdateDescription);
        tvKey = (TextView) view.findViewById(R.id.tvKey);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);

        session = new Session(getContext());
        user = session.getUser();
        projectNames = new Vector<>();
        getProjectList();

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        return view;
    }

    private void getProjectList(){
        projectNames.clear();
        projects = ProjectController.getProjectByEmail(user.getEmail());
        setProjectList();
    }

    private void setProjectList() {
        if(projects.size() == 0){
            toggleForm(false);
            projectNames.add("You don't have any projects");
        }
        else{
            toggleForm(true);
            for (Project project:projects) {
                projectNames.add(project.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                projectNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setAdapter(adapter);
        spinnerProject.getOnItemSelectedListener();
        spinnerProject.setSelection(0);

        if(projects.size() != 0) projectSpinnerListener();

    }

    private void toggleForm(boolean bool){
        txtProjectName.setEnabled(bool);
        txtDescription.setEnabled(bool);
        txtBudget.setEnabled(bool);
        cbStatus.setEnabled(bool);
        spinnerCategory.setEnabled(bool);
        toggleButton(bool);
    }

    private void toggleButton(boolean bool){
        if(bool) {
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        else{
            btnUpdate.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    private void projectSpinnerListener() {
        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Project project = projects.get(position);
                selectedProject = project;
                setFormWithProjectData(project);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v == btnUpdate){
            String name = txtProjectName.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();
            String description = txtDescription.getText().toString();
            int budget = Integer.parseInt(txtBudget.getText().toString());
            String status="";

            if(cbStatus.isChecked()){
                status = "Closed";
            }
            else{
                status = "Open";
            }
            String timestamp = String.valueOf((System.currentTimeMillis()/1000));

            selectedProject.setName(name);
            selectedProject.setCategory(category);
            selectedProject.setBudget(budget);
            selectedProject.setStatus(status);
            selectedProject.setDescription(description);
            selectedProject.setTimestamp(timestamp);

            ProjectController.updateProject(selectedProject);

            if(ProjectDetailController.getProjectDetailByProjectId(selectedProject.getId()) != null) {
                ProjectDetail projectDetail = ProjectDetailController.getProjectDetailByProjectId(selectedProject.getId());
                User projectManager = UserController.getUserByEmail(projectDetail.getUserEmail());

                RateReminder rateReminder = new RateReminder("", user.getId(), projectManager.getId());
                RateReminderController.insertRateReminder(rateReminder);

                rateReminder = new RateReminder("", projectManager.getId(), user.getId());
                RateReminderController.insertRateReminder(rateReminder);

                Intent intent = new Intent(getActivity(), ProjectReviewActivity .class);
                intent.putExtra("projectId", projectDetail.getProjectId());
                startActivity(intent);
            }

            getProjectList();
        }
        else if(v == btnDelete){
            ProjectController.deleteProject(selectedProject);
            getProjectList();
        }
    }

    private void setFormWithProjectData(Project project){
        txtProjectName.setText(project.getName());
        txtDescription.setText(project.getDescription());
        txtBudget.setText(String.valueOf(project.getBudget()));

        if(project.getStatus().equals("Open")){
            cbStatus.setChecked(false);
            toggleForm(true);
        }
        else{
            cbStatus.setChecked(true);
            toggleForm(false);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_project, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        int categoryIndex = adapter.getPosition(project.getCategory());
        spinnerCategory.setSelection(categoryIndex);
    }


}
