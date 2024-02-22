package com.warehouse.administration.role;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.enums.UserTypeEnum;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user_role", schema = "public", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }),
        @UniqueConstraint(columnNames = { "code" }) })
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "user_type")
    private UserTypeEnum userType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    @NotNull
    private List<UserRoleWarehouseScreenMapping> userRoleWarehouseScreenMappings;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Timestamp modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User createdBy;

    @Column(name = "created_by", insertable = false, updatable = false)
    @JsonIgnore
    private Long createdById;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public UserRole() {
    }

    public UserRole(String name, String code, UserTypeEnum userType) {
        this.name = name;
        this.code = code;
        this.userType = userType;
    }

    public UserRole(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public UserRole(Long id, String name, String code, UserTypeEnum userType, Date createdAt, Date modifiedAt,
            String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.userType = userType;
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

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public List<UserRoleWarehouseScreenMapping> getUserRoleWarehouseScreenMappings() {
        return userRoleWarehouseScreenMappings;
    }

    public void setUserRoleWarehouseScreenMappings(
            List<UserRoleWarehouseScreenMapping> userRoleWarehouseScreenMappings) {
        this.userRoleWarehouseScreenMappings = userRoleWarehouseScreenMappings;
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
        return "UserRole [code=" + code + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", createdById="
                + createdById + ", createdByUserName=" + createdByUserName + ", id=" + id + ", modifiedAt=" + modifiedAt
                + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
                + modifiedByUserName + ", name=" + name + ", userRoleWarehouseScreenMappings="
                + userRoleWarehouseScreenMappings + ", userType=" + userType + "]";
    }

}
