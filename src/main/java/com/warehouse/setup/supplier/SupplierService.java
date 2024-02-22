package com.warehouse.setup.supplier;

import com.warehouse.helper.PageHelper;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTime;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTimeRequest;

import org.springframework.data.domain.Page;

public interface SupplierService {

    // Supplier Module
    public String validateSupplierRequest(Supplier theSupplier);

    public Supplier saveSupplier(Supplier theSupplier);

    public Supplier findBySupplierId(Long theId);

    public Page<Supplier> findAllSupplierList(PageHelper page, SupplierRequest request);

    public void deleteBySupplierId(Long theId);

    // SupplierLeadTime Module
    public String validateSupplierLeadTimeRequest(SupplierLeadTime theSupplierLeadTime);

    public SupplierLeadTime saveSupplierLeadTime(SupplierLeadTime theSupplierLeadTime);

    public SupplierLeadTime findBySupplierLeadTimeId(Long theId);

    public Page<SupplierLeadTime> findAllSupplierLeadTimeList(PageHelper page, SupplierLeadTimeRequest request);

    public void deleteBySupplierLeadTimeId(Long theId);
}
