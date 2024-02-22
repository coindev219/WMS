package com.warehouse.setup.company.warehousecompanymapping;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.warehouse.Warehouse;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "warehouse_company_mapping", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "primary_company_id", "warehouse_id" }),
        @UniqueConstraint(columnNames = { "primary_company_id", "warehouse_id","ship_to" }) })
public class WarehouseCompanyMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Warehouse warehouse;

    @NotNull
    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Long warehouseId;

    @Transient
    private String warehouseName;

    @Transient
    private String warehouseCode;

    @ManyToOne
    @JoinColumn(name = "primary_company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PrimaryCompany primaryCompany;

    @Transient
    private String primaryCompanyName;

    @Transient
    private String primaryCompanyCode;

    @NotNull
    @Column(name = "primary_company_id", insertable = false, updatable = false)
    private Long primaryCompanyId;

    @Size(max = 100)
    @Column(name = "ship_to")
    private String shipTo;

    @Size(max = 100)
    @Column(name = "bill_to")
    private String billTo;

    @Size(max = 250)
    @Column(name = "tax_invoice_label")
    private String taxInvoicesLabel;

    @Size(max = 100)
    @Column(name = "tax_invoice_no")
    private String taxInvoicesNo;

    private Float sgst;

    private Float cgst;

    @Column(name = "is_mapping_active")
    private Boolean isMappingActive=true;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Timestamp modifiedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User createdBy;

    @Column(name = "created_by", insertable = false, updatable = false)
    @JsonIgnore
    private Long createdById;

    @ManyToOne
    @JoinColumn(name = "modified_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User modifiedBy;

    @Column(name = "modified_by", insertable = false, updatable = false)
    @JsonIgnore
    private Long modifiedById;

    @Transient
    private String createdByUserName;

    @Transient
    private String modifiedByUserName;

    public WarehouseCompanyMapping() {
    }

    
    public WarehouseCompanyMapping(Long warehouseId, String warehouseName, String warehouseCode,
            String primaryCompanyName, String primaryCompanyCode, Long primaryCompanyId) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyId = primaryCompanyId;
    }



    public WarehouseCompanyMapping(Long id, Long warehouseId, String warehouseName, String warehouseCode,
            String primaryCompanyName, String primaryCompanyCode, Long primaryCompanyId, String shipTo, String billTo,
            String taxInvoicesLabel, String taxInvoicesNo, Float sgst, Float cgst, Boolean isMappingActive,
            Date createdAt, Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyId = primaryCompanyId;
        this.shipTo = shipTo;
        this.billTo = billTo;
        this.taxInvoicesLabel = taxInvoicesLabel;
        this.taxInvoicesNo = taxInvoicesNo;
        this.sgst = sgst;
        this.cgst = cgst;
        this.isMappingActive = isMappingActive;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public PrimaryCompany getPrimaryCompany() {
        return primaryCompany;
    }

    public void setPrimaryCompany(PrimaryCompany primaryCompany) {
        this.primaryCompany = primaryCompany;
    }

    public String getPrimaryCompanyName() {
        return primaryCompanyName;
    }

    public void setPrimaryCompanyName(String primaryCompanyName) {
        this.primaryCompanyName = primaryCompanyName;
    }

    public String getPrimaryCompanyCode() {
        return primaryCompanyCode;
    }

    public void setPrimaryCompanyCode(String primaryCompanyCode) {
        this.primaryCompanyCode = primaryCompanyCode;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public String getTaxInvoicesLabel() {
        return taxInvoicesLabel;
    }


    public void setTaxInvoicesLabel(String taxInvoicesLabel) {
        this.taxInvoicesLabel = taxInvoicesLabel;
    }


    public String getTaxInvoicesNo() {
        return taxInvoicesNo;
    }


    public void setTaxInvoicesNo(String taxInvoicesNo) {
        this.taxInvoicesNo = taxInvoicesNo;
    }


    public Float getSgst() {
        return sgst;
    }

    public void setSgst(Float sgst) {
        this.sgst = sgst;
    }

    public Float getCgst() {
        return cgst;
    }

    public void setCgst(Float cgst) {
        this.cgst = cgst;
    }

    public Boolean getIsMappingActive() {
        return isMappingActive;
    }

    public void setIsMappingActive(Boolean isMappingActive) {
        this.isMappingActive = isMappingActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(Long modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public String getModifiedByUserName() {
        return modifiedByUserName;
    }

    public void setModifiedByUserName(String modifiedByUserName) {
        this.modifiedByUserName = modifiedByUserName;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    @Override
    public String toString() {
        return "WarehouseCompanyMapping [billTo=" + billTo + ", cgst=" + cgst + ", createdAt=" + createdAt
                + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", id=" + id + ", isMappingActive=" + isMappingActive + ", modifiedAt="
                + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
                + modifiedByUserName + ", primaryCompany=" + primaryCompany + ", primaryCompanyCode="
                + primaryCompanyCode + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName="
                + primaryCompanyName + ", sgst=" + sgst + ", shipTo=" + shipTo + ", taxInvoicesLabel="
                + taxInvoicesLabel + ", taxInvoicesNo=" + taxInvoicesNo + ", warehouse=" + warehouse
                + ", warehouseCode=" + warehouseCode + ", warehouseId=" + warehouseId + ", warehouseName="
                + warehouseName + "]";
    }

}
