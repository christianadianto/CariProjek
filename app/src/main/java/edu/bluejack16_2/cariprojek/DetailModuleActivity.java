package edu.bluejack16_2.cariprojek;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.bluejack16_2.cariprojek.Models.Module;

public class DetailModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_module);


        ListView listView = (ListView) findViewById(R.id.listViewModule);


        String category = getIntent().getStringExtra("CATEGORY");
        final Module module = new Module(category);
        ListViewModuleAdapter listViewModuleAdapter = new ListViewModuleAdapter(getApplicationContext());

        for(int i=0;i<module.titles.size();i++){
            listViewModuleAdapter.addTitle(module.titles.get(i));
            listView.setAdapter(listViewModuleAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(module.urls.get(position)));
                startActivity(intent);
            }
        });

    }
}
