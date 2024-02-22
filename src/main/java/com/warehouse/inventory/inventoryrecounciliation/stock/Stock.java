package com.warehouse.inventory.inventoryrecounciliation.stock;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.administration.user.User;
import com.warehouse.enums.DurationTimeEnum;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.warehouse.Warehouse;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "inventory_stock", schema = "public")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock_no")
    private String stockNo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @NotNull
    private List<StockLocation> stockLocation;

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

    @Column(name = "include_locations_moved_in")
    private Long includeLocationsThatMovedIn;

    @Column(name = "inventory_reconciliation_instruction_name")
    private Boolean inventoryReconciliationInstructionName;

    @Column(name = "is_blind_count")
    private Boolean isBlindCount = true;

    @Column(name = "is_dirty")
    private Boolean isDirty = false;

    @Column(name = "is_existing_stock_count_name")
    private Boolean isExistingStockCountName = true;

    @Column(name = "is_recount_on_batch_no")
    private Boolean isRecountOnBatchNo = false;

    @Column(name = "is_recount_on_expiry_date")
    private Boolean isRecountOnExpiryDate = false;

    @Column(name = "is_recount_on_location")
    private Boolean isRecountOnLocation = false;

    @Column(name = "is_recount_on_manufacture_date")
    private Boolean isRecountOnManufactureDate = false;

    @Column(name = "is_recount_on_pack_number")
    private Boolean isRecountOnPackNumber = false;

    @Column(name = "is_recount_on_serial_no")
    private Boolean isRecountOnSerialNo = false;

    @Column(name = "is_recount_required")
    private Boolean isRecountRequired = true;

    @Column(name = "is_selected")
    private Boolean isSelected;

    @Column(name = "location_hierarchy_id")
    private String locationHierarchyID;

    @Column(name = "locations_movedInUnit")
    private DurationTimeEnum locationsMovedInUnit;

    @Column(name = "max_no_of_lines_per_instruction")
    private Long maxNoOfLinesPerInstruction;

    @Column(name = "verify_batch_no")
    private Boolean verifyBatchNo = true;

    @Column(name = "verify_expiry_date")
    private Boolean verifyExpiryDate = true;

    @Column(name = "verify_manufacture_date")
    private Boolean verifyManufactureDate = true;

    @Column(name = "verify_pack_number")
    private Boolean verifyPackNumber = true;

    @Column(name = "verify_serial_no")
    private Boolean verifySerialNo = true;

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

    public Stock() {
    }

    public Stock(Long id, String stockNo, @NotNull List<StockLocation> stockLocation, PrimaryCompany primaryCompany,
            @NotNull Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode, Warehouse warehouse,
            @NotNull Long warehouseId, String warehouseName, String warehouseCode, CostBucket costBucket,
            @NotNull Long costBucketId, String costBucketName, String costBucketCode, Long includeLocationsThatMovedIn,
            Boolean inventoryReconciliationInstructionName, Boolean isBlindCount, Boolean isDirty,
            Boolean isExistingStockCountName, Boolean isRecountOnBatchNo, Boolean isRecountOnExpiryDate,
            Boolean isRecountOnLocation, Boolean isRecountOnManufactureDate, Boolean isRecountOnPackNumber,
            Boolean isRecountOnSerialNo, Boolean isRecountRequired, Boolean isSelected, String locationHierarchyID,
            DurationTimeEnum locationsMovedInUnit, Long maxNoOfLinesPerInstruction, Boolean verifyBatchNo,
            Boolean verifyExpiryDate, Boolean verifyManufactureDate, Boolean verifyPackNumber, Boolean verifySerialNo,
            Timestamp createdAt, Timestamp modifiedAt, User createdBy, Long createdById, User modifiedBy,
            Long modifiedById, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.stockNo = stockNo;
        this.stockLocation = stockLocation;
        this.primaryCompany = primaryCompany;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.warehouse = warehouse;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.costBucket = costBucket;
        this.costBucketId = costBucketId;
        this.costBucketName = costBucketName;
        this.costBucketCode = costBucketCode;
        this.includeLocationsThatMovedIn = includeLocationsThatMovedIn;
        this.inventoryReconciliationInstructionName = inventoryReconciliationInstructionName;
        this.isBlindCount = isBlindCount;
        this.isDirty = isDirty;
        this.isExistingStockCountName = isExistingStockCountName;
        this.isRecountOnBatchNo = isRecountOnBatchNo;
        this.isRecountOnExpiryDate = isRecountOnExpiryDate;
        this.isRecountOnLocation = isRecountOnLocation;
        this.isRecountOnManufactureDate = isRecountOnManufactureDate;
        this.isRecountOnPackNumber = isRecountOnPackNumber;
        this.isRecountOnSerialNo = isRecountOnSerialNo;
        this.isRecountRequired = isRecountRequired;
        this.isSelected = isSelected;
        this.locationHierarchyID = locationHierarchyID;
        this.locationsMovedInUnit = locationsMovedInUnit;
        this.maxNoOfLinesPerInstruction = maxNoOfLinesPerInstruction;
        this.verifyBatchNo = verifyBatchNo;
        this.verifyExpiryDate = verifyExpiryDate;
        this.verifyManufactureDate = verifyManufactureDate;
        this.verifyPackNumber = verifyPackNumber;
        this.verifySerialNo = verifySerialNo;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
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

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public List<StockLocation> getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(List<StockLocation> stockLocation) {
        this.stockLocation = stockLocation;
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

    public Long getIncludeLocationsThatMovedIn() {
        return includeLocationsThatMovedIn;
    }

    public void setIncludeLocationsThatMovedIn(Long includeLocationsThatMovedIn) {
        this.includeLocationsThatMovedIn = includeLocationsThatMovedIn;
    }

    public Boolean getInventoryReconciliationInstructionName() {
        return inventoryReconciliationInstructionName;
    }

    public void setInventoryReconciliationInstructionName(Boolean inventoryReconciliationInstructionName) {
        this.inventoryReconciliationInstructionName = inventoryReconciliationInstructionName;
    }

    public Boolean getBlindCount() {
        return isBlindCount;
    }

    public void setBlindCount(Boolean blindCount) {
        isBlindCount = blindCount;
    }

    public Boolean getDirty() {
        return isDirty;
    }

    public void setDirty(Boolean dirty) {
        isDirty = dirty;
    }

    public Boolean getExistingStockCountName() {
        return isExistingStockCountName;
    }

    public void setExistingStockCountName(Boolean existingStockCountName) {
        isExistingStockCountName = existingStockCountName;
    }

    public Boolean getRecountOnBatchNo() {
        return isRecountOnBatchNo;
    }

    public void setRecountOnBatchNo(Boolean recountOnBatchNo) {
        isRecountOnBatchNo = recountOnBatchNo;
    }

    public Boolean getRecountOnExpiryDate() {
        return isRecountOnExpiryDate;
    }

    public void setRecountOnExpiryDate(Boolean recountOnExpiryDate) {
        isRecountOnExpiryDate = recountOnExpiryDate;
    }

    public Boolean getRecountOnLocation() {
        return isRecountOnLocation;
    }

    public void setRecountOnLocation(Boolean recountOnLocation) {
        isRecountOnLocation = recountOnLocation;
    }

    public Boolean getRecountOnManufactureDate() {
        return isRecountOnManufactureDate;
    }

    public void setRecountOnManufactureDate(Boolean recountOnManufactureDate) {
        isRecountOnManufactureDate = recountOnManufactureDate;
    }

    public Boolean getRecountOnPackNumber() {
        return isRecountOnPackNumber;
    }

    public void setRecountOnPackNumber(Boolean recountOnPackNumber) {
        isRecountOnPackNumber = recountOnPackNumber;
    }

    public Boolean getRecountOnSerialNo() {
        return isRecountOnSerialNo;
    }

    public void setRecountOnSerialNo(Boolean recountOnSerialNo) {
        isRecountOnSerialNo = recountOnSerialNo;
    }

    public Boolean getRecountRequired() {
        return isRecountRequired;
    }

    public void setRecountRequired(Boolean recountRequired) {
        isRecountRequired = recountRequired;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getLocationHierarchyID() {
        return locationHierarchyID;
    }

    public void setLocationHierarchyID(String locationHierarchyID) {
        this.locationHierarchyID = locationHierarchyID;
    }

    public DurationTimeEnum getLocationsMovedInUnit() {
        return locationsMovedInUnit;
    }

    public void setLocationsMovedInUnit(DurationTimeEnum locationsMovedInUnit) {
        this.locationsMovedInUnit = locationsMovedInUnit;
    }

    public Long getMaxNoOfLinesPerInstruction() {
        return maxNoOfLinesPerInstruction;
    }

    public void setMaxNoOfLinesPerInstruction(Long maxNoOfLinesPerInstruction) {
        this.maxNoOfLinesPerInstruction = maxNoOfLinesPerInstruction;
    }

    public Boolean getVerifyBatchNo() {
        return verifyBatchNo;
    }

    public void setVerifyBatchNo(Boolean verifyBatchNo) {
        this.verifyBatchNo = verifyBatchNo;
    }

    public Boolean getVerifyExpiryDate() {
        return verifyExpiryDate;
    }

    public void setVerifyExpiryDate(Boolean verifyExpiryDate) {
        this.verifyExpiryDate = verifyExpiryDate;
    }

    public Boolean getVerifyManufactureDate() {
        return verifyManufactureDate;
    }

    public void setVerifyManufactureDate(Boolean verifyManufactureDate) {
        this.verifyManufactureDate = verifyManufactureDate;
    }

    public Boolean getVerifyPackNumber() {
        return verifyPackNumber;
    }

    public void setVerifyPackNumber(Boolean verifyPackNumber) {
        this.verifyPackNumber = verifyPackNumber;
    }

    public Boolean getVerifySerialNo() {
        return verifySerialNo;
    }

    public void setVerifySerialNo(Boolean verifySerialNo) {
        this.verifySerialNo = verifySerialNo;
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
}
