package com.comfostays.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.comfostays.R;

public class PopUpIdProofs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_id_proofs);

        Intent intent=getIntent();

        int id=intent.getIntExtra("IDs",0);


        ImageView imageView=(ImageView)findViewById(R.id.popUpIdProofs_imageView);

        if(imageView!=null){
            imageView.setImageResource(id);
        }
    }

    public void onClickClose(View view){

       this.finish();
    }
}
