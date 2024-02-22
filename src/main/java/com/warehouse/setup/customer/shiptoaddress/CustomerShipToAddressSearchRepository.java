package com.warehouse.setup.customer.shiptoaddress;

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
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerShipToAddressSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomerShipToAddressSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<CustomerShipToAddress> findAllWithFilters(PageHelper page, CustomerShipToAddressRequest request) {
        CriteriaQuery<CustomerShipToAddress> criteriaQuery = criteriaBuilder.createQuery(CustomerShipToAddress.class);
        Root<CustomerShipToAddress> rootObj = criteriaQuery.from(CustomerShipToAddress.class);

        Join<Customer, Warehouse> toWarehoueJoin = rootObj.join("defaultWarehouseForAllocation");
        Join<CustomerShipToAddress, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<CustomerShipToAddress, Country> toCountryJoin = rootObj.join("country");
        Join<CustomerShipToAddress, Customer> toCustomerJoin = rootObj.join("customer");
        Join<CustomerShipToAddress, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<CustomerShipToAddress, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        Join<CustomerShipToAddress, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<CustomerShipToAddress, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toCustomerJoin.get("id"), toCustomerJoin.get("name"),
                toCustomerJoin.get("code"), rootObj.get("shipTo"), rootObj.get("addressLine1"),
                rootObj.get("addressLine2"), rootObj.get("contactName"), toCountryJoin.get("id"),
                toCountryJoin.get("countryName"), toStateJoin.get("id"), toStateJoin.get("stateName"),
                rootObj.get("otherState"), toCityJoin.get("id"), toCityJoin.get("cityName"), rootObj.get("otherCity"),
                rootObj.get("postBox"), rootObj.get("postalCode"), rootObj.get("intlDialCode"),
                rootObj.get("areaDialingCode"), rootObj.get("phone"), rootObj.get("extensionNo"), rootObj.get("fax"),
                rootObj.get("mobile"), rootObj.get("email"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"), toWarehoueJoin.get("id"),
                toWarehoueJoin.get("name"), toWarehoueJoin.get("code"));

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

            if (Objects.nonNull(request.getDefaultWarehouseForAllocationId())) {
                predicates.add(
                        criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getDefaultWarehouseForAllocationId()));
            }

            if (Objects.nonNull(request.getCustomerId())) {
                predicates.add(criteriaBuilder.equal(toCustomerJoin.get("id"), request.getCustomerId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }
            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));
            }
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<CustomerShipToAddress> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(CustomerShipToAddressRequest request, Root<CustomerShipToAddress> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("id"), request.getId()));
        }

        if (Objects.nonNull(request.getShipTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("billTo"), "%" + request.getShipTo() + "%"));
        }

        if (Objects.nonNull(request.getContactName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("contactName"), "%" + request.getContactName() + "%"));
        }

        if (Objects.nonNull(request.getPostalCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postalCode"), "%" + request.getPostalCode() + "%"));
        }

        if (Objects.nonNull(request.getPostBox())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postBox"), "%" + request.getPostBox() + "%"));
        }

        if (Objects.nonNull(request.getPhone())) {
            predicates.add(criteriaBuilder.like(rootObj.get("phone"), "%" + request.getPhone() + "%"));
        }
        if (Objects.nonNull(request.getOtherState())) {
            predicates.add(criteriaBuilder.like(rootObj.get("otherState"), "%" + request.getOtherState() + "%"));
        }

        if (Objects.nonNull(request.getOtherCity())) {
            predicates.add(criteriaBuilder.like(rootObj.get("otherCity"), "%" + request.getOtherCity() + "%"));
        }

        if (Objects.nonNull(request.getMobile())) {
            predicates.add(criteriaBuilder.like(rootObj.get("mobile"), "%" + request.getMobile() + "%"));
        }
        if (Objects.nonNull(request.getIntlDialCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("intlDialCode"), "%" + request.getIntlDialCode() + "%"));
        }

        if (Objects.nonNull(request.getFax())) {
            predicates.add(criteriaBuilder.like(rootObj.get("fax"), "%" + request.getFax() + "%"));
        }

        if (Objects.nonNull(request.getEmail())) {
            predicates.add(criteriaBuilder.like(rootObj.get("email"), "%" + request.getEmail() + "%"));
        }

        if (Objects.nonNull(request.getAreaDialingCode())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("areaDialingCode"), "%" + request.getAreaDialingCode() + "%"));
        }
        if (Objects.nonNull(request.getAddressLine2())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine2"), "%" + request.getAddressLine2() + "%"));
        }

        if (Objects.nonNull(request.getAddressLine1())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine1"), "%" + request.getAddressLine1() + "%"));
        }

        if (Objects.nonNull(request.getExtensionNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("extensionNo"), "%" + request.getExtensionNo() + "%"));
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

    private void setOrder(PageHelper page, CriteriaQuery<CustomerShipToAddress> criteriaQuery,
            Root<CustomerShipToAddress> rootObj) {

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
        Root<CustomerShipToAddress> countRoot = countQuery.from(CustomerShipToAddress.class);

        countRoot.join("defaultWarehouseForAllocation");
        countRoot.join("primaryCompany");
        countRoot.join("country");
        countRoot.join("customer");
        countRoot.join("state", JoinType.LEFT);
        countRoot.join("city", JoinType.LEFT);
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
