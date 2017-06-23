package edu.bluejack16_2.cariprojek;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    Button btnRegis;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtName;
    EditText txtAddress;
    EditText txtPhone;

    DatabaseReference databaseUsers;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegis = (Button) findViewById(R.id.btnSubmitRegis);
        txtEmail = (EditText) findViewById(R.id.txtEmailRegis);
        txtPassword = (EditText) findViewById(R.id.txtPasswordRegis);
        txtName = (EditText) findViewById(R.id.txtNameRegis);
        txtAddress = (EditText) findViewById(R.id.txtAddressRegis);
        txtPhone = (EditText) findViewById(R.id.txtPhoneRegis);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email="";
                String password = "";
                String name = "";
                String address = "";
                String phone = "";
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                name = txtName.getText().toString();
                address = txtAddress.getText().toString();
                phone = txtPhone.getText().toString();

                final User user = new User(email,password,name,address,phone);
                isValid(user);

                firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String id = firebaseAuth.getCurrentUser().getUid();
                                    DatabaseReference curr_user =  databaseUsers.child(id);

                                    curr_user.child("email").setValue(user.getEmail());
                                    curr_user.child("name").setValue(user.getName());
                                    curr_user.child("phone").setValue(user.getPhone());
                                    curr_user.child("address").setValue(user.getAddress());

                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
    public  void isValid(User user){
        String err="";
        if(user.getEmail().equals("")){
            err = "email must be filled and valid";
            Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
            return;
        }
        if(user.getPassword().equals("")){
            err="password must be filled";
            Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
            return;
        }
        if(user.getPassword().length()<6){
            err="password must be more than 6 character";
            Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
            return;
        }
        if(user.getName().equals("")){
            err="name must be filled";
            Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
            return;
        }
        if(user.getAddress().equals("")){
            err="address must be filled";
            Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
            return;
        }
        if(user.getPhone().equals("")){
            err="phone must be filled";
            Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
