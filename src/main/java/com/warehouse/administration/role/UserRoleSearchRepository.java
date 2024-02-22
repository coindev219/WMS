package com.warehouse.administration.role;

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
import com.warehouse.administration.warehousescreen.WarehouseScreen;
import com.warehouse.enums.UserTypeEnum;
import com.warehouse.helper.PageHelper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserRoleSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<UserRole> findAllWithFilters(PageHelper page, UserRoleRequest request) {
        CriteriaQuery<UserRole> criteriaQuery = criteriaBuilder.createQuery(UserRole.class);
        Root<UserRole> rootObj = criteriaQuery.from(UserRole.class);

        Join<UserRole, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<UserRole, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"), rootObj.get("userType"),
                rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
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

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<UserRole> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    

    public List<UserRole> findAllWithFilters(UserRoleRequest request) {
        CriteriaQuery<UserRole> criteriaQuery = criteriaBuilder.createQuery(UserRole.class);
        Root<UserRole> rootObj = criteriaQuery.from(UserRole.class);

        Join<UserRole, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<UserRole, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"), rootObj.get("userType"),
                rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
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

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<UserRole> typedQuery = entityManager.createQuery(criteriaQuery);

        List<UserRole> userRoleList = typedQuery.getResultList();
        for (UserRole userRole : userRoleList) {
            userRole.setUserRoleWarehouseScreenMappings(findScreenByRole(userRole.getId()));
        }

        return userRoleList;
    }


    public List<UserRole> findUserRoleByUserType(UserTypeEnum userType) {
        CriteriaQuery<UserRole> criteriaQuery = criteriaBuilder.createQuery(UserRole.class);
        Root<UserRole> rootObj = criteriaQuery.from(UserRole.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"));

        predicates.add(criteriaBuilder.equal(rootObj.get("userType"), userType));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<UserRole> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    public List<UserRoleWarehouseScreenMapping> findScreenByRole(Long roleId) {
        CriteriaQuery<UserRoleWarehouseScreenMapping> criteriaQuery = criteriaBuilder
                .createQuery(UserRoleWarehouseScreenMapping.class);
        Root<UserRoleWarehouseScreenMapping> rootObj = criteriaQuery.from(UserRoleWarehouseScreenMapping.class);
        Join<UserRole, WarehouseScreen> toWarehouseScreenJoin = rootObj.join("warehouseScreen");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("warehouseScreenId"),
                toWarehouseScreenJoin.get("mainMenu"), toWarehouseScreenJoin.get("subMenu"),
                toWarehouseScreenJoin.get("screenName"), rootObj.get("crateOption"), rootObj.get("searchOption"),
                rootObj.get("actionOption"), rootObj.get("actionButtonOption"), rootObj.get("reportOption"),
                rootObj.get("hhtOption"), rootObj.get("notificationOption"));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        predicates.add(criteriaBuilder.equal(rootObj.get("userRoleId"), roleId));

        TypedQuery<UserRoleWarehouseScreenMapping> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private List<Predicate> getPredicate(UserRoleRequest request, Root<UserRole> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }

        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }

        if (Objects.nonNull(request.getUserType())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("userType"), request.getUserType()));
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

    private void setOrder(PageHelper page, CriteriaQuery<UserRole> criteriaQuery, Root<UserRole> rootObj) {

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
        Root<UserRole> countRoot = countQuery.from(UserRole.class);
        countRoot.join("createdBy", JoinType.LEFT);
        countRoot.join("modifiedBy", JoinType.LEFT);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
