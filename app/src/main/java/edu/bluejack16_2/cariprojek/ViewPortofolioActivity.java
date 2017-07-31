package edu.bluejack16_2.cariprojek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.PortofolioController;
import edu.bluejack16_2.cariprojek.Models.Portofolio;

public class ViewPortofolioActivity extends AppCompatActivity {

    Vector<Portofolio>portofolios;
    ListViewPortofolioAdapter listViewPortofolioAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_portofolio);

        ListView listView = (ListView) findViewById(R.id.listViewPortofolio);
        listViewPortofolioAdapter = new ListViewPortofolioAdapter(getApplicationContext());

        portofolios = PortofolioController.getPortofolios();
        for(int i=0;i<portofolios.size();i++){
            listViewPortofolioAdapter.addPortofolio(portofolios.get(i));
            listView.setAdapter(listViewPortofolioAdapter);
        }
    }

    public void getPortofolio(){
        Log.e("TEST", "getPortofolio: "+portofolios.size());
        listViewPortofolioAdapter.refresh(portofolios);
    }
}
