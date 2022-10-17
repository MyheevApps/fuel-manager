package com.myheev.fuelmanager.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.myheev.fuelmanager.fragments.ConsumptionFragment;
import com.myheev.fuelmanager.fragments.PricesFragment;
import com.myheev.fuelmanager.R;
import com.myheev.fuelmanager.fragments.TravelCostFragment;
import com.myheev.fuelmanager.viewpager.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new TravelCostFragment(), "Путешествие");
        viewPagerAdapter.addFragment(new ConsumptionFragment(), "Расход");
        viewPagerAdapter.addFragment(new PricesFragment(), "Цена");
        viewPager.setAdapter(viewPagerAdapter);
    }
}