package com.warehouse.country;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.warehouse.state.State;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@Table(name = "country",schema = "public")
public class Country{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotBlank(message = "{country.name}")
	@Size(max = 100)
	@Column(name="country_name",unique = true)
	private String countryName;

	@Transient
	private List<State> states =  new ArrayList<>();

	@Transient
	private String name;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<State> getStates() {
		return this.states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public void addState(State state) {
		if (this.states == null){
			this.states = new ArrayList<>();
		}
		this.states.add(state);
	}

	public void removeState(State state) {
		if (this.states != null && this.states.contains(state)){
			this.states.remove(state);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country() {
	}

	public Country(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [countryName=" + countryName + ", id=" + id + ", states=" + states + "]";
	}


}