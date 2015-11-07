package com.example.android.actionbarcompat.styled;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
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
        Log.d("name fragment", "onListItemClick");
        Cursor cursor = ((SimpleCursorAdapter) l.getAdapter()).getCursor();
        cursor.moveToPosition(position);
        displayParticipantInfoDialog(cursor);
        ((MainActivity) getActivity()).setFilterItem(null);


    }

    private void displayParticipantInfoDialog(Cursor cursor) {
        //Create and show the dialog
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        infoDialogFragment.setArguments(createParticipantInfoBundle(cursor));
        infoDialogFragment.show(fragmentTransaction, "infodialog");

    }

    private Bundle createParticipantInfoBundle(Cursor cursor) {
        Bundle bundle = new Bundle();
        bundle.putString("name", cursor.getString(1));
        bundle.putString("description", cursor.getString(2));
        return bundle;
    }

}