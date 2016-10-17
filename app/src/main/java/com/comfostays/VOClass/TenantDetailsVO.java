package com.comfostays.VOClass;

import java.io.Serializable;
import java.util.ArrayList;

public class TenantDetailsVO implements Serializable{

    private String tenantName;
    private String tenantEmailAddress;
    private String tenantContactNumber;
    private String tenantResidingSince;
    private String tenantDescription;
    private String tenantUploadedIdProofs;
    private String tenantResidingProperty;
    private String tenantProfession;
    private String tenantRoomNumber;
    private String tenantStayDuration;
    private String tenantLoggedComplaints;
    private String tenantProfilePic;
    private String listOfIdProofsPicSource;
    private String startDate;
    private String frequencyOfStay;
    private String propertyId;
    private String tenantGender;
    private String tenantDateOfBirth;

    public String getTenantDateOfBirth() {
        return tenantDateOfBirth;
    }

    public void setTenantDateOfBirth(String tenantDateOfBirth) {
        this.tenantDateOfBirth = tenantDateOfBirth;
    }

    private int tenantUserId;

    private boolean isRentPaid;
    private boolean isActive;
    private boolean isApproved;

    public String getTenantGender() {
        return tenantGender;
    }

    public void setTenantGender(String tenantGender) {
        this.tenantGender = tenantGender;
    }

    public int getTenantUserId() {
        return tenantUserId;
    }

    public void setTenantUserId(int tenantUserId) {
        this.tenantUserId = tenantUserId;
    }



    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }




    public String getTenantStayDuration() {
        return tenantStayDuration;
    }

    public void setTenantStayDuration(String tenantStayDuration) {
        this.tenantStayDuration = tenantStayDuration;
    }

    public String getListOfIdProofsPicSource() {
        return listOfIdProofsPicSource;
    }

    public void setListOfIdProofsPicSource(String listOfIdProofsPicSource) {
        this.listOfIdProofsPicSource = listOfIdProofsPicSource;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFrequencyOfStay() {
        return frequencyOfStay;
    }

    public void setFrequencyOfStay(String frequencyOfStay) {
        this.frequencyOfStay = frequencyOfStay;
    }


    public boolean isRentPaid() {
        return isRentPaid;
    }

    public void setRentPaid(boolean rentPaid) {
        isRentPaid = rentPaid;
    }



    public String getTenantProfilePic() {
        return tenantProfilePic;
    }

    public void setTenantProfilePic(String tenantProfilePic) {
        this.tenantProfilePic = tenantProfilePic;
    }



    public String getTenantResidingProperty() {
        return tenantResidingProperty;
    }

    public void setTenantResidingProperty(String tenantResidingProperty) {
        this.tenantResidingProperty = tenantResidingProperty;
    }



    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantEmailAddress() {
        return tenantEmailAddress;
    }

    public void setTenantEmailAddress(String tenantEmailAddress) {
        this.tenantEmailAddress = tenantEmailAddress;
    }

    public String getTenantContactNumber() {
        return tenantContactNumber;
    }

    public void setTenantContactNumber(String tenantContactNumber) {
        this.tenantContactNumber = tenantContactNumber;
    }

    public String getTenantResidingSince() {
        return tenantResidingSince;
    }

    public void setTenantResidingSince(String tenantResidingSince) {
        this.tenantResidingSince = tenantResidingSince;
    }

    public String getTenantDescription() {
        return tenantDescription;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public String getTenantUploadedIdProofs() {
        return tenantUploadedIdProofs;
    }

    public void setTenantUploadedIdProofs(String tenantUploadedIdProofs) {
        this.tenantUploadedIdProofs = tenantUploadedIdProofs;
    }

    public String getTenantProfession() {
        return tenantProfession;
    }

    public void setTenantProfession(String tenantProfession) {
        this.tenantProfession = tenantProfession;
    }

    public String getTenantRoomNumber() {
        return tenantRoomNumber;
    }

    public void setTenantRoomNumber(String tenantRoomNumber) {
        this.tenantRoomNumber = tenantRoomNumber;
    }

    public String getTenanatStayDuration() {
        return tenantStayDuration;
    }

    public void setTenanatStayDuration(String tenanatStayDuration) {
        this.tenantStayDuration = tenanatStayDuration;
    }

    public String getTenantLoggedComplaints() {
        return tenantLoggedComplaints;
    }

    public void setTenantLoggedComplaints(String tenantLoggedComplaints) {
        this.tenantLoggedComplaints = tenantLoggedComplaints;
    }





}
