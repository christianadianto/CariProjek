package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    LoginButton loginButton;
    CallbackManager callbackManager;
    GoogleApiClient googleApiClient;
    SignInButton signInButton;
    Button btnLogin;
    EditText txtEmail;
    EditText txtPassword;
    static final int REQ_CODE = 9001;
    String facebookEmail;
    String facebookPassword;
    String facebookAddress;
    String facebookPhone;
    String facebookName;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseUsers;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");


        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        sharedPreference = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        //login google
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,REQ_CODE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email="";
                String password = "";
                String err="";
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();

                if(email.equals("")){
                    err = "email must be filled and valid";
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    err="password must be filled";
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                    return;
                }
               checkUser(email,password);
            }
        });



        //facebook
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final Profile profile = Profile.getCurrentProfile();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Toast.makeText(LoginActivity.this, "login sukses", Toast.LENGTH_SHORT).show();
                                    facebookEmail = object.getString("email");
                                    facebookName = object.getString("name");
                                    facebookAddress = object.getString("address");
                                    facebookAddress = object.getString("phone");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(facebookEmail);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(LoginActivity.this, "checking email", Toast.LENGTH_SHORT).show();
                        if (dataSnapshot.getChildrenCount()==0){
                            registerUser();
                        }
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                LoginManager.getInstance().logOut();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registerUser() {

    }

    private void checkUser(String userEmail, final String userPassword) {

        Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(userEmail);
        final User user = new User();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        String name = d.child("name").getValue().toString();
                        String email = d.child("email").getValue().toString();
                        String password = d.child("password").getValue().toString();
                        String address = d.child("address").getValue().toString();
                        String phone = d.child("phone").getValue().toString();

                        if (password.equals(userPassword)) {

                            SharedPreferences.Editor editor = sharedPreference.edit();

                            editor.putString("email", user.getEmail());
                            editor.putString("password", user.getPassword());
                            editor.putString("name", user.getName());
                            editor.putString("phone", user.getPhone());
                            editor.putString("address", user.getAddress());
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "You need to register first", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveData(User user){

    }

    //facebook ?
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
