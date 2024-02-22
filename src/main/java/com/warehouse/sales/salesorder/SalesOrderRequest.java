package com.warehouse.sales.salesorder;

import com.warehouse.enums.ShipModeEnum;
import com.warehouse.enums.TradeTermEnum;
import com.warehouse.enums.TransactionTypeEnum;

public class SalesOrderRequest {

    private Long id;

    private String soNo;
    
    private Long primaryCompanyId;

    private Long costBucketId;

    private Long customerId;

    private TransactionTypeEnum transactionType;

    private String referenceOrderNo;

    private String salesPerson;

    private String email;

    private String phone;

    private Long customerBillToAddressId;

    private Long customerShipToAddressId;

    private Long soTypeId;

    private TradeTermEnum tradeTermEnum;

    private ShipModeEnum shipModeEnum;

    private String fcNo;

    private String foc;

    private Float totalSoAmt;

    private String soDateFrom;

    private String soDateTo;

    private String targetDispatchDateTime;

    private String validateRefSo;

    private Long freighterId;

    private Long vasActivityId;

    private String cod;

    private String allocateFromCrossDock;

    private String searchFor = "";

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private String destinationPort;

    private String customerCode;

    private String referenceSoNo;

    private String estimatedTimeOfDispatch;
    

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getReferenceSoNo() {
        return referenceSoNo;
    }

    public void setReferenceSoNo(String referenceSoNo) {
        this.referenceSoNo = referenceSoNo;
    }

    public String getEstimatedTimeOfDispatch() {
        return estimatedTimeOfDispatch;
    }

    public void setEstimatedTimeOfDispatch(String estimatedTimeOfDispatch) {
        this.estimatedTimeOfDispatch = estimatedTimeOfDispatch;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceOrderNo() {
        return referenceOrderNo;
    }

    public void setReferenceOrderNo(String referenceOrderNo) {
        this.referenceOrderNo = referenceOrderNo;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCustomerBillToAddressId() {
        return customerBillToAddressId;
    }

    public void setCustomerBillToAddressId(Long customerBillToAddressId) {
        this.customerBillToAddressId = customerBillToAddressId;
    }

    public Long getCustomerShipToAddressId() {
        return customerShipToAddressId;
    }

    public void setCustomerShipToAddressId(Long customerShipToAddressId) {
        this.customerShipToAddressId = customerShipToAddressId;
    }

    public Long getSoTypeId() {
        return soTypeId;
    }

    public void setSoTypeId(Long soTypeId) {
        this.soTypeId = soTypeId;
    }

    public TradeTermEnum getTradeTermEnum() {
        return tradeTermEnum;
    }

    public void setTradeTermEnum(TradeTermEnum tradeTermEnum) {
        this.tradeTermEnum = tradeTermEnum;
    }

    public ShipModeEnum getShipModeEnum() {
        return shipModeEnum;
    }

    public void setShipModeEnum(ShipModeEnum shipModeEnum) {
        this.shipModeEnum = shipModeEnum;
    }

    public String getFcNo() {
        return fcNo;
    }

    public void setFcNo(String fcNo) {
        this.fcNo = fcNo;
    }

    public String getFoc() {
        return foc;
    }

    public void setFoc(String foc) {
        this.foc = foc;
    }

    public Float getTotalSoAmt() {
        return totalSoAmt;
    }

    public void setTotalSoAmt(Float totalSoAmt) {
        this.totalSoAmt = totalSoAmt;
    }

    public String getSoDateFrom() {
        return soDateFrom;
    }

    public void setSoDateFrom(String soDateFrom) {
        this.soDateFrom = soDateFrom;
    }

    public String getSoDateTo() {
        return soDateTo;
    }

    public void setSoDateTo(String soDateTo) {
        this.soDateTo = soDateTo;
    }

    public String getTargetDispatchDateTime() {
        return targetDispatchDateTime;
    }

    public void setTargetDispatchDateTime(String targetDispatchDateTime) {
        this.targetDispatchDateTime = targetDispatchDateTime;
    }

    public String getValidateRefSo() {
        return validateRefSo;
    }

    public void setValidateRefSo(String validateRefSo) {
        this.validateRefSo = validateRefSo;
    }

    public Long getVasActivityId() {
        return vasActivityId;
    }

    public void setVasActivityId(Long vasActivityId) {
        this.vasActivityId = vasActivityId;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getAllocateFromCrossDock() {
        return allocateFromCrossDock;
    }

    public void setAllocateFromCrossDock(String allocateFromCrossDock) {
        this.allocateFromCrossDock = allocateFromCrossDock;
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

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Long getFreighterId() {
        return freighterId;
    }

    public void setFreighterId(Long freighterId) {
        this.freighterId = freighterId;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SalesOrderRequest [allocateFromCrossDock=" + allocateFromCrossDock + ", cod=" + cod + ", costBucketId="
                + costBucketId + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo
                + ", customerBillToAddressId=" + customerBillToAddressId + ", customerId=" + customerId
                + ", customerShipToAddressId=" + customerShipToAddressId + ", destinationPort=" + destinationPort
                + ", email=" + email + ", fcNo=" + fcNo + ", foc=" + foc + ", freighterId=" + freighterId + ", id=" + id
                + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo + ", phone=" + phone
                + ", primaryCompanyId=" + primaryCompanyId + ", referenceOrderNo=" + referenceOrderNo + ", salesPerson="
                + salesPerson + ", searchFor=" + searchFor + ", shipModeEnum=" + shipModeEnum + ", soDateFrom="
                + soDateFrom + ", soDateTo=" + soDateTo + ", soNo=" + soNo + ", soTypeId=" + soTypeId
                + ", targetDispatchDateTime=" + targetDispatchDateTime + ", totalSoAmt=" + totalSoAmt
                + ", tradeTermEnum=" + tradeTermEnum + ", transactionType=" + transactionType + ", validateRefSo="
                + validateRefSo + ", vasActivityId=" + vasActivityId + "]";
    }
}
