package com.comfostays.databasehandler;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.comfostays.CommonFunctionality;

public class TenantLocalDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "comforStays";
    private static final String KEY_ID = "id";
    Context context;
    Activity activity;

    public TenantLocalDatabaseHandler(Context context, Activity activity) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        this.activity=activity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {


            db.execSQL("");

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "");
        db.execSQL("DROP TABLE IF EXISTS " + "");

        // Create tables again
        onCreate(db);
    }
}
