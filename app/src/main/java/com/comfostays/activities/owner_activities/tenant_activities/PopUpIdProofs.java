package com.comfostays.activities.owner_activities.tenant_activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class PopUpIdProofs extends Activity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_id_proofs);

        try {
            Intent intent = getIntent();

            String idSource=intent.getStringExtra("IdSource");
            imageView= (ImageView) findViewById(R.id.popUpIdProofs_imageView);

            DownloadImageFromUrl downloadImageFromUrl=new DownloadImageFromUrl(imageView,this);

            downloadImageFromUrl.execute(idSource);

        }catch(Exception e){

        }
    }

    public void onClickClose(View view){

       this.finish();
    }
}
