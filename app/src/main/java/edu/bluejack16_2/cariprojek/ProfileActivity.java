package edu.bluejack16_2.cariprojek;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tabLayout = (TabLayout) findViewById(R.id.tabProfileLayout);
        viewPager = (ViewPager) findViewById(R.id.viewProfilePager);
        ViewProfilePagerAdapter viewPagerAdapter = new ViewProfilePagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragmentandTitle(new ProfileCreateProjectFragment(),"Created Project");
        viewPagerAdapter.addFragmentandTitle(new ProfileWorkProjectFragment(),"Worked Project");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
