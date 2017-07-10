package edu.bluejack16_2.cariprojek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    TextView tvEmail;
    TextView tvName;
    TextView tvAddress;
    TextView tvPhone;

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




        return  view;
    }

}

