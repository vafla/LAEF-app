package com.example.android.actionbarcompat.styled;

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
public class NameFragment extends ListFragment {

    private String[] m_nameList;

    public NameFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, m_nameList);
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Name Fragment", getListView().getItemAtPosition(position).toString());
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
            m_nameList = new String[args.getStringArray("Name").length];
            for (int i = 0; i < args.getStringArray("Name").length;
                 i++) {
                m_nameList[i] = args.getStringArray("Name")[i];
            }
        } catch (Exception e) {
            Log.e("NameFragment", e.toString());
        }
    }


}