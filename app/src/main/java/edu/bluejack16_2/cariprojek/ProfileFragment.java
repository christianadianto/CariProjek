package edu.bluejack16_2.cariprojek;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.bluejack16_2.cariprojek.Controllers.UserController;
import edu.bluejack16_2.cariprojek.Models.User;
import edu.bluejack16_2.cariprojek.Utilities.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView tvEmail;
    TextView tvName;
    TextView tvAddress;
    TextView tvPhone;
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

        return  view;
    }

}

