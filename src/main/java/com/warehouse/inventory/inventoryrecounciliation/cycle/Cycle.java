package com.warehouse.inventory.inventoryrecounciliation.cycle;

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
@Table(name = "inventory_cycle", schema = "public")
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cycle_no")
    private String cycleNo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cycle_id", referencedColumnName = "id")
    @NotNull
    private List<CycleSku> cycleSku;

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

    @Column(name = "include_skus_moved_in")
    private Long includeSkusThatMovedIn;

    @Column(name = "cycle_count_instruction_name")
    private String cycleCountInstructionName;

    @Column(name = "is_blind_count")
    private Boolean isBlindCount = true;

    @Column(name = "is_dirty")
    private Boolean isDirty = false;

    @Column(name = "is_existing_cycle_count_name")
    private Boolean isExistingCycleCountName = true;

    @Column(name = "is_rank_a")
    private Boolean isRankA = false;

    @Column(name = "is_rank_b")
    private Boolean isRankB = false;

    @Column(name = "is_rank_c")
    private Boolean isRankC = false;

    @Column(name = "is_rank_d")
    private Boolean isRankD = false;

    @Column(name = "is_rank_e")
    private Boolean isRankE = false;

    @Column(name = "is_recount_on_batch_no")
    private Boolean isRecountOnBatchNo = false;

    @Column(name = "is_recount_on_expiry_date")
    private Boolean isRecountOnExpiryDate = false;

    @Column(name = "is_recount_on_location")
    private Boolean isRecountOnLocation = false;

    @Column(name = "is_recount_on_manufacture_date")
    private Boolean isRecountOnManufactureDate = false;

    @Column(name = "is_recount_on_serial_no")
    private Boolean isRecountOnSerialNo = false;

    @Column(name = "is_recount_required")
    private Boolean isRecountRequired = true;

    @Column(name = "is_selected")
    private Boolean isSelected;

    @Column(name = "location_hierarchy_id")
    private String locationHierarchyId;

    @Column(name = "max_no_of_lines_per_instruction")
    private Long maxNoOfLinesPerInstruction;

    @Column(name = "locations_movedInUnit")
    private DurationTimeEnum skusMovedInUnit;

    @Column(name = "verify_batch_no")
    private Boolean verifyBatchNo = true;

    @Column(name = "verify_expiry_date")
    private Boolean verifyExpiryDate = true;

    @Column(name = "verify_manufacture_date")
    private Boolean verifyManufactureDate = true;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCycleNo() {
        return cycleNo;
    }

    public void setCycleNo(String cycleNo) {
        this.cycleNo = cycleNo;
    }

    public List<CycleSku> getCycleSku() {
        return cycleSku;
    }

    public void setCycleSku(List<CycleSku> cycleSku) {
        this.cycleSku = cycleSku;
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

    public Long getIncludeSkusThatMovedIn() {
        return includeSkusThatMovedIn;
    }

    public void setIncludeSkusThatMovedIn(Long includeSkusThatMovedIn) {
        this.includeSkusThatMovedIn = includeSkusThatMovedIn;
    }

    public String getCycleCountInstructionName() {
        return cycleCountInstructionName;
    }

    public void setCycleCountInstructionName(String cycleCountInstructionName) {
        this.cycleCountInstructionName = cycleCountInstructionName;
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

    public Boolean getExistingCycleCountName() {
        return isExistingCycleCountName;
    }

    public void setExistingCycleCountName(Boolean existingCycleCountName) {
        isExistingCycleCountName = existingCycleCountName;
    }

    public Boolean getRankA() {
        return isRankA;
    }

    public void setRankA(Boolean rankA) {
        isRankA = rankA;
    }

    public Boolean getRankB() {
        return isRankB;
    }

    public void setRankB(Boolean rankB) {
        isRankB = rankB;
    }

    public Boolean getRankC() {
        return isRankC;
    }

    public void setRankC(Boolean rankC) {
        isRankC = rankC;
    }

    public Boolean getRankD() {
        return isRankD;
    }

    public void setRankD(Boolean rankD) {
        isRankD = rankD;
    }

    public Boolean getRankE() {
        return isRankE;
    }

    public void setRankE(Boolean rankE) {
        isRankE = rankE;
    }

    public Boolean getRecountOnBatch() {
        return isRecountOnBatchNo;
    }

    public void setRecountOnBatch(Boolean recountOnBatch) {
        isRecountOnBatchNo = recountOnBatch;
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

    public String getLocationHierarchyId() {
        return locationHierarchyId;
    }

    public void setLocationHierarchyId(String locationHierarchyID) {
        this.locationHierarchyId = locationHierarchyID;
    }

    public Long getMaxNoOfLinesPerInstruction() {
        return maxNoOfLinesPerInstruction;
    }

    public void setMaxNoOfLinesPerInstruction(Long maxNoOfLinesPerInstruction) {
        this.maxNoOfLinesPerInstruction = maxNoOfLinesPerInstruction;
    }

    public DurationTimeEnum getSkusMovedInUnit() {
        return skusMovedInUnit;
    }

    public void setSkusMovedInUnit(DurationTimeEnum skusMovedInUnit) {
        this.skusMovedInUnit = skusMovedInUnit;
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

    @Transient
    private String modifiedByUserName;

    public Cycle() {
    }

    public Cycle(Long id, String cycleNo, @NotNull List<CycleSku> cycleSku, PrimaryCompany primaryCompany,
            @NotNull Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode, Warehouse warehouse,
            @NotNull Long warehouseId, String warehouseName, String warehouseCode, CostBucket costBucket,
            @NotNull Long costBucketId, String costBucketName, String costBucketCode, Long includeSkusThatMovedIn,
            String cycleCountInstructionName, Boolean isBlindCount, Boolean isDirty, Boolean isExistingCycleCountName,
            Boolean isRankA, Boolean isRankB, Boolean isRankC, Boolean isRankD, Boolean isRankE,
            Boolean isRecountOnBatchNo, Boolean isRecountOnExpiryDate, Boolean isRecountOnLocation,
            Boolean isRecountOnManufactureDate, Boolean isRecountOnSerialNo, Boolean isRecountRequired,
            Boolean isSelected, String locationHierarchyId, Long maxNoOfLinesPerInstruction,
            DurationTimeEnum skusMovedInUnit, Boolean verifyBatchNo, Boolean verifyExpiryDate,
            Boolean verifyManufactureDate, Boolean verifySerialNo, Timestamp createdAt, Timestamp modifiedAt,
            User createdBy, Long createdById, User modifiedBy, Long modifiedById, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.cycleNo = cycleNo;
        this.cycleSku = cycleSku;
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
        this.includeSkusThatMovedIn = includeSkusThatMovedIn;
        this.cycleCountInstructionName = cycleCountInstructionName;
        this.isBlindCount = isBlindCount;
        this.isDirty = isDirty;
        this.isExistingCycleCountName = isExistingCycleCountName;
        this.isRankA = isRankA;
        this.isRankB = isRankB;
        this.isRankC = isRankC;
        this.isRankD = isRankD;
        this.isRankE = isRankE;
        this.isRecountOnBatchNo = isRecountOnBatchNo;
        this.isRecountOnExpiryDate = isRecountOnExpiryDate;
        this.isRecountOnLocation = isRecountOnLocation;
        this.isRecountOnManufactureDate = isRecountOnManufactureDate;
        this.isRecountOnSerialNo = isRecountOnSerialNo;
        this.isRecountRequired = isRecountRequired;
        this.isSelected = isSelected;
        this.locationHierarchyId = locationHierarchyId;
        this.maxNoOfLinesPerInstruction = maxNoOfLinesPerInstruction;
        this.skusMovedInUnit = skusMovedInUnit;
        this.verifyBatchNo = verifyBatchNo;
        this.verifyExpiryDate = verifyExpiryDate;
        this.verifyManufactureDate = verifyManufactureDate;
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

    @Override
    public String toString() {
        return "Cycle{" + "id=" + id + ", cycleNo='" + cycleNo + '\'' + ", cycleSku=" + cycleSku + ", primaryCompany="
                + primaryCompany + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName='"
                + primaryCompanyName + '\'' + ", primaryCompanyCode='" + primaryCompanyCode + '\'' + ", warehouse="
                + warehouse + ", warehouseId=" + warehouseId + ", warehouseName='" + warehouseName + '\''
                + ", warehouseCode='" + warehouseCode + '\'' + ", costBucket=" + costBucket + ", costBucketId="
                + costBucketId + ", costBucketName='" + costBucketName + '\'' + ", costBucketCode='" + costBucketCode
                + '\'' + ", includeSkusThatMovedIn=" + includeSkusThatMovedIn + ", cycleCountInstructionName='"
                + cycleCountInstructionName + '\'' + ", isBlindCount=" + isBlindCount + ", isDirty=" + isDirty
                + ", isExistingCycleCountName=" + isExistingCycleCountName + ", isRankA=" + isRankA + ", isRankB="
                + isRankB + ", isRankC=" + isRankC + ", isRankD=" + isRankD + ", isRankE=" + isRankE
                + ", isRecountOnBatchNo=" + isRecountOnBatchNo + ", isRecountOnExpiryDate=" + isRecountOnExpiryDate
                + ", isRecountOnLocation=" + isRecountOnLocation + ", isRecountOnManufactureDate="
                + isRecountOnManufactureDate + ", isRecountOnSerialNo=" + isRecountOnSerialNo + ", isRecountRequired="
                + isRecountRequired + ", isSelected=" + isSelected + ", locationHierarchyId='" + locationHierarchyId
                + '\'' + ", maxNoOfLinesPerInstruction=" + maxNoOfLinesPerInstruction + ", skusMovedInUnit="
                + skusMovedInUnit + ", verifyBatchNo=" + verifyBatchNo + ", verifyExpiryDate=" + verifyExpiryDate
                + ", verifyManufactureDate=" + verifyManufactureDate + ", verifySerialNo=" + verifySerialNo
                + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", createdByUserName='" + createdByUserName + '\'' + ", modifiedByUserName='" + modifiedByUserName
                + '\'' + '}';
    }
}
