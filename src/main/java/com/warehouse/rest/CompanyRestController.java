package com.warehouse.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.warehouse.administration.user.User;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.CompanyService;
import com.warehouse.setup.company.customertype.CustomerType;
import com.warehouse.setup.company.customertype.CustomerTypeListAction;
import com.warehouse.setup.company.customertype.CustomerTypeRequest;
import com.warehouse.setup.company.packinggroup.PackingGroup;
import com.warehouse.setup.company.packinggroup.PackingGroupListAction;
import com.warehouse.setup.company.packinggroup.PackingGroupRequest;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyListAction;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRequest;
import com.warehouse.setup.company.reason.Reason;
import com.warehouse.setup.company.reason.ReasonListAction;
import com.warehouse.setup.company.reason.ReasonRequest;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingListResponse;
import com.warehouse.setup.warehouse.WarehouseRequest;

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
@RequestMapping("/api/setup/company")
public class CompanyRestController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/save")
    public PrimaryCompany addPrimaryCompany(@Valid @RequestBody PrimaryCompany thePrimaryCompany) {
        String error = companyService.validatePrimaryCompanyRequest(thePrimaryCompany);
        if (error != null) {
            throw new InvalidValueFoundException("PrimaryCompany", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        thePrimaryCompany.setCreatedBy(user);
        thePrimaryCompany.setModifiedBy(user);

        thePrimaryCompany = companyService.savePrimaryCompany(thePrimaryCompany);
        return thePrimaryCompany;
    }

    @PutMapping("/update/{id}")
    public PrimaryCompany updatePrimaryCompany(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody PrimaryCompany thePrimaryCompany) {
        thePrimaryCompany.setId(theId);
        PrimaryCompany searchObj = companyService.findByPrimaryCompanyId(theId);

        String error = companyService.validatePrimaryCompanyRequest(thePrimaryCompany);
        if (error != null) {
            throw new InvalidValueFoundException("PrimaryCompany", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        thePrimaryCompany.setCreatedBy(searchObj.getCreatedBy());
        thePrimaryCompany.setModifiedBy(user);

        thePrimaryCompany.setCreatedAt(searchObj.getCreatedAt());
        searchObj = companyService.savePrimaryCompany(thePrimaryCompany);
        return searchObj;
    }

    @PutMapping("/update")
    public PrimaryCompanyListAction updatePrimaryCompanyList(@Valid @RequestBody PrimaryCompanyListAction listAction) {

        PrimaryCompanyListAction responseListAction = new PrimaryCompanyListAction();

        User user = WarehouseHelper.getLoggedInUser();

        for (PrimaryCompany singleObj : listAction) {
            companyService.findByPrimaryCompanyId(singleObj.getId());
            String error = companyService.validatePrimaryCompanyRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("PrimaryCompany", error);
            }
        }
        for (PrimaryCompany singleObj : listAction) {
            PrimaryCompany searchedObj = companyService.findByPrimaryCompanyId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(companyService.savePrimaryCompany(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/detail/{id}")
    public PrimaryCompany getPrimaryCompanyById(@PathVariable(value = "id") Long theId) {
        return companyService.findByPrimaryCompanyId(theId);
    }

    @PostMapping("/list/search")
    public ResponseEntity<Page<PrimaryCompany>> getPrimaryCompanyList(PageHelper page,
            @RequestBody PrimaryCompanyRequest request) {
        return new ResponseEntity<>(companyService.findAllPrimaryCompanyList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePrimaryCompany(@PathVariable(value = "id") Long theId) {
        companyService.deleteByPrimaryCompanyId(theId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/packing-group/save")
    public PackingGroup addPackingGroup(@Valid @RequestBody PackingGroup thePackingGroup) {
        String error = companyService.validatePackingGroupRequest(thePackingGroup);
        if (error != null) {
            throw new InvalidValueFoundException("PackingGroup", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        thePackingGroup.setCreatedBy(user);
        thePackingGroup.setModifiedBy(user);

        thePackingGroup = companyService.savePackingGroup(thePackingGroup);
        return thePackingGroup;
    }

    @PutMapping("/packing-group/update/{id}")
    public PackingGroup updatePackingGroup(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody PackingGroup thePackingGroup) {
        thePackingGroup.setId(theId);
        PackingGroup packingGroupObj = companyService.findByPackingGroupId(theId);

        String error = companyService.validatePackingGroupRequest(thePackingGroup);
        if (error != null) {
            throw new InvalidValueFoundException("PackingGroup", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        thePackingGroup.setCreatedBy(packingGroupObj.getCreatedBy());
        thePackingGroup.setModifiedBy(user);

        thePackingGroup.setCreatedAt(packingGroupObj.getCreatedAt());
        packingGroupObj = companyService.savePackingGroup(thePackingGroup);
        return packingGroupObj;
    }

    @PutMapping("/packing-group/update")
    public PackingGroupListAction updatePackingGroupList(@Valid @RequestBody PackingGroupListAction listAction) {

        PackingGroupListAction responseListAction = new PackingGroupListAction();

        User user = WarehouseHelper.getLoggedInUser();

        for (PackingGroup singleObj : listAction) {
            companyService.findByPackingGroupId(singleObj.getId());
            String error = companyService.validatePackingGroupRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("PackingGroup", error);
            }
        }
        for (PackingGroup singleObj : listAction) {
            PackingGroup searchedObj = companyService.findByPackingGroupId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(companyService.savePackingGroup(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/packing-group/detail/{id}")
    public PackingGroup getPackingGroupById(@PathVariable(value = "id") Long theId) {
        return companyService.findByPackingGroupId(theId);
    }

    @PostMapping("/packing-group/list/search")
    public ResponseEntity<Page<PackingGroup>> getPackingGroupList(PageHelper page,
            @RequestBody PackingGroupRequest request) {
        return new ResponseEntity<>(companyService.findAllPackingGroupList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/packing-group/delete/{id}")
    public ResponseEntity<?> deletePackingGroup(@PathVariable(value = "id") Long theId) {
        companyService.deleteByPackingGroupId(theId);
        return ResponseEntity.ok().build();
    }

    // Reason Mapping Api

    @PostMapping("/reason/save")
    public Reason addReason(@Valid @RequestBody Reason theReason) {
        String error = companyService.validateReasonRequest(theReason);
        if (error != null) {
            throw new InvalidValueFoundException("Reason", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theReason.setCreatedBy(user);
        theReason.setModifiedBy(user);

        theReason = companyService.saveReason(theReason);
        return theReason;
    }

    @PutMapping("/reason/update/{id}")
    public Reason updateReason(@PathVariable(value = "id") Long theId, @Valid @RequestBody Reason theReason) {
        theReason.setId(theId);
        Reason reasonObj = companyService.findByReasonId(theId);

        String error = companyService.validateReasonRequest(theReason);
        if (error != null) {
            throw new InvalidValueFoundException("Reason", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theReason.setCreatedBy(reasonObj.getCreatedBy());
        theReason.setModifiedBy(user);

        theReason.setCreatedAt(reasonObj.getCreatedAt());
        reasonObj = companyService.saveReason(theReason);
        return reasonObj;
    }

    @PutMapping("/reason/update")
    public ReasonListAction updateReasonList(@Valid @RequestBody ReasonListAction listAction) {

        ReasonListAction responseListAction = new ReasonListAction();

        User user = WarehouseHelper.getLoggedInUser();

        for (Reason singleObj : listAction) {
            companyService.findByReasonId(singleObj.getId());
            String error = companyService.validateReasonRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("Reason", error);
            }
        }
        for (Reason singleObj : listAction) {
            Reason searchedObj = companyService.findByReasonId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);
            responseListAction.add(companyService.saveReason(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/reason/detail/{id}")
    public Reason getReasonById(@PathVariable(value = "id") Long theId) {
        return companyService.findByReasonId(theId);
    }

    @PostMapping("/reason/list/search")
    public ResponseEntity<Page<Reason>> getReasonList(PageHelper page, @RequestBody ReasonRequest request) {
        return new ResponseEntity<>(companyService.findAllReasonList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/reason/delete/{id}")
    public ResponseEntity<?> deleteReason(@PathVariable(value = "id") Long theId) {
        companyService.deleteByReasonId(theId);
        return ResponseEntity.ok().build();
    }

    // Customer Type Api

    @PostMapping("/customer-type/save")
    public CustomerType addCustomerType(@Valid @RequestBody CustomerType theCustomerType) {
        String error = companyService.validateCustomerTypeRequest(theCustomerType);
        if (error != null) {
            throw new InvalidValueFoundException("CustomerType", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomerType.setCreatedBy(user);
        theCustomerType.setModifiedBy(user);

        theCustomerType = companyService.saveCustomerType(theCustomerType);
        return theCustomerType;
    }

    @PutMapping("/customer-type/update/{id}")
    public CustomerType updateCustomerType(@PathVariable(value = "id") Long theId,
            @Valid @RequestBody CustomerType theCustomerType) {
        theCustomerType.setId(theId);
        CustomerType customerTypeObj = companyService.findByCustomerTypeId(theId);

        String error = companyService.validateCustomerTypeRequest(theCustomerType);
        if (error != null) {
            throw new InvalidValueFoundException("Reason", error);
        }

        User user = WarehouseHelper.getLoggedInUser();
        theCustomerType.setCreatedBy(customerTypeObj.getCreatedBy());
        theCustomerType.setModifiedBy(user);

        theCustomerType.setCreatedAt(customerTypeObj.getCreatedAt());
        customerTypeObj = companyService.saveCustomerType(theCustomerType);
        return customerTypeObj;
    }

    @PutMapping("/customer-type/update")
    public CustomerTypeListAction updateCustomerTypeList(@Valid @RequestBody CustomerTypeListAction listAction) {

        CustomerTypeListAction responseListAction = new CustomerTypeListAction();

        User user = WarehouseHelper.getLoggedInUser();

        for (CustomerType singleObj : listAction) {
            companyService.findByCustomerTypeId(singleObj.getId());
            String error = companyService.validateCustomerTypeRequest(singleObj);
            if (error != null) {
                throw new InvalidValueFoundException("CustomerType", error);
            }
        }
        for (CustomerType singleObj : listAction) {
            CustomerType searchedObj = companyService.findByCustomerTypeId(singleObj.getId());
            singleObj.setCreatedAt(searchedObj.getCreatedAt());
            singleObj.setCreatedBy(searchedObj.getCreatedBy());
            singleObj.setModifiedBy(user);

            responseListAction.add(companyService.saveCustomerType(singleObj));
        }

        return responseListAction;
    }

    @GetMapping("/customer-type/detail/{id}")
    public CustomerType getCustomerTypeById(@PathVariable(value = "id") Long theId) {
        return companyService.findByCustomerTypeId(theId);
    }

    @PostMapping("/customer-type/list/search")
    public ResponseEntity<Page<CustomerType>> getCustomerTypeList(PageHelper page,
            @RequestBody CustomerTypeRequest request) {
        return new ResponseEntity<>(companyService.findAllCustomerTypeList(page, request), HttpStatus.OK);
    }

    @DeleteMapping("/customer-type/delete/{id}")
    public ResponseEntity<?> deleteCustomerType(@PathVariable(value = "id") Long theId) {
        companyService.deleteByCustomerTypeId(theId);
        return ResponseEntity.ok().build();
    }

    // Warehouse Company Mapping Api
    @PostMapping("/warhouse-company-mapping/search")
    public ResponseEntity<List<WarehouseCompanyMappingListResponse>> getAllWarehousesWithWarehouseCompanyMapping(
            @RequestBody WarehouseRequest warehouseRequest) {
        return new ResponseEntity<>(companyService.findAllWarehouseWithWarehouseCompanyMapping(warehouseRequest),
                HttpStatus.OK);
    }

    @PostMapping("/warhouse-company-mapping")
    public List<WarehouseCompanyMapping> addUpdateWarehouseCompanyMapping(
            @Valid @RequestBody List<WarehouseCompanyMapping> integrationMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (WarehouseCompanyMapping integrationMapping : integrationMappingList) {
            String error = companyService.validateWarehouseCompanyMappingRequest(integrationMapping);
            if (error != null) {
                throw new InvalidValueFoundException("WarehouseCompanyMapping", error);
            }

            String duplicateKeyValue = integrationMapping.getWarehouseId() + ""
                    + integrationMapping.getPrimaryCompanyId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Mapping with Primary Company:"
                        + integrationMapping.getPrimaryCompany().getName() + " and Warehouse:"
                        + integrationMapping.getWarehouse().getName();
                throw new InvalidValueFoundException("WarehouseCompanyMapping", error);
            }
            arrayList.add(duplicateKeyValue);

        }
        for (WarehouseCompanyMapping integrationMapping : integrationMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            integrationMapping.setCreatedBy(user);
            integrationMapping.setModifiedBy(user);

            if (integrationMapping.getId() != null) {
                WarehouseCompanyMapping theWarehouseCompanyMapping = companyService
                        .findByWarehouseCompanyMappingId(integrationMapping.getId());
                integrationMapping.setCreatedAt(theWarehouseCompanyMapping.getCreatedAt());
                integrationMapping.setCreatedBy(theWarehouseCompanyMapping.getCreatedBy());
            }
        }
        return companyService.saveWarehouseCompanyMapping(integrationMappingList);
    }

    @DeleteMapping("/warhouse-company-mapping")
    public ResponseEntity<?> deleteWarehouseCompanyMappingList(
            @RequestBody List<WarehouseCompanyMapping> integrationMappingList) {
        companyService.deleteAllWarehouseCompanyMapping(integrationMappingList);
        return ResponseEntity.ok().build();
    }

}
