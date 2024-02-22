package com.warehouse.administration.user.warehousemapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.additionalsetup.costbucket.CostBucket;
import com.warehouse.administration.role.UserRole;
import com.warehouse.administration.user.User;
import com.warehouse.administration.user.UserRequest;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.enums.UserTypeEnum;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserWarehouseMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserWarehouseMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Transactional
    public void deleteUserWarehouseMapping(Long userId, Long warehouseId) {
        
        // create delete
        CriteriaDelete<UserWarehouseMapping> delete = criteriaBuilder.
         createCriteriaDelete(UserWarehouseMapping.class);
 
        // set the root class
        Root<UserWarehouseMapping> rootObj = delete.from(UserWarehouseMapping.class);
 
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(criteriaBuilder.equal(rootObj.get("userId"), userId));
        predicates.add(criteriaBuilder.equal(rootObj.get("warehouseId"), warehouseId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        // set where clause
        delete.where(predicate);
 
        // perform update
        entityManager.createQuery(delete).executeUpdate();
    }


    public Page<User> findAllWithFilters(PageHelper page, UserRequest request) {
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootObj = criteriaQuery.from(User.class);

        Join<User, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<User, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<User, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<User, Country> toCountryJoin = rootObj.join("country");
        Join<User, UserRole> toUserRoleJoin = rootObj.join("userRole");

        Join<User, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<User, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        Join<User, Supplier> toSupplierJoin = rootObj.join("supplier", JoinType.LEFT);
        Join<User, Customer> toCustomerJoin = rootObj.join("customer", JoinType.LEFT);

        Join<User, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<User, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toWarehoueJoin.get("id"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toCostBucketJoin.get("id"), toCostBucketJoin.get("name"),
                toCostBucketJoin.get("code"), rootObj.get("employeeNumber"), rootObj.get("employeeName"),
                rootObj.get("nonTransactionalUser"), rootObj.get("username"), rootObj.get("userType"),
                toCustomerJoin.get("id"), toCustomerJoin.get("name"), toCustomerJoin.get("code"),
                toSupplierJoin.get("id"), toSupplierJoin.get("name"), toSupplierJoin.get("code"),
                toUserRoleJoin.get("id"), toUserRoleJoin.get("name"), toUserRoleJoin.get("code"),

                rootObj.get("addressLine1"), rootObj.get("addressLine2"), rootObj.get("contactName"),
                toCountryJoin.get("id"), toCountryJoin.get("countryName"), toStateJoin.get("id"),
                toStateJoin.get("stateName"), toCityJoin.get("id"), toCityJoin.get("cityName"),

                rootObj.get("otherState"), rootObj.get("otherCity"), rootObj.get("postBox"), rootObj.get("postalCode"),
                rootObj.get("intlDialCode"), rootObj.get("areaDialingCode"), rootObj.get("phone"),
                rootObj.get("extensionNo"), rootObj.get("fax"), rootObj.get("mobile"), rootObj.get("email"),
                rootObj.get("createdAt"), rootObj.get("modifiedAt"), toCreatedByUserJoin.get("username"),
                toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(rootObj.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, rootObj);

            User user = WarehouseHelper.getLoggedInUser();
            Predicate subUsersPredicate = criteriaBuilder.equal(toCreatedByUserJoin.get("id"), user.getId());
            Predicate selfUserPredicte = criteriaBuilder.equal(rootObj.get("id"), user.getId());
            Predicate userCondition = criteriaBuilder.or(subUsersPredicate, selfUserPredicte);
            predicates.add(userCondition);

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }

            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));

            }
            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }
            if (Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
            }

            if (Objects.nonNull(request.getCostBucketId())) {
                predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
            }

            if (Objects.nonNull(request.getCustomerId())) {
                predicates.add(criteriaBuilder.equal(toCustomerJoin.get("id"), request.getCustomerId()));
            }

            if (Objects.nonNull(request.getSupplierId())) {
                predicates.add(criteriaBuilder.equal(toSupplierJoin.get("id"), request.getSupplierId()));
            }

            if (Objects.nonNull(request.getUserRoleId())) {
                predicates.add(criteriaBuilder.equal(toUserRoleJoin.get("id"), request.getUserRoleId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<UserWarehouseMappingListResponse> findAllUserListWithWarehouseMappingList(UserRequest request) {
        CriteriaQuery<UserWarehouseMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(UserWarehouseMappingListResponse.class);
        Root<User> rootObj = criteriaQuery.from(User.class);

        Join<User, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<User, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<User, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<User, Country> toCountryJoin = rootObj.join("country");
        Join<User, UserRole> toUserRoleJoin = rootObj.join("userRole");

        Join<User, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<User, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        Join<User, Supplier> toSupplierJoin = rootObj.join("supplier", JoinType.LEFT);
        Join<User, Customer> toCustomerJoin = rootObj.join("customer", JoinType.LEFT);

        Join<User, User> toCreatedByUserJoin = rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("employeeNumber"), rootObj.get("employeeName"),
                rootObj.get("username"), rootObj.get("userType"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(rootObj.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, rootObj);

            User user = WarehouseHelper.getLoggedInUser();
            Predicate subUsersPredicate = criteriaBuilder.equal(toCreatedByUserJoin.get("id"), user.getId());
            Predicate selfUserPredicte = criteriaBuilder.equal(rootObj.get("id"), user.getId());
            Predicate userCondition = criteriaBuilder.or(subUsersPredicate, selfUserPredicte);
            predicates.add(userCondition);
            

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }

            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));

            }
            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }
            if (Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
            }

            if (Objects.nonNull(request.getCostBucketId())) {
                predicates.add(criteriaBuilder.equal(toCostBucketJoin.get("id"), request.getCostBucketId()));
            }

            if (Objects.nonNull(request.getCustomerId())) {
                predicates.add(criteriaBuilder.equal(toCustomerJoin.get("id"), request.getCustomerId()));
            }

            if (Objects.nonNull(request.getSupplierId())) {
                predicates.add(criteriaBuilder.equal(toSupplierJoin.get("id"), request.getSupplierId()));
            }

            if (Objects.nonNull(request.getUserRoleId())) {
                predicates.add(criteriaBuilder.equal(toUserRoleJoin.get("id"), request.getUserRoleId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<UserWarehouseMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<UserWarehouseMappingListResponse> userWarehouseMappingListResponseList = typedQuery.getResultList();

        for (UserWarehouseMappingListResponse userWarehouseMappingListResponse : userWarehouseMappingListResponseList) {
            userWarehouseMappingListResponse.setUserWarehouseMappingList(
                    findUserWarehouseMappingList(userWarehouseMappingListResponse.getUserId()));
        }

        return userWarehouseMappingListResponseList;
    }

    public List<UserWarehouseMapping> findUserWarehouseMappingList(Long userId) {
        CriteriaQuery<UserWarehouseMapping> criteriaQuery = criteriaBuilder.createQuery(UserWarehouseMapping.class);
        Root<UserWarehouseMapping> rootObj = criteriaQuery.from(UserWarehouseMapping.class);

        Join<UserWarehouseMapping, User> toUserJoin = rootObj.join("user");
        Join<UserWarehouseMapping, Warehouse> toWarehoueJoin = rootObj.join("warehouse");

        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toWarehoueJoin.get("id"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), rootObj.get("createdAt"), rootObj.get("modifiedAt"));

        predicates.add(criteriaBuilder.equal(toUserJoin.get("id"), userId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<UserWarehouseMapping> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private List<Predicate> getPredicate(UserRequest request, Root<User> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.notEqual(rootObj.get("userType"), UserTypeEnum.WarehouseUser));
        predicates.add(criteriaBuilder.notEqual(rootObj.get("userType"), UserTypeEnum.HHTUser));

        if (Objects.nonNull(request.getEmployeeNumber())) {
            predicates
                    .add(criteriaBuilder.like(rootObj.get("employeeNumber"), "%" + request.getEmployeeNumber() + "%"));
        }
        if (Objects.nonNull(request.getEmployeeName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("employeeName"), "%" + request.getEmployeeName() + "%"));
        }
        if (Objects.nonNull(request.getEmployeeName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("employeeName"), "%" + request.getEmployeeName() + "%"));
        }
        if (Objects.nonNull(request.getUsername())) {
            predicates.add(criteriaBuilder.like(rootObj.get("username"), "%" + request.getUsername() + "%"));
        }
        if (Objects.nonNull(request.getUserType())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("userType"), request.getUserType()));
        }

        if (Objects.nonNull(request.getAddressLine1())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine1"), "%" + request.getAddressLine1() + "%"));
        }
        if (Objects.nonNull(request.getAddressLine2())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine2"), "%" + request.getAddressLine2() + "%"));
        }
        if (Objects.nonNull(request.getContactName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("contactName"), "%" + request.getContactName() + "%"));
        }

        if (Objects.nonNull(request.getOtherState())) {
            predicates.add(criteriaBuilder.like(rootObj.get("otherState"), "%" + request.getOtherState() + "%"));
        }
        if (Objects.nonNull(request.getPostBox())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postBox"), "%" + request.getPostBox() + "%"));
        }
        if (Objects.nonNull(request.getPostalCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postalCode"), "%" + request.getPostalCode() + "%"));
        }
        if (Objects.nonNull(request.getIntlDialCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("intlDialCode"), "%" + request.getIntlDialCode() + "%"));
        }
        if (Objects.nonNull(request.getAreaDialingCode())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("areaDialingCode"), "%" + request.getAreaDialingCode() + "%"));
        }
        if (Objects.nonNull(request.getPhone())) {
            predicates.add(criteriaBuilder.like(rootObj.get("phone"), "%" + request.getPhone() + "%"));
        }
        if (Objects.nonNull(request.getExtensionNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("extensionNo"), "%" + request.getExtensionNo() + "%"));
        }
        if (Objects.nonNull(request.getFax())) {
            predicates.add(criteriaBuilder.like(rootObj.get("fax"), "%" + request.getFax() + "%"));
        }
        if (Objects.nonNull(request.getMobile())) {
            predicates.add(criteriaBuilder.like(rootObj.get("mobile"), "%" + request.getMobile() + "%"));
        }
        if (Objects.nonNull(request.getEmail())) {
            predicates.add(criteriaBuilder.like(rootObj.get("email"), "%" + request.getEmail() + "%"));
        }

        if (Objects.nonNull(request.getNonTransactionalUser())) {
            if (request.getNonTransactionalUser().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("nonTransactionalUser"), true));
            } else if (request.getNonTransactionalUser().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("nonTransactionalUser"), false));
            }
        }

        if (Objects.nonNull(request.getIsActive())) {
            if (request.getIsActive().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isActive"), true));
            } else if (request.getIsActive().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("isActive"), false));
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        if (Objects.nonNull(request.getCreatedFrom())) {
            try {
                Date createdFrom = dateFormat.parse(request.getCreatedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class),
                        createdFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getCreatedTo())) {
            try {
                Date createdTo = dateFormat.parse(request.getCreatedTo());
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(rootObj.get("createdAt").as(java.sql.Date.class), createdTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedFrom())) {
            try {
                Date modifiedFrom = dateFormat.parse(request.getModifiedFrom());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(request.getModifiedTo())) {
            try {
                Date modifiedTo = dateFormat.parse(request.getModifiedTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(rootObj.get("modifiedAt").as(java.sql.Date.class),
                        modifiedTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<User> criteriaQuery, Root<User> rootObj) {

        if (page.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(rootObj.get(page.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(rootObj.get(page.getSortBy())));
        }
    }

    private Pageable getPageable(PageHelper page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getAllRecordsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");

        countRoot.join("primaryCompany");
        countRoot.join("warehouse");
        countRoot.join("costBucket");
        countRoot.join("country");
        countRoot.join("userRole");

        countRoot.join("state", JoinType.LEFT);
        countRoot.join("city", JoinType.LEFT);
        countRoot.join("supplier", JoinType.LEFT);
        countRoot.join("customer", JoinType.LEFT);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
