package com.warehouse.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.warehouse.additionalsetup.AdditionalSetupService;
import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.additionalsetup.costbucket.CostBucketRepository;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.administration.applicationconfiguration.ConfigureParameterRequest;
import com.warehouse.administration.applicationconfiguration.DefaultConfigParameterRepository;
import com.warehouse.administration.applicationconfiguration.DefaultConfigParameters;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValue;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueRepository;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueResponse;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueSearchRepository;
import com.warehouse.administration.applicationconfiguration.defaultinterfacevalue.DefaultInterfaceValueSearchRequest;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfig;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationConfigRepository;
import com.warehouse.administration.applicationconfiguration.organizationparameters.OrganizationParameterSearchRepository;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfig;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyConfigRepository;
import com.warehouse.administration.applicationconfiguration.primarycomapnyparameters.PrimaryCompanyParametersSearchRepository;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfig;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseConfigRepository;
import com.warehouse.administration.applicationconfiguration.warehouseparameters.WarehouseParametersSearchRepository;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfig;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfigParamtersResponse;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfigRepository;
import com.warehouse.administration.applicationconfiguration.warehouseprimarycompanyparameters.WarehousePrimaryCompanyConfigSearchRepository;
import com.warehouse.administration.organization.Organization;
import com.warehouse.administration.organization.OrganizationRepository;
import com.warehouse.administration.organization.OrganizationRequest;
import com.warehouse.administration.role.UserRole;
import com.warehouse.administration.role.UserRoleRepository;
import com.warehouse.administration.role.UserRoleRequest;
import com.warehouse.administration.role.UserRoleSearchRepository;
import com.warehouse.administration.role.UserRoleWarehouseScreenMapping;
import com.warehouse.administration.user.ChangePasswordRequest;
import com.warehouse.administration.user.User;
import com.warehouse.administration.user.UserRepository;
import com.warehouse.administration.user.UserRequest;
import com.warehouse.administration.user.UserSearchRepository;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMapping;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMappingListResponse;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMappingRepository;
import com.warehouse.administration.user.costbucketmapping.UserCostBucketMappingSearchRepository;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMapping;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMappingListResponse;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMappingRepository;
import com.warehouse.administration.user.primarycomapnymapping.UserPrimaryCompanyMappingSearchRepository;
import com.warehouse.administration.user.rolemapping.UserRoleMapping;
import com.warehouse.administration.user.rolemapping.UserRoleMappingListResponse;
import com.warehouse.administration.user.rolemapping.UserRoleMappingRepository;
import com.warehouse.administration.user.rolemapping.UserRoleMappingSearchRepository;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMapping;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMappingListResponse;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMappingRepository;
import com.warehouse.administration.user.warehousemapping.UserWarehouseMappingSearchRepository;
import com.warehouse.administration.warehousescreen.WarehouseScreen;
import com.warehouse.administration.warehousescreen.WarehouseScreenRepository;
import com.warehouse.city.City;
import com.warehouse.city.CityRepository;
import com.warehouse.country.Country;
import com.warehouse.country.CountryRepository;
import com.warehouse.enums.OrderTypeEnum;
import com.warehouse.enums.TransactionTypeEnum;
import com.warehouse.enums.UserTypeEnum;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRepository;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.customer.CustomerRepository;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.supplier.SupplierRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRepository;
import com.warehouse.state.State;
import com.warehouse.state.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministrationServiceImpl implements AdministrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private WarehouseScreenRepository warehouseScreenRepository;

    @Autowired
    private UserRoleSearchRepository userRoleSearchRepository;

    @Autowired
    private PrimaryCompanyRepository primaryCompanyRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CostBucketRepository costBucketRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserSearchRepository userSearchRepository;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    private UserRoleMappingSearchRepository userRoleMappingSearchRepository;

    @Autowired
    private UserWarehouseMappingRepository userWarehouseMappingRepository;

    @Autowired
    private UserWarehouseMappingSearchRepository userWarehouseMappingSearchRepository;

    @Autowired
    private UserPrimaryCompanyMappingRepository userPrimaryCompanyMappingRepository;

    @Autowired
    private UserPrimaryCompanyMappingSearchRepository userPrimaryCompanyMappingSearchRepository;

    @Autowired
    private UserCostBucketMappingRepository userCostBucketMappingRepository;

    @Autowired
    private UserCostBucketMappingSearchRepository userCostBucketMappingSearchRepository;

    @Autowired
    private AdditionalSetupService additionalSetupService;

    @Autowired
    private DefaultConfigParameterRepository defaultConfigParameterRepository;

    @Autowired
    private PrimaryCompanyParametersSearchRepository primaryCompanyParametersSearchRepository;

    @Autowired
    private PrimaryCompanyConfigRepository primaryCompanyConfigRepository;

    @Autowired
    private WarehouseParametersSearchRepository warehouseParametersSearchRepository;

    @Autowired
    private WarehouseConfigRepository warehouseConfigRepository;

    @Autowired
    private WarehouseCompanyMappingRepository warehouseCompanyMappingRepository;

    @Autowired
    private WarehousePrimaryCompanyConfigRepository warehousePrimaryCompanyConfigRepository;

    @Autowired
    private WarehousePrimaryCompanyConfigSearchRepository warehousePrimaryCompanyConfigSearchRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationParameterSearchRepository organizationParameterSearchRepository;

    @Autowired
    private OrganizationConfigRepository organizationConfigRepository;

    @Autowired
    private DefaultInterfaceValueSearchRepository defaultInterfaceValueSearchRepository;

    @Autowired
    private DefaultInterfaceValueRepository defaultInterfaceValueRepository;


    @Override
    @Transactional
    public User getUserByUserName(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndIsActiveTrue(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
        return user;
    }

    @Override
    public String validateUserRoleRequest(UserRole theUserRole) {

        if (theUserRole.getId() == null) {
            if (userRoleRepository.findByName(theUserRole.getName()).isPresent()) {
                return "Duplicate value found for name:" + theUserRole.getName();
            }

            if (userRoleRepository.findByCode(theUserRole.getCode()).isPresent()) {
                return "Duplicate value found for code:" + theUserRole.getCode();
            }
        } else {
            if (userRoleRepository.findByNameAndIdNot(theUserRole.getName(), theUserRole.getId()).isPresent()) {
                return "Duplicate value found for name:" + theUserRole.getName();
            }

            if (userRoleRepository.findByCodeAndIdNot(theUserRole.getCode(), theUserRole.getId()).isPresent()) {
                return "Duplicate value found for code:" + theUserRole.getCode();
            }

        }

        List<UserRoleWarehouseScreenMapping> userRoleWarehouseScreenMappings = theUserRole
                .getUserRoleWarehouseScreenMappings();

        for (UserRoleWarehouseScreenMapping userRoleWarehouseScreenMapping : userRoleWarehouseScreenMappings) {
            WarehouseScreen warehouseScreen = warehouseScreenRepository
                    .findById(userRoleWarehouseScreenMapping.getWarehouseScreenId())
                    .orElseThrow(() -> new ResourceNotFoundException("WarehouseScreen", "Id",
                            userRoleWarehouseScreenMapping.getWarehouseScreenId()));

            if (theUserRole.getId() != null) {
                userRoleWarehouseScreenMapping.setUserRoleId(theUserRole.getId());
            }

            userRoleWarehouseScreenMapping.setWarehouseScreen(warehouseScreen);
        }

        return null;
    }

    @Override
    public List<UserRole> saveUserRole(List<UserRole> theUserRoleList) {
        User user = WarehouseHelper.getLoggedInUser();

        for (UserRole userRole : theUserRoleList) {
            String error = validateUserRoleRequest(userRole);
            if (error != null) {
                throw new InvalidValueFoundException("UserRole", error);
            }

            userRole.setCreatedBy(user);
            userRole.setModifiedBy(user);

            if (userRole.getId() != null) {
                UserRole userRoleObj = userRoleRepository.findById(userRole.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserRole", "Id", userRole.getId()));
                userRole.setCreatedBy(userRoleObj.getCreatedBy());
                userRole.setCreatedAt(userRoleObj.getCreatedAt());
            }
        }
        return userRoleRepository.saveAll(theUserRoleList);
    }

    @Override
    public Page<UserRole> findUserRoleList(PageHelper page, UserRoleRequest request) {
        return userRoleSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public List<UserRole> findUserRoleList(UserRoleRequest request) {
        return userRoleSearchRepository.findAllWithFilters(request);
    }

    @Override
    public String validateUserRequest(User theUser) {

        if (Objects.isNull(theUser.getWarehouseId())) {
            return "Warehouse must not be null:";
        }

        if (Objects.isNull(theUser.getPrimaryCompanyId())) {
            return "Primary Company must not be null:";
        }

        if (Objects.isNull(theUser.getCostBucketId())) {
            return "Cost Bucket must not be null:";
        }

        if (Objects.isNull(theUser.getCountryId())) {
            return "Country must not be null:";
        }

        if (Objects.isNull(theUser.getUserRoleId())) {
            return "User Role must not be null:";
        }

        Warehouse warehouse = warehouseRepository.findById(theUser.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", theUser.getWarehouseId()));
        theUser.setWarehouse(warehouse);

        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theUser.getPrimaryCompanyId()).orElseThrow(
                () -> new ResourceNotFoundException("PrimaryCompany", "id", theUser.getPrimaryCompanyId()));
        theUser.setPrimaryCompany(primaryCompany);

        CostBucket costBucket = costBucketRepository.findById(theUser.getCostBucketId())
                .orElseThrow(() -> new ResourceNotFoundException("CostBucket", "id", theUser.getCostBucketId()));
        theUser.setCostBucket(costBucket);

        Country country = countryRepository.findById(theUser.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country", "id", theUser.getCountryId()));
        theUser.setCountry(country);

        UserRole userRole = userRoleRepository.findById(theUser.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("UserRole", "id", theUser.getUserRoleId()));
        theUser.setUserRole(userRole);

        if (Objects.nonNull(theUser.getSupplierId())) {
            Supplier supplier = supplierRepository.findById(theUser.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", theUser.getSupplierId()));
            theUser.setSupplier(supplier);
        }

        if (Objects.nonNull(theUser.getCustomerId())) {
            Customer customer = customerRepository.findById(theUser.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", theUser.getCustomerId()));
            theUser.setCustomer(customer);
            ;
        }

        if (Objects.nonNull(theUser.getStateId())) {
            State state = stateRepository.findById(theUser.getStateId())
                    .orElseThrow(() -> new ResourceNotFoundException("State", "id", theUser.getStateId()));
            theUser.setState(state);
        }

        if (Objects.nonNull(theUser.getCityId())) {
            City city = cityRepository.findById(theUser.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City", "id", theUser.getCityId()));
            theUser.setCity(city);
        }

        if (theUser.getId() == null) {
            if (userRepository.findByUsername(theUser.getUsername()).isPresent()) {
                return "Duplicate value found for UserName:" + theUser.getUsername();
            }
        } else {
            if (userRepository.findByUsernameAndIdNot(theUser.getUsername(), theUser.getId()).isPresent()) {
                return "Duplicate value found for UserName:" + theUser.getUsername();
            }

        }

        return null;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User loggedInUser = WarehouseHelper.getLoggedInUser();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepository.findByUsernameAndIsActiveTrue(loggedInUser.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + loggedInUser.getUsername()));

        if (!encoder.matches(request.getOldPassword(), loggedInUser.getPassword())) {
            throw new ResourceNotFoundException("User", "Old Password", request.getOldPassword());
        }

        user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<UserRole> findUserRoleByUserType(UserTypeEnum userType) {
        return userRoleSearchRepository.findUserRoleByUserType(userType);
    }

    @Override
    public List<User> saveUser(List<User> theUserList) {
        User loggedInUser = WarehouseHelper.getLoggedInUser();

        HashMap<Long, Long> previousMappedWarehouse = new HashMap<Long, Long>();
        HashMap<Long, Long> previousMappedPrimaryCompany = new HashMap<Long, Long>();
        HashMap<Long, Long> previousMappedUserRole = new HashMap<Long, Long>();
        HashMap<Long, Long> previousMappedCostBucket = new HashMap<Long, Long>();

        for (User user : theUserList) {

            user.setCreatedBy(loggedInUser);
            user.setModifiedBy(loggedInUser);

            if (user.getId() != null) {
                
                User userObj = userRepository.findById(user.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", user.getId()));
                user.setCreatedBy(userObj.getCreatedBy());
                user.setCreatedAt(userObj.getCreatedAt());

                
                previousMappedWarehouse.put(user.getId(), userObj.getWarehouseId());
                previousMappedPrimaryCompany.put(user.getId(), userObj.getPrimaryCompanyId());
                previousMappedUserRole.put(user.getId(), userObj.getUserRoleId());
                previousMappedCostBucket.put(user.getId(), userObj.getCostBucketId());
                
            }

            String error = validateUserRequest(user);
            if (error != null) {
                throw new InvalidValueFoundException("User", error);
            }

            // System.out.println("user Password " + user.getUserPassword());

            if (user.getUserPassword() != null && user.getUserPassword().trim().length() > 0) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
            }

        }

        ArrayList<HashMap<Long, Long>> allHashMap = new ArrayList<HashMap<Long, Long>>();
        allHashMap.add(previousMappedWarehouse);
        allHashMap.add(previousMappedPrimaryCompany);
        allHashMap.add(previousMappedUserRole);
        allHashMap.add(previousMappedCostBucket);

        List<User> theSavedUserList = userRepository.saveAll(theUserList);
        saveUserMappingWithOthers(allHashMap, theSavedUserList);
        saveOrderType(theSavedUserList);

        return theSavedUserList;
    }

    private void saveSupplierForUserCompany(List<User> theSavedUserList) {
        User loggedInUser = WarehouseHelper.getLoggedInUser();
        for (User theUser : theSavedUserList) {
            if (theUser.getSupplierId() == null) {
                Supplier theSupplier = new Supplier();

            }
        }
    }

    private void saveOrderType(List<User> theSavedUserList) {
        User loggedInUser = WarehouseHelper.getLoggedInUser();
        for (User theUser : theSavedUserList) {
            for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
                if (orderTypeEnum == OrderTypeEnum.Select) {
                    continue;
                }
                OrderType orderType = new OrderType();
                orderType.setPrimaryCompanyId(theUser.getPrimaryCompanyId());
                orderType.setName("Planned");
                orderType.setCode(orderTypeEnum.toString() + "_PLN");
                if (orderTypeEnum == OrderTypeEnum.ExtStockTrf) {
                    orderType.setCode("EST_PLN");
                }
                orderType.setAllowDangerLvlUnlocking(false);
                orderType.setExplodeKitBom(true);
                orderType.setOrderType(orderTypeEnum);
                orderType.setTransactionType(TransactionTypeEnum.Select);
                orderType.setCreatedBy(loggedInUser);
                orderType.setModifiedBy(loggedInUser);
                String error = additionalSetupService.validateOrderTypeRequest(orderType);
                if (error == null) {
                    additionalSetupService.saveOrderType(orderType);
                }
                
            }
        }
    }

    private void saveUserMappingWithOthers(ArrayList<HashMap<Long, Long>> allHashMap, List<User> theSavedUserList) {
        User loggedInUser = WarehouseHelper.getLoggedInUser();

        HashMap<Long, Long> previousMappedWarehouse = allHashMap.get(0);
        HashMap<Long, Long> previousMappedPrimaryCompany = allHashMap.get(1);
        HashMap<Long, Long> previousMappedUserRole = allHashMap.get(2);
        HashMap<Long, Long> previousMappedCostBucket = allHashMap.get(3);


        List<UserWarehouseMapping> theUserWarehouseMappingList = new ArrayList<UserWarehouseMapping>();
        List<UserCostBucketMapping> theUserCostBucketMappingList = new ArrayList<UserCostBucketMapping>();
        List<UserPrimaryCompanyMapping> theUserPrimaryCompanyMappingList = new ArrayList<UserPrimaryCompanyMapping>();
        List<UserRoleMapping> theUserRoleMappingList = new ArrayList<UserRoleMapping>();

        for (User theUser : theSavedUserList) {
            Long warehouseId = previousMappedWarehouse.get(theUser.getId());
            Long primaryCompanyId = previousMappedPrimaryCompany.get(theUser.getId());
            Long userRoleId = previousMappedUserRole.get(theUser.getId());
            Long costBucketId = previousMappedCostBucket.get(theUser.getId());

            if (warehouseId != null && theUser.getWarehouseId() != warehouseId) {
                userWarehouseMappingSearchRepository.deleteUserWarehouseMapping(theUser.getId(), warehouseId);
            }
            if (primaryCompanyId != null && theUser.getPrimaryCompanyId() != primaryCompanyId) {
                userPrimaryCompanyMappingSearchRepository.deleteUserPrimaryCompanyMapping(theUser.getId(),
                        primaryCompanyId);
            }
            if (userRoleId != null && theUser.getUserRoleId() != userRoleId) {
                userRoleMappingSearchRepository.deleteUserRoleMapping(theUser.getId(), userRoleId);
            }
            if (costBucketId != null && theUser.getCostBucketId() != costBucketId) {
                userCostBucketMappingSearchRepository.deleteUserCostBucketMapping(theUser.getId(), costBucketId);
            }

            if (theUser.getWarehouseId() != warehouseId) {
                UserWarehouseMapping userWarehouseMapping = new UserWarehouseMapping();
                userWarehouseMapping.setWarehouseId(theUser.getWarehouseId());
                userWarehouseMapping.setUserId(theUser.getId());
                userWarehouseMapping.setCreatedBy(loggedInUser);
                userWarehouseMapping.setModifiedBy(loggedInUser);
                validateUserWarehouseMappingRequest(userWarehouseMapping);
                theUserWarehouseMappingList.add(userWarehouseMapping);
            }
            if (theUser.getPrimaryCompanyId() != primaryCompanyId) {
                UserPrimaryCompanyMapping userPrimaryCompanyMapping = new UserPrimaryCompanyMapping();
                userPrimaryCompanyMapping.setPrimaryCompanyId(theUser.getPrimaryCompanyId());
                userPrimaryCompanyMapping.setUserId(theUser.getId());
                userPrimaryCompanyMapping.setCreatedBy(loggedInUser);
                userPrimaryCompanyMapping.setModifiedBy(loggedInUser);
                validateUserPrimaryCompanyMappingRequest(userPrimaryCompanyMapping);
                theUserPrimaryCompanyMappingList.add(userPrimaryCompanyMapping);
            }
            if (theUser.getCostBucketId() != costBucketId) {
                UserCostBucketMapping userCostBucketMapping = new UserCostBucketMapping();
                userCostBucketMapping.setCostBucketId(theUser.getCostBucketId());
                userCostBucketMapping.setUserId(theUser.getId());
                userCostBucketMapping.setCreatedBy(loggedInUser);
                userCostBucketMapping.setModifiedBy(loggedInUser);
                validateUserCostBucketMappingRequest(userCostBucketMapping);
                theUserCostBucketMappingList.add(userCostBucketMapping);
            }
            if (theUser.getUserRoleId() != userRoleId) {
                UserRoleMapping userRoleMapping = new UserRoleMapping();
                userRoleMapping.setUserRoleId(theUser.getUserRoleId());
                userRoleMapping.setUserId(theUser.getId());
                userRoleMapping.setCreatedBy(loggedInUser);
                userRoleMapping.setModifiedBy(loggedInUser);
                validateUserRoleMappingRequest(userRoleMapping);
                theUserRoleMappingList.add(userRoleMapping);
            }
        }
        saveUserWarehouseMapping(theUserWarehouseMappingList);
        saveUserPrimaryCompanyMapping(theUserPrimaryCompanyMappingList);
        saveUserCostBucketMapping(theUserCostBucketMappingList);
        saveUserRoleMapping(theUserRoleMappingList);
    }

    @Override
    public Page<User> findUserList(PageHelper page, UserRequest request) {
        return userSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public List<User> findUserList(UserRequest request) {
        return userSearchRepository.findAllWithFilters(request);
    }

    // User Role Mapping
    @Override
    public String validateUserRoleMappingRequest(UserRoleMapping theUserRoleMapping) {

        User user = userRepository.findById(theUserRoleMapping.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", theUserRoleMapping.getUserId()));
        theUserRoleMapping.setUser(user);

        UserRole userRole = userRoleRepository.findById(theUserRoleMapping.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("UserRole", "id", theUserRoleMapping.getUserRoleId()));
        theUserRoleMapping.setUserRole(userRole);

        if (theUserRoleMapping.getId() == null) {
            if (userRoleMappingRepository
                    .findByUserIdAndUserRoleId(theUserRoleMapping.getUserId(), theUserRoleMapping.getUserRoleId())
                    .isPresent()) {
                return "Duplicate value found for Mapping with User Name:" + theUserRoleMapping.getUser().getUsername()
                        + " and User Role:" + theUserRoleMapping.getUserRole().getName();
            }
        } else {
            if (userRoleMappingRepository.findByUserIdAndUserRoleIdAndIdNot(theUserRoleMapping.getUserId(),
                    theUserRoleMapping.getUserRoleId(), theUserRoleMapping.getId()).isPresent()) {
                return "Duplicate value found for Mapping with User Name:" + theUserRoleMapping.getUser().getUsername()
                        + " and User Role:" + theUserRoleMapping.getUserRole().getName();
            }
        }
        return null;
    }

    @Override
    public List<UserRoleMapping> saveUserRoleMapping(List<UserRoleMapping> theUserRoleMapping) {
        return userRoleMappingRepository.saveAll(theUserRoleMapping);
    }

    @Override
    public void deleteAllUserRoleMapping(List<UserRoleMapping> theUserRoleMapping) {
        userRoleMappingRepository.deleteAll(theUserRoleMapping);
    }

    @Override
    public List<UserRoleMappingListResponse> findUserListWithRoleMapping(UserRequest request) {
        return userRoleMappingSearchRepository.findAllUserListWithUserMappingList(request);
    }

    // User Warehouse Mapping
    @Override
    public Page<User> findUserListForWarehouseMapping(PageHelper page, UserRequest request) {
        return userWarehouseMappingSearchRepository.findAllWithFilters(page, request);
    }

    @Override
    public String validateUserWarehouseMappingRequest(UserWarehouseMapping theUserWarehouseMapping) {

        User user = userRepository.findById(theUserWarehouseMapping.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", theUserWarehouseMapping.getUserId()));
        theUserWarehouseMapping.setUser(user);

        Warehouse warehouse = warehouseRepository.findById(theUserWarehouseMapping.getWarehouseId()).orElseThrow(
                () -> new ResourceNotFoundException("Warehouse", "id", theUserWarehouseMapping.getWarehouseId()));
        theUserWarehouseMapping.setWarehouse(warehouse);

        if (theUserWarehouseMapping.getId() == null) {
            if (userWarehouseMappingRepository.findByUserIdAndWarehouseId(theUserWarehouseMapping.getUserId(),
                    theUserWarehouseMapping.getWarehouseId()).isPresent()) {
                return "Duplicate value found for Mapping with User Name:"
                        + theUserWarehouseMapping.getUser().getUsername() + " and Warehouse:"
                        + theUserWarehouseMapping.getWarehouse().getName();
            }
        } else {
            if (userWarehouseMappingRepository.findByUserIdAndWarehouseIdAndIdNot(theUserWarehouseMapping.getUserId(),
                    theUserWarehouseMapping.getWarehouseId(), theUserWarehouseMapping.getId()).isPresent()) {
                return "Duplicate value found for Mapping with User Name:"
                        + theUserWarehouseMapping.getUser().getUsername() + " and Warehouse:"
                        + theUserWarehouseMapping.getWarehouse().getName();
            }
        }
        return null;
    }

    @Override
    public List<UserWarehouseMapping> saveUserWarehouseMapping(List<UserWarehouseMapping> theUserWarehouseMapping) {
        return userWarehouseMappingRepository.saveAll(theUserWarehouseMapping);
    }

    @Override
    public void deleteAllUserWarehouseMapping(List<UserWarehouseMapping> theUserWarehouseMapping) {
        userWarehouseMappingRepository.deleteAll(theUserWarehouseMapping);
    }

    @Override
    public List<UserWarehouseMappingListResponse> findUserListWithWarehouseMapping(UserRequest request) {
        return userWarehouseMappingSearchRepository.findAllUserListWithWarehouseMappingList(request);
    }

    // User Primary Company Mapping
    @Override
    public String validateUserPrimaryCompanyMappingRequest(UserPrimaryCompanyMapping theUserPrimaryCompanyMapping) {

        User user = userRepository.findById(theUserPrimaryCompanyMapping.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", theUserPrimaryCompanyMapping.getUserId()));
        theUserPrimaryCompanyMapping.setUser(user);

        PrimaryCompany primaryCompany = primaryCompanyRepository
                .findById(theUserPrimaryCompanyMapping.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theUserPrimaryCompanyMapping.getPrimaryCompanyId()));
        theUserPrimaryCompanyMapping.setPrimaryCompany(primaryCompany);

        if (theUserPrimaryCompanyMapping.getId() == null) {
            if (userPrimaryCompanyMappingRepository
                    .findByUserIdAndPrimaryCompanyId(theUserPrimaryCompanyMapping.getUserId(),
                            theUserPrimaryCompanyMapping.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found for Mapping with User Name:"
                        + theUserPrimaryCompanyMapping.getUser().getUsername() + " and Primary Company:"
                        + theUserPrimaryCompanyMapping.getPrimaryCompany().getName();
            }
        } else {
            if (userPrimaryCompanyMappingRepository
                    .findByUserIdAndPrimaryCompanyIdAndIdNot(theUserPrimaryCompanyMapping.getUserId(),
                            theUserPrimaryCompanyMapping.getPrimaryCompanyId(), theUserPrimaryCompanyMapping.getId())
                    .isPresent()) {
                return "Duplicate value found for Mapping with User Name:"
                        + theUserPrimaryCompanyMapping.getUser().getUsername() + " and Primary Company:"
                        + theUserPrimaryCompanyMapping.getPrimaryCompany().getName();
            }
        }
        return null;
    }

    @Override
    public List<UserPrimaryCompanyMapping> saveUserPrimaryCompanyMapping(
            List<UserPrimaryCompanyMapping> theUserPrimaryCompanyMapping) {
        return userPrimaryCompanyMappingRepository.saveAll(theUserPrimaryCompanyMapping);
    }

    @Override
    public void deleteAllUserPrimaryCompanyMapping(List<UserPrimaryCompanyMapping> theUserPrimaryCompanyMapping) {
        userPrimaryCompanyMappingRepository.deleteAll(theUserPrimaryCompanyMapping);
    }

    @Override
    public List<UserPrimaryCompanyMappingListResponse> findUserListWithPrimaryCompanyMapping(UserRequest request) {
        return userPrimaryCompanyMappingSearchRepository.findAllUserListWithPrimaryCompanyMappingList(request);
    }

    // User Cost Bucket Mapping
    @Override
    public String validateUserCostBucketMappingRequest(UserCostBucketMapping theUserCostBucketMapping) {

        User user = userRepository.findById(theUserCostBucketMapping.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", theUserCostBucketMapping.getUserId()));
        theUserCostBucketMapping.setUser(user);

        CostBucket costBucket = costBucketRepository.findById(theUserCostBucketMapping.getCostBucketId()).orElseThrow(
                () -> new ResourceNotFoundException("CostBucket", "id", theUserCostBucketMapping.getCostBucketId()));
        theUserCostBucketMapping.setCostBucket(costBucket);

        if (theUserCostBucketMapping.getId() == null) {
            if (userCostBucketMappingRepository.findByUserIdAndCostBucketId(theUserCostBucketMapping.getUserId(),
                    theUserCostBucketMapping.getCostBucketId()).isPresent()) {
                return "Duplicate value found for Mapping with User Name:"
                        + theUserCostBucketMapping.getUser().getUsername() + " and Cost Bucket:"
                        + theUserCostBucketMapping.getCostBucket().getName();
            }
        } else {
            if (userCostBucketMappingRepository
                    .findByUserIdAndCostBucketIdAndIdNot(theUserCostBucketMapping.getUserId(),
                            theUserCostBucketMapping.getCostBucketId(), theUserCostBucketMapping.getId())
                    .isPresent()) {
                return "Duplicate value found for Mapping with User Name:"
                        + theUserCostBucketMapping.getUser().getUsername() + " and Cost Bucket:"
                        + theUserCostBucketMapping.getCostBucket().getName();
            }
        }
        return null;
    }

    @Override
    public List<UserCostBucketMapping> saveUserCostBucketMapping(List<UserCostBucketMapping> theUserCostBucketMapping) {
        return userCostBucketMappingRepository.saveAll(theUserCostBucketMapping);
    }

    @Override
    public void deleteAllUserCostBucketMapping(List<UserCostBucketMapping> theUserCostBucketMapping) {
        userCostBucketMappingRepository.deleteAll(theUserCostBucketMapping);
    }

    // Default Config Parameters
    @Override
    public List<UserCostBucketMappingListResponse> findUserListWithCostBucketMapping(UserRequest request) {
        return userCostBucketMappingSearchRepository.findAllUserListWithCostBucketMappingList(request);
    }

    @Override
    public List<DefaultConfigParameters> findByIsPrimaryCompanyConfigTrue() {
        return defaultConfigParameterRepository.findByIsPrimaryCompanyConfigTrue();
    }

    @Override
    public List<DefaultConfigParameters> findByIsWarehouseConfigTrue() {
        return defaultConfigParameterRepository.findByIsWarehouseConfigTrue();
    }

    @Override
    public List<DefaultConfigParameters> findByIsPrimaryCompanyConfigTrueAndIsWarehouseConfigTrue() {
        return defaultConfigParameterRepository.findByIsPrimaryCompanyConfigTrueAndIsWarehouseConfigTrue();
    }

    // Primary Company Configure Parameters

    @Override
    public String validatePrimaryCompanyConfigParametersRequest(PrimaryCompanyConfig thePrimaryCompanyConfig) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(thePrimaryCompanyConfig.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        thePrimaryCompanyConfig.getPrimaryCompanyId()));
        thePrimaryCompanyConfig.setPrimaryCompany(primaryCompany);

        if (thePrimaryCompanyConfig.getId() == null) {
            if (primaryCompanyConfigRepository
                    .findByConfigKeyAndPrimaryCompanyId(thePrimaryCompanyConfig.getConfigKey(),
                            thePrimaryCompanyConfig.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + thePrimaryCompanyConfig.getConfigKey()
                        + " and Primary Company:" + thePrimaryCompanyConfig.getPrimaryCompany().getName();
            }
        } else {
            if (primaryCompanyConfigRepository
                    .findByConfigKeyAndPrimaryCompanyIdAndIdNot(thePrimaryCompanyConfig.getConfigKey(),
                            thePrimaryCompanyConfig.getPrimaryCompanyId(), thePrimaryCompanyConfig.getId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + thePrimaryCompanyConfig.getConfigKey()
                        + " and Primary Company:" + thePrimaryCompanyConfig.getPrimaryCompany().getName();
            }
        }

        return null;
    }

    @Override
    public Page<PrimaryCompanyConfigParamtersResponse> findPrimaryCompanyList(PageHelper page,
            ConfigureParameterRequest request) {
        return primaryCompanyParametersSearchRepository.findPrimaryCompanyList(page, request);
    }

    @Override
    public List<PrimaryCompanyConfigParamtersResponse> findPrimaryCompanyListWithConfigParameter(
            ConfigureParameterRequest request) {
        return primaryCompanyParametersSearchRepository.findPrimaryCompanyListWithConfigParameter(request);
    }

    @Override
    public List<PrimaryCompanyConfig> savePrimaryCompanyConfigParameters(
            List<PrimaryCompanyConfig> thePrimaryCompanyConfigList) {
        return primaryCompanyConfigRepository.saveAll(thePrimaryCompanyConfigList);
    }

    @Override
    public void deletePrimaryCompanyConfigParameters(List<PrimaryCompanyConfig> thePrimaryCompanyConfigList) {
        primaryCompanyConfigRepository.deleteAll(thePrimaryCompanyConfigList);
    }


    // Warehouse Configure Parameters

    @Override
    public String validateWarehouseConfigParametersRequest(WarehouseConfig theWarehouseConfig) {
        Warehouse warehouse = warehouseRepository.findById(theWarehouseConfig.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id",
                        theWarehouseConfig.getWarehouseId()));
        theWarehouseConfig.setWarehouse(warehouse);

        if (theWarehouseConfig.getId() == null) {
            if (warehouseConfigRepository
                    .findByConfigKeyAndWarehouseId(theWarehouseConfig.getConfigKey(),
                            theWarehouseConfig.getWarehouseId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + theWarehouseConfig.getConfigKey()
                        + " and Primary Company:" + theWarehouseConfig.getWarehouse().getName();
            }
        } else {
            if (warehouseConfigRepository
                    .findByConfigKeyAndWarehouseIdAndIdNot(theWarehouseConfig.getConfigKey(),
                            theWarehouseConfig.getWarehouseId(), theWarehouseConfig.getId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + theWarehouseConfig.getConfigKey()
                        + " and Primary Company:" + theWarehouseConfig.getWarehouse().getName();
            }
        }

        return null;
    }

    @Override
    public Page<WarehouseConfigParamtersResponse> findWarehouseList(PageHelper page,
            ConfigureParameterRequest request) {
        return warehouseParametersSearchRepository.findWarehouseList(page, request);
    }

    @Override
    public List<WarehouseConfigParamtersResponse> findWarehouseListWithConfigParameter(
            ConfigureParameterRequest request) {
        return warehouseParametersSearchRepository.findWarehouseListWithConfigParameter(request);
    }

    @Override
    public List<WarehouseConfig> saveWarehouseConfigParameters(
            List<WarehouseConfig> theWarehouseConfigList) {
        return warehouseConfigRepository.saveAll(theWarehouseConfigList);
    }

    @Override
    public void deleteWarehouseConfigParameters(List<WarehouseConfig> theWarehouseConfigList) {
        warehouseConfigRepository.deleteAll(theWarehouseConfigList);
    }


    // Warehouse Company Configure Parameters
    @Override
    public String validateWarehousePrimaryCompanyConfigParametersRequest(WarehousePrimaryCompanyConfig theWarehousePrimaryCompanyConfig) {
        WarehouseCompanyMapping warehouseCompanyMapping = warehouseCompanyMappingRepository.findById(theWarehousePrimaryCompanyConfig.getWarehouseCompanyMappingId())
                .orElseThrow(() -> new ResourceNotFoundException("WarehousePrimaryCompany", "id",
                        theWarehousePrimaryCompanyConfig.getWarehouseCompanyMappingId()));
        theWarehousePrimaryCompanyConfig.setWarehouseCompanyMapping(warehouseCompanyMapping);

        if (theWarehousePrimaryCompanyConfig.getId() == null) {
            if (warehousePrimaryCompanyConfigRepository
                    .findByConfigKeyAndWarehouseCompanyMappingId(theWarehousePrimaryCompanyConfig.getConfigKey(),
                            theWarehousePrimaryCompanyConfig.getWarehouseCompanyMappingId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + theWarehousePrimaryCompanyConfig.getConfigKey()
                        + " and Warehouse Primary Company Mapping:" + theWarehousePrimaryCompanyConfig.getWarehouseCompanyMappingId();
            }
        } else {
            if (warehousePrimaryCompanyConfigRepository
                    .findByConfigKeyAndWarehouseCompanyMappingIdAndIdNot(theWarehousePrimaryCompanyConfig.getConfigKey(),
                            theWarehousePrimaryCompanyConfig.getWarehouseCompanyMappingId(), theWarehousePrimaryCompanyConfig.getId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + theWarehousePrimaryCompanyConfig.getConfigKey()
                + " and Warehouse Primary Company Mapping:" + theWarehousePrimaryCompanyConfig.getWarehouseCompanyMappingId();
            }
        }

        return null;
    }

    @Override
    public Page<WarehousePrimaryCompanyConfigParamtersResponse> findWarehousePrimaryCompanyList(PageHelper page,
            ConfigureParameterRequest request) {
        return warehousePrimaryCompanyConfigSearchRepository.findWarehouseCompanyMappingList(page, request);
    }

    @Override
    public List<WarehousePrimaryCompanyConfigParamtersResponse> findWarehouseCompanyMappingListWithConfig(
            ConfigureParameterRequest request) {
        return warehousePrimaryCompanyConfigSearchRepository.findWarehouseCompanyMappingListWithConfig(request);
    }

    @Override
    public List<WarehousePrimaryCompanyConfig> saveWarehousePrimaryCompanyConfigParameters(
            List<WarehousePrimaryCompanyConfig> theWarehousePrimaryCompanyConfigList) {
        return warehousePrimaryCompanyConfigRepository.saveAll(theWarehousePrimaryCompanyConfigList);
    }

    @Override
    public void deleteWarehousePrimaryCompanyConfigParameters(List<WarehousePrimaryCompanyConfig> theWarehousePrimaryCompanyConfigList) {
        warehousePrimaryCompanyConfigRepository.deleteAll(theWarehousePrimaryCompanyConfigList);
    }

    // Organization Config

    @Override
    public String validateOrganizationConfigParametersRequest(OrganizationConfig theOrganizationConfig) {
        Organization warehouse = organizationRepository.findById(theOrganizationConfig.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id",
                        theOrganizationConfig.getOrganizationId()));
        theOrganizationConfig.setOrganization(warehouse);

        if (theOrganizationConfig.getId() == null) {
            if (organizationConfigRepository
                    .findByConfigKeyAndOrganizationId(theOrganizationConfig.getConfigKey(),
                            theOrganizationConfig.getOrganizationId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + theOrganizationConfig.getConfigKey()
                        + " and Organization:" + theOrganizationConfig.getOrganization().getName();
            }
        } else {
            if (organizationConfigRepository
                    .findByConfigKeyAndOrganizationIdAndIdNot(theOrganizationConfig.getConfigKey(),
                            theOrganizationConfig.getOrganizationId(), theOrganizationConfig.getId())
                    .isPresent()) {
                return "Duplicate value found with Config Key:" + theOrganizationConfig.getConfigKey()
                        + " and Organization:" + theOrganizationConfig.getOrganization().getName();
            }
        }

        return null;
    }

    @Override
    public Page<OrganizationConfigParamtersResponse> findOrganizationList(PageHelper page,
    OrganizationRequest request) {
        return organizationParameterSearchRepository.findOrganizationList(page, request);
    }

    @Override
    public List<OrganizationConfigParamtersResponse> findOrganizationListWithConfigParameter(
        OrganizationRequest request) {
        return organizationParameterSearchRepository.findOrganizationListWithConfigParameter(request);
    }

    @Override
    public List<OrganizationConfig> saveOrganizationConfigParameters(
            List<OrganizationConfig> theOrganizationConfigList) {
        return organizationConfigRepository.saveAll(theOrganizationConfigList);
    }

    @Override
    public void deleteOrganizationConfigParameters(List<OrganizationConfig> theOrganizationConfigList) {
        organizationConfigRepository.deleteAll(theOrganizationConfigList);
    }


    // Default Interface value Configure Parameters

    @Override
    public String validateDefaultInterfaceValueRequest(DefaultInterfaceValue theDefaultInterfaceValue) {
        PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theDefaultInterfaceValue.getPrimaryCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                        theDefaultInterfaceValue.getPrimaryCompanyId()));
        theDefaultInterfaceValue.setPrimaryCompany(primaryCompany);

        if (theDefaultInterfaceValue.getId() == null) {
            if (primaryCompanyConfigRepository
                    .findByConfigKeyAndPrimaryCompanyId(theDefaultInterfaceValue.getConfigKey(),
                            theDefaultInterfaceValue.getPrimaryCompanyId())
                    .isPresent()) {
                return "Duplicate value found with Field:" + theDefaultInterfaceValue.getConfigKey()
                        + " and Primary Company:" + theDefaultInterfaceValue.getPrimaryCompany().getName();
            }
        } else {
            if (primaryCompanyConfigRepository
                    .findByConfigKeyAndPrimaryCompanyIdAndIdNot(theDefaultInterfaceValue.getConfigKey(),
                            theDefaultInterfaceValue.getPrimaryCompanyId(), theDefaultInterfaceValue.getId())
                    .isPresent()) {
                return "Duplicate value found with Field:" + theDefaultInterfaceValue.getConfigKey()
                        + " and Primary Company:" + theDefaultInterfaceValue.getPrimaryCompany().getName();
            }
        }

        return null;
    }


    @Override
    public Page<DefaultInterfaceValueResponse> findPrimaryCompanyList(PageHelper page,
    DefaultInterfaceValueSearchRequest request) {
        return defaultInterfaceValueSearchRepository.findPrimaryCompanyList(page, request);
    }

    @Override
    public List<DefaultInterfaceValueResponse> findPrimaryCompanyListWithDefaultInterfaceValue(
        DefaultInterfaceValueSearchRequest request) {
        return defaultInterfaceValueSearchRepository.findPrimaryCompanyListWithDefaultInterfaceValue(request);
    }

    @Override
    public List<DefaultInterfaceValue> saveDefaultInterfaceValue(
            List<DefaultInterfaceValue> theDefaultInterfaceValueList) {
        return defaultInterfaceValueRepository.saveAll(theDefaultInterfaceValueList);
    }

    @Override
    public void deleteDefaultInterfaceValue(List<DefaultInterfaceValue> theDefaultInterfaceValueList) {
        defaultInterfaceValueRepository.deleteAll(theDefaultInterfaceValueList);
    }

}
