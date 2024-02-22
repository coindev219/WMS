package com.warehouse.setup.customer.daystoexpirty;

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
import com.warehouse.enums.DurationTimeEnum;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.sku.Sku;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "customer_sku_days_to_expiry", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "cusotmer_id", "sku_id" }) })
public class CustomerWiseDaysToExpiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cusotmer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @NotNull
    @Column(name = "cusotmer_id", insertable = false, updatable = false)
    private Long customerId;

    @Transient
    private String customerName;

    @Transient
    private String customerCode;

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

    @Column(name = "days_to_expiry")
    private Integer daysToExpiry;

    @Column(name = "days_to_expiry_unit")
    private DurationTimeEnum daysToExpiryUnit = DurationTimeEnum.Select;

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

    public CustomerWiseDaysToExpiry() {
    }

    public CustomerWiseDaysToExpiry(Long id, Long customerId, String customerName, String customerCode, Long skuId,
            String skuName, String skuCode, Integer daysToExpiry, DurationTimeEnum daysToExpiryUnit, Date createdAt,
            Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCode = customerCode;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.daysToExpiry = daysToExpiry;
        this.daysToExpiryUnit = daysToExpiryUnit;
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

    public Integer getDaysToExpiry() {
        return daysToExpiry;
    }

    public void setDaysToExpiry(Integer daysToExpiry) {
        this.daysToExpiry = daysToExpiry;
    }

    public DurationTimeEnum getDaysToExpiryUnit() {
        return daysToExpiryUnit;
    }

    public void setDaysToExpiryUnit(DurationTimeEnum daysToExpiryUnit) {
        this.daysToExpiryUnit = daysToExpiryUnit;
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

}
