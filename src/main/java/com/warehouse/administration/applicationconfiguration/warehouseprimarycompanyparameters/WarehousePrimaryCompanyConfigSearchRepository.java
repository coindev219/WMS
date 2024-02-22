package com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters;

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

import com.warehouse.administration.applicationconfiguration.ConfigureParameterRequest;
import com.warehouse.administration.user.User;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMapping;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class WarehousePrimaryCompanyConfigSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public WarehousePrimaryCompanyConfigSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<WarehousePrimaryCompanyConfigParamtersResponse> findWarehouseCompanyMappingList(PageHelper page,
            ConfigureParameterRequest request) {
        CriteriaQuery<WarehousePrimaryCompanyConfigParamtersResponse> criteriaQuery = criteriaBuilder
                .createQuery(WarehousePrimaryCompanyConfigParamtersResponse.class);

        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        Join<WarehouseCompanyMapping, Warehouse> toWarehouseJoin = rootObj.join("warehouse");
        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toWarehouseJoin.get("id"), toWarehouseJoin.get("name"), toWarehouseJoin.get("code"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                rootObj.get("id"),rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

                
        predicates = getPredicate(request, toWarehouseJoin);

        List<Long> arrWarehouseIdList = getWarehouseIdList(request.getWarehouseId());

        In<Long> inClause = criteriaBuilder.in(toWarehouseJoin.get("id"));

        for (Long theId : arrWarehouseIdList) {
            inClause.value(theId);
        }
        predicates.add(inClause);

        if (Objects.nonNull(request.getPrimaryCompanyId())) {
            predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<WarehousePrimaryCompanyConfigParamtersResponse> typedQuery = entityManager
                .createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<Long> getWarehouseIdList(Long warehouseId) {
        CriteriaQuery<UserWarehouseMapping> criteriaQuery = criteriaBuilder.createQuery(UserWarehouseMapping.class);

        Root<UserWarehouseMapping> rootObj = criteriaQuery.from(UserWarehouseMapping.class);
        Join<UserWarehouseMapping, User> toUserJoin = rootObj.join("user");
        Join<UserWarehouseMapping, Warehouse> toWarehouseJoin = rootObj.join("warehouse");

        toWarehouseJoin.join("country");
        toWarehouseJoin.join("state", JoinType.LEFT);
        toWarehouseJoin.join("city", JoinType.LEFT);
        toWarehouseJoin.join("createdBy");
        toWarehouseJoin.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toWarehouseJoin.get("id"));

        User user = WarehouseHelper.getLoggedInUser();

        predicates.add(criteriaBuilder.equal(toUserJoin.get("id"), user.getId()));
        if (warehouseId != null) {
            predicates.add(criteriaBuilder.equal(toWarehouseJoin.get("id"), warehouseId));
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        List<UserWarehouseMapping> userWarehouseMappingList = entityManager.createQuery(criteriaQuery).getResultList();

        ArrayList<Long> warehouseIdList = new ArrayList<Long>();

        for (UserWarehouseMapping userWarehouseMapping : userWarehouseMappingList) {
            warehouseIdList.add(userWarehouseMapping.getWarehouseId());
        }

        return warehouseIdList;

    }

    public List<WarehousePrimaryCompanyConfigParamtersResponse> findWarehouseCompanyMappingListWithConfig(
            ConfigureParameterRequest request) {
        CriteriaQuery<WarehousePrimaryCompanyConfigParamtersResponse> criteriaQuery = criteriaBuilder
                .createQuery(WarehousePrimaryCompanyConfigParamtersResponse.class);

        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        Join<WarehouseCompanyMapping, Warehouse> toWarehouseJoin = rootObj.join("warehouse");
        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toWarehouseJoin.get("id"), toWarehouseJoin.get("name"), toWarehouseJoin.get("code"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                rootObj.get("id"),rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

        predicates = getPredicate(request, toWarehouseJoin);

        List<Long> arrWarehouseIdList = getWarehouseIdList(request.getWarehouseId());

        In<Long> inClause = criteriaBuilder.in(toWarehouseJoin.get("id"));

        for (Long theId : arrWarehouseIdList) {
            inClause.value(theId);
        }
        predicates.add(inClause);

        if (Objects.nonNull(request.getPrimaryCompanyId())) {
            predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        

        List<WarehousePrimaryCompanyConfigParamtersResponse> warehousePrimaryCompanyConfigParamtersResponseList = entityManager
                .createQuery(criteriaQuery).getResultList();

        for (WarehousePrimaryCompanyConfigParamtersResponse warehousePrimaryCompanyConfigParamtersResponse : warehousePrimaryCompanyConfigParamtersResponseList) {
            warehousePrimaryCompanyConfigParamtersResponse
                    .setWarehousePrimaryCompanyConfigList(findWarehouseCompanyConfigParameters(
                            warehousePrimaryCompanyConfigParamtersResponse.getWarehouseCompanyMappingId()));
        }

        return warehousePrimaryCompanyConfigParamtersResponseList;
    }

    public List<WarehousePrimaryCompanyConfig> findWarehouseCompanyConfigParameters(Long warehouseCompanyMappingId) {
        CriteriaQuery<WarehousePrimaryCompanyConfig> criteriaQuery = criteriaBuilder
                .createQuery(WarehousePrimaryCompanyConfig.class);

        Root<WarehousePrimaryCompanyConfig> rootObj = criteriaQuery.from(WarehousePrimaryCompanyConfig.class);
        rootObj.join("warehouseCompanyMapping");

        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("configKey"), rootObj.get("configValue"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(rootObj.get("warehouseCompanyMappingId"), warehouseCompanyMappingId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    private List<Predicate> getPredicate(ConfigureParameterRequest request,
            Join<WarehouseCompanyMapping, Warehouse> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

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

    private void setOrder(PageHelper page, CriteriaQuery<WarehousePrimaryCompanyConfigParamtersResponse> criteriaQuery,
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

        countRoot.join("createdBy");
        countRoot.join("modifiedBy");

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
