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
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuHomeFragment extends Fragment {

    Vector<Project> projects;
    SharedPreferences sharedPreferences;
    User user;
    Session session;

    public MenuHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_home, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.listViewHome);
        final ListViewProjectAdapter listViewProjectAdapter = new ListViewProjectAdapter(getContext());

        session = new Session(getContext());
        user = session.getUser();
        projects = ProjectController.getProjects();

        for (Project project:projects) {
            if (project.getStatus().equals("Open")) {
                listViewProjectAdapter.add(project);
                listView.setAdapter(listViewProjectAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), listViewHomeAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProjectReviewActivity.class);
                intent.putExtra("ID",listViewProjectAdapter.getItem(position).toString());
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
