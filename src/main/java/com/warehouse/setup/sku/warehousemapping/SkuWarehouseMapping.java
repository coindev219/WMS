package com.warehouse.setup.sku.warehousemapping;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.enums.AllocRuleEnum;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.supplier.Supplier;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sku_warehouse_mapping", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "warehouse_company_mapping_id", "sku_id" }) })
public class SkuWarehouseMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_company_mapping_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private WarehouseCompanyMapping warehouseCompanyMapping;

    @NotNull
    @Column(name = "warehouse_company_mapping_id", insertable = false, updatable = false)
    private Long warehouseCompanyMappingId;


    @ManyToOne
    @JoinColumn(name = "sku_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Sku sku;

    @NotNull
    @Column(name = "sku_id", insertable = false, updatable = false)
    private Long skuId;

    @Transient
    private String skuName;

    @Transient
    private String skuCode;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Supplier supplier;

    @NotNull
    @Column(name = "supplier_id", insertable = false, updatable = false)
    private Long supplierId;

    @Transient
    private String supplierName;

    @Transient
    private String supplierCode;

    @Column(name = "danger_level")
    private Integer dangerLevel;

    @NotNull
    @Column(name = "alloc_rule")
    private AllocRuleEnum allocRule = AllocRuleEnum.Select;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Timestamp modifiedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User createdBy;

    @Transient
    private String createdByUserName;

    @Column(name = "created_by", insertable = false, updatable = false)
    @JsonIgnore
    private Long createdById;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "modified_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User modifiedBy;

    @Column(name = "modified_by", insertable = false, updatable = false)
    @JsonIgnore
    private Long modifiedById;

    @Transient
    private String modifiedByUserName;

    public SkuWarehouseMapping() {
    }

    public SkuWarehouseMapping(Long id, Long skuId, String skuName,
            String skuCode, Long supplierId, String supplierName, String supplierCode, Integer dangerLevel,
            AllocRuleEnum allocRule, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.dangerLevel = dangerLevel;
        this.allocRule = allocRule;
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

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(Integer dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public AllocRuleEnum getAllocRule() {
        return allocRule;
    }

    public void setAllocRule(AllocRuleEnum allocRule) {
        this.allocRule = allocRule;
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

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
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

    public String getModifiedByUserName() {
        return modifiedByUserName;
    }

    public void setModifiedByUserName(String modifiedByUserName) {
        this.modifiedByUserName = modifiedByUserName;
    }

    public WarehouseCompanyMapping getWarehouseCompanyMapping() {
        return warehouseCompanyMapping;
    }

    public void setWarehouseCompanyMapping(WarehouseCompanyMapping warehouseCompanyMapping) {
        this.warehouseCompanyMapping = warehouseCompanyMapping;
    }

    public Long getWarehouseCompanyMappingId() {
        return warehouseCompanyMappingId;
    }

    public void setWarehouseCompanyMappingId(Long warehouseCompanyMappingId) {
        this.warehouseCompanyMappingId = warehouseCompanyMappingId;
    }

    @Override
    public String toString() {
        return "SkuWarehouseMapping [allocRule=" + allocRule + ", createdAt=" + createdAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", dangerLevel="
                + dangerLevel + ", id=" + id + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy
                + ", modifiedById=" + modifiedById + ", modifiedByUserName=" + modifiedByUserName + ", sku=" + sku
                + ", skuCode=" + skuCode + ", skuId=" + skuId + ", skuName=" + skuName + ", supplier=" + supplier
                + ", supplierCode=" + supplierCode + ", supplierId=" + supplierId + ", supplierName=" + supplierName
                + ", warehouseCompanyMapping=" + warehouseCompanyMapping + ", warehouseCompanyMappingId="
                + warehouseCompanyMappingId + "]";
    }

    
    
   
}
