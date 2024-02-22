package com.warehouse.additionalsetup.ordertype;

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
import com.warehouse.enums.OrderTypeEnum;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class OrderTypeSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public OrderTypeSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<OrderType> findAllWithFilters(PageHelper page, OrderTypeRequest request) {
        CriteriaQuery<OrderType> criteriaQuery = criteriaBuilder.createQuery(OrderType.class);
        Root<OrderType> rootObj = criteriaQuery.from(OrderType.class);
        Join<OrderType, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<OrderType, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<OrderType, User> toModifiedByUserJoin = rootObj.join("modifiedBy");
        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                rootObj.get("orderType"), rootObj.get("allowDangerLvlUnlocking"), rootObj.get("explodeKitBom"),
                rootObj.get("transactionType"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

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

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<OrderType> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(OrderTypeRequest request, Root<OrderType> rootObj) {
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

        if (Objects.nonNull(request.getPoType())) {
            if (request.getPoType().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("orderType"), OrderTypeEnum.PO));
            } else if (request.getPoType().equals("false")) {
                predicates.add(criteriaBuilder.notEqual(rootObj.get("orderType"), OrderTypeEnum.PO));
            }
        }

        if (Objects.nonNull(request.getSoType())) {
            if (request.getSoType().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("orderType"), OrderTypeEnum.SO));
            } else if (request.getSoType().equals("false")) {
                predicates.add(criteriaBuilder.notEqual(rootObj.get("orderType"), OrderTypeEnum.SO));
            }
        }

        if (Objects.nonNull(request.getExtStockTransfer())) {
            if (request.getExtStockTransfer().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("orderType"), OrderTypeEnum.ExtStockTrf));
            } else if (request.getExtStockTransfer().equals("false")) {
                predicates.add(criteriaBuilder.notEqual(rootObj.get("orderType"), OrderTypeEnum.ExtStockTrf));
            }
        }


        if (Objects.nonNull(request.getAllowDangerLvlUnlocking())) {
            if (request.getAllowDangerLvlUnlocking().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("allowDangerLvlUnlocking"), true));
            } else if (request.getAllowDangerLvlUnlocking().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("allowDangerLvlUnlocking"), false));
            }
        }

        if (Objects.nonNull(request.getExplodeKitBom())) {
            if (request.getExplodeKitBom().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("explodeKitBom"), true));
            } else if (request.getExplodeKitBom().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("explodeKitBom"), false));
            }
        }

        if (Objects.nonNull(request.getTransactionType())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("transactionType"), request.getTransactionType()));
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<OrderType> criteriaQuery, Root<OrderType> rootObj) {

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
        Root<OrderType> countRoot = countQuery.from(OrderType.class);
        countRoot.join("primaryCompany");
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
