package com.warehouse.setup.warehouse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.warehouse.administration.user.User;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingListResponse;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingSearchRepository;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMappingListResponse;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMappingSearchRepository;
import com.warehouse.setup.warehouse.printers.ConfigurePrinterListResponse;
import com.warehouse.setup.warehouse.printers.ConfigurePrinterSearchRepository;
import com.warehouse.setup.warehouse.runningcost.RunningCostListResponse;
import com.warehouse.setup.warehouse.runningcost.RunnngCostSearchRepository;
import com.warehouse.state.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    private ConfigurePrinterSearchRepository configurePrinterSearchRepository;

    @Autowired
    private IntegrationMappingSearchRepository integrationMappingSearchRepository;

    @Autowired
    private RunnngCostSearchRepository runningCostSearchRepository;

    @Autowired
    private WarehouseCompanyMappingSearchRepository warehouseCompanyMappingSearchRepository;

    public WarehouseSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<Warehouse> findWarehouseListByPrimaryCompany(WarehouseRequest warehouseRequest) {
        CriteriaQuery<Warehouse> criteriaQuery = criteriaBuilder.createQuery(Warehouse.class);
        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        Join<WarehouseCompanyMapping, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");

        Join<Warehouse, Country> warehouseCountryJoin = toPrimaryCompanyJoin.join("country");
        Join<Warehouse, State> warehouseStateJoin = toPrimaryCompanyJoin.join("state", JoinType.LEFT);
        Join<Warehouse, City> warehouseCityJoin = toPrimaryCompanyJoin.join("city",JoinType.LEFT);

        Join<Warehouse, User> toCreatedByUserJoin = toPrimaryCompanyJoin.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = toPrimaryCompanyJoin.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toWarehoueJoin.get("id"), toWarehoueJoin.get("addressLine1"),
                toWarehoueJoin.get("addressLine2"), toWarehoueJoin.get("areaDialingCode"), toWarehoueJoin.get("code"),
                toWarehoueJoin.get("contactName"), toWarehoueJoin.get("createdAt"), toWarehoueJoin.get("cstn"),
                toWarehoueJoin.get("docNoPrefix"), toWarehoueJoin.get("email"), toWarehoueJoin.get("exportLicenseNo"),
                toWarehoueJoin.get("extensionNo"), toWarehoueJoin.get("fax"), toWarehoueJoin.get("gstNo"),
                toWarehoueJoin.get("intlDialCode"), toWarehoueJoin.get("mobile"), toWarehoueJoin.get("modifiedAt"),
                toWarehoueJoin.get("name"), toWarehoueJoin.get("otherCity"), toWarehoueJoin.get("otherState"),
                toWarehoueJoin.get("phone"), toWarehoueJoin.get("postBox"), toWarehoueJoin.get("postalCode"),
                toWarehoueJoin.get("shipTo"), toWarehoueJoin.get("taxInvoiceStatement"), toWarehoueJoin.get("tin"),
                toWarehoueJoin.get("tradeLicenseNo"), warehouseCountryJoin.get("id"),
                warehouseCountryJoin.get("countryName"), warehouseStateJoin.get("id"),
                warehouseStateJoin.get("stateName"), warehouseCityJoin.get("id"), warehouseCityJoin.get("cityName"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"), toWarehoueJoin.get("id"),
                toWarehoueJoin.get("name"), toWarehoueJoin.get("code"));

        if (Objects.nonNull(warehouseRequest.getSearchFor()) && warehouseRequest.getSearchFor().trim().length() > 0) {

            String arrWarehouseIds[] = {};
            if (warehouseRequest.getSearchFor().length() == 1) {
                arrWarehouseIds = new String[] { warehouseRequest.getSearchFor() };
            } else {
                arrWarehouseIds = warehouseRequest.getSearchFor().split(",");
            }

            In<Long> warehouseInClause = criteriaBuilder.in(toWarehoueJoin.get("id"));
            for (String theWarehouseId : arrWarehouseIds) {
                warehouseInClause.value(Long.parseLong(theWarehouseId));
            }

            predicates.add(warehouseInClause);
        } else {
            predicates = getPredicate(warehouseRequest, toWarehoueJoin);

            if (Objects.nonNull(warehouseRequest.getCountryId())) {
                predicates.add(criteriaBuilder.equal(warehouseCountryJoin.get("id"), warehouseRequest.getCountryId()));
            }
            if (Objects.nonNull(warehouseRequest.getStateId())) {
                predicates.add(criteriaBuilder.equal(warehouseStateJoin.get("id"), warehouseRequest.getStateId()));
            }
            if (Objects.nonNull(warehouseRequest.getCityId())) {
                predicates.add(criteriaBuilder.equal(warehouseCityJoin.get("id"), warehouseRequest.getCityId()));
            }

        }

        predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), warehouseRequest.getCompanyId()));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public Page<Warehouse> findAllWithFilters(PageHelper warehousePage, WarehouseRequest warehouseRequest) {
        CriteriaQuery<Warehouse> criteriaQuery = criteriaBuilder.createQuery(Warehouse.class);
        Root<Warehouse> warehouseRoot = criteriaQuery.from(Warehouse.class);
        Join<Warehouse, Country> warehouseCountryJoin = warehouseRoot.join("country");
        Join<Warehouse, State> warehouseStateJoin = warehouseRoot.join("state", JoinType.LEFT);
        Join<Warehouse, City> warehouseCityJoin = warehouseRoot.join("city", JoinType.LEFT);
        Join<Warehouse, User> toCreatedByUserJoin = warehouseRoot.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = warehouseRoot.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(warehouseRoot.get("id"), warehouseRoot.get("addressLine1"),
                warehouseRoot.get("addressLine2"), warehouseRoot.get("areaDialingCode"), warehouseRoot.get("code"),
                warehouseRoot.get("contactName"), warehouseRoot.get("createdAt"), warehouseRoot.get("cstn"),
                warehouseRoot.get("docNoPrefix"), warehouseRoot.get("email"), warehouseRoot.get("exportLicenseNo"),
                warehouseRoot.get("extensionNo"), warehouseRoot.get("fax"), warehouseRoot.get("gstNo"),
                warehouseRoot.get("intlDialCode"), warehouseRoot.get("mobile"), warehouseRoot.get("modifiedAt"),
                warehouseRoot.get("name"), warehouseRoot.get("otherCity"), warehouseRoot.get("otherState"),
                warehouseRoot.get("phone"), warehouseRoot.get("postBox"), warehouseRoot.get("postalCode"),
                warehouseRoot.get("shipTo"), warehouseRoot.get("taxInvoiceStatement"), warehouseRoot.get("tin"),
                warehouseRoot.get("tradeLicenseNo"), warehouseCountryJoin.get("id"),
                warehouseCountryJoin.get("countryName"), warehouseStateJoin.get("id"),
                warehouseStateJoin.get("stateName"), warehouseCityJoin.get("id"), warehouseCityJoin.get("cityName"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"), warehouseRoot.get("id"),
                warehouseRoot.get("name"), warehouseRoot.get("code"));

        if (Objects.nonNull(warehouseRequest.getSearchFor()) && warehouseRequest.getSearchFor().trim().length() > 0) {

            System.out.println("Pring array"
                    + (warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));

            System.out.println(Arrays.asList(
                    warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));
            ArrayList<Long> arrayList = new ArrayList<Long>();
            arrayList.add(6l);

            String arrWarehouseIds[] = {};
            if (warehouseRequest.getSearchFor().length() == 1) {
                arrWarehouseIds = new String[] { warehouseRequest.getSearchFor() };
            } else {
                arrWarehouseIds = warehouseRequest.getSearchFor().split(",");
            }

            In<Long> warehouseInClause = criteriaBuilder.in(warehouseRoot.get("id"));
            for (String theWarehouseId : arrWarehouseIds) {
                warehouseInClause.value(Long.parseLong(theWarehouseId));
            }

            predicates.add(warehouseInClause);
        } else {
            predicates = getPredicate(warehouseRequest, warehouseRoot);

            if (Objects.nonNull(warehouseRequest.getCountryId())) {
                predicates.add(criteriaBuilder.equal(warehouseCountryJoin.get("id"), warehouseRequest.getCountryId()));
            }
            if (Objects.nonNull(warehouseRequest.getStateId())) {
                predicates.add(criteriaBuilder.equal(warehouseStateJoin.get("id"), warehouseRequest.getStateId()));
            }
            if (Objects.nonNull(warehouseRequest.getCityId())) {
                predicates.add(criteriaBuilder.equal(warehouseCityJoin.get("id"), warehouseRequest.getCityId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(warehousePage, criteriaQuery, warehouseRoot);
        TypedQuery<Warehouse> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(warehousePage.getPageNumber() * warehousePage.getPageSize());
        typedQuery.setMaxResults(warehousePage.getPageSize());

        Pageable pageable = getPageable(warehousePage);

        long employeesCount = getEmployeesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    public List<ConfigurePrinterListResponse> findAllWithConfigurePrinterList(WarehouseRequest warehouseRequest) {
        CriteriaQuery<ConfigurePrinterListResponse> criteriaQuery = criteriaBuilder
                .createQuery(ConfigurePrinterListResponse.class);
        Root<Warehouse> warehouseRoot = criteriaQuery.from(Warehouse.class);
        Join<Warehouse, Country> warehouseCountryJoin = warehouseRoot.join("country");
        Join<Warehouse, State> warehouseStateJoin = warehouseRoot.join("state", JoinType.LEFT);
        Join<Warehouse, City> warehouseCityJoin = warehouseRoot.join("city", JoinType.LEFT);
        warehouseRoot.join("createdBy");
        warehouseRoot.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(warehouseRoot.get("id"), warehouseRoot.get("code"), warehouseRoot.get("name"),
                warehouseRoot.get("contactName"));

        if (Objects.nonNull(warehouseRequest.getSearchFor()) && warehouseRequest.getSearchFor().trim().length() > 0) {

            System.out.println("Pring array"
                    + (warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));

            System.out.println(Arrays.asList(
                    warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));
            ArrayList<Long> arrayList = new ArrayList<Long>();
            arrayList.add(6l);

            String arrWarehouseIds[] = {};
            if (warehouseRequest.getSearchFor().length() == 1) {
                arrWarehouseIds = new String[] { warehouseRequest.getSearchFor() };
            } else {
                arrWarehouseIds = warehouseRequest.getSearchFor().split(",");
            }

            In<Long> warehouseInClause = criteriaBuilder.in(warehouseRoot.get("id"));
            for (String theWarehouseId : arrWarehouseIds) {
                warehouseInClause.value(Long.parseLong(theWarehouseId));
            }

            predicates.add(warehouseInClause);
        } else {
            predicates = getPredicate(warehouseRequest, warehouseRoot);

            if (Objects.nonNull(warehouseRequest.getCountryId())) {
                predicates.add(criteriaBuilder.equal(warehouseCountryJoin.get("id"), warehouseRequest.getCountryId()));
            }
            if (Objects.nonNull(warehouseRequest.getStateId())) {
                predicates.add(criteriaBuilder.equal(warehouseStateJoin.get("id"), warehouseRequest.getStateId()));
            }
            if (Objects.nonNull(warehouseRequest.getCityId())) {
                predicates.add(criteriaBuilder.equal(warehouseCityJoin.get("id"), warehouseRequest.getCityId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<ConfigurePrinterListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<ConfigurePrinterListResponse> warehouseList = typedQuery.getResultList();
        for (ConfigurePrinterListResponse warehouse : warehouseList) {
            warehouse.setConfigurePrinterList(
                    configurePrinterSearchRepository.findAllWithFilters(warehouse.getWarehouseId()));
        }

        return warehouseList;
    }

    public List<IntegrationMappingListResponse> findAllWithIntegrationMappingList(WarehouseRequest warehouseRequest) {
        CriteriaQuery<IntegrationMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(IntegrationMappingListResponse.class);
        Root<Warehouse> warehouseRoot = criteriaQuery.from(Warehouse.class);
        Join<Warehouse, Country> warehouseCountryJoin = warehouseRoot.join("country");
        Join<Warehouse, State> warehouseStateJoin = warehouseRoot.join("state", JoinType.LEFT);
        Join<Warehouse, City> warehouseCityJoin = warehouseRoot.join("city", JoinType.LEFT);
        warehouseRoot.join("createdBy");
        warehouseRoot.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(warehouseRoot.get("id"), warehouseRoot.get("code"), warehouseRoot.get("name"),
                warehouseRoot.get("contactName"));

        if (Objects.nonNull(warehouseRequest.getSearchFor()) && warehouseRequest.getSearchFor().trim().length() > 0) {

            System.out.println("Pring array"
                    + (warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));

            System.out.println(Arrays.asList(
                    warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));
            ArrayList<Long> arrayList = new ArrayList<Long>();
            arrayList.add(6l);

            String arrWarehouseIds[] = {};
            if (warehouseRequest.getSearchFor().length() == 1) {
                arrWarehouseIds = new String[] { warehouseRequest.getSearchFor() };
            } else {
                arrWarehouseIds = warehouseRequest.getSearchFor().split(",");
            }

            In<Long> warehouseInClause = criteriaBuilder.in(warehouseRoot.get("id"));
            for (String theWarehouseId : arrWarehouseIds) {
                warehouseInClause.value(Long.parseLong(theWarehouseId));
            }

            predicates.add(warehouseInClause);
        } else {
            predicates = getPredicate(warehouseRequest, warehouseRoot);

            if (Objects.nonNull(warehouseRequest.getCountryId())) {
                predicates.add(criteriaBuilder.equal(warehouseCountryJoin.get("id"), warehouseRequest.getCountryId()));
            }
            if (Objects.nonNull(warehouseRequest.getStateId())) {
                predicates.add(criteriaBuilder.equal(warehouseStateJoin.get("id"), warehouseRequest.getStateId()));
            }
            if (Objects.nonNull(warehouseRequest.getCityId())) {
                predicates.add(criteriaBuilder.equal(warehouseCityJoin.get("id"), warehouseRequest.getCityId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<IntegrationMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<IntegrationMappingListResponse> warehouseList = typedQuery.getResultList();
        for (IntegrationMappingListResponse warehouse : warehouseList) {
            warehouse.setIntegrationMappingList(
                    integrationMappingSearchRepository.findAllWithFilters(warehouse.getWarehouseId()));
        }

        return warehouseList;
    }

    public List<RunningCostListResponse> findAllWithRunningCostList(WarehouseRequest warehouseRequest) {
        CriteriaQuery<RunningCostListResponse> criteriaQuery = criteriaBuilder
                .createQuery(RunningCostListResponse.class);
        Root<Warehouse> warehouseRoot = criteriaQuery.from(Warehouse.class);
        Join<Warehouse, Country> warehouseCountryJoin = warehouseRoot.join("country");
        Join<Warehouse, State> warehouseStateJoin = warehouseRoot.join("state", JoinType.LEFT);
        Join<Warehouse, City> warehouseCityJoin = warehouseRoot.join("city", JoinType.LEFT);
        warehouseRoot.join("createdBy");
        warehouseRoot.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(warehouseRoot.get("id"), warehouseRoot.get("code"), warehouseRoot.get("name"),
                warehouseRoot.get("contactName"));

        if (Objects.nonNull(warehouseRequest.getSearchFor()) && warehouseRequest.getSearchFor().trim().length() > 0) {

            System.out.println("Pring array"
                    + (warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));

            System.out.println(Arrays.asList(
                    warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));
            ArrayList<Long> arrayList = new ArrayList<Long>();
            arrayList.add(6l);

            String arrWarehouseIds[] = {};
            if (warehouseRequest.getSearchFor().length() == 1) {
                arrWarehouseIds = new String[] { warehouseRequest.getSearchFor() };
            } else {
                arrWarehouseIds = warehouseRequest.getSearchFor().split(",");
            }

            In<Long> warehouseInClause = criteriaBuilder.in(warehouseRoot.get("id"));
            for (String theWarehouseId : arrWarehouseIds) {
                warehouseInClause.value(Long.parseLong(theWarehouseId));
            }

            predicates.add(warehouseInClause);
        } else {
            predicates = getPredicate(warehouseRequest, warehouseRoot);

            if (Objects.nonNull(warehouseRequest.getCountryId())) {
                predicates.add(criteriaBuilder.equal(warehouseCountryJoin.get("id"), warehouseRequest.getCountryId()));
            }
            if (Objects.nonNull(warehouseRequest.getStateId())) {
                predicates.add(criteriaBuilder.equal(warehouseStateJoin.get("id"), warehouseRequest.getStateId()));
            }
            if (Objects.nonNull(warehouseRequest.getCityId())) {
                predicates.add(criteriaBuilder.equal(warehouseCityJoin.get("id"), warehouseRequest.getCityId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<RunningCostListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<RunningCostListResponse> warehouseList = typedQuery.getResultList();
        for (RunningCostListResponse warehouse : warehouseList) {
            warehouse.setRunningCostList(runningCostSearchRepository.findAllWithFilters(warehouse.getWarehouseId()));
        }

        return warehouseList;
    }

    public List<WarehouseCompanyMappingListResponse> findAllWithWarehouseCompanyMappingList(
            WarehouseRequest warehouseRequest) {
        CriteriaQuery<WarehouseCompanyMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(WarehouseCompanyMappingListResponse.class);
        Root<Warehouse> warehouseRoot = criteriaQuery.from(Warehouse.class);
        Join<Warehouse, Country> warehouseCountryJoin = warehouseRoot.join("country");
        Join<Warehouse, State> warehouseStateJoin = warehouseRoot.join("state", JoinType.LEFT);
        Join<Warehouse, City> warehouseCityJoin = warehouseRoot.join("city", JoinType.LEFT);
        warehouseRoot.join("createdBy");
        warehouseRoot.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(warehouseRoot.get("id"), warehouseRoot.get("code"), warehouseRoot.get("name"),
                warehouseRoot.get("contactName"));

        if (Objects.nonNull(warehouseRequest.getSearchFor()) && warehouseRequest.getSearchFor().trim().length() > 0) {

            System.out.println("Pring array"
                    + (warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));

            System.out.println(Arrays.asList(
                    warehouseRequest.getSearchFor().length() == 1 ? new String[] { warehouseRequest.getSearchFor() }
                            : warehouseRequest.getSearchFor().split(",")));
            ArrayList<Long> arrayList = new ArrayList<Long>();
            arrayList.add(6l);

            String arrWarehouseIds[] = {};
            if (warehouseRequest.getSearchFor().length() == 1) {
                arrWarehouseIds = new String[] { warehouseRequest.getSearchFor() };
            } else {
                arrWarehouseIds = warehouseRequest.getSearchFor().split(",");
            }

            In<Long> warehouseInClause = criteriaBuilder.in(warehouseRoot.get("id"));
            for (String theWarehouseId : arrWarehouseIds) {
                warehouseInClause.value(Long.parseLong(theWarehouseId));
            }

            predicates.add(warehouseInClause);
        } else {
            predicates = getPredicate(warehouseRequest, warehouseRoot);

            if (Objects.nonNull(warehouseRequest.getCountryId())) {
                predicates.add(criteriaBuilder.equal(warehouseCountryJoin.get("id"), warehouseRequest.getCountryId()));
            }
            if (Objects.nonNull(warehouseRequest.getStateId())) {
                predicates.add(criteriaBuilder.equal(warehouseStateJoin.get("id"), warehouseRequest.getStateId()));
            }
            if (Objects.nonNull(warehouseRequest.getCityId())) {
                predicates.add(criteriaBuilder.equal(warehouseCityJoin.get("id"), warehouseRequest.getCityId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<WarehouseCompanyMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<WarehouseCompanyMappingListResponse> warehouseList = typedQuery.getResultList();
        for (WarehouseCompanyMappingListResponse warehouse : warehouseList) {
            warehouse.setWarehouseCompanyMappingList(
                    warehouseCompanyMappingSearchRepository.findAllWithFilters(warehouse.getWarehouseId()));
        }

        return warehouseList;
    }

    private List<Predicate> getPredicate(WarehouseRequest warehouseRequest,
            Join<WarehouseCompanyMapping, Warehouse> warehouseRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(warehouseRequest.getTradeLicenseNo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("tradeLicenseNo"),
                    "%" + warehouseRequest.getTradeLicenseNo() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getTin())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("tin"), "%" + warehouseRequest.getTin() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getTaxInvoiceStatement())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("taxInvoiceStatement"),
                    "%" + warehouseRequest.getTaxInvoiceStatement() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getShipTo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("shipTo"), "%" + warehouseRequest.getShipTo() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getPostalCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("postalCode"),
                    "%" + warehouseRequest.getPostalCode() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getPostBox())) {
            predicates
                    .add(criteriaBuilder.like(warehouseRoot.get("postBox"), "%" + warehouseRequest.getPostBox() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getPhone())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("phone"), "%" + warehouseRequest.getPhone() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getOtherState())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("otherState"),
                    "%" + warehouseRequest.getOtherState() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getOtherCity())) {
            predicates.add(
                    criteriaBuilder.like(warehouseRoot.get("otherCity"), "%" + warehouseRequest.getOtherCity() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getMobile())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("mobile"), "%" + warehouseRequest.getMobile() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getIntlDialCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("intlDialCode"),
                    "%" + warehouseRequest.getIntlDialCode() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getGstNo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("gstNo"), "%" + warehouseRequest.getGstNo() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getFax())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("fax"), "%" + warehouseRequest.getFax() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getExportLicenseNo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("extensionNo"),
                    "%" + warehouseRequest.getExportLicenseNo() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getEmail())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("email"), "%" + warehouseRequest.getEmail() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getDocNoPrefix())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("docNoPrefix"),
                    "%" + warehouseRequest.getDocNoPrefix() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getCstn())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("cstn"), "%" + warehouseRequest.getCstn() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getContactName())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("contactName"),
                    "%" + warehouseRequest.getContactName() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getAreaDialingCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("areaDialingCode"),
                    "%" + warehouseRequest.getAreaDialingCode() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getAddressLine2())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("addressLine2"),
                    "%" + warehouseRequest.getAddressLine2() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getAddressLine1())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("addressLine1"),
                    "%" + warehouseRequest.getAddressLine1() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getName())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("name"), "%" + warehouseRequest.getName() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("code"), "%" + warehouseRequest.getCode() + "%"));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        if (Objects.nonNull(warehouseRequest.getCreatedFrom())) {
            try {
                Date createdFrom = dateFormat.parse(warehouseRequest.getCreatedFrom());
                predicates.add(criteriaBuilder
                        .greaterThanOrEqualTo(warehouseRoot.get("createdAt").as(java.sql.Date.class), createdFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(warehouseRequest.getCreatedTo())) {
            try {
                Date createdTo = dateFormat.parse(warehouseRequest.getCreatedTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(warehouseRoot.get("createdAt").as(java.sql.Date.class),
                        createdTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(warehouseRequest.getModifiedFrom())) {
            try {
                Date modifiedFrom = dateFormat.parse(warehouseRequest.getModifiedFrom());
                predicates.add(criteriaBuilder
                        .greaterThanOrEqualTo(warehouseRoot.get("modifiedAt").as(java.sql.Date.class), modifiedFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(warehouseRequest.getModifiedTo())) {
            try {
                Date modifiedTo = dateFormat.parse(warehouseRequest.getModifiedTo());
                predicates.add(criteriaBuilder
                        .lessThanOrEqualTo(warehouseRoot.get("modifiedAt").as(java.sql.Date.class), modifiedTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return predicates;
    }

    private List<Predicate> getPredicate(WarehouseRequest warehouseRequest, Root<Warehouse> warehouseRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(warehouseRequest.getTradeLicenseNo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("tradeLicenseNo"),
                    "%" + warehouseRequest.getTradeLicenseNo() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getTin())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("tin"), "%" + warehouseRequest.getTin() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getTaxInvoiceStatement())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("taxInvoiceStatement"),
                    "%" + warehouseRequest.getTaxInvoiceStatement() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getShipTo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("shipTo"), "%" + warehouseRequest.getShipTo() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getPostalCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("postalCode"),
                    "%" + warehouseRequest.getPostalCode() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getPostBox())) {
            predicates
                    .add(criteriaBuilder.like(warehouseRoot.get("postBox"), "%" + warehouseRequest.getPostBox() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getPhone())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("phone"), "%" + warehouseRequest.getPhone() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getOtherState())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("otherState"),
                    "%" + warehouseRequest.getOtherState() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getOtherCity())) {
            predicates.add(
                    criteriaBuilder.like(warehouseRoot.get("otherCity"), "%" + warehouseRequest.getOtherCity() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getMobile())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("mobile"), "%" + warehouseRequest.getMobile() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getIntlDialCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("intlDialCode"),
                    "%" + warehouseRequest.getIntlDialCode() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getGstNo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("gstNo"), "%" + warehouseRequest.getGstNo() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getFax())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("fax"), "%" + warehouseRequest.getFax() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getExportLicenseNo())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("extensionNo"),
                    "%" + warehouseRequest.getExportLicenseNo() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getEmail())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("email"), "%" + warehouseRequest.getEmail() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getDocNoPrefix())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("docNoPrefix"),
                    "%" + warehouseRequest.getDocNoPrefix() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getCstn())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("cstn"), "%" + warehouseRequest.getCstn() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getContactName())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("contactName"),
                    "%" + warehouseRequest.getContactName() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getAreaDialingCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("areaDialingCode"),
                    "%" + warehouseRequest.getAreaDialingCode() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getAddressLine2())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("addressLine2"),
                    "%" + warehouseRequest.getAddressLine2() + "%"));
        }

        if (Objects.nonNull(warehouseRequest.getAddressLine1())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("addressLine1"),
                    "%" + warehouseRequest.getAddressLine1() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getName())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("name"), "%" + warehouseRequest.getName() + "%"));
        }
        if (Objects.nonNull(warehouseRequest.getCode())) {
            predicates.add(criteriaBuilder.like(warehouseRoot.get("code"), "%" + warehouseRequest.getCode() + "%"));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        if (Objects.nonNull(warehouseRequest.getCreatedFrom())) {
            try {
                Date createdFrom = dateFormat.parse(warehouseRequest.getCreatedFrom());
                predicates.add(criteriaBuilder
                        .greaterThanOrEqualTo(warehouseRoot.get("createdAt").as(java.sql.Date.class), createdFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(warehouseRequest.getCreatedTo())) {
            try {
                Date createdTo = dateFormat.parse(warehouseRequest.getCreatedTo());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(warehouseRoot.get("createdAt").as(java.sql.Date.class),
                        createdTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(warehouseRequest.getModifiedFrom())) {
            try {
                Date modifiedFrom = dateFormat.parse(warehouseRequest.getModifiedFrom());
                predicates.add(criteriaBuilder
                        .greaterThanOrEqualTo(warehouseRoot.get("modifiedAt").as(java.sql.Date.class), modifiedFrom));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        if (Objects.nonNull(warehouseRequest.getModifiedTo())) {
            try {
                Date modifiedTo = dateFormat.parse(warehouseRequest.getModifiedTo());
                predicates.add(criteriaBuilder
                        .lessThanOrEqualTo(warehouseRoot.get("modifiedAt").as(java.sql.Date.class), modifiedTo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper warehousePage, CriteriaQuery<Warehouse> criteriaQuery,
            Root<Warehouse> warehouseRoot) {

        if (warehousePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(warehouseRoot.get(warehousePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(warehouseRoot.get(warehousePage.getSortBy())));
        }
    }

    private Pageable getPageable(PageHelper warehousePage) {
        Sort sort = Sort.by(warehousePage.getSortDirection(), warehousePage.getSortBy());
        return PageRequest.of(warehousePage.getPageNumber(), warehousePage.getPageSize(), sort);
    }

    private long getEmployeesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Warehouse> countRoot = countQuery.from(Warehouse.class);
        countRoot.join("country");
        countRoot.join("state", JoinType.LEFT);
        countRoot.join("city", JoinType.LEFT);
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
