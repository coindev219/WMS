package com.warehouse.rest;

import javax.validation.Valid;

import com.warehouse.additionalsetup.AdditionalSetupService;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketListAction;
import com.warehouse.additionalsetup.costbucket.CostBucketRequest;
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.freighter.FreighterListAction;
import com.warehouse.additionalsetup.freighter.FreighterRequest;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.ordertype.OrderTypeListAction;
import com.warehouse.additionalsetup.ordertype.OrderTypeRequest;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.skutype.SkuTypeListAction;
import com.warehouse.additionalsetup.skutype.SkuTypeRequest;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.uom.UomListAction;
import com.warehouse.additionalsetup.uom.UomRequest;
import com.warehouse.additionalsetup.vasactivity.VasActivity;
import com.warehouse.additionalsetup.vasactivity.VasActivityListAction;
import com.warehouse.additionalsetup.vasactivity.VasActivityRequest;
import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;

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
@RequestMapping("/api/additional-setup/")
public class AdditionalSeupRestController {

    @Autowired
    private AdditionalSetupService additionalSetupService;

    @PostMapping("/sku-type/save")
    public SkuType addSkuType(@Valid @RequestBody SkuType theSkuType) {
        String error = additionalSetupService.validateSkuTypeRequest(theSkuType);
        if (error != null) {
            throw new InvalidValueFoundException("SkuType", error);
        }
        User user = WarehouseHelper.getLoggedInUser();
        theSkuType.setCreatedBy(user);
        theSkuType.setModifiedBy(user);

        theSkuType = additionalSetupService.saveSkuType(theSkuType);
        return theSkuType;
    }

    @PutMapping("/sku-type/update/{id}")
    public SkuType updateSkuType(@PathVariable(value = "id") Long theId, @Valid @RequestBody SkuType theSkuType) {
        theSkuType.setId(theId);
        SkuType detailObj = additionalSetupService.findBySkuTypeId(theId);

        String error = additionalSetupService.validateSkuTypeRequest(theSkuType);
        if (error != null) {
            throw new InvalidValueFoundException("SkuType", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSkuType.setCreatedBy(detailObj.getCreatedBy());
        theSkuType.setModifiedBy(user);

        theSkuType.setCreatedAt(detailObj.getCreatedAt());
        detailObj = additionalSetupService.saveSkuType(theSkuType);
        return detailObj;
    }

    @PutMapping("/sku-type/update")
    public SkuTypeListAction updateSkuTypeList(@Valid @RequestBody SkuTypeListAction listAction) {

        SkuTypeListAction responseListAction = new SkuTypeListAction();
        for (SkuType singleObj : listAction) {
            additionalSetupService.findBySkuTypeId(singleObj.getId());
            String error = additionalSetupService.validateSkuTypeRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("SkuType", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (SkuType singleObj : listAction) {
            SkuType searchedObj = additionalSetupService.findBySkuTypeId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(additionalSetupService.saveSkuType(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/sku-type/detail/{id}")
    public SkuType getSkuTypeById(@PathVariable(value = "id") Long theId) {
        return additionalSetupService.findBySkuTypeId(theId);
    }

    @PostMapping("/sku-type/list/search")
    public ResponseEntity<Page<SkuType>> getSkuTypeList(PageHelper page, @RequestBody SkuTypeRequest request) {
        return new ResponseEntity<>(additionalSetupService.findAllSkuTypeList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/sku-type/delete/{id}")
    public ResponseEntity<?> deleteSkuType(@PathVariable(value = "id") Long theId) {
        additionalSetupService.deleteBySkuTypeId(theId);
        return ResponseEntity.ok().build();
    }

    // Uom

    @PostMapping("/uom/save")
    public Uom addUom(@Valid @RequestBody Uom theUom) {

        User user = WarehouseHelper.getLoggedInUser();

        theUom.setCreatedBy(user);
        theUom.setCreatedById(Long.valueOf(user.getId()));
        theUom.setModifiedBy(user);

        String error = additionalSetupService.validateUomRequest(theUom);
        if (error != null) {
            throw new InvalidValueFoundException("Uom", error);
        }

        theUom = additionalSetupService.saveUom(theUom);
        return theUom;
    }

    @PutMapping("/uom/update/{id}")
    public Uom updateUom(@PathVariable(value = "id") Long theId, @Valid @RequestBody Uom theUom) {
        theUom.setId(theId);
        Uom detailObj = additionalSetupService.findByUomId(theId);

        User user = WarehouseHelper.getLoggedInUser();
        theUom.setCreatedBy(detailObj.getCreatedBy());
        theUom.setCreatedById(Long.valueOf(user.getId()));
        theUom.setModifiedBy(user);

        String error = additionalSetupService.validateUomRequest(theUom);
        if (error != null) {
            throw new InvalidValueFoundException("Uom", error);
        }

        theUom.setCreatedAt(detailObj.getCreatedAt());
        detailObj = additionalSetupService.saveUom(theUom);
        return detailObj;
    }

    @PutMapping("/uom/update")
    public UomListAction updateUomList(@Valid @RequestBody UomListAction listAction) {

        User user = WarehouseHelper.getLoggedInUser();

        UomListAction responseListAction = new UomListAction();
        for (Uom singleObj : listAction) {
            Uom searchedObj = additionalSetupService.findByUomId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            String error = additionalSetupService.validateUomRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("Uom", error);
            }
        }

        for (Uom singleObj : listAction) {
            responseListAction.add(additionalSetupService.saveUom(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/uom/detail/{id}")
    public Uom getUomById(@PathVariable(value = "id") Long theId) {
        return additionalSetupService.findByUomId(theId);
    }

    @PostMapping("/uom/list/search")
    public ResponseEntity<Page<Uom>> getUomList(PageHelper page, @RequestBody UomRequest request) {
        return new ResponseEntity<>(additionalSetupService.findAllUomList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/uom/delete/{id}")
    public ResponseEntity<?> deleteUom(@PathVariable(value = "id") Long theId) {
        additionalSetupService.deleteByUomId(theId);
        return ResponseEntity.ok().build();
    }

    // Cost Bucket Api

    @PostMapping("/cost-bucket/save")
    public CostBucket addCostBucket(@Valid @RequestBody CostBucket theCostBucket) {
        String error = additionalSetupService.validateCostBucketRequest(theCostBucket);
        if (error != null) {
            throw new InvalidValueFoundException("CostBucket", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCostBucket.setCreatedBy(user);
        theCostBucket.setModifiedBy(user);

        theCostBucket = additionalSetupService.saveCostBucket(theCostBucket);
        return theCostBucket;
    }

    @PutMapping("/cost-bucket/update/{id}")
    public CostBucket updateCostBucket(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody CostBucket theCostBucket) {
        theCostBucket.setId(theId);
        CostBucket detailObj = additionalSetupService.findByCostBucketId(theId);

        String error = additionalSetupService.validateCostBucketRequest(theCostBucket);
        if (error != null) {
            throw new InvalidValueFoundException("CostBucket", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCostBucket.setCreatedBy(detailObj.getCreatedBy());
        theCostBucket.setModifiedBy(user);

        theCostBucket.setCreatedAt(detailObj.getCreatedAt());
        detailObj = additionalSetupService.saveCostBucket(theCostBucket);
        return detailObj;
    }

    @PutMapping("/cost-bucket/update")
    public CostBucketListAction updateCostBucketList(@Valid @RequestBody CostBucketListAction listAction) {

        CostBucketListAction responseListAction = new CostBucketListAction();
        for (CostBucket singleObj : listAction) {
            additionalSetupService.findByCostBucketId(singleObj.getId());
            String error = additionalSetupService.validateCostBucketRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("CostBucket", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (CostBucket singleObj : listAction) {
            CostBucket searchedObj = additionalSetupService.findByCostBucketId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(additionalSetupService.saveCostBucket(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/cost-bucket/detail/{id}")
    public CostBucket getCostBucketById(@PathVariable(value = "id") Long theId) {
        return additionalSetupService.findByCostBucketId(theId);
    }

    @PostMapping("/cost-bucket/list/search")
    public ResponseEntity<Page<CostBucket>> getCostBucketList(PageHelper page, @RequestBody CostBucketRequest request) {
        return new ResponseEntity<>(additionalSetupService.findAllCostBucketList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/cost-bucket/delete/{id}")
    public ResponseEntity<?> deleteCostBucket(@PathVariable(value = "id") Long theId) {
        additionalSetupService.deleteByCostBucketId(theId);
        return ResponseEntity.ok().build();
    }

    // Order Type

    @PostMapping("/order-type/save")
    public OrderType addOrderType(@Valid @RequestBody OrderType theOrderType) {
        String error = additionalSetupService.validateOrderTypeRequest(theOrderType);
        if (error != null) {
            throw new InvalidValueFoundException("OrderType", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theOrderType.setCreatedBy(user);
        theOrderType.setModifiedBy(user);

        theOrderType = additionalSetupService.saveOrderType(theOrderType);
        return theOrderType;
    }

    @PutMapping("/order-type/update/{id}")
    public OrderType updateOrderType(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody OrderType theOrderType) {
        theOrderType.setId(theId);
        OrderType detailObj = additionalSetupService.findByOrderTypeId(theId);

        String error = additionalSetupService.validateOrderTypeRequest(theOrderType);
        if (error != null) {
            throw new InvalidValueFoundException("OrderType", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theOrderType.setCreatedBy(detailObj.getCreatedBy());
        theOrderType.setModifiedBy(user);

        theOrderType.setCreatedAt(detailObj.getCreatedAt());
        detailObj = additionalSetupService.saveOrderType(theOrderType);
        return detailObj;
    }

    @PutMapping("/order-type/update")
    public OrderTypeListAction updateOrderTypeList(@Valid @RequestBody OrderTypeListAction listAction) {

        OrderTypeListAction responseListAction = new OrderTypeListAction();
        for (OrderType singleObj : listAction) {
            additionalSetupService.findByOrderTypeId(singleObj.getId());
            String error = additionalSetupService.validateOrderTypeRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("OrderType", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (OrderType singleObj : listAction) {
            OrderType searchedObj = additionalSetupService.findByOrderTypeId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(additionalSetupService.saveOrderType(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/order-type/detail/{id}")
    public OrderType getOrderTypeById(@PathVariable(value = "id") Long theId) {
        return additionalSetupService.findByOrderTypeId(theId);
    }

    @PostMapping("/order-type/list/search")
    public ResponseEntity<Page<OrderType>> getOrderTypeList(PageHelper page, @RequestBody OrderTypeRequest request) {
        return new ResponseEntity<>(additionalSetupService.findAllOrderTypeList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/order-type/delete/{id}")
    public ResponseEntity<?> deleteOrderType(@PathVariable(value = "id") Long theId) {
        additionalSetupService.deleteByOrderTypeId(theId);
        return ResponseEntity.ok().build();
    }

    // Freighter Api
    @PostMapping("/freighter/save")
    public ResponseEntity<Freighter> addFreighter(@RequestBody Freighter theFreighter) {
        String error = additionalSetupService.validateFreighterRequest(theFreighter);
        if (error != null) {
            throw new InvalidValueFoundException("Freighter", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theFreighter.setCreatedBy(user);
        theFreighter.setModifiedBy(user);

        theFreighter = additionalSetupService.saveFreighter(theFreighter);
        return new ResponseEntity<>(theFreighter, HttpStatus.OK);
    }

    @PutMapping("/freighter/update/{id}")
    public ResponseEntity<Freighter> updateFreighter(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody Freighter theFreighter) {
        theFreighter.setId(theId);
        Freighter detailObj = additionalSetupService.findByFreighterId(theId);

        String error = additionalSetupService.validateFreighterRequest(theFreighter);
        if (error != null) {
            throw new InvalidValueFoundException("Freighter", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theFreighter.setCreatedBy(detailObj.getCreatedBy());
        theFreighter.setModifiedBy(user);

        theFreighter.setCreatedAt(detailObj.getCreatedAt());
        detailObj = additionalSetupService.saveFreighter(theFreighter);
        return new ResponseEntity<>(detailObj, HttpStatus.OK);
    }

    @PutMapping("/freighter/update")
    public ResponseEntity<FreighterListAction> updateFreighterList(@Valid @RequestBody FreighterListAction listAction) {

        FreighterListAction responseListAction = new FreighterListAction();
        for (Freighter singleObj : listAction) {
            additionalSetupService.findByFreighterId(singleObj.getId());
            String error = additionalSetupService.validateFreighterRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("Freighter", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (Freighter singleObj : listAction) {
            Freighter searchedObj = additionalSetupService.findByFreighterId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(additionalSetupService.saveFreighter(singleObj));
        }

        return new ResponseEntity<>(responseListAction, HttpStatus.OK);
    }

    @GetMapping("/freighter/detail/{id}")
    public ResponseEntity<Freighter> getFreighterById(@PathVariable(value = "id") Long theId) {
        return new ResponseEntity<>(additionalSetupService.findByFreighterId(theId), HttpStatus.OK);
    }

    @PostMapping("/freighter/list/search")
    public ResponseEntity<Page<Freighter>> getFreighterList(PageHelper page, @RequestBody FreighterRequest request) {
        return new ResponseEntity<>(additionalSetupService.findAllFreighterList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/freighter/delete/{id}")
    public ResponseEntity<?> deleteFreighter(@PathVariable(value = "id") Long theId) {
        additionalSetupService.deleteByFreighterId(theId);
        return ResponseEntity.ok().build();
    }

    // Vas Activity Api
    @PostMapping("/vas-activity/save")
    public ResponseEntity<VasActivity> addVasActivity(@RequestBody VasActivity theVasActivity) {

        User user = WarehouseHelper.getLoggedInUser();

        String error = additionalSetupService.validateVasActivityRequest(theVasActivity);
        if (error != null) {
            throw new InvalidValueFoundException("VasActivity", error);
        }
        theVasActivity.setCreatedBy(user);
        theVasActivity.setModifiedBy(user);

        theVasActivity = additionalSetupService.saveVasActivity(theVasActivity);
        return new ResponseEntity<>(theVasActivity, HttpStatus.OK);
    }

    @PutMapping("/vas-activity/update/{id}")
    public ResponseEntity<VasActivity> updateVasActivity(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody VasActivity theVasActivity) {
        theVasActivity.setId(theId);

        User user = WarehouseHelper.getLoggedInUser();

        VasActivity detailObj = additionalSetupService.findByVasActivityId(theId);
        String error = additionalSetupService.validateVasActivityRequest(theVasActivity);
        if (error != null) {
            throw new InvalidValueFoundException("VasActivity", error);
        }

        theVasActivity.setCreatedBy(detailObj.getCreatedBy());
        theVasActivity.setModifiedBy(user);

        theVasActivity.setCreatedAt(detailObj.getCreatedAt());
        detailObj = additionalSetupService.saveVasActivity(theVasActivity);
        return new ResponseEntity<>(detailObj, HttpStatus.OK);
    }

    @PutMapping("/vas-activity/update")
    public ResponseEntity<VasActivityListAction> updateVasActivityList(
            @Valid @RequestBody VasActivityListAction listAction) {

        User user = WarehouseHelper.getLoggedInUser();

        VasActivityListAction responseListAction = new VasActivityListAction();
        for (VasActivity singleObj : listAction) {
            additionalSetupService.findByVasActivityId(singleObj.getId());
            String error = additionalSetupService.validateVasActivityRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("VasActivity", error);
            }
        }

        for (VasActivity singleObj : listAction) {
            VasActivity searchedObj = additionalSetupService.findByVasActivityId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(additionalSetupService.saveVasActivity(singleObj));
        }

        return new ResponseEntity<>(responseListAction, HttpStatus.OK);
    }

    @GetMapping("/vas-activity/detail/{id}")
    public ResponseEntity<VasActivity> getVasActivityById(@PathVariable(value = "id") Long theId) {
        return new ResponseEntity<>(additionalSetupService.findByVasActivityId(theId), HttpStatus.OK);
    }

    @PostMapping("/vas-activity/list/search")
    public ResponseEntity<Page<VasActivity>> getVasActivityList(PageHelper page,
            @RequestBody VasActivityRequest request) {
        return new ResponseEntity<>(additionalSetupService.findAllVasActivityList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/vas-activity/delete/{id}")
    public ResponseEntity<?> deleteVasActivity(@PathVariable(value = "id") Long theId) {
        additionalSetupService.deleteByVasActivityId(theId);
        return ResponseEntity.ok().build();
    }

}
