package com.warehouse.inventory.inventoryrecounciliation.cycle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketRepository;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.uom.UomRepository;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.location.hierarchy.LocationHierarchyRepository;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.SkuRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CycleServiceImpl implements CycleService {

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CostBucketRepository costBucketRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private LocationHierarchyRepository locationHierarchyRepository;

    @Autowired
    private CycleSkuRepository cycleSkuRepository;

    @Autowired
    private UomRepository uomRepository;

    @Override
    public String validateCycleRequest(Cycle theCycleRequest) {

        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theCycleRequest.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theCycleRequest.getPrimaryCompanyId()));
        theCycleRequest.setPrimaryCompany(primaryCompany);

        Warehouse warehouse = warehouseRepository.findById(theCycleRequest.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", theCycleRequest.getWarehouseId()));
        theCycleRequest.setWarehouse(warehouse);

        CostBucket costBucket = costBucketRepository.findById(theCycleRequest.getCostBucketId()).orElseThrow(
                () -> new ResourceNotFoundException("CostBucket", "id", theCycleRequest.getCostBucketId()));
        theCycleRequest.setCostBucket(costBucket);

        if (Objects.nonNull(theCycleRequest.getLocationHierarchyId())) {

            String locationHierarchyIDArray[] = theCycleRequest.getLocationHierarchyId().split(";");
            for (String locationHierarchyIDStr : locationHierarchyIDArray) {
                Long locationHierarchyID = Long.valueOf(locationHierarchyIDStr);
                locationHierarchyRepository.findById(locationHierarchyID).orElseThrow(
                        () -> new ResourceNotFoundException("LocationHierarchy", "id", locationHierarchyID));
            }
        }

        List<CycleSku> cycleSkuList = theCycleRequest.getCycleSku();

        for (CycleSku cycleSku : cycleSkuList) {
            Sku sku = skuRepository.findById(cycleSku.getSkuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sku", "id", cycleSku.getSkuId()));
            cycleSku.setSku(sku);

            Uom uom = uomRepository.findById(sku.getStorageUomId())
                    .orElseThrow(() -> new ResourceNotFoundException("uom", "id", sku.getStorageUomId()));
            cycleSku.setUom(uom);
        }

        return null;
    }

    @Override
    public List<Cycle> saveCycles(List<Cycle> theCycleList) {
        List<Cycle> cycleList = cycleRepository.saveAll(theCycleList);

        for (Cycle cycle : cycleList) {
            if (Objects.isNull(cycle.getCycleNo()) || cycle.getCycleNo().isEmpty()) {
                String strId = String.valueOf(cycle.getId());
                while (strId.length() != 6) {
                    strId = "0" + strId;
                }
                int year = Integer.parseInt(new SimpleDateFormat("yy").format(new Date()));

                String cycleNo = cycle.getPrimaryCompany().getDocNoPrefix() + "_"
                        + cycle.getWarehouse().getDocNoPrefix() + "_CCI_" + year + "_" + strId;
                cycle.setCycleNo(cycleNo);
                cycle = cycleRepository.save(cycle);

                List<CycleSku> cycleSkuList = cycle.getCycleSku();

                cycleSkuRepository.saveAll(cycleSkuList);
            }
        }

        return cycleList;
    }

    @Override
    public List<Cycle> findAll() {
        return cycleRepository.findAll();
    }

}
