package com.example.thirdpractice;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.example.thirdpractice.fragments.BrandsFragment;
import com.example.thirdpractice.fragments.CarsFragment;
import com.example.thirdpractice.fragments.MainSettingsFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_drawer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initDrawer();
    }

    private void initDrawer() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_brands) {
                fragment = new BrandsFragment();
                setTitle("Марки автомобилей");
            } else if (itemId == R.id.nav_cars) {
                fragment = new CarsFragment();
                setTitle("Модели и машины");
            } else if (itemId == R.id.nav_settings) {
                fragment = new MainSettingsFragment();
                setTitle("Настройки");
            }

            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // Load default fragment
        Fragment defaultFragment = new BrandsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, defaultFragment)
                .commit();
        navigationView.setCheckedItem(R.id.nav_brands);
        setTitle("Марки автомобилей");
    }
}