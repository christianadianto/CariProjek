package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProjectFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    String email;
    ArrayList<Project>projects;

    Spinner spinnerProject;
    Spinner spinnerCategory;
    EditText txtProjectName;
    EditText txtBudget;
    CheckBox cbStatus;
    EditText txtDescription;
    TextView tvKey;
    String [] projectNames;

    Button btnUpdate;

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

        projects = new ArrayList<Project>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Projects");
        sharedPreferences = this.getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","android.studio@android.com");
        Query query = FirebaseDatabase.getInstance().getReference().child("Projects").orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                        projects.add(new Project(data.child("name").getValue().toString(), data.child("category").getValue().toString(),
                                data.child("description").getValue().toString(),Integer.parseInt(data.child("budget").getValue().toString()),
                                data.child("status").getValue().toString(), data.child("timestamp").getValue().toString(),data.getKey()));
                }
                setProjectList();

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void setProjectList() {

        int projectSize = 1;
        if(projects.size()>=1){
            projectSize = projects.size();
        }

        projectNames  = new String[projectSize];
        if(projects.size()<1) {
            projectNames[0]="you don't have any project";
        }
        else {
            for (int i = 0; i < projectSize; i++) {
                projectNames[i] = projects.get(i).getName();
            }
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                projectNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setAdapter(adapter);
        spinnerProject.getOnItemSelectedListener();

        spinnerProject.setSelection(0);
        Toast.makeText(getActivity(), "set success", Toast.LENGTH_SHORT).show();

        projectSpinnerListener();
    }

    private void projectSpinnerListener() {
        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "position"+position, Toast.LENGTH_SHORT).show();
                String projectName = projects.get(position).getName();
                String projectBudget = String.valueOf(projects.get(position).getBudget());
                String projectDescription = projects.get(position).getDescription();
                String projectStatus = projects.get(position).getStatus();
                String key = projects.get(position).getKey();
                String category = projects.get(position).getCategory();

                txtProjectName.setText(projectName);
                txtDescription.setText(projectDescription);
                txtBudget.setText(projectBudget);
                if(projectStatus.equals("Open")){
                    cbStatus.setChecked(false);
                }
                else{
                    cbStatus.setChecked(true);
                }
                tvKey.setText(key);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_project, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(adapter);
                if (!category.equals(null)) {
                    int spinnerPosition = adapter.getPosition(category);
                    spinnerCategory.setSelection(spinnerPosition);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
