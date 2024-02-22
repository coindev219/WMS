package com.warehouse.administration.user;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.administration.role.UserRole;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.enums.UserTypeEnum;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Warehouse warehouse;

    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Long warehouseId;

    @Transient
    private String warehouseName;

    @Transient
    private String warehouseCode;

    @ManyToOne
    @JoinColumn(name = "primary_company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PrimaryCompany primaryCompany;

    @Column(name = "primary_company_id", insertable = false, updatable = false)
    private Long primaryCompanyId;

    @Transient
    private String primaryCompanyName;

    @Transient
    private String primaryCompanyCode;

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

    @Column(name = "employee_no")
    private String employeeNumber;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "non_transactional_user")
    private Boolean nonTransactionalUser = false;

    @NotNull
    @Size(max = 100)
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Size(max = 100)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Transient
    private String userPassword;

    @Column(name = "user_type")
    private UserTypeEnum userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;

    @Transient
    private String customerName;

    @Transient
    private String customerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Supplier supplier;

    @Column(name = "supplier_id", insertable = false, updatable = false)
    private Long supplierId;

    @Transient
    private String supplierName;

    @Transient
    private String supplierCode;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserRole userRole;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Long userRoleId;

    @Transient
    private String userRoleName;

    @Transient
    private String userRoleCode;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @NotBlank(message = "{app.address_line_1}")
    @Column(name = "address_line_1")
    private String addressLine1;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "address_line_2")
    private String addressLine2;

    @Size(max = 100)
    @NotBlank(message = "{app.contact_name}")
    @Column(name = "contact_name")
    private String contactName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Country country;

    @Column(name = "country_id", insertable = false, updatable = false)
    private Long countryId;

    @Transient
    private String countryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private State state;

    @Column(name = "state_id", insertable = false, updatable = false)
    private Long stateId;

    @Transient
    private String stateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private City city;

    @Column(name = "city_id", insertable = false, updatable = false)
    private Long cityId;

    @Transient
    private String cityName;

    @Size(max = 100)
    @Column(name = "other_state")
    private String otherState;

    @Size(max = 100)
    @Column(name = "other_city")
    private String otherCity;

    @Size(max = 20)
    @Column(name = "post_box")
    private String postBox;

    @Size(max = 20)
    @Column(name = "postal_code")
    private String postalCode;

    @Size(max = 20)
    @Column(name = "intl_dial_code")
    private String intlDialCode;

    @Size(max = 20)
    @Column(name = "area_dialing_code")
    private String areaDialingCode;

    @Size(max = 15)
    @Column(name = "phone")
    private String phone;

    @Size(max = 20)
    @Column(name = "extension_no")
    private String extensionNo;

    @Size(max = 12)
    @Column(name = "fax")
    private String fax;

    @Size(max = 15)
    @Column(name = "mobile")
    private String mobile;

    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private Boolean isActive = true;

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

    public User() {
    }

    public User(String username, String password, UserTypeEnum userType, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.userRole = userRole;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, Long warehouseId, String warehouseName, String warehouseCode, Long primaryCompanyId,
            String primaryCompanyName, String primaryCompanyCode, Long costBucketId, String costBucketName,
            String costBucketCode, String employeeNumber, String employeeName, Boolean nonTransactionalUser,
            String username, UserTypeEnum userType, Long customerId, String customerName, String customerCode,
            Long supplierId, String supplierName, String supplierCode, Long userRoleId,
            String userRoleName, String userRoleCode, String addressLine1, String addressLine2, String contactName,
            Long countryId, String countryName, Long stateId, String stateName, Long cityId, String cityName,
            String otherState, String otherCity, String postBox, String postalCode, String intlDialCode,
            String areaDialingCode, String phone, String extensionNo, String fax, String mobile, String email,
            Date createdAt, Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.costBucketId = costBucketId;
        this.costBucketName = costBucketName;
        this.costBucketCode = costBucketCode;
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.nonTransactionalUser = nonTransactionalUser;
        this.username = username;
        this.userType = userType;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCode = customerCode;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.userRoleId = userRoleId;
        this.userRoleName = userRoleName;
        this.userRoleCode = userRoleCode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.contactName = contactName;
        this.countryId = countryId;
        this.countryName = countryName;
        this.stateId = stateId;
        this.stateName = stateName;
        this.cityId = cityId;
        this.cityName = cityName;
        this.otherState = otherState;
        this.otherCity = otherCity;
        this.postBox = postBox;
        this.postalCode = postalCode;
        this.intlDialCode = intlDialCode;
        this.areaDialingCode = areaDialingCode;
        this.phone = phone;
        this.extensionNo = extensionNo;
        this.fax = fax;
        this.mobile = mobile;
        this.email = email;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.createdByUserName = createdByUserName;
        this.modifiedByUserName = modifiedByUserName;
    }

    
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public PrimaryCompany getPrimaryCompany() {
        return primaryCompany;
    }

    public void setPrimaryCompany(PrimaryCompany primaryCompany) {
        this.primaryCompany = primaryCompany;
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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Boolean getNonTransactionalUser() {
        return nonTransactionalUser;
    }

    public void setNonTransactionalUser(Boolean nonTransactionalUser) {
        this.nonTransactionalUser = nonTransactionalUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
    }

    public String getOtherCity() {
        return otherCity;
    }

    public void setOtherCity(String otherCity) {
        this.otherCity = otherCity;
    }

    public String getPostBox() {
        return postBox;
    }

    public void setPostBox(String postBox) {
        this.postBox = postBox;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getIntlDialCode() {
        return intlDialCode;
    }

    public void setIntlDialCode(String intlDialCode) {
        this.intlDialCode = intlDialCode;
    }

    public String getAreaDialingCode() {
        return areaDialingCode;
    }

    public void setAreaDialingCode(String areaDialingCode) {
        this.areaDialingCode = areaDialingCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtensionNo() {
        return extensionNo;
    }

    public void setExtensionNo(String extensionNo) {
        this.extensionNo = extensionNo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "User [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", areaDialingCode="
                + areaDialingCode + ", city=" + city + ", cityId=" + cityId + ", cityName=" + cityName
                + ", contactName=" + contactName + ", costBucket=" + costBucket + ", costBucketCode=" + costBucketCode
                + ", costBucketId=" + costBucketId + ", costBucketName=" + costBucketName + ", country=" + country
                + ", countryId=" + countryId + ", countryName=" + countryName + ", createdAt=" + createdAt
                + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", customer=" + customer + ", customerCode=" + customerCode + ", customerId="
                + customerId + ", customerName=" + customerName + ", email=" + email + ", employeeName=" + employeeName
                + ", employeeNumber=" + employeeNumber + ", extensionNo=" + extensionNo + ", fax=" + fax + ", id=" + id
                + ", intlDialCode=" + intlDialCode + ", mobile=" + mobile + ", modifiedAt=" + modifiedAt
                + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
                + modifiedByUserName + ", nonTransactionalUser=" + nonTransactionalUser + ", otherCity=" + otherCity
                + ", otherState=" + otherState + ", password=" + password + ", phone=" + phone + ", postBox=" + postBox
                + ", postalCode=" + postalCode + ", primaryCompany=" + primaryCompany + ", primaryCompanyCode="
                + primaryCompanyCode + ", primaryCompanyId=" + primaryCompanyId + ", primaryCompanyName="
                + primaryCompanyName + ", state=" + state + ", stateId=" + stateId + ", stateName=" + stateName
                + ", supplier=" + supplier + ", supplierCode=" + supplierCode + ", supplierId=" + supplierId
                + ", supplierName=" + supplierName + ", userRole=" + userRole + ", userRoleCode=" + userRoleCode
                + ", userRoleId=" + userRoleId + ", userRoleName=" + userRoleName + ", userType=" + userType
                + ", username=" + username + ", warehouse=" + warehouse + ", warehouseCode=" + warehouseCode
                + ", warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + "]";
    }

}
