package com.warehouse.administration.user.costbucketmapping;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.administration.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user_costbucket_mapping", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "cost_bucket_id" }) })
public class UserCostBucketMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Transient
    private String userName;

    @ManyToOne
    @JoinColumn(name = "cost_bucket_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CostBucket costBucket;

    @Column(name = "cost_bucket_id", insertable = false, updatable = false)
    private Long costBucketId;

    @Transient
    private String costBucketName;

    @Transient
    private String costBucketCode;

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

    public UserCostBucketMapping() {
    }

    public UserCostBucketMapping(Long id, Long costBucketId, String costBucketName, String costBucketCode,
            Date createdAt, Date modifiedAt) {
        this.id = id;
        this.costBucketId = costBucketId;
        this.costBucketName = costBucketName;
        this.costBucketCode = costBucketCode;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "UserCostBucketMapping [costBucket=" + costBucket + ", costBucketCode=" + costBucketCode
                + ", costBucketId=" + costBucketId + ", costBucketName=" + costBucketName + ", createdAt=" + createdAt
                + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", id=" + id + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy
                + ", modifiedById=" + modifiedById + ", modifiedByUserName=" + modifiedByUserName + ", user=" + user
                + ", userId=" + userId + ", userName=" + userName + "]";
    }

}
