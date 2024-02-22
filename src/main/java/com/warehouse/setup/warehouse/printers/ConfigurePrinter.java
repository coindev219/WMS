package com.warehouse.setup.warehouse.printers;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.enums.PrinterLabelTypeEnum;
import com.warehouse.enums.PrinterOperationTypeEnum;
import com.warehouse.setup.location.Location;
import com.warehouse.setup.warehouse.Warehouse;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "configure_printer", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "warehouse_id" }) })
public class ConfigurePrinter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_dock")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Location fromDock;

    @Column(name = "from_dock", insertable = false, updatable = false)
    @NotNull
    private Long fromDockId;

    @Transient
    private String fromDockCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_dock")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Location toDock;

    @Column(name = "to_dock", insertable = false, updatable = false)
    @NotNull
    private Long toDockId;


    @Transient
    private String toDockCode;

    @Size(max = 250)
    @NotBlank(message = "{app.printer_name}")
    private String name;

    @Size(max = 100)
    @NotBlank(message = "{app.ipaddress}")
    @Column(name = "ipaddress")
    private String ipAddress;

    @Size(max = 100)
    @NotBlank(message = "{app.port_number}")
    @Column(name = "port_number")
    private String portNumber;

    @NotNull
    @Column(name = "operation_type")
    private PrinterOperationTypeEnum operationType;

    @NotNull
    @Column(name = "label_type")
    private PrinterLabelTypeEnum labelType;

    @Column(name = "is_default_printer")
    private Boolean isDefaultPrinter;

    private Boolean usable;

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

    public ConfigurePrinter() {
    }

    public ConfigurePrinter(Long id, Long warehouseId, String warehouseName, String warehouseCode, Long fromDockId,
            String fromDockCode, Long toDockId, String toDockCode, String name,
            String ipAddress, String portNumber, PrinterOperationTypeEnum operationType, PrinterLabelTypeEnum labelType,
            Boolean isDefaultPrinter, Boolean usable, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.fromDockId = fromDockId;
        this.fromDockCode = fromDockCode;
        this.toDockId = toDockId;
        this.toDockCode = toDockCode;
        this.name = name;
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.operationType = operationType;
        this.labelType = labelType;
        this.isDefaultPrinter = isDefaultPrinter;
        this.usable = usable;
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

    public Location getFromDock() {
        return fromDock;
    }

    public void setFromDock(Location fromDock) {
        this.fromDock = fromDock;
    }

    public Long getFromDockId() {
        return fromDockId;
    }

    public void setFromDockId(Long fromDockId) {
        this.fromDockId = fromDockId;
    }

    

    public String getFromDockCode() {
        return fromDockCode;
    }

    public void setFromDockCode(String fromDockCode) {
        this.fromDockCode = fromDockCode;
    }

    public Location getToDock() {
        return toDock;
    }

    public void setToDock(Location toDock) {
        this.toDock = toDock;
    }

    public Long getToDockId() {
        return toDockId;
    }

    public void setToDockId(Long toDockId) {
        this.toDockId = toDockId;
    }

    

    public String getToDockCode() {
        return toDockCode;
    }

    public void setToDockCode(String toDockCode) {
        this.toDockCode = toDockCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public PrinterOperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(PrinterOperationTypeEnum operationType) {
        this.operationType = operationType;
    }

    public PrinterLabelTypeEnum getLabelType() {
        return labelType;
    }

    public void setLabelType(PrinterLabelTypeEnum labelType) {
        this.labelType = labelType;
    }

    public Boolean getIsDefaultPrinter() {
        return isDefaultPrinter;
    }

    public void setIsDefaultPrinter(Boolean isDefaultPrinter) {
        this.isDefaultPrinter = isDefaultPrinter;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
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

    

    @Override
    public String toString() {
        return "ConfigurePrinter [createdAt=" + createdAt + ", createdBy=" + createdBy + ", createdById=" + createdById
                + ", createdByUserName=" + createdByUserName + ", fromDock=" + fromDock + ", fromDockCode="
                + fromDockCode + ", fromDockId=" + fromDockId + ", id=" + id + ", ipAddress=" + ipAddress
                + ", isDefaultPrinter=" + isDefaultPrinter + ", labelType=" + labelType + ", modifiedAt=" + modifiedAt
                + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
                + modifiedByUserName + ", name=" + name + ", operationType=" + operationType + ", portNumber="
                + portNumber + ", toDock=" + toDock + ", toDockCode=" + toDockCode + ", toDockId=" + toDockId
                + ", usable=" + usable + ", warehouse=" + warehouse + ", warehouseCode=" + warehouseCode
                + ", warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + "]";
    }

    

    

}
