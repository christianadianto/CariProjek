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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuHomeFragment extends Fragment implements View.OnClickListener{

    Vector<Project> projects;
    SharedPreferences sharedPreferences;
    User user;
    Session session;
    Spinner spCategory;
    ListView listView;
    ListViewProjectAdapter listViewProjectAdapter;
    Button btnAddProgress;

    public MenuHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_home, container, false);

        listView = (ListView) view.findViewById(R.id.listViewHome);
        listViewProjectAdapter = new ListViewProjectAdapter(getContext());
        spCategory = (Spinner) view.findViewById(R.id.spCategoryHome);
        btnAddProgress = (Button) view.findViewById(R.id.btnAddProgress);

        session = new Session(getContext());
        user = session.getUser();
        projects = ProjectController.getProjects();
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

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projects = ProjectController.getProjectByCategory(spCategory.getSelectedItem().toString());
                getProject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAddProgress.setOnClickListener(this);
        return view;

    }

    public void getProject(){
        listViewProjectAdapter.refresh(projects);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Cari Project ");
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddProgress){
            Intent intent = new Intent(getActivity(), AddProgressActivity.class);
            startActivity(intent);
        }
    }
}
