package com.example.android.actionbarcompat.styled;

import database.ParticipantContract.Organisation;
import framework.AbstractListFragment;

/**
 * Created by Ásta Lovísa on 11.10.2015.
 */
public class OrganisationFragment extends AbstractListFragment {

    public OrganisationFragment() {
        CONTENT_URI = Organisation.CONTENT_URI;
        PROJECTION = Organisation.PROJECTION_ALL;
        SORT_ORDER = Organisation.SORT_ORDER_DEFAULT;
        WHERE_CLAUSE = Organisation.COUNTRY_ID + "=?";

        displayedRows = new String[]{Organisation.ORGANISATION};
        NEXT_TAB_NUMBER = 2;
    }




}


