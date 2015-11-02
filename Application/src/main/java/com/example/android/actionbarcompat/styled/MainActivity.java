/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.actionbarcompat.styled;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import framework.ExcelLoader;


/**
 * Created by Ásta Lovisa.
 *
 * This is the Main Activity class that extends ActionBar Activity.
 * It createes an actionbar with three tabs, each it's own fragment
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    String m_filterItem = null;
    CountryFragment countryFragment;
    OrganisationFragment organisationFragment;
    NameFragment nameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        //String with file name + loading file
        String filename = "mockSheet.xls";
        ExcelLoader excelLoader = new ExcelLoader(filename);
        excelLoader.loadFile(getApplicationContext());


        // Set the Action Bar to use tabs for navigation
        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add three tabs to the Action Bar for display
        ab.addTab(ab.newTab().setText("Country").setTabListener(this));
        ab.addTab(ab.newTab().setText("Organisation").setTabListener(this));
        ab.addTab(ab.newTab().setText("Name").setTabListener(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu from menu resource (res/menu/main)
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a tab is selected.
        switch (tab.getPosition()) {
            case 0:
                countryFragment = new CountryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        countryFragment).commit();
                Log.d("MainActivity", "onTabSelect country");
                break;
            case 1:
                organisationFragment = new OrganisationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        organisationFragment).commit();
                Log.d("MainActivity", "onTabSelect organisation");
                break;
            case 2:
                nameFragment = new NameFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        nameFragment).commit();
                Log.d("MainActivity", "onTabSelect name");
                break;
            default:
                break;
        }
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is unselected.
        //setFilterItem(null);
        switch (tab.getPosition()) {
            case 0:
                getSupportFragmentManager().beginTransaction().remove(
                        countryFragment).commit();
                Log.d("MainActivity", "onTabUnSelect country");

                break;
            case 1:
                getSupportFragmentManager().beginTransaction().remove(
                        organisationFragment).commit();
                Log.d("MainActivity", "onTabUnSelect organisation");

                break;
            case 2:
                getSupportFragmentManager().beginTransaction().remove(
                        nameFragment).commit();
                Log.d("MainActivity", "onTabUnSelect name");
                break;
            default:
                break;
        }
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is selected again.
        Log.d("MainActivity", "onTabReselect");
    }
}
