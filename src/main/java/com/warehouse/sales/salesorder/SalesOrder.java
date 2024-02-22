package com.warehouse.sales.salesorder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.vasactivity.VasActivity;
import com.warehouse.administration.user.User;
import com.warehouse.country.Country;
import com.warehouse.enums.ShipModeEnum;
import com.warehouse.enums.TradeTermEnum;
import com.warehouse.enums.TransactionTypeEnum;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.customer.billtoaddress.CustomerBillToAddress;
import com.warehouse.setup.customer.shiptoaddress.CustomerShipToAddress;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "sales_order", schema = "public")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "so_no")
    private String soNo;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="sales_order_id", referencedColumnName="id")
    @NotNull
    private List<SalesOrderSku> salesOrderSku;

    @ManyToOne
    @JoinColumn(name = "primary_company_id", nullable = false)
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
    @JoinColumn(name = "cost_bucket", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CostBucket costBucket;

    @NotNull
    @Column(name = "cost_bucket", insertable = false, updatable = false)
    private Long costBucketId;

    @Transient
    private String costBucketName;

    @Transient
    private String costBucketCode;

    @ManyToOne
    @JoinColumn(name = "customer", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @NotNull
    @Column(name = "customer", insertable = false, updatable = false)
    private Long customerId;

    @Transient
    private String customerName;

    @Transient
    private String customerCode;

    @Column(name = "transaction_type")
    private TransactionTypeEnum transactionType;

    @Column(name = "reference_order_no")
    private String referenceOrderNo;

    @Column(name = "sales_person")
    private String salesPerson;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "bill_to", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CustomerBillToAddress customerBillToAddress;

    @NotNull
    @Column(name = "bill_to", insertable = false, updatable = false)
    private Long customerBillToAddressId;

    @Transient
    private String billTo;

    @ManyToOne
    @JoinColumn(name = "ship_to", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CustomerShipToAddress customerShipToAddress;

    @NotNull
    @Column(name = "ship_to", insertable = false, updatable = false)
    private Long customerShipToAddressId;

    @Transient
    private String shipTo;

    @ManyToOne
    @JoinColumn(name = "so_type", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private OrderType soType;

    @NotNull
    @Column(name = "so_type", insertable = false, updatable = false)
    private Long soTypeId;

    @Transient
    private String soTypeName;

    @Transient
    private String soTypeCode;

    @NotNull
    @Column(name = "trade_term")
    private TradeTermEnum tradeTermEnum;

    @NotNull
    @Column(name = "ship_mode")
    private ShipModeEnum shipModeEnum;

    @ManyToOne
    @JoinColumn(name = "destination_country", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Country destinationCountry;

    @NotNull
    @Column(name = "destination_country", insertable = false, updatable = false)
    private Long countryId;

    @Transient
    private String countryName;

    @Column(name = "destination_port")
    private String destinationPort;

    @ManyToOne
    @JoinColumn(name = "freighter", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Freighter freighter;

    @NotNull
    @Column(name = "freighter", insertable = false, updatable = false)
    private Long freighterId;

    @Transient
    private String freighterName;

    @Transient
    private String freighterCode;

    @Column(name = "fc_no")
    private String fcNo;

    @Column(name = "foc")
    private Boolean foc;

    @Column(name = "total_so_amt")
    private Float totalSoAmt;

    @Column(name = "so_receipt_date")
    private Date soReceiptDate;

    @Column(name = "target_dispatch_time")
    private Date targetDispatchDateTime;

    @Column(name = "validate_ref_so")
    private Boolean validateRefSo;

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

    @Column(name = "cod")
    private Boolean cod;

    @Column(name = "allocate_from_cross_dock")
    private Boolean allocateFromCrossDock;

    @Column(name = "is_approved")
    private Boolean isApproved =  false;

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

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceOrderNo() {
        return referenceOrderNo;
    }

    public void setReferenceOrderNo(String referenceOrderNo) {
        this.referenceOrderNo = referenceOrderNo;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CustomerBillToAddress getCustomerBillToAddress() {
        return customerBillToAddress;
    }

    public void setCustomerBillToAddress(CustomerBillToAddress customerBillToAddress) {
        this.customerBillToAddress = customerBillToAddress;
    }

    public Long getCustomerBillToAddressId() {
        return customerBillToAddressId;
    }

    public void setCustomerBillToAddressId(Long customerBillToAddressId) {
        this.customerBillToAddressId = customerBillToAddressId;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public CustomerShipToAddress getCustomerShipToAddress() {
        return customerShipToAddress;
    }

    public void setCustomerShipToAddress(CustomerShipToAddress customerShipToAddress) {
        this.customerShipToAddress = customerShipToAddress;
    }

    public Long getCustomerShipToAddressId() {
        return customerShipToAddressId;
    }

    public void setCustomerShipToAddressId(Long customerShipToAddressId) {
        this.customerShipToAddressId = customerShipToAddressId;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public OrderType getSoType() {
        return soType;
    }

    public void setSoType(OrderType soType) {
        this.soType = soType;
    }

    public Long getSoTypeId() {
        return soTypeId;
    }

    public void setSoTypeId(Long soTypeId) {
        this.soTypeId = soTypeId;
    }

    public String getSoTypeName() {
        return soTypeName;
    }

    public void setSoTypeName(String soTypeName) {
        this.soTypeName = soTypeName;
    }

    public String getSoTypeCode() {
        return soTypeCode;
    }

    public void setSoTypeCode(String soTypeCode) {
        this.soTypeCode = soTypeCode;
    }

    public TradeTermEnum getTradeTermEnum() {
        return tradeTermEnum;
    }

    public void setTradeTermEnum(TradeTermEnum tradeTermEnum) {
        this.tradeTermEnum = tradeTermEnum;
    }

    public ShipModeEnum getShipModeEnum() {
        return shipModeEnum;
    }

    public void setShipModeEnum(ShipModeEnum shipModeEnum) {
        this.shipModeEnum = shipModeEnum;
    }

    public Country getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(Country destinationCountry) {
        this.destinationCountry = destinationCountry;
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

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Freighter getFreighter() {
        return freighter;
    }

    public void setFreighter(Freighter freighter) {
        this.freighter = freighter;
    }

    public Long getFreighterId() {
        return freighterId;
    }

    public void setFreighterId(Long freighterId) {
        this.freighterId = freighterId;
    }

    public String getFreighterName() {
        return freighterName;
    }

    public void setFreighterName(String freighterName) {
        this.freighterName = freighterName;
    }

    public String getFreighterCode() {
        return freighterCode;
    }

    public void setFreighterCode(String freighterCode) {
        this.freighterCode = freighterCode;
    }

    public String getFcNo() {
        return fcNo;
    }

    public void setFcNo(String fcNo) {
        this.fcNo = fcNo;
    }

    public Boolean getFoc() {
        return foc;
    }

    public void setFoc(Boolean foc) {
        this.foc = foc;
    }

    public Float getTotalSoAmt() {
        return totalSoAmt;
    }

    public void setTotalSoAmt(Float totalSoAmt) {
        this.totalSoAmt = totalSoAmt;
    }

    public Date getSoReceiptDate() {
        return soReceiptDate;
    }

    public void setSoReceiptDate(Date soReceiptDate) {
        this.soReceiptDate = soReceiptDate;
    }

    public Date getTargetDispatchDateTime() {
        return targetDispatchDateTime;
    }

    public void setTargetDispatchDateTime(Date targetDispatchDateTime) {
        this.targetDispatchDateTime = targetDispatchDateTime;
    }

    public Boolean getValidateRefSo() {
        return validateRefSo;
    }

    public void setValidateRefSo(Boolean validateRefSo) {
        this.validateRefSo = validateRefSo;
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

    public Boolean getCod() {
        return cod;
    }

    public void setCod(Boolean cod) {
        this.cod = cod;
    }

    public Boolean getAllocateFromCrossDock() {
        return allocateFromCrossDock;
    }

    public void setAllocateFromCrossDock(Boolean allocateFromCrossDock) {
        this.allocateFromCrossDock = allocateFromCrossDock;
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

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public List<SalesOrderSku> getSalesOrderSku() {
        return salesOrderSku;
    }

    public void setSalesOrderSku(List<SalesOrderSku> salesOrderSku) {
        this.salesOrderSku = salesOrderSku;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
    
    public SalesOrder() {
        
    }

    public SalesOrder(Long id, String soNo, String customerCode, String referenceOrderNo, Date targetDispatchDateTime,
            Date createdAt, Date modifiedAt,Date soReceiptDate,String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.soNo = soNo;
        this.customerCode = customerCode;
        this.referenceOrderNo = referenceOrderNo;
        this.targetDispatchDateTime = targetDispatchDateTime;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.soReceiptDate = soReceiptDate;
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public SalesOrder(Long id, String soNo, String referenceOrderNo, String fcNo, Date createdAt, Date modifiedAt,
            String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.soNo = soNo;
        this.referenceOrderNo = referenceOrderNo;
        this.fcNo = fcNo;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public SalesOrder(Long id, String soNo, Long primaryCompanyId, String primaryCompanyName,
            String primaryCompanyCode,  Long costBucketId, String costBucketName, String costBucketCode,
            Long customerId, String customerName, String customerCode, TransactionTypeEnum transactionType,
            String referenceOrderNo, String salesPerson, String email, String phone,
             Long customerBillToAddressId, String billTo,  Long customerShipToAddressId, String shipTo,
             Long soTypeId, String soTypeName, String soTypeCode, TradeTermEnum tradeTermEnum,
             ShipModeEnum shipModeEnum, Long countryId, String countryName,
            String destinationPort, Long freighterId, String freighterName, String freighterCode, String fcNo,
            Boolean foc, Float totalSoAmt, Date soReceiptDate, Date targetDispatchDateTime, Boolean validateRefSo,
            Long vasActivityId, String vasActivityName, String vasActivityCode, Boolean cod,
            Boolean allocateFromCrossDock, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.soNo = soNo;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.costBucketId = costBucketId;
        this.costBucketName = costBucketName;
        this.costBucketCode = costBucketCode;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCode = customerCode;
        this.transactionType = transactionType;
        this.referenceOrderNo = referenceOrderNo;
        this.salesPerson = salesPerson;
        this.email = email;
        this.phone = phone;
        this.customerBillToAddressId = customerBillToAddressId;
        this.billTo = billTo;
        this.customerShipToAddressId = customerShipToAddressId;
        this.shipTo = shipTo;
        this.soTypeId = soTypeId;
        this.soTypeName = soTypeName;
        this.soTypeCode = soTypeCode;
        this.tradeTermEnum = tradeTermEnum;
        this.shipModeEnum = shipModeEnum;
        this.countryId = countryId;
        this.countryName = countryName;
        this.destinationPort = destinationPort;
        this.freighterId = freighterId;
        this.freighterName = freighterName;
        this.freighterCode = freighterCode;
        this.fcNo = fcNo;
        this.foc = foc;
        this.totalSoAmt = totalSoAmt;
        this.soReceiptDate = soReceiptDate;
        this.targetDispatchDateTime = targetDispatchDateTime;
        this.validateRefSo = validateRefSo;
        this.vasActivityId = vasActivityId;
        this.vasActivityName = vasActivityName;
        this.vasActivityCode = vasActivityCode;
        this.cod = cod;
        this.allocateFromCrossDock = allocateFromCrossDock;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    @Override
    public String toString() {
        return "SalesOrder [allocateFromCrossDock=" + allocateFromCrossDock + ", billTo=" + billTo + ", cod=" + cod
                + ", costBucket=" + costBucket + ", costBucketCode=" + costBucketCode + ", costBucketId=" + costBucketId
                + ", costBucketName=" + costBucketName + ", createdAt=" + createdAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", customer=" + customer
                + ", customerBillToAddress=" + customerBillToAddress + ", customerBillToAddressId="
                + customerBillToAddressId + ", customerCode=" + customerCode + ", customerId=" + customerId
                + ", customerName=" + customerName + ", customerShipToAddress=" + customerShipToAddress
                + ", customerShipToAddressId=" + customerShipToAddressId + ", destinationCountry=" + destinationCountry
                + ", countryId=" + countryId + ", countryName="
                + countryName + ", destinationPort=" + destinationPort + ", email=" + email + ", fcNo="
                + fcNo + ", foc=" + foc + ", freighter=" + freighter + ", freighterCode=" + freighterCode
                + ", freighterId=" + freighterId + ", freighterName=" + freighterName + ", id=" + id + ", isApproved="
                + isApproved + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById="
                + modifiedById + ", modifiedByUserName=" + modifiedByUserName + ", phone=" + phone + ", primaryCompany="
                + primaryCompany + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId="
                + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", referenceOrderNo="
                + referenceOrderNo + ", salesOrderSku=" + salesOrderSku + ", salesPerson=" + salesPerson
                + ", shipModeEnum=" + shipModeEnum + ", shipTo=" + shipTo + ", soNo=" + soNo + ", soReceiptDate="
                + soReceiptDate + ", soType=" + soType + ", soTypeCode=" + soTypeCode + ", soTypeId=" + soTypeId
                + ", soTypeName=" + soTypeName + ", targetDispatchDateTime=" + targetDispatchDateTime + ", totalSoAmt="
                + totalSoAmt + ", tradeTermEnum=" + tradeTermEnum + ", transactionType=" + transactionType
                + ", validateRefSo=" + validateRefSo + ", vasActivity=" + vasActivity + ", vasActivityCode="
                + vasActivityCode + ", vasActivityId=" + vasActivityId + ", vasActivityName=" + vasActivityName + "]";
    }

    

    

}
