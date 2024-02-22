package com.warehouse.administration;

import java.util.List;

import com.warehouse.administration.applicationconfiguration.ConfigureParameterRequest;
import com.warehouse.administration.applicationconfiguration.DefaultConfigParameters;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValue;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueResponse;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueSearchRequest;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfig;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfig;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfig;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfig;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfigParamtersResponse;
import com.warehouse.administration.organization.OrganizationRequest;
import com.warehouse.administration.role.UserRole;
import com.warehouse.administration.role.UserRoleRequest;
import com.warehouse.administration.user.ChangePasswordRequest;
import com.warehouse.administration.user.User;
import com.warehouse.administration.user.UserRequest;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMapping;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMappingListResponse;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMapping;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMappingListResponse;
import com.warehouse.administration.user.rolemapping.UserRoleMapping;
import com.warehouse.administration.user.rolemapping.UserRoleMappingListResponse;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMapping;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMappingListResponse;
import com.warehouse.enums.UserTypeEnum;
import com.warehouse.helper.PageHelper;

import org.springframework.data.domain.Page;

public interface AdministrationService {
    public User getUserByUserName(String userName);

    // User Role
    public String validateUserRoleRequest(UserRole theUserRole);

    public List<UserRole> saveUserRole(List<UserRole> theUserRole);

    public Page<UserRole> findUserRoleList(PageHelper page, UserRoleRequest request);

    public List<UserRole> findUserRoleList(UserRoleRequest request);

    // User
    public String validateUserRequest(User theUser);

    public List<UserRole> findUserRoleByUserType(UserTypeEnum userType);

    public List<User> saveUser(List<User> theUserList);

    public Page<User> findUserList(PageHelper page, UserRequest request);

    public List<User> findUserList(UserRequest request);

    public void changePassword(ChangePasswordRequest request);

    // User Role Mapping
    public String validateUserRoleMappingRequest(UserRoleMapping theUserRoleMapping);

    public List<UserRoleMapping> saveUserRoleMapping(List<UserRoleMapping> theUserRoleMapping);

    public void deleteAllUserRoleMapping(List<UserRoleMapping> theUserRoleMapping);

    public List<UserRoleMappingListResponse> findUserListWithRoleMapping(UserRequest request);

    // User Primary Company Mapping
    public String validateUserPrimaryCompanyMappingRequest(UserPrimaryCompanyMapping theUserPrimaryCompanyMapping);

    public List<UserPrimaryCompanyMapping> saveUserPrimaryCompanyMapping(
            List<UserPrimaryCompanyMapping> theUserPrimaryCompanyMapping);

    public void deleteAllUserPrimaryCompanyMapping(List<UserPrimaryCompanyMapping> theUserPrimaryCompanyMapping);

    public List<UserPrimaryCompanyMappingListResponse> findUserListWithPrimaryCompanyMapping(UserRequest request);

    // User Warehouse Mapping
    public Page<User> findUserListForWarehouseMapping(PageHelper page, UserRequest request);

    public String validateUserWarehouseMappingRequest(UserWarehouseMapping theUserWarehouseMapping);

    public List<UserWarehouseMapping> saveUserWarehouseMapping(List<UserWarehouseMapping> theUserWarehouseMapping);

    public void deleteAllUserWarehouseMapping(List<UserWarehouseMapping> theUserWarehouseMapping);

    public List<UserWarehouseMappingListResponse> findUserListWithWarehouseMapping(UserRequest request);

    // User Cost Bucket Mapping
    public String validateUserCostBucketMappingRequest(UserCostBucketMapping theUserCostBucketMapping);

    public List<UserCostBucketMapping> saveUserCostBucketMapping(List<UserCostBucketMapping> theUserCostBucketMapping);

    public void deleteAllUserCostBucketMapping(List<UserCostBucketMapping> theUserCostBucketMapping);

    public List<UserCostBucketMappingListResponse> findUserListWithCostBucketMapping(UserRequest request);

    // Default Config Parameters List
    public List<DefaultConfigParameters> findByIsPrimaryCompanyConfigTrue();

    public List<DefaultConfigParameters> findByIsWarehouseConfigTrue();

    public List<DefaultConfigParameters> findByIsPrimaryCompanyConfigTrueAndIsWarehouseConfigTrue();

    // Primary Company Configure Parameters
    public String validatePrimaryCompanyConfigParametersRequest(PrimaryCompanyConfig thePrimaryCompanyConfig);

    public Page<PrimaryCompanyConfigParamtersResponse> findPrimaryCompanyList(PageHelper page,
            ConfigureParameterRequest request);

    public List<PrimaryCompanyConfigParamtersResponse> findPrimaryCompanyListWithConfigParameter(
            ConfigureParameterRequest request);

    public List<PrimaryCompanyConfig> savePrimaryCompanyConfigParameters(
            List<PrimaryCompanyConfig> thePrimaryCompanyConfigList);

    public void deletePrimaryCompanyConfigParameters(List<PrimaryCompanyConfig> thePrimaryCompanyConfigList);

    // Warehouse Configure Parameters
    public String validateWarehouseConfigParametersRequest(WarehouseConfig theWarehouseConfig);

    public Page<WarehouseConfigParamtersResponse> findWarehouseList(PageHelper page, ConfigureParameterRequest request);

    public List<WarehouseConfigParamtersResponse> findWarehouseListWithConfigParameter(
            ConfigureParameterRequest request);

    public List<WarehouseConfig> saveWarehouseConfigParameters(List<WarehouseConfig> theWarehouseConfigList);

    public void deleteWarehouseConfigParameters(List<WarehouseConfig> theWarehouseConfigList);

    // Warehouse Company Mapping Configure Parameters
    public String validateWarehousePrimaryCompanyConfigParametersRequest(
            WarehousePrimaryCompanyConfig theWarehousePrimaryCompanyConfig);

    public Page<WarehousePrimaryCompanyConfigParamtersResponse> findWarehousePrimaryCompanyList(PageHelper page,
            ConfigureParameterRequest request);

    public List<WarehousePrimaryCompanyConfigParamtersResponse> findWarehouseCompanyMappingListWithConfig(
            ConfigureParameterRequest request);

    public List<WarehousePrimaryCompanyConfig> saveWarehousePrimaryCompanyConfigParameters(
            List<WarehousePrimaryCompanyConfig> theWarehousePrimaryCompanyConfigList);

    public void deleteWarehousePrimaryCompanyConfigParameters(
            List<WarehousePrimaryCompanyConfig> theWarehousePrimaryCompanyConfigList);

    // Organisation Configure Parameters
    public String validateOrganizationConfigParametersRequest(OrganizationConfig theOrganizationConfig);

    public Page<OrganizationConfigParamtersResponse> findOrganizationList(PageHelper page, OrganizationRequest request);

    public List<OrganizationConfigParamtersResponse> findOrganizationListWithConfigParameter(
            OrganizationRequest request);

    public List<OrganizationConfig> saveOrganizationConfigParameters(
            List<OrganizationConfig> theOrganizationConfigList);

    public void deleteOrganizationConfigParameters(List<OrganizationConfig> theOrganizationConfigList);

    // Default Interface Value
    public String validateDefaultInterfaceValueRequest(DefaultInterfaceValue theDefaultInterfaceValue);

    public Page<DefaultInterfaceValueResponse> findPrimaryCompanyList(PageHelper page,
            DefaultInterfaceValueSearchRequest request);

    public List<DefaultInterfaceValueResponse> findPrimaryCompanyListWithDefaultInterfaceValue(
            DefaultInterfaceValueSearchRequest request);

    public List<DefaultInterfaceValue> saveDefaultInterfaceValue(
            List<DefaultInterfaceValue> theDefaultInterfaceValueList);

    public void deleteDefaultInterfaceValue(List<DefaultInterfaceValue> theDefaultInterfaceValueList);
}
