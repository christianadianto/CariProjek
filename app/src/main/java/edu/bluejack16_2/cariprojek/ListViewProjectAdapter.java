package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Project;

/**
 * Created by chris on 07/25/2017.
 */

public class ListViewProjectAdapter extends BaseAdapter {
    Vector<Project> projects;
    Context context;

    public ListViewProjectAdapter(Context context) {
        projects = new Vector<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.data_project_profile,parent,false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvDataName);
        TextView tvBudget = (TextView) convertView.findViewById(R.id.tvDataBudget);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvDataCategory);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDataDescription);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvDataStatus);

        String category = ProjectController.minifyCategoryString(projects.get(position).getCategory());
        String description = ProjectController.minifyDescriptionString(projects.get(position).getDescription());

        String name = projects.get(position).getName();
        String budget = changeBudgetFormat(String.valueOf(projects.get(position).getBudget()));
        String status = projects.get(position).getStatus();

        tvName.setText(name);
        tvBudget.setText(budget);
        tvCategory.setText(category);
        tvDescription.setText(description);
        tvStatus.setText(status);

        return convertView;
    }

    public void add(Project project){
        projects.add(project);
    }

    public void refresh(Vector<Project>projects){
        this.projects = projects;
        this.notifyDataSetChanged();
    }

    private String changeBudgetFormat(String budget){

        budget = "IDR " + budget;
        return budget;
    }

}
