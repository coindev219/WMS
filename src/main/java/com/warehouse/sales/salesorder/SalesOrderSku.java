package com.warehouse.sales.salesorder;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.vasactivity.VasActivity;
import com.warehouse.administration.user.User;
import com.warehouse.enums.TransactionTypeEnum;
import com.warehouse.setup.company.reason.Reason;
import com.warehouse.setup.sku.Sku;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sales_order_sku", schema = "public")
public class SalesOrderSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    
    @Column(name = "sales_order_id", insertable = false, updatable = false)
    private Long salesOrderId;

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

    @Column(name = "reference_order_line_no")
    private String referenceOrderLineNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vas_activity", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private VasActivity vasActivity;

    @Column(name = "vas_activity", insertable = false, updatable = false)
    private Long vasActivityId;

    @Transient
    private String vasActivityName;

    @Transient
    private String vasActivityCode;

    @Column(name = "manufacturing_date")
    private Date manufacturingDate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name = "ordering_uom", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Uom orderingUom;

    @NotNull
    @Column(name = "ordering_uom", insertable = false, updatable = false)
    private Long orderingUomId;

    @Transient
    private String orderingUomName;

    @Transient
    private String orderingUomCode;

    @ManyToOne
    @JoinColumn(name = "billing_uom", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Uom billingUom;

    @NotNull
    @Column(name = "billing_uom", insertable = false, updatable = false)
    private Long billingUomId;

    @Transient
    private String billingUomName;

    @Transient
    private String billingUomCode;

    @Column(name = "ordered_quantity")
    private Integer orderedQuantity;

    @Column(name = "ordered_quantity_billing_uom")
    private Integer orderedQuantityBillingUom;

    @Column(name = "pack_no")
    private String packNo;

    @Column(name = "allocate_from_cross_dock")
    private Boolean allocateFromCrossDock;

    @Column(name = "transaction_type")
    private TransactionTypeEnum transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Reason reason;

    @Column(name = "reason", insertable = false, updatable = false)
    private Long reasonId;

    @Transient
    private String reasonName;

    @Transient
    private String ressonCode;


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

    public SalesOrderSku() {

    }
    
    public SalesOrderSku(Long id,Long skuId, String skuName, String skuCode, Date manufacturingDate, Date expiryDate,
            Long reasonId, String reasonName, String ressonCode) {
        this.id = id;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.reasonId = reasonId;
        this.reasonName = reasonName;
        this.ressonCode = ressonCode;
    }

    public SalesOrderSku(Long id, Long skuId, String skuName, String skuCode,
            String referenceOrderLineNo, Long vasActivityId, String vasActivityName, String vasActivityCode,
            Date manufacturingDate, Date expiryDate,Long orderingUomId, String orderingUomName,
            String orderingUomCode,  Long billingUomId, String billingUomName, String billingUomCode,
            Integer orderedQuantity, Integer orderedQuantityBillingUom, String packNo, Boolean allocateFromCrossDock,
            TransactionTypeEnum transactionType) {
        this.id = id;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.referenceOrderLineNo = referenceOrderLineNo;
        this.vasActivityId = vasActivityId;
        this.vasActivityName = vasActivityName;
        this.vasActivityCode = vasActivityCode;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.orderingUomId = orderingUomId;
        this.orderingUomName = orderingUomName;
        this.orderingUomCode = orderingUomCode;
        this.billingUomId = billingUomId;
        this.billingUomName = billingUomName;
        this.billingUomCode = billingUomCode;
        this.orderedQuantity = orderedQuantity;
        this.orderedQuantityBillingUom = orderedQuantityBillingUom;
        this.packNo = packNo;
        this.allocateFromCrossDock = allocateFromCrossDock;
        this.transactionType = transactionType;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
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

    public String getReferenceOrderLineNo() {
        return referenceOrderLineNo;
    }

    public void setReferenceOrderLineNo(String referenceOrderLineNo) {
        this.referenceOrderLineNo = referenceOrderLineNo;
    }

    public VasActivity getVasActivity() {
        return vasActivity;
    }

    public void setVasActivity(VasActivity vasActivity) {
        this.vasActivity = vasActivity;
    }

    public Long getVasActivityId() {
        return vasActivityId;
    }

    public void setVasActivityId(Long vasActivityId) {
        this.vasActivityId = vasActivityId;
    }

    public String getVasActivityName() {
        return vasActivityName;
    }

    public void setVasActivityName(String vasActivityName) {
        this.vasActivityName = vasActivityName;
    }

    public String getVasActivityCode() {
        return vasActivityCode;
    }

    public void setVasActivityCode(String vasActivityCode) {
        this.vasActivityCode = vasActivityCode;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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

    public Integer getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(Integer orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public Integer getOrderedQuantityBillingUom() {
        return orderedQuantityBillingUom;
    }

    public void setOrderedQuantityBillingUom(Integer orderedQuantityBillingUom) {
        this.orderedQuantityBillingUom = orderedQuantityBillingUom;
    }

    public String getPackNo() {
        return packNo;
    }

    public void setPackNo(String packNo) {
        this.packNo = packNo;
    }

    public Boolean getAllocateFromCrossDock() {
        return allocateFromCrossDock;
    }

    public void setAllocateFromCrossDock(Boolean allocateFromCrossDock) {
        this.allocateFromCrossDock = allocateFromCrossDock;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
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

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getRessonCode() {
        return ressonCode;
    }

    public void setRessonCode(String ressonCode) {
        this.ressonCode = ressonCode;
    }

    @Override
    public String toString() {
        return "SalesOrderSku [allocateFromCrossDock=" + allocateFromCrossDock + ", billingUom=" + billingUom
                + ", billingUomCode=" + billingUomCode + ", billingUomId=" + billingUomId + ", billingUomName="
                + billingUomName + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", createdById="
                + createdById + ", createdByUserName=" + createdByUserName + ", expiryDate=" + expiryDate + ", id=" + id
                + ", manufacturingDate=" + manufacturingDate + ", modifiedAt=" + modifiedAt + ", modifiedBy="
                + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName=" + modifiedByUserName
                + ", orderedQuantity=" + orderedQuantity + ", orderedQuantityBillingUom=" + orderedQuantityBillingUom
                + ", orderingUom=" + orderingUom + ", orderingUomCode=" + orderingUomCode + ", orderingUomId="
                + orderingUomId + ", orderingUomName=" + orderingUomName + ", packNo=" + packNo
                + ", referenceOrderLineNo=" + referenceOrderLineNo + ", salesOrderId=" + salesOrderId + ", sku=" + sku
                + ", skuCode=" + skuCode + ", skuId=" + skuId + ", skuName=" + skuName + ", transactionType="
                + transactionType + ", vasActivity=" + vasActivity + ", vasActivityCode=" + vasActivityCode
                + ", vasActivityId=" + vasActivityId + ", vasActivityName=" + vasActivityName + "]";
    }

   

}
