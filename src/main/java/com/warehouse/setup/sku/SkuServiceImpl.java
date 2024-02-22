package com.warehouse.setup.sku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketSearchRepository;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.skutype.SkuTypeRepository;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.uom.UomRepository;
import com.warehouse.administration.user.User;
import com.warehouse.country.Country;
import com.warehouse.country.CountryRepository;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.packinggroup.PackingGroup;
import com.warehouse.setup.company.packinggroup.PackingGroupRepository;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRepository;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRequest;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingSearchRepository;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.location.restriction.LocationRestrictionRepository;
import com.warehouse.setup.sku.category.SkuCategory;
import com.warehouse.setup.sku.category.SkuCategoryRepository;
import com.warehouse.setup.sku.category.SkuCategoryRequest;
import com.warehouse.setup.sku.category.SkuCategorySearchRepository;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMapping;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMappingRepository;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMappingRequest;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMappingSearchRepository;
import com.warehouse.setup.sku.managekitbom.ManageKitBomHeaderRequest;
import com.warehouse.setup.sku.managekitbom.ManageKitBomSearchRepository;
import com.warehouse.setup.sku.managekitbom.SkuManageKitBom;
import com.warehouse.setup.sku.managekitbom.SkuManageKitBomRepository;
import com.warehouse.setup.sku.managekitbom.SkuManageKitBomResponse;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategory;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategoryRepository;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategoryRequest;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategorySearchRepository;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMapping;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMappingListResponse;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMappingRepository;
import com.warehouse.setup.sku.uommapping.SkuUomMapping;
import com.warehouse.setup.sku.uommapping.SkuUomMappingListResponse;
import com.warehouse.setup.sku.uommapping.SkuUomMappingRepository;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMapping;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMappingListResponse;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMappingRepository;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMappingSearchRepository;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.supplier.SupplierRepository;
import com.warehouse.setup.supplier.SupplierRequest;
import com.warehouse.setup.supplier.SupplierSearchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuStorageCategoryRepository skuStorageCategoryRepository;

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private SkuStorageCategorySearchRepository skuStorageCategorySearchRepository;

    @Autowired
    private SkuCategoryRepository skuCategoryRepository;

    @Autowired
    private SkuCategorySearchRepository skuCategorySearchRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private SkuSearchRepository skuSearchRepository;

    @Autowired
    private WarehouseCompanyMappingSearchRepository warehouseCompanyMappingSearchRepository;

    @Autowired
    private SkuWarehouseMappingSearchRepository skuWarehouseMappingSearchRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SkuWarehouseMappingRepository skuWarehouseMappingRepository;

    @Autowired
    private SkuSupplierMappingRepository skuSupplierMappingRepository;

    @Autowired
    private SupplierSearchRepository supplierSearchRepository;

    @Autowired
    private UomRepository uomRepository;

    @Autowired
    private SkuTypeRepository skuTypeRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LocationRestrictionRepository locationRestrictionRepository;

    @Autowired
    private PackingGroupRepository packingGroupRepository;

    @Autowired
    private SkuUomMappingRepository skuUomMappingRepository;

    @Autowired
    private SkuCostBucketMappingRepository skuCostBucketMappingRepository;

    @Autowired
    private WarehouseCompanyMappingRepository warehouseCompanyMappingRepository;

    @Autowired
    private CostBucketSearchRepository costBucketSearchRepository;

    @Autowired
    private SkuCostBucketMappingSearchRepository skuCostBucketMappingSearchRepository;

    @Autowired
    private ManageKitBomSearchRepository manageKitBomSearchRepository;

    @Autowired
    private SkuManageKitBomRepository skuManageKitBomRepository;

    @Override
    public String validateSkuCategoryRequest(SkuCategory theSkuCategory) {

        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theSkuCategory.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theSkuCategory.getPrimaryCompanyId()));

        theSkuCategory.setPrimaryCompany(primaryCompany);

        if (theSkuCategory.getId() == null) {
            if (skuCategoryRepository
                    .findByNameAndPrimaryCompanyId(theSkuCategory.getName(), theSkuCategory.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Storage Category with name:" + theSkuCategory.getName()
                        + " and Primary Company:" + primaryCompany.getName();
            }
            if (skuCategoryRepository
                    .findByCodeAndPrimaryCompanyId(theSkuCategory.getCode(), theSkuCategory.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Storage Category Type with code:" + theSkuCategory.getCode()
                        + " and Primary Company:" + primaryCompany.getName();
            }

        } else {
            if (skuCategoryRepository.findByNameAndPrimaryCompanyIdAndIdNot(theSkuCategory.getName(),
                    theSkuCategory.getPrimaryCompanyId(), theSkuCategory.getId()).isPresent()) {
                return "Duplicate value found for Storage Category with id:" + theSkuCategory.getId() + " and name:"
                        + theSkuCategory.getName() + " and Primary Company:" + primaryCompany.getName();
            }
            if (skuCategoryRepository.findByCodeAndPrimaryCompanyIdAndIdNot(theSkuCategory.getCode(),
                    theSkuCategory.getPrimaryCompanyId(), theSkuCategory.getId()).isPresent()) {
                return "Duplicate value found for Storage Category with id:" + theSkuCategory.getId() + " and code:"
                        + theSkuCategory.getCode() + " and Primary Company:" + primaryCompany.getName();
            }
        }

        return null;
    }

    @Override
    public SkuCategory saveSkuCategory(SkuCategory theSkuCategory) {
        return skuCategoryRepository.save(theSkuCategory);
    }

    @Override
    public SkuCategory findBySkuCategoryId(Long theId) {
        return skuCategoryRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", theId));
    }

    @Override
    public Page<SkuCategory> findAllSkuCategoryList(PageHelper page, SkuCategoryRequest request) {
        return skuCategorySearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteBySkuCategoryId(Long theId) {
        skuCategoryRepository.deleteById(theId);
    }

    @Override
    public String validateSkuStorageCategoryRequest(SkuStorageCategory theSkuStorageCategory) {

        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theSkuStorageCategory.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theSkuStorageCategory.getPrimaryCompanyId()));
        theSkuStorageCategory.setPrimaryCompany(primaryCompany);

        if (theSkuStorageCategory.getId() == null) {
            if (skuStorageCategoryRepository.findByNameAndPrimaryCompanyId(theSkuStorageCategory.getName(),
                    theSkuStorageCategory.getPrimaryCompanyId()).isPresent()) {
                return "Duplicate value found for Category with name:" + theSkuStorageCategory.getName()
                        + " and Primary Company:" + primaryCompany.getName();
            }
            if (skuStorageCategoryRepository.findByCodeAndPrimaryCompanyId(theSkuStorageCategory.getCode(),
                    theSkuStorageCategory.getPrimaryCompanyId()).isPresent()) {
                return "Duplicate value found for Category Type with code:" + theSkuStorageCategory.getCode()
                        + " and Primary Company:" + primaryCompany.getName();
            }

        } else {
            if (skuStorageCategoryRepository.findByNameAndPrimaryCompanyIdAndIdNot(theSkuStorageCategory.getName(),
                    theSkuStorageCategory.getPrimaryCompanyId(), theSkuStorageCategory.getId()).isPresent()) {
                return "Duplicate value found for Category with id:" + theSkuStorageCategory.getId() + " and name:"
                        + theSkuStorageCategory.getName() + " and Primary Company:" + primaryCompany.getName();
            }
            if (skuStorageCategoryRepository.findByCodeAndPrimaryCompanyIdAndIdNot(theSkuStorageCategory.getCode(),
                    theSkuStorageCategory.getPrimaryCompanyId(), theSkuStorageCategory.getId()).isPresent()) {
                return "Duplicate value found for Category with id:" + theSkuStorageCategory.getId() + " and code:"
                        + theSkuStorageCategory.getCode() + " and Primary Company:" + primaryCompany.getName();
            }
        }

        return null;
    }

    @Override
    public SkuStorageCategory saveSkuStorageCategory(SkuStorageCategory theSkuStorageCategory) {
        return skuStorageCategoryRepository.save(theSkuStorageCategory);
    }

    @Override
    public SkuStorageCategory findBySkuStorageCategoryId(Long theId) {
        return skuStorageCategoryRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", theId));
    }

    @Override
    public Page<SkuStorageCategory> findAllSkuStorageCategoryList(PageHelper page, SkuStorageCategoryRequest request) {
        return skuStorageCategorySearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteBySkuStorageCategoryId(Long theId) {
        skuStorageCategoryRepository.deleteById(theId);
    }

    // Sku
    @Override
    public String validateSkuRequest(Sku theSku) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theSku.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id", theSku.getPrimaryCompanyId()));
        theSku.setPrimaryCompany(primaryCompany);

        if (Objects.nonNull(theSku.getSupplierId())) {
            Supplier supplier = supplierRepository.findById(theSku.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", theSku.getSupplierId()));
            theSku.setSupplier(supplier);
        }

        if (Objects.nonNull(theSku.getSkuTypeId())) {
            SkuType skuType = skuTypeRepository.findById(theSku.getSkuTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sku Type", "id", theSku.getSkuTypeId()));
            theSku.setSkuType(skuType);
        }

        if (Objects.nonNull(theSku.getSkuCategoryId())) {
            SkuCategory skuCategory = skuCategoryRepository.findById(theSku.getSkuCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sku Category", "id", theSku.getSkuCategoryId()));
            theSku.setSkuCategory(skuCategory);
        }

        if (Objects.nonNull(theSku.getStorageCategoryId())) {
            SkuStorageCategory storageCategory = skuStorageCategoryRepository.findById(theSku.getStorageCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Storage Category", "id",
                            theSku.getStorageCategoryId()));
            theSku.setStorageCategory(storageCategory);
        }

        if (Objects.nonNull(theSku.getCountryId())) {
            Country country = countryRepository.findById(theSku.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country", "id", theSku.getCountryId()));
            theSku.setCountry(country);
        }

        if (Objects.nonNull(theSku.getBillingUomId())) {
            Uom billingUom = uomRepository.findById(theSku.getBillingUomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Billing Uom", "id", theSku.getBillingUomId()));
            theSku.setBillingUom(billingUom);
        }
        if (Objects.nonNull(theSku.getOrderingUomId())) {
            Uom orderingUom = uomRepository.findById(theSku.getOrderingUomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ordering Uom", "id", theSku.getOrderingUomId()));
            theSku.setOrderingUom(orderingUom);
        }
        if (Objects.nonNull(theSku.getStorageUomId())) {
            Uom storageUom = uomRepository.findById(theSku.getStorageUomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Storage Uom", "id", theSku.getStorageUomId()));
            theSku.setStorageUom(storageUom);
        }

        if (Objects.nonNull(theSku.getLocationRestrictionId())) {
            LocationRestriction locationRestriction = locationRestrictionRepository
                    .findById(theSku.getLocationRestrictionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location Restriction", "id",
                            theSku.getLocationRestrictionId()));
            theSku.setLocationRestriction(locationRestriction);
        }
        if (Objects.nonNull(theSku.getPackingGroupId())) {
            PackingGroup packingGroup = packingGroupRepository.findById(theSku.getPackingGroupId()).orElseThrow(
                    () -> new ResourceNotFoundException("Packing Group", "id", theSku.getPackingGroupId()));
            theSku.setPackingGroup(packingGroup);
        }

        if (Objects.nonNull(theSku.getBaseSkuId())) {
            Sku baseSku = skuRepository.findById(theSku.getBaseSkuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sku", "id", theSku.getBaseSkuId()));
            theSku.setBaseSku(baseSku);
        }

        if (Objects.nonNull(theSku.getCostBucketId())) {
            // Will Add Later
        }

        if (Objects.nonNull(theSku.getMheTypeId())) {
            // Will Add Later
        }

        if (theSku.getId() == null) {
            if (skuRepository.findByNameAndPrimaryCompanyId(theSku.getName(), theSku.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Sku with name:" + theSku.getName() + " and Primary Company:"
                        + primaryCompany.getName();
            }
            if (skuRepository.findByCodeAndPrimaryCompanyId(theSku.getCode(), theSku.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Sku with code:" + theSku.getCode() + " and Primary Company:"
                        + primaryCompany.getName();
            }

        } else {
            if (skuRepository.findByNameAndPrimaryCompanyIdAndIdNot(theSku.getName(), theSku.getPrimaryCompanyId(),
                    theSku.getId()).isPresent()) {
                return "Duplicate value found for Sku with id:" + theSku.getId() + " and name:" + theSku.getName()
                        + " and Primary Company:" + primaryCompany.getName();
            }
            if (skuRepository.findByCodeAndPrimaryCompanyIdAndIdNot(theSku.getCode(), theSku.getPrimaryCompanyId(),
                    theSku.getId()).isPresent()) {
                return "Duplicate value found for Sku with id:" + theSku.getId() + " and code:" + theSku.getCode()
                        + " and Primary Company:" + primaryCompany.getName();
            }
        }

        return null;
    }

    @Override
    public Sku saveSku(Sku theSku) {

        Boolean isMapToAllWarehouse = theSku.getMapAllWarehouses();
        Boolean isMapToSuppliers = theSku.getMapAllSupplier();
        Boolean isMapToCostBuckets = theSku.getMapAllCostBuckets();
        Sku skuObj = skuRepository.save(theSku);
        if (skuObj.getId() == null) {
            return skuObj;
        }

        if (isMapToAllWarehouse) {
            theSku.setMapToWarehouseIds("");
        }
        List<SkuWarehouseMapping> allWarehouseMapping = new ArrayList<SkuWarehouseMapping>();
        List<WarehouseCompanyMapping> skuWarehouseMappingList = skuWarehouseMappingSearchRepository
                .findAll(theSku.getMapToWarehouseIds(), theSku.getPrimaryCompanyId());

        for (WarehouseCompanyMapping warehouseCompanyMapping : skuWarehouseMappingList) {
            SkuWarehouseMapping skuWarehouseMapping = new SkuWarehouseMapping();
            skuWarehouseMapping.setWarehouseCompanyMappingId(warehouseCompanyMapping.getId());
            skuWarehouseMapping.setSupplierId(theSku.getSupplierId());
            skuWarehouseMapping.setSkuId(skuObj.getId());
            skuWarehouseMapping.setDangerLevel(theSku.getDangerLevel());
            skuWarehouseMapping.setAllocRule(theSku.getAllocRule());
            skuWarehouseMapping.setCreatedBy(theSku.getCreatedBy());
            skuWarehouseMapping.setModifiedBy(theSku.getModifiedBy());
            validateSkuWarehouseMappingRequest(skuWarehouseMapping);
            allWarehouseMapping.add(skuWarehouseMapping);
        }
        allWarehouseMapping = saveSkuWarehouseMapping(allWarehouseMapping);

        Long supplierId = theSku.getSupplierId();

        List<SkuSupplierMapping> allSkuSupplierMapping = new ArrayList<SkuSupplierMapping>();
        List<SkuSupplierMappingListResponse> skuSupplierMappingList = supplierSearchRepository.findAll(supplierId,
                isMapToSuppliers);
        for (SkuSupplierMappingListResponse skuSupplierMappingListResponse : skuSupplierMappingList) {
            SkuSupplierMapping skuSupplierMapping = new SkuSupplierMapping();
            skuSupplierMapping.setPrimaryCompanyId(skuSupplierMappingListResponse.getPrimaryCompanyId());
            skuSupplierMapping.setSupplierId(theSku.getSupplierId());
            skuSupplierMapping.setSkuId(skuObj.getId());
            skuSupplierMapping.setPurchasePrice(theSku.getPurchasePrice());
            skuSupplierMapping.setCreatedBy(theSku.getCreatedBy());
            skuSupplierMapping.setModifiedBy(theSku.getModifiedBy());
            validateSkuSupplierMappingRequest(skuSupplierMapping);
            allSkuSupplierMapping.add(skuSupplierMapping);
        }
        saveSkuSupplierMapping(allSkuSupplierMapping);

        if (isMapToCostBuckets) {
            theSku.setCostBucketId(null);
        }
        List<SkuCostBucketMapping> allCostBucketMapping = new ArrayList<SkuCostBucketMapping>();
        List<CostBucket> allCostBuckets = costBucketSearchRepository.findAll();

        for (SkuWarehouseMapping skuWarehouseMapping : allWarehouseMapping) {
            for (CostBucket costBucket : allCostBuckets) {
                SkuCostBucketMapping skuCostBucketMapping = new SkuCostBucketMapping();
                skuCostBucketMapping.setSkuWarehouseMappingId(skuWarehouseMapping.getId());
                skuCostBucketMapping.setSkuWarehouseMapping(skuWarehouseMapping);
                skuCostBucketMapping.setCostBucket(costBucket);
                skuCostBucketMapping.setCostBucketId(costBucket.getId());
                skuCostBucketMapping.setCreatedBy(theSku.getCreatedBy());
                skuCostBucketMapping.setModifiedBy(theSku.getModifiedBy());
                skuCostBucketMapping.setMovingAverage(0F);
                allCostBucketMapping.add(skuCostBucketMapping);
            }
        }
        saveSkuCostBucketMapping(allCostBucketMapping);

        List<SkuUomMapping> allSkuUomMapping = new ArrayList<SkuUomMapping>();
        ArrayList<Uom> listUom = new ArrayList<Uom>(
                Arrays.asList(theSku.getStorageUom(), theSku.getBillingUom(), theSku.getOrderingUom()));

        ArrayList<Long> uomIdList = new ArrayList<Long>();
        for (int i = 0; i < listUom.size(); i++) {
            Uom uom = listUom.get(i);
            if (uomIdList.contains(uom.getId())) {
                continue;
            }
            uomIdList.add(uom.getId());

            SkuUomMapping skuUomMapping = new SkuUomMapping();
            skuUomMapping.setPrimaryCompanyId(theSku.getPrimaryCompanyId());
            skuUomMapping.setPrimaryCompany(theSku.getPrimaryCompany());
            skuUomMapping.setSkuId(skuObj.getId());
            skuUomMapping.setSku(skuObj);
            skuUomMapping.setUomId(uom.getId());
            skuUomMapping.setUom(uom);
            skuUomMapping.setMultiplicationFactorToConvertStorageUom(1f);
            skuUomMapping.setBarcode(skuObj.getCode());
            if (i == 1) {
                if (theSku.getBillingStorage() > 0) {
                    skuUomMapping.setMultiplicationFactorToConvertStorageUom(theSku.getBillingStorage());
                }
                skuUomMapping.setIsDefaultPurchaseBillingUom(true);
                skuUomMapping.setIsDefaultSalesBillingUom(true);
                skuUomMapping.setBarcode(skuObj.getId() + "-" + uom.getId());
            } else if (i == 2) {
                if (theSku.getOrderStorage() > 0) {
                    skuUomMapping.setMultiplicationFactorToConvertStorageUom(theSku.getOrderStorage());
                }
                skuUomMapping.setIsDefaultPurchaseOrderingUom(true);
                skuUomMapping.setIsDefaultSalesOrderingUom(true);
                skuUomMapping.setBarcode(skuObj.getId() + "-" + uom.getId());
            }
            allSkuUomMapping.add(skuUomMapping);
        }
        saveSkuUomMapping(allSkuUomMapping);

        return skuObj;
    }

    @Override
    public Sku findBySkuId(Long theId) {
        return skuRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Sku", "id", theId));
    }

    @Override
    public Page<Sku> findAllSkuList(PageHelper page, SkuRequest request) {
        return skuSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteBySkuId(Long theId) {
        skuRepository.deleteById(theId);
    }

    @Override
    public Page<WarehouseCompanyMapping> findAllWarehouseCompanyMapping(PageHelper page,
            WarehouseCompanyMappingRequest request) {
        return warehouseCompanyMappingSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public List<SkuWarehouseMappingListResponse> findAllWarehouseWithWarehouseSkuMapping(
            WarehouseCompanyMappingRequest request) {
        return skuWarehouseMappingSearchRepository.findAllWithFilters(request);
    }

    @Override
    public String validateSkuWarehouseMappingRequest(SkuWarehouseMapping theSkuWarehouseMapping) {
        if (Objects.nonNull(theSkuWarehouseMapping.getWarehouseCompanyMappingId())) {
            WarehouseCompanyMapping warehouseCompanyMapping = warehouseCompanyMappingRepository
                    .findById(theSkuWarehouseMapping.getWarehouseCompanyMappingId())
                    .orElseThrow(() -> new ResourceNotFoundException("WarehouseCompanyMapping", "id",
                            theSkuWarehouseMapping.getWarehouseCompanyMappingId()));
            theSkuWarehouseMapping.setWarehouseCompanyMapping(warehouseCompanyMapping);
        }

        Sku sku = skuRepository.findById(theSkuWarehouseMapping.getSkuId())
                .orElseThrow(() -> new ResourceNotFoundException("Sku", "id", theSkuWarehouseMapping.getSkuId()));
        theSkuWarehouseMapping.setSku(sku);

        Supplier supplier = supplierRepository.findById(theSkuWarehouseMapping.getSupplierId()).orElseThrow(
                () -> new ResourceNotFoundException("Supplier", "id", theSkuWarehouseMapping.getSupplierId()));
        theSkuWarehouseMapping.setSupplier(supplier);

        if (theSkuWarehouseMapping.getId() == null) {
            if (skuWarehouseMappingRepository
                    .findByWarehouseCompanyMappingIdAndSkuId(theSkuWarehouseMapping.getWarehouseCompanyMappingId(),
                            theSkuWarehouseMapping.getSkuId())
                    .isPresent()) {
                return "Duplicate value found for Mapping with Primary Company :"
                        + theSkuWarehouseMapping.getWarehouseCompanyMapping().getPrimaryCompanyId() + " and Warehouse:"
                        + theSkuWarehouseMapping.getWarehouseCompanyMapping().getWarehouseId() + " and Sku:"
                        + theSkuWarehouseMapping.getSku().getId();
            }
        } else {
            if (skuWarehouseMappingRepository.findByWarehouseCompanyMappingIdAndSkuIdAndIdNot(
                    theSkuWarehouseMapping.getWarehouseCompanyMappingId(), theSkuWarehouseMapping.getSkuId(),
                    theSkuWarehouseMapping.getId()).isPresent()) {
                return "Duplicate value found for Mapping with with ID:" + theSkuWarehouseMapping.getId()
                        + " and Primary Company :"
                        + theSkuWarehouseMapping.getWarehouseCompanyMapping().getPrimaryCompanyId() + " and Warehouse:"
                        + theSkuWarehouseMapping.getWarehouseCompanyMapping().getWarehouseId() + " and Sku:"
                        + theSkuWarehouseMapping.getSku().getId();
            }

        }
        return null;
    }

    @Override
    public List<SkuWarehouseMapping> saveSkuWarehouseMapping(List<SkuWarehouseMapping> theSkuWarehouseMapping) {
        return skuWarehouseMappingRepository.saveAll(theSkuWarehouseMapping);
    }

    @Override
    public SkuWarehouseMapping findBySkuWarehouseMappingId(Long theId) {
        return skuWarehouseMappingRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("SkuWarehouseMapping", "id", theId));
    }

    @Override
    public void deleteAllSkuWarehouseMapping(List<SkuWarehouseMapping> theSkuWarehouseMapping) {
        skuWarehouseMappingRepository.deleteAll(theSkuWarehouseMapping);

    }

    // Sku Supplier Mapping

    @Override
    public String validateSkuSupplierMappingRequest(SkuSupplierMapping theSkuSupplierMapping) {

        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theSkuSupplierMapping.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theSkuSupplierMapping.getPrimaryCompanyId()));
        theSkuSupplierMapping.setPrimaryCompany(primaryCompany);

        Sku sku = skuRepository.findById(theSkuSupplierMapping.getSkuId())
                .orElseThrow(() -> new ResourceNotFoundException("Sku", "id", theSkuSupplierMapping.getSkuId()));
        theSkuSupplierMapping.setSku(sku);

        Supplier supplier = supplierRepository.findById(theSkuSupplierMapping.getSupplierId()).orElseThrow(
                () -> new ResourceNotFoundException("Supplier", "id", theSkuSupplierMapping.getSupplierId()));
        theSkuSupplierMapping.setSupplier(supplier);

        if (theSkuSupplierMapping.getId() == null) {
            if (skuSupplierMappingRepository
                    .findByPrimaryCompanyIdAndSupplierIdAndSkuId(theSkuSupplierMapping.getPrimaryCompanyId(),
                            theSkuSupplierMapping.getSupplierId(), theSkuSupplierMapping.getSkuId())
                    .isPresent()) {
                return "Duplicate value found for Mapping with Primary Company Name:"
                        + theSkuSupplierMapping.getPrimaryCompany().getName() + " and Supplier:"
                        + theSkuSupplierMapping.getSupplier().getName() + " and Sku:"
                        + theSkuSupplierMapping.getSku().getName();
            }
        } else {
            if (skuSupplierMappingRepository.findByPrimaryCompanyIdAndSupplierIdAndSkuIdAndIdNot(
                    theSkuSupplierMapping.getPrimaryCompanyId(), theSkuSupplierMapping.getSupplierId(),
                    theSkuSupplierMapping.getSkuId(), theSkuSupplierMapping.getId()).isPresent()) {
                return "Duplicate value found for Mapping with with ID:" + theSkuSupplierMapping.getId()
                        + " and Primary Company Name:" + theSkuSupplierMapping.getPrimaryCompany().getName()
                        + " and Supplier:" + theSkuSupplierMapping.getSupplier().getName() + " and Sku:"
                        + theSkuSupplierMapping.getSku().getName();
            }

        }
        return null;
    }

    @Override
    public List<SkuSupplierMappingListResponse> findAllSupplierWithSkuSupplierMapping(SupplierRequest request) {
        return supplierSearchRepository.findAllWithFilters(request);
    }

    @Override
    public List<SkuSupplierMapping> saveSkuSupplierMapping(List<SkuSupplierMapping> theSkuSupplierMapping) {
        return skuSupplierMappingRepository.saveAll(theSkuSupplierMapping);
    }

    @Override
    public SkuSupplierMapping findBySkuSupplierMappingId(Long theId) {
        return skuSupplierMappingRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("SkuSupplierMapping", "id", theId));
    }

    @Override
    public void deleteAllSkuSupplierMapping(List<SkuSupplierMapping> theSkuSupplierMapping) {
        skuSupplierMappingRepository.deleteAll(theSkuSupplierMapping);
    }

    @Override
    public String validateSkuUomMappingRequest(SkuUomMapping theSkuUomMapping) {

        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theSkuUomMapping.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theSkuUomMapping.getPrimaryCompanyId()));
        theSkuUomMapping.setPrimaryCompany(primaryCompany);

        Uom uom = uomRepository.findById(theSkuUomMapping.getUomId())
                .orElseThrow(() -> new ResourceNotFoundException("Uom", "id", theSkuUomMapping.getUomId()));
        theSkuUomMapping.setUom(uom);

        Sku sku = skuRepository.findById(theSkuUomMapping.getSkuId())
                .orElseThrow(() -> new ResourceNotFoundException("Sku", "id", theSkuUomMapping.getSkuId()));
        theSkuUomMapping.setSku(sku);

        if (theSkuUomMapping.getId() == null) {
            if (skuUomMappingRepository.findByUomIdAndSkuId(theSkuUomMapping.getUomId(), theSkuUomMapping.getSkuId())
                    .isPresent()) {
                return "Duplicate value found with Uom:" + theSkuUomMapping.getUom().getName() + " and Sku:"
                        + theSkuUomMapping.getSku().getName();
            }

            if (skuUomMappingRepository.findByIsDefaultSalesOrderingUomTrueAndSkuId(theSkuUomMapping.getSkuId())
                    .isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultSalesOrderingUOM";
            }
            if (skuUomMappingRepository.findByIsDefaultSalesBillingUomTrueAndSkuId(theSkuUomMapping.getSkuId())
                    .isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultSalesBillingUom";
            }
            if (skuUomMappingRepository.findByIsDefaultPurchaseOrderingUomTrueAndSkuId(theSkuUomMapping.getSkuId())
                    .isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultPurchaseOrderingUom";
            }
            if (skuUomMappingRepository.findByIsDefaultPurchaseBillingUomTrueAndSkuId(theSkuUomMapping.getSkuId())
                    .isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultPurchaseBillingUom";
            }

        } else {
            if (skuUomMappingRepository.findByUomIdAndSkuIdAndIdNot(theSkuUomMapping.getUomId(),
                    theSkuUomMapping.getSkuId(), theSkuUomMapping.getId()).isPresent()) {
                return "Duplicate value found with ID " + theSkuUomMapping.getId() + "and Uom:"
                        + theSkuUomMapping.getUom().getName() + " and Sku:" + theSkuUomMapping.getSku().getName();
            }
            if (skuUomMappingRepository.findByIsDefaultSalesOrderingUomTrueAndSkuIdAndIdNot(theSkuUomMapping.getSkuId(),
                    theSkuUomMapping.getId()).isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultSalesOrderingUOM";
            }
            if (skuUomMappingRepository.findByIsDefaultSalesBillingUomTrueAndSkuIdAndIdNot(theSkuUomMapping.getSkuId(),
                    theSkuUomMapping.getId()).isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultSalesBillingUom";
            }
            if (skuUomMappingRepository.findByIsDefaultPurchaseOrderingUomTrueAndSkuIdAndIdNot(
                    theSkuUomMapping.getSkuId(), theSkuUomMapping.getId()).isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultPurchaseOrderingUom";
            }
            if (skuUomMappingRepository.findByIsDefaultPurchaseBillingUomTrueAndSkuIdAndIdNot(
                    theSkuUomMapping.getSkuId(), theSkuUomMapping.getId()).isPresent()) {
                return "The SKU " + theSkuUomMapping.getSku().getName()
                        + " can have only one UOM as DefaultPurchaseBillingUom";
            }

        }

        return null;
    }

    @Override
    public List<SkuUomMappingListResponse> findAllSkuWithSkuUomMapping(SkuRequest request) {
        return skuSearchRepository.findAllWithFilters(request);
    }

    @Override
    public List<SkuUomMapping> saveSkuUomMapping(List<SkuUomMapping> theSkuUomMapping) {
        return skuUomMappingRepository.saveAll(theSkuUomMapping);
    }

    @Override
    public SkuUomMapping findBySkuUomMappingId(Long theId) {
        return skuUomMappingRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("SkuUomMapping", "id", theId));
    }

    @Override
    public void deleteAllSkuUomMapping(List<SkuUomMapping> theSkuUomMapping) {
        skuUomMappingRepository.deleteAll(theSkuUomMapping);

    }

    @Override
    public List<SkuCostBucketMapping> saveSkuCostBucketMapping(List<SkuCostBucketMapping> theSkuCostBucketMapping) {
        return skuCostBucketMappingRepository.saveAll(theSkuCostBucketMapping);
    }

    @Override
    public List<SkuCostBucketMapping> findAllSkuCostBucketMapping(SkuCostBucketMappingRequest request) {
        return skuCostBucketMappingSearchRepository.findAll(request);
    }

    @Override
    public Page<SkuCostBucketMapping> findAllSkuCostBucketMapping(PageHelper page,
            SkuCostBucketMappingRequest request) {
        return skuCostBucketMappingSearchRepository.findAll(page, request);
    }

    @Override
    public Page<Sku> findSkuForManageKitBom(PageHelper page, SkuRequest request) {
        return manageKitBomSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public List<SkuManageKitBomResponse> findAllSkuForManageKitBom(SkuRequest request) {
        return manageKitBomSearchRepository.findAllWithFilters(request);
    }

    @Override
    public void saveSkuManageKitBom(List<ManageKitBomHeaderRequest> theManageKitBomHeaderRequestList) {
        for (ManageKitBomHeaderRequest manageKitBomHeaderRequest : theManageKitBomHeaderRequestList) {
            List<SkuManageKitBom> theSkuManageKitBoms = manageKitBomHeaderRequest.getSkuManageKitBomList();

            User user = WarehouseHelper.getLoggedInUser();

            for (SkuManageKitBom theSkuManageKitB : theSkuManageKitBoms) {

                theSkuManageKitB.setModifiedBy(user);
                theSkuManageKitB.setCreatedBy(user);

                if (theSkuManageKitB.getId() != null) {
                    SkuManageKitBom kitSku = skuManageKitBomRepository.findById(theSkuManageKitB.getId()).orElseThrow(
                            () -> new ResourceNotFoundException("SkuManageKitBom", "id", theSkuManageKitB.getId()));
                    theSkuManageKitB.setCreatedBy(kitSku.getCreatedBy());
                    theSkuManageKitB.setCreatedAt(kitSku.getCreatedAt());
                }

                PrimaryCompany primaryCompany = primaryCompanyRepository
                        .findById(theSkuManageKitB.getPrimaryCompanyId())
                        .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                theSkuManageKitB.getPrimaryCompanyId()));
                theSkuManageKitB.setPrimaryCompany(primaryCompany);

                Sku kitSku = skuRepository.findById(theSkuManageKitB.getKitSkuId()).orElseThrow(
                        () -> new ResourceNotFoundException("Kit Sku", "id", theSkuManageKitB.getKitSkuId()));
                theSkuManageKitB.setKitSku(kitSku);

                Sku componentSku = skuRepository.findById(theSkuManageKitB.getComponentSkuId())
                        .orElseThrow(() -> new ResourceNotFoundException("Component Sku", "id",
                                theSkuManageKitB.getComponentSkuId()));
                theSkuManageKitB.setComponentSku(componentSku);
            }

        }
        manageKitBomSearchRepository.updateManagerKitBom(theManageKitBomHeaderRequestList);
    }

    @Override
    public void deleteSkuManageKitBom(List<SkuManageKitBom> theSkuManageKitBomList) {
        skuManageKitBomRepository.deleteAll(theSkuManageKitBomList);
    }

}
