package com.warehouse.setup.location;

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
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.location.dimension.DimensionDefinition;
import com.warehouse.setup.location.hierarchy.LocationHierarchy;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.location.storagecategory.LocationStorageCategory;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class LocationSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public LocationSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Location> findAllWithFilters(PageHelper page, LocationRequest request) {
        CriteriaQuery<Location> criteriaQuery = criteriaBuilder.createQuery(Location.class);
        Root<Location> rootObj = criteriaQuery.from(Location.class);

        Join<Location, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<Location, LocationHierarchy> toLocationHierarchyJoin = rootObj.join("locationHierarchy");
        Join<Location, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Location, DimensionDefinition> toDimensionDefinitionJoin = rootObj.join("dimensionDefinition",
                JoinType.LEFT);
        Join<Location, LocationRestriction> toLocationRestrictionJoin = rootObj.join("locationRestriction",
                JoinType.LEFT);
        Join<Location, LocationStorageCategory> toLocationStorageCategoryJoin = rootObj.join("locationStorageCategory",
                JoinType.LEFT);
        Join<Location, Location> toLocationJoin = rootObj.join("location", JoinType.LEFT);
        Join<Location, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Location, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("code"),
                toLocationHierarchyJoin.get("id"), toLocationHierarchyJoin.get("name"),
                toLocationHierarchyJoin.get("code"), toWarehoueJoin.get("name"), toWarehoueJoin.get("code"),
                toWarehoueJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toPrimaryCompanyJoin.get("id"), toDimensionDefinitionJoin.get("id"),
                toDimensionDefinitionJoin.get("name"), toDimensionDefinitionJoin.get("code"),
                toLocationRestrictionJoin.get("id"), toLocationRestrictionJoin.get("name"),
                toLocationRestrictionJoin.get("code"), toLocationStorageCategoryJoin.get("id"),
                toLocationStorageCategoryJoin.get("name"), toLocationStorageCategoryJoin.get("code"),
                toLocationJoin.get("id"), toLocationJoin.get("code"),
                rootObj.get("outerLocation"), rootObj.get("usable"), rootObj.get("pickFace"), rootObj.get("optionA"),
                rootObj.get("optionB"), rootObj.get("optionC"), rootObj.get("optionD"), rootObj.get("optionE"),
                rootObj.get("restrictSuggestion"), rootObj.get("restrictAllocation"), rootObj.get("isPackLocation"),
                rootObj.get("binningSequenceNo"), rootObj.get("pickingSequenceNo"), rootObj.get("locationGroup"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

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

            if (Objects.nonNull(request.getLocationHierarchyId())) {
                predicates.add(
                        criteriaBuilder.equal(toLocationHierarchyJoin.get("id"), request.getLocationHierarchyId()));
            }

            if (Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getDimensionDefinitionId())) {
                predicates.add(
                        criteriaBuilder.equal(toDimensionDefinitionJoin.get("id"), request.getDimensionDefinitionId()));
            }

            if (Objects.nonNull(request.getLocationRestrictionId())) {
                predicates.add(
                        criteriaBuilder.equal(toLocationRestrictionJoin.get("id"), request.getLocationRestrictionId()));
            }

            if (Objects.nonNull(request.getLocationStorageCategoryId())) {
                predicates.add(criteriaBuilder.equal(toLocationStorageCategoryJoin.get("id"),
                        request.getLocationStorageCategoryId()));
            }

            if (Objects.nonNull(request.getUpperLocationId())) {
                predicates.add(criteriaBuilder.equal(toLocationJoin.get("id"), request.getUpperLocationId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<Location> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(LocationRequest request, Root<Location> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("id"), request.getId()));
        }

        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }
        if (Objects.nonNull(request.getOuterLocation())) {
            predicates.add(criteriaBuilder.like(rootObj.get("outerLocation"), "%" + request.getOuterLocation() + "%"));
        }

        if (Objects.nonNull(request.getBinningSequenceNo())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("binningSequenceNo"), request.getBinningSequenceNo()));
        }

        if (Objects.nonNull(request.getLocationGroup())) {
            predicates.add(criteriaBuilder.like(rootObj.get("locationGroup"), "%" + request.getLocationGroup() + "%"));
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

        if (Objects.nonNull(request.getUsable())) {
            if (request.getUsable().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("usable"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("usable"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getUsable().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("usable"), true));
            } else if (request.getUsable().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("usable"), false));
            }
        }

        if (Objects.nonNull(request.getPickFace())) {
            if (request.getPickFace().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("pickFace"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("pickFace"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getPickFace().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("pickFace"), true));
            } else if (request.getPickFace().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("pickFace"), false));
            }
        }

        if (Objects.nonNull(request.getOptionA())) {
            if (request.getOptionA().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("optionA"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("optionA"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getOptionA().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionA"), true));
            } else if (request.getOptionA().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionA"), false));
            }
        }

        if (Objects.nonNull(request.getOptionB())) {
            if (request.getOptionB().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("optionB"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("optionB"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getOptionB().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionB"), true));
            } else if (request.getOptionB().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionB"), false));
            }
        }

        if (Objects.nonNull(request.getOptionC())) {
            if (request.getOptionC().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("optionC"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("optionC"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getOptionC().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionC"), true));
            } else if (request.getOptionC().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionC"), false));
            }
        }

        if (Objects.nonNull(request.getOptionD())) {
            if (request.getOptionD().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("optionD"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("optionD"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getOptionD().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionD"), true));
            } else if (request.getOptionD().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionD"), false));
            }
        }

        if (Objects.nonNull(request.getOptionE())) {
            if (request.getOptionE().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("optionE"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("optionE"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getOptionE().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionE"), true));
            } else if (request.getOptionE().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("optionE"), false));
            }
        }

        if (Objects.nonNull(request.getRestrictSuggestion())) {
            if (request.getRestrictSuggestion().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("restrictSuggestion"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("restrictSuggestion"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getRestrictSuggestion().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("restrictSuggestion"), true));
            } else if (request.getRestrictSuggestion().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("restrictSuggestion"), false));
            }
        }

        if (Objects.nonNull(request.getRestrictAllocation())) {
            if (request.getRestrictAllocation().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("restrictAllocation"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("restrictAllocation"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getRestrictAllocation().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("restrictAllocation"), true));
            } else if (request.getRestrictAllocation().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("restrictAllocation"), false));
            }
        }

        if (Objects.nonNull(request.getIsPackLocation())) {
            if (request.getIsPackLocation().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("isPackLocation"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("isPackLocation"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getIsPackLocation().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isPackLocation"), true));
            } else if (request.getIsPackLocation().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isPackLocation"), false));
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<Location> criteriaQuery, Root<Location> rootObj) {

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
        Root<Location> countRoot = countQuery.from(Location.class);

        countRoot.join("warehouse");
        countRoot.join("locationHierarchy");
        countRoot.join("primaryCompany");
        countRoot.join("dimensionDefinition", JoinType.LEFT);
        countRoot.join("locationRestriction", JoinType.LEFT);
        countRoot.join("locationStorageCategory", JoinType.LEFT);
        countRoot.join("location", JoinType.LEFT);
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
