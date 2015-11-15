package al.laefapp.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import al.laefapp.database.DBBitmapUtility;


/**
 * Created by lovisa on 10/27/15.
 */
public class InfoDialogFragment extends DialogFragment {

    TextView m_description;
    ImageView m_image;
    Button m_button;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(al.laefapp.main.R.layout.dialogbox);

        m_button = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        m_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            dialog.setTitle(bundle.getString("name"));
            m_description = (TextView) dialog.findViewById(R.id.description);
            m_description.setText(bundle.getString("description"));
            Log.d("Info Dialog Fragment", m_description.toString());

            m_image = (ImageView) dialog.findViewById(R.id.image);
            byte[] imageByteArray = bundle.getByteArray("image");
            if (imageByteArray != null) {
                m_image.setImageBitmap(DBBitmapUtility.getImage(imageByteArray));
            } else {
                Log.d("Info Dialog Fragment", "image byte array is null");
                m_image.setImageResource(R.drawable.ic_launcher);
            }

        }
        return dialog;
    }


}
