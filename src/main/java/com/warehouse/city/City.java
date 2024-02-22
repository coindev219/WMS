package com.warehouse.city;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import com.warehouse.state.State;


/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@Table(name = "city",schema = "public")
public class City  {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotBlank(message = "{city.name}")
	@Size(max = 100)
	@Column(name="city_name")
	private String cityName;

	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false)
	@JsonIgnore
	private State state;

	@Column(name="state_id",insertable = false, updatable = false)
	private Long stateId;

	@Transient
	private String name;

	public City() {
	}

	public City(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "City [cityName=" + cityName + ", id=" + id + ", name=" + name + ", state=" + state + ", stateId="
				+ stateId + "]";
	}
	
}