package com.warehouse.setup.customer.maxage;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.administration.user.User;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerWiseMaxAgeSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomerWiseMaxAgeSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<CustomerWiseMaxAge> findAllWithFilters(Long customerId) {
        CriteriaQuery<CustomerWiseMaxAge> criteriaQuery = criteriaBuilder
                .createQuery(CustomerWiseMaxAge.class);
        Root<CustomerWiseMaxAge> rootObj = criteriaQuery.from(CustomerWiseMaxAge.class);
        Join<CustomerWiseMaxAge, Customer> toCustomerJoin = rootObj.join("customer");
        Join<CustomerWiseMaxAge, Sku> toSkuJoin = rootObj.join("sku");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toCustomerJoin.get("id"), toCustomerJoin.get("name"),
                toCustomerJoin.get("code"), toSkuJoin.get("id"), toSkuJoin.get("name"), toSkuJoin.get("code"),
                rootObj.get("maxAge"), rootObj.get("maxAgeUnit"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toCustomerJoin.get("id"), customerId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
