package com.warehouse.setup.warehouse;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import com.warehouse.administration.user.User;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMapping;
import com.warehouse.setup.warehouse.printers.ConfigurePrinter;
import com.warehouse.setup.warehouse.runningcost.RunningCost;
import com.warehouse.state.State;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * The persistent class for the warehouse database table.
 * 
 */

@Entity
@Table(name = "warehouse", schema = "public")
public class Warehouse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@NotBlank(message = "{app.address_line_1}")
	@Column(name = "address_line_1")
	private String addressLine1;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@NotNull
	@Column(name = "address_line_2")
	private String addressLine2;

	@Size(max = 20)
	@NotNull
	@Column(name = "area_dialing_code")
	private String areaDialingCode;

	@Size(max = 100)
	@NotBlank(message = "{app.code}")
	@Column(unique = true)
	private String code;

	@Size(max = 100)
	@NotBlank(message = "{app.contact_name}")
	@Column(name = "contact_name", unique = true)
	private String contactName;

	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;

	@Size(max = 50)
	@NotNull
	private String cstn;

	@Size(max = 50)
	@NotBlank(message = "{app.doc_no_prefix}")
	@Column(name = "doc_no_prefix")
	private String docNoPrefix;

	@Size(max = 100)
	@NotNull
	private String email;

	@Size(max = 50)
	@NotNull
	@Column(name = "export_license_no")
	private String exportLicenseNo;

	@Size(max = 50)
	@NotNull
	@Column(name = "extension_no")
	private String extensionNo;

	@Size(max = 50)
	@NotNull
	private String fax;

	@Size(max = 50)
	@NotNull
	@Column(name = "gst_no")
	private String gstNo;

	@Size(max = 50)
	@NotNull
	@Column(name = "intl_dial_code")
	private String intlDialCode;

	@Size(max = 50)
	@NotNull
	private String mobile;

	@Column(name = "modified_at")
	@UpdateTimestamp
	private Timestamp modifiedAt;

	@Size(max = 250)
	@NotBlank(message = "{app.name}")
	@Column(unique = true)
	private String name;

	@Size(max = 100)
	@NotNull
	@Column(name = "other_city")
	private String otherCity;

	@Size(max = 100)
	@NotNull
	@Column(name = "other_state")
	private String otherState;

	@Size(max = 50)
	@NotNull
	private String phone;

	@Size(max = 50)
	@NotNull
	@Column(name = "post_box")
	private String postBox;

	@Size(max = 50)
	@NotNull
	@Column(name = "postal_code")
	private String postalCode;

	@Size(max = 250)
	@NotBlank(message = "{app.ship_to}")
	@Column(name = "ship_to")
	private String shipTo;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@NotNull
	@Column(name = "tax_invoice_statement")
	private String taxInvoiceStatement;

	@Size(max = 50)
	@NotNull
	private String tin;

	@Size(max = 50)
	@NotNull
	@Column(name = "trade_license_no")
	private String tradeLicenseNo;

	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Country country;

	@Column(name = "country_id", insertable = false, updatable = false)
	private Long countryId;

	@Transient
	private String countryName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id", nullable = false)
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

	@Transient
	private List<ConfigurePrinter> configurePrinterList;

	@Transient
	private List<IntegrationMapping> integrationMappingList;

	@Transient
	private List<WarehouseCompanyMapping> warehouseCompanyMappingList;

	@Transient
	private List<RunningCost> runningCostList;

	@Transient
	private Long warehouseId;

	@Transient
	private String warehouseName;

	@Transient
	private String warehouseCode;


	public Warehouse() {
	}

	public Warehouse(Long id, String code, String contactName, String name) {
		this.id = id;
		this.code = code;
		this.contactName = contactName;
		this.name = name;
	}

	public Warehouse(Long id, String addressLine1, String addressLine2, String areaDialingCode, String code,
			String contactName, Date createdAt, String cstn, String docNoPrefix, String email, String exportLicenseNo,
			String extensionNo, String fax, String gstNo, String intlDialCode, String mobile, Date modifiedAt,
			String name, String otherCity, String otherState, String phone, String postBox, String postalCode,
			String shipTo, String taxInvoiceStatement, String tin, String tradeLicenseNo, Long countryId,
			String countryName, Long stateId, String stateName, Long cityId, String cityName, String createdByUserName,
			String modifiedByUserName,Long warehouseId, String warehouseName,String warehouseCode) {
		this.id = id;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.areaDialingCode = areaDialingCode;
		this.code = code;
		this.contactName = contactName;
		this.createdAt = new Timestamp(createdAt.getTime());
		this.cstn = cstn;
		this.docNoPrefix = docNoPrefix;
		this.email = email;
		this.exportLicenseNo = exportLicenseNo;
		this.extensionNo = extensionNo;
		this.fax = fax;
		this.gstNo = gstNo;
		this.intlDialCode = intlDialCode;
		this.mobile = mobile;
		this.modifiedAt = new Timestamp(modifiedAt.getTime());
		this.name = name;
		this.otherCity = otherCity;
		this.otherState = otherState;
		this.phone = phone;
		this.postBox = postBox;
		this.postalCode = postalCode;
		this.shipTo = shipTo;
		this.taxInvoiceStatement = taxInvoiceStatement;
		this.tin = tin;
		this.tradeLicenseNo = tradeLicenseNo;
		this.countryId = countryId;
		this.countryName = countryName;
		this.stateId = stateId;
		this.stateName = stateName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.createdByUserName = createdByUserName;
		this.modifiedByUserName = modifiedByUserName;
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.warehouseCode = warehouseCode;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAreaDialingCode() {
		return areaDialingCode;
	}

	public void setAreaDialingCode(String areaDialingCode) {
		this.areaDialingCode = areaDialingCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getCstn() {
		return cstn;
	}

	public void setCstn(String cstn) {
		this.cstn = cstn;
	}

	public String getDocNoPrefix() {
		return docNoPrefix;
	}

	public void setDocNoPrefix(String docNoPrefix) {
		this.docNoPrefix = docNoPrefix;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExportLicenseNo() {
		return exportLicenseNo;
	}

	public void setExportLicenseNo(String exportLicenseNo) {
		this.exportLicenseNo = exportLicenseNo;
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

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getIntlDialCode() {
		return intlDialCode;
	}

	public void setIntlDialCode(String intlDialCode) {
		this.intlDialCode = intlDialCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherCity() {
		return otherCity;
	}

	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}

	public String getOtherState() {
		return otherState;
	}

	public void setOtherState(String otherState) {
		this.otherState = otherState;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getTaxInvoiceStatement() {
		return taxInvoiceStatement;
	}

	public void setTaxInvoiceStatement(String taxInvoiceStatement) {
		this.taxInvoiceStatement = taxInvoiceStatement;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getTradeLicenseNo() {
		return tradeLicenseNo;
	}

	public void setTradeLicenseNo(String tradeLicenseNo) {
		this.tradeLicenseNo = tradeLicenseNo;
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

	public List<ConfigurePrinter> getConfigurePrinterList() {
		return configurePrinterList;
	}

	public void setConfigurePrinterList(List<ConfigurePrinter> configurePrinterList) {
		this.configurePrinterList = configurePrinterList;
	}

	public List<IntegrationMapping> getIntegrationMappingList() {
		return integrationMappingList;
	}

	public void setIntegrationMappingList(List<IntegrationMapping> integrationMappingList) {
		this.integrationMappingList = integrationMappingList;
	}

	public List<RunningCost> getRunningCostList() {
		return runningCostList;
	}

	public void setRunningCostList(List<RunningCost> runningCostList) {
		this.runningCostList = runningCostList;
	}

	public List<WarehouseCompanyMapping> getWarehouseCompanyMappingList() {
		return warehouseCompanyMappingList;
	}

	public void setWarehouseCompanyMappingList(List<WarehouseCompanyMapping> warehouseCompanyMappingList) {
		this.warehouseCompanyMappingList = warehouseCompanyMappingList;
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

	@Override
	public String toString() {
		return "Warehouse [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", areaDialingCode="
				+ areaDialingCode + ", city=" + city + ", cityId=" + cityId + ", cityName=" + cityName + ", code="
				+ code + ", contactName=" + contactName + ", country=" + country + ", countryId=" + countryId
				+ ", countryName=" + countryName + ", createdAt=" + createdAt + ", createdBy=" + createdBy
				+ ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", cstn=" + cstn
				+ ", docNoPrefix=" + docNoPrefix + ", email=" + email + ", exportLicenseNo=" + exportLicenseNo
				+ ", extensionNo=" + extensionNo + ", fax=" + fax + ", gstNo=" + gstNo + ", id=" + id
				+ ", intlDialCode=" + intlDialCode + ", mobile=" + mobile + ", modifiedAt=" + modifiedAt
				+ ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
				+ modifiedByUserName + ", name=" + name + ", otherCity=" + otherCity + ", otherState=" + otherState
				+ ", phone=" + phone + ", postBox=" + postBox + ", postalCode=" + postalCode + ", shipTo=" + shipTo
				+ ", state=" + state + ", stateId=" + stateId + ", stateName=" + stateName + ", taxInvoiceStatement="
				+ taxInvoiceStatement + ", tin=" + tin + ", tradeLicenseNo=" + tradeLicenseNo + "]";
	}

}