package com.warehouse.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRequest;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.SkuListAction;
import com.warehouse.setup.sku.SkuRequest;
import com.warehouse.setup.sku.SkuService;
import com.warehouse.setup.sku.category.SkuCategory;
import com.warehouse.setup.sku.category.SkuCategoryListAction;
import com.warehouse.setup.sku.category.SkuCategoryRequest;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMapping;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMappingRequest;
import com.warehouse.setup.sku.managekitbom.ManageKitBomHeaderRequest;
import com.warehouse.setup.sku.managekitbom.SkuManageKitBom;
import com.warehouse.setup.sku.managekitbom.SkuManageKitBomResponse;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategory;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategoryListAction;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategoryRequest;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMapping;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMappingListResponse;
import com.warehouse.setup.sku.uommapping.SkuUomMapping;
import com.warehouse.setup.sku.uommapping.SkuUomMappingListResponse;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMapping;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMappingListResponse;
import com.warehouse.setup.supplier.SupplierRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setup/sku")
public class SkuRestController {

    @Autowired
    private SkuService skuService;

    @PostMapping("/category/save")
    public SkuCategory addSkuCategory(@Valid @RequestBody SkuCategory theSkuCategory) {
        String error = skuService.validateSkuCategoryRequest(theSkuCategory);
        if (error != null) {
            throw new InvalidValueFoundException("SkuCategory", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSkuCategory.setCreatedBy(user);
        theSkuCategory.setModifiedBy(user);

        theSkuCategory = skuService.saveSkuCategory(theSkuCategory);
        return theSkuCategory;
    }

    @PutMapping("/category/update/{id}")
    public SkuCategory updateSkuCategory(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody SkuCategory theSkuCategory) {
        theSkuCategory.setId(theId);
        SkuCategory detailObj = skuService.findBySkuCategoryId(theId);

        String error = skuService.validateSkuCategoryRequest(theSkuCategory);
        if (error != null) {
            throw new InvalidValueFoundException("SkuCategory", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSkuCategory.setCreatedBy(detailObj.getCreatedBy());
        theSkuCategory.setModifiedBy(user);

        theSkuCategory.setCreatedAt(detailObj.getCreatedAt());
        detailObj = skuService.saveSkuCategory(theSkuCategory);
        return detailObj;
    }

    @PutMapping("/category/update")
    public SkuCategoryListAction updateSkuCategoryList(@Valid @RequestBody SkuCategoryListAction listAction) {

        SkuCategoryListAction responseListAction = new SkuCategoryListAction();
        for (SkuCategory singleObj : listAction) {
            skuService.findBySkuCategoryId(singleObj.getId());
            String error = skuService.validateSkuCategoryRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("SkuCategory", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (SkuCategory singleObj : listAction) {
            SkuCategory searchedObj = skuService.findBySkuCategoryId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            responseListAction.add(skuService.saveSkuCategory(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/category/detail/{id}")
    public SkuCategory getSkuCategoryById(@PathVariable(value = "id") Long theId) {
        return skuService.findBySkuCategoryId(theId);
    }

    @PostMapping("/category/list/search")
    public ResponseEntity<Page<SkuCategory>> getSkuCategoryList(PageHelper page,
            @RequestBody SkuCategoryRequest request) {
        return new ResponseEntity<>(skuService.findAllSkuCategoryList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<?> deleteSkuCategory(@PathVariable(value = "id") Long theId) {
        skuService.deleteBySkuCategoryId(theId);
        return ResponseEntity.ok().build();
    }

    // Storage Category api
    @PostMapping("/storage-category/save")
    public SkuStorageCategory addSkuStorageCategory(@Valid @RequestBody SkuStorageCategory theSkuStorageCategory) {
        String error = skuService.validateSkuStorageCategoryRequest(theSkuStorageCategory);
        if (error != null) {
            throw new InvalidValueFoundException("SkuStorageCategory", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSkuStorageCategory.setCreatedBy(user);
        theSkuStorageCategory.setModifiedBy(user);

        theSkuStorageCategory = skuService.saveSkuStorageCategory(theSkuStorageCategory);
        return theSkuStorageCategory;
    }

    @PutMapping("/storage-category/update/{id}")
    public SkuStorageCategory updateSkuStorageCategory(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody SkuStorageCategory theSkuStorageCategory) {
        theSkuStorageCategory.setId(theId);
        SkuStorageCategory detailObj = skuService.findBySkuStorageCategoryId(theId);

        String error = skuService.validateSkuStorageCategoryRequest(theSkuStorageCategory);
        if (error != null) {
            throw new InvalidValueFoundException("SkuStorageCategory", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSkuStorageCategory.setCreatedBy(detailObj.getCreatedBy());
        theSkuStorageCategory.setModifiedBy(user);

        theSkuStorageCategory.setCreatedAt(detailObj.getCreatedAt());
        detailObj = skuService.saveSkuStorageCategory(theSkuStorageCategory);
        return detailObj;
    }

    @PutMapping("/storage-category/update")
    public SkuStorageCategoryListAction updateSkuStorageCategoryList(
            @Valid @RequestBody SkuStorageCategoryListAction listAction) {

        SkuStorageCategoryListAction responseListAction = new SkuStorageCategoryListAction();
        for (SkuStorageCategory singleObj : listAction) {
            skuService.findBySkuStorageCategoryId(singleObj.getId());
            String error = skuService.validateSkuStorageCategoryRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("SkuStorageCategory", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (SkuStorageCategory singleObj : listAction) {
            SkuStorageCategory searchedObj = skuService.findBySkuStorageCategoryId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(skuService.saveSkuStorageCategory(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/storage-category/detail/{id}")
    public SkuStorageCategory getSkuStorageCategoryById(@PathVariable(value = "id") Long theId) {
        return skuService.findBySkuStorageCategoryId(theId);
    }

    @PostMapping("/storage-category/list/search")
    public ResponseEntity<Page<SkuStorageCategory>> getSkuStorageCategoryList(PageHelper page,
            @RequestBody SkuStorageCategoryRequest request) {
        return new ResponseEntity<>(skuService.findAllSkuStorageCategoryList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/storage-category/delete/{id}")
    public ResponseEntity<?> deleteSkuStorageCategory(@PathVariable(value = "id") Long theId) {
        skuService.deleteBySkuStorageCategoryId(theId);
        return ResponseEntity.ok().build();
    }

    // sku api
    @PostMapping("/save")
    public Sku addSku(@Valid @RequestBody Sku theSku) {
        String error = skuService.validateSkuRequest(theSku);
        if (error != null) {
            throw new InvalidValueFoundException("Sku", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSku.setCreatedBy(user);
        theSku.setModifiedBy(user);

        theSku = skuService.saveSku(theSku);
        return theSku;
    }

    @PutMapping("/update/{id}")
    public Sku updateSku(@PathVariable(value = "id") Long theId, @Valid @RequestBody Sku theSku) {
        theSku.setId(theId);
        Sku detailObj = skuService.findBySkuId(theId);

        String error = skuService.validateSkuRequest(theSku);
        if (error != null) {
            throw new InvalidValueFoundException("Sku", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSku.setCreatedBy(detailObj.getCreatedBy());
        theSku.setModifiedBy(user);

        theSku.setCreatedAt(detailObj.getCreatedAt());
        detailObj = skuService.saveSku(theSku);
        return detailObj;
    }

    @PutMapping("/update")
    public SkuListAction updateSkuList(@Valid @RequestBody SkuListAction listAction) {

        SkuListAction responseListAction = new SkuListAction();

        User user = WarehouseHelper.getLoggedInUser();

        for (Sku singleObj : listAction) {
            skuService.findBySkuId(singleObj.getId());
            String error = skuService.validateSkuRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("Sku", error);
            }
        }
        for (Sku singleObj : listAction) {
            Sku searchedObj = skuService.findBySkuId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());

            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            responseListAction.add(skuService.saveSku(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/detail/{id}")
    public Sku getSkuById(@PathVariable(value = "id") Long theId) {
        return skuService.findBySkuId(theId);
    }

    @PostMapping("/list/search")
    public ResponseEntity<Page<Sku>> getSkuList(PageHelper page, @RequestBody SkuRequest request) {
        return new ResponseEntity<>(skuService.findAllSkuList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSku(@PathVariable(value = "id") Long theId) {
        skuService.deleteBySkuId(theId);
        return ResponseEntity.ok().build();
    }

    // Sku Warehouse Mapping Api

    @PostMapping("/warehouses-company-mapping/search")
    public ResponseEntity<Page<WarehouseCompanyMapping>> getAllWarhouseWithCompanyMapping(PageHelper page,
            @RequestBody WarehouseCompanyMappingRequest request) {
        return new ResponseEntity<>(skuService.findAllWarehouseCompanyMapping(page, request), HttpStatus.OK);
    }

    @PostMapping("/warehouse-mapping/search")
    public ResponseEntity<List<SkuWarehouseMappingListResponse>> getAllWarhouseCompanyMappingWithSkuMappingList(
            @RequestBody WarehouseCompanyMappingRequest request) {
        return new ResponseEntity<>(skuService.findAllWarehouseWithWarehouseSkuMapping(request), HttpStatus.OK);
    }

    @PostMapping("/warehouse-mapping")
    public List<SkuWarehouseMapping> addUpdateSkuWarehouseMapping(
            @Valid @RequestBody List<SkuWarehouseMapping> skuWarehouseMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (SkuWarehouseMapping skuWarehouseMapping : skuWarehouseMappingList) {
            String error = skuService.validateSkuWarehouseMappingRequest(skuWarehouseMapping);
            if (error != null) {
                throw new InvalidValueFoundException("SkuWarehouseMapping", error);
            }

            String duplicateKeyValue = skuWarehouseMapping.getWarehouseCompanyMappingId() + ""
                    + skuWarehouseMapping.getSkuId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Mapping with Primary Company :"
                        + skuWarehouseMapping.getWarehouseCompanyMapping().getPrimaryCompanyId() + " and Warehouse:"
                        + skuWarehouseMapping.getWarehouseCompanyMapping().getWarehouseId() + " and Sku:"
                        + skuWarehouseMapping.getSku().getId();

                throw new InvalidValueFoundException("SkuWarehouseMapping", error);
            }

            arrayList.add(duplicateKeyValue);
        }

        for (SkuWarehouseMapping skuWarehouseMapping : skuWarehouseMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            skuWarehouseMapping.setCreatedBy(user);
            skuWarehouseMapping.setModifiedBy(user);

            if (skuWarehouseMapping.getId() != null) {
                SkuWarehouseMapping theSkuWarehouseMapping = skuService
                        .findBySkuWarehouseMappingId(skuWarehouseMapping.getId());
                skuWarehouseMapping.setCreatedAt(theSkuWarehouseMapping.getCreatedAt());
                skuWarehouseMapping.setCreatedBy(theSkuWarehouseMapping.getCreatedBy());
            }
        }
        return skuService.saveSkuWarehouseMapping(skuWarehouseMappingList);
    }

    @DeleteMapping("/warehouse-mapping")
    public ResponseEntity<?> deleteAllSkuWarehouseMapping(
            @RequestBody List<SkuWarehouseMapping> skuWarehouseMappingList) {
        skuService.deleteAllSkuWarehouseMapping(skuWarehouseMappingList);
        return ResponseEntity.ok().build();
    }

    // Sku Supplier Mapping Api

    @PostMapping("/supplier-mapping/search")
    public ResponseEntity<List<SkuSupplierMappingListResponse>> getAllSupplierWithSkuMappingList(
            @RequestBody SupplierRequest request) {
        return new ResponseEntity<>(skuService.findAllSupplierWithSkuSupplierMapping(request), HttpStatus.OK);
    }

    @PostMapping("/supplier-mapping")
    public List<SkuSupplierMapping> addUpdateSkuSupplierMapping(
            @Valid @RequestBody List<SkuSupplierMapping> skuSupplierMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (SkuSupplierMapping skuSupplierMapping : skuSupplierMappingList) {
            String error = skuService.validateSkuSupplierMappingRequest(skuSupplierMapping);
            if (error != null) {
                throw new InvalidValueFoundException("SkuSupplierMapping", error);
            }

            String duplicateKeyValue = skuSupplierMapping.getSupplierId() + ""
                    + skuSupplierMapping.getPrimaryCompanyId() + "" + skuSupplierMapping.getSkuId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Mapping with Primary Company Name:"
                        + skuSupplierMapping.getPrimaryCompany().getName() + " and Supplier:"
                        + skuSupplierMapping.getSupplier().getName() + " and Sku:"
                        + skuSupplierMapping.getSku().getName();
                throw new InvalidValueFoundException("SkuSupplierMapping", error);
            }

            arrayList.add(duplicateKeyValue);
        }

        for (SkuSupplierMapping skuSupplierMapping : skuSupplierMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            skuSupplierMapping.setCreatedBy(user);
            skuSupplierMapping.setModifiedBy(user);

            if (skuSupplierMapping.getId() != null) {
                SkuSupplierMapping theSkuSupplierMapping = skuService
                        .findBySkuSupplierMappingId(skuSupplierMapping.getId());
                skuSupplierMapping.setCreatedAt(theSkuSupplierMapping.getCreatedAt());
                skuSupplierMapping.setCreatedBy(theSkuSupplierMapping.getCreatedBy());
            }
        }
        return skuService.saveSkuSupplierMapping(skuSupplierMappingList);
    }

    @DeleteMapping("/supplier-mapping")
    public ResponseEntity<?> deleteAllSkuSupplierMapping(@RequestBody List<SkuSupplierMapping> skuSupplierMappingList) {
        skuService.deleteAllSkuSupplierMapping(skuSupplierMappingList);
        return ResponseEntity.ok().build();
    }

    // Sku Uom Mapping Api

    @PostMapping("/uom-mapping/search")
    public ResponseEntity<List<SkuUomMappingListResponse>> findAllSkuWithSkuUomMappingList(
            @RequestBody SkuRequest request) {
        return new ResponseEntity<>(skuService.findAllSkuWithSkuUomMapping(request), HttpStatus.OK);
    }

    @PostMapping("/uom-mapping")
    public List<SkuUomMapping> addUpdateSkuUomMapping(@Valid @RequestBody List<SkuUomMapping> skuUomMappingList) {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayListDefaultSalesOrderingUom = new ArrayList<String>();
        ArrayList<String> arrayListDefaultSalesBillingUom = new ArrayList<String>();
        ArrayList<String> arrayListDefaultPurchaseOrderingUom = new ArrayList<String>();
        ArrayList<String> arrayListDefaultPurchaseBillingUom = new ArrayList<String>();
        for (SkuUomMapping skuUomMapping : skuUomMappingList) {
            String error = skuService.validateSkuUomMappingRequest(skuUomMapping);
            if (error != null) {
                throw new InvalidValueFoundException("SkuUomMapping", error);
            }

            if (skuUomMapping.getIsDefaultPurchaseBillingUom()) {
                String duplicateKeyValue = skuUomMapping.getSkuId() + "DefaultPurchaseBillingUom";
                if (arrayListDefaultPurchaseBillingUom.contains(duplicateKeyValue)) {
                    error = "The SKU " + skuUomMapping.getSku().getName()
                            + " can have only one UOM as DefaultPurchaseBillingUom";
                    throw new InvalidValueFoundException("SkuUomMapping", error);
                }
                arrayListDefaultPurchaseBillingUom.add(duplicateKeyValue);
            }
            if (skuUomMapping.getIsDefaultSalesBillingUom()) {
                String duplicateKeyValue = skuUomMapping.getSkuId() + "DefaultSalesBillingUom";
                if (arrayListDefaultSalesBillingUom.contains(duplicateKeyValue)) {
                    error = "The SKU " + skuUomMapping.getSku().getName()
                            + " can have only one UOM as DefaultSalesBillingUom";
                    throw new InvalidValueFoundException("SkuUomMapping", error);
                }
                arrayListDefaultSalesBillingUom.add(duplicateKeyValue);
            }

            if (skuUomMapping.getIsDefaultPurchaseOrderingUom()) {
                String duplicateKeyValue = skuUomMapping.getSkuId() + "DefaultPurchaseOrderingUom";
                if (arrayListDefaultPurchaseOrderingUom.contains(duplicateKeyValue)) {
                    error = "The SKU " + skuUomMapping.getSku().getName()
                            + " can have only one UOM as DefaultPurchaseOrderingUom";
                    throw new InvalidValueFoundException("SkuUomMapping", error);
                }
                arrayListDefaultPurchaseOrderingUom.add(duplicateKeyValue);
            }
            if (skuUomMapping.getIsDefaultSalesOrderingUom()) {
                String duplicateKeyValue = skuUomMapping.getSkuId() + "DefaultSalesOrderingUom";
                if (arrayListDefaultSalesOrderingUom.contains(duplicateKeyValue)) {
                    error = "The SKU " + skuUomMapping.getSku().getName()
                            + " can have only one UOM as DefaultSalesOrderingUom";
                    throw new InvalidValueFoundException("SkuUomMapping", error);
                }
                arrayListDefaultSalesOrderingUom.add(duplicateKeyValue);
            }

            String duplicateKeyValue = skuUomMapping.getUomId() + "" + skuUomMapping.getSkuId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found with Uom:" + skuUomMapping.getUom().getName() + " and Sku:"
                        + skuUomMapping.getSku().getName();
                ;
                throw new InvalidValueFoundException("SkuUomMapping", error);
            }
            arrayList.add(duplicateKeyValue);
        }

        for (SkuUomMapping skuUomMapping : skuUomMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            skuUomMapping.setCreatedBy(user);
            skuUomMapping.setModifiedBy(user);

            if (skuUomMapping.getId() != null) {
                SkuUomMapping theSkuUomMapping = skuService.findBySkuUomMappingId(skuUomMapping.getId());
                skuUomMapping.setCreatedAt(theSkuUomMapping.getCreatedAt());
                skuUomMapping.setCreatedBy(theSkuUomMapping.getCreatedBy());
            }
        }
        return skuService.saveSkuUomMapping(skuUomMappingList);
    }

    @DeleteMapping("/uom-mapping")
    public ResponseEntity<?> deleteAllSkuUomMapping(@RequestBody List<SkuUomMapping> skuUomMappingList) {
        skuService.deleteAllSkuUomMapping(skuUomMappingList);
        return ResponseEntity.ok().build();
    }

    // Sku Cost Bucket mapping
    @PostMapping("/costbucket-mapping/search-all")
    public ResponseEntity<List<SkuCostBucketMapping>> findAllSkuCostBucketMapping(
            @RequestBody SkuCostBucketMappingRequest request) {
        return new ResponseEntity<>(skuService.findAllSkuCostBucketMapping(request), HttpStatus.OK);
    }

    @PostMapping("/costbucket-mapping/search")
    public ResponseEntity<Page<SkuCostBucketMapping>> findAllSkuCostBucketMapping(PageHelper page,
            @RequestBody SkuCostBucketMappingRequest request) {
        return new ResponseEntity<>(skuService.findAllSkuCostBucketMapping(page, request), HttpStatus.OK);
    }

    // Manage Kit Bom Api
    @PostMapping("/manage-kit-bom/search")
    public ResponseEntity<Page<Sku>> findSkuForManageKitBom(PageHelper page, @RequestBody SkuRequest request) {
        return new ResponseEntity<>(skuService.findSkuForManageKitBom(page, request), HttpStatus.OK);

    }

    @PostMapping("/manage-kit-bom/search-all")
    public ResponseEntity<List<SkuManageKitBomResponse>> findAllSkuForManageKitBom(@RequestBody SkuRequest request) {
        return new ResponseEntity<>(skuService.findAllSkuForManageKitBom(request), HttpStatus.OK);
    }

    @PostMapping("/manage-kit-bom/save")
    public ResponseEntity<?> saveSkuManageKitBom(
            @RequestBody List<ManageKitBomHeaderRequest> theManageKitBomHeaderRequestList) {
        skuService.saveSkuManageKitBom(theManageKitBomHeaderRequestList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/manage-kit-bom/delete")
    public ResponseEntity<?> deleteSkuManageKitBom(@RequestBody List<SkuManageKitBom> SkuManageKitBomList) {
        skuService.deleteSkuManageKitBom(SkuManageKitBomList);
        return ResponseEntity.ok().build();
    }

}
