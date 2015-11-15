package framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lovisa on 11/15/15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return image;
        } catch (MalformedURLException e) {
            Log.e("Bitmap", e.toString());
            return null;
        } catch (IOException io) {
            Log.e("Bitmap", io.toString());
            return null;
        } catch (Exception e) {
            Log.e("Bitmap", e.toString());
            return null;
        }
    }
}
