package com.warehouse.setup.customer.daystoexpirty;

import com.warehouse.enums.DurationTimeEnum;

public class CustomerWiseDaysToExpiryRequest {

    private Long id;

    private Long customerId;

    private Long skuId;

    private Integer daysToExpiry;

    private DurationTimeEnum daysToExpiryUnit = DurationTimeEnum.Select;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getDaysToExpiry() {
        return daysToExpiry;
    }

    public void setDaysToExpiry(Integer daysToExpiry) {
        this.daysToExpiry = daysToExpiry;
    }

    public DurationTimeEnum getDaysToExpiryUnit() {
        return daysToExpiryUnit;
    }

    public void setDaysToExpiryUnit(DurationTimeEnum daysToExpiryUnit) {
        this.daysToExpiryUnit = daysToExpiryUnit;
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
        return "CustomerWiseDaysToExpirtyRequest [createdFrom=" + createdFrom + ", createdTo=" + createdTo
                + ", customerId=" + customerId + ", daysToExpiry=" + daysToExpiry + ", daysToExpiryUnit="
                + daysToExpiryUnit + ", id=" + id + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo
                + ", searchFor=" + searchFor + ", skuId=" + skuId + "]";
    }

    

}
