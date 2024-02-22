package com.warehouse.additionalsetup.uom;

import com.warehouse.enums.UomCategoryEnum;

public class UomRequest {

    private Long id;

    private String uom;

    private String name;

    private UomCategoryEnum uomCategory;

    private String conversionFactor;

    private String allowFractions;

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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UomCategoryEnum getUomCategory() {
        return uomCategory;
    }

    public void setUomCategory(UomCategoryEnum uomCategory) {
        this.uomCategory = uomCategory;
    }

    public String getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(String conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getAllowFractions() {
        return allowFractions;
    }

    public void setAllowFractions(String allowFractions) {
        this.allowFractions = allowFractions;
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
        return "UomRequest [allowFractions=" + allowFractions + ", conversionFactor=" + conversionFactor
                + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo + ", id=" + id + ", modifiedFrom="
                + modifiedFrom + ", modifiedTo=" + modifiedTo + ", name=" + name + ", searchFor=" + searchFor + ", uom="
                + uom + ", uomCategory=" + uomCategory + "]";
    }

    

}
