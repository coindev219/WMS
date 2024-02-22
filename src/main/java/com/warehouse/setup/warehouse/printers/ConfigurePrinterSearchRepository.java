package com.warehouse.setup.warehouse.printers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.administration.user.User;
import com.warehouse.setup.location.Location;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.stereotype.Repository;

@Repository
public class ConfigurePrinterSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ConfigurePrinterSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<ConfigurePrinter> findAllWithFilters(Long warehouseId) {
        CriteriaQuery<ConfigurePrinter> criteriaQuery = criteriaBuilder.createQuery(ConfigurePrinter.class);
        Root<ConfigurePrinter> rootObj = criteriaQuery.from(ConfigurePrinter.class);
        Join<ConfigurePrinter, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<ConfigurePrinter, Location> fromDock = rootObj.join("fromDock");
        Join<ConfigurePrinter, Location> toDock = rootObj.join("toDock");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("warehouseId"),
        toWarehoueJoin.get("name"), toWarehoueJoin.get("code"), rootObj.get("fromDockId"),
         fromDock.get("code"), rootObj.get("toDockId"),
        toDock.get("code"), rootObj.get("name"),
                rootObj.get("ipAddress"),  rootObj.get("portNumber"), rootObj.get("operationType"),
                rootObj.get("labelType"), rootObj.get("isDefaultPrinter"), rootObj.get("usable"),
                rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), warehouseId));

        

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        
        return entityManager.createQuery(criteriaQuery).getResultList();
        
    }

    

}
