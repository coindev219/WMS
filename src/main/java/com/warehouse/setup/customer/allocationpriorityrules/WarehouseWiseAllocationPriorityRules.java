package com.warehouse.setup.customer.allocationpriorityrules;

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
import com.warehouse.administration.user.User;
import com.warehouse.setup.warehouse.Warehouse;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "warehouse_wise_allocation_priority_rules", schema = "public")

public class WarehouseWiseAllocationPriorityRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // @ManyToOne
    // @JoinColumn(name = "warehouse_wise_allocation_priority_rules_header_id")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private WarehouseWiseAllocationPriorityRulesHeader
    // warehouseWiseAllocationPriorityRulesHeader;

    // @NotNull
    @Column(name = "warehouse_wise_allocation_priority_rules_header_id")
    private Long warehouseWiseAllocationPriorityRulesHeaderId;

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

    @Column(name = "priority_rules")
    private Float priorityRules;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // public WarehouseWiseAllocationPriorityRulesHeader
    // getWarehouseWiseAllocationPriorityRulesHeader() {
    // return warehouseWiseAllocationPriorityRulesHeader;
    // }

    // public void setWarehouseWiseAllocationPriorityRulesHeader(
    // WarehouseWiseAllocationPriorityRulesHeader
    // warehouseWiseAllocationPriorityRulesHeader) {
    // this.warehouseWiseAllocationPriorityRulesHeader =
    // warehouseWiseAllocationPriorityRulesHeader;
    // }

    public Long getWarehouseWiseAllocationPriorityRulesHeaderId() {
    return warehouseWiseAllocationPriorityRulesHeaderId;
    }

    public void setWarehouseWiseAllocationPriorityRulesHeaderId(Long
    warehouseWiseAllocationPriorityRulesHeaderId) {
    this.warehouseWiseAllocationPriorityRulesHeaderId =
    warehouseWiseAllocationPriorityRulesHeaderId;
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

    public Float getPriorityRules() {
        return priorityRules;
    }

    public void setPriorityRules(Float priorityRules) {
        this.priorityRules = priorityRules;
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

    

    public WarehouseWiseAllocationPriorityRules(Long id, Long warehouseWiseAllocationPriorityRulesHeaderId,
            Long warehouseId, String warehouseName, String warehouseCode, Float priorityRules) {
        this.id = id;
        this.warehouseWiseAllocationPriorityRulesHeaderId = warehouseWiseAllocationPriorityRulesHeaderId;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.priorityRules = priorityRules;
    }

    @Override
    public String toString() {
        return "WarehouseWiseAllocationPriorityRules [createdAt=" + createdAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", id=" + id
                + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", modifiedByUserName=" + modifiedByUserName + ", priorityRules=" + priorityRules + ", warehouse="
                + warehouse + ", warehouseCode=" + warehouseCode + ", warehouseId=" + warehouseId + ", warehouseName="
                + warehouseName + "]";
    }

}
