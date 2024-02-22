package com.warehouse.setup.sku.category;

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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class SkuCategorySearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SkuCategorySearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<SkuCategory> findAllWithFilters(PageHelper page, SkuCategoryRequest request) {
        CriteriaQuery<SkuCategory> criteriaQuery = criteriaBuilder.createQuery(SkuCategory.class);
        Root<SkuCategory> rootObj = criteriaQuery.from(SkuCategory.class);
        Join<SkuCategory, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SkuCategory, SkuCategory> toUpperCategoryJoin = rootObj.join("upperCategory", JoinType.LEFT);
        Join<SkuCategory, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<SkuCategory, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toUpperCategoryJoin.get("id"), toUpperCategoryJoin.get("name"), toUpperCategoryJoin.get("code"),
                rootObj.get("isScanningCompulsory"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
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

            if (Objects.nonNull(request.getUpperCategoryId())) {
                predicates.add(criteriaBuilder.equal(toUpperCategoryJoin.get("id"), request.getUpperCategoryId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<SkuCategory> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(SkuCategoryRequest request, Root<SkuCategory> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }

        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
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

        if (Objects.nonNull(request.getIsScanningCompulsory())) {
            if (request.getIsScanningCompulsory().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("isScanningCompulsory"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("isScanningCompulsory"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getIsScanningCompulsory().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isScanningCompulsory"), true));
            } else if (request.getIsScanningCompulsory().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isScanningCompulsory"), false));
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<SkuCategory> criteriaQuery, Root<SkuCategory> rootObj) {

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
        Root<SkuCategory> countRoot = countQuery.from(SkuCategory.class);
        countRoot.join("primaryCompany", JoinType.LEFT);
        countRoot.join("upperCategory");
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
