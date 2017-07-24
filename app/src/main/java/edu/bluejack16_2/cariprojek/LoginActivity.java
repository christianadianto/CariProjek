package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
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

import java.lang.reflect.Array;
import java.util.Arrays;


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

    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreference;

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
            public void onSuccess(final LoginResult loginResult) {
                    //get data here using graph request api
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        Toast.makeText(LoginActivity.this, "masuk nih", Toast.LENGTH_SHORT).show();
                                        facebookEmail = object.getString("email");
                                        facebookName = object.getString("name");
                                        Toast.makeText(LoginActivity.this, facebookEmail, Toast.LENGTH_LONG).show();
                                        Toast.makeText(LoginActivity.this, facebookName, Toast.LENGTH_SHORT).show();

                                            Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(facebookEmail);

                                            query.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.getChildrenCount()!=0) {
                                                        Toast.makeText(LoginActivity.this, "data ada", Toast.LENGTH_SHORT).show();
                                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                            String name = d.child("name").getValue().toString();
                                                            String email = d.child("email").getValue().toString();
                                                            String password = d.child("password").getValue().toString();
                                                            String address = d.child("address").getValue().toString();
                                                            String phone = d.child("phone").getValue().toString();

                                                            SharedPreferences.Editor editor = sharedPreference.edit();

                                                            editor.putString("email", email);
                                                            editor.putString("password", password);
                                                            editor.putString("name", name);
                                                            editor.putString("phone", phone);
                                                            editor.putString("address", address);
                                                            editor.commit();
                                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                    else{
                                                        DatabaseReference new_user = myRef.push();

                                                        new_user.child("email").setValue(facebookEmail);
                                                        new_user.child("password").setValue("");
                                                        new_user.child("name").setValue(facebookName);
                                                        new_user.child("phone").setValue("");
                                                        new_user.child("address").setValue("");

                                                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                        //here
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


    private void checkUser(String userEmail, final String userPassword) {

        Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(userEmail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()!=0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Log.e("find me", d.getKey());
                        String name = d.child("name").getValue().toString();
                        String email = d.child("email").getValue().toString();
                        String password = d.child("password").getValue().toString();
                        String address = d.child("address").getValue().toString();
                        String phone = d.child("phone").getValue().toString();

                        if (password.equals(userPassword)) {

                            SharedPreferences.Editor editor = sharedPreference.edit();

                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putString("name", name);
                            editor.putString("phone", phone);
                            editor.putString("address", address);
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
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

    public void checkUser(GoogleSignInAccount account){
        final String name = account.getDisplayName();
        final String email = account.getEmail();
        final String password = "";
        final String address = "";
        final String phone = "";
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(email);

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

                        SharedPreferences.Editor editor = sharedPreference.edit();

                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putString("name", name);
                        editor.putString("phone", phone);
                        editor.putString("address", address);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    DatabaseReference new_user = myRef.push();

                    new_user.child("email").setValue(email);
                    new_user.child("password").setValue(password);
                    new_user.child("name").setValue(name);
                    new_user.child("phone").setValue(phone);
                    new_user.child("address").setValue(address);

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

            checkUser(account);

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
