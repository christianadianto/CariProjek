package edu.bluejack16_2.cariprojek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;

public class RegisterActivity extends AppCompatActivity {


    Button btnRegis;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtName;
    EditText txtAddress;
    EditText txtPhone;

    Session session;

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

        session = new Session(getApplicationContext());

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = "";
                String password = "";
                String name = "";
                String address = "";
                String phone = "";

                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                name = txtName.getText().toString();
                address = txtAddress.getText().toString();
                phone = txtPhone.getText().toString();

                User user = new User("", email, password, name, address, phone);

                if(UserController.validateUser(user).equals("")) {
                    UserController.insertUser(user);
                    user = UserController.getUserByEmail(email);
                    session.setUser(user);

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    String err = UserController.validateUser(user);
                    Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
