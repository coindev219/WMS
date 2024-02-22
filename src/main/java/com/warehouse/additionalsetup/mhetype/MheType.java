package com.warehouse.additionalsetup.mhetype;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.enums.UomLengthEnum;
import com.warehouse.enums.UomVolumeEnum;
import com.warehouse.enums.UomWeightEnum;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "mhe_type", schema = "public")
public class MheType {

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

    @Column(name = "uom_length")
    private UomLengthEnum uomLength;

    @Column(name = "max_length")
    private Float maxLength;

    @Column(name = "max_width")
    private Float maxWidth;

    @Column(name = "max_height")
    private Float maxHeight;

    @Column(name = "uom_volume")
    private UomVolumeEnum uomVolume;

    @Column(name = "gross_volume")
    private Float grossVolume;

    @Column(name = "gross_volume_calculate")
    private Boolean grossVolumeCalculate;

    @Column(name = "uom_weight")
    private UomWeightEnum uomWeight;

    @Column(name = "max_weight")
    private Float maxWeight;

    @Column(name = "tare_weight")
    private Float tareWeight;

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

    public UomLengthEnum getUomLength() {
        return uomLength;
    }

    public void setUomLength(UomLengthEnum uomLength) {
        this.uomLength = uomLength;
    }

    public Float getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Float maxLength) {
        this.maxLength = maxLength;
    }

    public Float getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public UomVolumeEnum getUomVolume() {
        return uomVolume;
    }

    public void setUomVolume(UomVolumeEnum uomVolume) {
        this.uomVolume = uomVolume;
    }

    public Float getGrossVolume() {
        return grossVolume;
    }

    public void setGrossVolume(Float grossVolume) {
        this.grossVolume = grossVolume;
    }

    public Boolean getGrossVolumeCalculate() {
        return grossVolumeCalculate;
    }

    public void setGrossVolumeCalculate(Boolean grossVolumeCalculate) {
        this.grossVolumeCalculate = grossVolumeCalculate;
    }

    public UomWeightEnum getUomWeight() {
        return uomWeight;
    }

    public void setUomWeight(UomWeightEnum uomWeight) {
        this.uomWeight = uomWeight;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Float getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(Float tareWeight) {
        this.tareWeight = tareWeight;
    }

    @Override
    public String toString() {
        return "MheType [code=" + code + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", createdById="
                + createdById + ", createdByUserName=" + createdByUserName + ", grossVolume=" + grossVolume
                + ", grossVolumeCalculate=" + grossVolumeCalculate + ", id=" + id + ", maxHeight=" + maxHeight
                + ", maxLength=" + maxLength + ", maxWeight=" + maxWeight + ", maxWidth=" + maxWidth + ", modifiedAt="
                + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
                + modifiedByUserName + ", name=" + name + ", tareWeight=" + tareWeight + ", uomLength=" + uomLength
                + ", uomVolume=" + uomVolume + ", uomWeight=" + uomWeight + "]";
    }

}
