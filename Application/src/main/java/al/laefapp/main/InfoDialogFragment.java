package al.laefapp.main;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import al.laefapp.database.DBBitmapUtility;


/**
 * Created by lovisa on 10/27/15.
 */
public class InfoDialogFragment extends DialogFragment {

    TextView m_description;
    ImageView imageView;
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

            imageView = (ImageView) dialog.findViewById(R.id.image);
            byte[] imageByteArray = bundle.getByteArray("image");
            if (imageByteArray != null) {
                Bitmap image = DBBitmapUtility.getImage(imageByteArray);
                float scalingFactor = getBitmapScalingFactor(image, imageView);
                imageView.setImageBitmap(scaleBitmap(image, scalingFactor));
            } else {
                Log.d("Info Dialog Fragment", "image byte array is null");
                imageView.setImageResource(R.drawable.ic_launcher);
            }

        }
        return dialog;
    }

    private Bitmap scaleBitmap(Bitmap bm, float scalingFactor) {
        int scalingHeight = (int) (bm.getHeight() * scalingFactor);
        int scalingWidth = (int) (bm.getWidth() * scalingFactor);
        return Bitmap.createScaledBitmap(bm, scalingWidth, scalingHeight, true);
    }


    private float getBitmapScalingFactor(Bitmap bm, ImageView imageView) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthpxl = displayMetrics.widthPixels;
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) imageView.getLayoutParams();

        int leftmargin = layoutParams.leftMargin;
        int rightmargin = layoutParams.rightMargin;

        int imageMaxWidth = widthpxl / 2 - (leftmargin + rightmargin);
        // Calculate scaling factor and return it
        return ((float) imageMaxWidth / (float) bm.getWidth());
    }



}
