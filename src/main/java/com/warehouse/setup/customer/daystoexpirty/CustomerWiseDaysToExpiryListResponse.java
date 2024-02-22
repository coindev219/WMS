package com.warehouse.setup.customer.daystoexpirty;

import java.util.List;

public class CustomerWiseDaysToExpiryListResponse {

    private Long customerId;
    private String customerName;
    private String customerCode;
    private List<CustomerWiseDaysToExpiry> customerWiseDaysToExpiryList;
    
    public CustomerWiseDaysToExpiryListResponse() {
    }
    public CustomerWiseDaysToExpiryListResponse(Long customerId, String customerName, String customerCode) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCode = customerCode;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerCode() {
        return customerCode;
    }
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    public List<CustomerWiseDaysToExpiry> getCustomerWiseDaysToExpiryList() {
        return customerWiseDaysToExpiryList;
    }
    public void setCustomerWiseDaysToExpiryList(List<CustomerWiseDaysToExpiry> customerWiseDaysToExpiryList) {
        this.customerWiseDaysToExpiryList = customerWiseDaysToExpiryList;
    }
    
    @Override
    public String toString() {
        return "CusotmerWiseDaysToExpiryListResponse [customerCode=" + customerCode + ", customerId=" + customerId
                + ", customerName=" + customerName + ", customerWiseDaysToExpiryList=" + customerWiseDaysToExpiryList
                + "]";
    }

    

}
