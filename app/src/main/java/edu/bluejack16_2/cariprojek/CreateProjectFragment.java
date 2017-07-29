package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.GPSTracker;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateProjectFragment extends Fragment implements View.OnClickListener{

    EditText txtName, txtBudget, txtDescription;
    Spinner spinnerCategory;
    Button btnSubmit;
    String[] category;

    Session session;
    User user;

    GPSTracker gpsTracker;
    Location location;

    public CreateProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_project, container, false);

        txtName = (EditText) view.findViewById(R.id.txtName);
        txtDescription = (EditText) view.findViewById(R.id.txtDescription);
        txtBudget = (EditText) view.findViewById(R.id.txtBudget);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);

        gpsTracker = new GPSTracker(getContext());
        session = new Session(getContext());
        user = session.getUser();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == btnSubmit){
            String email = user.getEmail();
            String name = txtName.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();
            String description = txtDescription.getText().toString();
            int budget = Integer.parseInt(txtBudget.getText().toString());
            String timestamp = String.valueOf(System.currentTimeMillis()/1000);

            location = gpsTracker.getLocation();
            double latatide = location.getLatitude();
            double longitude = location.getLongitude();

            Project project = new Project("", email, name, category, description, budget, "Open", timestamp, latatide, longitude);
            ProjectController.insertProject(project);

            Toast.makeText(getActivity(), "Success to create project", Toast.LENGTH_SHORT).show();

        }

    }
}
