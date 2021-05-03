package com.enerdia.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    PrecioFragment precioFragment = new PrecioFragment();
    HomeFragment homeFragment = new HomeFragment();
    GeneracionFragment generacionFragment = new GeneracionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(homeFragment);

    }

    private final  BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch(item.getItemId()){

                case R.id.precioFragment:
                    loadFragment(precioFragment);
                    return true;
                case R.id.homeFragment:
                    loadFragment(homeFragment);
                    return true;
                case R.id.generacionFragment:
                    loadFragment(generacionFragment);
                    return true;
            }
            return false;
        }
    };

    //este método recibe el fragmento y reempplaza lo que había para visualizar el nuevo fragmento

    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

}