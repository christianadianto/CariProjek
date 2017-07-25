package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by chris on 07/25/2017.
 */

public class ListViewProjectAdapter extends BaseAdapter {
    ArrayList<Project> projects;
    Context context;

    public ListViewProjectAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.data,parent,false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvDataName);
        TextView tvBudget = (TextView) convertView.findViewById(R.id.tvDataBudget);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvDataCategory);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDataDescription);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvDataStatus);

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
