package com.warehouse.administration.applicationconfiguration.defaultinterfacevalue;

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

import com.warehouse.administration.user.User;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMapping;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultInterfaceValueSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public DefaultInterfaceValueSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<DefaultInterfaceValueResponse> findPrimaryCompanyList(PageHelper page,
            DefaultInterfaceValueSearchRequest request) {
        CriteriaQuery<DefaultInterfaceValueResponse> criteriaQuery = criteriaBuilder
                .createQuery(DefaultInterfaceValueResponse.class);

        Root<UserPrimaryCompanyMapping> rootObj = criteriaQuery.from(UserPrimaryCompanyMapping.class);
        Join<UserPrimaryCompanyMapping, User> toUserJoin = rootObj.join("user");
        Join<UserPrimaryCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");

        toPrimaryCompanyJoin.join("currency");
        toPrimaryCompanyJoin.join("country");
        toPrimaryCompanyJoin.join("state", JoinType.LEFT);
        toPrimaryCompanyJoin.join("city", JoinType.LEFT);
        Join<Warehouse, User> toCreatedByUserJoin = toPrimaryCompanyJoin.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = toPrimaryCompanyJoin.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        // public DefaultInterfaceValueResponse(Long primaryCompanyId, String primaryCompanyName,
        //     String primaryCompanyCode, Date createdAt, Date modifiedAt, String createdByUserName,
        //     String modifiedByUserName)

        criteriaQuery.multiselect(toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toPrimaryCompanyJoin.get("createdAt"),
                toPrimaryCompanyJoin.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(toPrimaryCompanyJoin.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, toPrimaryCompanyJoin);

        }

        User user = WarehouseHelper.getLoggedInUser();

        predicates.add(criteriaBuilder.equal(toUserJoin.get("id"), user.getId()));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<DefaultInterfaceValueResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<DefaultInterfaceValueResponse> findPrimaryCompanyListWithDefaultInterfaceValue(
            DefaultInterfaceValueSearchRequest request) {
        CriteriaQuery<DefaultInterfaceValueResponse> criteriaQuery = criteriaBuilder
                .createQuery(DefaultInterfaceValueResponse.class);

        Root<UserPrimaryCompanyMapping> rootObj = criteriaQuery.from(UserPrimaryCompanyMapping.class);
        Join<UserPrimaryCompanyMapping, User> toUserJoin = rootObj.join("user");
        Join<UserPrimaryCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");

        toPrimaryCompanyJoin.join("currency");
        toPrimaryCompanyJoin.join("country");
        toPrimaryCompanyJoin.join("state", JoinType.LEFT);
        toPrimaryCompanyJoin.join("city", JoinType.LEFT);
        Join<Warehouse, User> toCreatedByUserJoin = toPrimaryCompanyJoin.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = toPrimaryCompanyJoin.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toPrimaryCompanyJoin.get("createdAt"),
                toPrimaryCompanyJoin.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(toPrimaryCompanyJoin.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, toPrimaryCompanyJoin);

        }

        User user = WarehouseHelper.getLoggedInUser();

        predicates.add(criteriaBuilder.equal(toUserJoin.get("id"), user.getId()));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        List<DefaultInterfaceValueResponse> defaultInterfaceValueResponseList = entityManager.createQuery(criteriaQuery)
                .getResultList();

        for (DefaultInterfaceValueResponse defaultInterfaceValueResponse : defaultInterfaceValueResponseList) {
            defaultInterfaceValueResponse.setDefaultInterfaceValueList(
                    findDefaultInterfaceValue(defaultInterfaceValueResponse.getPrimaryCompanyId()));
        }

        return defaultInterfaceValueResponseList;

    }

    public List<DefaultInterfaceValue> findDefaultInterfaceValue(Long primaryCompanyId) {
        CriteriaQuery<DefaultInterfaceValue> criteriaQuery = criteriaBuilder.createQuery(DefaultInterfaceValue.class);

        Root<DefaultInterfaceValue> rootObj = criteriaQuery.from(DefaultInterfaceValue.class);
        rootObj.join("primaryCompany");

        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("configKey"), rootObj.get("configValue"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(rootObj.get("primaryCompanyId"), primaryCompanyId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    private List<Predicate> getPredicate(DefaultInterfaceValueSearchRequest request,
            Join<UserPrimaryCompanyMapping, PrimaryCompany> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        // if (Objects.nonNull(request.getPrimaryCompanyName())) {
        //     predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getPrimaryCompanyName() + "%"));
        // }

        // if (Objects.nonNull(request.getPrimaryCompanyCode())) {
        //     predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getPrimaryCompanyCode() + "%"));
        // }

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

    private void setOrder(PageHelper page, CriteriaQuery<DefaultInterfaceValueResponse> criteriaQuery,
            Root<UserPrimaryCompanyMapping> rootObj) {

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
        Root<UserPrimaryCompanyMapping> countRoot = countQuery.from(UserPrimaryCompanyMapping.class);

        countRoot.join("user");
        countRoot.join("primaryCompany");
        

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
