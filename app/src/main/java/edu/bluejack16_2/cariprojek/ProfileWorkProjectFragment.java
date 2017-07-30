package edu.bluejack16_2.cariprojek;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileWorkProjectFragment extends Fragment {

    Vector<Project> projects;
    Session session;
    User user;

    public ProfileWorkProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_work_project, container, false);

        session = new Session(getContext());
        user = session.getUser();
        projects = ProjectController.getWorkedProjectByEmail(user.getEmail());

        ListView listView = (ListView) view.findViewById(R.id.listViewWorkProject);
        final ListViewProjectAdapter listViewProjectAdapter = new ListViewProjectAdapter(getContext());

        for (Project project: projects) {
            listViewProjectAdapter.add(project);
        }
        listView.setAdapter(listViewProjectAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                Project project = (Project) listViewProjectAdapter.getItem(position);
                intent.putExtra("projectId", project.getId());
                startActivity(intent);
            }
        });

        return view;
    }

}
