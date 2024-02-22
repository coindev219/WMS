package com.warehouse.administration.applicationconfiguration.organizationparameters;

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

import com.warehouse.administration.organization.Organization;
import com.warehouse.administration.organization.OrganizationRequest;
import com.warehouse.administration.user.User;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationParameterSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public OrganizationParameterSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<OrganizationConfigParamtersResponse> findOrganizationList(PageHelper page,
            OrganizationRequest request) {
        CriteriaQuery<OrganizationConfigParamtersResponse> criteriaQuery = criteriaBuilder
                .createQuery(OrganizationConfigParamtersResponse.class);
        Root<Organization> rootObj = criteriaQuery.from(Organization.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("code"), rootObj.get("name"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"));

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
        TypedQuery<OrganizationConfigParamtersResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<OrganizationConfigParamtersResponse> findOrganizationListWithConfigParameter(
            OrganizationRequest request) {
        CriteriaQuery<OrganizationConfigParamtersResponse> criteriaQuery = criteriaBuilder
                .createQuery(OrganizationConfigParamtersResponse.class);
        Root<Organization> rootObj = criteriaQuery.from(Organization.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("code"), rootObj.get("name"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"));

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

        TypedQuery<OrganizationConfigParamtersResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<OrganizationConfigParamtersResponse> organizationConfigParamtersResponseList = typedQuery.getResultList();
        for (OrganizationConfigParamtersResponse organizationConfigParamtersResponse : organizationConfigParamtersResponseList) {
            organizationConfigParamtersResponse.setOrganizationConfigList(
                    findOrganizationConfigParameters(organizationConfigParamtersResponse.getOrganizationId()));
        }
        return organizationConfigParamtersResponseList;
    }

    public List<OrganizationConfig> findOrganizationConfigParameters(Long organizationId) {
        CriteriaQuery<OrganizationConfig> criteriaQuery = criteriaBuilder.createQuery(OrganizationConfig.class);

        Root<OrganizationConfig> rootObj = criteriaQuery.from(OrganizationConfig.class);
        rootObj.join("organization");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();


        criteriaQuery.multiselect(rootObj.get("id"),rootObj.get("configKey"), rootObj.get("configValue"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(rootObj.get("organizationId"), organizationId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    private List<Predicate> getPredicate(OrganizationRequest request, Root<Organization> rootObj) {
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

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<OrganizationConfigParamtersResponse> criteriaQuery,
            Root<Organization> rootObj) {

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
        Root<Organization> countRoot = countQuery.from(Organization.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
