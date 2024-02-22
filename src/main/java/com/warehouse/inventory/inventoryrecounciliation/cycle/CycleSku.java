package com.warehouse.inventory.inventoryrecounciliation.cycle;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.administration.user.User;
import com.warehouse.setup.sku.Sku;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "inventory_cycle_sku", schema = "public")
public class CycleSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cycle_id", insertable = false, updatable = false)
    private Long cycleId;

    @ManyToOne
    @JoinColumn(name = "sku", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Sku sku;

    @NotNull
    @Column(name = "sku", insertable = false, updatable = false)
    private Long skuId;

    @Transient
    private String skuName;

    @Transient
    private String skuCode;

    @ManyToOne
    @JoinColumn(name = "uom", nullable = false)
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

    public CycleSku() {
    }

    public CycleSku(Long id, Long cycleId, Sku sku, @NotNull Long skuId, String skuName, String skuCode, Uom uom,
            @NotNull Long uomId, String uomName, String uomCode, Timestamp createdAt, Timestamp modifiedAt,
            User createdBy, Long createdById, User modifiedBy, Long modifiedById, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.cycleId = cycleId;
        this.sku = sku;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.uom = uom;
        this.uomId = uomId;
        this.uomName = uomName;
        this.uomCode = uomCode;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.modifiedBy = modifiedBy;
        this.modifiedById = modifiedById;
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
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

    @Override
    public String toString() {
        return "CycleSku{" + "id=" + id + ", cycleId=" + cycleId + ", sku=" + sku + ", skuId=" + skuId + ", skuName='"
                + skuName + '\'' + ", skuCode='" + skuCode + '\'' + ", uom=" + uom + ", uomId=" + uomId + ", uomName='"
                + uomName + '\'' + ", uomCode='" + uomCode + '\'' + ", createdAt=" + createdAt + ", modifiedAt="
                + modifiedAt + ", createdBy=" + createdBy + ", createdById=" + createdById + ", modifiedBy="
                + modifiedBy + ", modifiedById=" + modifiedById + ", createdByUserName='" + createdByUserName + '\''
                + ", modifiedByUserName='" + modifiedByUserName + '\'' + '}';
    }
}
