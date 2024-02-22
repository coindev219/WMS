package com.warehouse.setup.customer.allocationpriorityrules;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.administration.user.User;
import com.warehouse.enums.ShipModeEnum;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "warehouse_wise_allocation_priority_rules_header", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "primary_company_id", "cost_bucket_id", "ship_to_id", "order_type_id",
                "sku_type_id", "shipping_mode" }), })

public class WarehouseWiseAllocationPriorityRulesHeader {

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
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @NotNull
    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;

    @Transient
    private String customerName;

    @Transient
    private String customerCode;

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

    @ManyToOne
    @JoinColumn(name = "ship_to_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CustomerShipToAddress shipTo;

    @NotNull
    @Column(name = "ship_to_id", insertable = false, updatable = false)
    private Long shipToId;

    @Transient
    private String shipToAddress;

    @ManyToOne
    @JoinColumn(name = "order_type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private OrderType orderType;

    @NotNull
    @Column(name = "order_type_id", insertable = false, updatable = false)
    private Long orderTypeId;

    @Transient
    private String orderTypeName;

    @Transient
    private String orderTypeCode;

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

    @Column(name = "shipping_mode")
    @NotNull
    private ShipModeEnum shippingMode = ShipModeEnum.Select;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="warehouse_wise_allocation_priority_rules_header_id", referencedColumnName="id")
    @NotNull
    private List<WarehouseWiseAllocationPriorityRules> warehouseWiseAllocationPriorityRules;
   

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    public CustomerShipToAddress getShipTo() {
        return shipTo;
    }

    public void setShipTo(CustomerShipToAddress shipTo) {
        this.shipTo = shipTo;
    }

    public Long getShipToId() {
        return shipToId;
    }

    public void setShipToId(Long shipToId) {
        this.shipToId = shipToId;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
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

    public ShipModeEnum getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(ShipModeEnum shippingMode) {
        this.shippingMode = shippingMode;
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
 
    public List<WarehouseWiseAllocationPriorityRules> getWarehouseWiseAllocationPriorityRules() {
        return warehouseWiseAllocationPriorityRules;
    }

    public void setWarehouseWiseAllocationPriorityRules(
            List<WarehouseWiseAllocationPriorityRules> warehouseWiseAllocationPriorityRules) {
        this.warehouseWiseAllocationPriorityRules = warehouseWiseAllocationPriorityRules;
    }
    
    public WarehouseWiseAllocationPriorityRulesHeader(Long id,Long primaryCompanyId,
            String primaryCompanyName, String primaryCompanyCode, Long customerId, String customerName,
            String customerCode, Long costBucketId, String costBucketName, String costBucketCode,
            Long shipToId, String shipTo, Long orderTypeId,
            String orderTypeName, String orderTypeCode, Long skuTypeId, String skuTypeName, String skuTypeCode,
            ShipModeEnum shippingMode) {

        this.id = id;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCode = customerCode;
        this.costBucketId = costBucketId;
        this.costBucketName = costBucketName;
        this.costBucketCode = costBucketCode;
        this.shipToId = shipToId;
        this.shipToAddress = shipTo;
        this.orderTypeId = orderTypeId;
        this.orderTypeName = orderTypeName;
        this.orderTypeCode = orderTypeCode;
        this.skuTypeId = skuTypeId;
        this.skuTypeName = skuTypeName;
        this.skuTypeCode = skuTypeCode;
        this.shippingMode = shippingMode;
    }

    @Override
    public String toString() {
        return "WarehouseWiseAllocationPriorityRulesHeader [costBucket=" + costBucket + ", costBucketCode="
                + costBucketCode + ", costBucketId=" + costBucketId + ", costBucketName=" + costBucketName
                + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", createdById=" + createdById
                + ", createdByUserName=" + createdByUserName + ", customer=" + customer + ", customerCode="
                + customerCode + ", customerId=" + customerId + ", customerName=" + customerName + ", id=" + id
                + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", modifiedByUserName=" + modifiedByUserName + ", orderType=" + orderType + ", orderTypeCode="
                + orderTypeCode + ", orderTypeId=" + orderTypeId + ", orderTypeName=" + orderTypeName
                + ", primaryCompany=" + primaryCompany + ", primaryCompanyCode=" + primaryCompanyCode
                + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", shipTo="
                + shipTo + ", shipToAddress=" + shipToAddress + ", shipToId=" + shipToId + ", shippingMode="
                + shippingMode + ", skuType=" + skuType + ", skuTypeCode=" + skuTypeCode + ", skuTypeId=" + skuTypeId
                + ", skuTypeName=" + skuTypeName + ", warehouseWiseAllocationPriorityRules="
                + warehouseWiseAllocationPriorityRules + "]";
    }

    
    

}
