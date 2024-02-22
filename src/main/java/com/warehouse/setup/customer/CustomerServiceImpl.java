package com.warehouse.setup.customer;

import java.util.List;
import java.util.Objects;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketRepository;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.ordertype.OrderTypeRepository;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.skutype.SkuTypeRepository;
import com.warehouse.city.City;
import com.warehouse.city.CityRepository;
import com.warehouse.country.Country;
import com.warehouse.country.CountryRepository;
import com.warehouse.currency.Currency;
import com.warehouse.currency.CurrencyRepository;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.customertype.CustomerType;
import com.warehouse.setup.company.customertype.CustomerTypeRepository;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRules;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRulesHeader;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRulesHeaderRepository;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRulesRepository;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddress;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddressRepository;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddressRequest;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddressSearchRepository;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiry;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiryListResponse;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiryRepository;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAge;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAgeListResponse;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAgeRepository;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddressRepository;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddressRequest;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddressSearchRepository;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.SkuRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRepository;
import com.warehouse.state.State;
import com.warehouse.state.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

        @Autowired
        private PrimaryCompanyRepository primaryCompanyRepository;

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private CustomerSearchRepository customerSearchRepository;

        @Autowired
        private WarehouseRepository warehouseRepository;

        @Autowired
        private CurrencyRepository currencyRepository;

        @Autowired
        private StateRepository stateRepository;

        @Autowired
        private CityRepository cityRepository;

        @Autowired
        private CountryRepository countryRepository;

        @Autowired
        private CustomerTypeRepository customerTypeRepository;

        @Autowired
        private CustomerBillToAddressRepository customerBillToAddressRepository;

        @Autowired
        private CustomerBillToAddressSearchRepository customerBillToAddressSearchRepository;

        @Autowired
        private CustomerShipToAddressRepository customerShipToAddressRepository;

        @Autowired
        private CustomerShipToAddressSearchRepository customerShipToAddressSearchRepository;

        @Autowired
        private SkuRepository skuRepository;

        @Autowired
        private CustomerWiseDaysToExpiryRepository customerWiseDaysToExpiryRepository;

        @Autowired
        private CustomerWiseMaxAgeRepository customerWiseMaxAgeRepository;

        @Autowired
        private WarehouseWiseAllocationPriorityRulesHeaderRepository warehouseWiseAllocationPriorityRulesHeaderRepository;

        @Autowired
        private CostBucketRepository costBucketRepository;

        @Autowired
        private OrderTypeRepository orderTypeRepository;

        @Autowired
        private SkuTypeRepository skuTypeRepository;

        @Autowired
        private WarehouseWiseAllocationPriorityRulesRepository warehouseWiseAllocationPriorityRulesRepository;

        @Override
        public String validateCustomerRequest(Customer theCustomer) {
                PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theCustomer.getPrimaryCompanyId())
                                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                                theCustomer.getPrimaryCompanyId()));
                theCustomer.setPrimaryCompany(primaryCompany);

                Warehouse warehouse = warehouseRepository.findById(theCustomer.getDefaultWarehouseForAllocationId())
                                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id",
                                                theCustomer.getDefaultWarehouseForAllocationId()));
                theCustomer.setDefaultWarehouseForAllocation(warehouse);

                Currency currency = currencyRepository.findById(theCustomer.getCurrencyId()).orElseThrow(
                                () -> new ResourceNotFoundException("Currency", "id", theCustomer.getCurrencyId()));
                theCustomer.setCurrency(currency);

                Country country = countryRepository.findById(theCustomer.getCountryId()).orElseThrow(
                                () -> new ResourceNotFoundException("Country", "id", theCustomer.getCountryId()));
                theCustomer.setCountry(country);

                if (Objects.nonNull(theCustomer.getCustomerTypeId())) {
                        CustomerType customerType = customerTypeRepository.findById(theCustomer.getCustomerTypeId())
                                        .orElseThrow(() -> new ResourceNotFoundException("CustomerType", "id",
                                                        theCustomer.getCustomerTypeId()));
                        theCustomer.setCustomerType(customerType);
                }
                if (Objects.nonNull(theCustomer.getStateId())) {
                        State state = stateRepository.findById(theCustomer.getStateId()).orElseThrow(
                                        () -> new ResourceNotFoundException("State", "id", theCustomer.getStateId()));
                        theCustomer.setState(state);
                }
                if (Objects.nonNull(theCustomer.getCityId())) {
                        City city = cityRepository.findById(theCustomer.getCityId()).orElseThrow(
                                        () -> new ResourceNotFoundException("City", "id", theCustomer.getCityId()));
                        theCustomer.setCity(city);
                }

                if (theCustomer.getId() == null) {
                        if (customerRepository.findByNameAndPrimaryCompanyId(theCustomer.getName(),
                                        theCustomer.getPrimaryCompanyId()).isPresent()) {
                                return "Duplicate value found for Customer with name:" + theCustomer.getName()
                                                + " and Primary Company:" + primaryCompany.getName();
                        }
                        if (customerRepository.findByCodeAndPrimaryCompanyId(theCustomer.getCode(),
                                        theCustomer.getPrimaryCompanyId()).isPresent()) {
                                return "Duplicate value found for Customer with code:" + theCustomer.getCode()
                                                + " and Primary Company:" + primaryCompany.getName();
                        }
                        if (customerRepository.findByContactNameAndPrimaryCompanyId(theCustomer.getContactName(),
                                        theCustomer.getPrimaryCompanyId()).isPresent()) {
                                return "Duplicate value found for Customer with contactName:"
                                                + theCustomer.getContactName() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                } else {
                        if (customerRepository
                                        .findByNameAndPrimaryCompanyIdAndIdNot(theCustomer.getName(),
                                                        theCustomer.getPrimaryCompanyId(), theCustomer.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer with Id:" + theCustomer.getId()
                                                + " and name:" + theCustomer.getName() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                        if (customerRepository
                                        .findByCodeAndPrimaryCompanyIdAndIdNot(theCustomer.getCode(),
                                                        theCustomer.getPrimaryCompanyId(), theCustomer.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer with Id:" + theCustomer.getId()
                                                + " and code:" + theCustomer.getCode() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                        if (customerRepository
                                        .findByContactNameAndPrimaryCompanyIdAndIdNot(theCustomer.getContactName(),
                                                        theCustomer.getPrimaryCompanyId(), theCustomer.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer with Id:" + theCustomer.getId()
                                                + " and code:" + theCustomer.getContactName() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                }
                return null;
        }

        @Override
        public Customer saveCustomer(Customer theCustomer) {
                return customerRepository.save(theCustomer);
        }

        @Override
        public Customer findByCustomerId(Long theId) {
                return customerRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", theId));
        }

        @Override
        public Page<Customer> findAllCustomerList(PageHelper page, CustomerRequest request) {
                return customerSearchRepository.findAllWithFilters(page, request);
        }

        @Override
        public void deleteByCustomerId(Long theId) {
                customerRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", theId));
                customerRepository.deleteById(theId);

        }

        @Override
        public String validateCustomerBillToAddressRequest(CustomerBillToAddress theCustomerBillToAddress) {
                PrimaryCompany primaryCompany = primaryCompanyRepository
                                .findById(theCustomerBillToAddress.getPrimaryCompanyId())
                                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                                theCustomerBillToAddress.getPrimaryCompanyId()));
                theCustomerBillToAddress.setPrimaryCompany(primaryCompany);

                Customer customer = customerRepository.findById(theCustomerBillToAddress.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id",
                                                theCustomerBillToAddress.getCustomerId()));
                theCustomerBillToAddress.setCustomer(customer);

                Country country = countryRepository.findById(theCustomerBillToAddress.getCountryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Country", "id",
                                                theCustomerBillToAddress.getCountryId()));
                theCustomerBillToAddress.setCountry(country);

                if (Objects.nonNull(theCustomerBillToAddress.getStateId())) {
                        State state = stateRepository.findById(theCustomerBillToAddress.getStateId())
                                        .orElseThrow(() -> new ResourceNotFoundException("State", "id",
                                                        theCustomerBillToAddress.getStateId()));
                        theCustomerBillToAddress.setState(state);
                }
                if (Objects.nonNull(theCustomerBillToAddress.getCityId())) {
                        City city = cityRepository.findById(theCustomerBillToAddress.getCityId())
                                        .orElseThrow(() -> new ResourceNotFoundException("City", "id",
                                                        theCustomerBillToAddress.getCityId()));
                        theCustomerBillToAddress.setCity(city);
                }

                if (theCustomerBillToAddress.getId() == null) {
                        if (customerBillToAddressRepository
                                        .findByBillToAndCustomerId(theCustomerBillToAddress.getBillTo(),
                                                        theCustomerBillToAddress.getCustomerId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer Bill To Address with Bill To:"
                                                + theCustomerBillToAddress.getBillTo() + " and Customer:"
                                                + customer.getName();
                        }

                        if (customerBillToAddressRepository
                                        .findByContactNameAndCustomerId(theCustomerBillToAddress.getContactName(),
                                                        theCustomerBillToAddress.getCustomerId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer Bill To Address with contactName:"
                                                + theCustomerBillToAddress.getContactName() + " and Customer:"
                                                + customer.getName();
                        }
                } else {

                        if (customerBillToAddressRepository.findByBillToAndCustomerIdAndIdNot(
                                        theCustomerBillToAddress.getBillTo(), theCustomerBillToAddress.getCustomerId(),
                                        theCustomerBillToAddress.getId()).isPresent()) {
                                return "Duplicate value found for Customer Bill To Address with Id:"
                                                + theCustomerBillToAddress.getId() + " and Bill To:"
                                                + theCustomerBillToAddress.getBillTo() + " and Customer:"
                                                + customer.getName();
                        }

                        if (customerBillToAddressRepository.findByContactNameAndCustomerIdAndIdNot(
                                        theCustomerBillToAddress.getContactName(),
                                        theCustomerBillToAddress.getCustomerId(), theCustomerBillToAddress.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer Bill To Address with Id:"
                                                + theCustomerBillToAddress.getId() + " and Contact name:"
                                                + theCustomerBillToAddress.getContactName() + " and Customer:"
                                                + customer.getName();
                        }
                }
                return null;
        }

        @Override
        public CustomerBillToAddress saveCustomerBillToAddress(CustomerBillToAddress theCustomerBillToAddress) {
                return customerBillToAddressRepository.save(theCustomerBillToAddress);
        }

        @Override
        public CustomerBillToAddress findByCustomerBillToAddressId(Long theId) {
                return customerBillToAddressRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("CustomerBillToAddress", "id", theId));
        }

        @Override
        public Page<CustomerBillToAddress> findAllCustomerBillToAddressList(PageHelper page,
                        CustomerBillToAddressRequest request) {
                return customerBillToAddressSearchRepository.findAllWithFilters(page, request);
        }

        @Override
        public void deleteByCustomerBillToAddressId(Long theId) {
                customerBillToAddressRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("CustomerBillToAddress", "id", theId));
                customerBillToAddressRepository.deleteById(theId);

        }

        @Override
        public String validateCustomerShipToAddressRequest(CustomerShipToAddress theCustomerShipToAddress) {
                PrimaryCompany primaryCompany = primaryCompanyRepository
                                .findById(theCustomerShipToAddress.getPrimaryCompanyId())
                                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                                theCustomerShipToAddress.getPrimaryCompanyId()));
                theCustomerShipToAddress.setPrimaryCompany(primaryCompany);

                Warehouse warehouse = warehouseRepository
                                .findById(theCustomerShipToAddress.getDefaultWarehouseForAllocationId())
                                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id",
                                                theCustomerShipToAddress.getDefaultWarehouseForAllocationId()));
                theCustomerShipToAddress.setDefaultWarehouseForAllocation(warehouse);

                Customer customer = customerRepository.findById(theCustomerShipToAddress.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id",
                                                theCustomerShipToAddress.getCustomerId()));
                theCustomerShipToAddress.setCustomer(customer);

                Country country = countryRepository.findById(theCustomerShipToAddress.getCountryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Country", "id",
                                                theCustomerShipToAddress.getCountryId()));
                theCustomerShipToAddress.setCountry(country);

                if (Objects.nonNull(theCustomerShipToAddress.getStateId())) {
                        State state = stateRepository.findById(theCustomerShipToAddress.getStateId())
                                        .orElseThrow(() -> new ResourceNotFoundException("State", "id",
                                                        theCustomerShipToAddress.getStateId()));
                        theCustomerShipToAddress.setState(state);
                }
                if (Objects.nonNull(theCustomerShipToAddress.getCityId())) {
                        City city = cityRepository.findById(theCustomerShipToAddress.getCityId())
                                        .orElseThrow(() -> new ResourceNotFoundException("City", "id",
                                                        theCustomerShipToAddress.getCityId()));
                        theCustomerShipToAddress.setCity(city);
                }

                if (theCustomerShipToAddress.getId() == null) {
                        if (customerShipToAddressRepository
                                        .findByShipToAndCustomerId(theCustomerShipToAddress.getShipTo(),
                                                        theCustomerShipToAddress.getCustomerId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer Ship To Address with Bill To:"
                                                + theCustomerShipToAddress.getShipTo() + " and Customer:"
                                                + customer.getName();
                        }

                        if (customerShipToAddressRepository
                                        .findByContactNameAndCustomerId(theCustomerShipToAddress.getContactName(),
                                                        theCustomerShipToAddress.getCustomerId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer Ship To Address with contactName:"
                                                + theCustomerShipToAddress.getContactName() + " and Customer:"
                                                + customer.getName();
                        }
                } else {

                        if (customerShipToAddressRepository.findByShipToAndCustomerIdAndIdNot(
                                        theCustomerShipToAddress.getShipTo(), theCustomerShipToAddress.getCustomerId(),
                                        theCustomerShipToAddress.getId()).isPresent()) {
                                return "Duplicate value found for Customer Ship To Address with Id:"
                                                + theCustomerShipToAddress.getId() + " and Bill To:"
                                                + theCustomerShipToAddress.getShipTo() + " and Customer:"
                                                + customer.getName();
                        }

                        if (customerShipToAddressRepository.findByContactNameAndCustomerIdAndIdNot(
                                        theCustomerShipToAddress.getContactName(),
                                        theCustomerShipToAddress.getCustomerId(), theCustomerShipToAddress.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Customer Ship To Address with Id:"
                                                + theCustomerShipToAddress.getId() + " and Contact name:"
                                                + theCustomerShipToAddress.getContactName() + " and Customer:"
                                                + customer.getName();
                        }
                }
                return null;
        }

        @Override
        public CustomerShipToAddress saveCustomerShipToAddress(CustomerShipToAddress theCustomerShipToAddress) {
                return customerShipToAddressRepository.save(theCustomerShipToAddress);
        }

        @Override
        public CustomerShipToAddress findByCustomerShipToAddressId(Long theId) {
                return customerShipToAddressRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("CustomerShipToAddress", "id", theId));
        }

        @Override
        public Page<CustomerShipToAddress> findAllCustomerShipToAddressList(PageHelper page,
                        CustomerShipToAddressRequest request) {
                return customerShipToAddressSearchRepository.findAllWithFilters(page, request);
        }

        @Override
        public void deleteByCustomerShipToAddressId(Long theId) {
                customerShipToAddressRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("CustomerShipToAddress", "id", theId));
                customerShipToAddressRepository.deleteById(theId);

        }

        @Override
        public String validateCustomerWiseDaysToExpiryRequest(CustomerWiseDaysToExpiry theCustomerWiseDaysToExpiry) {
                Customer customer = customerRepository.findById(theCustomerWiseDaysToExpiry.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id",
                                                theCustomerWiseDaysToExpiry.getCustomerId()));
                theCustomerWiseDaysToExpiry.setCustomer(customer);

                Sku sku = skuRepository.findById(theCustomerWiseDaysToExpiry.getSkuId())
                                .orElseThrow(() -> new ResourceNotFoundException("Sku", "id",
                                                theCustomerWiseDaysToExpiry.getSkuId()));
                theCustomerWiseDaysToExpiry.setSku(sku);

                if (theCustomerWiseDaysToExpiry.getId() == null) {
                        if (customerWiseDaysToExpiryRepository
                                        .findByCustomerIdAndSkuId(theCustomerWiseDaysToExpiry.getCustomerId(),
                                                        theCustomerWiseDaysToExpiry.getSkuId())
                                        .isPresent()) {
                                return "Duplicate value found with Customer Name:"
                                                + theCustomerWiseDaysToExpiry.getCustomer().getName() + " and Sku:"
                                                + theCustomerWiseDaysToExpiry.getSku().getName();
                        }
                } else {
                        if (customerWiseDaysToExpiryRepository
                                        .findByCustomerIdAndSkuIdAndIdNot(theCustomerWiseDaysToExpiry.getCustomerId(),
                                                        theCustomerWiseDaysToExpiry.getSkuId(),
                                                        theCustomerWiseDaysToExpiry.getId())
                                        .isPresent()) {
                                return "Duplicate value found with ID " + theCustomerWiseDaysToExpiry.getId()
                                                + "and Customer Name:"
                                                + theCustomerWiseDaysToExpiry.getCustomer().getName() + " and Sku:"
                                                + theCustomerWiseDaysToExpiry.getSku().getName();
                        }

                }

                return null;
        }

        @Override
        public List<CustomerWiseDaysToExpiryListResponse> findAllCustomersWithCustomerWiseDaysToExpiry(
                        CustomerRequest request) {
                return customerSearchRepository.findAllWithFilters(request);
        }

        @Override
        public List<CustomerWiseDaysToExpiry> saveCustomerWiseDaysToExpiry(
                        List<CustomerWiseDaysToExpiry> theCustomerWiseDaysToExpiry) {
                return customerWiseDaysToExpiryRepository.saveAll(theCustomerWiseDaysToExpiry);
        }

        @Override
        public CustomerWiseDaysToExpiry findByCustomerWiseDaysToExpiryId(Long theId) {
                return customerWiseDaysToExpiryRepository.findById(theId).orElseThrow(
                                () -> new ResourceNotFoundException("CustomerWiseDaysToExpiry", "id", theId));
        }

        @Override
        public void deleteAllCustomerWiseDaysToExpiry(List<CustomerWiseDaysToExpiry> theCustomerWiseDaysToExpiry) {
                customerWiseDaysToExpiryRepository.deleteAll(theCustomerWiseDaysToExpiry);

        }

        @Override
        public String validateCustomerWiseMaxAgeRequest(CustomerWiseMaxAge theCustomerWiseMaxAge) {
                Customer customer = customerRepository.findById(theCustomerWiseMaxAge.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id",
                                                theCustomerWiseMaxAge.getCustomerId()));
                theCustomerWiseMaxAge.setCustomer(customer);

                Sku sku = skuRepository.findById(theCustomerWiseMaxAge.getSkuId()).orElseThrow(
                                () -> new ResourceNotFoundException("Sku", "id", theCustomerWiseMaxAge.getSkuId()));
                theCustomerWiseMaxAge.setSku(sku);

                if (theCustomerWiseMaxAge.getId() == null) {
                        if (customerWiseMaxAgeRepository.findByCustomerIdAndSkuId(theCustomerWiseMaxAge.getCustomerId(),
                                        theCustomerWiseMaxAge.getSkuId()).isPresent()) {
                                return "Duplicate value found with Customer Name:"
                                                + theCustomerWiseMaxAge.getCustomer().getName() + " and Sku:"
                                                + theCustomerWiseMaxAge.getSku().getName();
                        }
                } else {
                        if (customerWiseMaxAgeRepository
                                        .findByCustomerIdAndSkuIdAndIdNot(theCustomerWiseMaxAge.getCustomerId(),
                                                        theCustomerWiseMaxAge.getSkuId(), theCustomerWiseMaxAge.getId())
                                        .isPresent()) {
                                return "Duplicate value found with ID " + theCustomerWiseMaxAge.getId()
                                                + "and Customer Name:" + theCustomerWiseMaxAge.getCustomer().getName()
                                                + " and Sku:" + theCustomerWiseMaxAge.getSku().getName();
                        }

                }

                return null;
        }

        @Override
        public List<CustomerWiseMaxAgeListResponse> findAllCustomersWithCustomerWiseMaxAge(CustomerRequest request) {
                return customerSearchRepository.findAllWithFiltersCustomerWiseMaxAge(request);
        }

        @Override
        public List<CustomerWiseMaxAge> saveCustomerWiseMaxAge(List<CustomerWiseMaxAge> theCustomerWiseMaxAge) {
                return customerWiseMaxAgeRepository.saveAll(theCustomerWiseMaxAge);
        }

        @Override
        public CustomerWiseMaxAge findByCustomerWiseMaxAgeId(Long theId) {
                return customerWiseMaxAgeRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("CustomerWiseMaxAge", "id", theId));
        }

        @Override
        public void deleteAllCustomerWiseMaxAge(List<CustomerWiseMaxAge> theCustomerWiseMaxAge) {
                customerWiseMaxAgeRepository.deleteAll(theCustomerWiseMaxAge);
        }

        @Override
        public String validateWarehouseWiseAllocationPriorityRulesRequest(
                        WarehouseWiseAllocationPriorityRulesHeader theWarehouseWiseAllocationPriorityRulesHeader) {

                PrimaryCompany primaryCompany = primaryCompanyRepository
                                .findById(theWarehouseWiseAllocationPriorityRulesHeader.getPrimaryCompanyId())
                                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                                theWarehouseWiseAllocationPriorityRulesHeader.getPrimaryCompanyId()));
                theWarehouseWiseAllocationPriorityRulesHeader.setPrimaryCompany(primaryCompany);

                Customer customer = customerRepository
                                .findById(theWarehouseWiseAllocationPriorityRulesHeader.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id",
                                                theWarehouseWiseAllocationPriorityRulesHeader.getCustomerId()));
                theWarehouseWiseAllocationPriorityRulesHeader.setCustomer(customer);

                CostBucket costBucket = costBucketRepository
                                .findById(theWarehouseWiseAllocationPriorityRulesHeader.getCostBucketId())
                                .orElseThrow(() -> new ResourceNotFoundException("CostBucket", "id",
                                                theWarehouseWiseAllocationPriorityRulesHeader.getCostBucketId()));
                theWarehouseWiseAllocationPriorityRulesHeader.setCostBucket(costBucket);

                CustomerShipToAddress customerShipToAddress = customerShipToAddressRepository
                                .findById(theWarehouseWiseAllocationPriorityRulesHeader.getShipToId())
                                .orElseThrow(() -> new ResourceNotFoundException("CustomerShipToAddress", "id",
                                                theWarehouseWiseAllocationPriorityRulesHeader.getShipToId()));
                theWarehouseWiseAllocationPriorityRulesHeader.setShipTo(customerShipToAddress);

                OrderType orderType = orderTypeRepository
                                .findById(theWarehouseWiseAllocationPriorityRulesHeader.getOrderTypeId())
                                .orElseThrow(() -> new ResourceNotFoundException("OrderType", "id",
                                                theWarehouseWiseAllocationPriorityRulesHeader.getOrderTypeId()));
                theWarehouseWiseAllocationPriorityRulesHeader.setOrderType(orderType);

                SkuType skuType = skuTypeRepository
                                .findById(theWarehouseWiseAllocationPriorityRulesHeader.getSkuTypeId())
                                .orElseThrow(() -> new ResourceNotFoundException("SkuType", "id",
                                                theWarehouseWiseAllocationPriorityRulesHeader.getSkuTypeId()));
                theWarehouseWiseAllocationPriorityRulesHeader.setSkuType(skuType);

                List<WarehouseWiseAllocationPriorityRules> warehouseWiseAllocationPriorityRules = theWarehouseWiseAllocationPriorityRulesHeader
                                .getWarehouseWiseAllocationPriorityRules();

                for (WarehouseWiseAllocationPriorityRules warehouseWiseAllocationPriorityRulesObj : warehouseWiseAllocationPriorityRules) {
                        Warehouse warehouse = warehouseRepository
                                        .findById(warehouseWiseAllocationPriorityRulesObj.getWarehouseId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id",
                                                        warehouseWiseAllocationPriorityRulesObj.getWarehouseId()));
                        warehouseWiseAllocationPriorityRulesObj.setWarehouse(warehouse);
                }

                if (theWarehouseWiseAllocationPriorityRulesHeader.getId() == null) {
                        if (warehouseWiseAllocationPriorityRulesHeaderRepository
                                        .findByPrimaryCompanyIdAndCostBucketIdAndShipToIdAndOrderTypeIdAndSkuTypeIdAndShippingMode(
                                                        theWarehouseWiseAllocationPriorityRulesHeader
                                                                        .getPrimaryCompanyId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getCostBucketId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getShipToId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getOrderTypeId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getSkuTypeId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getShippingMode())
                                        .isPresent()) {
                                return "Duplicate value found with Primary Company:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getPrimaryCompany()
                                                                .getName()
                                                + " , Cost Bucket:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getCostBucket()
                                                                .getName()
                                                + " , Ship To:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getShipTo().getShipTo()
                                                + " , Order Type:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getOrderType().getName()
                                                + " , Sku Type:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getSkuType().getName()
                                                + " and Shipping Mode:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getShippingMode();
                        }
                } else {
                        if (warehouseWiseAllocationPriorityRulesHeaderRepository
                                        .findByPrimaryCompanyIdAndCostBucketIdAndShipToIdAndOrderTypeIdAndSkuTypeIdAndShippingModeAndIdNot(
                                                        theWarehouseWiseAllocationPriorityRulesHeader
                                                                        .getPrimaryCompanyId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getCostBucketId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getShipToId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getOrderTypeId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getSkuTypeId(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getShippingMode(),
                                                        theWarehouseWiseAllocationPriorityRulesHeader.getId())
                                        .isPresent()) {
                                return "Duplicate value found with Primary Company:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getPrimaryCompany()
                                                                .getName()
                                                + " , Cost Bucket:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getCostBucket()
                                                                .getName()
                                                + " , Ship To:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getShipTo().getShipTo()
                                                + " , Order Type:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getOrderType().getName()
                                                + " , Sku Type:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getSkuType().getName()
                                                + " and Shipping Mode:"
                                                + theWarehouseWiseAllocationPriorityRulesHeader.getShippingMode();
                        }

                }

                return null;
        }

        @Override
        public List<WarehouseWiseAllocationPriorityRulesHeader> saveWarehouseWiseAllocationPriorityRules(
                        List<WarehouseWiseAllocationPriorityRulesHeader> theWarehouseWiseAllocationPriorityRulesHeader) {
                return warehouseWiseAllocationPriorityRulesHeaderRepository
                                .saveAll(theWarehouseWiseAllocationPriorityRulesHeader);
        }

        @Override
        public void deleteAllWarehouseWiseAllocationPriorityRules(List<WarehouseWiseAllocationPriorityRules> theWarehouseWiseAllocationPriorityRules) {

                Long headerId =  null;
                if (theWarehouseWiseAllocationPriorityRules.size() > 0){
                        WarehouseWiseAllocationPriorityRules warehouseWiseAllocationPriorityRules = theWarehouseWiseAllocationPriorityRules.get(0);
                        Long id = warehouseWiseAllocationPriorityRules.getId();
                        if(id != null){
                                warehouseWiseAllocationPriorityRules = warehouseWiseAllocationPriorityRulesRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("WarehouseWiseAllocationPriorityRules", "id", id));
                                headerId = warehouseWiseAllocationPriorityRules.getWarehouseWiseAllocationPriorityRulesHeaderId();
                        }                        
                }
                warehouseWiseAllocationPriorityRulesRepository.deleteAll(theWarehouseWiseAllocationPriorityRules);

                if (headerId != null) {
                        Long mHeaderId = headerId;
                        WarehouseWiseAllocationPriorityRulesHeader warehouseWiseAllocationPriorityRulesHeader = warehouseWiseAllocationPriorityRulesHeaderRepository.findById(headerId)
                                .orElseThrow(() -> new ResourceNotFoundException("WarehouseWiseAllocationPriorityRulesHeader", "id", mHeaderId));
                        if(warehouseWiseAllocationPriorityRulesHeader.getWarehouseWiseAllocationPriorityRules().size() == 0){
                                warehouseWiseAllocationPriorityRulesHeaderRepository.deleteById(headerId);
                        }
                }
        }

        @Override
        public List<WarehouseWiseAllocationPriorityRulesHeader> findWarehousAllocationPriorityRulesWithFilters(
                        CustomerRequest request) {
               return customerSearchRepository.findWarehousAllocationPriorityRulesWithHeader(request);
        }

}
