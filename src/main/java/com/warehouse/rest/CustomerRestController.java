package com.warehouse.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.customer.CustomerListAction;
import com.warehouse.setup.customer.CustomerRequest;
import com.warehouse.setup.customer.CustomerService;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRules;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRulesHeader;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddress;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddressListAction;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddressRequest;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiry;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiryListResponse;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAge;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAgeListResponse;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddressListAction;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddressRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setup/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public Customer addCustomer(@Valid @RequestBody Customer theCustomer) {
        String error = customerService.validateCustomerRequest(theCustomer);
        if (error != null) {
            throw new InvalidValueFoundException("Customer", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomer.setCreatedBy(user);
        theCustomer.setModifiedBy(user);

        theCustomer = customerService.saveCustomer(theCustomer);
        return theCustomer;
    }

    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable(value = "id") Long theId, @Valid @RequestBody Customer theCustomer) {
        theCustomer.setId(theId);
        Customer detailObj = customerService.findByCustomerId(theId);

        String error = customerService.validateCustomerRequest(theCustomer);
        if (error != null) {
            throw new InvalidValueFoundException("Customer", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomer.setCreatedBy(detailObj.getCreatedBy());
        theCustomer.setModifiedBy(user);

        theCustomer.setCreatedAt(detailObj.getCreatedAt());
        detailObj = customerService.saveCustomer(theCustomer);
        return detailObj;
    }

    @PutMapping("/update")
    public CustomerListAction updateCustomerList(@Valid @RequestBody CustomerListAction listAction) {

        CustomerListAction responseListAction = new CustomerListAction();
        for (Customer singleObj : listAction) {
            customerService.findByCustomerId(singleObj.getId());
            String error = customerService.validateCustomerRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("Customer", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (Customer singleObj : listAction) {
            Customer searchedObj = customerService.findByCustomerId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(customerService.saveCustomer(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/detail/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Long theId) {
        return customerService.findByCustomerId(theId);
    }

    @PostMapping("/list/search")
    public ResponseEntity<Page<Customer>> getCustomerList(PageHelper page, @RequestBody CustomerRequest request) {
        return new ResponseEntity<>(customerService.findAllCustomerList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") Long theId) {
        customerService.deleteByCustomerId(theId);
        return ResponseEntity.ok().build();
    }

    // Customer Bill To Address

    @PostMapping("/bill-to-address/save")
    public CustomerBillToAddress addCustomerBillToAddress(
            @Valid @RequestBody CustomerBillToAddress theCustomerBillToAddress) {
        String error = customerService.validateCustomerBillToAddressRequest(theCustomerBillToAddress);
        if (error != null) {
            throw new InvalidValueFoundException("CustomerBillToAddress", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomerBillToAddress.setCreatedBy(user);
        theCustomerBillToAddress.setModifiedBy(user);

        theCustomerBillToAddress = customerService.saveCustomerBillToAddress(theCustomerBillToAddress);
        return theCustomerBillToAddress;
    }

    @PutMapping("/bill-to-address/update/{id}")
    public CustomerBillToAddress updateCustomerBillToAddress(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody CustomerBillToAddress theCustomerBillToAddress) {
        theCustomerBillToAddress.setId(theId);
        CustomerBillToAddress detailObj = customerService.findByCustomerBillToAddressId(theId);

        String error = customerService.validateCustomerBillToAddressRequest(theCustomerBillToAddress);
        if (error != null) {
            throw new InvalidValueFoundException("CustomerBillToAddress", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomerBillToAddress.setCreatedBy(detailObj.getCreatedBy());
        theCustomerBillToAddress.setModifiedBy(user);

        theCustomerBillToAddress.setCreatedAt(detailObj.getCreatedAt());
        detailObj = customerService.saveCustomerBillToAddress(theCustomerBillToAddress);
        return detailObj;
    }

    @PutMapping("/bill-to-address/update")
    public CustomerBillToAddressListAction updateCustomerBillToAddressList(
            @Valid @RequestBody CustomerBillToAddressListAction listAction) {

        CustomerBillToAddressListAction responseListAction = new CustomerBillToAddressListAction();
        for (CustomerBillToAddress singleObj : listAction) {
            customerService.findByCustomerBillToAddressId(singleObj.getId());
            String error = customerService.validateCustomerBillToAddressRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("CustomerBillToAddress", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (CustomerBillToAddress singleObj : listAction) {
            CustomerBillToAddress searchedObj = customerService.findByCustomerBillToAddressId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(customerService.saveCustomerBillToAddress(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/bill-to-address/detail/{id}")
    public CustomerBillToAddress getCustomerBillToAddressById(@PathVariable(value = "id") Long theId) {
        return customerService.findByCustomerBillToAddressId(theId);
    }

    @PostMapping("/bill-to-address/list/search")
    public ResponseEntity<Page<CustomerBillToAddress>> getCustomerBillToAddressList(PageHelper page,
            @RequestBody CustomerBillToAddressRequest request) {
        return new ResponseEntity<>(customerService.findAllCustomerBillToAddressList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/bill-to-address/delete/{id}")
    public ResponseEntity<?> deleteCustomerBillToAddress(@PathVariable(value = "id") Long theId) {
        customerService.deleteByCustomerBillToAddressId(theId);
        return ResponseEntity.ok().build();
    }

    // Customer Ship To Address

    @PostMapping("/ship-to-address/save")
    public CustomerShipToAddress addCustomerShipToAddress(
            @Valid @RequestBody CustomerShipToAddress theCustomerShipToAddress) {
        String error = customerService.validateCustomerShipToAddressRequest(theCustomerShipToAddress);
        if (error != null) {
            throw new InvalidValueFoundException("CustomerShipToAddress", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomerShipToAddress.setCreatedBy(user);
        theCustomerShipToAddress.setModifiedBy(user);

        theCustomerShipToAddress = customerService.saveCustomerShipToAddress(theCustomerShipToAddress);
        return theCustomerShipToAddress;
    }

    @PutMapping("/ship-to-address/update/{id}")
    public CustomerShipToAddress updateCustomerShipToAddress(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody CustomerShipToAddress theCustomerShipToAddress) {
        theCustomerShipToAddress.setId(theId);
        CustomerShipToAddress detailObj = customerService.findByCustomerShipToAddressId(theId);

        String error = customerService.validateCustomerShipToAddressRequest(theCustomerShipToAddress);
        if (error != null) {
            throw new InvalidValueFoundException("CustomerShipToAddress", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomerShipToAddress.setCreatedBy(detailObj.getCreatedBy());
        theCustomerShipToAddress.setModifiedBy(user);

        theCustomerShipToAddress.setCreatedAt(detailObj.getCreatedAt());
        detailObj = customerService.saveCustomerShipToAddress(theCustomerShipToAddress);
        return detailObj;
    }

    @PutMapping("/ship-to-address/update")
    public CustomerShipToAddressListAction updateCustomerShipToAddressList(
            @Valid @RequestBody CustomerShipToAddressListAction listAction) {

        CustomerShipToAddressListAction responseListAction = new CustomerShipToAddressListAction();
        for (CustomerShipToAddress singleObj : listAction) {
            customerService.findByCustomerShipToAddressId(singleObj.getId());
            String error = customerService.validateCustomerShipToAddressRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("CustomerShipToAddress", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (CustomerShipToAddress singleObj : listAction) {
            CustomerShipToAddress searchedObj = customerService.findByCustomerShipToAddressId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(customerService.saveCustomerShipToAddress(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/ship-to-address/detail/{id}")
    public CustomerShipToAddress getCustomerShipToAddressById(@PathVariable(value = "id") Long theId) {
        return customerService.findByCustomerShipToAddressId(theId);
    }

    @PostMapping("/ship-to-address/list/search")
    public ResponseEntity<Page<CustomerShipToAddress>> getCustomerShipToAddressList(PageHelper page,
            @RequestBody CustomerShipToAddressRequest request) {
        return new ResponseEntity<>(customerService.findAllCustomerShipToAddressList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/ship-to-address/delete/{id}")
    public ResponseEntity<?> deleteCustomerShipToAddress(@PathVariable(value = "id") Long theId) {
        customerService.deleteByCustomerShipToAddressId(theId);
        return ResponseEntity.ok().build();
    }

    // Customer Wise Days To Expiry

    @PostMapping("/days-to-expiry/search")
    public ResponseEntity<List<CustomerWiseDaysToExpiryListResponse>> getAllCustomersWithDaysToExpiryList(
            @RequestBody CustomerRequest request) {
        return new ResponseEntity<>(customerService.findAllCustomersWithCustomerWiseDaysToExpiry(request),
                HttpStatus.OK);
    }

    @PostMapping("/days-to-expiry")
    public List<CustomerWiseDaysToExpiry> addUpdateCustomerWiseDaysToExpiry(
            @Valid @RequestBody List<CustomerWiseDaysToExpiry> customerWiseDaysToExpiryList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (CustomerWiseDaysToExpiry customerWiseDaysToExpiry : customerWiseDaysToExpiryList) {
            String error = customerService.validateCustomerWiseDaysToExpiryRequest(customerWiseDaysToExpiry);
            if (error != null) {
                throw new InvalidValueFoundException("CustomerWiseDaysToExpiry", error);
            }

            String duplicateKeyValue = customerWiseDaysToExpiry.getCustomerId() + ""
                    + customerWiseDaysToExpiry.getSkuId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found with Customer Name:" + customerWiseDaysToExpiry.getCustomer().getName()
                        + " and Sku:" + customerWiseDaysToExpiry.getSku().getName();
                throw new InvalidValueFoundException("CustomerWiseDaysToExpiry", error);
            }

            arrayList.add(duplicateKeyValue);
        }

        for (CustomerWiseDaysToExpiry customerWiseDaysToExpiry : customerWiseDaysToExpiryList) {

            User user = WarehouseHelper.getLoggedInUser();
            customerWiseDaysToExpiry.setCreatedBy(user);
            customerWiseDaysToExpiry.setModifiedBy(user);

            if (customerWiseDaysToExpiry.getId() != null) {
                CustomerWiseDaysToExpiry theCustomerWiseDaysToExpiry = customerService
                        .findByCustomerWiseDaysToExpiryId(customerWiseDaysToExpiry.getId());
                customerWiseDaysToExpiry.setCreatedAt(theCustomerWiseDaysToExpiry.getCreatedAt());
                customerWiseDaysToExpiry.setCreatedBy(theCustomerWiseDaysToExpiry.getCreatedBy());
            }
        }
        return customerService.saveCustomerWiseDaysToExpiry(customerWiseDaysToExpiryList);
    }

    @DeleteMapping("/days-to-expiry")
    public ResponseEntity<?> deleteAllCustomerWiseDaysToExpiry(
            @RequestBody List<CustomerWiseDaysToExpiry> customerWiseDaysToExpiryList) {
        customerService.deleteAllCustomerWiseDaysToExpiry(customerWiseDaysToExpiryList);
        return ResponseEntity.ok().build();
    }

    // Customer Wise Max Age

    @PostMapping("/max-age/search")
    public ResponseEntity<List<CustomerWiseMaxAgeListResponse>> getAllCustomersWithMaxAgeList(
            @RequestBody CustomerRequest request) {
        return new ResponseEntity<>(customerService.findAllCustomersWithCustomerWiseMaxAge(request), HttpStatus.OK);
    }

    @PostMapping("/max-age")
    public List<CustomerWiseMaxAge> addUpdateCustomerWiseMaxAge(
            @Valid @RequestBody List<CustomerWiseMaxAge> customerWiseDaysToExpiryList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (CustomerWiseMaxAge customerWiseDaysToExpiry : customerWiseDaysToExpiryList) {
            String error = customerService.validateCustomerWiseMaxAgeRequest(customerWiseDaysToExpiry);
            if (error != null) {
                throw new InvalidValueFoundException("CustomerWiseMaxAge", error);
            }

            String duplicateKeyValue = customerWiseDaysToExpiry.getCustomerId() + ""
                    + customerWiseDaysToExpiry.getSkuId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found with Customer Name:" + customerWiseDaysToExpiry.getCustomer().getName()
                        + " and Sku:" + customerWiseDaysToExpiry.getSku().getName();
                throw new InvalidValueFoundException("CustomerWiseMaxAge", error);
            }

            arrayList.add(duplicateKeyValue);
        }

        for (CustomerWiseMaxAge customerWiseDaysToExpiry : customerWiseDaysToExpiryList) {

            User user = WarehouseHelper.getLoggedInUser();
            customerWiseDaysToExpiry.setCreatedBy(user);
            customerWiseDaysToExpiry.setModifiedBy(user);

            if (customerWiseDaysToExpiry.getId() != null) {
                CustomerWiseMaxAge theCustomerWiseMaxAge = customerService
                        .findByCustomerWiseMaxAgeId(customerWiseDaysToExpiry.getId());
                customerWiseDaysToExpiry.setCreatedAt(theCustomerWiseMaxAge.getCreatedAt());
                customerWiseDaysToExpiry.setCreatedBy(theCustomerWiseMaxAge.getCreatedBy());
            }
        }
        return customerService.saveCustomerWiseMaxAge(customerWiseDaysToExpiryList);
    }

    @DeleteMapping("/max-age")
    public ResponseEntity<?> deleteAllCustomerWiseMaxAge(
            @RequestBody List<CustomerWiseMaxAge> customerWiseDaysToExpiryList) {
        customerService.deleteAllCustomerWiseMaxAge(customerWiseDaysToExpiryList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/warehouse-wise-allocation-priority-rule")
    public List<WarehouseWiseAllocationPriorityRulesHeader> addUpdateWarehouseWiseAllocationPriorityRule(
            @Valid @RequestBody List<WarehouseWiseAllocationPriorityRulesHeader> theWarehouseWiseAllocationPriorityRulesHeaderList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (WarehouseWiseAllocationPriorityRulesHeader warehouseWiseAllocationPriorityRulesHeader : theWarehouseWiseAllocationPriorityRulesHeaderList) {
            String error = customerService
                    .validateWarehouseWiseAllocationPriorityRulesRequest(warehouseWiseAllocationPriorityRulesHeader);
            if (error != null) {
                throw new InvalidValueFoundException("WarehouseWiseAllocationPriorityRulesHeader", error);
            }

            String duplicateKeyValue = warehouseWiseAllocationPriorityRulesHeader.getPrimaryCompanyId() + ""
                    + warehouseWiseAllocationPriorityRulesHeader.getCostBucketId()
                    + warehouseWiseAllocationPriorityRulesHeader.getShipToId()
                    + warehouseWiseAllocationPriorityRulesHeader.getOrderTypeId()
                    + warehouseWiseAllocationPriorityRulesHeader.getSkuTypeId()
                    + warehouseWiseAllocationPriorityRulesHeader.getShippingMode();

            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found with Primary Company:"
                        + warehouseWiseAllocationPriorityRulesHeader.getPrimaryCompany().getName() + " , Cost Bucket:"
                        + warehouseWiseAllocationPriorityRulesHeader.getCostBucket().getName() + " , Ship To:"
                        + warehouseWiseAllocationPriorityRulesHeader.getShipTo().getShipTo() + " , Order Type:"
                        + warehouseWiseAllocationPriorityRulesHeader.getOrderType().getName() + " , Sku Type:"
                        + warehouseWiseAllocationPriorityRulesHeader.getSkuType().getName() + " and Shipping Mode:"
                        + warehouseWiseAllocationPriorityRulesHeader.getShippingMode();
                throw new InvalidValueFoundException("WarehouseWiseAllocationPriorityRulesHeader", error);
            }

            arrayList.add(duplicateKeyValue);

        }

        for (WarehouseWiseAllocationPriorityRulesHeader warehouseWiseAllocationPriorityRulesHeader : theWarehouseWiseAllocationPriorityRulesHeaderList) {

            User user = WarehouseHelper.getLoggedInUser();
            warehouseWiseAllocationPriorityRulesHeader.setCreatedBy(user);
            warehouseWiseAllocationPriorityRulesHeader.setModifiedBy(user);

            List<WarehouseWiseAllocationPriorityRules> warehouseWiseAllocationPriorityRules = warehouseWiseAllocationPriorityRulesHeader
                    .getWarehouseWiseAllocationPriorityRules();

            for (WarehouseWiseAllocationPriorityRules warehouseWiseAllocationPriorityRulesObj : warehouseWiseAllocationPriorityRules) {
                warehouseWiseAllocationPriorityRulesObj.setCreatedBy(user);
                warehouseWiseAllocationPriorityRulesObj.setModifiedBy(user);
            }
        }
        return customerService
                .saveWarehouseWiseAllocationPriorityRules(theWarehouseWiseAllocationPriorityRulesHeaderList);
    }

    @DeleteMapping("/warehouse-wise-allocation-priority-rule")
    public ResponseEntity<?> deleteAllWarehouseWiseAllocationPriorityRules(
            @RequestBody List<WarehouseWiseAllocationPriorityRules> theWarehouseWiseAllocationPriorityRules) {
        customerService.deleteAllWarehouseWiseAllocationPriorityRules(theWarehouseWiseAllocationPriorityRules);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/warehouse-wise-allocation-priority-rule/search")
    public ResponseEntity<List<WarehouseWiseAllocationPriorityRulesHeader>> getAllWarehousAllocationPriorityRulesWithHeader(
            @RequestBody CustomerRequest request) {
        return new ResponseEntity<>(customerService.findWarehousAllocationPriorityRulesWithFilters(request),
                HttpStatus.OK);
    }

}
