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
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCreateProjectFragment extends Fragment {

    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    String email;
    Vector<Project> projects;
    Session session;
    User user;

    public ProfileCreateProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_create_project, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.listViewProject);
        final ListViewProjectAdapter listViewProjectAdapter = new ListViewProjectAdapter(getContext());

        session = new Session(getContext());
        user = session.getUser();
        projects = ProjectController.getProjectByEmail(user.getEmail());

        for (Project project: projects) {
            listViewProjectAdapter.add(project);
            listView.setAdapter(listViewProjectAdapter);
        }

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
