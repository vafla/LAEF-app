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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import framework.ExcelLoader;
import framework.ParticipantInfo;

/**
 * This sample shows you how to use ActionBarCompat with a customized theme. It utilizes a split
 * action bar when running on a device with a narrow display, and show three tabs.
 *
 * This Activity extends from {@link ActionBarActivity}, which provides all of the function
 * necessary to display a compatible Action Bar on devices running Android v2.1+.
 *
 * The interesting bits of this sample start in the theme files
 * ('res/values/styles.xml' and 'res/values-v14</styles.xml').
 *
 * Many of the drawables used in this sample were generated with the
 * 'Android Action Bar Style Generator': http://jgilfelt.github.io/android-actionbarstylegenerator
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    List<ParticipantInfo> m_participantInfoList;
    String m_filterItem = null;
    CountryFragment countryFragment = new CountryFragment();
    OrganisationFragment organisationFragment = new OrganisationFragment();
    NameFragment nameFragment = new NameFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        //String with file name + loading file
        String filename = "mockSheet.xls";
        m_participantInfoList = new ArrayList<ParticipantInfo>();
        ExcelLoader excelLoader = new ExcelLoader(filename);
        excelLoader.loadFile(getApplicationContext(), m_participantInfoList);

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
                countryFragment.setArguments(ContentHandlerFactory("Country"));

                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        countryFragment).commit();
                Log.d("MainActivity", "onTabSelect country");

                break;
            case 1:
                organisationFragment.setArguments(ContentHandlerFactory("Organisation"));
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        organisationFragment).commit();
                Log.d("MainActivity", "onTabSelect organisation");

                break;
            case 2:
                nameFragment.setArguments(ContentHandlerFactory("Name"));

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
        Log.d("MainActivity", "onTabUnselected");
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is selected again.
        Log.d("MainActivity", "onTabReselect");
        setFilterItem(null);
    }

    private Bundle ContentHandlerFactory(String key) {
        Bundle contentBundle = new Bundle();
        String[] stringArray = new String[m_participantInfoList.size()];
        for (int i = 0; i < m_participantInfoList.size(); i++) {
            if (key == "Country") {
                stringArray[i] = m_participantInfoList.get(i).getCountry();
            } else if (key == "Organisation") {
                if (m_filterItem != null) {

                    if (m_participantInfoList.get(i).getCountry() == m_filterItem) {
                        Log.d("MainActivity", m_participantInfoList.get(i).getOrganisation());
                        stringArray[i] = m_participantInfoList.get(i).getOrganisation();
                    }
                } else {
                    stringArray[i] = m_participantInfoList.get(i).getOrganisation();
                }
            } else if (key == "Name") {
                if (m_filterItem != null) {
                    if (m_participantInfoList.get(i).getOrganisation() == m_filterItem) {
                        stringArray[i] = m_participantInfoList.get(i).getName();
                    }
                } else {
                    stringArray[i] = m_participantInfoList.get(i).getName();
                }
            }

        }
        Set<String> temp = new LinkedHashSet<String>(Arrays.asList(stringArray));
        String[] result = temp.toArray(new String[temp.size()]);
        contentBundle.putStringArray(key, result);
        return contentBundle;
    }

    public void setFilterItem(String filterItem) {
        Log.d("MainActivity", filterItem);
        m_filterItem = filterItem;
    }
}
