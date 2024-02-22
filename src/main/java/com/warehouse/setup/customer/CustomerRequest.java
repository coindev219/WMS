package com.warehouse.setup.customer;

import com.warehouse.enums.DurationTimeEnum;

public class CustomerRequest {

    private Long id;

    private Long primaryCompanyId;

    private String name;

    private String code;

    private Long customerTypeId;

    private Long currencyId;

    private String addressLine1;

    private String addressLine2;

    private String contactName;

    private Long countryId;

    private Long stateId;

    private String otherState;

    private Long cityId;

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

    private Long defaultWarehouseForAllocationId;

    private Integer minDaysToExpiry;

    private DurationTimeEnum minDaysToExpiryDurationTime;

    private Integer maxAge;

    private DurationTimeEnum maxAgeDurationTime;

    private String route;

    private String maintainBackOrder;

    private String gstNo;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private String searchFor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
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

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

    public Long getDefaultWarehouseForAllocationId() {
        return defaultWarehouseForAllocationId;
    }

    public void setDefaultWarehouseForAllocationId(Long defaultWarehouseForAllocationId) {
        this.defaultWarehouseForAllocationId = defaultWarehouseForAllocationId;
    }

    public Integer getMinDaysToExpiry() {
        return minDaysToExpiry;
    }

    public void setMinDaysToExpiry(Integer minDaysToExpiry) {
        this.minDaysToExpiry = minDaysToExpiry;
    }

    public DurationTimeEnum getMinDaysToExpiryDurationTime() {
        return minDaysToExpiryDurationTime;
    }

    public void setMinDaysToExpiryDurationTime(DurationTimeEnum minDaysToExpiryDurationTime) {
        this.minDaysToExpiryDurationTime = minDaysToExpiryDurationTime;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public DurationTimeEnum getMaxAgeDurationTime() {
        return maxAgeDurationTime;
    }

    public void setMaxAgeDurationTime(DurationTimeEnum maxAgeDurationTime) {
        this.maxAgeDurationTime = maxAgeDurationTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMaintainBackOrder() {
        return maintainBackOrder;
    }

    public void setMaintainBackOrder(String maintainBackOrder) {
        this.maintainBackOrder = maintainBackOrder;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
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

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    @Override
    public String toString() {
        return "CustomerRequest [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", areaDialingCode="
                + areaDialingCode + ", cityId=" + cityId + ", code=" + code + ", contactName=" + contactName
                + ", countryId=" + countryId + ", createdTo=" + createdTo + ", currencyId=" + currencyId
                + ", customerTypeId=" + customerTypeId + ", defaultWarehouseForAllocationId="
                + defaultWarehouseForAllocationId + ", email=" + email + ", extensionNo=" + extensionNo + ", fax=" + fax
                + ", gstNo=" + gstNo + ", id=" + id + ", intlDialCode=" + intlDialCode + ", maintainBackOrder="
                + maintainBackOrder + ", maxAge=" + maxAge + ", maxAgeDurationTime=" + maxAgeDurationTime
                + ", minDaysToExpiry=" + minDaysToExpiry + ", minDaysToExpiryDurationTime="
                + minDaysToExpiryDurationTime + ", mobile=" + mobile + ", modifiedFrom=" + modifiedFrom
                + ", modifiedTo=" + modifiedTo + ", name=" + name + ", otherCity=" + otherCity + ", otherState="
                + otherState + ", phone=" + phone + ", postBox=" + postBox + ", postalCode=" + postalCode
                + ", primaryCompanyId=" + primaryCompanyId + ", route=" + route + ", searchFor=" + searchFor
                + ", stateId=" + stateId + "]";
    }

    

    
}
