package com.comfostays;

import android.graphics.Color;

public class Constants {


    //Setting for temp

    public static final String SHARED_PREFERENCE_PASSWORD="password";

    public static final String SHARED_PREFERENCE_IS_USER_LOGGED_IN="isUserLoggedIn";
    public static final String SHARED_PREFERENCE_ACCOUNT_TYPE ="accountType";
    public static final String SHARED_PREFERENCE_USERNAME ="userName";
    public static final String SHARED_PREFERENCE_EMAIL_ADDRESS="emailAddress";
    public static final String SHARED_PREFERENCE_IS_ANY_PROPERTY_REGISTERED="isAnyPropertyRegistered";
    public static final String SHARED_PREFERENCE_STRING="spString";
    public static final String SHARED_PREFERENCE_LIST_TYPES_OF_ROOMS ="listOfTypesOfRooms";
    public static final String SHARED_PREFERENCE_LIST_PERCENTAGE_OF_EACH_ROOM ="listOfPercentageOfEachRoom";
    public static final String SHARED_PREFERENCE_OCCUPANCY_FROM_YEAR="occupancyFromYear";
    public static final String SHARED_PREFERENCE_OCCUPANCY_TO_YEAR="occupancyToYear";
    public static final String SHARED_PREFERENCE_OCCUPANCY_FROM_MONTH="occupancyFromMonth";
    public static final String SHARED_PREFERENCE_OCCUPANCY_TO_MONTH="occupancyToMonth";
    public static final String SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED="isCurrentOccupancyCheckedBoxChecked";
    public static final String SHARED_PREFERENCE_IS_OCCUPANCY_LOADED_FOR_FIRST_TIME="isOccupancyLoadedForFirstTime";
    public static final String SHARED_PREFERENCE_CURRENT_YEAR="occupancyCurrentYear";
    public static final String SHARED_PREFERENCE_CURRENT_MONTH="occupancyCurrentMonth";
    public static final String SHARED_PREFERENCE_TYPE_OF_CHART="typeOfChart";

    public static final String INTENT_PREVIOUS_ACTIVITY ="previousActivity";
    public static final String INTENT_SELECTED_PROPERTY_ID="selectedPropertyId";

    public static final String INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO ="floorToNumberOfRoomsVO";
    public static final String INTENT_ADD_PROPERTY_DETAILS_VO ="addPropertyDetailsVO";
    public static final String INTENT_MEAL_SCHEDULE_VO ="mealScheduleVO";
    public static final String INTENT_COST_AND_CHARGES_VO ="costAndChargesVO";
    public static final String INTENT_ROOM_OCCUPANCY_UPPER_LEVEL_COORDINATES="upperLevelRoomOccupancyCoordinates";
    public static final String INTENT_TENANT_ISSUES ="tenantIssues";
    public static final String INTENT_TENANT_ISSUES_STATUS="tenantIssuesStatus";

    public static final String INTENT_LIST_OF_TENANT_DETAILS_VO ="tenantDetailsVo";
    public static final String INTENT_TENANT_SELECTION="tenantSelection";
    public static final String INTENT_MAP_OF_TENANT_FILTER="tenantsFilterMap";

    public static final String INTENT_IS_TENANT_FILTER_APPLIED="isTenantFilterApplied";

    public static final String ALERT_TITLE="Wrong Input";
    public static final String ALERT_SUCCESS="Successful";
    public static final String ALERT_MESSAGE="Message";
    public static final String ALERT_EMPTY_TEXT="Empty Input";
    public static final String ALERT_EXCEPTION="Sorry";

    public static final String ALERT_BOX_POSITIVE_HEADING="Add Property";

    public static final String POPUP_MESSAGE_LOGIN_FAILED="Fail to login. Please check the credentials.";
    public static final String POPUP_MESSAGE_REGISTER_FAILED ="Something unusual occurred. Please come back again after sometime.";
    public static final String POPUP_MESSAGE_NO_INTERNET="Please check internet connection";
    public static final String POPUP_MESSAGE_USER_NOT_PRESENT="Please check input username or register to become part of ComforStays";
    public static final String POPUP_MESSAGE_PASSWORD_NOT_RESET="Failed to Reset password. Please try again after sometime";
    public static final String POPUP_MESSAGE_NO_USERNAME="Please enter User Name";
    public static final String POPUP_MESSAGE_NO_EMAIL_ADDRESS="Please enter Email Address";
    public static final String POPUP_MESSAGE_NO_CONTACT_NUMBER="Please enter Contact Number";
    public static final String POPUP_MESSAGE_NO_PASSWORD="Please enter password";
    public static final String POPUP_MESSAGE_NO_TYPE_SELECTION="Please select one of the types";
    public static  final String POPUP_MESSAGE_NO_DATE_OF_BIRTH="Please enter date of Birth";
    public static final String POPUP_MESSAGE_GPS_TRACK_SUCCESS="Successfully Saved Location";
    public static final String POPUP_MESSAGE_GPS_TRACK_FAIL="Failed to Save Location. Try after some time";
    public static final String POPUP_MESSAGE_NO_BUILDING_NAME="Please enter building name";
    public static final String POPUP_MESSAGE_NO_ADDRESS_LINE_ONE="Please enter address 1";
    public static final String POPUP_MESSAGE_NO_ADDRESS_LINE_TWO="Please enter address 2";
    public static final String POPUP_MESSAGE_NO_POSTAL_CODE="Please enter postal code";
    public static final String POPUP_MESSAGE_NO_STATE="Please enter state";
    public static final String POPUP_MESSAGE_NO_PROPERTY_TYPE="Please select one property type";
    public static final String POPUP_MESSAGE_FLOOR_REMOVED="successfully removed";
    public static final String POPUP_MESSAGE_FLOOR_NOT_REMOVED="Please select last added floor";
    public static final String POPUP_MESSAGE_NO_PROPERTY_FOUND="Please register property in order to use all features";
    public static final String POPUP_MESSAGE_ENTER_ROOM_NUMBERS="Please enter room number for all the floors";
    public static final String POPUP_MESSAGE_BUY_PAID_VERSION="This facility is availabe to Paid Version of App.";
    public static final String POPUP_MESSAGE_CHARGES_FOR_ALL_ROOMS="Please provide charges for all rooms.";
    public static final String POPUP_MESSAGE_TENANTS_NOTIFIED="Selected tenants have been notified.";
    public static final String POPUP_MESSAGE_ENTER_ALL_TIMES="Please provide timings for all meals";
    public static final String POPUP_MESSAGE_DELETE_PROPERTY="Do you want tot delete this property?";


    public static final String POPUP_MESSAGE_SELECT_CORRECT_YEAR="From year should be equal or less than To year";

    public static final String POPUP_MESSAGE_NO_ROOM_TYPE_ENTERED="Please enter type of Rooms";

    public static final String ACTION_LOGIN="login";
    public static final String ACTION_REGISTER="customerRegister";
    public static final String ACTION_FORGOT_PASSWORD="forgotPassword";
    public static final String ACTION_GET_USER_DETAILS="getUserDetails";
    public static final String ACTION_GET_OWNER_REGISTERED_PROPERTY_LAYOUT_DETAILS="getOwnerRegisteredPropertyLayoutDetails";
    public static final String ACTION_GET_OWNER_REGISTERED_PROPERTIES_DETAILS="getOwnerRegisteredPropertiesDetails";
    public static final String ACTION_GET_ROOM_OCCUPANCY_UPPER_LEVEL="getRoomOccupancyUpperLevel";
    public static final String ACTION_GET_ROOM_OCCUPANCY_ROOM_LEVEL="getRoomOccupancyRoomLevel";
    public static final String ACTION_GET_CURRENT_TENANT_DETAILS ="getCurrentTenantDetails";
    public static final String ACTION_GET_COST_AND_CHARGES="getCostAndCharges";
    public static final String ACTION_GET_TENANTS_FOR_REGISTRATION="getTenantsForRegistration";
    public static final String ACTION_NOTIFY_TENANTS="notifyTenants";
    public static final String ACTION_GET_NOTIFICATIONS="getNotifications";
    public static final String ACTION_GET_MEAL_TIMING_AND_SCHEDULE="getMealTimingAndSchedule";

    public static final String AUTHENTICATION_LOGIN_SUCCESS="Login Successful";
    public static final String AUTHENTICATION_LOGIN_FAIL="Login Failed";
    public static final String AUTHENTICATION_PASSWORD_RESET_SUCCESS="Password Reset Successful";
    public static final String AUTHENTICATION_PASSWORD_RESET_FAIL="Password Reset Failed";
    public static final String AUTHENTICATION_REGISTER_SUCCESS="Registration Successful";
    public static final String AUTHENTICATION_REGISTER_FAIL="Registration Failed";
    public static final String AUTHENTICATION_USER_DETAILS_FETCHED_SUCCESS="User Details fetched";

    public static final String AUTHENTICATION_EXISTING_USER_FAIL="User Not Present";

    public static final String POPUP_BUTTON_TITLE_SIGNUP="Sign Up";
    public static final String POPUP_BUTTON_TITLE_CANCEL="Cancel";

    public static final String TITLE_REGISTER="Welcome to ComforStays";
    public static final String TITLE_FORGOT_PASSWORD="Forgot Password";
    public static final String TITLE_MY_PROPERTIES ="My Properties";
    public static final String TITLE_ADD_PROPERTY="Add Property";
    public static final String TITLE_VIEW_PROPERTY="View Property";
    public static final String TITLE_OCCUPANCY="Occupancy";
    public static final String TITLE_NOTIFICATIONS="Notifications";
    public static final String TITLE_COST_AND_CHARGES="Cost and Charges";
    public static final String TITLE_TENANTS="Tenants";
    public static final String TITLE_TENANTS_DETAILS="Tenants Details";
    public static final String TITLE_CONTACT_US="Contact us";
    public static final String TITLE_SET_BUILDING_LAYOUT="Set Building Layout";
    public static final String TITLE_ROOM_CONFIGURATION="Room Configuration";
    public static final String TITLE_CAPTURE_OTHER_DETAILS="Facilities Details";
    public static final String TITLE_EDIT_BUILDING_DETAILS="Edit Building Details";
    public static final String TITLE_FILTER_TENANTS="Filter Tenants";
    public static final String TITLE_OCCUPIED="Occupied Status";
    public static final String TITLE_UNOCCUPIED="Un-Occupied Status";
    public static final String TITLE_NOTIFY_TENANT="Notify Tenants";
    public static final String TITLE_EDIT_COST_AND_CHARGES="Edit Cost and Charges";
    public static final String TITLE_ACCEPT_REJECT_TENANTS="Tenants Regisstration";
    public static final String TITLE_EDIT_MEALS_SCHEDULE="Edit Meal Schedule";
    public static final String ALERT_TITLE_DELETE="";

    public static final String CUSTOMER="Customer";
    public static final String OWNER="Owner";
    public static final String OCCUPIED="Occupied";
    public static final String UN_OCCUPIED="Un Occupied";

    public static final String WELCOME="Welcome ";
    public static final String PGs="Pgs";
    public static final String FLAT="Flat";
    public static final String HOTEL="Hotel";

    public static final String TOAST_BACK_FOR_EXIT="Press Back again to Exit.";

    public static final String EXCEPTION_SET_BUILDING_LAYOUT_ACTIVITY="Exception occurred at Set Building Layout";

    public static final String SHARED_PREFERENCE_ADD_PROPERTY_BUILDING_NAME="addProperty_buildingName";
    public static final String SHARED_PREFERENCE_ADD_PROPERTY_ADDRESS_LINE_1="addProperty_addressLine1";
    public static final String SHARED_PREFERENCE_ADD_PROPERTY_ADDRESS_LINE_2="addProperty_addressLine2";
    public static final String SHARED_PREFERENCE_ADD_PROPERTY_POSTAL_CODE="addProperty_postalCode";
    public static final String SHARED_PREFERENCE_ADD_PROPERTY_STATE="addProperty_state";
    public static final String SHARED_PREFERENCE_ADD_PROPERTY_GEO_LOCATION="addProperty_geoLocation";
    public static final String SHARED_PREFERENCE_ADD_PROPERTY_PROPERTY_TYPE="addProperty_propertyType";
    public static final String SHARED_PREFERENCE_PROPERTY_TYPE="propertyType";

    public static final String HINT_NO_OF_ROOMS="No. of Rooms";

    public static final String FLOOR="Floor ";
    public static final String COLON=":";

    public static final int COLOR_HINT_LIGHT_GREY= Color.LTGRAY;
    public static final String COLOR_APPLICATION_BLUE_COLOR="#2E9AFE";

    public static final String ACTIVITY_FINAL_PORTFOLIO="finalPortfolioActivity";
    public static final String ACTIVITY_NOTIFY_TENANT="notifyTenant";
    public static final String ACTIVITY_VIEW_PROPERTY="View Property";
    public static final String ACTIVITY_WELCOME_SCREEN="Welcome Screen";
    public static final String ACTIVITY_MY_PROPERTY ="My Property";
    public static  final String ACTIVITY_TENANT_ACTIVITY="tenantActivity";

    public static final String CURRENTLY_OCCUPIED_ROOMS="Currently Occupied Rooms";
    public static final String CURRENTLY_UNOCCUPIED_ROOMS="Currently Un-Occupied Rooms";

    public static final String TV="TV";
    public static final String FRIDGE="Fridge";
    public static final String GEYSER="Geyser";
    public static final String WASHING_MACHINE="Washing Machine";
    public static final String ALMIRAH="Almirah";
    public static final String BED="Bed";
    public static final String COOLER="Cooler";
    public static final String FAN="Fan";
    public static final String AC="Ac";
    public static final String SOFA="Sofa";
    public static final String CHAIR="Chair";

    public static final String MONDAY="Monday";
    public static final String TUESDAY="Tuesday";
    public static final String WEDNESDAY="Wednesday";
    public static final String THURSDAY="Thursday";
    public static final String FRIDAY="Friday";
    public static final String SATURDAY="Saturday";
    public static final String SUNDAY="Sunday";

    public static final String ACKNOWLEDGED="Acknowleged";
    public static final String DISMISSED="Dismissed";


}
