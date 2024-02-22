package com.warehouse.sales.salesorder;

import java.util.List;

import com.warehouse.helper.PageHelper;
import com.warehouse.sales.salesorder.vehicleinward.SalesOrderCustomResponse;
import com.warehouse.sales.salesorder.vehicleinward.VehicleInward;

import org.springframework.data.domain.Page;

public interface SalesOrderService {
    
    // Sales Order Api
    public String validateSalesOrderRequest(SalesOrder theSalesOrderRequest);

    public List<SalesOrder> saveSalesOrders(List<SalesOrder> theSalesOrderList);

    public void deleteAllSalesOrderSku(List<SalesOrderSku> theSalesOrderSkuList);

    public Page<SalesOrder> findSalesOrderWithFilters(PageHelper helper, SalesOrderRequest request);

    public List<SalesOrder> findSalesOrderWithFilters(SalesOrderRequest request);

    
    // Vehicle Inward Api
    public String validateVehicleInwardRequest(VehicleInward theVehicleInwardRequest);
    
    public List<VehicleInward> saveVehicleInward(List<VehicleInward> theVehicleInwardList);

    public Page<SalesOrderCustomResponse> findSalesOrderWithFcNo(PageHelper helper, SalesOrderRequest request);

    public List<SalesOrderCustomResponse> findSalesOrderWithFcNoAll(SalesOrderRequest request);


    //Approve Sales Order
    public void approveSalesOrders(List<SalesOrderRequest> theSalesOrderRequestList);


    // Pending Sales order
    public Page<SalesOrder> findPendingSalesOrder(PageHelper helper, SalesOrderRequest request);

    public List<SalesOrder> findAllPendingSalesOrder(SalesOrderRequest request);

    public void saveSalesOrderSkuReason(List<SalesOrderSkuRequest> theSalesOrderSkuRequestList);

}
