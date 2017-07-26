package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 07/26/2017.
 */

public class ListViewHomeAdapter extends BaseAdapter {
    ArrayList<Project> projects;
    Context context;

    public ListViewHomeAdapter(Context context) {
        projects = new ArrayList<Project>();
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
            convertView = inflater.inflate(R.layout.data_home,parent,false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvDataHomeName);
        TextView tvBudget = (TextView) convertView.findViewById(R.id.tvDataHomeBudget);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvDataHomeCategory);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDataHomeDescription);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvDataHomeStatus);

        tvName.setText(projects.get(position).getName());
        tvBudget.setText(String.valueOf(projects.get(position).getBudget()));
        tvCategory.setText(projects.get(position).getCategory());
        tvDescription.setText(projects.get(position).getDescription());
        tvStatus.setText(projects.get(position).getStatus());

        return convertView;
    }

    public void add(Project project){
        projects.add(project);
    }
}
