package com.comfostays.databasehandler;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.VOClass.PropertyLayoutDetailsVO;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.VOClass.UserDetailsVO;
import com.comfostays.sharedpreference.SharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OwnerLocalDatabaseHandler extends SQLiteOpenHelper {

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


    public OwnerLocalDatabaseHandler(Context context, Activity activity) {
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

            String CREATE_COST_AND_CHARGES_TABLE="CREATE TABLE " + TABLE_COST_AND_CHARGES + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROPERTY_ID + " INTEGER," + KEY_ROOM_TYPE + " TEXT," + KEY_CHARGES + " TEXT," + KEY_DURATION
                    + " TEXT," + KEY_CHARGE_TYPE  + " TEXT)";

            String CREATE_PROPERTY_LAYOUT_DETAILS = "CREATE TABLE " + TABLE_PROPERTY_LAYOUT_DETAILS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROPERTY_ID + " INTEGER," + KEY_FLOOR_NUMBER + " TEXT," + KEY_ROOM_NUMBER + " TEXT," + KEY_NUMBER_OF_OCCUPANCY + " INTEGER,"+ KEY_ROOM_TYPE + " TEXT)";

            String CREATE_CURRENT_TENANT_DETAILS = "CREATE TABLE " + TABLE_TENANT_DETAILS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROPERTY_ID + " INTEGER," + KEY_USER_NAME + " TEXT," + KEY_EMAIL_ADDRESS + " TEXT ," + KEY_CONTACT_NUMBER
                    + " TEXT," + KEY_DATE_OF_BIRTH + " TEXT," + KEY_GENDER + " TEXT," + KEY_START_DATE + " TEXT," + KEY_IS_RENT_PAID
                    + " TEXT," + KEY_IS_APPROVED + " TEXT," + KEY_STAY_FREQUENCY + " TEXT," + KEY_ID_PROOFS + " TEXT," + KEY_ID_PROOFS_TYPES + " TEXT," + KEY_PROFILE_PIC + " TEXT,"
                    + KEY_PROFESSION + " TEXT," + KEY_ROOM_NUMBER + " TEXT," + KEY_COMPLAINTS_LOGGED + " TEXT," + KEY_PROPERTY_NAME + " TEXT)";

            String CREATE_MEAL_SCHEDULE= "CREATE TABLE " + TABLE_MEAL_SCHEDULE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROPERTY_ID + " INTEGER," + KEY_BREAKFAST_FROM_TIME + " TEXT," + KEY_BREAKFAST_TO_TIME + " TEXT," + KEY_LUNCH_FROM_TIME
                    + " TEXT,"+ KEY_LUNCH_TO_TIME + " TEXT,"+ KEY_DINNER_FROM_TIME + " TEXT,"+ KEY_DINNER_TO_TIME + " TEXT,"+ KEY_MONDAY_MEALS + " TEXT,"
                    + KEY_TUESDAY_MEALS + " TEXT,"+ KEY_WEDNESDAY_MEALS + " TEXT,"+ KEY_THURSDAY_MEALS + " TEXT,"+ KEY_FRIDAY_MEALS + " TEXT,"+ KEY_SATURDAY_MEALS + " TEXT,"
                    + KEY_SUNDAY_MEALS + " TEXT)";


            db.execSQL(CREATE_USER_DETAILS_TABLE);
            db.execSQL(CREATE_PROPERTY_DETAILS_TABLE);
            db.execSQL(CREATE_PROPERTY_LAYOUT_DETAILS);
            db.execSQL(CREATE_MEAL_SCHEDULE);
            db.execSQL(CREATE_COST_AND_CHARGES_TABLE);
            db.execSQL(CREATE_CURRENT_TENANT_DETAILS);

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_DETAILS);

        // Create tables again
        onCreate(db);
    }

    public void setUserDetails(UserDetailsVO userDetails) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_SERVER_ENTRY_ID, userDetails.getUserId());
            values.put(KEY_USER_NAME, userDetails.getUserName());
            values.put(KEY_EMAIL_ADDRESS, userDetails.getEmailAddress());
            values.put(KEY_CONTACT_NUMBER, userDetails.getContactNumber());
            values.put(KEY_DATE_OF_BIRTH, userDetails.getDateOfBirth());
            values.put(KEY_GENDER, userDetails.getGender());
            values.put(KEY_ACCOUNT_TYPE, userDetails.getAccountType());

            // Inserting Row
            db.insertOrThrow(TABLE_USER_DETAILS, null, values);
            db.close(); // Closing database connection
        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    public void setPropertyDetails(PropertyDetailsVO propertyDetails) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_PROPERTY_ID, propertyDetails.getPropertyId());
            values.put(KEY_PROPERTY_NAME, propertyDetails.getPropertyName());
            values.put(KEY_ADDRESS_LINE_ONE, propertyDetails.getAddressLineOne());
            values.put(KEY_ADDRESS_LINE_TWO, propertyDetails.getAddressLineTwo());
            values.put(KEY_POSTAL_CODE, propertyDetails.getPostalCode());
            values.put(KEY_STATE, propertyDetails.getState());
            values.put(KEY_GEO_LOCATION, propertyDetails.getGeoLocation());
            values.put(KEY_PROPERTY_TYPE, propertyDetails.getPropertyType());
            values.put(KEY_IS_MEAL_OFFERED, String.valueOf(propertyDetails.isMealOffered()));
            values.put(KEY_IS_MEAL_SCHEDULE_AVAILABLE, String.valueOf(propertyDetails.isMealScheduleAvailable()));
            values.put(KEY_FACILITIES_PROVIDED, propertyDetails.getFacilitiesProvided());
            values.put(KEY_ROOM_TYPES, propertyDetails.getRoomTypes());
            values.put(KEY_FURNISHED_FLAT_ITEMS,propertyDetails.getFurnishedFlatItems());

            // Inserting Row
            db.insertOrThrow(TABLE_PROPERTY_DETAILS, null, values);
            db.close(); // Closing database connection
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    public void setPropertyLayoutDetails(PropertyLayoutDetailsVO layoutDetailsVO){

        try {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_PROPERTY_ID, layoutDetailsVO.getPropertyId());
            values.put(KEY_FLOOR_NUMBER, layoutDetailsVO.getFloorNumber());
            values.put(KEY_ROOM_NUMBER, layoutDetailsVO.getRoomNumber());
            values.put(KEY_ROOM_TYPE, layoutDetailsVO.getRoomType());
            values.put(KEY_NUMBER_OF_OCCUPANCY, layoutDetailsVO.getOccupancy());

            db.insertOrThrow(TABLE_PROPERTY_LAYOUT_DETAILS, null, values);
            db.close(); // Closing database connection

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    public void setCostAndCharges(CostAndChargesVO costAndCharges){

        try {

            LinkedHashMap<String,String> otherChargesMap=costAndCharges.getOtherChargesMap();
            ArrayList<String> listOfRoomTypesAndChargesWithDuration=costAndCharges.getListOfRoomTypesAndChargesWithDuration();

            SQLiteDatabase db = this.getWritableDatabase();

            for(int i=0;i<listOfRoomTypesAndChargesWithDuration.size();i++){

                String[] array=listOfRoomTypesAndChargesWithDuration.get(i).split(":");

                if(array.length==3) {

                    ContentValues values = new ContentValues();
                    values.put(KEY_PROPERTY_ID, costAndCharges.getPropertyId());
                    values.put(KEY_ROOM_TYPE, array[0]);
                    values.put(KEY_CHARGES, array[1]);
                    values.put(KEY_DURATION, array[2]);

                    db.insertOrThrow(TABLE_COST_AND_CHARGES, null, values);
                }
            }

            for(Map.Entry<String,String> entry : otherChargesMap.entrySet()){

                String chargeType=entry.getKey();
                String charges=entry.getValue();

                ContentValues values = new ContentValues();
                values.put(KEY_PROPERTY_ID, costAndCharges.getPropertyId());
                values.put(KEY_CHARGE_TYPE,chargeType);
                values.put(KEY_CHARGES, charges);

                db.insertOrThrow(TABLE_COST_AND_CHARGES, null, values);
            }


            db.close(); // Closing database connection

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }

    }

    public void setMealSchedule(MealScheduleVO mealScheduleVO){

        try {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_PROPERTY_ID, mealScheduleVO.getPropertyId());
            values.put(KEY_BREAKFAST_FROM_TIME, mealScheduleVO.getBreakfastFromTime());
            values.put(KEY_BREAKFAST_TO_TIME, mealScheduleVO.getBreakfastToTime());
            values.put(KEY_LUNCH_FROM_TIME, mealScheduleVO.getLunchFromTime());
            values.put(KEY_LUNCH_TO_TIME, mealScheduleVO.getLunchToTime());
            values.put(KEY_DINNER_FROM_TIME, mealScheduleVO.getDinnerFromTime());
            values.put(KEY_DINNER_TO_TIME, mealScheduleVO.getDinnerToTime());

            List<String> listOfMeals=mealScheduleVO.getListOfMeals();

            values.put(KEY_MONDAY_MEALS, listOfMeals.get(0));
            values.put(KEY_TUESDAY_MEALS,listOfMeals.get(1));
            values.put(KEY_WEDNESDAY_MEALS, listOfMeals.get(2));
            values.put(KEY_THURSDAY_MEALS,listOfMeals.get(3));
            values.put(KEY_FRIDAY_MEALS, listOfMeals.get(4));
            values.put(KEY_SATURDAY_MEALS, listOfMeals.get(5));
            values.put(KEY_SUNDAY_MEALS, listOfMeals.get(6));

            db.insertOrThrow(TABLE_MEAL_SCHEDULE, null, values);
            db.close(); // Closing database connection

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }

    }

    public void setCurrentTenantDetails(TenantDetailsVO tenantDetails) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, tenantDetails.getTenantName());
            values.put(KEY_EMAIL_ADDRESS, tenantDetails.getTenantEmailAddress());
            values.put(KEY_CONTACT_NUMBER, tenantDetails.getTenantContactNumber());
            values.put(KEY_DATE_OF_BIRTH, tenantDetails.getTenantDateOfBirth());
            values.put(KEY_GENDER, tenantDetails.getTenantGender());
            values.put(KEY_PROPERTY_ID, tenantDetails.getPropertyId());
            values.put(KEY_START_DATE, tenantDetails.getStartDate());
            values.put(KEY_IS_RENT_PAID, String.valueOf(tenantDetails.isRentPaid()));
            values.put(KEY_IS_APPROVED, String.valueOf(tenantDetails.isApproved()));
            values.put(KEY_STAY_FREQUENCY, tenantDetails.getFrequencyOfStay());
            values.put(KEY_ID_PROOFS, tenantDetails.getListOfIdProofsPicSource());
            values.put(KEY_ID_PROOFS_TYPES, tenantDetails.getTenantUploadedIdProofs());
            values.put(KEY_PROFILE_PIC, tenantDetails.getTenantProfilePic());
            values.put(KEY_PROFESSION, tenantDetails.getTenantProfession());
            values.put(KEY_ROOM_NUMBER, tenantDetails.getTenantRoomNumber());
            values.put(KEY_COMPLAINTS_LOGGED, tenantDetails.getTenantLoggedComplaints());

            db.insertOrThrow(TABLE_TENANT_DETAILS, null, values);
            db.close();

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    /*public UserDetailsVO getUserDetails(int id) {

        UserDetailsVO userDetails = new UserDetailsVO();
        Cursor cursor=null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            cursor = db.query(TABLE_USER_DETAILS, new String[]{KEY_ID,
                            KEY_SERVER_ENTRY_ID, KEY_USER_NAME, KEY_EMAIL_ADDRESS, KEY_CONTACT_NUMBER, KEY_DATE_OF_BIRTH, KEY_GENDER, KEY_ACCOUNT_TYPE}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null) {

                cursor.moveToFirst();

                userDetails.setUserId(cursor.getInt(1));
                userDetails.setUserName(cursor.getString(2));
                userDetails.setEmailAddress(cursor.getString(3));
                userDetails.setContactNumber(cursor.getString(4));
                userDetails.setDateOfBirth(cursor.getString(5));
                userDetails.setGender(cursor.getString(6));
                userDetails.setAccountType(cursor.getString(7));
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {

            if(cursor!=null){
                cursor.close();
            }

        }

            return userDetails;

    }*/

    /*public void deleteUserDetailsRecord(UserDetailsVO userDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_DETAILS, KEY_ID + " = ?",
                new String[] { String.valueOf(userDetails.getUserId()) });
        db.close();
    }*/

    public PropertyDetailsVO getPropertyDetailsVO(int propertyId){

        PropertyDetailsVO propertyDetailsVO=new PropertyDetailsVO();
        Cursor cursor=null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String query="Select * from "+TABLE_PROPERTY_DETAILS+" where "+KEY_PROPERTY_ID +"="+propertyId;

            cursor = db.rawQuery(query,null);
            if (cursor != null) {

                cursor.moveToFirst();

                propertyDetailsVO.setPropertyId(propertyId);
                propertyDetailsVO.setPropertyName(cursor.getString(2));
                propertyDetailsVO.setAddressLineOne(cursor.getString(3));
                propertyDetailsVO.setAddressLineTwo(cursor.getString(4));
                propertyDetailsVO.setPostalCode(Integer.valueOf(cursor.getString(5)));
                propertyDetailsVO.setState(cursor.getString(6));
                propertyDetailsVO.setGeoLocation(cursor.getString(7));
                propertyDetailsVO.setPropertyType(cursor.getString(8));
                if (cursor.getString(9).equalsIgnoreCase("true")) {
                    propertyDetailsVO.setMealOffered(true);
                } else {
                    propertyDetailsVO.setMealOffered(false);
                }
                if (cursor.getString(10).equalsIgnoreCase("true")) {
                    propertyDetailsVO.setMealScheduleAvailable(true);
                } else {
                    propertyDetailsVO.setMealScheduleAvailable(false);
                }

                if (cursor.getString(11) != null) {

                    propertyDetailsVO.setFacilitiesProvided(cursor.getString(11));


                    String[] facilities = cursor.getString(11).split(":");

                    propertyDetailsVO.setListOfFacilities(facilities);
                }

                if (cursor.getString(12) != null) {

                    propertyDetailsVO.setRoomTypes(cursor.getString(12));

                    String[] roomTypes = cursor.getString(12).split(":");

                    propertyDetailsVO.setTypeOfRooms(roomTypes);
                }

                propertyDetailsVO.setFurnishedFlatItems(cursor.getString(13));
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {

            if(cursor!=null){

                cursor.close();
            }
        }
        return propertyDetailsVO;
    }

    public MealScheduleVO getMealScheduleVO(int propertyId){

        MealScheduleVO mealScheduleVO=new MealScheduleVO();
        Cursor cursor=null;
        ArrayList<String> listOfMeals=new ArrayList<>();

        try{

            SQLiteDatabase db = this.getReadableDatabase();

            String query="Select * from "+TABLE_MEAL_SCHEDULE+" where "+KEY_PROPERTY_ID +"="+propertyId;

            cursor = db.rawQuery(query,null);
            if (cursor != null) {
                cursor.moveToFirst();

                mealScheduleVO.setPropertyId(propertyId);

                mealScheduleVO.setBreakfastFromTime(cursor.getString(2));
                mealScheduleVO.setBreakfastToTime(cursor.getString(3));
                mealScheduleVO.setLunchFromTime(cursor.getString(4));
                mealScheduleVO.setLunchToTime(cursor.getString(5));
                mealScheduleVO.setDinnerFromTime(cursor.getString(6));
                mealScheduleVO.setDinnerToTime(cursor.getString(7));

                String mondayMeal=cursor.getString(8);
                String tuesdayMeal=cursor.getString(9);
                String wednesdayMeal=cursor.getString(10);
                String thursdayMeal=cursor.getString(11);
                String fridayMeal=cursor.getString(12);
                String saturdayMeal=cursor.getString(13);
                String sundayMeal=cursor.getString(14);

                listOfMeals.add(mondayMeal);
                listOfMeals.add(tuesdayMeal);
                listOfMeals.add(wednesdayMeal);
                listOfMeals.add(thursdayMeal);
                listOfMeals.add(fridayMeal);
                listOfMeals.add(saturdayMeal);
                listOfMeals.add(sundayMeal);

                mealScheduleVO.setListOfMeals(listOfMeals);
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {

            if(cursor!=null){

                cursor.close();
            }
        }

        return mealScheduleVO;
    }

    /*public int updateUserDetails(UserDetailsVO userDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GENDER, userDetails.getGender());
        values.put(KEY_USER_NAME, userDetails.getUserName());

        // updating row
        return db.update(TABLE_USER_DETAILS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(userDetails.getUserId()) });
    }*/

    public void updateBuildingDetails(PropertyDetailsVO propertyDetails){

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String query="delete from "+ TABLE_PROPERTY_DETAILS +" where " +KEY_PROPERTY_ID+"="+propertyDetails.getPropertyId();

            db.execSQL(query);

            setPropertyDetails(propertyDetails);

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    public void updateFacilitiesProvided(String facilities,String propertyId){

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_FACILITIES_PROVIDED, facilities);

            db.update(TABLE_PROPERTY_DETAILS, values, KEY_PROPERTY_ID+"="+propertyId, null);

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    public void updateMealsSchedule(MealScheduleVO mealScheduleVO){

        try {

            SQLiteDatabase db = this.getWritableDatabase();

            String query="delete from "+ TABLE_MEAL_SCHEDULE +" where " +KEY_PROPERTY_ID+"="+mealScheduleVO.getPropertyId();

            db.execSQL(query);

            setMealSchedule(mealScheduleVO);

        }catch (Exception e) {

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }

    public int getMaxPropertyId(){

        int maxPropertyId = 0;
        Cursor cursor =null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String query = "Select max(" + KEY_PROPERTY_ID + ") from " + TABLE_PROPERTY_DETAILS;

            cursor = db.rawQuery(query, null);
            if (cursor != null) {
                cursor.moveToFirst();

                maxPropertyId = cursor.getInt(0);
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {

            if(cursor!=null){

                cursor.close();
            }
        }

        return  maxPropertyId;
    }

    public FloorToRoomVO getPropertyLayoutDetails(int propertyId){

        FloorToRoomVO floorToRoomVO=new FloorToRoomVO();
        LinkedHashMap<String,LinkedHashMap<String,String>> mapOfFloorToRoomNoAndRoomType=new LinkedHashMap<>();
        LinkedHashMap<String,Integer> floorToNumberOfRoomsMap=new LinkedHashMap<>();

        Cursor cursor=null;


        ArrayList<String> floorName=new ArrayList<>();

        String getFloorNamesQuery="SELECT distinct "+KEY_FLOOR_NUMBER +" from "+ TABLE_PROPERTY_LAYOUT_DETAILS + " where " + KEY_PROPERTY_ID + " = " + propertyId;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            cursor = db.rawQuery(getFloorNamesQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    floorName.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            for(int i=0;i<floorName.size();i++){

                int countOfNumberOfRooms=0;

                LinkedHashMap<String,String> roomNumberToRoomTypeMap=new LinkedHashMap<>();

                String query = "SELECT "+KEY_ROOM_NUMBER+ " , "+KEY_ROOM_TYPE+ " from " + TABLE_PROPERTY_LAYOUT_DETAILS + " where " + KEY_PROPERTY_ID + " = " + propertyId +" and "+KEY_FLOOR_NUMBER + " = \"" +floorName.get(i) +"\"";

                cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    do {

                        roomNumberToRoomTypeMap.put(cursor.getString(0),cursor.getString(1));
                        countOfNumberOfRooms++;

                    } while (cursor.moveToNext());
                }

                mapOfFloorToRoomNoAndRoomType.put(floorName.get(i),roomNumberToRoomTypeMap);
                floorToNumberOfRoomsMap.put(floorName.get(i),countOfNumberOfRooms);
            }

            floorToRoomVO.setFloorToNumberOfRoomsMap(floorToNumberOfRoomsMap);
            floorToRoomVO.setMapOfFloorToRoomNoAndRoomType(mapOfFloorToRoomNoAndRoomType);

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {
            if(cursor!=null) {
                cursor.close();
            }
        }

        return floorToRoomVO;
    }

    public CostAndChargesVO getCostAndCharges(int propertyId){

        CostAndChargesVO costAndCharges=new CostAndChargesVO();

        LinkedHashMap<String,String> otherChargesMap=new LinkedHashMap<>();
        ArrayList<String> listOfRoomTypesAndChargesWithDuration=new ArrayList<>();

        Cursor cursor=null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String getFloorNamesQuery = "SELECT * from " + TABLE_COST_AND_CHARGES + " where " + KEY_PROPERTY_ID + " = " + propertyId;

            cursor = db.rawQuery(getFloorNamesQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    if(cursor.getString(2)==null || cursor.getString(2).equalsIgnoreCase("")){

                        otherChargesMap.put(cursor.getString(5),cursor.getString(3));
                    }else{

                        listOfRoomTypesAndChargesWithDuration.add(cursor.getString(2) +":"+cursor.getString(3)+":"+cursor.getString(4));
                    }

                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        costAndCharges.setList(listOfRoomTypesAndChargesWithDuration);
        costAndCharges.setOtherChargesMap(otherChargesMap);
        costAndCharges.setPropertyId(propertyId);

        return costAndCharges;
    }

    public ArrayList<TenantDetailsVO> getFilteredTenantDetails(String whereClause){

        ArrayList<TenantDetailsVO> listOfTenants=new ArrayList<>();

        TenantDetailsVO tenantDetailsVO;

        Cursor cursor=null;

        try {

            String query = "SELECT * from " + TABLE_TENANT_DETAILS + whereClause;

            SQLiteDatabase db = this.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    tenantDetailsVO=new TenantDetailsVO();

                    tenantDetailsVO.setTenantName(cursor.getString(2));
                    tenantDetailsVO.setTenantEmailAddress(cursor.getString(3));
                    tenantDetailsVO.setTenantContactNumber(cursor.getString(4));
                    tenantDetailsVO.setTenantDateOfBirth(cursor.getString(5));
                    tenantDetailsVO.setTenantGender(cursor.getString(6));
                    tenantDetailsVO.setStartDate(cursor.getString(7));

                    if(cursor.getString(8).equalsIgnoreCase("True")){

                        tenantDetailsVO.setRentPaid(true);
                    }else{

                        tenantDetailsVO.setRentPaid(false);
                    }

                    if(cursor.getString(9).equalsIgnoreCase("True")){

                        tenantDetailsVO.setApproved(true);
                    }else{

                        tenantDetailsVO.setApproved(false);
                    }

                    tenantDetailsVO.setFrequencyOfStay(cursor.getString(10));
                    tenantDetailsVO.setTenantUploadedIdProofs(cursor.getString(11));
                    tenantDetailsVO.setListOfIdProofsPicSource(cursor.getString(12));
                    tenantDetailsVO.setTenantProfilePic(cursor.getString(13));
                    tenantDetailsVO.setTenantProfession(cursor.getString(14));
                    tenantDetailsVO.setTenantRoomNumber(cursor.getString(15));
                    tenantDetailsVO.setTenantLoggedComplaints(cursor.getString(16));
                    listOfTenants.add(tenantDetailsVO);

                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return listOfTenants;
    }

    public ArrayList<TenantDetailsVO> getAllCurrentTenants(){

        ArrayList<TenantDetailsVO> listOfTenants=new ArrayList<>();

        TenantDetailsVO tenantDetailsVO;

        Cursor cursor=null;

        try {

            String query = "SELECT * from " + TABLE_TENANT_DETAILS ;

            SQLiteDatabase db = this.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    tenantDetailsVO=new TenantDetailsVO();

                    tenantDetailsVO.setTenantName(cursor.getString(2));
                    tenantDetailsVO.setTenantEmailAddress(cursor.getString(3));
                    tenantDetailsVO.setTenantContactNumber(cursor.getString(4));
                    tenantDetailsVO.setTenantDateOfBirth(cursor.getString(5));
                    tenantDetailsVO.setTenantGender(cursor.getString(6));
                    tenantDetailsVO.setStartDate(cursor.getString(7));

                    if(cursor.getString(8).equalsIgnoreCase("True")){

                        tenantDetailsVO.setRentPaid(true);
                    }else{

                        tenantDetailsVO.setRentPaid(false);
                    }

                    if(cursor.getString(9).equalsIgnoreCase("True")){

                        tenantDetailsVO.setApproved(true);
                    }else{

                        tenantDetailsVO.setApproved(false);
                    }

                    tenantDetailsVO.setFrequencyOfStay(cursor.getString(10));
                    tenantDetailsVO.setListOfIdProofsPicSource(cursor.getString(11));
                    tenantDetailsVO.setTenantUploadedIdProofs(cursor.getString(12));
                    tenantDetailsVO.setTenantProfilePic(cursor.getString(13));
                    tenantDetailsVO.setTenantProfession(cursor.getString(14));
                    tenantDetailsVO.setTenantRoomNumber(cursor.getString(15));
                    tenantDetailsVO.setTenantLoggedComplaints(cursor.getString(16));

                    listOfTenants.add(tenantDetailsVO);

                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {
            if(cursor!=null){
                cursor.close();
            }

        }

        return listOfTenants;


    }

    public LinkedHashMap<Integer,String> getMapOfRegisteredProperties(){

        LinkedHashMap<Integer, String> mapOfRegisteredProperties = new LinkedHashMap<>();
        Cursor cursor=null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT " + KEY_PROPERTY_ID + "," + KEY_PROPERTY_NAME + " from " + TABLE_PROPERTY_DETAILS;

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    mapOfRegisteredProperties.put(cursor.getInt(0), cursor.getString(1));
                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return mapOfRegisteredProperties;
    }

    public ArrayList<String> getAllRegisteredPropertyNames(){

        ArrayList<String> listOfRegisteredPropertyNames=new ArrayList<>();
        Cursor cursor=null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT distinct " + KEY_PROPERTY_NAME + " from " + TABLE_PROPERTY_DETAILS;

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    listOfRegisteredPropertyNames.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return listOfRegisteredPropertyNames;
    }

    public Set<String> getTypesOfRooms(){

        Set<String> listOfTypesOfRooms=new HashSet<>();
        Cursor cursor=null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT " + KEY_ROOM_TYPES + " from " + TABLE_PROPERTY_DETAILS;

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    String[] roomTypes=cursor.getString(0).split(":");

                    for(String roomType : roomTypes){

                        listOfTypesOfRooms.add(roomType);
                    }

                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return listOfTypesOfRooms;
    }

    public String[] getCurrentOccupiedRoomsForFlatAndHotels(){

        Cursor cursor=null;
        String[] arrayOfOccupiedRooms=null;

        int count=0;

        try{

            SQLiteDatabase db = this.getReadableDatabase();

            String query="Select distinct "+KEY_ROOM_NUMBER+" from "+TABLE_TENANT_DETAILS;

            cursor = db.rawQuery(query, null);

            arrayOfOccupiedRooms=new String[cursor.getCount()];

            if (cursor.moveToFirst()) {
                do {

                    arrayOfOccupiedRooms[count]=cursor.getString(0);

                    count++;

                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);


        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return arrayOfOccupiedRooms;
    }

    public HashMap<String,Integer> getCurrentOccupiedRoomsForPg(){

        Cursor cursor=null;
        HashMap<String,Integer> mapOfOccupiedRoomsToFreeOccupancy=new HashMap<>();
        HashMap<String,Integer> mapOfRoomNumberToOccupancy=getRoomNumberToOccupancyMap();

        ArrayList<String> listOfOccupiedRooms=new ArrayList<>();

        try{

            SQLiteDatabase db = this.getReadableDatabase();

            String query="Select distinct "+KEY_ROOM_NUMBER+ " from "+TABLE_TENANT_DETAILS;

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    listOfOccupiedRooms.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            for(int i=0;i<listOfOccupiedRooms.size();i++) {

                String countOfOccupancyInOccupiedRooms="select count("+KEY_ROOM_NUMBER+") from "+TABLE_TENANT_DETAILS+" where "+KEY_ROOM_NUMBER+"=\""+listOfOccupiedRooms.get(i)+"\"";

                cursor = db.rawQuery(countOfOccupancyInOccupiedRooms, null);

                if (cursor.moveToFirst()) {
                    do {

                        int occupancy=mapOfRoomNumberToOccupancy.get(listOfOccupiedRooms.get(i));

                        int actualOccupancy=cursor.getInt(0);

                        int vacantOccupancy=occupancy-actualOccupancy;

                        mapOfOccupiedRoomsToFreeOccupancy.put(listOfOccupiedRooms.get(i), vacantOccupancy);

                    } while (cursor.moveToNext());
                }
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);


        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return mapOfOccupiedRoomsToFreeOccupancy;
    }

    public String[] getCurrentUnOccupiedRoomsForFlatAndHotels(){

        Cursor cursor=null;
        String[] arrayOfUnOccupiedRooms=null;

        String query;
        int count=0;

        try{

            SQLiteDatabase db = this.getReadableDatabase();

            query = "Select " + KEY_ROOM_NUMBER + " from " + TABLE_PROPERTY_LAYOUT_DETAILS + " where "+KEY_ROOM_NUMBER +" not in (Select distinct "+KEY_ROOM_NUMBER+" from "+TABLE_TENANT_DETAILS+")";

            cursor = db.rawQuery(query, null);

            arrayOfUnOccupiedRooms=new String[cursor.getCount()];

            if (cursor.moveToFirst()) {
                do {

                    arrayOfUnOccupiedRooms[count]=cursor.getString(0);

                    count++;

                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);


        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return arrayOfUnOccupiedRooms;
    }

    public HashMap<String,Integer> getCurrentUnOccupiedRoomsForPg(){

        Cursor cursor=null;
        HashMap<String,Integer> mapOfUnOccupiedRooms=new HashMap<>();

        try{

            SQLiteDatabase db = this.getReadableDatabase();

            String query="Select "+KEY_ROOM_NUMBER+" from "+TABLE_TENANT_DETAILS;

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {



                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);


        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return mapOfUnOccupiedRooms;
    }

    public HashMap<String,Integer> getRoomNumberToOccupancyMap(){

        Cursor cursor=null;
        HashMap<String,Integer> mapOfRoomNumberToOccupancy=new HashMap<>();

        try{

            SQLiteDatabase db = this.getReadableDatabase();

            String query="Select "+KEY_ROOM_NUMBER+","+KEY_NUMBER_OF_OCCUPANCY+" from "+TABLE_PROPERTY_LAYOUT_DETAILS;

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    mapOfRoomNumberToOccupancy.put(cursor.getString(0),cursor.getInt(1));

                } while (cursor.moveToNext());
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);


        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return mapOfRoomNumberToOccupancy;
    }

    public void deleteDatabase(Context context){

        context.deleteDatabase(DATABASE_NAME);
    }

    public void deleteProperty(int propertyId){

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String query = "delete from " + TABLE_PROPERTY_DETAILS  + " where " + KEY_PROPERTY_ID + " = " + propertyId;

            db.execSQL(query);

            query = "delete from " +  TABLE_PROPERTY_LAYOUT_DETAILS  + " where " + KEY_PROPERTY_ID + " = " + propertyId;

            db.execSQL(query);

            query = "delete from " +  TABLE_COST_AND_CHARGES  + " where " + KEY_PROPERTY_ID + " = " + propertyId;

            db.execSQL(query);

            query = "delete from " +  TABLE_MEAL_SCHEDULE  + " where " + KEY_PROPERTY_ID + " = " + propertyId;

            db.execSQL(query);

            query = "delete from " +   TABLE_TENANT_DETAILS + " where " + KEY_PROPERTY_ID + " = " + propertyId;

            db.execSQL(query);

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(activity);
        }
    }
}
