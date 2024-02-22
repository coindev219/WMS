package com.warehouse.setup.supplier;

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
import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.administration.user.User;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.currency.Currency;
import com.warehouse.enums.ShipModeEnum;
import com.warehouse.enums.TradeTermEnum;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "supplier", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "primary_company_id" }),
        @UniqueConstraint(columnNames = { "code", "primary_company_id" }),
        @UniqueConstraint(columnNames = { "contact_name", "primary_company_id" }) })

public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "primary_company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PrimaryCompany primaryCompany;

    @NotNull
    @Column(name = "primary_company_id", insertable = false, updatable = false)
    private Long primaryCompanyId;

    @Transient
    private String primaryCompanyName;

    @Transient
    private String primaryCompanyCode;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Warehouse warehouse;

    @NotNull
    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Long warehouseId;

    @Transient
    private String warehouseName;

    @Transient
    private String warehouseCode;

    @Size(max = 250)
    @NotBlank(message = "{app.name}")
    private String name;

    @Size(max = 100)
    @NotBlank(message = "{app.code}")
    private String code;

    @Size(max = 100)
    @Column(name = "supplier_category")
    private String supplierCategory;

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

    @NotNull
    @Column(name = "trade_term")
    private TradeTermEnum tradeTerm = TradeTermEnum.Select;

    @NotNull
    @Column(name = "forecast_order_ship_mode")
    private ShipModeEnum forecastOrderShipMode = ShipModeEnum.Select;

    @NotNull
    @Column(name = "back_order_ship_mode")
    private ShipModeEnum backOrderShipMode = ShipModeEnum.Select;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freighter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Freighter freighter;

    @Column(name = "freighter_id", insertable = false, updatable = false)
    private Long freighterId;

    @Transient
    private String freighterName;

    @Transient
    private String freighterCode;

    @ManyToOne
    @JoinColumn(name = "order_type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private OrderType defaultPoType;

    @NotNull
    @Column(name = "order_type_id", insertable = false, updatable = false)
    private Long defaultPoTypeId;

    @Transient
    private String defaultPoTypeName;

    @Transient
    private String defaultPoTypeCode;

    // @NotNull
    // @Column(name = "default_po_type")
    // private DefaultPoTypeEnum defaultPoType = DefaultPoTypeEnum.Select;

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

    @Size(max = 500)
    @NotBlank(message = "{app.shipTo}")
    @Column(name = "ship_to")
    private String shipTo;

    @Size(max = 50)
    @Column(name = "gst_no")
    private String gstNo;

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

    public Supplier() {
    }

    public Supplier(Long id, Long primaryCompanyId, String primaryCompanyName, String primaryCompanyCode,
            Long warehouseId, String warehouseName, String warehouseCode, String name, String code,
            String supplierCategory, String addressLine1, String addressLine2, String contactName, Long countryId,
            String countryName, Long stateId, String stateName, Long cityId, String cityName, String otherState,
            String otherCity, String postBox, String postalCode, String intlDialCode, String areaDialingCode,
            String phone, String extensionNo, String fax, String mobile, String email, TradeTermEnum tradeTerm,
            ShipModeEnum forecastOrderShipMode, ShipModeEnum backOrderShipMode, Long freighterId,String freighterName,String freighterCode,
            Long defaultPoTypeId,String defaultPoTypeName,String defaultPoTypeCode,
            Long currencyId,String currencyName, String shipTo, String gstNo,
            Date createdAt, Date modifiedAt, String createdByUserName, String modifiedByUserName) {
        this.id = id;
        this.primaryCompanyId = primaryCompanyId;
        this.primaryCompanyName = primaryCompanyName;
        this.primaryCompanyCode = primaryCompanyCode;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.name = name;
        this.code = code;
        this.supplierCategory = supplierCategory;
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
        this.tradeTerm = tradeTerm;
        this.forecastOrderShipMode = forecastOrderShipMode;
        this.backOrderShipMode = backOrderShipMode;
        this.freighterId = freighterId;
        this.freighterName = freighterName;
        this.freighterCode = freighterCode;
        this.defaultPoTypeId = defaultPoTypeId;
        this.defaultPoTypeName = defaultPoTypeName;
        this.defaultPoTypeCode = defaultPoTypeCode;
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.shipTo = shipTo;
        this.gstNo = gstNo;
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

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
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

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
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

    public TradeTermEnum getTradeTerm() {
        return tradeTerm;
    }

    public void setTradeTerm(TradeTermEnum tradeTerm) {
        this.tradeTerm = tradeTerm;
    }

    public ShipModeEnum getForecastOrderShipMode() {
        return forecastOrderShipMode;
    }

    public void setForecastOrderShipMode(ShipModeEnum forecastOrderShipMode) {
        this.forecastOrderShipMode = forecastOrderShipMode;
    }

    public ShipModeEnum getBackOrderShipMode() {
        return backOrderShipMode;
    }

    public void setBackOrderShipMode(ShipModeEnum backOrderShipMode) {
        this.backOrderShipMode = backOrderShipMode;
    }

    public Freighter getFreighter() {
        return freighter;
    }

    public void setFreighter(Freighter freighter) {
        this.freighter = freighter;
    }

    public Long getFreighterId() {
        return freighterId;
    }

    public void setFreighterId(Long freighterId) {
        this.freighterId = freighterId;
    }


    public String getFreighterName() {
        return freighterName;
    }

    public void setFreighterName(String freighterName) {
        this.freighterName = freighterName;
    }

    public String getFreighterCode() {
        return freighterCode;
    }

    public void setFreighterCode(String freighterCode) {
        this.freighterCode = freighterCode;
    }

    public OrderType getDefaultPoType() {
        return defaultPoType;
    }

    public void setDefaultPoType(OrderType defaultPoType) {
        this.defaultPoType = defaultPoType;
    }

    public Long getDefaultPoTypeId() {
        return defaultPoTypeId;
    }

    public void setDefaultPoTypeId(Long defaultPoTypeId) {
        this.defaultPoTypeId = defaultPoTypeId;
    }

    public String getDefaultPoTypeName() {
        return defaultPoTypeName;
    }

    public void setDefaultPoTypeName(String defaultPoTypeName) {
        this.defaultPoTypeName = defaultPoTypeName;
    }

    public String getDefaultPoTypeCode() {
        return defaultPoTypeCode;
    }

    public void setDefaultPoTypeCode(String defaultPoTypeCode) {
        this.defaultPoTypeCode = defaultPoTypeCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
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
        return "Supplier [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", areaDialingCode="
                + areaDialingCode + ", backOrderShipMode=" + backOrderShipMode + ", city=" + city + ", cityId=" + cityId
                + ", cityName=" + cityName + ", code=" + code + ", contactName=" + contactName + ", country=" + country
                + ", countryId=" + countryId + ", countryName=" + countryName + ", createdAt=" + createdAt
                + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdByUserName="
                + createdByUserName + ", currency=" + currency + ", currencyId=" + currencyId + ", currencyName="
                + currencyName + ", defaultPoType=" + defaultPoType + ", defaultPoTypeCode=" + defaultPoTypeCode
                + ", defaultPoTypeId=" + defaultPoTypeId + ", defaultPoTypeName=" + defaultPoTypeName + ", email="
                + email + ", extensionNo=" + extensionNo + ", fax=" + fax + ", forecastOrderShipMode="
                + forecastOrderShipMode + ", freighter=" + freighter + ", freighterCode=" + freighterCode
                + ", freighterId=" + freighterId + ", freighterName=" + freighterName + ", gstNo=" + gstNo + ", id="
                + id + ", intlDialCode=" + intlDialCode + ", mobile=" + mobile + ", modifiedAt=" + modifiedAt
                + ", modifiedBy=" + modifiedBy + ", modifiedById=" + modifiedById + ", modifiedByUserName="
                + modifiedByUserName + ", name=" + name + ", otherCity=" + otherCity + ", otherState=" + otherState
                + ", phone=" + phone + ", postBox=" + postBox + ", postalCode=" + postalCode + ", primaryCompany="
                + primaryCompany + ", primaryCompanyCode=" + primaryCompanyCode + ", primaryCompanyId="
                + primaryCompanyId + ", primaryCompanyName=" + primaryCompanyName + ", shipTo=" + shipTo + ", state="
                + state + ", stateId=" + stateId + ", stateName=" + stateName + ", supplierCategory=" + supplierCategory
                + ", tradeTerm=" + tradeTerm + ", warehouse=" + warehouse + ", warehouseCode=" + warehouseCode
                + ", warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + "]";
    }

    

}
