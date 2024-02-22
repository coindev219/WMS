package com.warehouse.administration.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warehouse.administration.warehousescreen.WarehouseScreen;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "user_role_warehouse_screen_mapping", schema = "public")
public class UserRoleWarehouseScreenMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_role_id")
    @JsonIgnore
    private Long userRoleId;

    @ManyToOne
    @JoinColumn(name = "warehouse_screen_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private WarehouseScreen warehouseScreen;

    @NotNull
    @Column(name = "warehouse_screen_id", insertable = false, updatable = false)
    private Long warehouseScreenId;

    @Transient
    private String mainMenu;

    @Transient
    private String subMenu;

    @Transient
    private String screenName;

    @Column(name = "create_option")
    Boolean crateOption = false;

    @Column(name = "search_option")
    Boolean searchOption = false;

    @Column(name = "action_option")
    Boolean actionOption = false;

    @Column(name = "action_button_option")
    Boolean actionButtonOption = false;

    @Column(name = "report_option")
    Boolean reportOption = false;

    @Column(name = "hht_option")
    Boolean hhtOption = false;

    @Column(name = "notification_option")
    Boolean notificationOption = false;

    public UserRoleWarehouseScreenMapping() {
    }

    public UserRoleWarehouseScreenMapping(Long id, Long warehouseScreenId, String screenMainMenu, String screenSubMenu,
            String screenName, Boolean crateOption, Boolean searchOption, Boolean actionOption,
            Boolean actionButtonOption, Boolean reportOption, Boolean hhtOption, Boolean notificationOption) {
        this.id = id;
        this.warehouseScreenId = warehouseScreenId;
        this.mainMenu = screenMainMenu;
        this.subMenu = screenSubMenu;
        this.screenName = screenName;
        this.crateOption = crateOption;
        this.searchOption = searchOption;
        this.actionOption = actionOption;
        this.actionButtonOption = actionButtonOption;
        this.reportOption = reportOption;
        this.hhtOption = hhtOption;
        this.notificationOption = notificationOption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WarehouseScreen getWarehouseScreen() {
        return warehouseScreen;
    }

    public void setWarehouseScreen(WarehouseScreen warehouseScreen) {
        this.warehouseScreen = warehouseScreen;
    }

    public Long getWarehouseScreenId() {
        return warehouseScreenId;
    }

    public void setWarehouseScreenId(Long warehouseScreenId) {
        this.warehouseScreenId = warehouseScreenId;
    }

    public Boolean getCrateOption() {
        return crateOption;
    }

    public void setCrateOption(Boolean crateOption) {
        this.crateOption = crateOption;
    }

    public Boolean getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(Boolean searchOption) {
        this.searchOption = searchOption;
    }

    public Boolean getActionOption() {
        return actionOption;
    }

    public void setActionOption(Boolean actionOption) {
        this.actionOption = actionOption;
    }

    public Boolean getActionButtonOption() {
        return actionButtonOption;
    }

    public void setActionButtonOption(Boolean actionButtonOption) {
        this.actionButtonOption = actionButtonOption;
    }

    public Boolean getReportOption() {
        return reportOption;
    }

    public void setReportOption(Boolean reportOption) {
        this.reportOption = reportOption;
    }

    public Boolean getHhtOption() {
        return hhtOption;
    }

    public void setHhtOption(Boolean hhtOption) {
        this.hhtOption = hhtOption;
    }

    public Boolean getNotificationOption() {
        return notificationOption;
    }

    public void setNotificationOption(Boolean notificationOption) {
        this.notificationOption = notificationOption;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
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

    @Override
    public String toString() {
        return "UserRoleWarehouseScreenMapping [actionButtonOption=" + actionButtonOption + ", actionOption="
                + actionOption + ", crateOption=" + crateOption + ", hhtOption=" + hhtOption + ", id=" + id
                + ", notificationOption=" + notificationOption + ", reportOption=" + reportOption + ", screenMainMenu="
                + mainMenu + ", screenName=" + screenName + ", screenSubMenu=" + subMenu + ", searchOption="
                + searchOption + ", userRoleId=" + userRoleId + ", warehouseScreen=" + warehouseScreen
                + ", warehouseScreenId=" + warehouseScreenId + "]";
    }

}
