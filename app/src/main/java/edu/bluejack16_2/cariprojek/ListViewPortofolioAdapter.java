package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Models.Portofolio;
import edu.bluejack16_2.cariprojek.Models.Project;

/**
 * Created by chris on 07/31/2017.
 */

public class ListViewPortofolioAdapter extends BaseAdapter {
    Vector<Portofolio>portofolios;
    Context context;

    public ListViewPortofolioAdapter(Context context){
        portofolios = new Vector<>();
        this.context = context;
    }
    @Override
    public int getCount() {
        return portofolios.size();
    }

    @Override
    public Object getItem(int position) {
        return portofolios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.data_portofolio,parent,false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvViewPortofolioName);
        TextView tvUrl = (TextView) convertView.findViewById(R.id.tvViewPortofolioUrl);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvViewCategoryPortofolio);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvViewDescriptionPortofolio);

        Log.e("CHECKING", "getView: "+portofolios.get(position).getName() );
        tvName.setText(portofolios.get(position).getName());
        tvUrl.setText(portofolios.get(position).getUrl());
        tvCategory.setText(portofolios.get(position).getCategory());
        tvDescription.setText(portofolios.get(position).getDescription());

        return convertView;
    }

    public void addPortofolio(Portofolio portofolio){
        portofolios.add(portofolio);
    }

    public void refresh(Vector<Portofolio>portofolios){
        this.portofolios = portofolios;
        this.notifyDataSetChanged();
    }
}
