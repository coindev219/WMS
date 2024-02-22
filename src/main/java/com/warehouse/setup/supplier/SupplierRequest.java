package com.warehouse.setup.supplier;

import com.warehouse.enums.ShipModeEnum;
import com.warehouse.enums.TradeTermEnum;

public class SupplierRequest {

    private Long id;

    private Long primaryCompanyId;

    private Long warehouseId;

    private String name;

    private String code;

    private String supplierCategory;

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

    private TradeTermEnum tradeTerm;

    private ShipModeEnum forecastOrderShipMode;

    private ShipModeEnum backOrderShipMode;

    private Long freighterId;

    private Long defaultPoTypeId;

    private Long currencyId;

    private String shipTo;

    private String gstNo;

    private String searchFor;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;


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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
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

    public TradeTermEnum getTradeTerm() {
        return tradeTerm;
    }

    public void setTradeTerm(TradeTermEnum tradeTerm) {
        this.tradeTerm = tradeTerm;
    }

    public ShipModeEnum getForecastOrderShipMode() {
        return forecastOrderShipMode;
    }

    public void setForecastOrderShipMode(ShipModeEnum forecastOrderShipMode) {
        this.forecastOrderShipMode = forecastOrderShipMode;
    }

    public ShipModeEnum getBackOrderShipMode() {
        return backOrderShipMode;
    }

    public void setBackOrderShipMode(ShipModeEnum backOrderShipMode) {
        this.backOrderShipMode = backOrderShipMode;
    }

    public Long getFreighterId() {
        return freighterId;
    }

    public void setFreighterId(Long freighterId) {
        this.freighterId = freighterId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }


    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
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

    public Long getDefaultPoTypeId() {
        return defaultPoTypeId;
    }

    public void setDefaultPoTypeId(Long defaultPoTypeId) {
        this.defaultPoTypeId = defaultPoTypeId;
    }

    @Override
    public String toString() {
        return "SupplierRequest [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", areaDialingCode="
                + areaDialingCode + ", backOrderShipMode=" + backOrderShipMode + ", cityId=" + cityId + ", code=" + code
                + ", contactName=" + contactName + ", countryId=" + countryId + ", createdFrom=" + createdFrom
                + ", createdTo=" + createdTo + ", currencyId=" + currencyId + ", defaultPoTypeId=" + defaultPoTypeId
                + ", email=" + email + ", extensionNo=" + extensionNo + ", fax=" + fax + ", forecastOrderShipMode="
                + forecastOrderShipMode + ", freighterId=" + freighterId + ", gstNo=" + gstNo + ", id=" + id
                + ", intlDialCode=" + intlDialCode + ", mobile=" + mobile + ", modifiedFrom=" + modifiedFrom
                + ", modifiedTo=" + modifiedTo + ", name=" + name + ", otherCity=" + otherCity + ", otherState="
                + otherState + ", phone=" + phone + ", postBox=" + postBox + ", postalCode=" + postalCode
                + ", primaryCompanyId=" + primaryCompanyId + ", searchFor=" + searchFor + ", shipTo=" + shipTo
                + ", stateId=" + stateId + ", supplierCategory=" + supplierCategory + ", tradeTerm=" + tradeTerm
                + ", warehouseId=" + warehouseId + "]";
    }

    

   

}
