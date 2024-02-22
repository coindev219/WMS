package com.warehouse.setup.sku;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.mhetype.MheType;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.country.Country;
import com.warehouse.enums.AllocRuleEnum;
import com.warehouse.enums.ComplexityEnum;
import com.warehouse.enums.DurationTimeEnum;
import com.warehouse.enums.SerialNoValidationRuleEnum;
import com.warehouse.setup.company.packinggroup.PackingGroup;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.sku.category.SkuCategory;
import com.warehouse.setup.sku.storagecategory.SkuStorageCategory;
import com.warehouse.setup.supplier.Supplier;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sku", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "primary_company_id" }),
        @UniqueConstraint(columnNames = { "code", "primary_company_id" }), })
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 250)
    @NotBlank(message = "{app.name}")
    private String name;

    @Size(max = 100)
    @NotBlank(message = "{app.code}")
    private String code;

    @Transient
    private String mapToWarehouseIds = "";

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
    @JoinColumn(name = "sku_type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SkuType skuType;

    @NotNull
    @Column(name = "sku_type_id", insertable = false, updatable = false)
    private Long skuTypeId;

    @Transient
    private String skuTypeName;

    @Transient
    private String skuTypeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SkuCategory skuCategory;

    @Column(name = "sku_category_id", insertable = false, updatable = false)
    private Long skuCategoryId;

    @Transient
    private String skuCategoryName;

    @Transient
    private String skuCategoryCode;

    @Size(max = 100)
    private String brand;

    @Size(max = 100)
    private String model;

    @Size(max = 100)
    private String size;

    @Size(max = 100)
    private String colour;

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

    @Size(max = 100)
    @Column(name = "reference_sku")
    private String referenceSKU;

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

    @Size(max = 100)
    @Column(name = "bar_code")
    private String barCode;

    @Size(max = 100)
    @Column(name = "hs_code")
    private String HSCode;

    @Size(max = 100)
    @Column(name = "abc_code")
    private String ABCCode;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Country country;

    @Column(name = "country_id", insertable = false, updatable = false)
    private Long countryId;

    @Transient
    private String countryName;

    @Size(max = 100)
    @Column(name = "origin_type")
    private String originType;

    @ManyToOne
    @JoinColumn(name = "storage_uom_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Uom storageUom;

    @NotNull
    @Column(name = "storage_uom_id", insertable = false, updatable = false)
    private Long storageUomId;

    @Transient
    private String storageUomName;

    @Transient
    private String storageUomCode;

    @ManyToOne
    @JoinColumn(name = "billing_uom_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Uom billingUom;

    @NotNull
    @Column(name = "billing_uom_id", insertable = false, updatable = false)
    private Long billingUomId;

    @Transient
    private String billingUomName;

    @Transient
    private String billingUomCode;

    @ManyToOne
    @JoinColumn(name = "ordering_uom_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Uom orderingUom;

    @NotNull
    @Column(name = "ordering_uom_id", insertable = false, updatable = false)
    private Long orderingUomId;

    @Transient
    private String orderingUomName;

    @Transient
    private String orderingUomCode;

    @Column(name = "billing_storage")
    private Float billingStorage;

    @Column(name = "order_storage")
    private Float orderStorage;

    @Column(name = "max_qty_per_trip")
    private Float maxQtyPerTrip;

    @Column(name = "purchase_price")
    private Float purchasePrice;

    @Column(name = "sales_price")
    private Float salesPrice;

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

    @Column(name = "danger_level")
    private Integer dangerLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_restriction_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private LocationRestriction locationRestriction;

    @Column(name = "location_restriction_id", insertable = false, updatable = false)
    private Long locationRestrictionId;

    @Transient
    private String locationRestrictionName;

    @Transient
    private String locationRestrictionCode;

    private Float length;

    private Float height;

    private Float width;

    @Column(name = "gross_weight")
    private Float grossWeight;

    @Column(name = "gross_volume")
    private Float grossVolume;

    @Column(name = "gross_volume_calculate")
    private Boolean grossVolumeCalculate;

    private Boolean kit;

    @Column(name = "no_of_components")
    private Integer noOfComponents;

    @Column(name = "purchase_unit_price")
    private Float purchaseUnitPrice;

    @Column(name = "sales_unit_price")
    private Float salesUnitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_group_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PackingGroup packingGroup;

    @Column(name = "packing_group_id", insertable = false, updatable = false)
    private Long packingGroupId;

    @Transient
    private String packingGroupName;

    @Transient
    private String packingGroupCode;

    @Column(name = "manufacture_dt")
    private Boolean manufactureDt;

    @Column(name = "serial_no")
    private Boolean serialNo;

    private Boolean batch;

    @Column(name = "expiry_dt")
    private Boolean expiryDt;

    @Column(name = "min_free_shelf_life")
    private Integer minFreeShelfLife;

    @Column(name = "min_free_shelf_life_unit")
    private DurationTimeEnum minFreeShelfLifeUnit = DurationTimeEnum.Select;

    @Column(name = "total_shelf_life")
    private Integer totalShelfLife;

    @Column(name = "total_shelf_life_unit")
    private DurationTimeEnum totalShelfLifeUnit = DurationTimeEnum.Select;

    @Column(name = "alloc_rule")
    private AllocRuleEnum allocRule = AllocRuleEnum.Select;

    @Column(name = "serial_no_validation_rule")
    private SerialNoValidationRuleEnum serialNoValidationRule = SerialNoValidationRuleEnum.Select;

    private Boolean sellable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_sku_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Sku baseSku;

    @Column(name = "base_sku_id", insertable = false, updatable = false)
    private Long baseSkuId;

    @Transient
    private String baseSkuName;

    @Transient
    private String baseSkuCode;

    @Column(name = "x_factor")
    private Float xFactor;

    @Column(name = "map_all_warehouses")
    private Boolean mapAllWarehouses;

    @Column(name = "map_all_supplier")
    private Boolean mapAllSupplier;

    @Column(name = "barcode_required")
    private Boolean barcodeRequired;

    @Column(name = "min_alloc_qty")
    private Integer minAllocQty;

    @Column(name = "min_putaway_qty")
    private Integer minPutawayQty;

    @Column(name = "map_all_cost_buckets")
    private Boolean mapAllCostBuckets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_bucket_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CostBucket costBucket;

    @Column(name = "cost_bucket_id", insertable = false, updatable = false)
    private Long costBucketId;

    @Transient
    private String costBucketName;

    @Transient
    private String costBucketCode;

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

    @Column(name = "weight_capture_lot_size")
    private Float weightCaptureLotSize;

    private Boolean purchasable;

    @Size(max = 100)
    @Column(name = "loading_pattern")
    private String loadingPattern;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public SkuType getSkuType() {
        return skuType;
    }

    public void setSkuType(SkuType skuType) {
        this.skuType = skuType;
    }

    public Long getSkuTypeId() {
        return skuTypeId;
    }

    public void setSkuTypeId(Long skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    public String getSkuTypeName() {
        return skuTypeName;
    }

    public void setSkuTypeName(String skuTypeName) {
        this.skuTypeName = skuTypeName;
    }

    public String getSkuTypeCode() {
        return skuTypeCode;
    }

    public void setSkuTypeCode(String skuTypeCode) {
        this.skuTypeCode = skuTypeCode;
    }

    public SkuCategory getSkuCategory() {
        return skuCategory;
    }

    public void setSkuCategory(SkuCategory skuCategory) {
        this.skuCategory = skuCategory;
    }

    public Long getSkuCategoryId() {
        return skuCategoryId;
    }

    public void setSkuCategoryId(Long skuCategoryId) {
        this.skuCategoryId = skuCategoryId;
    }

    public String getSkuCategoryName() {
        return skuCategoryName;
    }

    public void setSkuCategoryName(String skuCategoryName) {
        this.skuCategoryName = skuCategoryName;
    }

    public String getSkuCategoryCode() {
        return skuCategoryCode;
    }

    public void setSkuCategoryCode(String skuCategoryCode) {
        this.skuCategoryCode = skuCategoryCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
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

    public String getReferenceSKU() {
        return referenceSKU;
    }

    public void setReferenceSKU(String referenceSKU) {
        this.referenceSKU = referenceSKU;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getHSCode() {
        return HSCode;
    }

    public void setHSCode(String hSCode) {
        HSCode = hSCode;
    }

    public String getABCCode() {
        return ABCCode;
    }

    public void setABCCode(String aBCCode) {
        ABCCode = aBCCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }

    public Uom getStorageUom() {
        return storageUom;
    }

    public void setStorageUom(Uom storageUom) {
        this.storageUom = storageUom;
    }

    public Long getStorageUomId() {
        return storageUomId;
    }

    public void setStorageUomId(Long storageUomId) {
        this.storageUomId = storageUomId;
    }

    public String getStorageUomName() {
        return storageUomName;
    }

    public void setStorageUomName(String storageUomName) {
        this.storageUomName = storageUomName;
    }

    public String getStorageUomCode() {
        return storageUomCode;
    }

    public void setStorageUomCode(String storageUomCode) {
        this.storageUomCode = storageUomCode;
    }

    public Uom getBillingUom() {
        return billingUom;
    }

    public void setBillingUom(Uom billingUom) {
        this.billingUom = billingUom;
    }

    public Long getBillingUomId() {
        return billingUomId;
    }

    public void setBillingUomId(Long billingUomId) {
        this.billingUomId = billingUomId;
    }

    public String getBillingUomName() {
        return billingUomName;
    }

    public void setBillingUomName(String billingUomName) {
        this.billingUomName = billingUomName;
    }

    public String getBillingUomCode() {
        return billingUomCode;
    }

    public void setBillingUomCode(String billingUomCode) {
        this.billingUomCode = billingUomCode;
    }

    public Uom getOrderingUom() {
        return orderingUom;
    }

    public void setOrderingUom(Uom orderingUom) {
        this.orderingUom = orderingUom;
    }

    public Long getOrderingUomId() {
        return orderingUomId;
    }

    public void setOrderingUomId(Long orderingUomId) {
        this.orderingUomId = orderingUomId;
    }

    public String getOrderingUomName() {
        return orderingUomName;
    }

    public void setOrderingUomName(String orderingUomName) {
        this.orderingUomName = orderingUomName;
    }

    public String getOrderingUomCode() {
        return orderingUomCode;
    }

    public void setOrderingUomCode(String orderingUomCode) {
        this.orderingUomCode = orderingUomCode;
    }

    public Float getBillingStorage() {
        return billingStorage;
    }

    public void setBillingStorage(Float billingStorage) {
        this.billingStorage = billingStorage;
    }

    public Float getOrderStorage() {
        return orderStorage;
    }

    public void setOrderStorage(Float orderStorage) {
        this.orderStorage = orderStorage;
    }

    public Float getMaxQtyPerTrip() {
        return maxQtyPerTrip;
    }

    public void setMaxQtyPerTrip(Float maxQtyPerTrip) {
        this.maxQtyPerTrip = maxQtyPerTrip;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Float getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Float salesPrice) {
        this.salesPrice = salesPrice;
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

    public Integer getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(Integer dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public LocationRestriction getLocationRestriction() {
        return locationRestriction;
    }

    public void setLocationRestriction(LocationRestriction locationRestriction) {
        this.locationRestriction = locationRestriction;
    }

    public Long getLocationRestrictionId() {
        return locationRestrictionId;
    }

    public void setLocationRestrictionId(Long locationRestrictionId) {
        this.locationRestrictionId = locationRestrictionId;
    }

    public String getLocationRestrictionName() {
        return locationRestrictionName;
    }

    public void setLocationRestrictionName(String locationRestrictionName) {
        this.locationRestrictionName = locationRestrictionName;
    }

    public String getLocationRestrictionCode() {
        return locationRestrictionCode;
    }

    public void setLocationRestrictionCode(String locationRestrictionCode) {
        this.locationRestrictionCode = locationRestrictionCode;
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

    public Float getGrossVolume() {
        return grossVolume;
    }

    public void setGrossVolume(Float grossVolume) {
        this.grossVolume = grossVolume;
    }

    public Boolean getGrossVolumeCalculate() {
        return grossVolumeCalculate;
    }

    public void setGrossVolumeCalculate(Boolean grossVolumeCalculate) {
        this.grossVolumeCalculate = grossVolumeCalculate;
    }

    public Boolean getKit() {
        return kit;
    }

    public void setKit(Boolean kit) {
        this.kit = kit;
    }

    public Integer getNoOfComponents() {
        return noOfComponents;
    }

    public void setNoOfComponents(Integer noOfComponents) {
        this.noOfComponents = noOfComponents;
    }

    public PackingGroup getPackingGroup() {
        return packingGroup;
    }

    public void setPackingGroup(PackingGroup packingGroup) {
        this.packingGroup = packingGroup;
    }

    public Long getPackingGroupId() {
        return packingGroupId;
    }

    public void setPackingGroupId(Long packingGroupId) {
        this.packingGroupId = packingGroupId;
    }

    public String getPackingGroupName() {
        return packingGroupName;
    }

    public void setPackingGroupName(String packingGroupName) {
        this.packingGroupName = packingGroupName;
    }

    public String getPackingGroupCode() {
        return packingGroupCode;
    }

    public void setPackingGroupCode(String packingGroupCode) {
        this.packingGroupCode = packingGroupCode;
    }

    public Boolean getManufactureDt() {
        return manufactureDt;
    }

    public void setManufactureDt(Boolean manufactureDt) {
        this.manufactureDt = manufactureDt;
    }

    public Boolean getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Boolean serialNo) {
        this.serialNo = serialNo;
    }

    public Boolean getBatch() {
        return batch;
    }

    public void setBatch(Boolean batch) {
        this.batch = batch;
    }

    public Boolean getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(Boolean expiryDt) {
        this.expiryDt = expiryDt;
    }

    public Integer getMinFreeShelfLife() {
        return minFreeShelfLife;
    }

    public void setMinFreeShelfLife(Integer minFreeShelfLife) {
        this.minFreeShelfLife = minFreeShelfLife;
    }

    public DurationTimeEnum getMinFreeShelfLifeUnit() {
        return minFreeShelfLifeUnit;
    }

    public void setMinFreeShelfLifeUnit(DurationTimeEnum minFreeShelfLifeUnit) {
        this.minFreeShelfLifeUnit = minFreeShelfLifeUnit;
    }

    public Integer getTotalShelfLife() {
        return totalShelfLife;
    }

    public void setTotalShelfLife(Integer totalShelfLife) {
        this.totalShelfLife = totalShelfLife;
    }

    public DurationTimeEnum getTotalShelfLifeUnit() {
        return totalShelfLifeUnit;
    }

    public void setTotalShelfLifeUnit(DurationTimeEnum totalShelfLifeUnit) {
        this.totalShelfLifeUnit = totalShelfLifeUnit;
    }

    public AllocRuleEnum getAllocRule() {
        return allocRule;
    }

    public void setAllocRule(AllocRuleEnum allocRule) {
        this.allocRule = allocRule;
    }

    public SerialNoValidationRuleEnum getSerialNoValidationRule() {
        return serialNoValidationRule;
    }

    public void setSerialNoValidationRule(SerialNoValidationRuleEnum serialNoValidationRule) {
        this.serialNoValidationRule = serialNoValidationRule;
    }

    public Boolean getSellable() {
        return sellable;
    }

    public void setSellable(Boolean sellable) {
        this.sellable = sellable;
    }

    public Sku getBaseSku() {
        return baseSku;
    }

    public void setBaseSku(Sku baseSku) {
        this.baseSku = baseSku;
    }

    public Long getBaseSkuId() {
        return baseSkuId;
    }

    public void setBaseSkuId(Long baseSkuId) {
        this.baseSkuId = baseSkuId;
    }

    public String getBaseSkuName() {
        return baseSkuName;
    }

    public void setBaseSkuName(String baseSkuName) {
        this.baseSkuName = baseSkuName;
    }

    public String getBaseSkuCode() {
        return baseSkuCode;
    }

    public void setBaseSkuCode(String baseSkuCode) {
        this.baseSkuCode = baseSkuCode;
    }

    public Float getxFactor() {
        return xFactor;
    }

    public void setxFactor(Float xFactor) {
        this.xFactor = xFactor;
    }

    public Boolean getMapAllWarehouses() {
        return mapAllWarehouses;
    }

    public void setMapAllWarehouses(Boolean mapAllWarehouses) {
        this.mapAllWarehouses = mapAllWarehouses;
    }

    public Boolean getMapAllSupplier() {
        return mapAllSupplier;
    }

    public void setMapAllSupplier(Boolean mapAllSupplier) {
        this.mapAllSupplier = mapAllSupplier;
    }

    public Boolean getBarcodeRequired() {
        return barcodeRequired;
    }

    public void setBarcodeRequired(Boolean barcodeRequired) {
        this.barcodeRequired = barcodeRequired;
    }

    public Integer getMinAllocQty() {
        return minAllocQty;
    }

    public void setMinAllocQty(Integer minAllocQty) {
        this.minAllocQty = minAllocQty;
    }

    public Integer getMinPutawayQty() {
        return minPutawayQty;
    }

    public void setMinPutawayQty(Integer minPutawayQty) {
        this.minPutawayQty = minPutawayQty;
    }

    public Boolean getMapAllCostBuckets() {
        return mapAllCostBuckets;
    }

    public void setMapAllCostBuckets(Boolean mapAllCostBuckets) {
        this.mapAllCostBuckets = mapAllCostBuckets;
    }

    public CostBucket getCostBucket() {
        return costBucket;
    }

    public void setCostBucket(CostBucket costBucket) {
        this.costBucket = costBucket;
    }

    public Long getCostBucketId() {
        return costBucketId;
    }

    public void setCostBucketId(Long costBucketId) {
        this.costBucketId = costBucketId;
    }

    public String getCostBucketName() {
        return costBucketName;
    }

    public void setCostBucketName(String costBucketName) {
        this.costBucketName = costBucketName;
    }

    public String getCostBucketCode() {
        return costBucketCode;
    }

    public void setCostBucketCode(String costBucketCode) {
        this.costBucketCode = costBucketCode;
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

    public Float getWeightCaptureLotSize() {
        return weightCaptureLotSize;
    }

    public void setWeightCaptureLotSize(Float weightCaptureLotSize) {
        this.weightCaptureLotSize = weightCaptureLotSize;
    }

    public Boolean getPurchasable() {
        return purchasable;
    }

    public void setPurchasable(Boolean purchasable) {
        this.purchasable = purchasable;
    }

    public String getLoadingPattern() {
        return loadingPattern;
    }

    public void setLoadingPattern(String loadingPattern) {
        this.loadingPattern = loadingPattern;
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

    public String getMapToWarehouseIds() {
        return mapToWarehouseIds;
    }

    public void setMapToWarehouseIds(String mapToWarehouseIds) {
        this.mapToWarehouseIds = mapToWarehouseIds;
    }

    public Float getPurchaseUnitPrice() {
        return purchaseUnitPrice;
    }

    public void setPurchaseUnitPrice(Float purchaseUnitPrice) {
        this.purchaseUnitPrice = purchaseUnitPrice;
    }

    public Float getSalesUnitPrice() {
        return salesUnitPrice;
    }

    public void setSalesUnitPrice(Float salesUnitPrice) {
        this.salesUnitPrice = salesUnitPrice;
    }

    public Sku() {
    }

    public Sku(Long id, String name, String code, Long primaryCompanyId, String primaryCompanyName,
            String primaryCompanyCode, Long skuTypeId, String skuTypeName, String skuTypeCode, Long skuCategoryId,
            String skuCategoryName, String skuCategoryCode, Long storageUomId, String storageUomName,
            String storageUomCode, Long billingUomId, String billingUomName, String billingUomCode, Integer noOfComponents,
            Float purchaseUnitPrice, Float salesUnitPrice, Boolean manufactureDt, Boolean serialNo, Boolean expiryDt,
            Date createdAt, Date modifiedAt, String createdByUserName,String modifiedByUserName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.skuTypeId = skuTypeId;
        this.skuTypeName = skuTypeName;
        this.skuTypeCode = skuTypeCode;
        this.skuCategoryId = skuCategoryId;
        this.skuCategoryName = skuCategoryName;
        this.skuCategoryCode = skuCategoryCode;
        this.storageUomId = storageUomId;
        this.storageUomName = storageUomName;
        this.storageUomCode = storageUomCode;
        this.billingUomId = billingUomId;
        this.billingUomName = billingUomName;
        this.billingUomCode = billingUomCode;
        this.noOfComponents = noOfComponents;
        this.purchaseUnitPrice = purchaseUnitPrice;
        this.salesUnitPrice = salesUnitPrice;
        this.manufactureDt = manufactureDt;
        this.serialNo = serialNo;
        this.expiryDt = expiryDt;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public Sku(Long id, String name, String code, Long primaryCompanyId, String primaryCompanyName,
            String primaryCompanyCode, Long skuTypeId, String skuTypeName, String skuTypeCode, Long skuCategoryId,
            String skuCategoryName, String skuCategoryCode, String brand, String model, String size, String colour,
            Long storageCategoryId, String storageCategoryName, String storageCategoryCode, String referenceSKU,
            Long supplierId, String supplierName, String supplierCode, String barCode, String hSCode, String aBCCode,
            Long countryId, String countryName, String originType, Long storageUomId, String storageUomName,
            String storageUomCode, Long billingUomId, String billingUomName, String billingUomCode, Long orderingUomId,
            String orderingUomName, String orderingUomCode, Float billingStorage, Float orderStorage,
            Float maxQtyPerTrip, Float purchasePrice, Float salesPrice, Float purchaseLotSize, Float salesLotSize,
            Integer minPoQty, Integer maxPoQty, Integer minSoQty, Integer maxSoQty, Integer dangerLevel,
            Long locationRestrictionId, String locationRestrictionName, String locationRestrictionCode, Float length,
            Float height, Float width, Float grossWeight, Float grossVolume, Boolean grossVolumeCalculate, Boolean kit,
            Integer noOfComponents, Long packingGroupId, String packingGroupName, String packingGroupCode,
            Boolean manufactureDt, Boolean serialNo, Boolean batch, Boolean expiryDt, Integer minFreeShelfLife,
            DurationTimeEnum minFreeShelfLifeUnit, Integer totalShelfLife, DurationTimeEnum totalShelfLifeUnit,
            AllocRuleEnum allocRule, SerialNoValidationRuleEnum serialNoValidationRule, Boolean sellable,
            Long baseSkuId, String baseSkuName, String baseSkuCode, Float xFactor, Boolean mapAllWarehouses,
            Boolean mapAllSupplier, Boolean barcodeRequired, Integer minAllocQty, Integer minPutawayQty,
            Boolean mapAllCostBuckets, Long costBucketId, String costBucketName, String costBucketCode, Float netWeight,
            Boolean weightRequired, ComplexityEnum inComplexity, ComplexityEnum outComplexity, Long mheTypeId,
            String mheTypeName, String mheTypeCode, Float weightCaptureLotSize, Boolean purchasable,
            String loadingPattern, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.skuTypeId = skuTypeId;
        this.skuTypeName = skuTypeName;
        this.skuTypeCode = skuTypeCode;
        this.skuCategoryId = skuCategoryId;
        this.skuCategoryName = skuCategoryName;
        this.skuCategoryCode = skuCategoryCode;
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.colour = colour;
        this.storageCategoryId = storageCategoryId;
        this.storageCategoryName = storageCategoryName;
        this.storageCategoryCode = storageCategoryCode;
        this.referenceSKU = referenceSKU;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.barCode = barCode;
        HSCode = hSCode;
        ABCCode = aBCCode;
        this.countryId = countryId;
        this.countryName = countryName;
        this.originType = originType;
        this.storageUomId = storageUomId;
        this.storageUomName = storageUomName;
        this.storageUomCode = storageUomCode;
        this.billingUomId = billingUomId;
        this.billingUomName = billingUomName;
        this.billingUomCode = billingUomCode;
        this.orderingUomId = orderingUomId;
        this.orderingUomName = orderingUomName;
        this.orderingUomCode = orderingUomCode;
        this.billingStorage = billingStorage;
        this.orderStorage = orderStorage;
        this.maxQtyPerTrip = maxQtyPerTrip;
        this.purchasePrice = purchasePrice;
        this.salesPrice = salesPrice;
        this.purchaseLotSize = purchaseLotSize;
        this.salesLotSize = salesLotSize;
        this.minPoQty = minPoQty;
        this.maxPoQty = maxPoQty;
        this.minSoQty = minSoQty;
        this.maxSoQty = maxSoQty;
        this.dangerLevel = dangerLevel;
        this.locationRestrictionId = locationRestrictionId;
        this.locationRestrictionName = locationRestrictionName;
        this.locationRestrictionCode = locationRestrictionCode;
        this.length = length;
        this.height = height;
        this.width = width;
        this.grossWeight = grossWeight;
        this.grossVolume = grossVolume;
        this.grossVolumeCalculate = grossVolumeCalculate;
        this.kit = kit;
        this.noOfComponents = noOfComponents;
        this.packingGroupId = packingGroupId;
        this.packingGroupName = packingGroupName;
        this.packingGroupCode = packingGroupCode;
        this.manufactureDt = manufactureDt;
        this.serialNo = serialNo;
        this.batch = batch;
        this.expiryDt = expiryDt;
        this.minFreeShelfLife = minFreeShelfLife;
        this.minFreeShelfLifeUnit = minFreeShelfLifeUnit;
        this.totalShelfLife = totalShelfLife;
        this.totalShelfLifeUnit = totalShelfLifeUnit;
        this.allocRule = allocRule;
        this.serialNoValidationRule = serialNoValidationRule;
        this.sellable = sellable;
        this.baseSkuId = baseSkuId;
        this.baseSkuName = baseSkuName;
        this.baseSkuCode = baseSkuCode;
        this.xFactor = xFactor;
        this.mapAllWarehouses = mapAllWarehouses;
        this.mapAllSupplier = mapAllSupplier;
        this.barcodeRequired = barcodeRequired;
        this.minAllocQty = minAllocQty;
        this.minPutawayQty = minPutawayQty;
        this.mapAllCostBuckets = mapAllCostBuckets;
        this.costBucketId = costBucketId;
        this.costBucketName = costBucketName;
        this.costBucketCode = costBucketCode;
        this.netWeight = netWeight;
        this.weightRequired = weightRequired;
        this.inComplexity = inComplexity;
        this.outComplexity = outComplexity;
        this.mheTypeId = mheTypeId;
        this.mheTypeName = mheTypeName;
        this.mheTypeCode = mheTypeCode;
        this.weightCaptureLotSize = weightCaptureLotSize;
        this.purchasable = purchasable;
        this.loadingPattern = loadingPattern;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    @Override
    public String toString() {
        return "Sku [ABCCode=" + ABCCode + ", HSCode=" + HSCode + ", allocRule=" + allocRule + ", barCode=" + barCode
                + ", barcodeRequired=" + barcodeRequired + ", baseSku=" + baseSku + ", baseSkuCode=" + baseSkuCode
                + ", baseSkuId=" + baseSkuId + ", baseSkuName=" + baseSkuName + ", batch=" + batch + ", billingStorage="
                + billingStorage + ", billingUom=" + billingUom + ", billingUomCode=" + billingUomCode
                + ", billingUomId=" + billingUomId + ", billingUomName=" + billingUomName + ", brand=" + brand
                + ", code=" + code + ", colour=" + colour + ", costBucket=" + costBucket + ", costBucketCode="
                + costBucketCode + ", costBucketId=" + costBucketId + ", costBucketName=" + costBucketName
                + ", country=" + country + ", countryId=" + countryId + ", countryName=" + countryName + ", createdAt="
                + createdAt + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", dangerLevel=" + dangerLevel + ", expiryDt=" + expiryDt + ", grossVolume="
                + grossVolume + ", grossVolumeCalculate=" + grossVolumeCalculate + ", grossWeight=" + grossWeight
                + ", height=" + height + ", id=" + id + ", inComplexity=" + inComplexity + ", kit=" + kit + ", length="
                + length + ", loadingPattern=" + loadingPattern + ", locationRestriction=" + locationRestriction
                + ", locationRestrictionCode=" + locationRestrictionCode + ", locationRestrictionId="
                + locationRestrictionId + ", locationRestrictionName=" + locationRestrictionName + ", manufactureDt="
                + manufactureDt + ", mapAllCostBuckets=" + mapAllCostBuckets + ", mapAllSupplier=" + mapAllSupplier
                + ", mapAllWarehouses=" + mapAllWarehouses + ", maxPoQty=" + maxPoQty + ", maxQtyPerTrip="
                + maxQtyPerTrip + ", maxSoQty=" + maxSoQty + ", mheType=" + mheType + ", mheTypeCode=" + mheTypeCode
                + ", mheTypeId=" + mheTypeId + ", mheTypeName=" + mheTypeName + ", minAllocQty=" + minAllocQty
                + ", minFreeShelfLife=" + minFreeShelfLife + ", minFreeShelfLifeUnit=" + minFreeShelfLifeUnit
                + ", minPoQty=" + minPoQty + ", minPutawayQty=" + minPutawayQty + ", minSoQty=" + minSoQty + ", model="
                + model + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", modifiedByUserName=" + modifiedByUserName + ", name=" + name + ", netWeight=" + netWeight
                + ", noOfComponents=" + noOfComponents + ", orderStorage=" + orderStorage + ", orderingUom="
                + orderingUom + ", orderingUomCode=" + orderingUomCode + ", orderingUomId=" + orderingUomId
                + ", orderingUomName=" + orderingUomName + ", originType=" + originType + ", outComplexity="
                + outComplexity + ", packingGroup=" + packingGroup + ", packingGroupCode=" + packingGroupCode
                + ", packingGroupId=" + packingGroupId + ", packingGroupName=" + packingGroupName + ", primaryCompany="
                + primaryCompany + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId="
                + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", purchasable=" + purchasable
                + ", purchaseLotSize=" + purchaseLotSize + ", purchasePrice=" + purchasePrice + ", referenceSKU="
                + referenceSKU + ", salesLotSize=" + salesLotSize + ", salesPrice=" + salesPrice + ", sellable="
                + sellable + ", serialNo=" + serialNo + ", serialNoValidationRule=" + serialNoValidationRule + ", size="
                + size + ", skuCategory=" + skuCategory + ", skuCategoryCode=" + skuCategoryCode + ", skuCategoryId="
                + skuCategoryId + ", skuCategoryName=" + skuCategoryName + ", skuType=" + skuType + ", skuTypeCode="
                + skuTypeCode + ", skuTypeId=" + skuTypeId + ", skuTypeName=" + skuTypeName + ", storageCategory="
                + storageCategory + ", storageCategoryCode=" + storageCategoryCode + ", storageCategoryId="
                + storageCategoryId + ", storageCategoryName=" + storageCategoryName + ", storageUom=" + storageUom
                + ", storageUomCode=" + storageUomCode + ", storageUomId=" + storageUomId + ", storageUomName="
                + storageUomName + ", supplier=" + supplier + ", supplierCode=" + supplierCode + ", supplierId="
                + supplierId + ", supplierName=" + supplierName + ", totalShelfLife=" + totalShelfLife
                + ", totalShelfLifeUnit=" + totalShelfLifeUnit + ", weightCaptureLotSize=" + weightCaptureLotSize
                + ", weightRequired=" + weightRequired + ", width=" + width + ", xFactor=" + xFactor + "]";
    }

}
