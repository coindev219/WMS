package com.warehouse.sales.salesorder.vehicleinward;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.administration.user.User;
import com.warehouse.enums.VehicleTypeEnum;
import com.warehouse.state.State;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "vehicle_inward", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = { "fc_no" }))
public class VehicleInward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_reward_id", referencedColumnName = "id")
    @NotNull
    private List<VehicleInwardLRNumber> vehicleInwardLRNumbersList;

    @NotEmpty
    @Column(name = "fc_no")
    private String fcNo;

    @NotEmpty
    @Column(name = "vehicle_no")
    private String vehicleNo;

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

    @Column(name = "kam")
    private String kam;

    @Column(name = "kae")
    private String kae;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "indented_weight")
    private Float indentedWeight;

    @Column(name = "indented_length")
    private Float indentedLength;

    @Column(name = "in_date_time")
    private Date inDateTime;

    @Column(name = "gross_weight")
    private Float grossWeight;

    @Column(name = "empty_weight")
    private Float emptyWeight;

    @Column(name = "load_weight")
    private Float loadWeight;

    @Column(name = "planned_capacity")
    private Float plannedCapacity;

    @Column(name = "load_length")
    private Float loadLengthenLength;

    @Column(name = "load_width")
    private Float loadWidth;

    @Column(name = "driver_name")
    private Float driverName;

    @Column(name = "driver_mobile")
    private Float driverMobile;

    @Column(name = "driver_licence_no")
    private Float driverLicenceNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "licence_issue_state")
    @JsonIgnore
    private State state;

    @Column(name = "licence_issue_state", insertable = false, updatable = false)
    private Long stateId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "driver_address")
    private String driverAddress;

    @Column(name = "check_post")
    private String checkPost;

    @Column(name = "lr_quantity")
    private Integer lrQuantity;

    @Column(name = "vehicle_type")
    private VehicleTypeEnum vehicleType;

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


    public VehicleInward() {
    }

    public VehicleInward(String fcNo) {
        this.fcNo = fcNo;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFcNo() {
        return fcNo;
    }

    public void setFcNo(String fcNo) {
        this.fcNo = fcNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
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

    public String getKam() {
        return kam;
    }

    public void setKam(String kam) {
        this.kam = kam;
    }

    public String getKae() {
        return kae;
    }

    public void setKae(String kae) {
        this.kae = kae;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Float getIndentedWeight() {
        return indentedWeight;
    }

    public void setIndentedWeight(Float indentedWeight) {
        this.indentedWeight = indentedWeight;
    }

    public Float getIndentedLength() {
        return indentedLength;
    }

    public void setIndentedLength(Float indentedLength) {
        this.indentedLength = indentedLength;
    }

    public Date getInDateTime() {
        return inDateTime;
    }

    public void setInDateTime(Date inDateTime) {
        this.inDateTime = inDateTime;
    }

    public Float getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Float grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Float getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(Float emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    public Float getLoadWeight() {
        return loadWeight;
    }

    public void setLoadWeight(Float loadWeight) {
        this.loadWeight = loadWeight;
    }

    public Float getPlannedCapacity() {
        return plannedCapacity;
    }

    public void setPlannedCapacity(Float plannedCapacity) {
        this.plannedCapacity = plannedCapacity;
    }

    public Float getLoadLengthenLength() {
        return loadLengthenLength;
    }

    public void setLoadLengthenLength(Float loadLengthenLength) {
        this.loadLengthenLength = loadLengthenLength;
    }

    public Float getLoadWidth() {
        return loadWidth;
    }

    public void setLoadWidth(Float loadWidth) {
        this.loadWidth = loadWidth;
    }

    public Float getDriverName() {
        return driverName;
    }

    public void setDriverName(Float driverName) {
        this.driverName = driverName;
    }

    public Float getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(Float driverMobile) {
        this.driverMobile = driverMobile;
    }

    public Float getDriverLicenceNo() {
        return driverLicenceNo;
    }

    public void setDriverLicenceNo(Float driverLicenceNo) {
        this.driverLicenceNo = driverLicenceNo;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getCheckPost() {
        return checkPost;
    }

    public void setCheckPost(String checkPost) {
        this.checkPost = checkPost;
    }

    public Integer getLrQuantity() {
        return lrQuantity;
    }

    public void setLrQuantity(Integer lrQuantity) {
        this.lrQuantity = lrQuantity;
    }

    public VehicleTypeEnum getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeEnum vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<VehicleInwardLRNumber> getVehicleInwardLRNumbersList() {
        return vehicleInwardLRNumbersList;
    }

    public void setVehicleInwardLRNumbersList(List<VehicleInwardLRNumber> vehicleInwardLRNumbersList) {
        this.vehicleInwardLRNumbersList = vehicleInwardLRNumbersList;
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
        return "VehicleInward [checkPost=" + checkPost + ", createdAt=" + createdAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", driverAddress="
                + driverAddress + ", driverLicenceNo=" + driverLicenceNo + ", driverMobile=" + driverMobile
                + ", driverName=" + driverName + ", email=" + email + ", emptyWeight=" + emptyWeight + ", fcNo=" + fcNo
                + ", freighter=" + freighter + ", freighterCode=" + freighterCode + ", freighterId=" + freighterId
                + ", freighterName=" + freighterName + ", grossWeight=" + grossWeight + ", id=" + id + ", inDateTime="
                + inDateTime + ", indentedLength=" + indentedLength + ", indentedWeight=" + indentedWeight + ", kae="
                + kae + ", kam=" + kam + ", licenseIssueStateId=" + stateId + ", loadLengthenLength="
                + loadLengthenLength + ", loadWeight=" + loadWeight + ", loadWidth=" + loadWidth + ", lrQuantity="
                + lrQuantity + ", mobileNo=" + mobileNo + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy
                + ", modifiedById=" + modifiedById + ", modifiedByUserName=" + modifiedByUserName + ", plannedCapacity="
                + plannedCapacity + ", state=" + state + ", vehicleInwardLRNumbersList=" + vehicleInwardLRNumbersList
                + ", vehicleNo=" + vehicleNo + ", vehicleType=" + vehicleType + "]";
    }

    

}
