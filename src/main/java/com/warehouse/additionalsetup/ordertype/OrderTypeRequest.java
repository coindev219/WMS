package com.warehouse.additionalsetup.ordertype;

import com.warehouse.enums.TransactionTypeEnum;

public class OrderTypeRequest {

    private Long id;

    private String code;

    private String name;

    private String poType;

    private String soType;

    private String extStockTransfer;

    private String allowDangerLvlUnlocking;

    private String explodeKitBom;

    private TransactionTypeEnum transactionType;

    private Long primaryCompanyId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoType() {
        return poType;
    }

    public void setPoType(String poType) {
        this.poType = poType;
    }

    public String getSoType() {
        return soType;
    }

    public void setSoType(String soType) {
        this.soType = soType;
    }

    public String getExtStockTransfer() {
        return extStockTransfer;
    }

    public void setExtStockTransfer(String extStockTransfer) {
        this.extStockTransfer = extStockTransfer;
    }

    public String getAllowDangerLvlUnlocking() {
        return allowDangerLvlUnlocking;
    }

    public void setAllowDangerLvlUnlocking(String allowDangerLvlUnlocking) {
        this.allowDangerLvlUnlocking = allowDangerLvlUnlocking;
    }

    public String getExplodeKitBom() {
        return explodeKitBom;
    }

    public void setExplodeKitBom(String explodeKitBom) {
        this.explodeKitBom = explodeKitBom;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
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
        return "OrderTypeRequest [allowDangerLvlUnlocking=" + allowDangerLvlUnlocking + ", code=" + code
                + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo + ", explodeKitBom=" + explodeKitBom
                + ", extStockTransfer=" + extStockTransfer + ", id=" + id + ", modifiedFrom=" + modifiedFrom
                + ", modifiedTo=" + modifiedTo + ", name=" + name + ", poType=" + poType + ", primaryCompanyId="
                + primaryCompanyId + ", searchFor=" + searchFor + ", soType=" + soType + ", transactionType="
                + transactionType + "]";
    }

    

}
