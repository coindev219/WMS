package com.warehouse.administration.user.costbucketmapping;

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
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.customer.Customer;
import com.warehouse.setup.supplier.Supplier;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.springframework.stereotype.Repository;

@Repository
public class UserCostBucketMappingSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserCostBucketMappingSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public void deleteUserCostBucketMapping(Long userId, Long costBucketId) {
        
        // create delete
        CriteriaDelete<UserCostBucketMapping> delete = criteriaBuilder.createCriteriaDelete(UserCostBucketMapping.class);
 
        // set the root class
        Root<UserCostBucketMapping> rootObj = delete.from(UserCostBucketMapping.class);
 
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(criteriaBuilder.equal(rootObj.get("userId"), userId));
        predicates.add(criteriaBuilder.equal(rootObj.get("costBucketId"), costBucketId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        // set where clause
        delete.where(predicate);
 
        // perform update
        entityManager.createQuery(delete).executeUpdate();
    }

    public List<UserCostBucketMappingListResponse> findAllUserListWithCostBucketMappingList(UserRequest request) {
        CriteriaQuery<UserCostBucketMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(UserCostBucketMappingListResponse.class);
        Root<User> rootObj = criteriaQuery.from(User.class);

        Join<User, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("costBucket");
        Join<User, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<User, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        Join<User, Country> toCountryJoin = rootObj.join("country");
        Join<User, UserRole> toUserRoleJoin = rootObj.join("costBucket");

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

        TypedQuery<UserCostBucketMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<UserCostBucketMappingListResponse> costBucketMappingListResponseList = typedQuery.getResultList();

        for (UserCostBucketMappingListResponse costBucketMappingListResponse : costBucketMappingListResponseList) {
            costBucketMappingListResponse
                    .setUserRoleMappingList(findCostBucketMappingList(costBucketMappingListResponse.getUserId()));
        }

        return costBucketMappingListResponseList;
    }

    public List<UserCostBucketMapping> findCostBucketMappingList(Long userId) {
        CriteriaQuery<UserCostBucketMapping> criteriaQuery = criteriaBuilder.createQuery(UserCostBucketMapping.class);
        Root<UserCostBucketMapping> rootObj = criteriaQuery.from(UserCostBucketMapping.class);

        Join<UserCostBucketMapping, User> toUserJoin = rootObj.join("user");
        Join<UserCostBucketMapping, CostBucket> toCostBucketJoin = rootObj.join("costBucket");
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toCostBucketJoin.get("id"), toCostBucketJoin.get("name"),
        toCostBucketJoin.get("code"), rootObj.get("createdAt"), rootObj.get("modifiedAt"));

        predicates.add(criteriaBuilder.equal(toUserJoin.get("id"), userId));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<UserCostBucketMapping> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private List<Predicate> getPredicate(UserRequest request, Root<User> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

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

}
