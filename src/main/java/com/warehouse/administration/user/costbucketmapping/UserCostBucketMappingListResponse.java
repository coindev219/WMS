package com.warehouse.administration.user.costbucketmapping;

import java.util.List;

import com.warehouse.enums.UserTypeEnum;

public class UserCostBucketMappingListResponse {

    private Long userId;
    private String employeeNumber;
    private String employeeName;
    private String username;
    private UserTypeEnum userType;

    private List<UserCostBucketMapping> costBucketMappingList;

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

    public List<UserCostBucketMapping> getUserRoleMappingList() {
        return costBucketMappingList;
    }

    public void setUserRoleMappingList(List<UserCostBucketMapping> costBucketMappingList) {
        this.costBucketMappingList = costBucketMappingList;
    }

    

    public UserCostBucketMappingListResponse(Long userId, String employeeNumber, String employeeName, String username,
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
                + ", userId=" + userId + ", costBucketMappingList=" + costBucketMappingList + ", userType=" + userType
                + ", username=" + username + "]";
    }

}
