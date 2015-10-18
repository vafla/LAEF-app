package com.example.android.actionbarcompat.styled;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import framework.AbstractListFragment;

/**
 * Created by Ásta Lovísa on 11.10.2015.
 */
public class NameFragment extends AbstractListFragment {

    public NameFragment() {

        FRAGMENT_NAME = "Name";
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
    }

}