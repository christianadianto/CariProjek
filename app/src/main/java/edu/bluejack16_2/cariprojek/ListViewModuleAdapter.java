package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 07/29/2017.
 */

public class ListViewModuleAdapter extends BaseAdapter {
    ArrayList<String>titleModules;
    Context context;

    public ListViewModuleAdapter(Context context){
        titleModules = new ArrayList<String>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return titleModules.size();
    }

    @Override
    public Object getItem(int position) {
        return titleModules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.data_module,parent,false);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitleModule);
        tvTitle.setText(titleModules.get(position));


        return convertView;
    }
}
