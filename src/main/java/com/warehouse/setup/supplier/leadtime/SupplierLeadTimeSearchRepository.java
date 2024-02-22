package com.warehouse.setup.supplier.leadtime;

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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.administration.user.User;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierLeadTimeSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SupplierLeadTimeSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<SupplierLeadTime> findAllWithFilters(PageHelper page, SupplierLeadTimeRequest request) {
        CriteriaQuery<SupplierLeadTime> criteriaQuery = criteriaBuilder.createQuery(SupplierLeadTime.class);
        Root<SupplierLeadTime> rootObj = criteriaQuery.from(SupplierLeadTime.class);

        Join<SupplierLeadTime, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<SupplierLeadTime, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SupplierLeadTime, Supplier> toSupplierJoin = rootObj.join("supplier");
        Join<SupplierLeadTime, Supplier> toSkuTypeJoin = rootObj.join("skuType");
        Join<SupplierLeadTime, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<SupplierLeadTime, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toWarehoueJoin.get("id"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), toSupplierJoin.get("id"), toSupplierJoin.get("name"),
                toSupplierJoin.get("code"), toSkuTypeJoin.get("id"), toSkuTypeJoin.get("name"),
                toSkuTypeJoin.get("code"), rootObj.get("supplierLeadTimeDays"), rootObj.get("orderCycle"),
                rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            System.out.println("Inside Search by keword");

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

            if (Objects.nonNull(request.getSupplierId())) {
                predicates.add(criteriaBuilder.equal(toSupplierJoin.get("id"), request.getSupplierId()));
            }

            if (Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getSkuTypeId())) {
                predicates.add(criteriaBuilder.equal(toSkuTypeJoin.get("id"), request.getSkuTypeId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<SupplierLeadTime> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(SupplierLeadTimeRequest request, Root<SupplierLeadTime> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("id"), request.getId()));
        }

        if (Objects.nonNull(request.getOrderCycle())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("orderCycle"), request.getOrderCycle()));
        }
        if (Objects.nonNull(request.getSupplierLeadTimeDays())) {
            predicates
                    .add(criteriaBuilder.equal(rootObj.get("supplierLeadTimeDays"), request.getSupplierLeadTimeDays()));
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

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<SupplierLeadTime> criteriaQuery,
            Root<SupplierLeadTime> rootObj) {

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
        Root<SupplierLeadTime> countRoot = countQuery.from(SupplierLeadTime.class);
        countRoot.join("warehouse");
        countRoot.join("primaryCompany");
        countRoot.join("supplier");
        countRoot.join("skuType");
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
