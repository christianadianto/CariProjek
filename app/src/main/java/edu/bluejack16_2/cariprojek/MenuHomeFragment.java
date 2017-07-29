package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Project;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuHomeFragment extends Fragment {

//    DatabaseReference myRef;
    Vector<Project> projects;
    SharedPreferences sharedPreferences;
    String email;

    public MenuHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_home, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","android.studio@android.com");

        final ListView listView = (ListView) view.findViewById(R.id.listViewHome);
        final ListViewHomeAdapter listViewHomeAdapter = new ListViewHomeAdapter(getContext());

        projects = ProjectController.getProjects();

        for (Project project:projects) {
            if (project.getStatus().equals("Open")) {
                listViewHomeAdapter.add(project);
                listView.setAdapter(listViewHomeAdapter);
            }
        }

//        Query query = FirebaseDatabase.getInstance().getReference().child("Projects").orderByChild("timestamp");
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
//
//                if(dataSnapshot.getChildrenCount()!=0){
//                    for (DataSnapshot data:dataSnapshot.getChildren()) {
//
//                        name = data.child("project").child("name").getValue().toString();
//                        category = data.child("project").child("category").getValue().toString();
//                        description = data.child("project").child("description").getValue().toString();
//                        budget = Integer.parseInt(data.child("project").child("budget").getValue().toString());
//                        status = data.child("project").child("status").getValue().toString();
//                        timestamp = data.child("project").child("timestamp").getValue().toString();
//                        id = data.getKey();
//                        longitude = Double.parseDouble(data.child("project").child("longitude").getValue().toString());
//                        latitude = Double.parseDouble(data.child("project").child("latitude").getValue().toString());
//
//                        Project project = new Project(name, category, description, budget, status, timestamp, id, latitude, longitude);
//                        if (status.equals("Open")) {
//                            listViewHomeAdapter.add(project);
//                            listView.setAdapter(listViewHomeAdapter);
//                        }
//
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
//                Toast.makeText(getContext(), listViewHomeAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProjectReviewActivity.class);
                intent.putExtra("ID",listViewHomeAdapter.getItem(position).toString());
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Menu Home");
    }
}
