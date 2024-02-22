package com.warehouse.sales.salesorder.vehicleinward;

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
import com.warehouse.administration.user.User;
import com.warehouse.helper.PageHelper;
import com.warehouse.sales.salesorder.SalesOrder;
import com.warehouse.sales.salesorder.SalesOrderRequest;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleInwardSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public VehicleInwardSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public String[] findAllFcNoInVehicleInward() {
        CriteriaQuery<VehicleInward> criteriaQuery = criteriaBuilder
                .createQuery(VehicleInward.class);
        Root<VehicleInward> rootObj = criteriaQuery.from(VehicleInward.class);
        rootObj.join("freighter");
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();
        criteriaQuery.multiselect(rootObj.get("fcNo"));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);
        TypedQuery<VehicleInward> typedQuery = entityManager.createQuery(criteriaQuery);

        List<VehicleInward> vehicleInwardList = typedQuery.getResultList();
        String []arrFCNo = new String[vehicleInwardList.size()];

        System.out.println("Saved FC NO=="+arrFCNo);

        for (int i = 0; i < vehicleInwardList.size(); i++) {
            arrFCNo[i] = vehicleInwardList.get(i).getFcNo();   
        }
        return arrFCNo;
    }

    public Page<SalesOrderCustomResponse> findAllWithFilters(PageHelper page, SalesOrderRequest request) {
        CriteriaQuery<SalesOrderCustomResponse> criteriaQuery = criteriaBuilder
                .createQuery(SalesOrderCustomResponse.class);
        Root<SalesOrder> rootObj = criteriaQuery.from(SalesOrder.class);
        Join<SalesOrder, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SalesOrder, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        rootObj.join("customer");
        rootObj.join("customerBillToAddress");
        rootObj.join("customerShipToAddress");
        rootObj.join("soType");
        rootObj.join("destinationCountry");
        rootObj.join("freighter");
        rootObj.join("vasActivity", JoinType.LEFT);
        Join<SalesOrder, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<SalesOrder, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("soNo"), rootObj.get("referenceOrderNo"),
                rootObj.get("fcNo"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

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

        }

        String arrFcNo[] = findAllFcNoInVehicleInward();
        In<String> inClause = criteriaBuilder.in(rootObj.get("fcNo"));
        inClause.value("");
        for (String fcNo : arrFcNo) {
            inClause.value(fcNo);
        }
        predicates.add(inClause.not());

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<SalesOrderCustomResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<SalesOrderCustomResponse> findAllWithFilters(SalesOrderRequest request) {
        CriteriaQuery<SalesOrderCustomResponse> criteriaQuery = criteriaBuilder
                .createQuery(SalesOrderCustomResponse.class);
        Root<SalesOrder> rootObj = criteriaQuery.from(SalesOrder.class);
        Join<SalesOrder, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SalesOrder, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        rootObj.join("customer");
        rootObj.join("customerBillToAddress");
        rootObj.join("customerShipToAddress");
        rootObj.join("soType");
        rootObj.join("destinationCountry");
        rootObj.join("freighter");
        rootObj.join("vasActivity", JoinType.LEFT);
        Join<SalesOrder, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<SalesOrder, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("soNo"), rootObj.get("referenceOrderNo"),
                rootObj.get("fcNo"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

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
        }

        String arrFcNo[] = findAllFcNoInVehicleInward();
        In<String> inClause = criteriaBuilder.in(rootObj.get("fcNo"));
        inClause.value("");
        for (String fcNo : arrFcNo) {
            inClause.value(fcNo);
        }
        predicates.add(inClause.not());
        

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);
        TypedQuery<SalesOrderCustomResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private List<Predicate> getPredicate(SalesOrderRequest request, Root<SalesOrder> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getSoNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("soNo"), "%" + request.getSoNo() + "%"));
        }

        if (Objects.nonNull(request.getReferenceOrderNo())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("referenceOrderNo"), "%" + request.getReferenceOrderNo() + "%"));
        }

        if (Objects.nonNull(request.getFcNo()) && request.getFcNo().trim().length() > 0) {
            predicates.add(criteriaBuilder.like(rootObj.get("fcNo"), "%" + request.getFcNo() + "%"));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        if (Objects.nonNull(request.getCreatedFrom())) {
            try {
                Date createdFrom = dateFormat.parse(request.getCreatedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class),
                        createdFrom));
            } catch (ParseException e) {

                //e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getCreatedTo())) {
            try {
                Date createdTo = dateFormat.parse(request.getCreatedTo());
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class), createdTo));
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedFrom())) {
            try {
                Date modifiedFrom = dateFormat.parse(request.getModifiedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedFrom));
            } catch (ParseException e) {

                //e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedTo())) {
            try {
                Date modifiedTo = dateFormat.parse(request.getModifiedTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedTo));
            } catch (ParseException e) {
               // e.printStackTrace();
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<SalesOrderCustomResponse> criteriaQuery,
            Root<SalesOrder> rootObj) {

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
