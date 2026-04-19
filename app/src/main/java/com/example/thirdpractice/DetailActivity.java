package com.example.thirdpractice;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.thirdpractice.fragments.GarageFragment;
import com.example.thirdpractice.fragments.FavoritesFragment;
import com.example.thirdpractice.fragments.DetailSettingsFragment;

public class DetailActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
    private String brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        brandName = getIntent().getStringExtra("brandName");
        
        initToolbar();
        initBottomNavigation();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Марка: " + brandName);
        }
    }

    private void initBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_garage) {
                fragment = new GarageFragment();
                setTitle("Мой гараж - " + brandName);
            } else if (itemId == R.id.nav_favorites) {
                fragment = new FavoritesFragment();
                setTitle("Избранное - " + brandName);
            } else if (itemId == R.id.nav_detail_settings) {
                fragment = new DetailSettingsFragment();
                setTitle("Параметры - " + brandName);
            }

            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.detail_fragment_container, fragment);
                transaction.commit();
            }

            return true;
        });

        // Load default fragment
        Fragment defaultFragment = new GarageFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_fragment_container, defaultFragment)
                .commit();
        bottomNavigation.setSelectedItemId(R.id.nav_garage);
        setTitle("Мой гараж - " + brandName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
