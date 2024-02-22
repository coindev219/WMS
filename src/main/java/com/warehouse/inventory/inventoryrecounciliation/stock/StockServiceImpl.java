package com.warehouse.inventory.inventoryrecounciliation.stock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketRepository;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.location.Location;
import com.warehouse.setup.location.LocationRepository;
import com.warehouse.setup.location.hierarchy.LocationHierarchyRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CostBucketRepository costBucketRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationHierarchyRepository locationHierarchyRepository;

    @Autowired
    private StockLocationRepository stockLocationRepository;

    @Override
    public String validateStockRequest(Stock theStockRequest) {

        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theStockRequest.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theStockRequest.getPrimaryCompanyId()));
        theStockRequest.setPrimaryCompany(primaryCompany);

        Warehouse warehouse = warehouseRepository.findById(theStockRequest.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", theStockRequest.getWarehouseId()));
        theStockRequest.setWarehouse(warehouse);

        CostBucket costBucket = costBucketRepository.findById(theStockRequest.getCostBucketId()).orElseThrow(
                () -> new ResourceNotFoundException("CostBucket", "id", theStockRequest.getCostBucketId()));
        theStockRequest.setCostBucket(costBucket);

        if (Objects.nonNull(theStockRequest.getLocationHierarchyID())) {

            String locationHierarchyIDArray[] = theStockRequest.getLocationHierarchyID().split(";");
            for (String locationHierarchyIDStr : locationHierarchyIDArray) {
                Long locationHierarchyID = Long.valueOf(locationHierarchyIDStr);
                locationHierarchyRepository.findById(locationHierarchyID).orElseThrow(
                        () -> new ResourceNotFoundException("LocationHierarchy", "id", locationHierarchyID));
            }
        }

        List<StockLocation> stockLocationList = theStockRequest.getStockLocation();

        for (StockLocation stockLocation : stockLocationList) {
            Location location = locationRepository.findById(stockLocation.getLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location", "id", stockLocation.getLocationId()));
            stockLocation.setLocation(location);
        }

        return null;
    }

    @Override
    public List<Stock> saveStocks(List<Stock> theStockList) {
        List<Stock> stockList = stockRepository.saveAll(theStockList);

        for (Stock stock : stockList) {
            if (Objects.isNull(stock.getStockNo()) || stock.getStockNo().isEmpty()) {
                String strId = String.valueOf(stock.getId());
                while (strId.length() != 6) {
                    strId = "0" + strId;
                }
                int year = Integer.parseInt(new SimpleDateFormat("yy").format(new Date()));

                String stockNo = stock.getPrimaryCompany().getDocNoPrefix() + "_"
                        + stock.getWarehouse().getDocNoPrefix() + "_STI_" + year + "_" + strId;
                stock.setStockNo(stockNo);
                stock = stockRepository.save(stock);

                List<StockLocation> stockLocationList = stock.getStockLocation();

                stockLocationRepository.saveAll(stockLocationList);
            }
        }

        return stockList;
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

}
