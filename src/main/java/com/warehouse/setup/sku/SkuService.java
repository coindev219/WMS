package com.warehouse.setup.sku;

import java.util.List;

import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRequest;
import com.warehouse.setup.sku.category.SkuCategory;
import com.warehouse.setup.sku.category.SkuCategoryRequest;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMapping;
import com.warehouse.setup.sku.costbucketmapping.SkuCostBucketMappingRequest;
import com.warehouse.setup.sku.managekitbom.ManageKitBomHeaderRequest;
import com.warehouse.setup.sku.managekitbom.SkuManageKitBom;
import com.warehouse.setup.sku.managekitbom.SkuManageKitBomResponse;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategory;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategoryRequest;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMapping;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMappingListResponse;
import com.warehouse.setup.sku.uommapping.SkuUomMapping;
import com.warehouse.setup.sku.uommapping.SkuUomMappingListResponse;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMapping;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMappingListResponse;
import com.warehouse.setup.supplier.SupplierRequest;

import org.springframework.data.domain.Page;

public interface SkuService {

    // Category
    public String validateSkuCategoryRequest(SkuCategory theSkuCategory);

    public SkuCategory saveSkuCategory(SkuCategory theSkuCategory);

    public SkuCategory findBySkuCategoryId(Long theId);

    public Page<SkuCategory> findAllSkuCategoryList(PageHelper page, SkuCategoryRequest request);

    public void deleteBySkuCategoryId(Long theId);

    
    // Storage Category
    public String validateSkuStorageCategoryRequest(SkuStorageCategory theSkuStorageCategory);

    public SkuStorageCategory saveSkuStorageCategory(SkuStorageCategory theSkuStorageCategory);

    public SkuStorageCategory findBySkuStorageCategoryId(Long theId);

    public Page<SkuStorageCategory> findAllSkuStorageCategoryList(PageHelper page, SkuStorageCategoryRequest request);

    public void deleteBySkuStorageCategoryId(Long theId);


    // Sku
    public String validateSkuRequest(Sku theSku);

    public Sku saveSku(Sku theSku);

    public Sku findBySkuId(Long theId);

    public Page<Sku> findAllSkuList(PageHelper page, SkuRequest request);

    public void deleteBySkuId(Long theId);

    // Sku Warehouse Mapping
    public Page<WarehouseCompanyMapping> findAllWarehouseCompanyMapping(PageHelper page, WarehouseCompanyMappingRequest request);

    public String validateSkuWarehouseMappingRequest(SkuWarehouseMapping theSkuWarehouseMapping);
    
    public List<SkuWarehouseMappingListResponse> findAllWarehouseWithWarehouseSkuMapping(WarehouseCompanyMappingRequest request);
   
    public List<SkuWarehouseMapping> saveSkuWarehouseMapping(List<SkuWarehouseMapping> theSkuWarehouseMapping);

	public SkuWarehouseMapping findBySkuWarehouseMappingId(Long theId);

	public void deleteAllSkuWarehouseMapping(List<SkuWarehouseMapping> theSkuWarehouseMapping);
    

    // Sku Supplier Mapping

    public String validateSkuSupplierMappingRequest(SkuSupplierMapping theSkuSupplierMapping);
    
    public List<SkuSupplierMappingListResponse> findAllSupplierWithSkuSupplierMapping(SupplierRequest request);
   
    public List<SkuSupplierMapping> saveSkuSupplierMapping(List<SkuSupplierMapping> theSkuSupplierMapping);

	public SkuSupplierMapping findBySkuSupplierMappingId(Long theId);

	public void deleteAllSkuSupplierMapping(List<SkuSupplierMapping> theSkuSupplierMapping);


    // Sku Uom Mapping
    public String validateSkuUomMappingRequest(SkuUomMapping theSkuUomMapping);

    public List<SkuUomMappingListResponse> findAllSkuWithSkuUomMapping(SkuRequest request);
    
    public List<SkuUomMapping> saveSkuUomMapping(List<SkuUomMapping> theSkuUomMapping);

    public SkuUomMapping findBySkuUomMappingId(Long theId);

    public void deleteAllSkuUomMapping(List<SkuUomMapping> theSkuUomMapping);

    // Sku Cost Bucket Mapping
    public List<SkuCostBucketMapping> saveSkuCostBucketMapping(List<SkuCostBucketMapping> theSkuCostBucketMapping);
    public List<SkuCostBucketMapping> findAllSkuCostBucketMapping(SkuCostBucketMappingRequest request);
    public Page<SkuCostBucketMapping> findAllSkuCostBucketMapping(PageHelper page, SkuCostBucketMappingRequest request);


    // Manage Kit Bom 
    public Page<Sku> findSkuForManageKitBom(PageHelper page, SkuRequest request);
    public List<SkuManageKitBomResponse> findAllSkuForManageKitBom(SkuRequest request);
    public void saveSkuManageKitBom(List<ManageKitBomHeaderRequest> theManageKitBomHeaderRequestList);
    public void deleteSkuManageKitBom(List<SkuManageKitBom> theSkuManageKitBomList);
    


}
