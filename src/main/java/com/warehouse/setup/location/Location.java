package com.warehouse.setup.location;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.location.dimension.DimensionDefinition;
import com.warehouse.setup.location.hierarchy.LocationHierarchy;
import com.warehouse.setup.location.restriction.LocationRestriction;
import com.warehouse.setup.location.storagecategory.LocationStorageCategory;
import com.warehouse.setup.warehouse.Warehouse;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "location", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code", "location_hierarchy_id" }) })
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @NotBlank(message = "{app.code}")
    private String code;

    @ManyToOne
    @JoinColumn(name = "location_hierarchy_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private LocationHierarchy locationHierarchy;

    @NotNull
    @Column(name = "location_hierarchy_id", insertable = false, updatable = false)
    private Long locationHierarchyId;

    @Transient
    private String locationHierarchyName;

    @Transient
    private String locationHierarchyCode;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Warehouse warehouse;

    @Transient
    private String warehouseName;

    @Transient
    private String warehouseCode;

    @NotNull
    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Long warehouseId;

    @ManyToOne
    @JoinColumn(name = "primary_company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PrimaryCompany primaryCompany;

    @Transient
    private String primaryCompanyName;

    @Transient
    private String primaryCompanyCode;

    @NotNull
    @Column(name = "primary_company_id", insertable = false, updatable = false)
    private Long primaryCompanyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dimension_definition_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DimensionDefinition dimensionDefinition;

    @Column(name = "dimension_definition_id", insertable = false, updatable = false)
    private Long dimensionDefinitionId;

    @Transient
    private String dimensionDefinitionName;

    @Transient
    private String dimensionDefinitionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_restriction_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private LocationRestriction locationRestriction;

    @Column(name = "location_restriction_id", insertable = false, updatable = false)
    private Long locationRestrictionId;

    @Transient
    private String locationRestrictionName;

    @Transient
    private String locationRestrictionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_storage_category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private LocationStorageCategory locationStorageCategory;

    @Column(name = "location_storage_category_id", insertable = false, updatable = false)
    private Long locationStorageCategoryId;

    @Transient
    private String locationStorageName;

    @Transient
    private String locationStorageCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_location_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Location location;

    @Column(name = "upper_location_id", insertable = false, updatable = false)
    private Long upperLocationId;

    @Transient
    private String upperLocationCode;

    @Column(name = "outer_location")
    private String outerLocation;

    private Boolean usable;

    @Column(name = "pick_face")
    private Boolean pickFace;

    @Column(name = "option_a")
    private Boolean optionA;

    @Column(name = "option_b")
    private Boolean optionB;

    @Column(name = "option_c")
    private Boolean optionC;

    @Column(name = "option_d")
    private Boolean optionD;

    @Column(name = "option_e")
    private Boolean optionE;

    @Column(name = "restrict_suggestion")
    private Boolean restrictSuggestion;

    @Column(name = "restrict_allocation")
    private Boolean restrictAllocation;

    @Column(name = "is_pack_location")
    private Boolean isPackLocation;

    @Column(name = "binning_seq_no")
    private Integer binningSequenceNo;

    @Column(name = "picking_seq_no")
    private Integer pickingSequenceNo;

    @Column(name = "location_group")
    private String locationGroup;

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

    public Location() {
    }

    public Location(Long id, String code, Long locationHierarchyId, String locationHierarchyName,
            String locationHierarchyCode, String warehouseName, String warehouseCode, Long warehouseId,
            String primaryCompanyName, String primaryCompanyCode, Long primaryCompanyId, Long dimensionDefinitionId,
            String dimensionDefinitionName, String dimensionDefinitionCode, Long locationRestrictionId,
            String locationRestrictionName, String locationRestrictionCode, Long locationStorageCategoryId,
            String locationStorageName, String locationStorageCode, Long upperLocationId,
            String upperLocationCode, String outerLocation, Boolean usable, Boolean pickFace,
            Boolean optionA, Boolean optionB, Boolean optionC, Boolean optionD, Boolean optionE,
            Boolean restrictSuggestion, Boolean restrictAllocation, Boolean isPackLocation, Integer binningSequenceNo,
            Integer pickingSequenceNo, String locationGroup, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {

        this.id = id;
        this.code = code;
        this.locationHierarchyId = locationHierarchyId;
        this.locationHierarchyName = locationHierarchyName;
        this.locationHierarchyCode = locationHierarchyCode;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.warehouseId = warehouseId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyId = primaryCompanyId;
        this.dimensionDefinitionId = dimensionDefinitionId;
        this.dimensionDefinitionName = dimensionDefinitionName;
        this.dimensionDefinitionCode = dimensionDefinitionCode;
        this.locationRestrictionId = locationRestrictionId;
        this.locationRestrictionName = locationRestrictionName;
        this.locationRestrictionCode = locationRestrictionCode;
        this.locationStorageCategoryId = locationStorageCategoryId;
        this.locationStorageName = locationStorageName;
        this.locationStorageCode = locationStorageCode;
        this.upperLocationId = upperLocationId;
        this.upperLocationCode = upperLocationCode;
        this.outerLocation = outerLocation;
        this.usable = usable;
        this.pickFace = pickFace;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.restrictSuggestion = restrictSuggestion;
        this.restrictAllocation = restrictAllocation;
        this.isPackLocation = isPackLocation;
        this.binningSequenceNo = binningSequenceNo;
        this.pickingSequenceNo = pickingSequenceNo;
        this.locationGroup = locationGroup;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    public String getLocationHierarchyName() {
        return locationHierarchyName;
    }

    public void setLocationHierarchyName(String locationHierarchyName) {
        this.locationHierarchyName = locationHierarchyName;
    }

    public String getLocationHierarchyCode() {
        return locationHierarchyCode;
    }

    public void setLocationHierarchyCode(String locationHierarchyCode) {
        this.locationHierarchyCode = locationHierarchyCode;
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

    public PrimaryCompany getPrimaryCompany() {
        return primaryCompany;
    }

    public void setPrimaryCompany(PrimaryCompany primaryCompany) {
        this.primaryCompany = primaryCompany;
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

    public String getDimensionDefinitionName() {
        return dimensionDefinitionName;
    }

    public void setDimensionDefinitionName(String dimensionDefinitionName) {
        this.dimensionDefinitionName = dimensionDefinitionName;
    }

    public String getDimensionDefinitionCode() {
        return dimensionDefinitionCode;
    }

    public void setDimensionDefinitionCode(String dimensionDefinitionCode) {
        this.dimensionDefinitionCode = dimensionDefinitionCode;
    }

    public String getLocationRestrictionName() {
        return locationRestrictionName;
    }

    public void setLocationRestrictionName(String locationRestrictionName) {
        this.locationRestrictionName = locationRestrictionName;
    }

    public String getLocationRestrictionCode() {
        return locationRestrictionCode;
    }

    public void setLocationRestrictionCode(String locationRestrictionCode) {
        this.locationRestrictionCode = locationRestrictionCode;
    }

    public String getLocationStorageName() {
        return locationStorageName;
    }

    public void setLocationStorageName(String locationStorageName) {
        this.locationStorageName = locationStorageName;
    }

    public String getLocationStorageCode() {
        return locationStorageCode;
    }

    public void setLocationStorageCode(String locationStorageCode) {
        this.locationStorageCode = locationStorageCode;
    }

    public String getUpperLocationCode() {
        return upperLocationCode;
    }

    public void setUpperLocationCode(String upperLocationCode) {
        this.upperLocationCode = upperLocationCode;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public PrimaryCompany getPrimarycompany() {
        return primaryCompany;
    }

    public void setPrimarycompany(PrimaryCompany primaryCompany) {
        this.primaryCompany = primaryCompany;
    }

    public Long getPrimaryCompanyId() {
        return primaryCompanyId;
    }

    public void setPrimaryCompanyId(Long primaryCompanyId) {
        this.primaryCompanyId = primaryCompanyId;
    }

    public DimensionDefinition getDimensionDefinition() {
        return dimensionDefinition;
    }

    public void setDimensionDefinition(DimensionDefinition dimensionDefinition) {
        this.dimensionDefinition = dimensionDefinition;
    }

    public Long getDimensionDefinitionId() {
        return dimensionDefinitionId;
    }

    public void setDimensionDefinitionId(Long dimensionDefinitionId) {
        this.dimensionDefinitionId = dimensionDefinitionId;
    }

    public LocationRestriction getLocationRestriction() {
        return locationRestriction;
    }

    public void setLocationRestriction(LocationRestriction locationRestriction) {
        this.locationRestriction = locationRestriction;
    }

    public Long getLocationRestrictionId() {
        return locationRestrictionId;
    }

    public void setLocationRestrictionId(Long locationRestrictionId) {
        this.locationRestrictionId = locationRestrictionId;
    }

    public LocationStorageCategory getLocationStorageCategory() {
        return locationStorageCategory;
    }

    public void setLocationStorageCategory(LocationStorageCategory locationStorageCategory) {
        this.locationStorageCategory = locationStorageCategory;
    }

    public Long getLocationStorageCategoryId() {
        return locationStorageCategoryId;
    }

    public void setLocationStorageCategoryId(Long locationStorageCategoryId) {
        this.locationStorageCategoryId = locationStorageCategoryId;
    }

    public LocationHierarchy getLocationHierarchy() {
        return locationHierarchy;
    }

    public void setLocationHierarchy(LocationHierarchy locationHierarchy) {
        this.locationHierarchy = locationHierarchy;
    }

    public Long getLocationHierarchyId() {
        return locationHierarchyId;
    }

    public void setLocationHierarchyId(Long locationHierarchyId) {
        this.locationHierarchyId = locationHierarchyId;
    }

    public String getOuterLocation() {
        return outerLocation;
    }

    public void setOuterLocation(String outerLocation) {
        this.outerLocation = outerLocation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getUpperLocationId() {
        return upperLocationId;
    }

    public void setUpperLocationId(Long upperLocationId) {
        this.upperLocationId = upperLocationId;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    public Boolean getPickFace() {
        return pickFace;
    }

    public void setPickFace(Boolean pickFace) {
        this.pickFace = pickFace;
    }

    public Boolean getOptionA() {
        return optionA;
    }

    public void setOptionA(Boolean optionA) {
        this.optionA = optionA;
    }

    public Boolean getOptionB() {
        return optionB;
    }

    public void setOptionB(Boolean optionB) {
        this.optionB = optionB;
    }

    public Boolean getOptionC() {
        return optionC;
    }

    public void setOptionC(Boolean optionC) {
        this.optionC = optionC;
    }

    public Boolean getOptionD() {
        return optionD;
    }

    public void setOptionD(Boolean optionD) {
        this.optionD = optionD;
    }

    public Boolean getOptionE() {
        return optionE;
    }

    public void setOptionE(Boolean optionE) {
        this.optionE = optionE;
    }

    public Boolean getRestrictSuggestion() {
        return restrictSuggestion;
    }

    public void setRestrictSuggestion(Boolean restrictSuggestion) {
        this.restrictSuggestion = restrictSuggestion;
    }

    public Boolean getRestrictAllocation() {
        return restrictAllocation;
    }

    public void setRestrictAllocation(Boolean restrictAllocation) {
        this.restrictAllocation = restrictAllocation;
    }

    public Boolean getIsPackLocation() {
        return isPackLocation;
    }

    public void setIsPackLocation(Boolean isPackLocation) {
        this.isPackLocation = isPackLocation;
    }

    public Integer getBinningSequenceNo() {
        return binningSequenceNo;
    }

    public void setBinningSequenceNo(Integer binningSequenceNo) {
        this.binningSequenceNo = binningSequenceNo;
    }

    public Integer getPickingSequenceNo() {
        return pickingSequenceNo;
    }

    public void setPickingSequenceNo(Integer pickingSequenceNo) {
        this.pickingSequenceNo = pickingSequenceNo;
    }

    public String getLocationGroup() {
        return locationGroup;
    }

    public void setLocationGroup(String locationGroup) {
        this.locationGroup = locationGroup;
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

    @Override
    public String toString() {
        return "Location [binningSequenceNo=" + binningSequenceNo + ", code=" + code + ", createdAt=" + createdAt
                + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", dimensionDefinition=" + dimensionDefinition + ", dimensionDefinitionCode="
                + dimensionDefinitionCode + ", dimensionDefinitionId=" + dimensionDefinitionId
                + ", dimensionDefinitionName=" + dimensionDefinitionName + ", id=" + id + ", isPackLocation="
                + isPackLocation + ", location=" + location + ", locationGroup=" + locationGroup
                + ", locationHierarchy=" + locationHierarchy + ", locationHierarchyCode=" + locationHierarchyCode
                + ", locationHierarchyId=" + locationHierarchyId + ", locationHierarchyName=" + locationHierarchyName
                + ", locationRestriction=" + locationRestriction + ", locationRestrictionCode="
                + locationRestrictionCode + ", locationRestrictionId=" + locationRestrictionId
                + ", locationRestrictionName=" + locationRestrictionName + ", locationStorageCategory="
                + locationStorageCategory + ", locationStorageCategoryId=" + locationStorageCategoryId
                + ", locationStorageCode=" + locationStorageCode + ", locationStorageName=" + locationStorageName
                + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", modifiedByUserName=" + modifiedByUserName + ", optionA=" + optionA + ", optionB=" + optionB
                + ", optionC=" + optionC + ", optionD=" + optionD + ", optionE=" + optionE + ", outerLocation="
                + outerLocation + ", pickFace=" + pickFace + ", pickingSequenceNo=" + pickingSequenceNo
                + ", primaryCompany=" + primaryCompany + ", primaryCompanyCode=" + primaryCompanyCode
                + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName
                + ", restrictAllocation=" + restrictAllocation + ", restrictSuggestion=" + restrictSuggestion
                + ", upperLocationCode=" + upperLocationCode + ", upperLocationId=" + upperLocationId + ", usable="
                + usable + ", warehouse=" + warehouse + ", warehouseCode=" + warehouseCode + ", warehouseId="
                + warehouseId + ", warehouseName=" + warehouseName + "]";
    }

    

    

}
