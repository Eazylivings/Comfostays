package com.comfostays.activities.owner_activities.tenant_activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.comfostays.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DownloadImageFromUrl extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;
    Activity baseActivity;

    public DownloadImageFromUrl(ImageView imageView,Activity baseActivity){

        this.imageView=imageView;
        this.baseActivity=baseActivity;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap map = null;
        for (String url : urls) {
            map = downloadImage(url);
        }
        return map;
    }

    // Sets the Bitmap returned by doInBackground
    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);

        ProgressBar progressBar=(ProgressBar)baseActivity.findViewById(R.id.progressBar_imageLoadingProgress);

        if(progressBar!=null){
            progressBar.setVisibility(View.GONE);
        }

        if(baseActivity.getClass()==TenantsDetailsActivity.class){

            ImageView tenantPic=(ImageView)baseActivity.findViewById(R.id.tenantsDetails_imageView_profilePic);

            if(tenantPic!=null){

                tenantPic.setVisibility(View.VISIBLE);
            }

        }else if(baseActivity.getClass()==PopUpIdProofs.class){

            ImageView tenantPic=(ImageView)baseActivity.findViewById(R.id.popUpIdProofs_imageView);

            if(tenantPic!=null){

                tenantPic.setVisibility(View.VISIBLE);
            }
        }


    }

    // Creates Bitmap from InputStream and returns it
    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }
}
