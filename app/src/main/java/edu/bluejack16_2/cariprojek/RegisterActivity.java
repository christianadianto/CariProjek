package edu.bluejack16_2.cariprojek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    Button btnRegis;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtName;
    EditText txtAddress;
    EditText txtPhone;

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



        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email="";
                String password = "";
                String name = "";
                String address = "";
                String phone = "";
                String err="";
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                name = txtName.getText().toString();
                address = txtAddress.getText().toString();
                phone = txtPhone.getText().toString();

               if(email.equals("")){
                   err = "email must be filled and valid";
                   Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
                   return;
               }
               if(password.equals("")){
                   err="password must be filled";
                   Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
                   return;
               }
               if(name.equals("")){
                   err="name must be filled";
                   Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
                   return;
               }
               if(address.equals("")){
                   err="address must be filled";
                   Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
                   return;
               }
               if(phone.equals("")){
                   err="phone must be filled";
                   Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
                   return;
               }
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
