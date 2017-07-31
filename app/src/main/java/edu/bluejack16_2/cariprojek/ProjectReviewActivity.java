package edu.bluejack16_2.cariprojek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.bluejack16_2.cariprojek.Controllers.ProjectDetailController;
import edu.bluejack16_2.cariprojek.Controllers.RateController;
import edu.bluejack16_2.cariprojek.Controllers.RateReminderController;
import edu.bluejack16_2.cariprojek.Controllers.ReviewController;
import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.ProjectDetail;
import edu.bluejack16_2.cariprojek.Models.Rate;
import edu.bluejack16_2.cariprojek.Models.RateReminder;
import edu.bluejack16_2.cariprojek.Models.Review;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class ProjectReviewActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvName;
    private RatingBar rbRate;
    private EditText txtReview;
    private Button btnSubmit;

    private Session session;
    private String projectId;

    private ProjectDetail projectDetail;

    private User user;
    private User userTarget;
    private float ratePoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_review);

        session = new Session(getApplicationContext());
        user = session.getUser();

        projectId = getIntent().getStringExtra("projectId");
        Log.e("PROJECTID", "onCreate: " + projectId );
        projectDetail = ProjectDetailController.getProjectDetailByProjectId(projectId);

        userTarget = UserController.getUserByEmail(projectDetail.getUserEmail());
        ratePoint = 0;
        init();
        btnSubmit.setOnClickListener(this);

    }

    public void init(){
        tvName = (TextView) findViewById(R.id.tvName);
        rbRate = (RatingBar) findViewById(R.id.rbRate);
        txtReview = (EditText) findViewById(R.id.txtReview);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        tvName.setText(userTarget.getName());

        rbRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratePoint = v;
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == btnSubmit){
            Rate rate = new Rate("", userTarget.getId(), ratePoint);
            RateController.insertRate(rate);
            Log.e("Test", "onClick: " + user.getId() + " " + userTarget.getId() );

            RateReminder rateReminder = RateReminderController.getRateReminder(user.getId(), userTarget.getId());
            RateReminderController.removeRateReminder(rateReminder.getId());

            Review review = new Review("", userTarget.getEmail(),user.getEmail(), txtReview.getText().toString());
            ReviewController.insertReview(review);

            Toast.makeText(this, "Success Insert Review", Toast.LENGTH_SHORT).show();
        }
    }
}
