package com.warehouse.setup.sku.managekitbom;

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
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.additionalsetup.mhetype.MheType;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.country.Country;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.packinggroup.PackingGroup;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.SkuRequest;
import com.warehouse.setup.sku.category.SkuCategory;
import com.warehouse.setup.supplier.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ManageKitBomSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    private SkuManageKitBomRepository skuManageKitBomRepository;

    public ManageKitBomSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Transactional
    public void updateManagerKitBom(List<ManageKitBomHeaderRequest> theManageKitBomHeaderRequestList) {

        for (ManageKitBomHeaderRequest manageKitBomHeaderRequest : theManageKitBomHeaderRequestList) {
            updateKitSkuUnitPrice(manageKitBomHeaderRequest);
            List<SkuManageKitBom> theSkuManageKitBoms = manageKitBomHeaderRequest.getSkuManageKitBomList();
            skuManageKitBomRepository.saveAll(theSkuManageKitBoms);
        }
    }

    @Transactional
    public void updateKitSkuUnitPrice(ManageKitBomHeaderRequest request) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        // create update
        CriteriaUpdate<Sku> update = cb.createCriteriaUpdate(Sku.class);

        // set the root class
        Root<Sku> rootObj = update.from(Sku.class);

        // set update and where clause
        update.set("purchaseUnitPrice", request.getPurchaseUnitPrice());
        update.set("salesUnitPrice", request.getSalesUnitPrice());

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(criteriaBuilder.equal(rootObj.get("id"), request.getKitSkuId()));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        update.where(predicate);

        // perform update
        this.entityManager.createQuery(update).executeUpdate();
    }

    public Page<Sku> findAllWithFilters(PageHelper page, SkuRequest request) {
        CriteriaQuery<Sku> criteriaQuery = criteriaBuilder.createQuery(Sku.class);
        Root<Sku> rootObj = criteriaQuery.from(Sku.class);

        Join<Sku, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Sku, SkuType> toSkuTypeJoin = rootObj.join("skuType");
        Join<Sku, SkuCategory> toSkuCategory = rootObj.join("skuCategory", JoinType.LEFT);
        rootObj.join("storageCategory", JoinType.LEFT);
        Join<Sku, Supplier> toSupplierJoin = rootObj.join("supplier");
        Join<Sku, Country> toCountryJoin = rootObj.join("country", JoinType.LEFT);
        Join<Sku, Uom> toStorageUomJoin = rootObj.join("storageUom");
        Join<Sku, Uom> toBillingUomJoin = rootObj.join("billingUom");
        rootObj.join("orderingUom");
        rootObj.join("locationRestriction", JoinType.LEFT);
        Join<Sku, PackingGroup> toPackingGroup = rootObj.join("packingGroup", JoinType.LEFT);
        rootObj.join("baseSku", JoinType.LEFT);
        rootObj.join("costBucket", JoinType.LEFT);
        Join<Sku, MheType> toMheTypeJoin = rootObj.join("mheType", JoinType.LEFT);
        Join<Sku, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Sku, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toSkuTypeJoin.get("id"), toSkuTypeJoin.get("name"), toSkuTypeJoin.get("code"), toSkuCategory.get("id"),
                toSkuCategory.get("name"), toSkuCategory.get("code"), toStorageUomJoin.get("id"),
                toStorageUomJoin.get("name"), toStorageUomJoin.get("uom"), toBillingUomJoin.get("id"),
                toBillingUomJoin.get("name"), toBillingUomJoin.get("uom"), rootObj.get("noOfComponents"),
                rootObj.get("purchaseUnitPrice"), rootObj.get("salesUnitPrice"), rootObj.get("manufactureDt"),
                rootObj.get("serialNo"), rootObj.get("expiryDt"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
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
            if (Objects.nonNull(request.getSkuTypeId())) {
                predicates.add(criteriaBuilder.equal(toSkuTypeJoin.get("id"), request.getSkuTypeId()));
            }
            if (Objects.nonNull(request.getSkuCategoryId())) {
                predicates.add(criteriaBuilder.equal(toSkuCategory.get("id"), request.getSkuCategoryId()));
            }
            if (Objects.nonNull(request.getSupplierId())) {
                predicates.add(criteriaBuilder.equal(toSupplierJoin.get("id"), request.getSupplierId()));
            }
            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }
            if (Objects.nonNull(request.getStorageUomId())) {
                predicates.add(criteriaBuilder.equal(toStorageUomJoin.get("id"), request.getStorageUomId()));
            }

            if (Objects.nonNull(request.getPackingGroupId())) {
                predicates.add(criteriaBuilder.equal(toPackingGroup.get("id"), request.getPackingGroupId()));
            }

            if (Objects.nonNull(request.getMheTypeId())) {
                predicates.add(criteriaBuilder.equal(toMheTypeJoin.get("id"), request.getMheTypeId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<Sku> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<SkuManageKitBomResponse> findAllWithFilters(SkuRequest request) {
        CriteriaQuery<SkuManageKitBomResponse> criteriaQuery = criteriaBuilder
                .createQuery(SkuManageKitBomResponse.class);
        Root<Sku> rootObj = criteriaQuery.from(Sku.class);

        Join<Sku, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Sku, SkuType> toSkuTypeJoin = rootObj.join("skuType");
        Join<Sku, SkuCategory> toSkuCategory = rootObj.join("skuCategory", JoinType.LEFT);
        rootObj.join("storageCategory", JoinType.LEFT);
        Join<Sku, Supplier> toSupplierJoin = rootObj.join("supplier");
        Join<Sku, Country> toCountryJoin = rootObj.join("country", JoinType.LEFT);
        Join<Sku, Uom> toStorageUomJoin = rootObj.join("storageUom");
        rootObj.join("billingUom");
        rootObj.join("orderingUom");
        rootObj.join("locationRestriction", JoinType.LEFT);
        Join<Sku, PackingGroup> toPackingGroup = rootObj.join("packingGroup", JoinType.LEFT);
        rootObj.join("baseSku", JoinType.LEFT);
        rootObj.join("costBucket", JoinType.LEFT);
        Join<Sku, MheType> toMheTypeJoin = rootObj.join("mheType", JoinType.LEFT);
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), rootObj.get("id"), rootObj.get("name"), rootObj.get("code"),
                rootObj.get("purchaseUnitPrice"), rootObj.get("salesUnitPrice"), rootObj.get("noOfComponents"));

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
            if (Objects.nonNull(request.getSkuTypeId())) {
                predicates.add(criteriaBuilder.equal(toSkuTypeJoin.get("id"), request.getSkuTypeId()));
            }
            if (Objects.nonNull(request.getSkuCategoryId())) {
                predicates.add(criteriaBuilder.equal(toSkuCategory.get("id"), request.getSkuCategoryId()));
            }
            if (Objects.nonNull(request.getSupplierId())) {
                predicates.add(criteriaBuilder.equal(toSupplierJoin.get("id"), request.getSupplierId()));
            }
            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }
            if (Objects.nonNull(request.getStorageUomId())) {
                predicates.add(criteriaBuilder.equal(toStorageUomJoin.get("id"), request.getStorageUomId()));
            }

            if (Objects.nonNull(request.getPackingGroupId())) {
                predicates.add(criteriaBuilder.equal(toPackingGroup.get("id"), request.getPackingGroupId()));
            }

            if (Objects.nonNull(request.getMheTypeId())) {
                predicates.add(criteriaBuilder.equal(toMheTypeJoin.get("id"), request.getMheTypeId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<SkuManageKitBomResponse> typedQuery = entityManager.createQuery(criteriaQuery);
        List<SkuManageKitBomResponse> kitSkuList = typedQuery.getResultList();
        for (SkuManageKitBomResponse skuManageKitBomResponse : kitSkuList) {
            skuManageKitBomResponse
                    .setSkuManageKitBomList(getComponentSkuOfKitSku(skuManageKitBomResponse.getKitSkuId()));
        }
        return kitSkuList;
    }

    public List<SkuManageKitBom> getComponentSkuOfKitSku(Long kitSkuId) {
        CriteriaQuery<SkuManageKitBom> criteriaQuery = criteriaBuilder.createQuery(SkuManageKitBom.class);
        Root<SkuManageKitBom> rootObj = criteriaQuery.from(SkuManageKitBom.class);

        Join<SkuManageKitBom, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<SkuManageKitBom, Sku> toKitSkuJoin = rootObj.join("kitSku");
        Join<SkuManageKitBom, Sku> toComponentSkuJoin = rootObj.join("componentSku");
        Join<Sku, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Sku, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toKitSkuJoin.get("id"), toKitSkuJoin.get("name"),
                toKitSkuJoin.get("code"), toComponentSkuJoin.get("id"), toComponentSkuJoin.get("name"),
                toComponentSkuJoin.get("code"), rootObj.get("quantity"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        predicates.add(criteriaBuilder.equal(toKitSkuJoin.get("id"), kitSkuId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<SkuManageKitBom> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private List<Predicate> getPredicate(SkuRequest request, Root<Sku> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getKitSkuId())) {
            predicates.add(criteriaBuilder.notEqual(rootObj.get("id"), request.getKitSkuId()));
        }

        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }
        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }

        if (Objects.nonNull(request.getReferenceSKU())) {
            predicates.add(criteriaBuilder.like(rootObj.get("referenceSKU"), "%" + request.getReferenceSKU() + "%"));
        }

        if (Objects.nonNull(request.getBarcodeRequired())) {
            if (request.getBarcodeRequired().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("barcodeRequired"), true));
            } else if (request.getBarcodeRequired().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("barcodeRequired"), false));
            }
        }

        if (Objects.nonNull(request.getOutComplexity())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("outComplexity"), request.getOutComplexity()));
        }

        if (Objects.nonNull(request.getWeightCaptureLotSize())) {
            predicates
                    .add(criteriaBuilder.equal(rootObj.get("weightCaptureLotSize"), request.getWeightCaptureLotSize()));
        }

        if (Objects.nonNull(request.getBatch())) {
            if (request.getBatch().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("batch"), true));
            } else if (request.getBatch().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("batch"), false));
            }
        }
        if (Objects.nonNull(request.getExpiryDt())) {
            if (request.getExpiryDt().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("expiryDt"), true));
            } else if (request.getExpiryDt().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("expiryDt"), false));
            }
        }
        if (Objects.nonNull(request.getHSCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("HSCode"), "%" + request.getHSCode() + "%"));
        }
        if (Objects.nonNull(request.getInComplexity())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("inComplexity"), request.getInComplexity()));
        }
        if (Objects.nonNull(request.getManufactureDt())) {
            if (request.getManufactureDt().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("manufactureDt"), true));
            } else if (request.getManufactureDt().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("manufactureDt"), false));
            }
        }
        if (Objects.nonNull(request.getOriginType())) {
            predicates.add(criteriaBuilder.like(rootObj.get("originType"), "%" + request.getOriginType() + "%"));
        }
        if (Objects.nonNull(request.getSellable())) {
            if (request.getSellable().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("sellable"), true));
            } else if (request.getSellable().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("sellable"), false));
            }
        }

        if (Objects.nonNull(request.getSerialNo())) {
            if (request.getSerialNo().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("serialNo"), true));
            } else if (request.getSerialNo().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("serialNo"), false));
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        if (Objects.nonNull(request.getCreatedFrom())) {
            try {
                Date createdFrom = dateFormat.parse(request.getCreatedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class),
                        createdFrom));
            } catch (ParseException e) {

                // e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getCreatedTo())) {
            try {
                Date createdTo = dateFormat.parse(request.getCreatedTo());
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class), createdTo));
            } catch (ParseException e) {
                // e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedFrom())) {
            try {
                Date modifiedFrom = dateFormat.parse(request.getModifiedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedFrom));
            } catch (ParseException e) {

                // e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedTo())) {
            try {
                Date modifiedTo = dateFormat.parse(request.getModifiedTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedTo));
            } catch (ParseException e) {
                // e.printStackTrace();
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<Sku> criteriaQuery, Root<Sku> rootObj) {

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
        Root<Sku> countRoot = countQuery.from(Sku.class);
        countRoot.join("primaryCompany");
        countRoot.join("skuType");
        countRoot.join("skuCategory", JoinType.LEFT);
        countRoot.join("storageCategory", JoinType.LEFT);
        countRoot.join("supplier");
        countRoot.join("country", JoinType.LEFT);
        countRoot.join("storageUom");
        countRoot.join("billingUom");
        countRoot.join("orderingUom");
        countRoot.join("locationRestriction", JoinType.LEFT);
        countRoot.join("packingGroup", JoinType.LEFT);
        countRoot.join("baseSku", JoinType.LEFT);
        countRoot.join("costBucket", JoinType.LEFT);
        countRoot.join("mheType", JoinType.LEFT);
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
