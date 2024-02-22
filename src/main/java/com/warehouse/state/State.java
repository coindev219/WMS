package com.warehouse.state;

import java.util.ArrayList;
import java.util.List;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.warehouse.city.City;
import com.warehouse.country.Country;


/**
 * The persistent class for the state database table.
 * 
 */
@Entity
@Table(name = "state",schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class State  {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotBlank(message = "{state.name}")
	@Size(max = 100)
	@Column(name="state_name")
	private String stateName;

	@Transient
	private String name;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false)
	@JsonIgnore
	private Country country;

	@Column(name="country_id", insertable = false, updatable = false)
	private Long countryId;

	@Transient
	private List<City> cities = new ArrayList<>();

	public State() {
	}

	
	public State(Long id, String name) {
		this.id = id;
		this.name = name;
	}



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void addCity(City city) {
		if (this.cities == null){
			this.cities = new ArrayList<>();
		}
		this.cities.add(city);
	}

	public void removeCity(City city) {
		if (this.cities != null && this.cities.contains(city)){
			this.cities.remove(city);
		}
	}


	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "State [cities=" + cities + ", country=" + country + ", id=" + id + ", stateName=" + stateName + "]";
	}


	
	
}