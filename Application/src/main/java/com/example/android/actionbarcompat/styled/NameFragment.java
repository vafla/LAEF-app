package com.example.android.actionbarcompat.styled;


import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ListView;

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
        displayParticipantInDialog(getListView().getItemAtPosition(position).toString());

    }

    private void displayParticipantInDialog(/*ParticipantInfo participantInfo*/String participantInfo) {
        final DialogFragment dialog = new DialogFragment();
/*        dialog.setTitle(participantInfo.getName();
        dialog.setContentView(R.layout.dialogbox);
        TextView author = (TextView) dialog.findViewById(R.id.participant_name);*/
/*        final Button closeButton = (Button) dialog
                .findViewById(R.id.close_dialog_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        dialog.show(getFragmentManager(), participantInfo);
    }

}