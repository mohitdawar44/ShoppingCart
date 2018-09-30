package com.shopit.shopit.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shopit.shopit.MainActivity;
import com.shopit.shopit.R;
import com.shopit.shopit.fragments.DisplayItemsFragment;
import com.shopit.shopit.fragments.SettingsFragment;
import com.shopit.shopit.login.LoginActivity;

/**
 *
 * Created by mohit on 6/1/18.
 */

public class Navigator extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.user_loggedin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(Navigator.this, LoginActivity.class));
                    finish();
                }
            }
        };

        if (auth.getCurrentUser() != null) {
            userEmail.setText(auth.getCurrentUser().getEmail());
        }
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransactionUtils.changeFragment(this,new DisplayItemsFragment(),R.id.navigated_frame);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (id){
            case R.id.settings : //FragmentTransactionUtils.changeFragment(this,new SettingsFragment(),R.id.navigated_frame);
                break;
            case R.id.categories :FragmentTransactionUtils.changeFragment(this,new DisplayItemsFragment(),R.id.navigated_frame);
                break;
            case R.id.signout : auth.signOut();
                break;
        }


        return true;
    }
}
