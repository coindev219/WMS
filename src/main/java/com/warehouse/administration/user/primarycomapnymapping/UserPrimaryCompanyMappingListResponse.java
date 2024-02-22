package com.warehouse.administration.user.primarycomapnymapping;

import java.util.List;

import com.warehouse.enums.UserTypeEnum;

public class UserPrimaryCompanyMappingListResponse {

    private Long userId;
    private String employeeNumber;
    private String employeeName;
    private String username;
    private UserTypeEnum userType;

    private List<UserPrimaryCompanyMapping> primaryCompanyMappingList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public List<UserPrimaryCompanyMapping> getUserRoleMappingList() {
        return primaryCompanyMappingList;
    }

    public void setUserRoleMappingList(List<UserPrimaryCompanyMapping> primaryCompanyMappingList) {
        this.primaryCompanyMappingList = primaryCompanyMappingList;
    }

    

    public UserPrimaryCompanyMappingListResponse(Long userId, String employeeNumber, String employeeName, String username,
            UserTypeEnum userType) {
        this.userId = userId;
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.username = username;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserRoleMappingListResponse [employeeName=" + employeeName + ", employeeNumber=" + employeeNumber
                + ", userId=" + userId + ", primaryCompanyMappingList=" + primaryCompanyMappingList + ", userType=" + userType
                + ", username=" + username + "]";
    }

}
