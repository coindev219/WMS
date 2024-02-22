package com.warehouse.sales.salesorder;

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
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.vasactivity.VasActivity;
import com.warehouse.administration.user.User;
import com.warehouse.country.Country;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddress;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;
import com.warehouse.setup.sku.Sku;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SalesOrderSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SalesOrderSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Transactional
    public void approveSalesOrder(List<SalesOrderRequest> theSalesOrderRequestList) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
 
        // create update
        CriteriaUpdate<SalesOrder> update = cb.createCriteriaUpdate(SalesOrder.class);
 
        // set the root class
        Root<SalesOrder> rootObj = update.from(SalesOrder.class);
 
        // set update and where clause
        update.set("isApproved", true);

        List<Predicate> predicates = new ArrayList<Predicate>();

        
        In<Long> inClause = criteriaBuilder.in(rootObj.get("id"));
        for (SalesOrderRequest request : theSalesOrderRequestList) {
            inClause.value(request.getId());
        }

        predicates.add(inClause);

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        update.where(predicate);
        
        // perform update
        this.entityManager.createQuery(update).executeUpdate();
    }


    public Page<SalesOrder> findAllWithFilters(PageHelper page, SalesOrderRequest request) {
        CriteriaQuery<SalesOrder> criteriaQuery = criteriaBuilder.createQuery(SalesOrder.class);
        Root<SalesOrder> rootObj = criteriaQuery.from(SalesOrder.class);
        Join<SalesOrder, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SalesOrder, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<SalesOrder, Customer> toCustomerJoin = rootObj.join("customer");
        Join<SalesOrder, CustomerBillToAddress> toCustomerBillToAddressJoin = rootObj.join("customerBillToAddress");
        Join<SalesOrder, CustomerShipToAddress> toCustomerShipToAddressJoin = rootObj.join("customerShipToAddress");
        Join<SalesOrder, OrderType> toOrderTypeJoin = rootObj.join("soType");
        Join<SalesOrder, Country> toCountryJoin = rootObj.join("destinationCountry");
        Join<SalesOrder, Freighter> toFreighterJoin = rootObj.join("freighter");
        Join<SalesOrder, VasActivity> toVasActivity = rootObj.join("vasActivity", JoinType.LEFT);
        Join<SalesOrder, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<SalesOrder, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("soNo"), 
                toPrimaryCompanyJoin.get("id"),toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toCostBucketJoin.get("id"),toCostBucketJoin.get("name"), toCostBucketJoin.get("code"),
                toCustomerJoin.get("id"),toCustomerJoin.get("name"), toCustomerJoin.get("code"),rootObj.get("transactionType"),
                rootObj.get("referenceOrderNo"),rootObj.get("salesPerson"),rootObj.get("email"),rootObj.get("phone"),
                toCustomerBillToAddressJoin.get("id"),toCustomerBillToAddressJoin.get("billTo"),
                toCustomerShipToAddressJoin.get("id"),toCustomerShipToAddressJoin.get("shipTo"),
                toOrderTypeJoin.get("id"),toOrderTypeJoin.get("name"), toOrderTypeJoin.get("code"),rootObj.get("tradeTermEnum"),
                rootObj.get("shipModeEnum"),toCountryJoin.get("id"),toCountryJoin.get("countryName"),rootObj.get("destinationPort"),
                toFreighterJoin.get("id"),toFreighterJoin.get("name"), toFreighterJoin.get("code"),
                rootObj.get("fcNo"),rootObj.get("foc"),rootObj.get("totalSoAmt"),rootObj.get("soReceiptDate"),
                rootObj.get("targetDispatchDateTime"),rootObj.get("validateRefSo"),
                toVasActivity.get("id"),toVasActivity.get("name"), toVasActivity.get("code"),rootObj.get("cod"),
                rootObj.get("allocateFromCrossDock"),rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

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

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCostBucketId())) {
                predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
            }

            if (Objects.nonNull(request.getCustomerId())) {
                predicates.add(criteriaBuilder.equal(toCustomerJoin.get("id"), request.getCustomerId()));
            }

            if (Objects.nonNull(request.getCustomerBillToAddressId())) {
                predicates.add(criteriaBuilder.equal(toCustomerBillToAddressJoin.get("id"), request.getCustomerBillToAddressId()));
            }

            if (Objects.nonNull(request.getCustomerShipToAddressId())) {
                predicates.add(criteriaBuilder.equal(toCustomerShipToAddressJoin.get("id"), request.getCustomerShipToAddressId()));
            }

            if (Objects.nonNull(request.getSoTypeId())) {
                predicates.add(criteriaBuilder.equal(toOrderTypeJoin.get("id"), request.getSoTypeId()));
            }

            if (Objects.nonNull(request.getFreighterId())) {
                predicates.add(criteriaBuilder.equal(toFreighterJoin.get("id"), request.getFreighterId()));
            }

            if (Objects.nonNull(request.getVasActivityId())) {
                predicates.add(criteriaBuilder.equal(toVasActivity.get("id"), request.getVasActivityId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<SalesOrder> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<SalesOrder> findAllWithFilters(SalesOrderRequest request) {
        CriteriaQuery<SalesOrder> criteriaQuery = criteriaBuilder.createQuery(SalesOrder.class);
        Root<SalesOrder> rootObj = criteriaQuery.from(SalesOrder.class);
        Join<SalesOrder, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SalesOrder, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<SalesOrder, Customer> toCustomerJoin = rootObj.join("customer");
        Join<SalesOrder, CustomerBillToAddress> toCustomerBillToAddressJoin = rootObj.join("customerBillToAddress");
        Join<SalesOrder, CustomerShipToAddress> toCustomerShipToAddressJoin = rootObj.join("customerShipToAddress");
        Join<SalesOrder, OrderType> toOrderTypeJoin = rootObj.join("soType");
        Join<SalesOrder, Country> toCountryJoin = rootObj.join("destinationCountry");
        Join<SalesOrder, Freighter> toFreighterJoin = rootObj.join("freighter");
        Join<SalesOrder, VasActivity> toVasActivity = rootObj.join("vasActivity", JoinType.LEFT);
        Join<SalesOrder, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<SalesOrder, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("soNo"), 
                toPrimaryCompanyJoin.get("id"),toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toCostBucketJoin.get("id"),toCostBucketJoin.get("name"), toCostBucketJoin.get("code"),
                toCustomerJoin.get("id"),toCustomerJoin.get("name"), toCustomerJoin.get("code"),rootObj.get("transactionType"),
                rootObj.get("referenceOrderNo"),rootObj.get("salesPerson"),rootObj.get("email"),rootObj.get("phone"),
                toCustomerBillToAddressJoin.get("id"),toCustomerBillToAddressJoin.get("billTo"),
                toCustomerShipToAddressJoin.get("id"),toCustomerShipToAddressJoin.get("shipTo"),
                toOrderTypeJoin.get("id"),toOrderTypeJoin.get("name"), toOrderTypeJoin.get("code"),rootObj.get("tradeTermEnum"),
                rootObj.get("shipModeEnum"),toCountryJoin.get("id"),toCountryJoin.get("countryName"),rootObj.get("destinationPort"),
                toFreighterJoin.get("id"),toFreighterJoin.get("name"), toFreighterJoin.get("code"),
                rootObj.get("fcNo"),rootObj.get("foc"),rootObj.get("totalSoAmt"),rootObj.get("soReceiptDate"),
                rootObj.get("targetDispatchDateTime"),rootObj.get("validateRefSo"),
                toVasActivity.get("id"),toVasActivity.get("name"), toVasActivity.get("code"),rootObj.get("cod"),
                rootObj.get("allocateFromCrossDock"),rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

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

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCostBucketId())) {
                predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
            }

            if (Objects.nonNull(request.getCustomerId())) {
                predicates.add(criteriaBuilder.equal(toCustomerJoin.get("id"), request.getCustomerId()));
            }

            if (Objects.nonNull(request.getCustomerBillToAddressId())) {
                predicates.add(criteriaBuilder.equal(toCustomerBillToAddressJoin.get("id"), request.getCustomerBillToAddressId()));
            }

            if (Objects.nonNull(request.getCustomerShipToAddressId())) {
                predicates.add(criteriaBuilder.equal(toCustomerShipToAddressJoin.get("id"), request.getCustomerShipToAddressId()));
            }

            if (Objects.nonNull(request.getSoTypeId())) {
                predicates.add(criteriaBuilder.equal(toOrderTypeJoin.get("id"), request.getSoTypeId()));
            }

            if (Objects.nonNull(request.getFreighterId())) {
                predicates.add(criteriaBuilder.equal(toFreighterJoin.get("id"), request.getFreighterId()));
            }

            if (Objects.nonNull(request.getVasActivityId())) {
                predicates.add(criteriaBuilder.equal(toVasActivity.get("id"), request.getVasActivityId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);
        TypedQuery<SalesOrder> typedQuery = entityManager.createQuery(criteriaQuery);

        List<SalesOrder> salesOrderList = typedQuery.getResultList();
        for (SalesOrder salesOrder : salesOrderList) {
            List<SalesOrderSku> salesOrderSkuList =  findAllWithFilters(salesOrder.getId());
            salesOrder.setSalesOrderSku(salesOrderSkuList);
        }
        
        return salesOrderList;
    }

    public List<SalesOrderSku> findAllWithFilters(Long salesOrderId) {
        CriteriaQuery<SalesOrderSku> criteriaQuery = criteriaBuilder.createQuery(SalesOrderSku.class);
        Root<SalesOrderSku> rootObj = criteriaQuery.from(SalesOrderSku.class);
        Join<SalesOrderSku, Sku> toSkuJoin = rootObj.join("sku");
        Join<SalesOrder, VasActivity> toVasActivity = rootObj.join("vasActivity", JoinType.LEFT);
        Join<SalesOrderSku, Uom> toBillingUomJoin = rootObj.join("billingUom", JoinType.LEFT);
        Join<SalesOrderSku, Uom> toOrderingUomJoin = rootObj.join("orderingUom", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), 
                toSkuJoin.get("id"),toSkuJoin.get("name"), toSkuJoin.get("code"),
                rootObj.get("referenceOrderLineNo"), 
                toVasActivity.get("id"),toVasActivity.get("name"), toVasActivity.get("code"),
                rootObj.get("manufacturingDate"),rootObj.get("expiryDate"),
                toOrderingUomJoin.get("id"),toOrderingUomJoin.get("name"), toOrderingUomJoin.get("uom"),
                toBillingUomJoin.get("id"),toBillingUomJoin.get("name"), toBillingUomJoin.get("uom"),
                rootObj.get("orderedQuantity"),rootObj.get("orderedQuantityBillingUom"),
                rootObj.get("packNo"),rootObj.get("allocateFromCrossDock"),rootObj.get("transactionType"));

        if (Objects.nonNull(salesOrderId)) {
            predicates.add(criteriaBuilder.equal(rootObj.get("salesOrderId"), salesOrderId));
        }


        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);
        TypedQuery<SalesOrderSku> typedQuery = entityManager.createQuery(criteriaQuery);
        

        return typedQuery.getResultList();
    }


    private List<Predicate> getPredicate(SalesOrderRequest request, Root<SalesOrder> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(rootObj.get("isApproved"), false));

        if (Objects.nonNull(request.getSoNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("soNo"), "%" + request.getSoNo() + "%"));
        }

        if (Objects.nonNull(request.getTradeTermEnum())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("transactionType"), request.getTradeTermEnum()));
        }

        if (Objects.nonNull(request.getReferenceOrderNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("referenceOrderNo"), "%" + request.getReferenceOrderNo() + "%"));
        }

        if (Objects.nonNull(request.getSalesPerson())) {
            predicates.add(criteriaBuilder.like(rootObj.get("salesPerson"), "%" + request.getSalesPerson() + "%"));
        }

        if (Objects.nonNull(request.getEmail())) {
            predicates.add(criteriaBuilder.like(rootObj.get("email"), "%" + request.getEmail() + "%"));
        }

        if (Objects.nonNull(request.getPhone())) {
            predicates.add(criteriaBuilder.like(rootObj.get("phone"), "%" + request.getPhone() + "%"));
        }

        if (Objects.nonNull(request.getDestinationPort())) {
            predicates.add(criteriaBuilder.like(rootObj.get("destinationPort"), "%" + request.getDestinationPort() + "%"));
        }

        if (Objects.nonNull(request.getFcNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("fcNo"), "%" + request.getFcNo() + "%"));
        }
        
        if (Objects.nonNull(request.getFoc())) {
            if (request.getFoc().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("foc"), true));
            } else if (request.getFoc().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("foc"), false));
            }
        }

        if (Objects.nonNull(request.getValidateRefSo())) {
            if (request.getValidateRefSo().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("validateRefSo"), true));
            } else if (request.getValidateRefSo().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("validateRefSo"), false));
            }
        }

        if (Objects.nonNull(request.getCod())) {
            if (request.getCod().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("cod"), true));
            } else if (request.getCod().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("cod"), false));
            }
        }
        
        if (Objects.nonNull(request.getAllocateFromCrossDock())) {
            if (request.getAllocateFromCrossDock().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("allocateFromCrossDock"), true));
            } else if (request.getAllocateFromCrossDock().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("allocateFromCrossDock"), false));
            }
        }

        if (Objects.nonNull(request.getTotalSoAmt())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("totalSoAmt"), request.getTotalSoAmt()));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        if (Objects.nonNull(request.getSoDateFrom())) {
            try {
                Date createdFrom = dateFormat.parse(request.getSoDateFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("soReceiptDate").as(java.sql.Date.class),
                        createdFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getSoDateTo())) {
            try {
                Date createdTo = dateFormat.parse(request.getSoDateTo());
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(rootObj.get("soReceiptDate").as(java.sql.Date.class), createdTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (Objects.nonNull(request.getTargetDispatchDateTime())) {
            try {
                Date createdTo = dateFormat.parse(request.getTargetDispatchDateTime());
                predicates.add(
                        criteriaBuilder.equal(rootObj.get("targetDispatchDateTime").as(java.util.Date.class), createdTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        
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

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<SalesOrder> criteriaQuery, Root<SalesOrder> rootObj) {

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
        Root<SalesOrder> countRoot = countQuery.from(SalesOrder.class);
        countRoot.join("primaryCompany");
        countRoot.join("costBucket");
        countRoot.join("customer");
        countRoot.join("customerBillToAddress");
        countRoot.join("customerShipToAddress");
        countRoot.join("soType");
        countRoot.join("destinationCountry");
        countRoot.join("freighter");
        countRoot.join("vasActivity", JoinType.LEFT);
        countRoot.join("primaryCompany");
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
