package com.warehouse.administration.user;

import com.warehouse.enums.UserTypeEnum;

public class UserRequest {

    private Long id;

    private Long warehouseId;

    private Long primaryCompanyId;

    private Long costBucketId;

    private String employeeNumber;

    private String employeeName;

    private String nonTransactionalUser;

    private String username;

    private UserTypeEnum userType;

    private Long customerId;

    private Long supplierId;

    private Long userRoleId;

    private String addressLine1;

    private String addressLine2;

    private String contactName;

    private Long countryId;

    private Long stateId;

    private Long cityId;

    private String otherState;

    private String otherCity;

    private String postBox;

    private String postalCode;

    private String intlDialCode;

    private String areaDialingCode;

    private String phone;

    private String extensionNo;

    private String fax;

    private String mobile;

    private String email;

    private String searchFor = "";

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private String isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public Long getCostBucketId() {
        return costBucketId;
    }

    public void setCostBucketId(Long costBucketId) {
        this.costBucketId = costBucketId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getNonTransactionalUser() {
        return nonTransactionalUser;
    }

    public void setNonTransactionalUser(String nonTransactionalUser) {
        this.nonTransactionalUser = nonTransactionalUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
    }

    public String getOtherCity() {
        return otherCity;
    }

    public void setOtherCity(String otherCity) {
        this.otherCity = otherCity;
    }

    public String getPostBox() {
        return postBox;
    }

    public void setPostBox(String postBox) {
        this.postBox = postBox;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getIntlDialCode() {
        return intlDialCode;
    }

    public void setIntlDialCode(String intlDialCode) {
        this.intlDialCode = intlDialCode;
    }

    public String getAreaDialingCode() {
        return areaDialingCode;
    }

    public void setAreaDialingCode(String areaDialingCode) {
        this.areaDialingCode = areaDialingCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtensionNo() {
        return extensionNo;
    }

    public void setExtensionNo(String extensionNo) {
        this.extensionNo = extensionNo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(String createdTo) {
        this.createdTo = createdTo;
    }

    public String getModifiedFrom() {
        return modifiedFrom;
    }

    public void setModifiedFrom(String modifiedFrom) {
        this.modifiedFrom = modifiedFrom;
    }

    public String getModifiedTo() {
        return modifiedTo;
    }

    public void setModifiedTo(String modifiedTo) {
        this.modifiedTo = modifiedTo;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "UserRequest [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", areaDialingCode="
                + areaDialingCode + ", cityId=" + cityId + ", contactName=" + contactName + ", costBucketId="
                + costBucketId + ", countryId=" + countryId + ", createdFrom=" + createdFrom + ", createdTo="
                + createdTo + ", customerId=" + customerId + ", email=" + email + ", employeeName=" + employeeName
                + ", employeeNumber=" + employeeNumber + ", extensionNo=" + extensionNo + ", fax=" + fax + ", id=" + id
                + ", intlDialCode=" + intlDialCode + ", isActive=" + isActive + ", mobile=" + mobile + ", modifiedFrom="
                + modifiedFrom + ", modifiedTo=" + modifiedTo + ", nonTransactionalUser=" + nonTransactionalUser
                + ", otherCity=" + otherCity + ", otherState=" + otherState + ", phone=" + phone + ", postBox="
                + postBox + ", postalCode=" + postalCode + ", primaryCompanyId=" + primaryCompanyId + ", searchFor="
                + searchFor + ", stateId=" + stateId + ", supplierId=" + supplierId + ", userRoleId=" + userRoleId
                + ", userType=" + userType + ", username=" + username + ", warehouseId=" + warehouseId + "]";
    }

}
