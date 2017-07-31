package edu.bluejack16_2.cariprojek;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Controllers.ProjectController;
import edu.bluejack16_2.cariprojek.Controllers.ProjectDetailController;
import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.Project;
import edu.bluejack16_2.cariprojek.Models.ProjectDetail;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class ProjectDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvProjectCategory;
    private TextView tvProjectName;
    private TextView tvProjectBudget;
    private TextView tvProjectDescription;
    private TextView tvProjectStatus;

    private TextView tvOwnerName;
    private TextView tvOwnerRate;

    private TextView tvChoosenUser;
    private TextView tvChoosenName;
    private TextView tvChoosenUserRate;

    private LinearLayout layoutChoosenUser;
    private LinearLayout layoutButton;

    private Button btnJoin;
    private Button btnChat;

    private Session session;
    private User user;
    private Project project;
    private User ownerProject;
    private Vector<User> joinedUsers;
    private String projectId;
    private ListView listView;
    private ListViewJoinedUserAdapter listViewJoinedUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        init();

        session = new Session(getApplicationContext());
        user = session.getUser();
        projectId = getIntent().getStringExtra("projectId");

        project = ProjectController.getProjectById(projectId);
        ownerProject = UserController.getUserByEmail(project.getOwner());
        joinedUsers = UserController.getUserByProject(projectId);

        refreshPojectDetail();

    }

    private void init(){
        listView = (ListView) findViewById(R.id.listViewJoinedUser);
        listViewJoinedUserAdapter = new ListViewJoinedUserAdapter(getApplicationContext());

        tvProjectName = (TextView) findViewById(R.id.tvProjectName);
        tvProjectCategory = (TextView) findViewById(R.id.tvProjectCategory);
        tvProjectBudget = (TextView) findViewById(R.id.tvProjectBudget);
        tvProjectDescription = (TextView) findViewById(R.id.tvProjectDescription);
        tvProjectStatus = (TextView) findViewById(R.id.tvProjectStatus);

        tvOwnerName = (TextView) findViewById(R.id.tvProjectOwner);
        tvOwnerRate = (TextView) findViewById(R.id.tvProjectOwnerRate);

        tvChoosenUser = (TextView) findViewById(R.id.tvChoosenUser);
        tvChoosenName = (TextView) findViewById(R.id.tvChoosenName);
        tvChoosenUserRate = (TextView) findViewById(R.id.tvChoosenUserRate);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnChat = (Button) findViewById(R.id.btnChat);

        btnJoin.setVisibility(View.GONE);
        btnChat.setVisibility(View.GONE);

        layoutChoosenUser = (LinearLayout) findViewById(R.id.choosenUserSection);
        layoutButton = (LinearLayout) findViewById(R.id.buttonSection);

        btnJoin.setOnClickListener(this);
        btnChat.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ProjectDetailActivity.this, "MASUK SHORT", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ProfileOtherUserActivity.class);
                User choosenUser = (User) listViewJoinedUserAdapter.getItem(i);
                intent.putExtra("projectId", choosenUser.getEmail());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(isOwnerProject() && isAvailable()){
                    User user = (User) listViewJoinedUserAdapter.getItem(i);
                    chooseUserAsManager(user);
                }

                return true;
            }
        });
    }

    private void setProjectDetail(){

        String category = ProjectController.minifyCategoryString(project.getCategory());

        tvProjectName.setText(project.getName());
        tvProjectCategory.setText(category);
        tvProjectBudget.setText(String.valueOf(project.getBudget()));
        tvProjectDescription.setText(project.getDescription());
        tvProjectStatus.setText(project.getStatus());

        tvOwnerName.setText(ownerProject.getName());
        tvOwnerRate.setText(String.valueOf(5));

    }

    private void setJoinedUser(){
        listViewJoinedUserAdapter.setUsers(joinedUsers);
        listView.setAdapter(listViewJoinedUserAdapter);
        listViewJoinedUserAdapter.refreshItems(joinedUsers);
    }

    private void toggleButtonForUser(){

        if(isOwnerProject() && isAvailable()) {
            btnJoin.setVisibility(View.GONE);
            btnChat.setVisibility(View.GONE);
        }
        else if(isOwnerProject() && !isAvailable()) {
            btnJoin.setVisibility(View.GONE);
            btnChat.setVisibility(View.VISIBLE);
        }
        else if(isProjectManager()) {
            btnJoin.setVisibility(View.GONE);
            btnChat.setVisibility(View.VISIBLE);
        }
        else if(!isProjectManager() && isAvailable()){
            btnJoin.setVisibility(View.VISIBLE);
            btnChat.setVisibility(View.GONE);
        }

        Log.e("TOGGLE", "toggleButtonForUser: " + isOwnerProject() + " " + isAvailable() + " " + isProjectManager() );
    }

    private void checkProjectManager(){

        if(!isAvailable()){
            User user = UserController.getChoosenUser(projectId);
            tvChoosenUser.setText((""+user.getName().charAt(0)).toUpperCase());
            tvChoosenName.setText(user.getName());
            tvChoosenUserRate.setText(String.valueOf(2));
            btnChat.setVisibility(View.VISIBLE);
        }
        else{
            layoutChoosenUser.setVisibility(View.GONE);
        }

    }

    private void checkAlreadyJoinProject(){
        for (User user:joinedUsers) {
            if(user.getId().equals(this.user.getId()))
                btnJoin.setVisibility(View.GONE);
        }
    }

    private void refreshPojectDetail(){
        setProjectDetail();
        setJoinedUser();
        toggleButtonForUser();
        checkProjectManager();
        checkAlreadyJoinProject();
    }

    private void insertProjectDetail(){

        String userId = user.getEmail();
        String projectId = this.projectId;

        ProjectDetail projectDetail = new ProjectDetail("", userId, projectId, "");
        ProjectDetailController.insertProjectDetail(projectDetail);

    }

    private void chooseUserAsManager(final User user){

        final String projectId = this.projectId;

        AlertDialog.Builder alert = new AlertDialog.Builder(ProjectDetailActivity.this);
        alert.setMessage("Are You Sure Choose " + user.getName() + " as Project Manager ? ");
        alert.setCancelable(false);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ProjectDetailActivity.this, "MASUK", Toast.LENGTH_SHORT).show();
                ProjectDetailController.updateProjectDetail(projectId, user.getEmail());
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create().show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnJoin){

            insertProjectDetail();
            joinedUsers.add(user);
            btnJoin.setVisibility(View.GONE);
            refreshPojectDetail();
        }

        if(view == btnChat){
            Toast.makeText(this, "btnChat", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), RoomChatActivity.class);
            //Project project = (Project) listViewProjectAdapter.getItem(position);
            intent.putExtra("projectId", project.getId());
            startActivity(intent);
        }
    }

    private boolean isOwnerProject(){
        return user.getId().equals(ownerProject.getId()) ? true : false;
    }

    private boolean isAvailable(){
        return UserController.getChoosenUser(projectId) == null ? true : false;
    }

    private boolean isProjectManager(){
        if(!isAvailable())
            return UserController.getChoosenUser(projectId).getId().equals(user.getId()) ? true : false;

        return false;
    }

}
