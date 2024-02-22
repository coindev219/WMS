package com.warehouse.setup.company.primarycompany;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.user.User;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.currency.Currency;
import com.warehouse.enums.WeekDayEnum;
import com.warehouse.state.State;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "primary_company", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name"}),
        @UniqueConstraint(columnNames = { "code"}),
        @UniqueConstraint(columnNames = { "contact_name"}) })
public class PrimaryCompany {

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

    @Transient
    private Long warehouseId;

    @Size(max = 20)
    @NotBlank(message = "{app.doc_no_prefix}")
    private String docNoPrefix;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Currency currency;

    @NotNull
    @Column(name = "currency_id", insertable = false, updatable = false)
    private Long currencyId;

    @Transient
    private String currencyName;

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

    @NotNull
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

    @Column(name = "maintain_back_order")
    private Boolean maintainBackOrder;

    @Column(name = "generate_po_for_back_order")
    private Boolean generatePoForBackOrder;

    @Column(name = "max_so_lines")
    private Integer maxSoLines;

    @Column(name = "max_qty_factor_monthly_avg_per_so_line")
    private Float maxQtyFactorMonthlyAvgPerSoLine;

    @Column(name = "min_qty_factor_monthly_avg_per_so_line")
    private Float minQtyFactorMonthlyAvgPerSoLine;

    @Column(name = "max_po_lines")
    private Integer maxPoLines;

    @Column(name = "max_qty_factor_monthly_avg_per_po_line")
    private Float maxQtyFactorMonthlyAvgPerPoLine;

    @Column(name = "min_qty_factor_monthly_avg_per_po_line")
    private Float minQtyFactorMonthlyAvgPerPoLine;

    @Column(name = "lcf_max_limit")
    private Float lcfMaxLimit;

    @Column(name = "decimal_tolerance")
    private Float decimalTolerance;

    @Size(max = 50)
    @Column(name = "gst_no")
    private String gstNo;

    @Size(max = 500)
    @NotBlank(message = "{app.bill_to}")
    @Column(name = "bill_to")
    private String billTo;

    @Size(max = 500)
    @NotBlank(message = "{app.ship_to}")
    @Column(name = "ship_to")
    private String shipTo;

    @Size(max = 100)
    @Column(name = "tax_invoice_label")
    private String taxInvoiceLabel;

    @Size(max = 100)
    @Column(name = "tax_invoice_number")
    private String taxInvoiceNumber;

    @Column(name = "week_start_date")
    private WeekDayEnum weekStartDate;

    @Column(name = "pan_no")
    private String panNo;

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

    public PrimaryCompany() {
    }

    public PrimaryCompany(Long id, String name, String code, String docNoPrefix, String currencyName, Long currencyId,
            String addressLine1, String addressLine2, String contactName, Long countryId, String countryName,
            Long stateId, String stateName, Long cityId, String cityName, String otherState, String otherCity,
            String postBox, String postalCode, String intlDialCode, String areaDialingCode, String phone,
            String extensionNo, String fax, String mobile, String email, Boolean maintainBackOrder,
            Boolean generatePoForBackOrder, Integer maxSoLines, Float maxQtyFactorMonthlyAvgPerSoLine,
            Float minQtyFactorMonthlyAvgPerSoLine, Integer maxPoLines, Float maxQtyFactorMonthlyAvgPerPoLine,
            Float minQtyFactorMonthlyAvgPerPoLine, Float lcfMaxLimit, Float decimalTolerance, String gstNo,
            String billTo, String shipTo, String taxInvoiceLabel, String taxInvoiceNumber, WeekDayEnum weekStartDate,
            String panNo, Date createdAt, Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.docNoPrefix = docNoPrefix;
        this.currencyName = currencyName;
        this.currencyId = currencyId;
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
        this.maintainBackOrder = maintainBackOrder;
        this.generatePoForBackOrder = generatePoForBackOrder;
        this.maxSoLines = maxSoLines;
        this.maxQtyFactorMonthlyAvgPerSoLine = maxQtyFactorMonthlyAvgPerSoLine;
        this.minQtyFactorMonthlyAvgPerSoLine = minQtyFactorMonthlyAvgPerSoLine;
        this.maxPoLines = maxPoLines;
        this.maxQtyFactorMonthlyAvgPerPoLine = maxQtyFactorMonthlyAvgPerPoLine;
        this.minQtyFactorMonthlyAvgPerPoLine = minQtyFactorMonthlyAvgPerPoLine;
        this.lcfMaxLimit = lcfMaxLimit;
        this.decimalTolerance = decimalTolerance;
        this.gstNo = gstNo;
        this.billTo = billTo;
        this.shipTo = shipTo;
        this.taxInvoiceLabel = taxInvoiceLabel;
        this.taxInvoiceNumber = taxInvoiceNumber;
        this.weekStartDate = weekStartDate;
        this.panNo = panNo;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getDocNoPrefix() {
        return docNoPrefix;
    }

    public void setDocNoPrefix(String docNoPrefix) {
        this.docNoPrefix = docNoPrefix;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
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

    public Boolean getMaintainBackOrder() {
        return maintainBackOrder;
    }

    public void setMaintainBackOrder(Boolean maintainBackOrder) {
        this.maintainBackOrder = maintainBackOrder;
    }

    public Boolean getGeneratePoForBackOrder() {
        return generatePoForBackOrder;
    }

    public void setGeneratePoForBackOrder(Boolean generatePoForBackOrder) {
        this.generatePoForBackOrder = generatePoForBackOrder;
    }

    public Integer getMaxSoLines() {
        return maxSoLines;
    }

    public void setMaxSoLines(Integer maxSoLines) {
        this.maxSoLines = maxSoLines;
    }

    public Float getMaxQtyFactorMonthlyAvgPerSoLine() {
        return maxQtyFactorMonthlyAvgPerSoLine;
    }

    public void setMaxQtyFactorMonthlyAvgPerSoLine(Float maxQtyFactorMonthlyAvgPerSoLine) {
        this.maxQtyFactorMonthlyAvgPerSoLine = maxQtyFactorMonthlyAvgPerSoLine;
    }

    public Float getMinQtyFactorMonthlyAvgPerSoLine() {
        return minQtyFactorMonthlyAvgPerSoLine;
    }

    public void setMinQtyFactorMonthlyAvgPerSoLine(Float minQtyFactorMonthlyAvgPerSoLine) {
        this.minQtyFactorMonthlyAvgPerSoLine = minQtyFactorMonthlyAvgPerSoLine;
    }

    public Integer getMaxPoLines() {
        return maxPoLines;
    }

    public void setMaxPoLines(Integer maxPoLines) {
        this.maxPoLines = maxPoLines;
    }

    public Float getMaxQtyFactorMonthlyAvgPerPoLine() {
        return maxQtyFactorMonthlyAvgPerPoLine;
    }

    public void setMaxQtyFactorMonthlyAvgPerPoLine(Float maxQtyFactorMonthlyAvgPerPoLine) {
        this.maxQtyFactorMonthlyAvgPerPoLine = maxQtyFactorMonthlyAvgPerPoLine;
    }

    public Float getMinQtyFactorMonthlyAvgPerPoLine() {
        return minQtyFactorMonthlyAvgPerPoLine;
    }

    public void setMinQtyFactorMonthlyAvgPerPoLine(Float minQtyFactorMonthlyAvgPerPoLine) {
        this.minQtyFactorMonthlyAvgPerPoLine = minQtyFactorMonthlyAvgPerPoLine;
    }

    public Float getLcfMaxLimit() {
        return lcfMaxLimit;
    }

    public void setLcfMaxLimit(Float lcfMaxLimit) {
        this.lcfMaxLimit = lcfMaxLimit;
    }

    public Float getDecimalTolerance() {
        return decimalTolerance;
    }

    public void setDecimalTolerance(Float decimalTolerance) {
        this.decimalTolerance = decimalTolerance;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getTaxInvoiceLabel() {
        return taxInvoiceLabel;
    }

    public void setTaxInvoiceLabel(String taxInvoiceLabel) {
        this.taxInvoiceLabel = taxInvoiceLabel;
    }

    public String getTaxInvoiceNumber() {
        return taxInvoiceNumber;
    }

    public void setTaxInvoiceNumber(String taxInvoiceNumber) {
        this.taxInvoiceNumber = taxInvoiceNumber;
    }

    public WeekDayEnum getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(WeekDayEnum weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
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

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "PrimaryCompany [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", areaDialingCode="
                + areaDialingCode + ", billTo=" + billTo + ", city=" + city + ", cityId=" + cityId + ", cityName="
                + cityName + ", code=" + code + ", contactName=" + contactName + ", country=" + country + ", countryId="
                + countryId + ", countryName=" + countryName + ", createdAt=" + createdAt + ", createdBy=" + createdBy
                + ", createdById=" + createdById + ", createdByUserName=" + createdByUserName + ", currency=" + currency
                + ", currencyId=" + currencyId + ", currencyName=" + currencyName + ", decimalTolerance="
                + decimalTolerance + ", docNoPrefix=" + docNoPrefix + ", email=" + email + ", extensionNo="
                + extensionNo + ", fax=" + fax + ", generatePoForBackOrder=" + generatePoForBackOrder + ", gstNo="
                + gstNo + ", id=" + id + ", intlDialCode=" + intlDialCode + ", lcfMaxLimit=" + lcfMaxLimit
                + ", maintainBackOrder=" + maintainBackOrder + ", maxPoLines=" + maxPoLines
                + ", maxQtyFactorMonthlyAvgPerPoLine=" + maxQtyFactorMonthlyAvgPerPoLine
                + ", maxQtyFactorMonthlyAvgPerSoLine=" + maxQtyFactorMonthlyAvgPerSoLine + ", maxSoLines=" + maxSoLines
                + ", minQtyFactorMonthlyAvgPerPoLine=" + minQtyFactorMonthlyAvgPerPoLine
                + ", minQtyFactorMonthlyAvgPerSoLine=" + minQtyFactorMonthlyAvgPerSoLine + ", mobile=" + mobile
                + ", modifiedAt=" + modifiedAt + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById
                + ", modifiedByUserName=" + modifiedByUserName + ", name=" + name + ", otherCity=" + otherCity
                + ", otherState=" + otherState + ", panNo=" + panNo + ", phone=" + phone + ", postBox=" + postBox
                + ", postalCode=" + postalCode + ", shipTo=" + shipTo + ", state=" + state + ", stateId=" + stateId
                + ", stateName=" + stateName + ", taxInvoiceLabel=" + taxInvoiceLabel + ", taxInvoiceNumber="
                + taxInvoiceNumber + ", warehouseId=" + warehouseId + ", weekStartDate=" + weekStartDate + "]";
    }

}
