package com.warehouse.setup.location.hierarchy;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.enums.DataTypeEnum;
import com.warehouse.setup.location.locationtype.LocationType;
import com.warehouse.setup.warehouse.Warehouse;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "location_hierarchy", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "warehouse_id", "location_type_id" }),
        @UniqueConstraint(columnNames = { "code", "warehouse_id", "location_type_id" }), })
public class LocationHierarchy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 250)
    @NotBlank(message = "{app.name}")
    private String name;

    @Size(max = 100)
    @NotBlank(message = "{app.code}")
    private String code;

    @NumberFormat(style = Style.NUMBER)
    @Min(value = 1, message = "{app.no_of_digits}")
    @Column(name = "no_of_digits")
    private Long noOfDigits;

    @Size(max = 50)
    private String prefix;

    @Size(max = 50)
    private String suffix;

    private Boolean usable;

    @NotNull
    private DataTypeEnum dataType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_hierarchy_id")
    @JsonIgnore
    private LocationHierarchy upperHierarchy;

    @Column(name = "upper_hierarchy_id", insertable = false, updatable = false)
    private Long upperHierarchyId;
    

    @ManyToOne
    @JoinColumn(name = "location_type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private LocationType locationType;

    @NotNull
    @Column(name = "location_type_id", insertable = false, updatable = false)
    private Long locationTypeId;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Warehouse warehouse;

    @NotNull
    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Long warehouseId;


    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Timestamp modifiedAt;

    @Transient
    private String locationTypeName;

    @Transient
    private String warehouseName;

    @Transient
    private String warehouseCode;

    @Transient
    private String upperHierarchyName;

    @Transient
    private String upperHierarchyCode;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNoOfDigits() {
        return noOfDigits;
    }

    public void setNoOfDigits(Long noOfDigits) {
        this.noOfDigits = noOfDigits;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Boolean isUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    public DataTypeEnum getDataType() {
        return dataType;
    }

    public void setDataType(DataTypeEnum dataType) {
        this.dataType = dataType;
    }

    public LocationHierarchy getUpperHierarchy() {
        return upperHierarchy;
    }

    public void setUpperHierarchy(LocationHierarchy upperHierarchy) {
        this.upperHierarchy = upperHierarchy;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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

    public Boolean getUsable() {
        return usable;
    }

    public String getLocationTypeName() {
        return locationTypeName;
    }

    public void setLocationTypeName(String locationTypeName) {
        this.locationTypeName = locationTypeName;
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

    public String getUpperHierarchyName() {
        return upperHierarchyName;
    }

    public void setUpperHierarchyName(String upperHierarchyName) {
        this.upperHierarchyName = upperHierarchyName;
    }

    public String getUpperHierarchyCode() {
        return upperHierarchyCode;
    }

    public void setUpperHierarchyCode(String upperHierarchyCode) {
        this.upperHierarchyCode = upperHierarchyCode;
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

    public LocationHierarchy() {
    }

    public LocationHierarchy(Long id, String name, String code, Long noOfDigits, String prefix, String suffix,
            Boolean usable, DataTypeEnum dataType, Long upperHierarchyId, Long locationTypeId, Long warehouseId,
            Date createdAt, Date modifiedAt, String locationTypeName, String warehouseName, String warehouseCode,
            String upperHierarchyName, String upperHierarchyCode, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.noOfDigits = noOfDigits;
        this.prefix = prefix;
        this.suffix = suffix;
        this.usable = usable;
        this.dataType = dataType;
        this.upperHierarchyId = upperHierarchyId;
        this.locationTypeId = locationTypeId;
        this.warehouseId = warehouseId;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.locationTypeName = locationTypeName;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.upperHierarchyName = upperHierarchyName;
        this.upperHierarchyCode = upperHierarchyCode;
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    @Override
    public String toString() {
        return "LocationHierarchy [code=" + code + ", createdAt=" + createdAt + ", dataType=" + dataType + ", id=" + id
                + ", locationType=" + locationType + ", modifiedAt=" + modifiedAt + ", name=" + name + ", noOfDigits="
                + noOfDigits + ", prefix=" + prefix + ", suffix=" + suffix + ", upperHierarchy=" + upperHierarchy
                + ", usable=" + usable + ", warehouse=" + warehouse + "]";
    }

    public Long getUpperHierarchyId() {
        return upperHierarchyId;
    }

    public void setUpperHierarchyId(Long upperHierarchyId) {
        this.upperHierarchyId = upperHierarchyId;
    }

    public Long getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

}
