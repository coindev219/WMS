package com.warehouse.setup.sku;

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

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.mhetype.MheType;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.country.Country;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.packinggroup.PackingGroup;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.sku.category.SkuCategory;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategory;
import com.warehouse.setup.sku.uommapping.SkuUomMappingListResponse;
import com.warehouse.setup.sku.uommapping.SkuUomMappingSearchRepository;
import com.warehouse.setup.supplier.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class SkuSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    private SkuUomMappingSearchRepository skuUomMappingSearchRepository;

    public SkuSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Sku> findAllWithFilters(PageHelper page, SkuRequest request) {
        CriteriaQuery<Sku> criteriaQuery = criteriaBuilder.createQuery(Sku.class);
        Root<Sku> rootObj = criteriaQuery.from(Sku.class);

        Join<Sku, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Sku, SkuType> toSkuTypeJoin = rootObj.join("skuType");
        Join<Sku, SkuCategory> toSkuCategory = rootObj.join("skuCategory", JoinType.LEFT);
        Join<Sku, SkuStorageCategory> toStorageCategory = rootObj.join("storageCategory", JoinType.LEFT);
        Join<Sku, Supplier> toSupplierJoin = rootObj.join("supplier");
        Join<Sku, Country> toCountryJoin = rootObj.join("country", JoinType.LEFT);
        Join<Sku, Uom> toStorageUomJoin = rootObj.join("storageUom");
        Join<Sku, Uom> toBillingUomJoin = rootObj.join("billingUom");
        Join<Sku, Uom> toOrderingUomJoin = rootObj.join("orderingUom");
        Join<Sku, LocationRestriction> toLocationRestrictionJoin = rootObj.join("locationRestriction", JoinType.LEFT);
        Join<Sku, PackingGroup> toPackingGroup = rootObj.join("packingGroup", JoinType.LEFT);
        Join<Sku, Sku> toBaseSkuJoin = rootObj.join("baseSku", JoinType.LEFT);
        Join<Sku, CostBucket> toCostBucketJoin = rootObj.join("costBucket", JoinType.LEFT);
        Join<Sku, MheType> toMheTypeJoin = rootObj.join("mheType", JoinType.LEFT);
        Join<Sku, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Sku, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"), toPrimaryCompanyJoin.get("code"),
                toSkuTypeJoin.get("id"), toSkuTypeJoin.get("name"), toSkuTypeJoin.get("code"), toSkuCategory.get("id"),
                toSkuCategory.get("name"), toSkuCategory.get("code"), rootObj.get("brand"), rootObj.get("model"),
                rootObj.get("size"), rootObj.get("colour"), toStorageCategory.get("id"), toStorageCategory.get("name"),
                toStorageCategory.get("code"), rootObj.get("referenceSKU"), toSupplierJoin.get("id"),
                toSupplierJoin.get("name"), toSupplierJoin.get("code"), rootObj.get("barCode"), rootObj.get("HSCode"),
                rootObj.get("ABCCode"), toCountryJoin.get("id"), toCountryJoin.get("countryName"),
                rootObj.get("originType"), toStorageUomJoin.get("id"), toStorageUomJoin.get("name"),
                toStorageUomJoin.get("uom"), toBillingUomJoin.get("id"), toBillingUomJoin.get("name"),
                toBillingUomJoin.get("uom"), toOrderingUomJoin.get("id"), toOrderingUomJoin.get("name"),
                toOrderingUomJoin.get("uom"), rootObj.get("billingStorage"), rootObj.get("orderStorage"),
                rootObj.get("maxQtyPerTrip"), rootObj.get("purchasePrice"), rootObj.get("salesPrice"),
                rootObj.get("purchaseLotSize"), rootObj.get("salesLotSize"), rootObj.get("minPoQty"),
                rootObj.get("maxPoQty"), rootObj.get("minSoQty"), rootObj.get("maxSoQty"), rootObj.get("dangerLevel"),
                toLocationRestrictionJoin.get("id"), toLocationRestrictionJoin.get("name"),
                toLocationRestrictionJoin.get("code"), rootObj.get("length"), rootObj.get("height"),
                rootObj.get("width"), rootObj.get("grossWeight"), rootObj.get("grossVolume"),
                rootObj.get("grossVolumeCalculate"), rootObj.get("kit"), rootObj.get("noOfComponents"),
                toPackingGroup.get("id"), toPackingGroup.get("name"), toPackingGroup.get("code"),
                rootObj.get("manufactureDt"), rootObj.get("serialNo"), rootObj.get("batch"), rootObj.get("expiryDt"),
                rootObj.get("minFreeShelfLife"), rootObj.get("minFreeShelfLifeUnit"), rootObj.get("totalShelfLife"),
                rootObj.get("totalShelfLifeUnit"), rootObj.get("allocRule"), rootObj.get("serialNoValidationRule"),
                rootObj.get("sellable"), toBaseSkuJoin.get("id"), toBaseSkuJoin.get("name"), toBaseSkuJoin.get("code"),
                rootObj.get("xFactor"), rootObj.get("mapAllWarehouses"), rootObj.get("mapAllSupplier"),
                rootObj.get("barcodeRequired"), rootObj.get("minAllocQty"), rootObj.get("minPutawayQty"),
                rootObj.get("mapAllCostBuckets"), toCostBucketJoin.get("id"), toCostBucketJoin.get("name"),
                toCostBucketJoin.get("code"), rootObj.get("netWeight"), rootObj.get("weightRequired"),
                rootObj.get("inComplexity"), rootObj.get("outComplexity"), toMheTypeJoin.get("id"),
                toMheTypeJoin.get("name"), toMheTypeJoin.get("code"), rootObj.get("weightCaptureLotSize"),
                rootObj.get("purchasable"), rootObj.get("loadingPattern"), rootObj.get("createdAt"),
                rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

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
            if (Objects.nonNull(request.getStorageCategoryId())) {
                predicates.add(criteriaBuilder.equal(toStorageCategory.get("id"), request.getStorageCategoryId()));
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
            if (Objects.nonNull(request.getBillingUomId())) {
                predicates.add(criteriaBuilder.equal(toBillingUomJoin.get("id"), request.getBillingUomId()));
            }
            if (Objects.nonNull(request.getOrderingUomId())) {
                predicates.add(criteriaBuilder.equal(toOrderingUomJoin.get("id"), request.getOrderingUomId()));
            }
            if (Objects.nonNull(request.getLocationRestrictionId())) {
                predicates.add(
                        criteriaBuilder.equal(toLocationRestrictionJoin.get("id"), request.getLocationRestrictionId()));
            }
            if (Objects.nonNull(request.getPackingGroupId())) {
                predicates.add(criteriaBuilder.equal(toPackingGroup.get("id"), request.getPackingGroupId()));
            }
            if (Objects.nonNull(request.getBaseSkuId())) {
                predicates.add(criteriaBuilder.equal(toBaseSkuJoin.get("id"), request.getBaseSkuId()));
            }
            if (Objects.nonNull(request.getCostBucketId())) {
                predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
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

    public List<SkuUomMappingListResponse> findAllWithFilters(SkuRequest request) {
        CriteriaQuery<SkuUomMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(SkuUomMappingListResponse.class);
        Root<Sku> rootObj = criteriaQuery.from(Sku.class);

        Join<Sku, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Sku, SkuType> toSkuTypeJoin = rootObj.join("skuType");
        Join<Sku, SkuCategory> toSkuCategory = rootObj.join("skuCategory", JoinType.LEFT);
        Join<Sku, SkuStorageCategory> toStorageCategory = rootObj.join("storageCategory", JoinType.LEFT);
        Join<Sku, Supplier> toSupplierJoin = rootObj.join("supplier");
        Join<Sku, Country> toCountryJoin = rootObj.join("country", JoinType.LEFT);
        Join<Sku, Uom> toStorageUomJoin = rootObj.join("storageUom");
        Join<Sku, Uom> toBillingUomJoin = rootObj.join("billingUom");
        Join<Sku, Uom> toOrderingUomJoin = rootObj.join("orderingUom");
        Join<Sku, LocationRestriction> toLocationRestrictionJoin = rootObj.join("locationRestriction", JoinType.LEFT);
        Join<Sku, PackingGroup> toPackingGroup = rootObj.join("packingGroup", JoinType.LEFT);
        Join<Sku, Sku> toBaseSkuJoin = rootObj.join("baseSku", JoinType.LEFT);
        Join<Sku, CostBucket> toCostBucketJoin = rootObj.join("costBucket", JoinType.LEFT);
        Join<Sku, MheType> toMheTypeJoin = rootObj.join("mheType", JoinType.LEFT);
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toPrimaryCompanyJoin.get("id"),toPrimaryCompanyJoin.get("code"),toPrimaryCompanyJoin.get("name")
                , rootObj.get("id"), rootObj.get("code"),rootObj.get("name"),
                toBillingUomJoin.get("name"), toOrderingUomJoin.get("name"), toBillingUomJoin.get("name"),
                toOrderingUomJoin.get("name"), toStorageUomJoin.get("name"), 
                rootObj.get("netWeight"), rootObj.get("inComplexity"), rootObj.get("outComplexity"),
                toMheTypeJoin.get("name"), rootObj.get("length"), rootObj.get("height"), rootObj.get("width"),
                rootObj.get("grossWeight"));
                

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
            if (Objects.nonNull(request.getStorageCategoryId())) {
                predicates.add(criteriaBuilder.equal(toStorageCategory.get("id"), request.getStorageCategoryId()));
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
            if (Objects.nonNull(request.getBillingUomId())) {
                predicates.add(criteriaBuilder.equal(toBillingUomJoin.get("id"), request.getBillingUomId()));
            }
            if (Objects.nonNull(request.getOrderingUomId())) {
                predicates.add(criteriaBuilder.equal(toOrderingUomJoin.get("id"), request.getOrderingUomId()));
            }
            if (Objects.nonNull(request.getLocationRestrictionId())) {
                predicates.add(
                        criteriaBuilder.equal(toLocationRestrictionJoin.get("id"), request.getLocationRestrictionId()));
            }
            if (Objects.nonNull(request.getPackingGroupId())) {
                predicates.add(criteriaBuilder.equal(toPackingGroup.get("id"), request.getPackingGroupId()));
            }
            if (Objects.nonNull(request.getBaseSkuId())) {
                predicates.add(criteriaBuilder.equal(toBaseSkuJoin.get("id"), request.getBaseSkuId()));
            }
            if (Objects.nonNull(request.getCostBucketId())) {
                predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
            }
            if (Objects.nonNull(request.getMheTypeId())) {
                predicates.add(criteriaBuilder.equal(toMheTypeJoin.get("id"), request.getMheTypeId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<SkuUomMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<SkuUomMappingListResponse> arrSkuUomMappingListResponse = typedQuery.getResultList();

        for (SkuUomMappingListResponse skuUomMappingListResponse : arrSkuUomMappingListResponse) {
            skuUomMappingListResponse.setSkuUomMappingList(
                    skuUomMappingSearchRepository.findAllWithFilters(skuUomMappingListResponse.getSkuId()));
        }

        return arrSkuUomMappingListResponse;
    }

    private List<Predicate> getPredicate(SkuRequest request, Root<Sku> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        System.out.println("Number of Component "+request.getNoOfComponents());

        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }
        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }
        if (Objects.nonNull(request.getBrand())) {
            predicates.add(criteriaBuilder.like(rootObj.get("brand"), "%" + request.getBrand() + "%"));
        }
        if (Objects.nonNull(request.getModel())) {
            predicates.add(criteriaBuilder.like(rootObj.get("model"), "%" + request.getModel() + "%"));
        }
        if (Objects.nonNull(request.getSize())) {
            predicates.add(criteriaBuilder.like(rootObj.get("size"), "%" + request.getSize() + "%"));
        }
        if (Objects.nonNull(request.getColour())) {
            predicates.add(criteriaBuilder.like(rootObj.get("colour"), "%" + request.getColour() + "%"));
        }
        if (Objects.nonNull(request.getReferenceSKU())) {
            predicates.add(criteriaBuilder.like(rootObj.get("referenceSKU"), "%" + request.getReferenceSKU() + "%"));
        }
        if (Objects.nonNull(request.getBarCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("barCode"), "%" + request.getBarCode() + "%"));
        }
        if (Objects.nonNull(request.getHSCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("HSCode"), "%" + request.getHSCode() + "%"));
        }
        if (Objects.nonNull(request.getABCCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("ABCCode"), "%" + request.getABCCode() + "%"));
        }
        if (Objects.nonNull(request.getOriginType())) {
            predicates.add(criteriaBuilder.like(rootObj.get("originType"), "%" + request.getOriginType() + "%"));
        }
        if (Objects.nonNull(request.getBillingStorage())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("billingStorage"), request.getBillingStorage()));
        }
        if (Objects.nonNull(request.getOrderStorage())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("orderStorage"), request.getOrderStorage()));
        }
        if (Objects.nonNull(request.getMaxQtyPerTrip())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxQtyPerTrip"), request.getMaxQtyPerTrip()));
        }
        if (Objects.nonNull(request.getPurchasePrice())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("purchasePrice"), request.getPurchasePrice()));
        }
        if (Objects.nonNull(request.getSalesPrice())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("salesPrice"), request.getSalesPrice()));
        }
        if (Objects.nonNull(request.getPurchaseLotSize())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("purchaseLotSize"), request.getPurchaseLotSize()));
        }
        if (Objects.nonNull(request.getSalesLotSize())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("salesLotSize"), request.getSalesLotSize()));
        }
        if (Objects.nonNull(request.getMinPoQty())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minPoQty"), request.getMinPoQty()));
        }
        if (Objects.nonNull(request.getMaxPoQty())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxPoQty"), request.getMaxPoQty()));
        }
        if (Objects.nonNull(request.getMinSoQty())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minSoQty"), request.getMinSoQty()));
        }
        if (Objects.nonNull(request.getMaxSoQty())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxSoQty"), request.getMaxSoQty()));
        }
        if (Objects.nonNull(request.getDangerLevel())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("dangerLevel"), request.getDangerLevel()));
        }
        if (Objects.nonNull(request.getLength())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("length"), request.getLength()));
        }
        if (Objects.nonNull(request.getHeight())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("height"), request.getHeight()));
        }
        if (Objects.nonNull(request.getWidth())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("width"), request.getWidth()));
        }
        if (Objects.nonNull(request.getGrossWeight())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("grossWeight"), request.getGrossWeight()));
        }
        if (Objects.nonNull(request.getGrossVolume())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("grossVolume"), request.getGrossVolume()));
        }
        if (Objects.nonNull(request.getNoOfComponents())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("noOfComponents"), request.getNoOfComponents()));
        }
        if (Objects.nonNull(request.getMinFreeShelfLife())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minFreeShelfLife"), request.getMinFreeShelfLife()));
        }
        if (Objects.nonNull(request.getMinFreeShelfLifeUnit())) {
            predicates
                    .add(criteriaBuilder.equal(rootObj.get("minFreeShelfLifeUnit"), request.getMinFreeShelfLifeUnit()));
        }
        if (Objects.nonNull(request.getTotalShelfLife())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("totalShelfLife"), request.getTotalShelfLife()));
        }
        if (Objects.nonNull(request.getTotalShelfLifeUnit())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("totalShelfLifeUnit"), request.getTotalShelfLifeUnit()));
        }
        if (Objects.nonNull(request.getAllocRule())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("allocRule"), request.getAllocRule()));
        }
        if (Objects.nonNull(request.getSerialNoValidationRule())) {
            predicates.add(
                    criteriaBuilder.equal(rootObj.get("serialNoValidationRule"), request.getSerialNoValidationRule()));
        }
        if (Objects.nonNull(request.getxFactor())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("xFactor"), request.getxFactor()));
        }
        if (Objects.nonNull(request.getMinAllocQty())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minAllocQty"), request.getMinAllocQty()));
        }
        if (Objects.nonNull(request.getMinPutawayQty())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minPutawayQty"), request.getMinPutawayQty()));
        }
        if (Objects.nonNull(request.getNetWeight())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("netWeight"), request.getNetWeight()));
        }
        if (Objects.nonNull(request.getInComplexity())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("inComplexity"), request.getInComplexity()));
        }
        if (Objects.nonNull(request.getOutComplexity())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("outComplexity"), request.getOutComplexity()));
        }
        if (Objects.nonNull(request.getWeightCaptureLotSize())) {
            predicates
                    .add(criteriaBuilder.equal(rootObj.get("weightCaptureLotSize"), request.getWeightCaptureLotSize()));
        }
        if (Objects.nonNull(request.getLoadingPattern())) {
            predicates
                    .add(criteriaBuilder.like(rootObj.get("loadingPattern"), "%" + request.getLoadingPattern() + "%"));
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

        if (Objects.nonNull(request.getGrossVolumeCalculate())) {
            if (request.getGrossVolumeCalculate().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("grossVolumeCalculate"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("grossVolumeCalculate"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getGrossVolumeCalculate().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("grossVolumeCalculate"), true));
            } else if (request.getGrossVolumeCalculate().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("grossVolumeCalculate"), false));
            }
        }
        if (Objects.nonNull(request.getKit())) {
            if (request.getKit().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("kit"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("kit"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getKit().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("kit"), true));
            } else if (request.getKit().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("kit"), false));
            }
        }
        if (Objects.nonNull(request.getManufactureDt())) {
            if (request.getManufactureDt().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("manufactureDt"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("manufactureDt"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getManufactureDt().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("manufactureDt"), true));
            } else if (request.getManufactureDt().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("manufactureDt"), false));
            }
        }
        if (Objects.nonNull(request.getSerialNo())) {
            if (request.getSerialNo().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("serialNo"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("serialNo"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getSerialNo().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("serialNo"), true));
            } else if (request.getSerialNo().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("serialNo"), false));
            }
        }
        if (Objects.nonNull(request.getBatch())) {
            if (request.getBatch().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("batch"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("batch"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getBatch().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("batch"), true));
            } else if (request.getBatch().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("batch"), false));
            }
        }
        if (Objects.nonNull(request.getExpiryDt())) {
            if (request.getExpiryDt().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("expiryDt"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("expiryDt"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getExpiryDt().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("expiryDt"), true));
            } else if (request.getExpiryDt().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("expiryDt"), false));
            }
        }
        if (Objects.nonNull(request.getSellable())) {
            if (request.getSellable().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("sellable"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("sellable"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getSellable().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("sellable"), true));
            } else if (request.getSellable().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("sellable"), false));
            }
        }
        if (Objects.nonNull(request.getBarcodeRequired())) {
            if (request.getBarcodeRequired().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("barcodeRequired"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("barcodeRequired"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getBarcodeRequired().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("barcodeRequired"), true));
            } else if (request.getBarcodeRequired().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("barcodeRequired"), false));
            }
        }
        if (Objects.nonNull(request.getWeightRequired())) {
            if (request.getWeightRequired().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("weightRequired"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("weightRequired"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getWeightRequired().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("weightRequired"), true));
            } else if (request.getWeightRequired().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("weightRequired"), false));
            }
        }
        if (Objects.nonNull(request.getPurchasable())) {
            if (request.getPurchasable().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("purchasable"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("purchasable"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getPurchasable().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("purchasable"), true));
            } else if (request.getPurchasable().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("purchasable"), false));
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
