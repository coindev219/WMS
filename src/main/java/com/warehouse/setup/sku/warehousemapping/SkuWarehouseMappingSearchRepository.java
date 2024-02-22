package com.warehouse.setup.sku.warehousemapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.warehouse.administration.user.User;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRequest;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.stereotype.Repository;
import java.util.Objects;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Repository
public class SkuWarehouseMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SkuWarehouseMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<SkuWarehouseMapping> findAllWithFilters(Long warehouseCompanyMappingId) {
        CriteriaQuery<SkuWarehouseMapping> criteriaQuery = criteriaBuilder.createQuery(SkuWarehouseMapping.class);
        Root<SkuWarehouseMapping> rootObj = criteriaQuery.from(SkuWarehouseMapping.class);
        Join<SkuWarehouseMapping, WarehouseCompanyMapping> toWarehoueCompanyMappingJoin = rootObj
                .join("warehouseCompanyMapping");
        Join<SkuWarehouseMapping, Sku> toSkuJoin = rootObj.join("sku");
        Join<SkuWarehouseMapping, Supplier> toSupplierJoin = rootObj.join("supplier");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toSkuJoin.get("id"), toSkuJoin.get("name"), toSkuJoin.get("code"),
                toSupplierJoin.get("id"), toSupplierJoin.get("name"), toSupplierJoin.get("code"),
                rootObj.get("dangerLevel"), rootObj.get("allocRule"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toWarehoueCompanyMappingJoin.get("id"),
                warehouseCompanyMappingId));
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public List<SkuWarehouseMappingListResponse> findAllWithFilters(WarehouseCompanyMappingRequest request) {
        CriteriaQuery<SkuWarehouseMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(SkuWarehouseMappingListResponse.class);
        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        Join<WarehouseCompanyMapping, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toWarehoueJoin.get("id"), toWarehoueJoin.get("code"),
                toWarehoueJoin.get("name"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("code"),
                toPrimaryCompanyJoin.get("name"));

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

            if (Objects.nonNull(request.getShipTo())) {
                predicates.add(criteriaBuilder.like(rootObj.get("shipTo"), "%" + request.getShipTo() + "%"));
            }

            if (Objects.nonNull(request.getBillTo())) {
                predicates.add(criteriaBuilder.like(rootObj.get("billTo"), "%" + request.getBillTo() + "%"));
            }

            if (Objects.nonNull(request.getTaxInvoicesLabel())) {
                predicates.add(criteriaBuilder.like(rootObj.get("taxInvoicesLabel"),
                        "%" + request.getTaxInvoicesLabel() + "%"));
            }

            if (Objects.nonNull(request.getTaxInvoicesNo())) {
                predicates.add(
                        criteriaBuilder.like(rootObj.get("taxInvoicesNo"), "%" + request.getTaxInvoicesNo() + "%"));
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
                    predicates.add(criteriaBuilder
                            .greaterThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class), createdFrom));
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(request.getCreatedTo())) {
                try {
                    Date createdTo = dateFormat.parse(request.getCreatedTo());
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class),
                            createdTo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(request.getModifiedFrom())) {
                try {
                    Date modifiedFrom = dateFormat.parse(request.getModifiedFrom());
                    predicates.add(criteriaBuilder
                            .greaterThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class), modifiedFrom));
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

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (java.util.Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
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

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<SkuWarehouseMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<SkuWarehouseMappingListResponse> skuWarehouseMappingList = typedQuery.getResultList();
        for (SkuWarehouseMappingListResponse skuWarehouseMappingListResponse : skuWarehouseMappingList) {
            skuWarehouseMappingListResponse.setSkuWarehouseMappingList(
                    findAllWithFilters(skuWarehouseMappingListResponse.getWarehouseCompanyMappingId()));
        }

        return skuWarehouseMappingList;
    }

    public List<WarehouseCompanyMapping> findAll(String mapToWarehouseIds, Long primaryCompanyId) {
        CriteriaQuery<WarehouseCompanyMapping> criteriaQuery = criteriaBuilder
                .createQuery(WarehouseCompanyMapping.class);
        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        rootObj.join("warehouse");
        rootObj.join("primaryCompany");
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (Objects.nonNull(mapToWarehouseIds) && mapToWarehouseIds.trim().length() > 0) {

            String arrSearchIds[] = {};
            if (mapToWarehouseIds.length() == 1) {
                arrSearchIds = new String[] { mapToWarehouseIds };
            } else {
                arrSearchIds = mapToWarehouseIds.split(",");
            }

            In<Long> inClause = criteriaBuilder.in(rootObj.get("warehouseId"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        }

        predicates.add(criteriaBuilder.equal(rootObj.get("primaryCompanyId"), primaryCompanyId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<WarehouseCompanyMapping> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

}
