package com.warehouse.setup.sku.managekitbom;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.sku.Sku;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sku_manage_kit_bom", schema = "public")
public class SkuManageKitBom {

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
    @JoinColumn(name = "kit_sku_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Sku kitSku;

    @NotNull
    @Column(name = "kit_sku_id", insertable = false, updatable = false)
    private Long kitSkuId;

    @Transient
    private String kitSkuName;

    @Transient
    private String kitSkuCode;

    @ManyToOne
    @JoinColumn(name = "component_sku_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Sku componentSku;

    @NotNull
    @Column(name = "component_sku_id", insertable = false, updatable = false)
    private Long componentSkuId;

    @Transient
    private String componentSkuName;

    @Transient
    private String componentSkuCode;

    private Integer quantity;

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


    public SkuManageKitBom() {
    }

    public SkuManageKitBom(Long id, Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode,
            Long kitSkuId, String kitSkuName, String kitSkuCode, Long componentSkuId, String componentSkuName,
            String componentSkuCode, Integer quantity, Date createdAt, Date modifiedAt, String createdByUserName,
             String modifiedByUserName) {
        this.id = id;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.kitSkuId = kitSkuId;
        this.kitSkuName = kitSkuName;
        this.kitSkuCode = kitSkuCode;
        this.componentSkuId = componentSkuId;
        this.componentSkuName = componentSkuName;
        this.componentSkuCode = componentSkuCode;
        this.quantity = quantity;
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

    public Sku getKitSku() {
        return kitSku;
    }

    public void setKitSku(Sku kitSku) {
        this.kitSku = kitSku;
    }

    public Long getKitSkuId() {
        return kitSkuId;
    }

    public void setKitSkuId(Long kitSkuId) {
        this.kitSkuId = kitSkuId;
    }

    public String getKitSkuName() {
        return kitSkuName;
    }

    public void setKitSkuName(String kitSkuName) {
        this.kitSkuName = kitSkuName;
    }

    public String getKitSkuCode() {
        return kitSkuCode;
    }

    public void setKitSkuCode(String kitSkuCode) {
        this.kitSkuCode = kitSkuCode;
    }

    public Sku getComponentSku() {
        return componentSku;
    }

    public void setComponentSku(Sku componentSku) {
        this.componentSku = componentSku;
    }

    public Long getComponentSkuId() {
        return componentSkuId;
    }

    public void setComponentSkuId(Long componentSkuId) {
        this.componentSkuId = componentSkuId;
    }

    public String getComponentSkuName() {
        return componentSkuName;
    }

    public void setComponentSkuName(String componentSkuName) {
        this.componentSkuName = componentSkuName;
    }

    public String getComponentSkuCode() {
        return componentSkuCode;
    }

    public void setComponentSkuCode(String componentSkuCode) {
        this.componentSkuCode = componentSkuCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        return "SkuManagerKitBom [componentSku=" + componentSku + ", componentSkuCode=" + componentSkuCode
                + ", componentSkuId=" + componentSkuId + ", componentSkuName=" + componentSkuName + ", createdAt="
                + createdAt + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", id=" + id + ", kitSku=" + kitSku + ", kitSkuCode=" + kitSkuCode + ", kitSkuId="
                + kitSkuId + ", kitSkuName=" + kitSkuName + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy
                + ", modifiedById=" + modifiedById + ", modifiedByUserName=" + modifiedByUserName + ", primaryCompany="
                + primaryCompany + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId="
                + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", quantity=" + quantity + "]";
    }

}
