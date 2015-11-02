package framework;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.actionbarcompat.styled.MainActivity;
import com.example.android.actionbarcompat.styled.R;


/**
 * Created by lovisa on 10/16/15.
 */
public abstract class AbstractListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    protected Uri CONTENT_URI;
    protected String[] PROJECTION;
    protected String SORT_ORDER;

    protected String[] displayedRows;

    protected int NEXT_TAB_NUMBER;

    private SimpleCursorAdapter simpleCursorAdapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        int[] bindingFields = {android.R.id.text1};

        // Create a sumple cursor adapter and set it to display
        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, null, displayedRows, bindingFields, 0);

        setListAdapter(simpleCursorAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("AbstractFragment", "onListItemClick");
        ActionBar.Tab tab = ((MainActivity) getActivity()).getSupportActionBar().getTabAt(NEXT_TAB_NUMBER);
        ((MainActivity) getActivity()).getSupportActionBar().selectTab(tab);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), CONTENT_URI, PROJECTION, null, null, SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (getListAdapter() != null) {
            ((SimpleCursorAdapter) this.getListAdapter()).swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleCursorAdapter) this.getListAdapter()).swapCursor(null);
    }
}
