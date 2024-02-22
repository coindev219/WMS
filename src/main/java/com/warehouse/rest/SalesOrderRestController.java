package com.warehouse.rest;

import java.util.List;

import javax.validation.Valid;

import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.sales.salesorder.SalesOrder;
import com.warehouse.sales.salesorder.SalesOrderRequest;
import com.warehouse.sales.salesorder.SalesOrderService;
import com.warehouse.sales.salesorder.SalesOrderSku;
import com.warehouse.sales.salesorder.SalesOrderSkuRequest;
import com.warehouse.sales.salesorder.vehicleinward.SalesOrderCustomResponse;
import com.warehouse.sales.salesorder.vehicleinward.VehicleInward;
import com.warehouse.sales.salesorder.vehicleinward.VehicleInwardLRNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales-order")
public class SalesOrderRestController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping("/create")
    public List<SalesOrder> addUpdateSalesOrder(@Valid @RequestBody List<SalesOrder> theSalesOrderList) {

        // ArrayList<String> arrayList = new ArrayList<String>();

        for (SalesOrder salesOrder : theSalesOrderList) {
            String error = salesOrderService.validateSalesOrderRequest(salesOrder);
            if (error != null) {
                throw new InvalidValueFoundException("SalesOrder", error);
            }
        }

        for (SalesOrder salesOrder : theSalesOrderList) {

            User user = WarehouseHelper.getLoggedInUser();
            salesOrder.setCreatedBy(user);
            salesOrder.setModifiedBy(user);

            List<SalesOrderSku> salesOrderSkuList = salesOrder.getSalesOrderSku();

            for (SalesOrderSku salesOrderSku : salesOrderSkuList) {
                salesOrderSku.setCreatedBy(user);
                salesOrderSku.setModifiedBy(user);
            }
        }
        return salesOrderService.saveSalesOrders(theSalesOrderList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSalesOrderSku(@RequestBody List<SalesOrderSku> theSalesOrderSkuList) {
        salesOrderService.deleteAllSalesOrderSku(theSalesOrderSkuList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<SalesOrder>> getSalesOrderList(PageHelper page, @RequestBody SalesOrderRequest request) {
        return new ResponseEntity<>(salesOrderService.findSalesOrderWithFilters(page, request), HttpStatus.OK);
    }

    @PostMapping("/search-all")
    public ResponseEntity<List<SalesOrder>> getSalesOrderList(@RequestBody SalesOrderRequest request) {
        return new ResponseEntity<>(salesOrderService.findSalesOrderWithFilters(request), HttpStatus.OK);
    }

    // Vehicle Inward
    @PostMapping("/vehicle-inward/search")
    public ResponseEntity<Page<SalesOrderCustomResponse>> getSalesOrderListWithFcNo(PageHelper page,
            @RequestBody SalesOrderRequest request) {
        return new ResponseEntity<>(salesOrderService.findSalesOrderWithFcNo(page, request), HttpStatus.OK);
    }

    @PostMapping("/vehicle-inward/search-all")
    public ResponseEntity<List<SalesOrderCustomResponse>> getSalesOrderListWithFcNo(
            @RequestBody SalesOrderRequest request) {
        return new ResponseEntity<>(salesOrderService.findSalesOrderWithFcNoAll(request), HttpStatus.OK);
    }

    @PostMapping("/vehicle-inward/create")
    public List<VehicleInward> addUpdateVehicleInward(@Valid @RequestBody List<VehicleInward> theVehicleInwardList) {

        for (VehicleInward vehicleInward : theVehicleInwardList) {
            String error = salesOrderService.validateVehicleInwardRequest(vehicleInward);
            if (error != null) {
                throw new InvalidValueFoundException("SalesOrder", error);
            }
        }

        for (VehicleInward vehicleInward : theVehicleInwardList) {

            User user = WarehouseHelper.getLoggedInUser();
            vehicleInward.setCreatedBy(user);
            vehicleInward.setModifiedBy(user);

            List<VehicleInwardLRNumber> vehicleInwardList = vehicleInward.getVehicleInwardLRNumbersList();

            for (VehicleInwardLRNumber vehicleInwardLRNumber : vehicleInwardList) {
                vehicleInwardLRNumber.setCreatedBy(user);
                vehicleInwardLRNumber.setModifiedBy(user);
            }
        }
        return salesOrderService.saveVehicleInward(theVehicleInwardList);
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveSalesOrder(@Valid @RequestBody List<SalesOrderRequest> theSalesOrderRequestList) {
        salesOrderService.approveSalesOrders(theSalesOrderRequestList);
        return ResponseEntity.ok().build();
    }

    // Pending Sales Order Api
    @PostMapping("/pending-sales-order/search")
    public ResponseEntity<Page<SalesOrder>> getPendingSalesOrder(PageHelper page,
            @RequestBody SalesOrderRequest request) {
        return new ResponseEntity<>(salesOrderService.findPendingSalesOrder(page, request), HttpStatus.OK);
    }

    @PostMapping("/pending-sales-order/search-all")
    public ResponseEntity<List<SalesOrder>> getPendingSalesOrderAll(@RequestBody SalesOrderRequest request) {
        return new ResponseEntity<>(salesOrderService.findAllPendingSalesOrder(request), HttpStatus.OK);
    }

    @PostMapping("/pending-sales-order/update")
    public ResponseEntity<?> saveSalesOrderSkuReason(
            @Valid @RequestBody List<SalesOrderSkuRequest> theSalesOrderSkuList) {
        salesOrderService.saveSalesOrderSkuReason(theSalesOrderSkuList);
        return ResponseEntity.ok().build();
    }

}
