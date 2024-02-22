package com.warehouse.additionalsetup.uom;

import java.util.Date;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.enums.UomCategoryEnum;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "uom", schema = "public", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "created_by" }),
        @UniqueConstraint(columnNames = { "uom", "created_by" }) }

)
public class Uom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @NotBlank(message = "{app.uom}")
    private String uom;

    @Size(max = 250)
    @NotBlank(message = "{app.name}")
    private String name;

    @Column(name = "uom_category")
    private UomCategoryEnum uomCategory;

    @NotBlank(message = "{app.conversion_factor}")
    @Size(max = 100)
    @Column(name = "conversion_factor")
    private String conversionFactor;

    @Column(name = "allow_fraction")
    private Boolean allowFractions;

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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UomCategoryEnum getUomCategory() {
        return uomCategory;
    }

    public void setUomCategory(UomCategoryEnum uomCategory) {
        this.uomCategory = uomCategory;
    }

    public String getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(String conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public Boolean getAllowFractions() {
        return allowFractions;
    }

    public void setAllowFractions(Boolean allowFractions) {
        this.allowFractions = allowFractions;
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

    public Uom() {
    }

    public Uom(Long id, String uom,
            String name, UomCategoryEnum uomCategory,
            String conversionFactor,
            Boolean allowFractions, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.uom = uom;
        this.name = name;
        this.uomCategory = uomCategory;
        this.conversionFactor = conversionFactor;
        this.allowFractions = allowFractions;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    @Override
    public String toString() {
        return "Uom [allowFractions=" + allowFractions + ", conversionFactor=" + conversionFactor + ", createdAt="
                + createdAt + ", createdBy=" + createdBy + ", createdById=" + createdById + ", id=" + id
                + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", name=" + name + ", uom=" + uom + ", uomCategory=" + uomCategory + "]";
    }

}
