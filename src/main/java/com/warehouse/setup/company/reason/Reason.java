package com.warehouse.setup.company.reason;

import java.sql.Timestamp;
import java.util.Date;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "reason", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code", "primary_company_id" }),
        @UniqueConstraint(columnNames = { "name", "primary_company_id" }) }

)
public class Reason {

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

    @ManyToOne
    @JoinColumn(name = "primary_company_id", nullable = false)
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

    public Reason() {
    }

    public Reason(Long id, String name, String code, String primaryCompanyName, String primaryCompanyCode,
            Long primaryCompanyId, Date createdAt, Date modifiedAt, String createdByUserName,
            String modifiedByUserName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.primaryCompanyId = primaryCompanyId;
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

    public PrimaryCompany getPrimaryCompany() {
        return primaryCompany;
    }

    public void setPrimaryCompany(PrimaryCompany primaryCompany) {
        this.primaryCompany = primaryCompany;
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
        return "Reason [code=" + code + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", createdById="
                + createdById + ", createdByUserName=" + createdByUserName + ", id=" + id + ", modifiedAt=" + modifiedAt
                + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
                + modifiedByUserName + ", name=" + name + ", primaryCompany=" + primaryCompany + ", primaryCompanyCode="
                + primaryCompanyCode + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName="
                + primaryCompanyName + "]";
    }

}
