package com.warehouse.setup.company;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.warehouse.city.City;
import com.warehouse.city.CityService;
import com.warehouse.country.Country;
import com.warehouse.country.CountryService;
import com.warehouse.currency.Currency;
import com.warehouse.currency.CurrencyService;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.customertype.CustomerType;
import com.warehouse.setup.company.customertype.CustomerTypeRepository;
import com.warehouse.setup.company.customertype.CustomerTypeRequest;
import com.warehouse.setup.company.customertype.CustomerTypeSearchRepository;
import com.warehouse.setup.company.packinggroup.PackingGroup;
import com.warehouse.setup.company.packinggroup.PackingGroupRepository;
import com.warehouse.setup.company.packinggroup.PackingGroupRequest;
import com.warehouse.setup.company.packinggroup.PackingGroupSearchRepository;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRequest;
import com.warehouse.setup.company.primarycompany.PrimaryCompanySearchRepository;
import com.warehouse.setup.company.reason.Reason;
import com.warehouse.setup.company.reason.ReasonRepository;
import com.warehouse.setup.company.reason.ReasonRequest;
import com.warehouse.setup.company.reason.ReasonSearchRepository;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingListResponse;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRepository;
import com.warehouse.setup.warehouse.WarehouseRequest;
import com.warehouse.setup.warehouse.WarehouseSearchRepository;
import com.warehouse.setup.warehouse.WarehouseService;
import com.warehouse.state.State;
import com.warehouse.state.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private PrimaryCompanySearchRepository primaryCompanySearchRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private PackingGroupRepository packingGroupRepository;

    @Autowired
    private PackingGroupSearchRepository packingGroupSearchRepository;

    @Autowired
    private ReasonRepository reasonRepository;

    @Autowired
    private ReasonSearchRepository reasonSearchRepository;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private CustomerTypeSearchRepository customerTypeSearchRepository;

    @Autowired
    private WarehouseCompanyMappingRepository warehouseCompanyMappingRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseSearchRepository warehouseSearchRepository;


    @Override
    public String validatePrimaryCompanyRequest(PrimaryCompany thePrimaryCompany) {
        if (Objects.nonNull(thePrimaryCompany.getWarehouseId())) {
            warehouseService.findById(thePrimaryCompany.getWarehouseId());
        }

        countryService.findById(thePrimaryCompany.getCountryId());
        
        if (Objects.nonNull(thePrimaryCompany.getStateId())) {
            stateService.findById(thePrimaryCompany.getStateId());
        }

        if (Objects.nonNull(thePrimaryCompany.getCityId())) {
            cityService.findById(thePrimaryCompany.getCityId());
        }
        
        currencyService.findById(thePrimaryCompany.getCurrencyId());

        if (thePrimaryCompany.getId() == null) {
            if (primaryCompanyRepository
                    .findByName(thePrimaryCompany.getName())
                    .isPresent()) {
                return "Duplicate value found for Primary Company with name:" + thePrimaryCompany.getName();
            }
            if (primaryCompanyRepository
                    .findByCode(thePrimaryCompany.getCode())
                    .isPresent()) {
                return "Duplicate value found for Primary Company with code:" + thePrimaryCompany.getCode();
            }
            if (primaryCompanyRepository.findByContactName(thePrimaryCompany.getContactName()).isPresent()) {
                return "Duplicate value found for Primary Company with contactName:"
                        + thePrimaryCompany.getContactName();
            }
        } else {
            if (primaryCompanyRepository.findByNameAndIdNot(thePrimaryCompany.getName(),
                     thePrimaryCompany.getId()).isPresent()) {
                return "Duplicate value found for Primary Company with Id:" + thePrimaryCompany.getId() + " and name:"
                        + thePrimaryCompany.getName();
            }
            if (primaryCompanyRepository.findByCodeAndIdNot(thePrimaryCompany.getCode(),
                    thePrimaryCompany.getId()).isPresent()) {
                return "Duplicate value found for Primary Company with Id:" + thePrimaryCompany.getId() + " and code:"
                        + thePrimaryCompany.getCode();
            }
            if (primaryCompanyRepository.findByContactNameAndIdNot(thePrimaryCompany.getContactName(),
                    thePrimaryCompany.getId()).isPresent()) {
                return "Duplicate value found for Primary Company with Id:" + thePrimaryCompany.getId() + " and code:"
                        + thePrimaryCompany.getContactName();
            }
        }
        return null;
    }

    @Override
    public PrimaryCompany savePrimaryCompany(PrimaryCompany thePrimaryCompany) {

        Warehouse warehouseObj = null;
        if (Objects.nonNull(thePrimaryCompany.getWarehouseId())) {
            warehouseObj = warehouseService.findById(thePrimaryCompany.getWarehouseId());
        }
        Country country = countryService.findById(thePrimaryCompany.getCountryId());
        
        
        if (Objects.nonNull(thePrimaryCompany.getStateId())) {
            State state = stateService.findById(thePrimaryCompany.getStateId());
            thePrimaryCompany.setState(state);
        }

        if (Objects.nonNull(thePrimaryCompany.getCityId())) {
            City city = cityService.findById(thePrimaryCompany.getCityId());
            thePrimaryCompany.setCity(city);
        }
        
        Currency currency = currencyService.findById(thePrimaryCompany.getCurrencyId());

        thePrimaryCompany.setCountry(country);
        thePrimaryCompany.setCurrency(currency);
        
        PrimaryCompany primaryCompany = primaryCompanyRepository.save(thePrimaryCompany);

        if (thePrimaryCompany.getId() == null){
            WarehouseCompanyMapping warehouseCompanyMapping = new WarehouseCompanyMapping();
            warehouseCompanyMapping.setPrimaryCompany(primaryCompany);
            warehouseCompanyMapping.setWarehouseId(thePrimaryCompany.getWarehouseId());
            warehouseCompanyMapping.setPrimaryCompanyId(primaryCompany.getId());
            warehouseCompanyMapping.setWarehouse(warehouseObj);
            warehouseCompanyMapping.setShipTo(thePrimaryCompany.getShipTo());
            warehouseCompanyMapping.setBillTo(thePrimaryCompany.getBillTo());
            warehouseCompanyMapping.setTaxInvoicesLabel("");
            warehouseCompanyMapping.setTaxInvoicesNo("");
            warehouseCompanyMapping.setCreatedBy(primaryCompany.getCreatedBy());
            warehouseCompanyMapping.setModifiedBy(primaryCompany.getCreatedBy());
            
            saveWarehouseCompanyMapping(Collections.singletonList(warehouseCompanyMapping));
        }
        

        return primaryCompany;

    }

    @Override
    public PrimaryCompany findByPrimaryCompanyId(Long theId) {
        return primaryCompanyRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id", theId));
    }

    @Override
    public Page<PrimaryCompany> findAllPrimaryCompanyList(PageHelper page, PrimaryCompanyRequest request) {
        return primaryCompanySearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByPrimaryCompanyId(Long theId) {
        primaryCompanyRepository.findById(theId);
        primaryCompanyRepository.deleteById(theId);
    }

    // Packing Group Api

    @Override
    public String validatePackingGroupRequest(PackingGroup thePackingGroup) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(thePackingGroup.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        thePackingGroup.getPrimaryCompanyId()));

        thePackingGroup.setPrimaryCompany(primaryCompany);

        if (thePackingGroup.getId() == null) {
            if (packingGroupRepository
                    .findByNameAndPrimaryCompanyId(thePackingGroup.getName(), thePackingGroup.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Packing Group with name:" + thePackingGroup.getName()
                        + " and Primary Company:" + primaryCompany.getName();
            }
            if (packingGroupRepository
                    .findByCodeAndPrimaryCompanyId(thePackingGroup.getCode(), thePackingGroup.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Packing Group with code:" + thePackingGroup.getCode()
                        + " and Primary Company:" + primaryCompany.getName();
            }

        } else {
            if (packingGroupRepository.findByNameAndPrimaryCompanyIdAndIdNot(thePackingGroup.getName(),
                    thePackingGroup.getPrimaryCompanyId(), thePackingGroup.getId()).isPresent()) {
                return "Duplicate value found for Packing Group with id:" + thePackingGroup.getId() + " and name:"
                        + thePackingGroup.getName() + " and Primary Company:" + primaryCompany.getName();
            }
            if (packingGroupRepository.findByCodeAndPrimaryCompanyIdAndIdNot(thePackingGroup.getCode(),
                    thePackingGroup.getPrimaryCompanyId(), thePackingGroup.getId()).isPresent()) {
                return "Duplicate value found for Packing Group with id:" + thePackingGroup.getId() + " and code:"
                        + thePackingGroup.getCode() + " and Primary Company:" + primaryCompany.getName();
            }
        }
        return null;
    }

    @Override
    public PackingGroup savePackingGroup(PackingGroup thePackingGroup) {
        return packingGroupRepository.save(thePackingGroup);
    }

    @Override
    public PackingGroup findByPackingGroupId(Long theId) {
        return packingGroupRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("PackingGroup", "id", theId));
    }

    @Override
    public Page<PackingGroup> findAllPackingGroupList(PageHelper page, PackingGroupRequest request) {
        return packingGroupSearchRepository.findAllWithFilters(page, request);

    }

    @Override
    public void deleteByPackingGroupId(Long theId) {
        packingGroupRepository.deleteById(theId);
    }

    @Override
    public String validateReasonRequest(Reason theReason) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theReason.getPrimaryCompanyId()).orElseThrow(
                () -> new ResourceNotFoundException("PrimaryCompany", "id", theReason.getPrimaryCompanyId()));

        theReason.setPrimaryCompany(primaryCompany);

        if (theReason.getId() == null) {
            if (reasonRepository.findByNameAndPrimaryCompanyId(theReason.getName(), theReason.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Reason with name:" + theReason.getName() + " and Primary Company:"
                        + primaryCompany.getName();
            }
            if (reasonRepository.findByCodeAndPrimaryCompanyId(theReason.getCode(), theReason.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Reason with code:" + theReason.getCode() + " and Primary Company:"
                        + primaryCompany.getName();
            }

        } else {
            if (reasonRepository.findByNameAndPrimaryCompanyIdAndIdNot(theReason.getName(),
                    theReason.getPrimaryCompanyId(), theReason.getId()).isPresent()) {
                return "Duplicate value found for Reason with id:" + theReason.getId() + " and name:"
                        + theReason.getName() + " and Primary Company:" + primaryCompany.getName();
            }
            if (reasonRepository.findByCodeAndPrimaryCompanyIdAndIdNot(theReason.getCode(),
                    theReason.getPrimaryCompanyId(), theReason.getId()).isPresent()) {
                return "Duplicate value found for Reason with id:" + theReason.getId() + " and code:"
                        + theReason.getCode() + " and Primary Company:" + primaryCompany.getName();
            }
        }
        return null;
    }

    @Override
    public Reason saveReason(Reason theReason) {
        return reasonRepository.save(theReason);
    }

    @Override
    public Reason findByReasonId(Long theId) {
        return reasonRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("Reason", "id", theId));
    }

    @Override
    public Page<Reason> findAllReasonList(PageHelper page, ReasonRequest request) {
        return reasonSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByReasonId(Long theId) {
        reasonRepository.deleteById(theId);
    }

    @Override
    public String validateCustomerTypeRequest(CustomerType theCustomerType) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theCustomerType.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theCustomerType.getPrimaryCompanyId()));

        theCustomerType.setPrimaryCompany(primaryCompany);

        if (theCustomerType.getId() == null) {
            if (customerTypeRepository
                    .findByNameAndPrimaryCompanyId(theCustomerType.getName(), theCustomerType.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Customer Type with name:" + theCustomerType.getName()
                        + " and Primary Company:" + primaryCompany.getName();
            }
            if (customerTypeRepository
                    .findByCodeAndPrimaryCompanyId(theCustomerType.getCode(), theCustomerType.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Customer Type with code:" + theCustomerType.getCode()
                        + " and Primary Company:" + primaryCompany.getName();
            }

        } else {
            if (customerTypeRepository.findByNameAndPrimaryCompanyIdAndIdNot(theCustomerType.getName(),
                    theCustomerType.getPrimaryCompanyId(), theCustomerType.getId()).isPresent()) {
                return "Duplicate value found for Customer Type with id:" + theCustomerType.getId() + " and name:"
                        + theCustomerType.getName() + " and Primary Company:" + primaryCompany.getName();
            }
            if (customerTypeRepository.findByCodeAndPrimaryCompanyIdAndIdNot(theCustomerType.getCode(),
                    theCustomerType.getPrimaryCompanyId(), theCustomerType.getId()).isPresent()) {
                return "Duplicate value found for Customer Type with id:" + theCustomerType.getId() + " and code:"
                        + theCustomerType.getCode() + " and Primary Company:" + primaryCompany.getName();
            }
        }
        return null;
    }

    @Override
    public CustomerType saveCustomerType(CustomerType theCustomerType) {
        return customerTypeRepository.save(theCustomerType);
    }

    @Override
    public CustomerType findByCustomerTypeId(Long theId) {
        return customerTypeRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Type", "id", theId));
    }

    @Override
    public Page<CustomerType> findAllCustomerTypeList(PageHelper page, CustomerTypeRequest request) {
        return customerTypeSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public void deleteByCustomerTypeId(Long theId) {
        customerTypeRepository.deleteById(theId);

    }


     // Company Warehouse mapping
     @Override
     public List<WarehouseCompanyMappingListResponse> findAllWarehouseWithWarehouseCompanyMapping(WarehouseRequest warehouseRequest) {
         return warehouseSearchRepository.findAllWithWarehouseCompanyMappingList(warehouseRequest);
     }
 
     @Override
     public String validateWarehouseCompanyMappingRequest(WarehouseCompanyMapping theWarehouseCompanyMapping) {
         if (Objects.nonNull(theWarehouseCompanyMapping.getWarehouseId())) {
             Warehouse warehouse = warehouseRepository.findById(theWarehouseCompanyMapping.getWarehouseId()).orElseThrow(
                     () -> new ResourceNotFoundException("Warehouse", "id", theWarehouseCompanyMapping.getWarehouseId()));
             theWarehouseCompanyMapping.setWarehouse(warehouse);
         }
 
         PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theWarehouseCompanyMapping.getPrimaryCompanyId())
                 .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                 theWarehouseCompanyMapping.getPrimaryCompanyId()));
 
         theWarehouseCompanyMapping.setPrimaryCompany(primaryCompany);
 
 
         if (theWarehouseCompanyMapping.getId() == null) {
             if (warehouseCompanyMappingRepository
                     .findByPrimaryCompanyIdAndWarehouseId(theWarehouseCompanyMapping.getPrimaryCompanyId(), theWarehouseCompanyMapping.getWarehouseId()).isPresent()) {
                 return "Duplicate value found for Mapping with Primary Company Name:" + theWarehouseCompanyMapping.getPrimaryCompany().getName()
                         + " and Warehouse:" + theWarehouseCompanyMapping.getWarehouse().getName();
             }
             if (warehouseCompanyMappingRepository
                     .findByPrimaryCompanyIdAndWarehouseIdAndShipTo(theWarehouseCompanyMapping.getPrimaryCompanyId(), theWarehouseCompanyMapping.getWarehouseId(),theWarehouseCompanyMapping.getShipTo()).isPresent()) {
                 return "Duplicate value found for Mapping with Primary Company Name:" + theWarehouseCompanyMapping.getPrimaryCompany().getName()
                         + " and Warehouse:" + theWarehouseCompanyMapping.getWarehouse().getName()+" and Ship To "+theWarehouseCompanyMapping.getShipTo();
             }
         } else {
             if (warehouseCompanyMappingRepository
                     .findByPrimaryCompanyIdAndWarehouseIdAndIdNot(theWarehouseCompanyMapping.getPrimaryCompanyId(), theWarehouseCompanyMapping.getWarehouseId(), theWarehouseCompanyMapping.getId()).isPresent()) {
                 return "Duplicate value found for Mapping with with ID:"+theWarehouseCompanyMapping.getId()+" and Primary Company Name:" + theWarehouseCompanyMapping.getPrimaryCompany().getName()
                         + " and Warehouse:" + theWarehouseCompanyMapping.getWarehouse().getName();
             }
             if (warehouseCompanyMappingRepository
                     .findByPrimaryCompanyIdAndWarehouseIdAndShipToAndIdNot(theWarehouseCompanyMapping.getPrimaryCompanyId(), theWarehouseCompanyMapping.getWarehouseId(),theWarehouseCompanyMapping.getShipTo(), theWarehouseCompanyMapping.getId()).isPresent()) {
                 return "Duplicate value found for Mapping with with ID:"+theWarehouseCompanyMapping.getId()+" and Primary Company Name:" + theWarehouseCompanyMapping.getPrimaryCompany().getName()
                         + " and Warehouse:" + theWarehouseCompanyMapping.getWarehouse().getName()+" and Ship To "+theWarehouseCompanyMapping.getShipTo();
             }
         }
         return null;
     }
 
     @Override
     public List<WarehouseCompanyMapping> saveWarehouseCompanyMapping(List<WarehouseCompanyMapping> theWarehouseCompanyMapping) {
         return warehouseCompanyMappingRepository.saveAll(theWarehouseCompanyMapping);
     }
 
     @Override
     public WarehouseCompanyMapping findByWarehouseCompanyMappingId(Long theId) {
         return warehouseCompanyMappingRepository.findById(theId)
                 .orElseThrow(() -> new ResourceNotFoundException("WarehouseCompanyMapping", "id", theId));
     }
 
     @Override
     public void deleteAllWarehouseCompanyMapping(List<WarehouseCompanyMapping> theWarehouseCompanyMapping) {
         warehouseCompanyMappingRepository.deleteAll(theWarehouseCompanyMapping);
 
     }
 

}
