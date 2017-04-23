package com.comfostays.activities.owner_activities.tenant_activities;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class PopUpIdProofs extends Activity{

    ImageView imageView;
    String idSource;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_id_proofs);

        try {
            Intent intent = getIntent();

            idSource=intent.getStringExtra("IdSource");
            imageView= (ImageView) findViewById(R.id.popUpIdProofs_imageView);

            Picasso.with(this)
                    .load(idSource)
                    .placeholder(R.drawable.id_proof_default)
                    .error(R.drawable.error_image)
                    .into(imageView);

        }catch(Exception e){

        }
    }

    public void onClickClose(View view){

       this.finish();
    }

    //http://stackoverflow.com/questions/32799353/saving-image-from-url-using-picasso

    public void onClickDownload(View view){

        try {

            DownloadImaeFromUrl.downLoad(this, getApplicationContext(), "http://photos.filmibeat.com/ph-big/2011/09/1315938300381714.jpg");


            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File myImageFile = new File(directory, "my_image.jpeg");
            Picasso.with(this).load(myImageFile).into(imageView);

        /*Picasso.with(this)
                .load(idSource)
                .into(getTarget("IdProof"));*/

        }catch(Exception e){

            int f=0;
        }
    }

    //target to save
    private static Target getTarget(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
}
