package com.warehouse.setup.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.administration.user.User;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.currency.Currency;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.customertype.CustomerType;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRules;
import com.warehouse.setup.customer.allocationpriorityrules.WarehouseWiseAllocationPriorityRulesHeader;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpiryListResponse;
import com.warehouse.setup.customer.daystoexpirty.CustomerWiseDaysToExpirySearchRepository;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAgeListResponse;
import com.warehouse.setup.customer.maxage.CustomerWiseMaxAgeSearchRepository;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    private CustomerWiseDaysToExpirySearchRepository customerWiseDaysToExpirtySearchRepository;

    @Autowired
    private CustomerWiseMaxAgeSearchRepository customerWiseMaxAgeSearchRepository;

    public CustomerSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Customer> findAllWithFilters(PageHelper page, CustomerRequest request) {
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> rootObj = criteriaQuery.from(Customer.class);

        Join<Customer, Warehouse> toWarehoueJoin = rootObj.join("defaultWarehouseForAllocation");
        Join<Customer, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Customer, Country> toCountryJoin = rootObj.join("country");
        Join<Customer, Currency> toCurrencyJoin = rootObj.join("currency");
        Join<Customer, CustomerType> toCustomerTypeJoin = rootObj.join("customerType", JoinType.LEFT);
        Join<Customer, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<Customer, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        Join<Customer, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Customer, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), rootObj.get("name"), rootObj.get("code"),
                toCustomerTypeJoin.get("id"), toCustomerTypeJoin.get("name"), toCustomerTypeJoin.get("code"),
                toCurrencyJoin.get("id"), toCurrencyJoin.get("name"), rootObj.get("addressLine1"),
                rootObj.get("addressLine2"), rootObj.get("contactName"), toCountryJoin.get("id"),
                toCountryJoin.get("countryName"), toStateJoin.get("id"), toStateJoin.get("stateName"),
                rootObj.get("otherState"), toCityJoin.get("id"), toCityJoin.get("cityName"), rootObj.get("otherCity"),
                rootObj.get("postBox"), rootObj.get("postalCode"), rootObj.get("intlDialCode"),
                rootObj.get("areaDialingCode"), rootObj.get("phone"), rootObj.get("extensionNo"), rootObj.get("fax"),
                rootObj.get("mobile"), rootObj.get("email"), toWarehoueJoin.get("id"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), rootObj.get("minDaysToExpiry"), rootObj.get("minDaysToExpiryDurationTime"),
                rootObj.get("maxAge"), rootObj.get("maxAgeDurationTime"), rootObj.get("route"),
                rootObj.get("maintainBackOrder"), rootObj.get("gstNo"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(rootObj.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, rootObj);

            if (Objects.nonNull(request.getDefaultWarehouseForAllocationId())) {
                predicates.add(
                        criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getDefaultWarehouseForAllocationId()));
            }

            if (Objects.nonNull(request.getCustomerTypeId())) {
                predicates.add(criteriaBuilder.equal(toCustomerTypeJoin.get("id"), request.getCustomerTypeId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCurrencyId())) {
                predicates.add(criteriaBuilder.equal(toCurrencyJoin.get("id"), request.getCurrencyId()));
            }

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }
            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));
            }
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<Customer> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<CustomerWiseDaysToExpiryListResponse> findAllWithFilters(CustomerRequest request) {
        CriteriaQuery<CustomerWiseDaysToExpiryListResponse> criteriaQuery = criteriaBuilder.createQuery(CustomerWiseDaysToExpiryListResponse.class);
        Root<Customer> rootObj = criteriaQuery.from(Customer.class);

        Join<Customer, Warehouse> toWarehoueJoin = rootObj.join("defaultWarehouseForAllocation");
        Join<Customer, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Customer, Country> toCountryJoin = rootObj.join("country");
        Join<Customer, Currency> toCurrencyJoin = rootObj.join("currency");
        Join<Customer, CustomerType> toCustomerTypeJoin = rootObj.join("customerType", JoinType.LEFT);
        Join<Customer, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<Customer, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code")
                );

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(rootObj.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, rootObj);

            if (Objects.nonNull(request.getDefaultWarehouseForAllocationId())) {
                predicates.add(
                        criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getDefaultWarehouseForAllocationId()));
            }

            if (Objects.nonNull(request.getCustomerTypeId())) {
                predicates.add(criteriaBuilder.equal(toCustomerTypeJoin.get("id"), request.getCustomerTypeId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCurrencyId())) {
                predicates.add(criteriaBuilder.equal(toCurrencyJoin.get("id"), request.getCurrencyId()));
            }

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }
            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));
            }
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        
        TypedQuery<CustomerWiseDaysToExpiryListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<CustomerWiseDaysToExpiryListResponse> responses = typedQuery.getResultList();
        for (CustomerWiseDaysToExpiryListResponse cusotmerWiseDaysToExpiryListResponse : responses) {
            cusotmerWiseDaysToExpiryListResponse.setCustomerWiseDaysToExpiryList(customerWiseDaysToExpirtySearchRepository.findAllWithFilters(cusotmerWiseDaysToExpiryListResponse.getCustomerId()));
        }
        return responses;
    }

    public List<CustomerWiseMaxAgeListResponse> findAllWithFiltersCustomerWiseMaxAge(CustomerRequest request) {
        CriteriaQuery<CustomerWiseMaxAgeListResponse> criteriaQuery = criteriaBuilder.createQuery(CustomerWiseMaxAgeListResponse.class);
        Root<Customer> rootObj = criteriaQuery.from(Customer.class);

        Join<Customer, Warehouse> toWarehoueJoin = rootObj.join("defaultWarehouseForAllocation");
        Join<Customer, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Customer, Country> toCountryJoin = rootObj.join("country");
        Join<Customer, Currency> toCurrencyJoin = rootObj.join("currency");
        Join<Customer, CustomerType> toCustomerTypeJoin = rootObj.join("customerType", JoinType.LEFT);
        Join<Customer, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<Customer, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code")
                );

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(rootObj.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, rootObj);

            if (Objects.nonNull(request.getDefaultWarehouseForAllocationId())) {
                predicates.add(
                        criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getDefaultWarehouseForAllocationId()));
            }

            if (Objects.nonNull(request.getCustomerTypeId())) {
                predicates.add(criteriaBuilder.equal(toCustomerTypeJoin.get("id"), request.getCustomerTypeId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCurrencyId())) {
                predicates.add(criteriaBuilder.equal(toCurrencyJoin.get("id"), request.getCurrencyId()));
            }

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }
            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));
            }
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        
        TypedQuery<CustomerWiseMaxAgeListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<CustomerWiseMaxAgeListResponse> responses = typedQuery.getResultList();
        for (CustomerWiseMaxAgeListResponse customerWiseMaxAgeListResponse : responses) {
            customerWiseMaxAgeListResponse.setCustomerWiseMaxAgeList(customerWiseMaxAgeSearchRepository.findAllWithFilters(customerWiseMaxAgeListResponse.getCustomerId()));
        }
        return responses;
    }

    public List<WarehouseWiseAllocationPriorityRulesHeader> findWarehousAllocationPriorityRulesWithHeader(CustomerRequest request) {
        CriteriaQuery<WarehouseWiseAllocationPriorityRulesHeader> criteriaQuery = criteriaBuilder.createQuery(WarehouseWiseAllocationPriorityRulesHeader.class);
        Root<WarehouseWiseAllocationPriorityRulesHeader> rootObj = criteriaQuery.from(WarehouseWiseAllocationPriorityRulesHeader.class);

        Join<WarehouseWiseAllocationPriorityRulesHeader, Customer> toCustomerJoin = rootObj.join("customer");
        Join<WarehouseWiseAllocationPriorityRulesHeader, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<WarehouseWiseAllocationPriorityRulesHeader, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<WarehouseWiseAllocationPriorityRulesHeader, CustomerShipToAddress> toShipJoin = rootObj.join("shipTo");
        Join<WarehouseWiseAllocationPriorityRulesHeader, OrderType> toOrderTypeJoin = rootObj.join("orderType");
        Join<WarehouseWiseAllocationPriorityRulesHeader, SkuType> toSkuTypeJoin = rootObj.join("skuType");
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"),toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code")
        ,toCustomerJoin.get("id"), toCustomerJoin.get("name"), toCustomerJoin.get("code")
        ,toCostBucketJoin.get("id"), toCostBucketJoin.get("name"), toCostBucketJoin.get("code")
        ,toShipJoin.get("id"),toShipJoin.get("shipTo")
        ,toOrderTypeJoin.get("id"), toOrderTypeJoin.get("name"), toOrderTypeJoin.get("code")
        ,toSkuTypeJoin.get("id"), toSkuTypeJoin.get("name"), toSkuTypeJoin.get("code"), rootObj.get("shippingMode")
                );

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(toCustomerJoin.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        }else{
            if (Objects.nonNull(request.getId())) {
                predicates.add(criteriaBuilder.equal(toCustomerJoin.get("id"), request.getId()));
            }

            if (Objects.nonNull(request.getCustomerTypeId())) {
                predicates.add(criteriaBuilder.equal(toCustomerJoin.get("customer_type_id"), request.getCustomerTypeId()));
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            if (Objects.nonNull(request.getCreatedFrom())) {
                try {
                    Date createdFrom = dateFormat.parse(request.getCreatedFrom());
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(toCustomerJoin.get("createdAt").as(java.sql.Date.class),
                            createdFrom));
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(request.getCreatedTo())) {
                try {
                    Date createdTo = dateFormat.parse(request.getCreatedTo());
                    predicates.add(
                            criteriaBuilder.lessThanOrEqualTo(toCustomerJoin.get("createdAt").as(java.sql.Date.class), createdTo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(request.getModifiedFrom())) {
                try {
                    Date modifiedFrom = dateFormat.parse(request.getModifiedFrom());
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(toCustomerJoin.get("modifiedAt").as(java.sql.Date.class),
                            modifiedFrom));
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(request.getModifiedTo())) {
                try {
                    Date modifiedTo = dateFormat.parse(request.getModifiedTo());
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(toCustomerJoin.get("modifiedAt").as(java.sql.Date.class),
                            modifiedTo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } 

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<WarehouseWiseAllocationPriorityRulesHeader> typedQuery = entityManager.createQuery(criteriaQuery);

        List<WarehouseWiseAllocationPriorityRulesHeader> responses = typedQuery.getResultList();

        for (WarehouseWiseAllocationPriorityRulesHeader warehouseWiseAllocationPriorityRulesHeader : responses) {
            warehouseWiseAllocationPriorityRulesHeader.setWarehouseWiseAllocationPriorityRules(findWarehousAllocationPriorityRulesByHeaderId(warehouseWiseAllocationPriorityRulesHeader.getId()));
        }
        return responses;
    }


    public List<WarehouseWiseAllocationPriorityRules> findWarehousAllocationPriorityRulesByHeaderId(Long headerId) {
        CriteriaQuery<WarehouseWiseAllocationPriorityRules> criteriaQuery = criteriaBuilder.createQuery(WarehouseWiseAllocationPriorityRules.class);
        Root<WarehouseWiseAllocationPriorityRules> rootObj = criteriaQuery.from(WarehouseWiseAllocationPriorityRules.class);

        Join<WarehouseWiseAllocationPriorityRules, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        // public WarehouseWiseAllocationPriorityRules(Long id, Long warehouseWiseAllocationPriorityRulesHeaderId,
        //     Long warehouseId, String warehouseName, String warehouseCode, Float priorityRules) {

        criteriaQuery.multiselect(rootObj.get("id"),rootObj.get("warehouseWiseAllocationPriorityRulesHeaderId"),toWarehoueJoin.get("id"), toWarehoueJoin.get("name"), toWarehoueJoin.get("code"),
         rootObj.get("priorityRules")
                );

        if (!Objects.nonNull(headerId)) {
            return new ArrayList<>();
        }

        predicates.add(criteriaBuilder.equal(rootObj.get("warehouseWiseAllocationPriorityRulesHeaderId"), headerId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);
        
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    private List<Predicate> getPredicate(CustomerRequest request, Root<Customer> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("id"), request.getId()));
        }

        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }
        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }

        if (Objects.nonNull(request.getContactName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("contactName"), "%" + request.getContactName() + "%"));
        }

        if (Objects.nonNull(request.getPostalCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postalCode"), "%" + request.getPostalCode() + "%"));
        }

        if (Objects.nonNull(request.getPostBox())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postBox"), "%" + request.getPostBox() + "%"));
        }

        if (Objects.nonNull(request.getPhone())) {
            predicates.add(criteriaBuilder.like(rootObj.get("phone"), "%" + request.getPhone() + "%"));
        }
        if (Objects.nonNull(request.getOtherState())) {
            predicates.add(criteriaBuilder.like(rootObj.get("otherState"), "%" + request.getOtherState() + "%"));
        }

        if (Objects.nonNull(request.getOtherCity())) {
            predicates.add(criteriaBuilder.like(rootObj.get("otherCity"), "%" + request.getOtherCity() + "%"));
        }

        if (Objects.nonNull(request.getMobile())) {
            predicates.add(criteriaBuilder.like(rootObj.get("mobile"), "%" + request.getMobile() + "%"));
        }
        if (Objects.nonNull(request.getIntlDialCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("intlDialCode"), "%" + request.getIntlDialCode() + "%"));
        }
        if (Objects.nonNull(request.getGstNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("gstNo"), "%" + request.getGstNo() + "%"));
        }
        if (Objects.nonNull(request.getFax())) {
            predicates.add(criteriaBuilder.like(rootObj.get("fax"), "%" + request.getFax() + "%"));
        }

        if (Objects.nonNull(request.getEmail())) {
            predicates.add(criteriaBuilder.like(rootObj.get("email"), "%" + request.getEmail() + "%"));
        }

        if (Objects.nonNull(request.getAreaDialingCode())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("areaDialingCode"), "%" + request.getAreaDialingCode() + "%"));
        }
        if (Objects.nonNull(request.getAddressLine2())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine2"), "%" + request.getAddressLine2() + "%"));
        }

        if (Objects.nonNull(request.getAddressLine1())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine1"), "%" + request.getAddressLine1() + "%"));
        }

        if (Objects.nonNull(request.getExtensionNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("extensionNo"), "%" + request.getExtensionNo() + "%"));
        }

        if (Objects.nonNull(request.getMinDaysToExpiry())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minDaysToExpiry"), request.getMinDaysToExpiry()));
        }

        if (Objects.nonNull(request.getMinDaysToExpiryDurationTime())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minDaysToExpiryDurationTime"),
                    request.getMinDaysToExpiryDurationTime()));
        }

        if (Objects.nonNull(request.getMaxAge())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxAge"), request.getMaxAge()));
        }

        if (Objects.nonNull(request.getMaxAgeDurationTime())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxAgeDurationTime"), request.getMaxAgeDurationTime()));
        }

        if (Objects.nonNull(request.getRoute())) {
            predicates.add(criteriaBuilder.like(rootObj.get("route"), "%" + request.getRoute() + "%"));
        }

        if (Objects.nonNull(request.getGstNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("gstNo"), "%" + request.getGstNo() + "%"));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        if (Objects.nonNull(request.getCreatedFrom())) {
            try {
                Date createdFrom = dateFormat.parse(request.getCreatedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class),
                        createdFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getCreatedTo())) {
            try {
                Date createdTo = dateFormat.parse(request.getCreatedTo());
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class), createdTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedFrom())) {
            try {
                Date modifiedFrom = dateFormat.parse(request.getModifiedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedTo())) {
            try {
                Date modifiedTo = dateFormat.parse(request.getModifiedTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (Objects.nonNull(request.getMaintainBackOrder())) {
            if (request.getMaintainBackOrder().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("maintainBackOrder"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("maintainBackOrder"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getMaintainBackOrder().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("maintainBackOrder"), true));
            } else if (request.getMaintainBackOrder().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("maintainBackOrder"), false));
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<Customer> criteriaQuery, Root<Customer> rootObj) {

        if (page.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(rootObj.get(page.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(rootObj.get(page.getSortBy())));
        }
    }

    private Pageable getPageable(PageHelper page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getAllRecordsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Customer> countRoot = countQuery.from(Customer.class);

        countRoot.join("defaultWarehouseForAllocation");
        countRoot.join("primaryCompany");
        countRoot.join("country");
        countRoot.join("currency");
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");
        countRoot.join("customerType", JoinType.LEFT);
        countRoot.join("state", JoinType.LEFT);
        countRoot.join("city", JoinType.LEFT);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
