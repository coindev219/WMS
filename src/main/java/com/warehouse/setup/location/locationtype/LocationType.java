package com.warehouse.setup.location.locationtype;

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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@Table(name = "location_type",schema = "public",
	uniqueConstraints= @UniqueConstraint(columnNames={"name"})
)
public class LocationType{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotBlank(message = "{app.name}")
	@Size(max = 100)
	@Column(name="name")
	private String name;
	
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
	
	
	
	public LocationType() {
	}

	
	public LocationType(Long id, String name, Date createdAt,
			Date modifiedAt, String createdByUserName, String modifiedByUserName) {
		this.id = id;
		this.name = name;
		this.createdAt = new Timestamp(createdAt.getTime());
		this.modifiedAt = new Timestamp(modifiedAt.getTime());
		this.createdByUserName = createdByUserName;
		this.modifiedByUserName = modifiedByUserName;
	}


	public Long getId() {
		return this.id;
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

	
	

}