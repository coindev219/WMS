package com.warehouse.currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.country.Country;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "currency",schema = "public")
public class Currency {
    
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

    @Size(max = 250)
	@NotBlank(message = "{app.name}")
	private String name;


    @Size(max = 10)
	@NotBlank(message = "{app.currenc_symbol}")
	private String currencySymbol;

    @OneToOne
    @JoinColumn(name = "country_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Country country;

    @NotNull
    @Column(name="country_id",insertable = false,updatable = false)
    private Long countryId;


    private Boolean isActive;


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


    public String getCurrencySymbol() {
        return currencySymbol;
    }


    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
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


    public Boolean getIsActive() {
        return isActive;
    }


    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


    @Override
    public String toString() {
        return "Currency [country=" + country + ", countryId=" + countryId + ", currencySymbol=" + currencySymbol
                + ", id=" + id + ", isActive=" + isActive + ", name=" + name + "]";
    }

    

}
