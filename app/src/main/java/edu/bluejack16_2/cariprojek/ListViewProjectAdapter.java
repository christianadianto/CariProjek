package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

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
        return projects.get(position).getId();
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

        String category = minifyCategoryString(projects.get(position).getCategory());
        String description = minifyDescriptionString(projects.get(position).getDescription());

        tvName.setText(projects.get(position).getName());
        tvBudget.setText(String.valueOf(projects.get(position).getBudget()));
        tvCategory.setText(category);
        tvDescription.setText(description);
        tvStatus.setText(projects.get(position).getStatus());

        return convertView;
    }

    private String minifyDescriptionString(String description){
        if(description.length() > 50)
            return description.substring(0, 48) + " ...";

        return description;
    }


    private String minifyCategoryString(String category){

        if(category.equals("C++") || category.equals("C#"))
            return category;

        else if(category.indexOf(" ") != -1)
            return (""+category.charAt(0)+category.charAt(category.indexOf(" ")+1));

        return (""+category.charAt(0)+category.charAt(1));
    }

    public void add(Project project){
        projects.add(project);
    }

    public void refresh(Vector<Project>projects){
        this.projects = projects;
        this.notifyDataSetChanged();
    }

}
