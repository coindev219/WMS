package com.warehouse.setup.customer;

import java.util.List;

import com.warehouse.helper.PageHelper;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRules;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRulesHeader;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddress;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddressRequest;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiryListResponse;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAge;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAgeListResponse;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiry;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddressRequest;

import org.springframework.data.domain.Page;

public interface CustomerService {

    // Customer
    public String validateCustomerRequest(Customer theCustomer);

    public Customer saveCustomer(Customer theCustomer);

    public Customer findByCustomerId(Long theId);

    public Page<Customer> findAllCustomerList(PageHelper page, CustomerRequest request);

    public void deleteByCustomerId(Long theId);
    

    // Customer Bill To Address
    public String validateCustomerBillToAddressRequest(CustomerBillToAddress theCustomerBillToAddress);

    public CustomerBillToAddress saveCustomerBillToAddress(CustomerBillToAddress theCustomerBillToAddress);

    public CustomerBillToAddress findByCustomerBillToAddressId(Long theId);

    public Page<CustomerBillToAddress> findAllCustomerBillToAddressList(PageHelper page, CustomerBillToAddressRequest request);

    public void deleteByCustomerBillToAddressId(Long theId);


    // Customer Ship To Address
    public String validateCustomerShipToAddressRequest(CustomerShipToAddress theCustomerShipToAddress);

    public CustomerShipToAddress saveCustomerShipToAddress(CustomerShipToAddress theCustomerShipToAddress);

    public CustomerShipToAddress findByCustomerShipToAddressId(Long theId);

    public Page<CustomerShipToAddress> findAllCustomerShipToAddressList(PageHelper page, CustomerShipToAddressRequest request);

    public void deleteByCustomerShipToAddressId(Long theId);


    // Customer Wise Days Expiry

    public String validateCustomerWiseDaysToExpiryRequest(CustomerWiseDaysToExpiry theCustomerWiseDaysToExpiry);

    public List<CustomerWiseDaysToExpiryListResponse> findAllCustomersWithCustomerWiseDaysToExpiry(CustomerRequest request);
    
    public List<CustomerWiseDaysToExpiry> saveCustomerWiseDaysToExpiry(List<CustomerWiseDaysToExpiry> theCustomerWiseDaysToExpiry);

    public CustomerWiseDaysToExpiry findByCustomerWiseDaysToExpiryId(Long theId);

    public void deleteAllCustomerWiseDaysToExpiry(List<CustomerWiseDaysToExpiry> theCustomerWiseDaysToExpiry);


     // Customer Wise Max Age

    public String validateCustomerWiseMaxAgeRequest(CustomerWiseMaxAge theCustomerWiseMaxAge);

    public List<CustomerWiseMaxAgeListResponse> findAllCustomersWithCustomerWiseMaxAge(CustomerRequest request);
    
    public List<CustomerWiseMaxAge> saveCustomerWiseMaxAge(List<CustomerWiseMaxAge> theCustomerWiseMaxAge);

    public CustomerWiseMaxAge findByCustomerWiseMaxAgeId(Long theId);

    public void deleteAllCustomerWiseMaxAge(List<CustomerWiseMaxAge> theCustomerWiseMaxAge);
    

    
    // Warehouse wise allocation priority Rules
    public String validateWarehouseWiseAllocationPriorityRulesRequest(WarehouseWiseAllocationPriorityRulesHeader theWarehouseWiseAllocationPriorityRulesHeader);

    public List<WarehouseWiseAllocationPriorityRulesHeader> saveWarehouseWiseAllocationPriorityRules(List<WarehouseWiseAllocationPriorityRulesHeader> theWarehouseWiseAllocationPriorityRulesHeader);

    public void deleteAllWarehouseWiseAllocationPriorityRules(List<WarehouseWiseAllocationPriorityRules> theWarehouseWiseAllocationPriorityRules);

    public List<WarehouseWiseAllocationPriorityRulesHeader> findWarehousAllocationPriorityRulesWithFilters(CustomerRequest request);
  
}
