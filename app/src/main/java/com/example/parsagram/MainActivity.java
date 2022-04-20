package com.example.parsagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.parsagram.fragments.HomeFragment;
import com.example.parsagram.fragments.ShirtFragment;
import com.example.parsagram.fragments.UserFragment;
import com.example.parsagram.fragments.WardrobeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();

    final Fragment userFragment = new UserFragment();
    final Fragment wardrobeFragment = new WardrobeFragment();
    final Fragment homeFragment = new HomeFragment();
    final Fragment shirtFragment = new ShirtFragment();


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);


        //queryPosts();


        bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragment = homeFragment;
                                break;
                            case R.id.action_compose:
                                fragment = wardrobeFragment;
                                break;
                            case R.id.action_profile:
                            default:
                                fragment = userFragment;
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        return true;
                    }
                });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}