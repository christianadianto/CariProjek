package edu.bluejack16_2.cariprojek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    LoginButton loginButton;
    CallbackManager callbackManager;
    GoogleApiClient googleApiClient;
    SignInButton signInButton;
    Button btnLogin;
    EditText txtEmail;
    EditText txtPassword;
    TextView tvRegister;

    static final int REQ_CODE = 9001;

    String facebookEmail;
    String facebookName;

    String gmailEmail;
    String gmailName;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile", "user_friends"));
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);

        session = new Session(getApplicationContext());

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

        UserController.getInstance();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

                if(checkUser(email,password)){
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        //facebook
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                    //get data_project here using graph request api
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        facebookEmail = object.getString("email");
                                        facebookName = object.getString("name");
                                        User user;

                                        if(UserController.getUserByEmail(facebookEmail) != null){
                                            user = UserController.getUserByEmail(facebookEmail);
                                        }
                                        else{
                                            user = new User("", facebookEmail, "", facebookName, "", "");
                                            UserController.insertUser(user);
                                        }
                                        session.setUser(user);

                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,name");
                request.setParameters(parameters);
                request.executeAsync();

                LoginManager.getInstance().logOut();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }

        });

    }

    private boolean checkUser(String userEmail, final String userPassword) {

        if(UserController.userAuth(userEmail, userPassword)){
            User user = UserController.getUserByEmail(userEmail);
            session.setUser(user);
            return true;
        }
        else{
            Toast.makeText(this, "Email Or Password Invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void checkUser(GoogleSignInAccount account){
        gmailName = account.getDisplayName();
        gmailEmail = account.getEmail();

        User user;

        if(UserController.getUserByEmail(gmailEmail) != null){
            user = UserController.getUserByEmail(gmailEmail);
        }
        else{
            user = new User("", gmailEmail, "", gmailName, "", "");
            UserController.insertUser(user);
        }
        session.setUser(user);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        //finish();

    }

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
            checkUser(account);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
