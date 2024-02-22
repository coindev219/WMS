package com.warehouse.setup.warehouse;

import java.sql.Timestamp;

public class WarehouseRequest {
    public String addressLine1;
    public String addressLine2;
    public String areaDialingCode;
    public String code;
    public String contactName;
    public Timestamp createdAt;
    public String cstn;
    public String docNoPrefix;
    public String email;
    public String exportLicenseNo;
    public String extensionNo;
    public String fax;
    public String gstNo;
    public String intlDialCode;
    public String mobile;
    public Timestamp modifiedAt;
    public String name;
    public String otherCity;
    public String otherState;
    public String phone;
    public String postBox;
    public String postalCode;
    public String shipTo;
    public String taxInvoiceStatement;
    public String tin;
    public String tradeLicenseNo;
    public Long countryId;
    public Long stateId;
    public Long cityId;
    private String searchFor = "";
    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public String getAreaDialingCode() {
        return areaDialingCode;
    }

    public void setAreaDialingCode(String areaDialingCode) {
        this.areaDialingCode = areaDialingCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCstn() {
        return cstn;
    }

    public void setCstn(String cstn) {
        this.cstn = cstn;
    }

    public String getDocNoPrefix() {
        return docNoPrefix;
    }

    public void setDocNoPrefix(String docNoPrefix) {
        this.docNoPrefix = docNoPrefix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExportLicenseNo() {
        return exportLicenseNo;
    }

    public void setExportLicenseNo(String exportLicenseNo) {
        this.exportLicenseNo = exportLicenseNo;
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

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getIntlDialCode() {
        return intlDialCode;
    }

    public void setIntlDialCode(String intlDialCode) {
        this.intlDialCode = intlDialCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherCity() {
        return otherCity;
    }

    public void setOtherCity(String otherCity) {
        this.otherCity = otherCity;
    }

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getTaxInvoiceStatement() {
        return taxInvoiceStatement;
    }

    public void setTaxInvoiceStatement(String taxInvoiceStatement) {
        this.taxInvoiceStatement = taxInvoiceStatement;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
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

    @Override
    public String toString() {
        return "WarehouseRequest [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
                + ", areaDialingCode=" + areaDialingCode + ", cityId=" + cityId + ", code=" + code + ", contactName="
                + contactName + ", countryId=" + countryId + ", createdAt=" + createdAt + ", createdFrom=" + createdFrom
                + ", createdTo=" + createdTo + ", cstn=" + cstn + ", docNoPrefix=" + docNoPrefix + ", email=" + email
                + ", exportLicenseNo=" + exportLicenseNo + ", extensionNo=" + extensionNo + ", fax=" + fax + ", gstNo="
                + gstNo + ", intlDialCode=" + intlDialCode + ", mobile=" + mobile + ", modifiedAt=" + modifiedAt
                + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo + ", name=" + name + ", otherCity="
                + otherCity + ", otherState=" + otherState + ", phone=" + phone + ", postBox=" + postBox
                + ", postalCode=" + postalCode + ", searchFor=" + searchFor + ", shipTo=" + shipTo + ", stateId="
                + stateId + ", taxInvoiceStatement=" + taxInvoiceStatement + ", tin=" + tin + ", tradeLicenseNo="
                + tradeLicenseNo + "]";
    }

}
