package com.warehouse.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.warehouse.administration.AdministrationService;
import com.warehouse.administration.applicationconfiguration.ConfigureParameterRequest;
import com.warehouse.administration.applicationconfiguration.DefaultConfigParameters;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValue;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueRepository;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueResponse;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueSearchRequest;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfig;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfigRepository;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfig;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfigRepository;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfig;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfigRepository;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfig;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfigRepository;
import com.warehouse.administration.organization.OrganizationRequest;
import com.warehouse.administration.role.UserRole;
import com.warehouse.administration.role.UserRoleRequest;
import com.warehouse.administration.user.ChangePasswordRequest;
import com.warehouse.administration.user.User;
import com.warehouse.administration.user.UserRequest;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMapping;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMappingListResponse;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMappingRepository;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMapping;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMappingListResponse;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMappingRepository;
import com.warehouse.administration.user.rolemapping.UserRoleMapping;
import com.warehouse.administration.user.rolemapping.UserRoleMappingListResponse;
import com.warehouse.administration.user.rolemapping.UserRoleMappingRepository;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMapping;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMappingListResponse;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMappingRepository;
import com.warehouse.administration.warehousescreen.WarehouseScreen;
import com.warehouse.administration.warehousescreen.WarehouseScreenRepository;
import com.warehouse.enums.UserTypeEnum;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administration")
public class AdministrationRestController {

    @Autowired
    private WarehouseScreenRepository warehouseScreenRepository;

    @Autowired
    private AdministrationService administrationService;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    private UserWarehouseMappingRepository userWarehouseMappingRepository;

    @Autowired
    private UserPrimaryCompanyMappingRepository userPrimaryCompanyMappingRepository;

    @Autowired
    private UserCostBucketMappingRepository userCostBucketMappingRepository;

    @Autowired
    private PrimaryCompanyConfigRepository primaryCompanyConfigRepository;

    @Autowired
    private WarehouseConfigRepository warehouseConfigRepository;

    @Autowired
    private WarehousePrimaryCompanyConfigRepository warehousePrimaryCompanyConfigRepository;

    @Autowired
    private OrganizationConfigRepository organizationConfigRepository;

    @Autowired
    private DefaultInterfaceValueRepository defaultInterfaceValueRepository;


    @PostMapping("/save-warehouse-screen")
    public ResponseEntity<List<WarehouseScreen>> saveWarehouseScreen(
            @RequestBody List<WarehouseScreen> warehouseScreens) {
        return new ResponseEntity<>(warehouseScreenRepository.saveAll(warehouseScreens), HttpStatus.OK);
    }

    @GetMapping("/warehouse-screen-list/{userType}")
    public ResponseEntity<List<WarehouseScreen>> warehouseScreenListByUserType(
            @PathVariable("userType") UserTypeEnum userType) {
        return new ResponseEntity<>(warehouseScreenRepository.findAllByUserType(userType), HttpStatus.OK);
    }

    @PostMapping("/user-role/save")
    public ResponseEntity<List<UserRole>> saveUserRole(@Valid @RequestBody List<UserRole> theUserRoleList) {
        return new ResponseEntity<>(administrationService.saveUserRole(theUserRoleList), HttpStatus.OK);
    }

    @PostMapping("/user-role/search")
    public ResponseEntity<Page<UserRole>> getUserRoleList(PageHelper page, @RequestBody UserRoleRequest request) {
        return new ResponseEntity<>(administrationService.findUserRoleList(page, request), HttpStatus.OK);
    }

    @PostMapping("/user-role/search-all")
    public ResponseEntity<List<UserRole>> getUserRoleList(@RequestBody UserRoleRequest request) {
        return new ResponseEntity<>(administrationService.findUserRoleList(request), HttpStatus.OK);
    }

    @GetMapping("/user-role/{userType}")
    public ResponseEntity<List<UserRole>> findUserRoleByUserType(@PathVariable("userType") UserTypeEnum userType) {
        return new ResponseEntity<>(administrationService.findUserRoleByUserType(userType), HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<List<User>> saveUser(@Valid @RequestBody List<User> theUserList) {
        return new ResponseEntity<>(administrationService.saveUser(theUserList), HttpStatus.OK);
    }

    @PostMapping("/user/search")
    public ResponseEntity<Page<User>> getUserList(PageHelper page, @RequestBody UserRequest request) {
        return new ResponseEntity<>(administrationService.findUserList(page, request), HttpStatus.OK);
    }

    @PostMapping("/user/search-all")
    public ResponseEntity<List<User>> getUserList(@RequestBody UserRequest request) {
        return new ResponseEntity<>(administrationService.findUserList(request), HttpStatus.OK);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        administrationService.changePassword(changePasswordRequest);
        return ResponseEntity.ok().build();
    }

    // User Role mapping
    @PostMapping("/user-role-mapping")
    public List<UserRoleMapping> saveUserRoleMapping(@Valid @RequestBody List<UserRoleMapping> userRoleMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (UserRoleMapping userRoleMapping : userRoleMappingList) {
            String error = administrationService.validateUserRoleMappingRequest(userRoleMapping);
            if (error != null) {
                throw new InvalidValueFoundException("UserRoleMapping", error);
            }

            String duplicateKeyValue = userRoleMapping.getUser() + "" + userRoleMapping.getUserId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Mapping with User Name:" + userRoleMapping.getUser().getUsername()
                        + " and User Role:" + userRoleMapping.getUserRole().getName();
                throw new InvalidValueFoundException("UserRoleMapping", error);
            }
            arrayList.add(duplicateKeyValue);

        }
        for (UserRoleMapping userRoleMapping : userRoleMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            userRoleMapping.setCreatedBy(user);
            userRoleMapping.setModifiedBy(user);

            if (userRoleMapping.getId() != null) {
                UserRoleMapping theUserRoleMapping = userRoleMappingRepository.findById(userRoleMapping.getId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("UserRoleMapping", "id", userRoleMapping.getId()));
                userRoleMapping.setCreatedAt(theUserRoleMapping.getCreatedAt());
                userRoleMapping.setCreatedBy(theUserRoleMapping.getCreatedBy());
            }
        }
        return administrationService.saveUserRoleMapping(userRoleMappingList);
    }

    @DeleteMapping("/user-role-mapping")
    public ResponseEntity<?> deleteAllUserRoleMapping(@RequestBody List<UserRoleMapping> userRoleMappingList) {
        administrationService.deleteAllUserRoleMapping(userRoleMappingList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-role-mapping/search-all")
    public ResponseEntity<List<UserRoleMappingListResponse>> findUserListWithRoleMapping(
            @RequestBody UserRequest request) {
        return new ResponseEntity<>(administrationService.findUserListWithRoleMapping(request), HttpStatus.OK);
    }

    // User Warehouse Mapping
    @PostMapping("/user-warehouse-mapping/search")
    public ResponseEntity<Page<User>> findUserListForWarehouseMapping(PageHelper page,
            @RequestBody UserRequest request) {
        return new ResponseEntity<>(administrationService.findUserListForWarehouseMapping(page, request),
                HttpStatus.OK);
    }

    @PostMapping("/user-warehouse-mapping")
    public List<UserWarehouseMapping> saveUserWarehouseMapping(
            @Valid @RequestBody List<UserWarehouseMapping> userRoleMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (UserWarehouseMapping userRoleMapping : userRoleMappingList) {
            String error = administrationService.validateUserWarehouseMappingRequest(userRoleMapping);
            if (error != null) {
                throw new InvalidValueFoundException("UserWarehouseMapping", error);
            }

            String duplicateKeyValue = userRoleMapping.getUser() + "" + userRoleMapping.getUserId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Mapping with User Name:" + userRoleMapping.getUser().getUsername()
                        + " and User Role:" + userRoleMapping.getWarehouse().getName();
                throw new InvalidValueFoundException("UserWarehouseMapping", error);
            }
            arrayList.add(duplicateKeyValue);

        }
        for (UserWarehouseMapping userRoleMapping : userRoleMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            userRoleMapping.setCreatedBy(user);
            userRoleMapping.setModifiedBy(user);

            if (userRoleMapping.getId() != null) {
                UserWarehouseMapping theUserWarehouseMapping = userWarehouseMappingRepository
                        .findById(userRoleMapping.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserWarehouseMapping", "id",
                                userRoleMapping.getId()));
                userRoleMapping.setCreatedAt(theUserWarehouseMapping.getCreatedAt());
                userRoleMapping.setCreatedBy(theUserWarehouseMapping.getCreatedBy());
            }
        }
        return administrationService.saveUserWarehouseMapping(userRoleMappingList);
    }

    @DeleteMapping("/user-warehouse-mapping")
    public ResponseEntity<?> deleteAllUserWarehouseMapping(
            @RequestBody List<UserWarehouseMapping> userRoleMappingList) {
        administrationService.deleteAllUserWarehouseMapping(userRoleMappingList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-warehouse-mapping/search-all")
    public ResponseEntity<List<UserWarehouseMappingListResponse>> findUserListWithWarehouseMapping(
            @RequestBody UserRequest request) {
        return new ResponseEntity<>(administrationService.findUserListWithWarehouseMapping(request), HttpStatus.OK);
    }

    // User Primary Company mapping
    @PostMapping("/user-primarycompany-mapping")
    public List<UserPrimaryCompanyMapping> saveUserPrimaryCompanyMapping(
            @Valid @RequestBody List<UserPrimaryCompanyMapping> userRoleMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (UserPrimaryCompanyMapping userRoleMapping : userRoleMappingList) {
            String error = administrationService.validateUserPrimaryCompanyMappingRequest(userRoleMapping);
            if (error != null) {
                throw new InvalidValueFoundException("UserPrimaryCompanyMapping", error);
            }

            String duplicateKeyValue = userRoleMapping.getUser() + "" + userRoleMapping.getUserId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Mapping with User Name:" + userRoleMapping.getUser().getUsername()
                        + " and Primary Company:" + userRoleMapping.getPrimaryCompany().getName();
                throw new InvalidValueFoundException("UserPrimaryCompanyMapping", error);
            }
            arrayList.add(duplicateKeyValue);

        }
        for (UserPrimaryCompanyMapping userRoleMapping : userRoleMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            userRoleMapping.setCreatedBy(user);
            userRoleMapping.setModifiedBy(user);

            if (userRoleMapping.getId() != null) {
                UserPrimaryCompanyMapping theUserPrimaryCompanyMapping = userPrimaryCompanyMappingRepository
                        .findById(userRoleMapping.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserPrimaryCompanyMapping", "id",
                                userRoleMapping.getId()));
                userRoleMapping.setCreatedAt(theUserPrimaryCompanyMapping.getCreatedAt());
                userRoleMapping.setCreatedBy(theUserPrimaryCompanyMapping.getCreatedBy());
            }
        }
        return administrationService.saveUserPrimaryCompanyMapping(userRoleMappingList);
    }

    @DeleteMapping("/user-primarycompany-mapping")
    public ResponseEntity<?> deleteAllUserPrimaryCompanyMapping(
            @RequestBody List<UserPrimaryCompanyMapping> userRoleMappingList) {
        administrationService.deleteAllUserPrimaryCompanyMapping(userRoleMappingList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-primarycompany-mapping/search-all")
    public ResponseEntity<List<UserPrimaryCompanyMappingListResponse>> findUserListWithPrimaryCompanyMapping(
            @RequestBody UserRequest request) {
        return new ResponseEntity<>(administrationService.findUserListWithPrimaryCompanyMapping(request),
                HttpStatus.OK);
    }

    // User Cost Bucket mapping
    @PostMapping("/user-costbucket-mapping")
    public List<UserCostBucketMapping> saveUserCostBucketMapping(
            @Valid @RequestBody List<UserCostBucketMapping> userRoleMappingList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (UserCostBucketMapping userRoleMapping : userRoleMappingList) {
            String error = administrationService.validateUserCostBucketMappingRequest(userRoleMapping);
            if (error != null) {
                throw new InvalidValueFoundException("UserCostBucketMapping", error);
            }

            String duplicateKeyValue = userRoleMapping.getUser() + "" + userRoleMapping.getUserId();
            if (arrayList.contains(duplicateKeyValue)) {
                error = "Duplicate value found for Mapping with User Name:" + userRoleMapping.getUser().getUsername()
                        + " and Primary Company:" + userRoleMapping.getCostBucket().getName();
                throw new InvalidValueFoundException("UserCostBucketMapping", error);
            }
            arrayList.add(duplicateKeyValue);

        }
        for (UserCostBucketMapping userRoleMapping : userRoleMappingList) {

            User user = WarehouseHelper.getLoggedInUser();
            userRoleMapping.setCreatedBy(user);
            userRoleMapping.setModifiedBy(user);

            if (userRoleMapping.getId() != null) {
                UserCostBucketMapping theUserCostBucketMapping = userCostBucketMappingRepository
                        .findById(userRoleMapping.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserCostBucketMapping", "id",
                                userRoleMapping.getId()));
                userRoleMapping.setCreatedAt(theUserCostBucketMapping.getCreatedAt());
                userRoleMapping.setCreatedBy(theUserCostBucketMapping.getCreatedBy());
            }
        }
        return administrationService.saveUserCostBucketMapping(userRoleMappingList);
    }

    @DeleteMapping("/user-costbucket-mapping")
    public ResponseEntity<?> deleteAllUserCostBucketMapping(
            @RequestBody List<UserCostBucketMapping> userRoleMappingList) {
        administrationService.deleteAllUserCostBucketMapping(userRoleMappingList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-costbucket-mapping/search-all")
    public ResponseEntity<List<UserCostBucketMappingListResponse>> findUserListWithCostBucketMapping(
            @RequestBody UserRequest request) {
        return new ResponseEntity<>(administrationService.findUserListWithCostBucketMapping(request), HttpStatus.OK);
    }

    @GetMapping("/default-config-parameters-company/list")
    public ResponseEntity<List<DefaultConfigParameters>> findByIsPrimaryCompanyConfigTrue() {
        return new ResponseEntity<>(administrationService.findByIsPrimaryCompanyConfigTrue(), HttpStatus.OK);
    }

    @GetMapping("/default-config-parameters-warehouse/list")
    public ResponseEntity<List<DefaultConfigParameters>> findByIsWarehouseConfigTrue() {
        return new ResponseEntity<>(administrationService.findByIsWarehouseConfigTrue(), HttpStatus.OK);
    }

    @GetMapping("/default-config-parameters-company-warehouse/list")
    public ResponseEntity<List<DefaultConfigParameters>> findByIsPrimaryCompanyConfigTrueAndIsWarehouseConfigTrue() {
        return new ResponseEntity<>(administrationService.findByIsPrimaryCompanyConfigTrueAndIsWarehouseConfigTrue(),
                HttpStatus.OK);
    }

    // Primary Company Configure Parameters

    @PostMapping("/configuration/primary-company/search")
    public ResponseEntity<Page<PrimaryCompanyConfigParamtersResponse>> findPrimaryCompanyList(PageHelper page,
            @RequestBody ConfigureParameterRequest request) {
        return new ResponseEntity<>(administrationService.findPrimaryCompanyList(page, request), HttpStatus.OK);
    }

    @PostMapping("/configuration/primary-company-config/search")
    public ResponseEntity<List<PrimaryCompanyConfigParamtersResponse>> findPrimaryCompanyListWithConfigParameter(
            @RequestBody ConfigureParameterRequest request) {
        return new ResponseEntity<>(administrationService.findPrimaryCompanyListWithConfigParameter(request),
                HttpStatus.OK);
    }

    @PostMapping("/configuration/primary-company-config/save")
    public ResponseEntity<List<PrimaryCompanyConfig>> savePrimaryCompanyConfigParameters(
            @RequestBody List<PrimaryCompanyConfig> thePrimaryCompanyConfigList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (PrimaryCompanyConfig primaryCompanyConfig : thePrimaryCompanyConfigList) {
            String error = administrationService.validatePrimaryCompanyConfigParametersRequest(primaryCompanyConfig);
            if (error != null) {
                throw new InvalidValueFoundException("PrimaryCompanyConfig", error);
            }

            String duplicateKeyValue = primaryCompanyConfig.getConfigKey() + ""
                    + primaryCompanyConfig.getPrimaryCompany().getName();
            if (arrayList.contains(duplicateKeyValue)) {

                error = "Duplicate value found with Config Key:" + primaryCompanyConfig.getConfigKey()
                        + " and Primary Company:" + primaryCompanyConfig.getPrimaryCompany().getName();

                throw new InvalidValueFoundException("PrimaryCompanyConfig", error);
            }
            arrayList.add(duplicateKeyValue);

        }

        for (PrimaryCompanyConfig primaryCompanyConfig : thePrimaryCompanyConfigList) {
            User user = WarehouseHelper.getLoggedInUser();
            primaryCompanyConfig.setCreatedBy(user);
            primaryCompanyConfig.setModifiedBy(user);

            if (primaryCompanyConfig.getId() != null) {
                PrimaryCompanyConfig primaryCompanyConfigObj = primaryCompanyConfigRepository
                        .findById(primaryCompanyConfig.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserCostBucketMapping", "id",
                                primaryCompanyConfig.getId()));
                primaryCompanyConfig.setCreatedAt(primaryCompanyConfigObj.getCreatedAt());
                primaryCompanyConfig.setCreatedBy(primaryCompanyConfigObj.getCreatedBy());
            }
        }

        return new ResponseEntity<>(
                administrationService.savePrimaryCompanyConfigParameters(thePrimaryCompanyConfigList), HttpStatus.OK);

    }

    @DeleteMapping("/configuration/primary-company-config/delete")
    public ResponseEntity<?> deletePrimaryCompanyConfigParameters(@RequestBody 
            List<PrimaryCompanyConfig> thePrimaryCompanyConfigList) {
        administrationService.deletePrimaryCompanyConfigParameters(thePrimaryCompanyConfigList);
        return ResponseEntity.ok().build();
    }

    // Warehouse Configure Parameters
    @PostMapping("/configuration/warehouse/search")
    public ResponseEntity<Page<WarehouseConfigParamtersResponse>> findWarehouseList(PageHelper page,
            @RequestBody ConfigureParameterRequest request) {
        return new ResponseEntity<>(administrationService.findWarehouseList(page, request), HttpStatus.OK);
    }

    @PostMapping("/configuration/warehouse-config/search")
    public ResponseEntity<List<WarehouseConfigParamtersResponse>> findWarehouseListWithConfigParameter(
            @RequestBody ConfigureParameterRequest request) {
        return new ResponseEntity<>(administrationService.findWarehouseListWithConfigParameter(request),
                HttpStatus.OK);
    }

    @PostMapping("/configuration/warehouse-config/save")
    public ResponseEntity<List<WarehouseConfig>> saveWarehouseConfigParameters(
            @RequestBody List<WarehouseConfig> theWarehouseConfigList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (WarehouseConfig warehouseConfig : theWarehouseConfigList) {
            String error = administrationService.validateWarehouseConfigParametersRequest(warehouseConfig);
            if (error != null) {
                throw new InvalidValueFoundException("WarehouseConfig", error);
            }

            String duplicateKeyValue = warehouseConfig.getConfigKey() + ""
                    + warehouseConfig.getWarehouse().getName();
            if (arrayList.contains(duplicateKeyValue)) {

                error = "Duplicate value found with Config Key:" + warehouseConfig.getConfigKey()
                        + " and Primary Company:" + warehouseConfig.getWarehouse().getName();

                throw new InvalidValueFoundException("WarehouseConfig", error);
            }
            arrayList.add(duplicateKeyValue);

        }

        for (WarehouseConfig warehouseConfig : theWarehouseConfigList) {
            User user = WarehouseHelper.getLoggedInUser();
            warehouseConfig.setCreatedBy(user);
            warehouseConfig.setModifiedBy(user);

            if (warehouseConfig.getId() != null) {
                WarehouseConfig warehouseConfigObj = warehouseConfigRepository
                        .findById(warehouseConfig.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserCostBucketMapping", "id",
                                warehouseConfig.getId()));
                warehouseConfig.setCreatedAt(warehouseConfigObj.getCreatedAt());
                warehouseConfig.setCreatedBy(warehouseConfigObj.getCreatedBy());
            }
        }

        return new ResponseEntity<>(
                administrationService.saveWarehouseConfigParameters(theWarehouseConfigList), HttpStatus.OK);

    }

    @DeleteMapping("/configuration/warehouse-config/delete")
    public ResponseEntity<?> deleteWarehouseConfigParameters(@RequestBody 
            List<WarehouseConfig> theWarehouseConfigList) {
        administrationService.deleteWarehouseConfigParameters(theWarehouseConfigList);
        return ResponseEntity.ok().build();
    }

    // Warehouse Primary Company Configure Parameters
    @PostMapping("/configuration/warehouse-company/search")
    public ResponseEntity<Page<WarehousePrimaryCompanyConfigParamtersResponse>> findWarehousePrimaryCompanyList(PageHelper page,
            @RequestBody ConfigureParameterRequest request) {
        return new ResponseEntity<>(administrationService.findWarehousePrimaryCompanyList(page, request), HttpStatus.OK);
    }

    @PostMapping("/configuration/warehouse-company-config/search")
    public ResponseEntity<List<WarehousePrimaryCompanyConfigParamtersResponse>> findWarehouseCompanyMappingListWithConfig(
            @RequestBody ConfigureParameterRequest request) {
        return new ResponseEntity<>(administrationService.findWarehouseCompanyMappingListWithConfig(request),
                HttpStatus.OK);
    }

    @PostMapping("/configuration/warehouse-company-config/save")
    public ResponseEntity<List<WarehousePrimaryCompanyConfig>> saveWarehousePrimaryCompanyConfigParameters(
            @RequestBody List<WarehousePrimaryCompanyConfig> theWarehousePrimaryCompanyConfigList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (WarehousePrimaryCompanyConfig warehousePrimaryCompanyConfig : theWarehousePrimaryCompanyConfigList) {
            String error = administrationService.validateWarehousePrimaryCompanyConfigParametersRequest(warehousePrimaryCompanyConfig);
            if (error != null) {
                throw new InvalidValueFoundException("WarehousePrimaryCompanyConfig", error);
            }

            String duplicateKeyValue = warehousePrimaryCompanyConfig.getConfigKey() + ""
                    + warehousePrimaryCompanyConfig.getWarehouseCompanyMappingId();
            if (arrayList.contains(duplicateKeyValue)) {

                error = "Duplicate value found with Config Key:" + warehousePrimaryCompanyConfig.getConfigKey()
                        + " and Warehouse Primary Company:" + warehousePrimaryCompanyConfig.getWarehouseCompanyMappingId();

                throw new InvalidValueFoundException("WarehousePrimaryCompanyConfig", error);
            }
            arrayList.add(duplicateKeyValue);

        }

        for (WarehousePrimaryCompanyConfig warehousePrimaryCompanyConfig : theWarehousePrimaryCompanyConfigList) {
            User user = WarehouseHelper.getLoggedInUser();
            warehousePrimaryCompanyConfig.setCreatedBy(user);
            warehousePrimaryCompanyConfig.setModifiedBy(user);

            if (warehousePrimaryCompanyConfig.getId() != null) {
                WarehousePrimaryCompanyConfig warehousePrimaryCompanyConfigObj = warehousePrimaryCompanyConfigRepository
                        .findById(warehousePrimaryCompanyConfig.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserCostBucketMapping", "id",
                                warehousePrimaryCompanyConfig.getId()));
                warehousePrimaryCompanyConfig.setCreatedAt(warehousePrimaryCompanyConfigObj.getCreatedAt());
                warehousePrimaryCompanyConfig.setCreatedBy(warehousePrimaryCompanyConfigObj.getCreatedBy());
            }
        }

        return new ResponseEntity<>(
                administrationService.saveWarehousePrimaryCompanyConfigParameters(theWarehousePrimaryCompanyConfigList), HttpStatus.OK);

    }

    @DeleteMapping("/configuration/warehouse-company-config/delete")
    public ResponseEntity<?> deleteWarehousePrimaryCompanyConfigParameters(@RequestBody 
            List<WarehousePrimaryCompanyConfig> theWarehousePrimaryCompanyConfigList) {
        administrationService.deleteWarehousePrimaryCompanyConfigParameters(theWarehousePrimaryCompanyConfigList);
        return ResponseEntity.ok().build();
    }

    // Organization Configure Parameters
    @PostMapping("/configuration/organization/search")
    public ResponseEntity<Page<OrganizationConfigParamtersResponse>> findOrganizationList(PageHelper page,
            @RequestBody OrganizationRequest request) {
        return new ResponseEntity<>(administrationService.findOrganizationList(page, request), HttpStatus.OK);
    }

    @PostMapping("/configuration/organization-config/search")
    public ResponseEntity<List<OrganizationConfigParamtersResponse>> findOrganizationListWithConfigParameter(
            @RequestBody OrganizationRequest request) {
        return new ResponseEntity<>(administrationService.findOrganizationListWithConfigParameter(request),
                HttpStatus.OK);
    }

    @PostMapping("/configuration/organization-config/save")
    public ResponseEntity<List<OrganizationConfig>> saveOrganizationConfigParameters(
            @RequestBody List<OrganizationConfig> theOrganizationConfigList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (OrganizationConfig organizationConfig : theOrganizationConfigList) {
            String error = administrationService.validateOrganizationConfigParametersRequest(organizationConfig);
            if (error != null) {
                throw new InvalidValueFoundException("OrganizationConfig", error);
            }

            String duplicateKeyValue = organizationConfig.getConfigKey() + ""
                    + organizationConfig.getOrganization().getName();
            if (arrayList.contains(duplicateKeyValue)) {

                error = "Duplicate value found with Config Key:" + organizationConfig.getConfigKey()
                        + " and Organization:" + organizationConfig.getOrganization().getName();

                throw new InvalidValueFoundException("OrganizationConfig", error);
            }
            arrayList.add(duplicateKeyValue);

        }

        for (OrganizationConfig organizationConfig : theOrganizationConfigList) {
            User user = WarehouseHelper.getLoggedInUser();
            organizationConfig.setCreatedBy(user);
            organizationConfig.setModifiedBy(user);

            if (organizationConfig.getId() != null) {
                OrganizationConfig organizationConfigObj = organizationConfigRepository
                        .findById(organizationConfig.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserCostBucketMapping", "id",
                                organizationConfig.getId()));
                organizationConfig.setCreatedAt(organizationConfigObj.getCreatedAt());
                organizationConfig.setCreatedBy(organizationConfigObj.getCreatedBy());
            }
        }

        return new ResponseEntity<>(
                administrationService.saveOrganizationConfigParameters(theOrganizationConfigList), HttpStatus.OK);

    }

    @DeleteMapping("/configuration/organization-config/delete")
    public ResponseEntity<?> deleteOrganizationConfigParameters(@RequestBody 
            List<OrganizationConfig> theOrganizationConfigList) {
        administrationService.deleteOrganizationConfigParameters(theOrganizationConfigList);
        return ResponseEntity.ok().build();
    }

    // Default Interface Value
    @PostMapping("/configuration/default-interface_value/primary-company/search")
    public ResponseEntity<Page<DefaultInterfaceValueResponse>> findPrimaryCompanyList(PageHelper page,
            @RequestBody DefaultInterfaceValueSearchRequest request) {
        return new ResponseEntity<>(administrationService.findPrimaryCompanyList(page, request), HttpStatus.OK);
    }

    @PostMapping("/configuration/default-interface_value/primary-company-config/search")
    public ResponseEntity<List<DefaultInterfaceValueResponse>> findPrimaryCompanyListWithDefaultInterfaceValue(
            @RequestBody DefaultInterfaceValueSearchRequest request) {
        return new ResponseEntity<>(administrationService.findPrimaryCompanyListWithDefaultInterfaceValue(request),
                HttpStatus.OK);
    }

    @PostMapping("/configuration/default-interface_value/save")
    public ResponseEntity<List<DefaultInterfaceValue>> saveDefaultInterfaceValue(
            @RequestBody List<DefaultInterfaceValue> theDefaultInterfaceValueList) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (DefaultInterfaceValue defaultInterfaceValue : theDefaultInterfaceValueList) {
            String error = administrationService.validateDefaultInterfaceValueRequest(defaultInterfaceValue);
            if (error != null) {
                throw new InvalidValueFoundException("DefaultInterfaceValue", error);
            }

            String duplicateKeyValue = defaultInterfaceValue.getConfigKey() + ""
                    + defaultInterfaceValue.getPrimaryCompany().getName();
            if (arrayList.contains(duplicateKeyValue)) {

                error = "Duplicate value found with Config Key:" + defaultInterfaceValue.getConfigKey()
                        + " and Primary Company:" + defaultInterfaceValue.getPrimaryCompany().getName();

                throw new InvalidValueFoundException("DefaultInterfaceValue", error);
            }
            arrayList.add(duplicateKeyValue);

        }

        for (DefaultInterfaceValue defaultInterfaceValue : theDefaultInterfaceValueList) {
            User user = WarehouseHelper.getLoggedInUser();
            defaultInterfaceValue.setCreatedBy(user);
            defaultInterfaceValue.setModifiedBy(user);

            if (defaultInterfaceValue.getId() != null) {
                DefaultInterfaceValue defaultInterfaceValueObj = defaultInterfaceValueRepository
                        .findById(defaultInterfaceValue.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserCostBucketMapping", "id",
                                defaultInterfaceValue.getId()));
                defaultInterfaceValue.setCreatedAt(defaultInterfaceValueObj.getCreatedAt());
                defaultInterfaceValue.setCreatedBy(defaultInterfaceValueObj.getCreatedBy());
            }
        }

        return new ResponseEntity<>(
                administrationService.saveDefaultInterfaceValue(theDefaultInterfaceValueList), HttpStatus.OK);

    }

    @DeleteMapping("/configuration/default-interface_value/delete")
    public ResponseEntity<?> deleteDefaultInterfaceValue(@RequestBody 
            List<DefaultInterfaceValue> theDefaultInterfaceValueList) {
        administrationService.deleteDefaultInterfaceValue(theDefaultInterfaceValueList);
        return ResponseEntity.ok().build();
    }


}
