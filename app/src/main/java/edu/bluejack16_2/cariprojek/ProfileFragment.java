package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    TextView tvEmail;
    TextView tvName;
    TextView tvAddress;
    TextView tvPhone;
    Button btnViewPortofolio;
    Button btnViewReview;
    Session session;
    User user;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvEmail = (TextView) view.findViewById(R.id.tvEmailProfileContent);
        tvName = (TextView) view.findViewById(R.id.tvNameProfileContent);
        tvAddress = (TextView) view.findViewById(R.id.tvAddressProfileContent);
        tvPhone = (TextView) view.findViewById(R.id.tvPhoneProfileContent);
        btnViewPortofolio = (Button) view.findViewById(R.id.btnViewPortofolio);
        btnViewReview = (Button) view.findViewById(R.id.btnViewReview);

        session = new Session(getContext());
        if(session.getSession("userId") == null || session.getSession("userId").equals(""))
            user = session.getUser();
        else{
            String email = session.getSession("userId");
            user = UserController.getUserByEmail(email);
        }

        tvEmail.setText(user.getEmail());
        tvName.setText(user.getName());
        tvAddress.setText(user.getAddress());
        tvPhone.setText(user.getPhonenumber());

        btnViewReview.setOnClickListener(this);
        btnViewPortofolio.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnViewReview){
            Toast.makeText(getContext(), "review", Toast.LENGTH_SHORT).show();
        }
        else if(v == btnViewPortofolio){
            Intent intent = new Intent(getContext(),ViewPortofolioActivity.class);
            startActivity(intent);
        }
    }
}

