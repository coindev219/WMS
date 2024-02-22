package com.warehouse.setup.sku.costbucketmapping;

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
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.administration.user.User;
import com.warehouse.setup.sku.warehousemapping.SkuWarehouseMapping;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sku_cost_bucket_mapping", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "sku_warehouse_mapping_id", "cost_bucket_id" }) })

public class SkuCostBucketMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sku_warehouse_mapping_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SkuWarehouseMapping skuWarehouseMapping;

    @NotNull
    @Column(name = "sku_warehouse_mapping_id", insertable = false, updatable = false)
    private Long skuWarehouseMappingId;

    @Transient
    private Long warehouseId;

    @Transient
    private String warehouseName;

    @Transient
    private String warehouseCode;

    @Transient
    private Long primaryCompanyId;

    @Transient
    private String primaryCompanyName;

    @Transient
    private String primaryCompanyCode;

    @Transient
    private Long skuId;

    @Transient
    private String skuName;

    @Transient
    private String skuCode;

    @ManyToOne
    @JoinColumn(name = "cost_bucket_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CostBucket costBucket;

    @NotNull
    @Column(name = "cost_bucket_id", insertable = false, updatable = false)
    private Long costBucketId;

    @Transient
    private String costBucketName;

    @Transient
    private String costBucketCode;

    @Column(name = "moving_average")
    private Float movingAverage;

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

    public Float getMovingAverage() {
        return movingAverage;
    }

    public void setMovingAverage(Float movingAverage) {
        this.movingAverage = movingAverage;
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

    public SkuWarehouseMapping getSkuWarehouseMapping() {
        return skuWarehouseMapping;
    }

    public void setSkuWarehouseMapping(SkuWarehouseMapping skuWarehouseMapping) {
        this.skuWarehouseMapping = skuWarehouseMapping;
    }

    public Long getSkuWarehouseMappingId() {
        return skuWarehouseMappingId;
    }

    public void setSkuWarehouseMappingId(Long skuWarehouseMappingId) {
        this.skuWarehouseMappingId = skuWarehouseMappingId;
    }

    public SkuCostBucketMapping() {
    }

    public SkuCostBucketMapping(Long id, Long warehouseId, String warehouseName, String warehouseCode,
            Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode, Long skuId, String skuName,
            String skuCode, Long costBucketId, String costBucketName, String costBucketCode, Float movingAverage,
            Date createdAt, Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.costBucketId = costBucketId;
        this.costBucketName = costBucketName;
        this.costBucketCode = costBucketCode;
        this.movingAverage = movingAverage;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    @Override
    public String toString() {
        return "SkuCostBucketMapping [costBucket=" + costBucket + ", costBucketCode=" + costBucketCode
                + ", costBucketId=" + costBucketId + ", costBucketName=" + costBucketName + ", createdAt=" + createdAt
                + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", id=" + id + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy
                + ", modifiedById=" + modifiedById + ", modifiedByUserName=" + modifiedByUserName + ", movingAverage="
                + movingAverage + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId="
                + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", skuCode=" + skuCode + ", skuId="
                + skuId + ", skuName=" + skuName + ", skuWarehouseMapping=" + skuWarehouseMapping
                + ", skuWarehouseMappingId=" + skuWarehouseMappingId + ", warehouseCode=" + warehouseCode
                + ", warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + "]";
    }

}
