package edu.bluejack16_2.cariprojek;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ProjectReviewActivity extends AppCompatActivity {

    TextView tvID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_review);

        String id = getIntent().getStringExtra("ID");

        tvID = (TextView) findViewById(R.id.tvID);
        tvID.setText(id);


    }
}
