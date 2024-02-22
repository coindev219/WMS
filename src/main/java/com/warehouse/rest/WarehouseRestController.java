package com.warehouse.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseListAction;
import com.warehouse.setup.warehouse.WarehouseRequest;
import com.warehouse.setup.warehouse.WarehouseService;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMapping;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMappingListResponse;
import com.warehouse.setup.warehouse.printers.ConfigurePrinter;
import com.warehouse.setup.warehouse.printers.ConfigurePrinterListResponse;
import com.warehouse.setup.warehouse.runningcost.RunningCost;
import com.warehouse.setup.warehouse.runningcost.RunningCostListResponse;

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
@RequestMapping("/api/setup/warehouse")
public class WarehouseRestController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/warehouses/search")
    public ResponseEntity<Page<Warehouse>> getAllWarehouses(PageHelper warehousePage,
            @RequestBody WarehouseRequest warehouseRequest) {
        return new ResponseEntity<>(warehouseService.findAll(warehousePage, warehouseRequest), HttpStatus.OK);
    }

    @GetMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable(value = "id") Long warehouseId) {
        return new ResponseEntity<>(warehouseService.findById(warehouseId), HttpStatus.OK);
    }

    @PostMapping("/warehouses")
    public ResponseEntity<Warehouse> addWarehouse(@Valid @RequestBody Warehouse theWarehouse) {

        warehouseService.validateWarehouseRequest(theWarehouse);

        User user = WarehouseHelper.getLoggedInUser();

        theWarehouse.setCreatedBy(user);
        theWarehouse.setModifiedBy(user);

        theWarehouse = warehouseService.save(theWarehouse);
        return new ResponseEntity<>(theWarehouse, HttpStatus.OK);
    }

    @PutMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable(value = "id") Long warehouseId,
            @Valid @RequestBody Warehouse theWarehouse) {

        Warehouse warehouseObj = warehouseService.findById(warehouseId);

        theWarehouse.setId(warehouseId);
        warehouseService.validateWarehouseRequest(theWarehouse);

        User user = WarehouseHelper.getLoggedInUser();

        theWarehouse.setModifiedBy(user);

        theWarehouse.setCreatedBy(warehouseObj.getCreatedBy());
        theWarehouse.setCreatedAt(warehouseObj.getCreatedAt());
        warehouseObj = warehouseService.save(theWarehouse);
        return new ResponseEntity<>(warehouseObj, HttpStatus.OK);
    }

    @PutMapping("/warehouses")
    public ResponseEntity<WarehouseListAction> updateWarehouseList(
            @Valid @RequestBody WarehouseListAction warehouseListAction) {

        User user = WarehouseHelper.getLoggedInUser();

        for (Warehouse theWarehouse : warehouseListAction) {
            warehouseService.validateWarehouseRequest(theWarehouse);
        }

        WarehouseListAction responseWarehouseListAction = new WarehouseListAction();
        for (Warehouse theWarehouse : warehouseListAction) {
            theWarehouse.setModifiedBy(user);
            Warehouse warehouseObj = warehouseService.findById(theWarehouse.getId());
            theWarehouse.setCreatedAt(warehouseObj.getCreatedAt());
            theWarehouse.setCreatedBy(warehouseObj.getCreatedBy());
            responseWarehouseListAction.add(warehouseService.save(theWarehouse));
        }
        return new ResponseEntity<>(responseWarehouseListAction, HttpStatus.OK);
    }

    @DeleteMapping("/warehouses/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable(value = "id") Long warehouseId) {
        warehouseService.findById(warehouseId);
        warehouseService.deleteById(warehouseId);
        return ResponseEntity.ok().build();
    }

    // Configure Printer Api

    @PostMapping("/warehouses/configure-printer/search")
    public ResponseEntity<List<ConfigurePrinterListResponse>> getAllWarehousesWithConfigurePrinterList(
            @RequestBody WarehouseRequest warehouseRequest) {
        return new ResponseEntity<>(warehouseService.findAllWarehouseWithConfigurePrinters(warehouseRequest),
                HttpStatus.OK);
    }

    @PostMapping("/configure-printer")
    public List<ConfigurePrinter> addUpdatePrinterList(
            @Valid @RequestBody List<ConfigurePrinter> configurePrinterList) {
        ArrayList<String> arrayList = new ArrayList<String>();

        for (ConfigurePrinter configurePrinter : configurePrinterList) {
            String error = warehouseService.validateConfigurePrinterRequest(configurePrinter);
            if (error != null) {
                throw new InvalidValueFoundException("ConfigurePrinter", error);
            }

            String duplicateKeyValue = configurePrinter.getWarehouseId() + "" + configurePrinter.getName();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Configure Printer with Printer Name:" + configurePrinter.getName()
                        + " and Warehouse:" + configurePrinter.getWarehouseId();
                throw new InvalidValueFoundException("ConfigurePrinter", error);
            }

            arrayList.add(duplicateKeyValue);

            User user = WarehouseHelper.getLoggedInUser();
            configurePrinter.setCreatedBy(user);
            configurePrinter.setModifiedBy(user);

            if (configurePrinter.getId() != null) {
                ConfigurePrinter theConfigurePrinter = warehouseService
                        .findByConfigurePrinterId(configurePrinter.getId());
                configurePrinter.setCreatedAt(theConfigurePrinter.getCreatedAt());
                configurePrinter.setCreatedBy(theConfigurePrinter.getCreatedBy());
            }
        }
        return warehouseService.savePrinter(configurePrinterList);
    }

    @DeleteMapping("/configure-printer")
    public ResponseEntity<?> deletePrinterList(@RequestBody List<ConfigurePrinter> configurePrinterList) {
        warehouseService.deleteAllPrinters(configurePrinterList);
        return ResponseEntity.ok().build();
    }

    // Integration Mapping Api

    @PostMapping("/warehouses/integration-mapping/search")
    public ResponseEntity<List<IntegrationMappingListResponse>> getAllWarehousesWithIntegrationMapping(
            @RequestBody WarehouseRequest warehouseRequest) {
        return new ResponseEntity<>(warehouseService.findAllWarehouseWithIntegrationMapping(warehouseRequest),
                HttpStatus.OK);
    }

    @PostMapping("/integration-mapping")
    public List<IntegrationMapping> addUpdateIntegrationMapping(
            @Valid @RequestBody List<IntegrationMapping> integrationMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (IntegrationMapping integrationMapping : integrationMappingList) {
            String error = warehouseService.validateIntregrationMappingRequest(integrationMapping);
            if (error != null) {
                throw new InvalidValueFoundException("IntegrationMapping", error);
            }

            String duplicateKeyValue = integrationMapping.getWarehouseId() + "" + integrationMapping.getCode();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Integration Mapping with code:" + integrationMapping.getCode()
                        + " and Warehouse:" + integrationMapping.getWarehouseId();
                throw new InvalidValueFoundException("IntegrationMapping", error);
            }

            arrayList.add(duplicateKeyValue);
        }

        for (IntegrationMapping integrationMapping : integrationMappingList) {
            User user = WarehouseHelper.getLoggedInUser();

            integrationMapping.setCreatedBy(user);
            integrationMapping.setModifiedBy(user);

            if (integrationMapping.getId() != null) {
                IntegrationMapping theIntegrationMapping = warehouseService
                        .findByIntregrationMappingId(integrationMapping.getId());
                integrationMapping.setCreatedAt(theIntegrationMapping.getCreatedAt());
                integrationMapping.setCreatedBy(theIntegrationMapping.getCreatedBy());
            }
        }

        return warehouseService.saveIntregrationMapping(integrationMappingList);
    }

    @DeleteMapping("/integration-mapping")
    public ResponseEntity<?> deleteIntegrationMappingList(
            @RequestBody List<IntegrationMapping> integrationMappingList) {
        warehouseService.deleteAllIntregrationMapping(integrationMappingList);
        return ResponseEntity.ok().build();
    }

    // Running Cost Api

    @PostMapping("/warehouses/running-cost/search")
    public ResponseEntity<List<RunningCostListResponse>> getAllWarehousesWithRunningCost(
            @RequestBody WarehouseRequest warehouseRequest) {
        return new ResponseEntity<>(warehouseService.findAllWarehouseWithRunningCost(warehouseRequest), HttpStatus.OK);
    }

    @PostMapping("/running-cost")
    public List<RunningCost> addUpdateRunningCost(@Valid @RequestBody List<RunningCost> integrationMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (RunningCost integrationMapping : integrationMappingList) {
            String error = warehouseService.validateRunningCostRequest(integrationMapping);
            if (error != null) {
                throw new InvalidValueFoundException("RunningCost", error);
            }

            String duplicateKeyValue = integrationMapping.getWarehouseId() + "" + integrationMapping.getCost();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Running Cost with cost:" + integrationMapping.getCost()
                        + " and Warehouse:" + integrationMapping.getWarehouseId();
                throw new InvalidValueFoundException("RunningCost", error);
            }

            arrayList.add(duplicateKeyValue);
        }

        for (RunningCost integrationMapping : integrationMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            integrationMapping.setCreatedBy(user);
            integrationMapping.setModifiedBy(user);

            if (integrationMapping.getId() != null) {
                RunningCost theRunningCost = warehouseService.findByRunningCostId(integrationMapping.getId());
                integrationMapping.setCreatedAt(theRunningCost.getCreatedAt());
                integrationMapping.setCreatedBy(theRunningCost.getCreatedBy());
            }
        }
        return warehouseService.saveRunningCost(integrationMappingList);
    }

    @DeleteMapping("/running-cost")
    public ResponseEntity<?> deleteRunningCostList(@RequestBody List<RunningCost> integrationMappingList) {
        warehouseService.deleteAllRunningCost(integrationMappingList);
        return ResponseEntity.ok().build();
    }

}
