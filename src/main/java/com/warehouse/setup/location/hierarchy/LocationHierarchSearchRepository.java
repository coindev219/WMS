package com.warehouse.setup.location.hierarchy;

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
import com.warehouse.enums.DataTypeEnum;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.location.locationtype.LocationType;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class LocationHierarchSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public LocationHierarchSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<LocationHierarchy> findAllWithFilters(PageHelper page, LocationHierarchyRequest request) {
        CriteriaQuery<LocationHierarchy> criteriaQuery = criteriaBuilder.createQuery(LocationHierarchy.class);
        Root<LocationHierarchy> rootObj = criteriaQuery.from(LocationHierarchy.class);
        Join<LocationHierarchy, Warehouse> locationHierarchyWarehouseJoin = rootObj.join("warehouse");
        Join<LocationHierarchy, LocationType> locationHierarchyLocationTypeJoin = rootObj.join("locationType");
        Join<LocationHierarchy, LocationHierarchy> locationHierarchyLocationHierarchyJoin = rootObj
                .join("upperHierarchy", JoinType.LEFT);
        Join<LocationHierarchy, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<LocationHierarchy, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.<Long>get("id"), rootObj.<String>get("name"), rootObj.<String>get("code"),
                rootObj.<Long>get("noOfDigits"), rootObj.<String>get("prefix"), rootObj.<String>get("suffix"),
                rootObj.<Boolean>get("usable"), rootObj.<DataTypeEnum>get("dataType"),
                rootObj.<Long>get("upperHierarchyId"), rootObj.<Long>get("locationTypeId"),
                rootObj.<Long>get("warehouseId"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                locationHierarchyLocationTypeJoin.<String>get("name"),
                locationHierarchyWarehouseJoin.<String>get("name"), locationHierarchyWarehouseJoin.<String>get("code"),
                locationHierarchyLocationHierarchyJoin.<String>get("name"),
                locationHierarchyLocationHierarchyJoin.<String>get("code"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

        System.out.println(request);

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

            if (Objects.nonNull(request.getLocationTypeId())) {
                predicates.add(criteriaBuilder.equal(locationHierarchyLocationTypeJoin.get("id"),
                        request.getLocationTypeId()));
            }

            if (Objects.nonNull(request.getWarehouseId())) {
                predicates
                        .add(criteriaBuilder.equal(locationHierarchyWarehouseJoin.get("id"), request.getWarehouseId()));
            }

            if (Objects.nonNull(request.getUpperHierarchyId())) {
                predicates.add(criteriaBuilder.equal(locationHierarchyLocationHierarchyJoin.get("id"),
                        request.getUpperHierarchyId()));

            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<LocationHierarchy> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(LocationHierarchyRequest request, Root<LocationHierarchy> rootObj) {
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
        if (Objects.nonNull(request.getNoOfDigits())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("noOfDigits"), request.getNoOfDigits()));
        }

        if (Objects.nonNull(request.getPrefix())) {
            predicates.add(criteriaBuilder.like(rootObj.get("prefix"), "%" + request.getPrefix() + "%"));
        }
        if (Objects.nonNull(request.getSuffix())) {
            predicates.add(criteriaBuilder.like(rootObj.get("suffix"), "%" + request.getSuffix() + "%"));
        }

        if (Objects.nonNull(request.getDataType())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("dataType"), request.getDataType()));
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

        if (Objects.nonNull(request.isUsable())) {
            if (request.isUsable().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("usable"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("usable"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.isUsable().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("usable"), true));
            } else if (request.isUsable().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("usable"), false));
            }
        }



        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<LocationHierarchy> criteriaQuery,
            Root<LocationHierarchy> rootObj) {

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
        Root<LocationHierarchy> countRoot = countQuery.from(LocationHierarchy.class);
        countRoot.join("warehouse");
        countRoot.join("locationType");
        countRoot.join("upperHierarchy", JoinType.LEFT);
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
