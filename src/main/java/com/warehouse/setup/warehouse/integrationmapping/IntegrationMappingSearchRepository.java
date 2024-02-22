package com.warehouse.setup.warehouse.integrationmapping;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.administration.user.User;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.stereotype.Repository;

@Repository
public class IntegrationMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public IntegrationMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<IntegrationMapping> findAllWithFilters(Long warehouseId) {
        CriteriaQuery<IntegrationMapping> criteriaQuery = criteriaBuilder.createQuery(IntegrationMapping.class);
        Root<IntegrationMapping> rootObj = criteriaQuery.from(IntegrationMapping.class);
        Join<IntegrationMapping, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<IntegrationMapping, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<IntegrationMapping, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("warehouseId"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), rootObj.get("code"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), warehouseId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
