package com.warehouse.setup.sku.suppliermapping;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.stereotype.Repository;

@Repository
public class SkuSupplierMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SkuSupplierMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<SkuSupplierMapping> findAllWithFilters(Long supplierId, Long primaryCompanyId) {
        CriteriaQuery<SkuSupplierMapping> criteriaQuery = criteriaBuilder.createQuery(SkuSupplierMapping.class);
        Root<SkuSupplierMapping> rootObj = criteriaQuery.from(SkuSupplierMapping.class);
        Join<SkuSupplierMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SkuSupplierMapping, Sku> toSkuJoin = rootObj.join("sku");
        Join<SkuSupplierMapping, Uom> toUomJoin = rootObj.join("uom", JoinType.LEFT);
        Join<SkuSupplierMapping, Supplier> toSupplierJoin = rootObj.join("supplier");
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();


        criteriaQuery.multiselect(rootObj.get("id"),toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toSkuJoin.get("id"), toSkuJoin.get("name"), toSkuJoin.get("code"),
                toSupplierJoin.get("id"), toSupplierJoin.get("name"), toSupplierJoin.get("code"),
                rootObj.get("purchasePrice"), rootObj.get("tradeTerm"),toUomJoin.get("id"), toUomJoin.get("name"),
                toUomJoin.get("uom"),rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toSupplierJoin.get("id"), supplierId));
        predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), primaryCompanyId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
