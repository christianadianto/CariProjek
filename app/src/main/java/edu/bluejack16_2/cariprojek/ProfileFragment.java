package edu.bluejack16_2.cariprojek;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

    FloatingActionButton btnPortofolio;
    FloatingActionButton btnAdd;
    FloatingActionButton btnUpdate;
    FloatingActionButton btnViewPortofolio;

    boolean isClicked;

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
        btnPortofolio = (FloatingActionButton) view.findViewById(R.id.btn_portofolio);
        btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        btnUpdate = (FloatingActionButton) view.findViewById(R.id.btn_update);
        btnViewPortofolio = (FloatingActionButton) view.findViewById(R.id.btn_view);
        //btnViewReview = (Button) view.findViewById(R.id.btnViewReview);

        btnAdd.hide();
        btnUpdate.hide();
        isClicked = false;

        session = new Session(getContext());
        if(session.getSession("userId") == null || session.getSession("userId").equals(""))
            user = session.getUser();
        else{
            String email = session.getSession("userId");
            user = UserController.getUserByEmail(email);
            btnPortofolio.hide();
        }

        tvEmail.setText(user.getEmail());
        tvName.setText(user.getName());
        tvAddress.setText(user.getAddress());
        tvPhone.setText(user.getPhonenumber());

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnPortofolio.setOnClickListener(this);
        btnViewPortofolio.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnPortofolio){
            toggleButton();
        }
        else if(v == btnAdd){
            changeFragment(new CreatePortofolioFragment());
        }
        else if(v == btnUpdate){
            changeFragment(new UpdatePortofolioFragment());
        }
        else if(v == btnViewPortofolio){
            Intent intent = new Intent(getContext(), ViewPortofolioActivity.class);
            startActivity(intent);
        }
    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment).addToBackStack("back");
        ft.commit();
    }

    public void toggleButton(){
        if(isClicked){
            btnAdd.hide();
            btnUpdate.hide();
            isClicked = false;
        }
        else{
            btnUpdate.show();
            btnAdd.show();
            isClicked = true;
        }

    }

}

