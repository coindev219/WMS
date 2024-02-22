package com.warehouse.setup.customer.maxage;

import java.util.List;

public class CustomerWiseMaxAgeListResponse {

    private Long customerId;
    private String customerName;
    private String customerCode;
    private List<CustomerWiseMaxAge> customerWiseMaxAgeList;
    
    public CustomerWiseMaxAgeListResponse() {
    }

    public CustomerWiseMaxAgeListResponse(Long customerId, String customerName, String customerCode) {
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
    
    public List<CustomerWiseMaxAge> getCustomerWiseMaxAgeList() {
        return customerWiseMaxAgeList;
    }
    public void setCustomerWiseMaxAgeList(List<CustomerWiseMaxAge> customerWiseMaxAgeList) {
        this.customerWiseMaxAgeList = customerWiseMaxAgeList;
    }

    @Override
    public String toString() {
        return "CustomerWiseMaxAgeResponse [customerCode=" + customerCode + ", customerId=" + customerId
                + ", customerName=" + customerName + ", customerWiseMaxAgeList=" + customerWiseMaxAgeList + "]";
    }

}
