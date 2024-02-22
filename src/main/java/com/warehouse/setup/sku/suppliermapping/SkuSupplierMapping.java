package com.warehouse.setup.sku.suppliermapping;

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
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.enums.TradeTermEnum;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.supplier.Supplier;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sku_supplier_mapping", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "supplier_id", "primary_company_id", "sku_id" }) })
public class SkuSupplierMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "primary_company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PrimaryCompany primaryCompany;

    @NotNull
    @Column(name = "primary_company_id", insertable = false, updatable = false)
    private Long primaryCompanyId;

    @Transient
    private String primaryCompanyName;

    @Transient
    private String primaryCompanyCode;

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

    @Column(name = "purchase_price")
    private Float purchasePrice;

    @NotNull
    @Column(name = "trade_term")
    private TradeTermEnum tradeTerm = TradeTermEnum.Select;

    @ManyToOne
    @JoinColumn(name = "uom_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Uom uom;

    @Column(name = "uom_id", insertable = false, updatable = false)
    private Long uomId;

    @Transient
    private String uomName;

    @Transient
    private String uomCode;

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

    public SkuSupplierMapping() {
    }

    public SkuSupplierMapping(Long id, Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode,
            Long skuId, String skuName, String skuCode, Long supplierId, String supplierName, String supplierCode,
            Float purchasePrice, TradeTermEnum tradeTerm, Long uomId, String uomName, String uomCode, Date createdAt,
            Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.purchasePrice = purchasePrice;
        this.tradeTerm = tradeTerm;
        this.uomId = uomId;
        this.uomName = uomName;
        this.uomCode = uomCode;
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

    public PrimaryCompany getPrimaryCompany() {
        return primaryCompany;
    }

    public void setPrimaryCompany(PrimaryCompany primaryCompany) {
        this.primaryCompany = primaryCompany;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
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

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Long getSkuId() {
        return this.skuId;
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

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public TradeTermEnum getTradeTerm() {
        return tradeTerm;
    }

    public void setTradeTerm(TradeTermEnum tradeTerm) {
        this.tradeTerm = tradeTerm;
    }

    public Uom getUom() {
        return uom;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
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

    @Override
    public String toString() {
        return "SkuSupplierMapping [SkuId=" + skuId + ", createdAt=" + createdAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", id=" + id
                + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", modifiedByUserName=" + modifiedByUserName + ", primaryCompany=" + primaryCompany
                + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId=" + primaryCompanyId
                + ", primaryCompanyName=" + primaryCompanyName + ", purchasePrice=" + purchasePrice + ", sku=" + sku
                + ", skuCode=" + skuCode + ", skuName=" + skuName + ", supplier=" + supplier + ", supplierCode="
                + supplierCode + ", supplierId=" + supplierId + ", supplierName=" + supplierName + ", tradeTerm="
                + tradeTerm + ", uom=" + uom + ", uomCode=" + uomCode + ", uomId=" + uomId + ", uomName=" + uomName
                + "]";
    }

}
