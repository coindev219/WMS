package com.warehouse.setup.company.warehousecompanymapping;

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
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseCompanyMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public WarehouseCompanyMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<WarehouseCompanyMapping> findAllWithFilters(Long warehouseId) {
        CriteriaQuery<WarehouseCompanyMapping> criteriaQuery = criteriaBuilder
                .createQuery(WarehouseCompanyMapping.class);
        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        Join<WarehouseCompanyMapping, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toWarehoueJoin.get("id"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toPrimaryCompanyJoin.get("id"), rootObj.get("shipTo"), rootObj.get("billTo"),
                rootObj.get("taxInvoicesLabel"), rootObj.get("taxInvoicesNo"), rootObj.get("sgst"), rootObj.get("cgst"),
                rootObj.get("isMappingActive"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), warehouseId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public Page<WarehouseCompanyMapping> findAllWithFilters(PageHelper page, WarehouseCompanyMappingRequest request) {
        CriteriaQuery<WarehouseCompanyMapping> criteriaQuery = criteriaBuilder
                .createQuery(WarehouseCompanyMapping.class);
        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        Join<WarehouseCompanyMapping, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toWarehoueJoin.get("id"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toPrimaryCompanyJoin.get("id"), rootObj.get("shipTo"), rootObj.get("billTo"),
                rootObj.get("taxInvoicesLabel"), rootObj.get("taxInvoicesNo"), rootObj.get("sgst"), rootObj.get("cgst"),
                rootObj.get("isMappingActive"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
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

            if (Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
            }
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<WarehouseCompanyMapping> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(WarehouseCompanyMappingRequest request,
            Root<WarehouseCompanyMapping> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getShipTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("shipTo"), "%" + request.getShipTo() + "%"));
        }

        if (Objects.nonNull(request.getBillTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("billTo"), "%" + request.getBillTo() + "%"));
        }

        if (Objects.nonNull(request.getTaxInvoicesLabel())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("taxInvoicesLabel"), "%" + request.getTaxInvoicesLabel() + "%"));
        }

        if (Objects.nonNull(request.getTaxInvoicesNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("taxInvoicesNo"), "%" + request.getTaxInvoicesNo() + "%"));
        }

        if (Objects.nonNull(request.getSgst())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("sgst"), request.getSgst()));
        }
        if (Objects.nonNull(request.getCgst())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("cgst"), request.getCgst()));
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

        if (Objects.nonNull(request.getIsMappingActive())) {
            if (request.getIsMappingActive().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("isMappingActive"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("isMappingActive"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getIsMappingActive().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isMappingActive"), true));
            } else if (request.getIsMappingActive().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isMappingActive"), false));
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<WarehouseCompanyMapping> criteriaQuery,
            Root<WarehouseCompanyMapping> rootObj) {

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
        Root<WarehouseCompanyMapping> countRoot = countQuery.from(WarehouseCompanyMapping.class);
        countRoot.join("warehouse");
        countRoot.join("primaryCompany");
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
