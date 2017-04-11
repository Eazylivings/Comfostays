package com.comfostays.databasehandler;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.comfostays.CommonFunctionality;
import com.comfostays.VOClass.EventsVO;

import java.util.ArrayList;

public class CustomerLocalDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "comforStays";
    private static final String KEY_ID = "id";
    Context context;
    Activity activity;

    private static final String TABLE_USER_DETAILS = "user_details";
    private static final String KEY_SERVER_ENTRY_ID="serverEntryId";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_EMAIL_ADDRESS = "email_address";
    private static final String KEY_CONTACT_NUMBER = "contact_number";
    private static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_ACCOUNT_TYPE = "account_type";
    private static final String KEY_REGISTRATION_DATE="registration_date";

    private static final String TABLE_PROPERTY_DETAILS="property_details";
    private static final String KEY_PROPERTY_ID="property_id";
    private static final String KEY_PROPERTY_NAME="property_name";
    private static final String KEY_ADDRESS_LINE_ONE="address_line_one";
    private static final String KEY_ADDRESS_LINE_TWO="address_line_two";
    private static final String KEY_POSTAL_CODE="postal_code";
    private static final String KEY_STATE="state";
    private static final String KEY_GEO_LOCATION="geo_location";
    private static final String KEY_PROPERTY_TYPE="property_type";
    private static final String KEY_IS_MEAL_OFFERED="is_meal_offered";
    private static final String KEY_IS_MEAL_SCHEDULE_AVAILABLE="is_meal_schedule_available";
    private static final String KEY_FACILITIES_PROVIDED="facilities_provided";
    private static final String KEY_ROOM_TYPES="room_types";
    private static final String KEY_FURNISHED_FLAT_ITEMS="furnished_flat_items";

    private static final String TABLE_TENANT_DETAILS="tenant_details";
    private static final String KEY_START_DATE="start_date";
    private static final String KEY_IS_RENT_PAID="is_rent_paid";
    private static final String KEY_IS_APPROVED="is_approved";
    private static final String KEY_STAY_FREQUENCY="stay_frequency";
    private static final String KEY_ID_PROOFS="id_proofs_source";
    private static final String KEY_ID_PROOFS_TYPES="id_proof_types";
    private static final String KEY_PROFILE_PIC="profile_pic_source";
    private static final String KEY_PROFESSION="profession";
    private static final String KEY_ROOM_NUMBER="room_number";
    private static final String KEY_COMPLAINTS_LOGGED="complaints_logged";
    private static final String KEY_NUMBER_OF_OCCUPANCY="occupancy";

    private static final String TABLE_PROPERTY_LAYOUT_DETAILS="property_layout_details";
    private static final String KEY_ROOM_TYPE="room_type";
    private static final String KEY_FLOOR_NUMBER="floor_number";

    private static final String TABLE_COST_AND_CHARGES="cost_and_charges";
    private static final String KEY_CHARGES="charges";
    private static final String KEY_DURATION="floor_number";
    private static final String KEY_CHARGE_TYPE="charge_type";

    private static final String TABLE_MEAL_SCHEDULE="meal_timing_and_schedule";
    private static final String KEY_BREAKFAST_FROM_TIME="breakfast_from_time";
    private static final String KEY_BREAKFAST_TO_TIME="breakfast_to_time";
    private static final String KEY_LUNCH_FROM_TIME="lunch_from_time";
    private static final String KEY_LUNCH_TO_TIME="lunch_to_time";
    private static final String KEY_DINNER_FROM_TIME="dinner_from_time";
    private static final String KEY_DINNER_TO_TIME="dinner_to_time";
    private static final String KEY_MONDAY_MEALS="monday_meals";
    private static final String KEY_TUESDAY_MEALS="tuesday_meals";
    private static final String KEY_WEDNESDAY_MEALS="wednesday_meals";
    private static final String KEY_THURSDAY_MEALS="thursday_meals";
    private static final String KEY_FRIDAY_MEALS="friday_meals";
    private static final String KEY_SATURDAY_MEALS="saturday_meals";
    private static final String KEY_SUNDAY_MEALS="sunday_meals";

    private static final String TABLE_EVENT_DETAILS="table_event_details";

    public CustomerLocalDatabaseHandler(Context context, Activity activity) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        this.activity=activity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            String CREATE_USER_DETAILS_TABLE = "CREATE TABLE " + TABLE_USER_DETAILS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SERVER_ENTRY_ID + " INTEGER," + KEY_PROPERTY_ID + " INTEGER," + KEY_USER_NAME + " TEXT," + KEY_EMAIL_ADDRESS + " TEXT ," + KEY_CONTACT_NUMBER
                    + " TEXT," + KEY_DATE_OF_BIRTH + " TEXT," + KEY_GENDER + " TEXT,"+ KEY_ACCOUNT_TYPE + " TEXT," + KEY_REGISTRATION_DATE + " TEXT)";

            String CREATE_PROPERTY_DETAILS_TABLE = "CREATE TABLE " + TABLE_PROPERTY_DETAILS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROPERTY_ID + " INTEGER," + KEY_PROPERTY_NAME + " TEXT," + KEY_ADDRESS_LINE_ONE + " TEXT," + KEY_ADDRESS_LINE_TWO
                    + " TEXT," + KEY_POSTAL_CODE + " TEXT," + KEY_STATE + " TEXT," + KEY_GEO_LOCATION + " TEXT," + KEY_PROPERTY_TYPE + " TEXT," + KEY_IS_MEAL_OFFERED
                    + " TEXT," + KEY_IS_MEAL_SCHEDULE_AVAILABLE + " TEXT," + KEY_FACILITIES_PROVIDED + " TEXT," + KEY_ROOM_TYPES + " TEXT,"+ KEY_FURNISHED_FLAT_ITEMS + " TEXT)";

            String CREATE_MEAL_SCHEDULE= "CREATE TABLE " + TABLE_MEAL_SCHEDULE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROPERTY_ID + " INTEGER," + KEY_BREAKFAST_FROM_TIME + " TEXT," + KEY_BREAKFAST_TO_TIME + " TEXT," + KEY_LUNCH_FROM_TIME
                    + " TEXT,"+ KEY_LUNCH_TO_TIME + " TEXT,"+ KEY_DINNER_FROM_TIME + " TEXT,"+ KEY_DINNER_TO_TIME + " TEXT,"+ KEY_MONDAY_MEALS + " TEXT,"
                    + KEY_TUESDAY_MEALS + " TEXT,"+ KEY_WEDNESDAY_MEALS + " TEXT,"+ KEY_THURSDAY_MEALS + " TEXT,"+ KEY_FRIDAY_MEALS + " TEXT,"+ KEY_SATURDAY_MEALS + " TEXT,"
                    + KEY_SUNDAY_MEALS + " TEXT)";

            String CREATE_EVENT_TABLE="CREATE TABLE " + TABLE_EVENT_DETAILS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SERVER_ENTRY_ID + " INTEGER," + KEY_PROPERTY_ID + " INTEGER," + KEY_USER_NAME + " TEXT," + KEY_EMAIL_ADDRESS + " TEXT ," + KEY_CONTACT_NUMBER
                    + " TEXT," + KEY_DATE_OF_BIRTH + " TEXT," + KEY_GENDER + " TEXT,"+ KEY_ACCOUNT_TYPE + " TEXT," + KEY_REGISTRATION_DATE + " TEXT)";

            db.execSQL(CREATE_USER_DETAILS_TABLE);
            db.execSQL(CREATE_PROPERTY_DETAILS_TABLE);
            db.execSQL(CREATE_MEAL_SCHEDULE);
            db.execSQL(CREATE_EVENT_TABLE);


        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    public ArrayList<EventsVO> getListOfEventsVO(int propertyId){

        ArrayList<EventsVO> listOfEventsVO=new ArrayList<>();


        return listOfEventsVO;

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
