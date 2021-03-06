package al.laefapp.main;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;

import al.laefapp.database.ParticipantContract.Countries;
import framework.AbstractListFragment;


/**
 * Created by Ásta Lovísa on 11.10.2015.
 *
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CountryFragment extends AbstractListFragment {

    private SimpleCursorAdapter simpleCursorAdapter;

    public CountryFragment() {

        CONTENT_URI = Countries.CONTENT_URI;
        PROJECTION = Countries.PROJECTION_ALL;
        SORT_ORDER = Countries.SORT_ORDER_DEFAULT;
        WHERE_CLAUSE = null;

        displayedRows = new String[]{Countries.COUNTRY};
        NEXT_TAB_NUMBER = 1;
    }


}
