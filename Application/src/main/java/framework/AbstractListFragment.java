package framework;

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

import com.example.android.actionbarcompat.styled.R;

/**
 * Created by lovisa on 10/16/15.
 */
public abstract class AbstractListFragment extends ListFragment {

    protected String[] m_list;

    protected String FRAGMENT_NAME;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, m_list);
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
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
            m_list = new String[args.getStringArray(FRAGMENT_NAME).length];
            for (int i = 0; i < args.getStringArray(FRAGMENT_NAME).length;
                 i++) {
                m_list[i] = args.getStringArray(FRAGMENT_NAME)[i];
            }
        } catch (Exception e) {
            Log.e(FRAGMENT_NAME, e.toString());
        }
    }
}