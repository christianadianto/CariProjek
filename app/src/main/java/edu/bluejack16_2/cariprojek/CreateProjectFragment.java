package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.Intent;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateProjectFragment extends Fragment implements View.OnClickListener{

    EditText txtName, txtBudget, txtDescription;
    Spinner spinnerCategory;
    Button btnSubmit;
    String[] category;

    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    String email;

    GPSTracker gpsTracker;
    Location location;

    public CreateProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_project, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Projects");

        txtName = (EditText) view.findViewById(R.id.txtName);
        txtDescription = (EditText) view.findViewById(R.id.txtDescription);
        txtBudget = (EditText) view.findViewById(R.id.txtBudget);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        sharedPreferences = this.getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","android.studio@android.com");

        btnSubmit.setOnClickListener(this);

        gpsTracker = new GPSTracker(getContext());


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == btnSubmit){

            location = gpsTracker.getLocation();
            String name = txtName.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();
            String description = txtDescription.getText().toString();
            int budget = Integer.parseInt(txtBudget.getText().toString());
            Long timestampLong = System.currentTimeMillis()/1000;

            Project project = new Project(name, category, description, budget,location.getLatitude(),location.getLongitude());

            DatabaseReference new_project = myRef.push();
            new_project.child("project").setValue(project);
            new_project.child("id").setValue(new_project.getKey());
//            new_project.child("email").setValue(email);
//            new_project.child("name").setValue(project.getName());
//            new_project.child("description").setValue(project.getDescription());
//            new_project.child("budget").setValue(project.getBudget());
//            new_project.child("category").setValue(project.getCategory());
//            new_project.child("status").setValue("Open");
//            new_project.child("latitude").setValue(project.getLatitude());
//            new_project.child("longitude").setValue(project.getLongitude());
//            new_project.child("timestamp").setValue(timestampLong.toString());

            Toast.makeText(getActivity(), "Success to create project", Toast.LENGTH_SHORT).show();

        }

    }
}
