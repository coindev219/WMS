package com.warehouse.setup.customer.billtoaddress;

public class CustomerBillToAddressRequest {

    private Long id;

    private Long primaryCompanyId;

    private String billTo;

    private Long customerId;

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

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    @Override
    public String toString() {
        return "CustomerBillToAddressRequest [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
                + ", areaDialingCode=" + areaDialingCode + ", billTo=" + billTo + ", cityId=" + cityId
                + ", contactName=" + contactName + ", countryId=" + countryId + ", createdFrom=" + createdFrom
                + ", createdTo=" + createdTo + ", customerId=" + customerId + ", email=" + email + ", extensionNo="
                + extensionNo + ", fax=" + fax + ", id=" + id + ", intlDialCode=" + intlDialCode + ", mobile=" + mobile
                + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo + ", otherCity=" + otherCity
                + ", otherState=" + otherState + ", phone=" + phone + ", postBox=" + postBox + ", postalCode="
                + postalCode + ", primaryCompanyId=" + primaryCompanyId + ", searchFor=" + searchFor + ", stateId="
                + stateId + "]";
    }

}
