package com.comfostays.validateprogress;

import com.comfostays.Constants;
import com.comfostays.VOClass.PropertyDetailsVO;

public class CustomerTestClass {

    public PropertyDetailsVO getPropertyDetails(){



        PropertyDetailsVO propertyDetails=new PropertyDetailsVO();
        propertyDetails.setPropertyId(1);
        propertyDetails.setPropertyName("SN Plaza");
        propertyDetails.setAddressLineOne("G2 Anjiah Nagar");
        propertyDetails.setAddressLineTwo("Gachibowli, Hyderabad");
        propertyDetails.setPostalCode(500032);
        propertyDetails.setState("Hyderabad");
        propertyDetails.setPropertyType(Constants.PGs);
        propertyDetails.setMealOffered(true);
        propertyDetails.setMealScheduleAvailable(true);
        propertyDetails.setFacilitiesProvided("TV:GYM:4 Wheeler Parking");
        propertyDetails.setRoomTypes("Single Sharing:Double Sharing:Triple Sharing");

      return propertyDetails;
    }
}
