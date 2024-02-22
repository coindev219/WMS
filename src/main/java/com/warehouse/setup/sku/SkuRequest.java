package com.warehouse.setup.sku;

import com.warehouse.enums.AllocRuleEnum;
import com.warehouse.enums.ComplexityEnum;
import com.warehouse.enums.DurationTimeEnum;
import com.warehouse.enums.SerialNoValidationRuleEnum;

public class SkuRequest {

    private Long id;

    private String name;

    private String code;

    private Long primaryCompanyId;

    private Long skuTypeId;

    private Long skuCategoryId;

    private String brand;

    private String model;

    private String size;

    private String colour;

    private Long storageCategoryId;

    private String referenceSKU;

    private Long supplierId;

    private String barCode;

    private String HSCode;

    private String ABCCode;

    private Long countryId;

    private String originType;

    private Long storageUomId;

    private Long billingUomId;

    private Long orderingUomId;

    private Float billingStorage;

    private Float orderStorage;

    private Float maxQtyPerTrip;

    private Float purchasePrice;

    private Float salesPrice;

    private Float purchaseLotSize;

    private Float salesLotSize;

    private Integer minPoQty;

    private Integer maxPoQty;

    private Integer minSoQty;

    private Integer maxSoQty;

    private Integer dangerLevel;

    private Long locationRestrictionId;

    private Float length;

    private Float height;

    private Float width;

    private Float grossWeight;

    private Float grossVolume;

    private String grossVolumeCalculate;

    private String kit;

    private Integer noOfComponents;

    private Long packingGroupId;

    private String manufactureDt;

    private String serialNo;

    private String batch;

    private String expiryDt;

    private Integer minFreeShelfLife;

    private DurationTimeEnum minFreeShelfLifeUnit;

    private Integer totalShelfLife;

    private DurationTimeEnum totalShelfLifeUnit;

    private AllocRuleEnum allocRule;

    private SerialNoValidationRuleEnum serialNoValidationRule;

    private String sellable;

    private Long baseSkuId;

    private Float xFactor;

    private String mapAllWarehouses;

    private String mapAllSupplier;

    private String barcodeRequired;

    private Integer minAllocQty;

    private Integer minPutawayQty;

    private String mapAllCostBuckets;

    private Long costBucketId;

    private Float netWeight;

    private String weightRequired;

    private ComplexityEnum inComplexity;

    private ComplexityEnum outComplexity;

    private Long mheTypeId;

    private Float weightCaptureLotSize;

    private String purchasable;

    private String loadingPattern;

    private String searchFor;

    private String createdFrom;

    private String createdTo;

    private String modifiedFrom;

    private String modifiedTo;

    private Long kitSkuId;

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

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public Long getSkuTypeId() {
        return skuTypeId;
    }

    public void setSkuTypeId(Long skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    public Long getSkuCategoryId() {
        return skuCategoryId;
    }

    public void setSkuCategoryId(Long skuCategoryId) {
        this.skuCategoryId = skuCategoryId;
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

    public Long getStorageCategoryId() {
        return storageCategoryId;
    }

    public void setStorageCategoryId(Long storageCategoryId) {
        this.storageCategoryId = storageCategoryId;
    }

    public String getReferenceSKU() {
        return referenceSKU;
    }

    public void setReferenceSKU(String referenceSKU) {
        this.referenceSKU = referenceSKU;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }

    public Long getStorageUomId() {
        return storageUomId;
    }

    public void setStorageUomId(Long storageUomId) {
        this.storageUomId = storageUomId;
    }

    public Long getBillingUomId() {
        return billingUomId;
    }

    public void setBillingUomId(Long billingUomId) {
        this.billingUomId = billingUomId;
    }

    public Long getOrderingUomId() {
        return orderingUomId;
    }

    public void setOrderingUomId(Long orderingUomId) {
        this.orderingUomId = orderingUomId;
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

    public Long getLocationRestrictionId() {
        return locationRestrictionId;
    }

    public void setLocationRestrictionId(Long locationRestrictionId) {
        this.locationRestrictionId = locationRestrictionId;
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

    public String getGrossVolumeCalculate() {
        return grossVolumeCalculate;
    }

    public void setGrossVolumeCalculate(String grossVolumeCalculate) {
        this.grossVolumeCalculate = grossVolumeCalculate;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public Integer getNoOfComponents() {
        return noOfComponents;
    }

    public void setNoOfComponents(Integer noOfComponents) {
        this.noOfComponents = noOfComponents;
    }

    public Long getPackingGroupId() {
        return packingGroupId;
    }

    public void setPackingGroupId(Long packingGroupId) {
        this.packingGroupId = packingGroupId;
    }

    public String getManufactureDt() {
        return manufactureDt;
    }

    public void setManufactureDt(String manufactureDt) {
        this.manufactureDt = manufactureDt;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(String expiryDt) {
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

    public String getSellable() {
        return sellable;
    }

    public void setSellable(String sellable) {
        this.sellable = sellable;
    }

    public Long getBaseSkuId() {
        return baseSkuId;
    }

    public void setBaseSkuId(Long baseSkuId) {
        this.baseSkuId = baseSkuId;
    }

    public Float getxFactor() {
        return xFactor;
    }

    public void setxFactor(Float xFactor) {
        this.xFactor = xFactor;
    }

    public String getMapAllWarehouses() {
        return mapAllWarehouses;
    }

    public void setMapAllWarehouses(String mapAllWarehouses) {
        this.mapAllWarehouses = mapAllWarehouses;
    }

    public String getMapAllSupplier() {
        return mapAllSupplier;
    }

    public void setMapAllSupplier(String mapAllSupplier) {
        this.mapAllSupplier = mapAllSupplier;
    }

    public String getBarcodeRequired() {
        return barcodeRequired;
    }

    public void setBarcodeRequired(String barcodeRequired) {
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

    public String getMapAllCostBuckets() {
        return mapAllCostBuckets;
    }

    public void setMapAllCostBuckets(String mapAllCostBuckets) {
        this.mapAllCostBuckets = mapAllCostBuckets;
    }

    public Long getCostBucketId() {
        return costBucketId;
    }

    public void setCostBucketId(Long costBucketId) {
        this.costBucketId = costBucketId;
    }

    public Float getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Float netWeight) {
        this.netWeight = netWeight;
    }

    public String getWeightRequired() {
        return weightRequired;
    }

    public void setWeightRequired(String weightRequired) {
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

    public Long getMheTypeId() {
        return mheTypeId;
    }

    public void setMheTypeId(Long mheTypeId) {
        this.mheTypeId = mheTypeId;
    }

    public Float getWeightCaptureLotSize() {
        return weightCaptureLotSize;
    }

    public void setWeightCaptureLotSize(Float weightCaptureLotSize) {
        this.weightCaptureLotSize = weightCaptureLotSize;
    }

    public String getPurchasable() {
        return purchasable;
    }

    public void setPurchasable(String purchasable) {
        this.purchasable = purchasable;
    }

    public String getLoadingPattern() {
        return loadingPattern;
    }

    public void setLoadingPattern(String loadingPattern) {
        this.loadingPattern = loadingPattern;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(String createdTo) {
        this.createdTo = createdTo;
    }

    public String getModifiedFrom() {
        return modifiedFrom;
    }

    public void setModifiedFrom(String modifiedFrom) {
        this.modifiedFrom = modifiedFrom;
    }

    public String getModifiedTo() {
        return modifiedTo;
    }

    public void setModifiedTo(String modifiedTo) {
        this.modifiedTo = modifiedTo;
    }

    public Long getKitSkuId() {
        return kitSkuId;
    }

    public void setKitSkuId(Long kitSkuId) {
        this.kitSkuId = kitSkuId;
    }

    @Override
    public String toString() {
        return "SkuRequest [ABCCode=" + ABCCode + ", HSCode=" + HSCode + ", allocRule=" + allocRule + ", barCode="
                + barCode + ", barcodeRequired=" + barcodeRequired + ", baseSkuId=" + baseSkuId + ", batch=" + batch
                + ", billingStorage=" + billingStorage + ", billingUomId=" + billingUomId + ", brand=" + brand
                + ", code=" + code + ", colour=" + colour + ", costBucketId=" + costBucketId + ", countryId="
                + countryId + ", createdFrom=" + createdFrom + ", createdTo=" + createdTo + ", dangerLevel="
                + dangerLevel + ", expiryDt=" + expiryDt + ", grossVolume=" + grossVolume + ", grossVolumeCalculate="
                + grossVolumeCalculate + ", grossWeight=" + grossWeight + ", height=" + height + ", id=" + id
                + ", inComplexity=" + inComplexity + ", kit=" + kit + ", kitSkuId=" + kitSkuId + ", length=" + length
                + ", loadingPattern=" + loadingPattern + ", locationRestrictionId=" + locationRestrictionId
                + ", manufactureDt=" + manufactureDt + ", mapAllCostBuckets=" + mapAllCostBuckets + ", mapAllSupplier="
                + mapAllSupplier + ", mapAllWarehouses=" + mapAllWarehouses + ", maxPoQty=" + maxPoQty
                + ", maxQtyPerTrip=" + maxQtyPerTrip + ", maxSoQty=" + maxSoQty + ", mheTypeId=" + mheTypeId
                + ", minAllocQty=" + minAllocQty + ", minFreeShelfLife=" + minFreeShelfLife + ", minFreeShelfLifeUnit="
                + minFreeShelfLifeUnit + ", minPoQty=" + minPoQty + ", minPutawayQty=" + minPutawayQty + ", minSoQty="
                + minSoQty + ", model=" + model + ", modifiedFrom=" + modifiedFrom + ", modifiedTo=" + modifiedTo
                + ", name=" + name + ", netWeight=" + netWeight + ", noOfComponents=" + noOfComponents
                + ", orderStorage=" + orderStorage + ", orderingUomId=" + orderingUomId + ", originType=" + originType
                + ", outComplexity=" + outComplexity + ", packingGroupId=" + packingGroupId + ", primaryCompanyId="
                + primaryCompanyId + ", purchasable=" + purchasable + ", purchaseLotSize=" + purchaseLotSize
                + ", purchasePrice=" + purchasePrice + ", referenceSKU=" + referenceSKU + ", salesLotSize="
                + salesLotSize + ", salesPrice=" + salesPrice + ", searchFor=" + searchFor + ", sellable=" + sellable
                + ", serialNo=" + serialNo + ", serialNoValidationRule=" + serialNoValidationRule + ", size=" + size
                + ", skuCategoryId=" + skuCategoryId + ", skuTypeId=" + skuTypeId + ", storageCategoryId="
                + storageCategoryId + ", storageUomId=" + storageUomId + ", supplierId=" + supplierId
                + ", totalShelfLife=" + totalShelfLife + ", totalShelfLifeUnit=" + totalShelfLifeUnit
                + ", weightCaptureLotSize=" + weightCaptureLotSize + ", weightRequired=" + weightRequired + ", width="
                + width + ", xFactor=" + xFactor + "]";
    }

    

    

}
