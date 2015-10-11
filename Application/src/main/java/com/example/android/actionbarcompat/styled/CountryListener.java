package com.example.android.actionbarcompat.styled;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Ásta Lovísa on 11.10.2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CountryListener extends ListFragment {

    private String[] m_countryList;

    public CountryListener() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, m_countryList);
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Country Listener", getListView().getItemAtPosition(position).toString());
        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview, container, false);
    }

    @Override
    public void setArguments(Bundle args) {
        try {
            m_countryList = new String[args.getStringArray("Country").length];
            for (int i = 0; i < args.getStringArray("Country").length;
                 i++) {
                m_countryList[i] = args.getStringArray("Country")[i];
            }
        } catch (Exception e) {
            Log.e("CountryListener", e.toString());
        }
    }


}
