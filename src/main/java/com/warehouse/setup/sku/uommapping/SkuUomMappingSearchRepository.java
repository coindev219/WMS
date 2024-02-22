package com.warehouse.setup.sku.uommapping;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.additionalsetup.mhetype.MheType;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategory;
import com.warehouse.setup.warehouse.Warehouse;

import org.springframework.stereotype.Repository;

@Repository
public class SkuUomMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SkuUomMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<SkuUomMapping> findAllWithFilters(Long skuId) {
        CriteriaQuery<SkuUomMapping> criteriaQuery = criteriaBuilder.createQuery(SkuUomMapping.class);
        Root<SkuUomMapping> rootObj = criteriaQuery.from(SkuUomMapping.class);
        Join<SkuUomMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SkuUomMapping, Sku> toSkuJoin = rootObj.join("sku");
        Join<SkuUomMapping, Uom> toUomJoin = rootObj.join("uom", JoinType.LEFT);
        Join<SkuUomMapping, MheType> toMheTypeJoin = rootObj.join("mheType", JoinType.LEFT);
        Join<SkuUomMapping, SkuStorageCategory> toStorageCategoryJoin = rootObj.join("storageCategory", JoinType.LEFT);
        Join<Warehouse, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toSkuJoin.get("id"), toSkuJoin.get("name"), toSkuJoin.get("code"),
                toUomJoin.get("id"), toUomJoin.get("name"), toUomJoin.get("uom"),
                rootObj.get("multiplicationFactorToConvertStorageUom"), rootObj.get("isDefaultSalesOrderingUom"),
                rootObj.get("isDefaultSalesBillingUom"), rootObj.get("isDefaultPurchaseOrderingUom"),
                rootObj.get("isDefaultPurchaseBillingUom"), rootObj.get("barcode"), rootObj.get("purchaseLotSize"),
                rootObj.get("salesLotSize"), rootObj.get("minPoQty"), rootObj.get("maxPoQty"), rootObj.get("minSoQty"),
                rootObj.get("maxSoQty"), rootObj.get("netWeight"), rootObj.get("weightRequired"),
                rootObj.get("inComplexity"), rootObj.get("outComplexity"), toMheTypeJoin.get("id"),
                toMheTypeJoin.get("name"), toMheTypeJoin.get("code"), rootObj.get("length"), rootObj.get("height"),
                rootObj.get("width"), rootObj.get("grossWeight"), toStorageCategoryJoin.get("id"),
                toStorageCategoryJoin.get("name"), toStorageCategoryJoin.get("code"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toSkuJoin.get("id"), skuId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

}
