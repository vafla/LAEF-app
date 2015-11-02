package com.example.android.actionbarcompat.styled;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


/**
 * Created by lovisa on 10/27/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class InfoDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage("Zupp")
                .setTitle("Name")
                .setCancelable(true);

        return alertDialogBuilder.create();

    }


}
