package com.example.android.actionbarcompat.styled;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;

import database.ParticipantContract.Names;
import framework.AbstractListFragment;

/**
 * Created by Ásta Lovísa on 11.10.2015.
 */
public class NameFragment extends AbstractListFragment {

    public NameFragment() {

        CONTENT_URI = Names.CONTENT_URI;
        PROJECTION = Names.PROJECTION_ALL;
        SORT_ORDER = Names.SORT_ORDER_DEFAULT;
        WHERE_CLAUSE = Names.ORGANISATION_ID + "=?";

        displayedRows = new String[]{Names.NAME};
    }


    // Overrides method from AbstractListFragment
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        displayParticipantInfoDialog();

    }

    private void displayParticipantInfoDialog() {
        //Create and show the dialog
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        infoDialogFragment.show(fragmentTransaction, "infodialog");

    }

}