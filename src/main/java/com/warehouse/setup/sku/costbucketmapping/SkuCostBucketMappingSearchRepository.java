package com.warehouse.setup.sku.costbucketmapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.administration.user.User;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMapping;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class SkuCostBucketMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SkuCostBucketMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<SkuCostBucketMapping> findAll(SkuCostBucketMappingRequest request) {
        CriteriaQuery<SkuCostBucketMapping> criteriaQuery = criteriaBuilder.createQuery(SkuCostBucketMapping.class);
        Root<SkuCostBucketMapping> rootObj = criteriaQuery.from(SkuCostBucketMapping.class);
        Join<SkuCostBucketMapping, SkuWarehouseMapping> toSkuWarehouseMappingJoin = rootObj
                .join("skuWarehouseMapping");
        Join<SkuWarehouseMapping, WarehouseCompanyMapping> toWarehoueCompanyMappingJoin = toSkuWarehouseMappingJoin
                .join("warehouseCompanyMapping");

        Join<SkuWarehouseMapping, Sku> toSkuJoin = toSkuWarehouseMappingJoin.join("sku");

        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = toWarehoueCompanyMappingJoin
                .join("primaryCompany");
        Join<WarehouseCompanyMapping, Warehouse> toWarehouseJoin = toWarehoueCompanyMappingJoin.join("warehouse");
        
        Join<SkuCostBucketMapping, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toWarehouseJoin.get("id"), toWarehouseJoin.get("name"),
                toWarehouseJoin.get("code"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toSkuJoin.get("id"), toSkuJoin.get("name"), toSkuJoin.get("code"),
                toCostBucketJoin.get("id"), toCostBucketJoin.get("name"), toCostBucketJoin.get("code"),
                rootObj.get("movingAverage"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getWarehouseId())) {
            predicates.add(criteriaBuilder.equal(toWarehouseJoin.get("id"), request.getWarehouseId()));
        }

        if (Objects.nonNull(request.getPrimaryCompanyId())) {
            predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"),
                    request.getPrimaryCompanyId()));
        }

        if (Objects.nonNull(request.getSkuId())) {
            predicates.add(criteriaBuilder.equal(toSkuJoin.get("id"), request.getSkuId()));
        }

        if (Objects.nonNull(request.getCostBucketId())) {
            predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
        }

        if (Objects.nonNull(request.getMovingAverage())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("movingAverage"), request.getMovingAverage()));
        }

        if (Objects.nonNull(request.getWarehousePrimaryCompanyShipTo())) {
            predicates.add(criteriaBuilder.like(toWarehoueCompanyMappingJoin.get("shipTo"),
                    "%" + request.getWarehousePrimaryCompanyShipTo() + "%"));
        }

        if (Objects.nonNull(request.getWarehouseShipTo())) {
            predicates
                    .add(criteriaBuilder.like(toWarehouseJoin.get("shipTo"), "%" + request.getWarehouseShipTo() + "%"));
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

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public Page<SkuCostBucketMapping> findAll(PageHelper page, SkuCostBucketMappingRequest request) {
        CriteriaQuery<SkuCostBucketMapping> criteriaQuery = criteriaBuilder.createQuery(SkuCostBucketMapping.class);
        Root<SkuCostBucketMapping> rootObj = criteriaQuery.from(SkuCostBucketMapping.class);
        Join<SkuCostBucketMapping, SkuWarehouseMapping> toSkuWarehouseMappingJoin = rootObj
                .join("skuWarehouseMapping");
        Join<SkuWarehouseMapping, WarehouseCompanyMapping> toWarehoueCompanyMappingJoin = toSkuWarehouseMappingJoin
                .join("warehouseCompanyMapping");

        Join<SkuWarehouseMapping, Sku> toSkuJoin = toSkuWarehouseMappingJoin.join("sku");

        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = toWarehoueCompanyMappingJoin
                .join("primaryCompany");
        Join<WarehouseCompanyMapping, Warehouse> toWarehouseJoin = toWarehoueCompanyMappingJoin.join("warehouse");
        
        Join<SkuCostBucketMapping, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toWarehouseJoin.get("id"), toWarehouseJoin.get("name"),
                toWarehouseJoin.get("code"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toSkuJoin.get("id"), toSkuJoin.get("name"), toSkuJoin.get("code"),
                toCostBucketJoin.get("id"), toCostBucketJoin.get("name"), toCostBucketJoin.get("code"),
                rootObj.get("movingAverage"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getWarehouseId())) {
            predicates.add(criteriaBuilder.equal(toWarehouseJoin.get("id"), request.getWarehouseId()));
        }

        if (Objects.nonNull(request.getPrimaryCompanyId())) {
            predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"),
                    request.getPrimaryCompanyId()));
        }

        if (Objects.nonNull(request.getSkuId())) {
            predicates.add(criteriaBuilder.equal(toSkuJoin.get("id"), request.getSkuId()));
        }

        if (Objects.nonNull(request.getCostBucketId())) {
            predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
        }

        if (Objects.nonNull(request.getMovingAverage())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("movingAverage"), request.getMovingAverage()));
        }

        if (Objects.nonNull(request.getWarehousePrimaryCompanyShipTo())) {
            predicates.add(criteriaBuilder.like(toWarehoueCompanyMappingJoin.get("shipTo"),
                    "%" + request.getWarehousePrimaryCompanyShipTo() + "%"));
        }

        if (Objects.nonNull(request.getWarehouseShipTo())) {
            predicates
                    .add(criteriaBuilder.like(toWarehouseJoin.get("shipTo"), "%" + request.getWarehouseShipTo() + "%"));
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

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<SkuCostBucketMapping> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);

    }

    private void setOrder(PageHelper page, CriteriaQuery<SkuCostBucketMapping> criteriaQuery, Root<SkuCostBucketMapping> rootObj) {

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
        Root<SkuCostBucketMapping> countRoot = countQuery.from(SkuCostBucketMapping.class);
        Join<SkuCostBucketMapping, SkuWarehouseMapping> toSkuWarehouseMappingJoin = countRoot
        .join("skuWarehouseMapping");
        Join<SkuWarehouseMapping, WarehouseCompanyMapping> toWarehoueCompanyMappingJoin = toSkuWarehouseMappingJoin
        .join("warehouseCompanyMapping");
        toSkuWarehouseMappingJoin.join("sku");
        toWarehoueCompanyMappingJoin.join("primaryCompany");
        toWarehoueCompanyMappingJoin.join("warehouse");
        countRoot.join("costBucket");
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");
        
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }



}
