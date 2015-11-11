package al.laefapp.main;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


/**
 * Created by lovisa on 10/27/15.
 */
public class InfoDialogFragment extends DialogFragment {
    TextView m_name;
    TextView m_description;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(al.laefapp.main.R.layout.dialogbox);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialog.show();
        Bundle bundle = getArguments();
        if (bundle != null) {
            m_name = (TextView) dialog.findViewById(al.laefapp.main.R.id.participant_name);
            m_name.setText(bundle.getString("name"));
            m_description = (TextView) dialog.findViewById(al.laefapp.main.R.id.description);
            m_description.setText(bundle.getString("description"));
        }
        return dialog;
    }


}
