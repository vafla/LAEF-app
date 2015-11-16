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

package al.laefapp.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import al.laefapp.database.ParticipantContract;
import framework.ExcelLoader;
import framework.FileChooser;


/**
 * Created by Ásta Lovisa.
 *
 * This is the Main Activity class that extends ActionBar Activity.
 * It createes an actionbar with three tabs, each it's own fragment
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, FileChooser.FileSelectedListener {

    String m_filterItem = null;
    CountryFragment countryFragment;
    OrganisationFragment organisationFragment;
    NameFragment nameFragment;

    public static final String PREFS_NAME = "LaefAPP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(al.laefapp.main.R.layout.mainactivity);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);
        if (!dialogShown) {
            FileChooser fileChooser = new FileChooser(this).setFileListener(this);
            fileChooser.showDialog();

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("dialogShown", true);
            editor.commit();
        }

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
        getMenuInflater().inflate(al.laefapp.main.R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                FileChooser fileChooser = new FileChooser(this).setFileListener(this);
                fileChooser.showDialog();
                return true;
            case R.id.menu_delete:
                new AlertDialog.Builder(this).setTitle("Delete Database")
                        .setMessage("Are you sure you want to delete the database?")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCurrentDatabase();
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            default:
                return false;
        }
    }
    // Implemented from ActionBar.TabListener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a tab is selected.
        switch (tab.getPosition()) {
            case 0:
                countryFragment = new CountryFragment();
                setFilterItem(null);
                getSupportFragmentManager().beginTransaction().replace(al.laefapp.main.R.id.container,
                        countryFragment).commit();
                Log.d("MainActivity", "onTabSelect country");
                break;
            case 1:
                organisationFragment = new OrganisationFragment();
                organisationFragment.setArguments(createQueryBundle());
                getSupportFragmentManager().beginTransaction().replace(al.laefapp.main.R.id.container,
                        organisationFragment).commit();
                Log.d("MainActivity", "onTabSelect organisation");
                break;
            case 2:
                nameFragment = new NameFragment();
                nameFragment.setArguments(createQueryBundle());
                getSupportFragmentManager().beginTransaction().replace(al.laefapp.main.R.id.container,
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
                setFilterItem(null);
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

    public String getFilterItem() {
        return m_filterItem;
    }

    public void setFilterItem(String filterItem) {
        m_filterItem = filterItem;
    }

    private Bundle createQueryBundle() {

        if (m_filterItem != null) {
            Bundle returnBundle = new Bundle();
            returnBundle.putStringArray("filterid", new String[]{m_filterItem});
            return returnBundle;
        }
        return null;

    }

    @Override
    public void fileSelected(File file) {
        deleteCurrentDatabase();
        String filename = file.getPath();
        Log.d("MainActivity", filename);
        ExcelLoader excelLoader = new ExcelLoader(filename);
        excelLoader.loadFile(getApplicationContext());

    }

    private void deleteCurrentDatabase() {
        try {
            getContentResolver().delete(ParticipantContract.Countries.CONTENT_URI, null, null);
            getContentResolver().delete(ParticipantContract.Organisation.CONTENT_URI, null, null);
            getContentResolver().delete(ParticipantContract.Names.CONTENT_URI, null, null);
        } catch (Exception e) {
            Log.e("Delete Database failed", e.toString());
        }

    }

}
