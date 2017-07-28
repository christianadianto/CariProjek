package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 07/27/2017.
 */

public class GridViewProjectAdapter extends BaseAdapter {

    ArrayList<String> categories;
    Context context;

    public GridViewProjectAdapter(Context context) {
        categories = new ArrayList<String>();
        this.context = context;
        Resources resources = context.getResources();
        String[] tempCategories = resources.getStringArray(R.array.category_project);
        setCategories(tempCategories);
    }

    public void setCategories(String [] tempCategories){
        for(int i = 0;i<tempCategories.length;i++){
            categories.add(tempCategories[i]);
        }
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.data_category,parent,false);
        }
        TextView tvLearningCategory = (TextView) convertView.findViewById(R.id.tvLearningCategory);
        tvLearningCategory.setText(categories.get(position));

        return convertView;
    }
}
