package com.warehouse.setup.company;

import java.util.List;

import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.customertype.CustomerType;
import com.warehouse.setup.company.customertype.CustomerTypeRequest;
import com.warehouse.setup.company.packinggroup.PackingGroup;
import com.warehouse.setup.company.packinggroup.PackingGroupRequest;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRequest;
import com.warehouse.setup.company.reason.Reason;
import com.warehouse.setup.company.reason.ReasonRequest;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingListResponse;
import com.warehouse.setup.warehouse.WarehouseRequest;

import org.springframework.data.domain.Page;

public interface CompanyService {

    public String validatePrimaryCompanyRequest(PrimaryCompany thePrimaryCompany);

    public PrimaryCompany savePrimaryCompany(PrimaryCompany thePrimaryCompany);

    public PrimaryCompany findByPrimaryCompanyId(Long theId);

    public Page<PrimaryCompany> findAllPrimaryCompanyList(PageHelper page, PrimaryCompanyRequest request);

    public void deleteByPrimaryCompanyId(Long theId);

    // Packing Group Module
    public String validatePackingGroupRequest(PackingGroup thePackingGroup);

    public PackingGroup savePackingGroup(PackingGroup thePackingGroup);

    public PackingGroup findByPackingGroupId(Long theId);

    public Page<PackingGroup> findAllPackingGroupList(PageHelper page, PackingGroupRequest request);

    public void deleteByPackingGroupId(Long theId);

    // Reason Module
    public String validateReasonRequest(Reason theReason);

    public Reason saveReason(Reason theReason);

    public Reason findByReasonId(Long theId);

    public Page<Reason> findAllReasonList(PageHelper page, ReasonRequest request);

    public void deleteByReasonId(Long theId);

    // Customer Type Module
    public String validateCustomerTypeRequest(CustomerType theCustomerType);

    public CustomerType saveCustomerType(CustomerType theCustomerType);

    public CustomerType findByCustomerTypeId(Long theId);

    public Page<CustomerType> findAllCustomerTypeList(PageHelper page, CustomerTypeRequest request);

    public void deleteByCustomerTypeId(Long theId);


    
	// Map Warehouse and Company
		
	public List<WarehouseCompanyMappingListResponse> findAllWarehouseWithWarehouseCompanyMapping(WarehouseRequest warehouseRequest);

	public String validateWarehouseCompanyMappingRequest(WarehouseCompanyMapping theWarehouseCompanyMapping);

	public List<WarehouseCompanyMapping> saveWarehouseCompanyMapping(List<WarehouseCompanyMapping> theWarehouseCompanyMapping);

	public WarehouseCompanyMapping findByWarehouseCompanyMappingId(Long theId);

	public void deleteAllWarehouseCompanyMapping(List<WarehouseCompanyMapping> theWarehouseCompanyMapping);

}
