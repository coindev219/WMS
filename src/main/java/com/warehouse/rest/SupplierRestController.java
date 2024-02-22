package com.warehouse.rest;

import javax.validation.Valid;

import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.supplier.SupplierListAction;
import com.warehouse.setup.supplier.SupplierRequest;
import com.warehouse.setup.supplier.SupplierService;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTime;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTimeListAction;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTimeRequest;

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
@RequestMapping("/api/setup/supplier")
public class SupplierRestController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/save")
    public Supplier addSupplier(@Valid @RequestBody Supplier theSupplier) {
        String error = supplierService.validateSupplierRequest(theSupplier);
        if (error != null) {
            throw new InvalidValueFoundException("Supplier", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSupplier.setCreatedBy(user);
        theSupplier.setModifiedBy(user);

        theSupplier = supplierService.saveSupplier(theSupplier);
        return theSupplier;
    }

    @PutMapping("/update/{id}")
    public Supplier updateSupplier(@PathVariable(value = "id") Long theId, @Valid @RequestBody Supplier theSupplier) {
        theSupplier.setId(theId);
        Supplier detailObj = supplierService.findBySupplierId(theId);

        String error = supplierService.validateSupplierRequest(theSupplier);
        if (error != null) {
            throw new InvalidValueFoundException("Supplier", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSupplier.setCreatedBy(detailObj.getCreatedBy());
        theSupplier.setModifiedBy(user);

        theSupplier.setCreatedAt(detailObj.getCreatedAt());
        detailObj = supplierService.saveSupplier(theSupplier);
        return detailObj;
    }

    @PutMapping("/update")
    public SupplierListAction updateSupplierList(@Valid @RequestBody SupplierListAction listAction) {

        SupplierListAction responseListAction = new SupplierListAction();
        for (Supplier singleObj : listAction) {
            supplierService.findBySupplierId(singleObj.getId());
            String error = supplierService.validateSupplierRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("Supplier", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (Supplier singleObj : listAction) {
            Supplier searchedObj = supplierService.findBySupplierId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(supplierService.saveSupplier(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/detail/{id}")
    public Supplier getSupplierById(@PathVariable(value = "id") Long theId) {
        return supplierService.findBySupplierId(theId);
    }

    @PostMapping("/list/search")
    public ResponseEntity<Page<Supplier>> getSupplierList(PageHelper page, @RequestBody SupplierRequest request) {
        return new ResponseEntity<>(supplierService.findAllSupplierList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable(value = "id") Long theId) {
        supplierService.deleteBySupplierId(theId);
        return ResponseEntity.ok().build();
    }

    // Supplier Lead Times

    @PostMapping("/lead-time/save")
    public SupplierLeadTime addSupplierLeadTime(@Valid @RequestBody SupplierLeadTime theSupplierLeadTime) {
        String error = supplierService.validateSupplierLeadTimeRequest(theSupplierLeadTime);
        if (error != null) {
            throw new InvalidValueFoundException("SupplierLeadTime", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSupplierLeadTime.setCreatedBy(user);
        theSupplierLeadTime.setModifiedBy(user);

        theSupplierLeadTime = supplierService.saveSupplierLeadTime(theSupplierLeadTime);
        return theSupplierLeadTime;
    }

    @PutMapping("/lead-time/update/{id}")
    public SupplierLeadTime updateSupplierLeadTime(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody SupplierLeadTime theSupplierLeadTime) {
        theSupplierLeadTime.setId(theId);
        SupplierLeadTime detailObj = supplierService.findBySupplierLeadTimeId(theId);

        String error = supplierService.validateSupplierLeadTimeRequest(theSupplierLeadTime);
        if (error != null) {
            throw new InvalidValueFoundException("SupplierLeadTime", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theSupplierLeadTime.setCreatedBy(detailObj.getCreatedBy());
        theSupplierLeadTime.setModifiedBy(user);

        theSupplierLeadTime.setCreatedAt(detailObj.getCreatedAt());
        detailObj = supplierService.saveSupplierLeadTime(theSupplierLeadTime);
        return detailObj;
    }

    @PutMapping("/lead-time/update")
    public SupplierLeadTimeListAction updateSupplierLeadTimeList(
            @Valid @RequestBody SupplierLeadTimeListAction listAction) {

        SupplierLeadTimeListAction responseListAction = new SupplierLeadTimeListAction();
        for (SupplierLeadTime singleObj : listAction) {
            supplierService.findBySupplierLeadTimeId(singleObj.getId());
            String error = supplierService.validateSupplierLeadTimeRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("SupplierLeadTime", error);
            }
        }

        User user = WarehouseHelper.getLoggedInUser();

        for (SupplierLeadTime singleObj : listAction) {
            SupplierLeadTime searchedObj = supplierService.findBySupplierLeadTimeId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(supplierService.saveSupplierLeadTime(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/lead-time/detail/{id}")
    public SupplierLeadTime getSupplierLeadTimeById(@PathVariable(value = "id") Long theId) {
        return supplierService.findBySupplierLeadTimeId(theId);
    }

    @PostMapping("/lead-time/list/search")
    public ResponseEntity<Page<SupplierLeadTime>> getSupplierLeadTimeList(PageHelper page,
            @RequestBody SupplierLeadTimeRequest request) {
        return new ResponseEntity<>(supplierService.findAllSupplierLeadTimeList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/lead-time/delete/{id}")
    public ResponseEntity<?> deleteSupplierLeadTime(@PathVariable(value = "id") Long theId) {
        supplierService.deleteBySupplierLeadTimeId(theId);
        return ResponseEntity.ok().build();
    }

}
