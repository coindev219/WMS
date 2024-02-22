package com.warehouse.setup.sku.uommapping;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import com.warehouse.additionalsetup.mhetype.MheType;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.enums.ComplexityEnum;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.sku.Sku;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategory;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sku_uom_mapping", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "uom_id", "sku_id" }) })
public class SkuUomMapping {

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
    @JoinColumn(name = "uom_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Uom uom;

    @NotNull
    @Column(name = "uom_id", insertable = false, updatable = false)
    private Long uomId;

    @Transient
    private String uomName;

    @Transient
    private String uomCode;

    @Column(name = "multiplication_factor_to_storage_uom")
    private Float multiplicationFactorToConvertStorageUom;

    @Column(name = "is_default_sales_ordering_uom")
    private Boolean isDefaultSalesOrderingUom;

    @Column(name = "is_default_sales_billing_uom")
    private Boolean isDefaultSalesBillingUom;

    @Column(name = "is_default_purchase_ordering_uom")
    private Boolean isDefaultPurchaseOrderingUom;

    @Column(name = "is_default_purchase_billing_uom")
    private Boolean isDefaultPurchaseBillingUom;

    private String barcode;
    
    @Column(name = "purchase_lot_size")
    private Float purchaseLotSize;

    @Column(name = "sales_lot_size")
    private Float salesLotSize;

    @Column(name = "min_po_qty")
    private Integer minPoQty;

    @Column(name = "max_po_qty")
    private Integer maxPoQty;

    @Column(name = "min_so_qty")
    private Integer minSoQty;

    @Column(name = "max_so_qty")
    private Integer maxSoQty;

    @Column(name = "net_weight")
    private Float netWeight;

    @Column(name = "weight_required")
    private Boolean weightRequired;

    @Column(name = "in_complexity")
    private ComplexityEnum inComplexity = ComplexityEnum.Select;

    @Column(name = "out_complexity")
    private ComplexityEnum outComplexity = ComplexityEnum.Select;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mhe_type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private MheType mheType;

    @Column(name = "mhe_type_id", insertable = false, updatable = false)
    private Long mheTypeId;

    @Transient
    private String mheTypeName;

    @Transient
    private String mheTypeCode;

    private Float length;

    private Float height;

    private Float width;

    @Column(name = "gross_weight")
    private Float grossWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SkuStorageCategory storageCategory;

    @Column(name = "storage_category_id", insertable = false, updatable = false)
    private Long storageCategoryId;

    @Transient
    private String storageCategoryName;

    @Transient
    private String storageCategoryCode;

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

    public SkuUomMapping() {
    }

    
    public SkuUomMapping(Long id, Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode,
            Long skuId, String skuName, String skuCode, Long uomId, String uomName, String uomCode,
            Float multiplicationFactorToConvertStorageUom, Boolean isDefaultSalesOrderingUom,
            Boolean isDefaultSalesBillingUom, Boolean isDefaultPurchaseOrderingUom, Boolean isDefaultPurchaseBillingUom,
            String barcode, Float purchaseLotSize, Float salesLotSize, Integer minPoQty, Integer maxPoQty,
            Integer minSoQty, Integer maxSoQty, Float netWeight, Boolean weightRequired, ComplexityEnum inComplexity,
            ComplexityEnum outComplexity, Long mheTypeId, String mheTypeName, String mheTypeCode, Float length,
            Float height, Float width, Float grossWeight, Long storageCategoryId, String storageCategoryName,
            String storageCategoryCode, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.uomId = uomId;
        this.uomName = uomName;
        this.uomCode = uomCode;
        this.multiplicationFactorToConvertStorageUom = multiplicationFactorToConvertStorageUom;
        this.isDefaultSalesOrderingUom = isDefaultSalesOrderingUom;
        this.isDefaultSalesBillingUom = isDefaultSalesBillingUom;
        this.isDefaultPurchaseOrderingUom = isDefaultPurchaseOrderingUom;
        this.isDefaultPurchaseBillingUom = isDefaultPurchaseBillingUom;
        this.barcode = barcode;
        this.purchaseLotSize = purchaseLotSize;
        this.salesLotSize = salesLotSize;
        this.minPoQty = minPoQty;
        this.maxPoQty = maxPoQty;
        this.minSoQty = minSoQty;
        this.maxSoQty = maxSoQty;
        this.netWeight = netWeight;
        this.weightRequired = weightRequired;
        this.inComplexity = inComplexity;
        this.outComplexity = outComplexity;
        this.mheTypeId = mheTypeId;
        this.mheTypeName = mheTypeName;
        this.mheTypeCode = mheTypeCode;
        this.length = length;
        this.height = height;
        this.width = width;
        this.grossWeight = grossWeight;
        this.storageCategoryId = storageCategoryId;
        this.storageCategoryName = storageCategoryName;
        this.storageCategoryCode = storageCategoryCode;
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


    public Float getMultiplicationFactorToConvertStorageUom() {
        return multiplicationFactorToConvertStorageUom;
    }


    public void setMultiplicationFactorToConvertStorageUom(Float multiplicationFactorToConvertStorageUom) {
        this.multiplicationFactorToConvertStorageUom = multiplicationFactorToConvertStorageUom;
    }


    public Boolean getIsDefaultSalesOrderingUom() {
        return isDefaultSalesOrderingUom;
    }


    public void setIsDefaultSalesOrderingUom(Boolean isDefaultSalesOrderingUom) {
        this.isDefaultSalesOrderingUom = isDefaultSalesOrderingUom;
    }


    public Boolean getIsDefaultSalesBillingUom() {
        return isDefaultSalesBillingUom;
    }


    public void setIsDefaultSalesBillingUom(Boolean isDefaultSalesBillingUom) {
        this.isDefaultSalesBillingUom = isDefaultSalesBillingUom;
    }


    public Boolean getIsDefaultPurchaseOrderingUom() {
        return isDefaultPurchaseOrderingUom;
    }


    public void setIsDefaultPurchaseOrderingUom(Boolean isDefaultPurchaseOrderingUom) {
        this.isDefaultPurchaseOrderingUom = isDefaultPurchaseOrderingUom;
    }


    public Boolean getIsDefaultPurchaseBillingUom() {
        return isDefaultPurchaseBillingUom;
    }


    public void setIsDefaultPurchaseBillingUom(Boolean isDefaultPurchaseBillingUom) {
        this.isDefaultPurchaseBillingUom = isDefaultPurchaseBillingUom;
    }


    public String getBarcode() {
        return barcode;
    }


    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    public Float getPurchaseLotSize() {
        return purchaseLotSize;
    }


    public void setPurchaseLotSize(Float purchaseLotSize) {
        this.purchaseLotSize = purchaseLotSize;
    }


    public Float getSalesLotSize() {
        return salesLotSize;
    }


    public void setSalesLotSize(Float salesLotSize) {
        this.salesLotSize = salesLotSize;
    }


    public Integer getMinPoQty() {
        return minPoQty;
    }


    public void setMinPoQty(Integer minPoQty) {
        this.minPoQty = minPoQty;
    }


    public Integer getMaxPoQty() {
        return maxPoQty;
    }


    public void setMaxPoQty(Integer maxPoQty) {
        this.maxPoQty = maxPoQty;
    }


    public Integer getMinSoQty() {
        return minSoQty;
    }


    public void setMinSoQty(Integer minSoQty) {
        this.minSoQty = minSoQty;
    }


    public Integer getMaxSoQty() {
        return maxSoQty;
    }


    public void setMaxSoQty(Integer maxSoQty) {
        this.maxSoQty = maxSoQty;
    }


    public Float getNetWeight() {
        return netWeight;
    }


    public void setNetWeight(Float netWeight) {
        this.netWeight = netWeight;
    }


    public Boolean getWeightRequired() {
        return weightRequired;
    }


    public void setWeightRequired(Boolean weightRequired) {
        this.weightRequired = weightRequired;
    }


    public ComplexityEnum getInComplexity() {
        return inComplexity;
    }


    public void setInComplexity(ComplexityEnum inComplexity) {
        this.inComplexity = inComplexity;
    }


    public ComplexityEnum getOutComplexity() {
        return outComplexity;
    }


    public void setOutComplexity(ComplexityEnum outComplexity) {
        this.outComplexity = outComplexity;
    }


    public MheType getMheType() {
        return mheType;
    }


    public void setMheType(MheType mheType) {
        this.mheType = mheType;
    }


    public Long getMheTypeId() {
        return mheTypeId;
    }


    public void setMheTypeId(Long mheTypeId) {
        this.mheTypeId = mheTypeId;
    }


    public String getMheTypeName() {
        return mheTypeName;
    }


    public void setMheTypeName(String mheTypeName) {
        this.mheTypeName = mheTypeName;
    }


    public String getMheTypeCode() {
        return mheTypeCode;
    }


    public void setMheTypeCode(String mheTypeCode) {
        this.mheTypeCode = mheTypeCode;
    }


    public Float getLength() {
        return length;
    }


    public void setLength(Float length) {
        this.length = length;
    }


    public Float getHeight() {
        return height;
    }


    public void setHeight(Float height) {
        this.height = height;
    }


    public Float getWidth() {
        return width;
    }


    public void setWidth(Float width) {
        this.width = width;
    }


    public Float getGrossWeight() {
        return grossWeight;
    }


    public void setGrossWeight(Float grossWeight) {
        this.grossWeight = grossWeight;
    }


    public SkuStorageCategory getStorageCategory() {
        return storageCategory;
    }


    public void setStorageCategory(SkuStorageCategory storageCategory) {
        this.storageCategory = storageCategory;
    }


    public Long getStorageCategoryId() {
        return storageCategoryId;
    }


    public void setStorageCategoryId(Long storageCategoryId) {
        this.storageCategoryId = storageCategoryId;
    }


    public String getStorageCategoryName() {
        return storageCategoryName;
    }


    public void setStorageCategoryName(String storageCategoryName) {
        this.storageCategoryName = storageCategoryName;
    }


    public String getStorageCategoryCode() {
        return storageCategoryCode;
    }


    public void setStorageCategoryCode(String storageCategoryCode) {
        this.storageCategoryCode = storageCategoryCode;
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
        return "SkuUomMapping [barcode=" + barcode + ", createdAt=" + createdAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", grossWeight="
                + grossWeight + ", height=" + height + ", id=" + id + ", inComplexity=" + inComplexity
                + ", isDefaultPurchaseBillingUom=" + isDefaultPurchaseBillingUom + ", isDefaultPurchaseOrderingUom="
                + isDefaultPurchaseOrderingUom + ", isDefaultSalesBillingUom=" + isDefaultSalesBillingUom
                + ", isDefaultSalesOrderingUom=" + isDefaultSalesOrderingUom + ", length=" + length + ", maxPoQty="
                + maxPoQty + ", maxSoQty=" + maxSoQty + ", mheType=" + mheType + ", mheTypeCode=" + mheTypeCode
                + ", mheTypeId=" + mheTypeId + ", mheTypeName=" + mheTypeName + ", minPoQty=" + minPoQty + ", minSoQty="
                + minSoQty + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById="
                + modifiedById + ", modifiedByUserName=" + modifiedByUserName
                + ", multiplicationFactorToConvertStorageUom=" + multiplicationFactorToConvertStorageUom
                + ", netWeight=" + netWeight + ", outComplexity=" + outComplexity + ", primaryCompany=" + primaryCompany
                + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId=" + primaryCompanyId
                + ", primaryCompanyName=" + primaryCompanyName + ", purchaseLotSize=" + purchaseLotSize
                + ", salesLotSize=" + salesLotSize + ", sku=" + sku + ", skuCode=" + skuCode + ", skuId=" + skuId
                + ", skuName=" + skuName + ", storageCategory=" + storageCategory + ", storageCategoryCode="
                + storageCategoryCode + ", storageCategoryId=" + storageCategoryId + ", storageCategoryName="
                + storageCategoryName + ", uom=" + uom + ", uomCode=" + uomCode + ", uomId=" + uomId + ", uomName="
                + uomName + ", weightRequired=" + weightRequired + ", width=" + width + "]";
    }


    
    
    

}
