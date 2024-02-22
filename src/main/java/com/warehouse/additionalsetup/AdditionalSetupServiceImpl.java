package com.warehouse.additionalsetup;

import java.util.Objects;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketRepository;
import com.warehouse.additionalsetup.costbucket.CostBucketRequest;
import com.warehouse.additionalsetup.costbucket.CostBucketSearchRepository;
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.freighter.FreighterRepository;
import com.warehouse.additionalsetup.freighter.FreighterRequest;
import com.warehouse.additionalsetup.freighter.FreighterSearchRepository;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.ordertype.OrderTypeRepository;
import com.warehouse.additionalsetup.ordertype.OrderTypeRequest;
import com.warehouse.additionalsetup.ordertype.OrderTypeSearchRepository;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.skutype.SkuTypeRepository;
import com.warehouse.additionalsetup.skutype.SkuTypeRequest;
import com.warehouse.additionalsetup.skutype.SkuTypeSearchRepository;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.uom.UomRepository;
import com.warehouse.additionalsetup.uom.UomRequest;
import com.warehouse.additionalsetup.uom.UomSearchRepository;
import com.warehouse.additionalsetup.vasactivity.VasActivity;
import com.warehouse.additionalsetup.vasactivity.VasActivityRepository;
import com.warehouse.additionalsetup.vasactivity.VasActivityRequest;
import com.warehouse.additionalsetup.vasactivity.VasActivitySearchRepository;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AdditionalSetupServiceImpl implements AdditionalSetupService {

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private SkuTypeRepository skuTypeRepository;

    @Autowired
    private SkuTypeSearchRepository skuTypeSearchRepository;

    @Autowired
    private UomRepository uomRepository;

    @Autowired
    private UomSearchRepository uomSearchRepository;

    @Autowired
    private CostBucketRepository costBucketRepository;

    @Autowired
    private CostBucketSearchRepository costBucketSearchRepository;

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private OrderTypeSearchRepository orderTypeSearchRepository;

    @Autowired
    private FreighterRepository freighterRepository;

    @Autowired
    private FreighterSearchRepository freighterSearchRepository;

    @Autowired
    private VasActivityRepository vasActivityRepository;

    @Autowired
    private VasActivitySearchRepository vasActivitySearchRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public String validateSkuTypeRequest(SkuType theSkuType) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theSkuType.getPrimaryCompanyId()).orElseThrow(
                () -> new ResourceNotFoundException("PrimaryCompany", "id", theSkuType.getPrimaryCompanyId()));

        theSkuType.setPrimaryCompany(primaryCompany);

        if (theSkuType.getId() == null) {
            if (skuTypeRepository.findByNameAndPrimaryCompanyId(theSkuType.getName(), theSkuType.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Sku Type with name:" + theSkuType.getName() + " and Primary Company:"
                        + primaryCompany.getName();
            }
            if (skuTypeRepository.findByCodeAndPrimaryCompanyId(theSkuType.getCode(), theSkuType.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Sku Type with code:" + theSkuType.getCode() + " and Primary Company:"
                        + primaryCompany.getName();
            }

        } else {
            if (skuTypeRepository.findByNameAndPrimaryCompanyIdAndIdNot(theSkuType.getName(),
                    theSkuType.getPrimaryCompanyId(), theSkuType.getId()).isPresent()) {
                return "Duplicate value found for Sku Type with id:" + theSkuType.getId() + " and name:"
                        + theSkuType.getName() + " and Primary Company:" + primaryCompany.getName();
            }
            if (skuTypeRepository.findByCodeAndPrimaryCompanyIdAndIdNot(theSkuType.getCode(),
                    theSkuType.getPrimaryCompanyId(), theSkuType.getId()).isPresent()) {
                return "Duplicate value found for Sku Type with id:" + theSkuType.getId() + " and code:"
                        + theSkuType.getCode() + " and Primary Company:" + primaryCompany.getName();
            }
        }
        return null;
    }

    @Override
    public SkuType saveSkuType(SkuType theSkuType) {
        return skuTypeRepository.save(theSkuType);
    }

    @Override
    public SkuType findBySkuTypeId(Long theId) {
        return skuTypeRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Sku Type", "id", theId));
    }

    @Override
    public Page<SkuType> findAllSkuTypeList(PageHelper page, SkuTypeRequest request) {
        return skuTypeSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteBySkuTypeId(Long theId) {
        skuTypeRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Sku Type", "id", theId));
        skuTypeRepository.deleteById(theId);

    }

    // Uom

    @Override
    public String validateUomRequest(Uom theUom) {
        if (theUom.getId() == null) {
            if (uomRepository.findByNameAndCreatedById(theUom.getName(), theUom.getCreatedById()).isPresent()) {
                return "Duplicate value found for Uom with name:" + theUom.getName();
            }
            if (uomRepository.findByUomAndCreatedById(theUom.getUom(), theUom.getCreatedById()).isPresent()) {
                return "Duplicate value found for Uom with uom:" + theUom.getUom();
            }

        } else {
            if (uomRepository
                    .findByNameAndCreatedByIdAndIdNot(theUom.getName(), theUom.getCreatedById(), theUom.getId())
                    .isPresent()) {
                return "Duplicate value found for Uom with name:" + theUom.getName();
            }
            if (uomRepository.findByUomAndCreatedByIdAndIdNot(theUom.getUom(), theUom.getCreatedById(), theUom.getId())
                    .isPresent()) {
                return "Duplicate value found for Uom with uom:" + theUom.getUom();
            }
        }
        return null;
    }

    @Override
    public Uom saveUom(Uom theUom) {
        return uomRepository.save(theUom);
    }

    @Override
    public Uom findByUomId(Long theId) {
        return uomRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("UOM", "id", theId));
    }

    @Override
    public Page<Uom> findAllUomList(PageHelper page, UomRequest request) {
        return uomSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByUomId(Long theId) {
        uomRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("UOM", "id", theId));
        uomRepository.deleteById(theId);

    }

    // Cost Bucket Api

    @Override
    public String validateCostBucketRequest(CostBucket theCostBucket) {

        if (theCostBucket.getId() == null) {
            if (costBucketRepository.findByName(theCostBucket.getName()).isPresent()) {
                return "Duplicate value found for Cost Bucket with name:" + theCostBucket.getName();
            }
            if (costBucketRepository.findByCode(theCostBucket.getCode()).isPresent()) {
                return "Duplicate value found for Cost Bucket with code:" + theCostBucket.getCode();
            }

        } else {
            if (costBucketRepository.findByNameAndIdNot(theCostBucket.getName(), theCostBucket.getId()).isPresent()) {
                return "Duplicate value found for Cost Bucket with id:" + theCostBucket.getId() + " and name:"
                        + theCostBucket.getName();
            }
            if (costBucketRepository.findByCodeAndIdNot(theCostBucket.getCode(), theCostBucket.getId()).isPresent()) {
                return "Duplicate value found for Cost Bucket with id:" + theCostBucket.getId() + " and code:"
                        + theCostBucket.getCode();
            }
        }
        return null;
    }

    @Override
    public CostBucket saveCostBucket(CostBucket theCostBucket) {
        return costBucketRepository.save(theCostBucket);
    }

    @Override
    public CostBucket findByCostBucketId(Long theId) {
        return costBucketRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Cost Bucket", "id", theId));
    }

    @Override
    public Page<CostBucket> findAllCostBucketList(PageHelper page, CostBucketRequest request) {
        return costBucketSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByCostBucketId(Long theId) {
        costBucketRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Cost Bucket", "id", theId));
        costBucketRepository.deleteById(theId);

    }

    // Order Type

    @Override
    public String validateOrderTypeRequest(OrderType theOrderType) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theOrderType.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theOrderType.getPrimaryCompanyId()));

        theOrderType.setPrimaryCompany(primaryCompany);

        if (theOrderType.getId() == null) {
           
            if (orderTypeRepository
                    .findByCodeAndPrimaryCompanyId(theOrderType.getCode(), theOrderType.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Order Type with code:" + theOrderType.getCode()
                        + " and Primary Company:" + primaryCompany.getName();
            }

        } else {
            
            if (orderTypeRepository.findByCodeAndPrimaryCompanyIdAndIdNot(theOrderType.getCode(),
                    theOrderType.getPrimaryCompanyId(), theOrderType.getId()).isPresent()) {
                return "Duplicate value found for Order Type with id:" + theOrderType.getId() + " and code:"
                        + theOrderType.getCode() + " and Primary Company:" + primaryCompany.getName();
            }
        }
        return null;
    }

    @Override
    public OrderType saveOrderType(OrderType theOrderType) {
        return orderTypeRepository.save(theOrderType);
    }

    @Override
    public OrderType findByOrderTypeId(Long theId) {
        return orderTypeRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Type", "id", theId));
    }

    @Override
    public Page<OrderType> findAllOrderTypeList(PageHelper page, OrderTypeRequest request) {
        return orderTypeSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByOrderTypeId(Long theId) {
        orderTypeRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Order Type", "id", theId));
        orderTypeRepository.deleteById(theId);

    }

    // Freighter Api
    @Override
    public String validateFreighterRequest(Freighter theFreighter) {

        if (theFreighter.getId() == null) {
            if (freighterRepository.findByName(theFreighter.getName()).isPresent()) {
                return "Duplicate value found for Freighter with name:" + theFreighter.getName();
            }
            if (freighterRepository.findByCode(theFreighter.getCode()).isPresent()) {
                return "Duplicate value found for Freighter with code:" + theFreighter.getCode();
            }

        } else {
            if (freighterRepository.findByNameAndIdNot(theFreighter.getName(), theFreighter.getId()).isPresent()) {
                return "Duplicate value found for Freighter with id:" + theFreighter.getId() + " and name:"
                        + theFreighter.getName();
            }
            if (freighterRepository.findByCodeAndIdNot(theFreighter.getCode(), theFreighter.getId()).isPresent()) {
                return "Duplicate value found for Freighter with id:" + theFreighter.getId() + " and code:"
                        + theFreighter.getCode();
            }
        }
        return null;
    }

    @Override
    public Freighter saveFreighter(Freighter theFreighter) {
        return freighterRepository.save(theFreighter);
    }

    @Override
    public Freighter findByFreighterId(Long theId) {
        return freighterRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Freighter", "id", theId));
    }

    @Override
    public Page<Freighter> findAllFreighterList(PageHelper page, FreighterRequest request) {
        return freighterSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByFreighterId(Long theId) {
        freighterRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Freighter", "id", theId));
        freighterRepository.deleteById(theId);

    }

    // Vas Activity Api
    @Override
    public String validateVasActivityRequest(VasActivity theVasActivity) {

        if (Objects.nonNull(theVasActivity.getWarehouseId())) {
            Warehouse warehouse = warehouseRepository.findById(theVasActivity.getWarehouseId()).orElseThrow(
                    () -> new ResourceNotFoundException("Warehouse", "id", theVasActivity.getWarehouseId()));
            theVasActivity.setWarehouse(warehouse);
        }

        if (theVasActivity.getId() == null) {
            if (vasActivityRepository.findByNameAndWarehouseId(theVasActivity.getName(), theVasActivity.getWarehouseId()).isPresent()) {
                return "Duplicate value found for VasActivity with name:" + theVasActivity.getName() + " and Warehouse:"
                        + theVasActivity.getWarehouse().getName();
            }
            if (vasActivityRepository.findByCodeAndWarehouseId(theVasActivity.getCode(), theVasActivity.getWarehouseId()).isPresent()) {
                return "Duplicate value found for VasActivity with code:" + theVasActivity.getCode() + " and Warehouse:"
                        + theVasActivity.getWarehouse().getName();
            }

        } else {
            if (vasActivityRepository.findByNameAndWarehouseIdAndIdNot(theVasActivity.getName(), theVasActivity.getWarehouseId(), theVasActivity.getId())
                    .isPresent()) {
                return "Duplicate value found for VasActivity with id:" + theVasActivity.getId() + " and name:"
                        + theVasActivity.getName() + " and Warehouse:" + theVasActivity.getWarehouse().getName();
            }
            if (vasActivityRepository.findByCodeAndWarehouseIdAndIdNot(theVasActivity.getCode(), theVasActivity.getWarehouseId(), theVasActivity.getId())
                    .isPresent()) {
                return "Duplicate value found for VasActivity with id:" + theVasActivity.getId() + " and code:"
                        + theVasActivity.getCode() + " and Warehouse:" + theVasActivity.getWarehouse().getName();
            }
        }
        return null;
    }

    @Override
    public VasActivity saveVasActivity(VasActivity theVasActivity) {
        return vasActivityRepository.save(theVasActivity);
    }

    @Override
    public VasActivity findByVasActivityId(Long theId) {
        return vasActivityRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("VasActivity", "id", theId));
    }

    @Override
    public Page<VasActivity> findAllVasActivityList(PageHelper page, VasActivityRequest request) {
        return vasActivitySearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByVasActivityId(Long theId) {
        vasActivityRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("VasActivity", "id", theId));
        vasActivityRepository.deleteById(theId);

    }

}
