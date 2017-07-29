package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Project;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCreateProjectFragment extends Fragment {

    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    String email;
    Vector<Project> projects;

    public ProfileCreateProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_create_project, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","android.studio@android.com");

        final ListView listView = (ListView) view.findViewById(R.id.listViewProject);
        final ListViewProjectAdapter listViewProjectAdapter = new ListViewProjectAdapter(getContext());

        projects = ProjectController.getProjects();

        for (Project project: projects) {
            if(project.getStatus().equals("Open") && project.getOwner().equals(email)){
                listViewProjectAdapter.add(project);
                listView.setAdapter(listViewProjectAdapter);
            }
        }

//        Query query = FirebaseDatabase.getInstance().getReference().child("Projects").orderByChild("email").equalTo(email);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String name = "";
//                String category = "";
//                String description = "";
//                int budget = 0;
//                String status ="";
//                String timestamp = "";
//                String id = "";
//                double longitude=0;
//                double latitude=0;
//                if(dataSnapshot.getChildrenCount()!=0){
//                    for (DataSnapshot data:dataSnapshot.getChildren()) {
//                        name = data.child("name").getValue().toString();
//                        category = data.child("category").getValue().toString();
//                        description = data.child("description").getValue().toString();
//                        budget = Integer.parseInt(data.child("budget").getValue().toString());
//                        status = data.child("status").getValue().toString();
//                        timestamp = data.child("timestamp").getValue().toString();
//                        id = data.child("id").getValue().toString();
//                        longitude = Double.parseDouble(data.child("longitude").getValue().toString());
//                        latitude = Double.parseDouble(data.child("latitude").getValue().toString());
//
//                        Project project = new Project(name,category,description,budget,status,timestamp,id,latitude,longitude);
//                        if(status.equals("Open")) {
//                            listViewProjectAdapter.add(project);
//                            listView.setAdapter(listViewProjectAdapter);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), listViewHomeAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProjectReviewActivity.class);
                intent.putExtra("ID",listViewProjectAdapter.getItem(position).toString());
                startActivity(intent);
            }
        });


        return view;
    }

}
