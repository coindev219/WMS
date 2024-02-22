package com.warehouse.administration.warehousescreen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.warehouse.enums.UserTypeEnum;

/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@Table(name = "warehouse_screen", schema = "public", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "main_menu", "sub_menu", "screen_name", "user_type" })})

public class WarehouseScreen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "{}")
	@Column(name = "main_menu")
	private String mainMenu;

	@NotBlank(message = "{}")
	@Column(name = "sub_menu")
	private String subMenu;

	@NotBlank(message = "{}")
	@Column(name = "screen_name")
	private String screenName;

	@NotNull
	@Column(name = "user_type")
	private UserTypeEnum userType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(String mainMenu) {
		this.mainMenu = mainMenu;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "WarehouseScreen [id=" + id + ", mainMenu=" + mainMenu + ", screenName=" + screenName + ", subMenu="
				+ subMenu + ", userType=" + userType + "]";
	}

}