package com.shopit.shopit.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mohit on 12/11/16.
 */
public class FragmentTransactionUtils extends FragmentActivity {

    private static FragmentManager mFragmentManager;
    private static FragmentTransaction fragmentTransaction;

    public static void changeFragment(AppCompatActivity appCompatActivity, Fragment fragment, int containerId)
    {
        mFragmentManager = appCompatActivity.getSupportFragmentManager();
        fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId,fragment);
        fragmentTransaction.commit();
    }

}
