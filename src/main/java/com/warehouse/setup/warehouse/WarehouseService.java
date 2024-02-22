package com.warehouse.setup.warehouse;

import java.util.List;

import com.warehouse.helper.PageHelper;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMapping;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMappingListResponse;
import com.warehouse.setup.warehouse.printers.ConfigurePrinter;
import com.warehouse.setup.warehouse.printers.ConfigurePrinterListResponse;
import com.warehouse.setup.warehouse.runningcost.RunningCost;
import com.warehouse.setup.warehouse.runningcost.RunningCostListResponse;

import org.springframework.data.domain.Page;


public interface WarehouseService {

	public String validateWarehouseRequest(Warehouse theWarehouse);

	public Page<Warehouse> findAll(PageHelper warehousePage, WarehouseRequest warehouseRequest);

	public Warehouse findById(Long theId);

	public Warehouse save(Warehouse theWarehouse);

	public void deleteById(Long theId);

	public boolean isWarehouseExists(Long theId);

	
	
	
	
	// Configure Printer
	public List<ConfigurePrinterListResponse> findAllWarehouseWithConfigurePrinters(WarehouseRequest warehouseRequest);

	public String validateConfigurePrinterRequest(ConfigurePrinter theConfigurePrinter);

	public List<ConfigurePrinter> savePrinter(List<ConfigurePrinter> theConfigurePrinter);

	public ConfigurePrinter findByConfigurePrinterId(Long theId);

	public void deleteAllPrinters(List<ConfigurePrinter> theConfigurePrinter);

	// Integrated Mapping Printer
	public List<IntegrationMappingListResponse> findAllWarehouseWithIntegrationMapping(WarehouseRequest warehouseRequest);

	public String validateIntregrationMappingRequest(IntegrationMapping theIntregrationMapping);

	public List<IntegrationMapping> saveIntregrationMapping(List<IntegrationMapping> theIntregrationMapping);

	public IntegrationMapping findByIntregrationMappingId(Long theId);

	public void deleteAllIntregrationMapping(List<IntegrationMapping> theIntregrationMapping);


	// RunningCost
		
	public List<RunningCostListResponse> findAllWarehouseWithRunningCost(WarehouseRequest warehouseRequest);

	public String validateRunningCostRequest(RunningCost theRunningCost);

	public List<RunningCost> saveRunningCost(List<RunningCost> theRunningCost);

	public RunningCost findByRunningCostId(Long theId);

	public void deleteAllRunningCost(List<RunningCost> theRunningCost);


}
