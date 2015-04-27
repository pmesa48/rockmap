package mundo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pablomesa on 16/04/15.
 */
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    /**
     * url de la imagen que se desea descargar
     */
    private String url;

    /**
     * ImageView donde se desea ubicar la imagen
     */
    private ImageView imageView;

    /**
     * Bitmap de la imagen a descargar
     */
    private Bitmap myBitmap;

    /**
     * Imagen escalada
     */
    private Bitmap scaled;

    /**
     * Cosntructor del ImageLoadTask
     * @param url url de la imagen a descargar
     * @param imageView imagview donde se ubica la imagen
     * @param bm1 bitmap de la imagen
     * @param bm2 bitmap de la imagen escalada
     */
    public ImageLoadTask(String url, ImageView imageView, Bitmap bm1, Bitmap bm2) {
        this.url = url;
        this.imageView = imageView;
        myBitmap = bm1;
        scaled = bm2;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
            int nh = (int) ( myBitmap.getHeight() * (512.0 / myBitmap.getWidth()) );
            scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);


            return scaled;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}