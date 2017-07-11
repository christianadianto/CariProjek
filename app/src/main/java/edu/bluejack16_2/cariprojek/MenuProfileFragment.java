package edu.bluejack16_2.cariprojek;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuProfileFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager viewPager;

    public MenuProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_profile, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabProfileLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewProfilePager);

        ViewProfilePagerAdapter viewProfilePagerAdapter = new ViewProfilePagerAdapter(getChildFragmentManager());
        viewProfilePagerAdapter.addFragmentandTitle(new ProfileFragment(),"My Profile");
        viewProfilePagerAdapter.addFragmentandTitle(new ProfileCreateProjectFragment(),"Created Project");
        viewProfilePagerAdapter.addFragmentandTitle(new ProfileWorkProjectFragment(),"Worked Project");
//        ViewProfilePagerAdapter viewPagerAdapter = new ViewProfilePagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.addFragmentandTitle(new ProfileCreateProjectFragment(),"Created Project");
//        viewPagerAdapter.addFragmentandTitle(new ProfileWorkProjectFragment(),"Worked Project");

        viewPager.setAdapter(viewProfilePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Menu Profile");
    }
}
