package com.warehouse.savedsearch;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.enums.SavedSearchFilterTypeEnum;
import com.warehouse.enums.SearchTypeEnum;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "saved_search",schema = "public",
            uniqueConstraints= @UniqueConstraint(columnNames={"search_name", "filter_type"})
        )
public class SavedSearch {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
    
    
    @NotNull
    @Column(name="filter_type")
    private SavedSearchFilterTypeEnum filterType;

    @Size(max = 50)
    @NotBlank(message="{app.search_name}")
    @Column(name="search_name")
    private String searchName;


    @NotNull
    @Column(name="type")
    private SearchTypeEnum type;


    @Lob
	@Type(type = "org.hibernate.type.TextType")
    @NotBlank(message="{app.saved_search_detail}")
    @Column(name="search_detail")
    private String searchDetail;

    @ManyToOne
    @JoinColumn(name = "admin_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
    

    @Column(name="created_at")
	@CreationTimestamp
	@JsonIgnore
	private Timestamp createdAt;


    @Column(name="modified_at")
	@UpdateTimestamp
	@JsonIgnore
	private Timestamp modifiedAt;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public SavedSearchFilterTypeEnum getFilterType() {
        return filterType;
    }


    public void setFilterType(SavedSearchFilterTypeEnum filterType) {
        this.filterType = filterType;
    }


    public String getSearchName() {
        return searchName;
    }


    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }


    public SearchTypeEnum getType() {
        return type;
    }


    public void setType(SearchTypeEnum type) {
        this.type = type;
    }


    public String getSearchDetail() {
        return searchDetail;
    }


    public void setSearchDetail(String searchDetail) {
        this.searchDetail = searchDetail;
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

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "SavedSearch [createdAt=" + createdAt + ", filterType=" + filterType + ", id=" + id + ", modifiedAt="
                + modifiedAt + ", searchDetail=" + searchDetail + ", searchName=" + searchName + ", type=" + type
                + ", user=" + user + "]";
    }

}
