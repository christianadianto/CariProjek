package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Progress;
import edu.bluejack16_2.cariprojek.Models.Project;

/**
 * Created by 1601266375O on 8/13/2017.
 */

public class ListViewProgressAdapter extends BaseAdapter {
    Vector<Progress>listProgress;

    Context context;

    public ListViewProgressAdapter(Context context) {
        listProgress = new Vector<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return listProgress.size();
    }

    @Override
    public Object getItem(int i) {
        return listProgress.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.data_progress,parent,false);
        }

        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);

        String description = listProgress.get(i).getProgress();
        String date = minifyDateFormat(listProgress.get(i).getTime());

        tvDate.setText(date);
        tvDescription.setText(description);

        return convertView;
    }
    public void add(Progress progress){
        listProgress.add(progress);
    }

    public void refresh(Vector<Progress>progress){
        this.listProgress = progress;
        this.notifyDataSetChanged();
    }

    public String minifyDateFormat(String date){

        String[] arrDate = date.split("-");
        String newDate = arrDate[0]+"/"+arrDate[1];
        return newDate;

    }
}
