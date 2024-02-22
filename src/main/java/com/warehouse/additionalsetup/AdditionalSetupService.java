package com.warehouse.additionalsetup;

import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketRequest;
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.freighter.FreighterRequest;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.ordertype.OrderTypeRequest;
import com.warehouse.additionalsetup.skutype.SkuTypeRequest;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.uom.UomRequest;
import com.warehouse.additionalsetup.vasactivity.VasActivity;
import com.warehouse.additionalsetup.vasactivity.VasActivityRequest;
import com.warehouse.helper.PageHelper;

import org.springframework.data.domain.Page;

public interface AdditionalSetupService {

    // Sku Type
    public String validateSkuTypeRequest(SkuType theSkuType);

    public SkuType saveSkuType(SkuType theSkuType);

    public SkuType findBySkuTypeId(Long theId);

    public Page<SkuType> findAllSkuTypeList(PageHelper page, SkuTypeRequest request);

    public void deleteBySkuTypeId(Long theId);

    // Uom

    public String validateUomRequest(Uom theUom);

    public Uom saveUom(Uom theUom);

    public Uom findByUomId(Long theId);

    public Page<Uom> findAllUomList(PageHelper page, UomRequest request);

    public void deleteByUomId(Long theId);


    // Cost Bucket
    public String validateCostBucketRequest(CostBucket theCostBucket);

    public CostBucket saveCostBucket(CostBucket theCostBucket);

    public CostBucket findByCostBucketId(Long theId);

    public Page<CostBucket> findAllCostBucketList(PageHelper page, CostBucketRequest request);

    public void deleteByCostBucketId(Long theId);
    

    // Order Type
    public String validateOrderTypeRequest(OrderType theOrderType);

    public OrderType saveOrderType(OrderType theOrderType);

    public OrderType findByOrderTypeId(Long theId);

    public Page<OrderType> findAllOrderTypeList(PageHelper page, OrderTypeRequest request);

    public void deleteByOrderTypeId(Long theId);


    // Freighter
    public String validateFreighterRequest(Freighter theFreighter);

    public Freighter saveFreighter(Freighter theFreighter);

    public Freighter findByFreighterId(Long theId);

    public Page<Freighter> findAllFreighterList(PageHelper page, FreighterRequest request);

    public void deleteByFreighterId(Long theId);


    // Vas Activity
    public String validateVasActivityRequest(VasActivity theVasActivity);

    public VasActivity saveVasActivity(VasActivity theVasActivity);

    public VasActivity findByVasActivityId(Long theId);

    public Page<VasActivity> findAllVasActivityList(PageHelper page, VasActivityRequest request);

    public void deleteByVasActivityId(Long theId);
  
}
