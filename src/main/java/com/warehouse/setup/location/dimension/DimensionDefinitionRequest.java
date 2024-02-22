package com.warehouse.setup.location.dimension;

public class DimensionDefinitionRequest {
    
    
	private String name;
	private String code;
    private Float length;
    private Float weight;
    private Float height;
    private Float width;
    private String searchFor = "";
    private String createdFrom;
    private String createdTo;
    private String modifiedFrom;
    private String modifiedTo;
    
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
    public Float getLength() {
        return length;
    }
    public void setLength(Float length) {
        this.length = length;
    }
    public Float getWeight() {
        return weight;
    }
    public void setWeight(Float weight) {
        this.weight = weight;
    }
    public Float getHeight() {
        return height;
    }
    public void setHeight(Float height) {
        this.height = height;
    }
    public Float getWidth() {
        return width;
    }
    public void setWidth(Float width) {
        this.width = width;
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
        return "DimensionDefinitionRequest [code=" + code + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo
                + ", height=" + height + ", length=" + length + ", modifiedFrom=" + modifiedFrom + ", modifiedTo="
                + modifiedTo + ", name=" + name + ", searchFor=" + searchFor + ", weight=" + weight + ", width=" + width
                + "]";
    }
    

    
}
