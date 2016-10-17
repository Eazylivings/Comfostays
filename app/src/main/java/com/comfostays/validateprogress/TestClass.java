package com.comfostays.validateprogress;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.VOClass.PropertyLayoutDetailsVO;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.VOClass.UserDetailsVO;
import com.comfostays.sharedpreference.SharedPreference;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TestClass {

    public String checkEmailAndPassword(String emailAddress, String password){

        return Constants.AUTHENTICATION_LOGIN_SUCCESS;

        /*try {
            FileInputStream in = new FileInputStream("LoginDetails.txt");

            if ( in != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    if(receiveString.contains(emailAddress) && receiveString.contains(password)){
                        return Constants.AUTHENTICATION_LOGIN_SUCCESS;
                    }
                }

                in.close();
                return Constants.AUTHENTICATION_LOGIN_FAIL;
            }else{
                return Constants.AUTHENTICATION_LOGIN_FAIL;
            }
        }
        catch (Exception e) {
            return Constants.AUTHENTICATION_LOGIN_FAIL;

        }*/
    }

    public String passwordResetSuccessfully(String emailAddress){

        ArrayList<String> list=new ArrayList<String>();
        list.add("shwetangagarwal@gmail.com");
        list.add("kavitastar.g3@gmail.com");

        if(list.contains(emailAddress)){
            return Constants.AUTHENTICATION_PASSWORD_RESET_SUCCESS;
        }else{
            return Constants.AUTHENTICATION_EXISTING_USER_FAIL;
        }
    }

    public String userRegistration(String userName,String emailAddress, String contactNumber,String password,String type,Context context){

        String finalUserDetails = userName + ":" + emailAddress + ":" + contactNumber + ":" + password+":"+type+"\n";
        String loginDetails=userName+":"+password;

        return writeToFile(finalUserDetails,loginDetails, context);
    }

    public UserDetailsVO getUserDetails(Context context, Activity activity){

        UserDetailsVO userDetails=new UserDetailsVO();
        userDetails.setUserId(1);
        userDetails.setUserName("Shwetang");
        userDetails.setAccountType("Owner");
        userDetails.setEmailAddress("shwetangagarwal@gmail.com");
        userDetails.setDateOfBirth("20-Aug-1992");
        userDetails.setContactNumber("7674013173");
        userDetails.setGender("Male");
        return userDetails;
    }

    public ArrayList<PropertyLayoutDetailsVO> getPropertyLayoutDetailsVO(){

        ArrayList<PropertyLayoutDetailsVO> listofPropertyLayoutDetailsVO=new ArrayList<>();

        PropertyLayoutDetailsVO layoutDetailsVO=new PropertyLayoutDetailsVO();

        layoutDetailsVO.setPropertyId(1);
        layoutDetailsVO.setFloorNumber("Ground Floor");
        layoutDetailsVO.setRoomNumber("G1");
        layoutDetailsVO.setRoomType("Single Sharing");
        layoutDetailsVO.setOccupancy(2);

        listofPropertyLayoutDetailsVO.add(layoutDetailsVO);

        layoutDetailsVO=new PropertyLayoutDetailsVO();

        layoutDetailsVO.setPropertyId(1);
        layoutDetailsVO.setFloorNumber("Ground Floor");
        layoutDetailsVO.setRoomNumber("G2");
        layoutDetailsVO.setRoomType("Double Sharing");
        layoutDetailsVO.setOccupancy(4);

        listofPropertyLayoutDetailsVO.add(layoutDetailsVO);

        layoutDetailsVO=new PropertyLayoutDetailsVO();

        layoutDetailsVO.setPropertyId(1);
        layoutDetailsVO.setFloorNumber("1st Floor");
        layoutDetailsVO.setRoomNumber("101");
        layoutDetailsVO.setRoomType("Double Sharing");
        layoutDetailsVO.setOccupancy(4);

        listofPropertyLayoutDetailsVO.add(layoutDetailsVO);

        layoutDetailsVO=new PropertyLayoutDetailsVO();

        layoutDetailsVO.setPropertyId(1);
        layoutDetailsVO.setFloorNumber("1st Floor");
        layoutDetailsVO.setRoomNumber("102");
        layoutDetailsVO.setRoomType("Triple Sharing");
        layoutDetailsVO.setOccupancy(6);

        listofPropertyLayoutDetailsVO.add(layoutDetailsVO);

        layoutDetailsVO=new PropertyLayoutDetailsVO();

        layoutDetailsVO.setPropertyId(1);
        layoutDetailsVO.setFloorNumber("2nd Floor");
        layoutDetailsVO.setRoomNumber("201");
        layoutDetailsVO.setRoomType("Double Sharing");
        layoutDetailsVO.setOccupancy(4);

        listofPropertyLayoutDetailsVO.add(layoutDetailsVO);

        layoutDetailsVO=new PropertyLayoutDetailsVO();

        layoutDetailsVO.setPropertyId(1);
        layoutDetailsVO.setFloorNumber("2nd Floor");
        layoutDetailsVO.setRoomNumber("202");
        layoutDetailsVO.setRoomType("Triple Sharing");
        layoutDetailsVO.setOccupancy(6);

        listofPropertyLayoutDetailsVO.add(layoutDetailsVO);

        return listofPropertyLayoutDetailsVO;
    }

    public ArrayList<TenantDetailsVO> getListOfTenantDetailsVO(){

        ArrayList<TenantDetailsVO> listOfTenants=new ArrayList<>();

        TenantDetailsVO tenantDetailsVO=new TenantDetailsVO();


        tenantDetailsVO.setTenantName("Shwetang");
        tenantDetailsVO.setTenantEmailAddress("shwetangagarwal@gmail.com");
        tenantDetailsVO.setTenantContactNumber("7674013173");
        tenantDetailsVO.setTenantDescription("I am from Delhi. Working with Oracle India Pvt Ltd.");
        tenantDetailsVO.setTenantUploadedIdProofs("PAN Card , Driving Licence");
        tenantDetailsVO.setTenantResidingSince("1st May , 2014");
        tenantDetailsVO.setTenantProfession("Software Engineer");
        tenantDetailsVO.setTenanatStayDuration("27");
        tenantDetailsVO.setTenantRoomNumber("G2");
        tenantDetailsVO.setTenantLoggedComplaints("5");
        tenantDetailsVO.setRentPaid(true);
        tenantDetailsVO.setTenantResidingProperty("SN Plaza");
        tenantDetailsVO.setTenantProfilePic(R.drawable.profile_pic_one+"");

        listOfTenants.add(tenantDetailsVO);

        tenantDetailsVO=new TenantDetailsVO();


        tenantDetailsVO.setTenantName("Prudhvi");
        tenantDetailsVO.setTenantEmailAddress("prudhvi.k@gmail.com");
        tenantDetailsVO.setTenantContactNumber("9811530263");
        tenantDetailsVO.setTenantDescription("I am from Satupalli. Working with TCS since 2014");
        tenantDetailsVO.setTenantUploadedIdProofs("PAN Card , Driving Licence");
        tenantDetailsVO.setTenantResidingSince("1st July , 2016");
        tenantDetailsVO.setTenantProfession("Software Engineer");
        tenantDetailsVO.setTenanatStayDuration("3");
        tenantDetailsVO.setTenantRoomNumber("G2");
        tenantDetailsVO.setTenantLoggedComplaints("1");
        tenantDetailsVO.setRentPaid(true);
        tenantDetailsVO.setTenantResidingProperty("SN Plaza");
        tenantDetailsVO.setTenantProfilePic(R.drawable.profile_pic_two+"");

        listOfTenants.add(tenantDetailsVO);

        tenantDetailsVO=new TenantDetailsVO();


        tenantDetailsVO.setTenantName("Akhil Sagar");
        tenantDetailsVO.setTenantEmailAddress("akhilsagar@gmail.com");
        tenantDetailsVO.setTenantContactNumber("9876543211");
        tenantDetailsVO.setTenantDescription("I am from Kurnool. Working with TCS since 2014.");
        tenantDetailsVO.setTenantUploadedIdProofs("PAN Card , Driving Licence");
        tenantDetailsVO.setTenantResidingSince("1st July , 2016");
        tenantDetailsVO.setTenantProfession("Software Engineer");
        tenantDetailsVO.setTenanatStayDuration("2");
        tenantDetailsVO.setTenantRoomNumber("101");
        tenantDetailsVO.setTenantLoggedComplaints("5");
        tenantDetailsVO.setRentPaid(false);
        tenantDetailsVO.setTenantResidingProperty("SN Plaza");
        tenantDetailsVO.setTenantProfilePic(R.drawable.profile_pic_three+"");

        listOfTenants.add(tenantDetailsVO);

        tenantDetailsVO=new TenantDetailsVO();


        tenantDetailsVO.setTenantName("Kavita Gottipalli");
        tenantDetailsVO.setTenantEmailAddress("kavitastar.g3@gmail.com");
        tenantDetailsVO.setTenantContactNumber("7838635565");
        tenantDetailsVO.setTenantDescription("I am from Orrisa. Working with TCS since 2014.");
        tenantDetailsVO.setTenantUploadedIdProofs("PAN Card , Driving Licence");
        tenantDetailsVO.setTenantResidingSince("1st May , 2014");
        tenantDetailsVO.setTenantProfession("Software Engineer");
        tenantDetailsVO.setTenanatStayDuration("27");
        tenantDetailsVO.setTenantRoomNumber("G1");
        tenantDetailsVO.setTenantLoggedComplaints("0");
        tenantDetailsVO.setRentPaid(true);
        tenantDetailsVO.setTenantResidingProperty("SN Plaza");
        tenantDetailsVO.setTenantProfilePic(R.drawable.profile_pic_one+"");

        listOfTenants.add(tenantDetailsVO);
        return listOfTenants;
    }


    public String getUpperLevelRoomOccupancy(int fromYear,int toYear, int fromMonth, int toMonth){


        int total=fromMonth*fromYear + toYear*toMonth;

        int xPercent=((fromMonth*fromYear)%total)%100;
        int yPercent=((toYear*toMonth)%total)%100;


        return xPercent+":"+yPercent;
    }

    public String getRoomLevelRoomOccupancy(String type){

        String finalResult="";

        if(type.equalsIgnoreCase(Constants.OCCUPIED)){

            finalResult=10+":"+20+":"+70;

        }else{
            finalResult=40+":"+10+":"+50;
        }



        return finalResult;
    }


    public List<PropertyDetailsVO> getOwnerRegisteredProperties(String emailAddress){

        List<PropertyDetailsVO> list=new ArrayList<>();

        PropertyDetailsVO propertyDetails=new PropertyDetailsVO();
        propertyDetails.setPropertyId(1);
        propertyDetails.setPropertyName("SN Plaza");
        propertyDetails.setAddressLineOne("G2 Anjiah Nagar");
        propertyDetails.setAddressLineTwo("Gachibowli, Hyderabad");
        propertyDetails.setPostalCode(500032);
        propertyDetails.setState("Hyderabad");
        propertyDetails.setPropertyType(Constants.PGs);
        propertyDetails.setMealOffered(false);
        propertyDetails.setMealScheduleAvailable(false);
        propertyDetails.setFacilitiesProvided("TV:GYM:4 Wheeler Parking");
        propertyDetails.setRoomTypes("Single Sharing:Double Sharing:Triple Sharing");

        list.add(propertyDetails);

        return list;
    }

    private String writeToFile(String data,String data2,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("SignUpDetails.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

            outputStreamWriter = new OutputStreamWriter(context.openFileOutput("LoginDetails.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data2);
            outputStreamWriter.close();

            return Constants.AUTHENTICATION_REGISTER_SUCCESS;
        }
        catch (Exception e) {
            return Constants.AUTHENTICATION_REGISTER_FAIL;
        }
    }


    private String readFromFile() {

        String ret = "";

        try {
            FileInputStream in = new FileInputStream("SignUpDetails.txt");

            if ( in != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                in.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }



}
