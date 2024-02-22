package com.warehouse.setup.company.primarycompany;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.warehouse.currency.Currency;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMapping;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class PrimaryCompanySearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PrimaryCompanySearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<PrimaryCompany> findPrimaryCompanyByWarehouse(PrimaryCompanyRequest request) {
        CriteriaQuery<PrimaryCompany> criteriaQuery = criteriaBuilder.createQuery(PrimaryCompany.class);
        Root<WarehouseCompanyMapping> rootObj = criteriaQuery.from(WarehouseCompanyMapping.class);
        Join<WarehouseCompanyMapping, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<WarehouseCompanyMapping, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");

        Join<PrimaryCompany, Currency> primaryCompanyCurrency = toPrimaryCompanyJoin.join("currency");
        Join<PrimaryCompany, Country> primaryCompanyCountry = toPrimaryCompanyJoin.join("country");
        Join<PrimaryCompany, State> primaryCompanyState = toPrimaryCompanyJoin.join("state", JoinType.LEFT);
        Join<PrimaryCompany, City> primaryCompanyCiy = toPrimaryCompanyJoin.join("city", JoinType.LEFT);
        Join<Warehouse, User> toCreatedByUserJoin = toPrimaryCompanyJoin.join("createdBy");
        Join<Warehouse, User> toModifiedByUserJoin = toPrimaryCompanyJoin.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toPrimaryCompanyJoin.get("docNoPrefix"),
                primaryCompanyCurrency.get("name"), primaryCompanyCurrency.get("id"),
                toPrimaryCompanyJoin.get("addressLine1"), toPrimaryCompanyJoin.get("addressLine2"),
                toPrimaryCompanyJoin.get("contactName"), primaryCompanyCountry.get("id"),
                primaryCompanyCountry.get("countryName"), primaryCompanyState.get("id"),
                primaryCompanyState.get("stateName"), primaryCompanyCiy.get("id"), primaryCompanyCiy.get("cityName"),
                toPrimaryCompanyJoin.get("otherState"), toPrimaryCompanyJoin.get("otherCity"),
                toPrimaryCompanyJoin.get("postBox"), toPrimaryCompanyJoin.get("postalCode"),
                toPrimaryCompanyJoin.get("intlDialCode"), toPrimaryCompanyJoin.get("areaDialingCode"),
                toPrimaryCompanyJoin.get("phone"), toPrimaryCompanyJoin.get("extensionNo"),
                toPrimaryCompanyJoin.get("fax"), toPrimaryCompanyJoin.get("mobile"), toPrimaryCompanyJoin.get("email"),
                toPrimaryCompanyJoin.get("maintainBackOrder"), toPrimaryCompanyJoin.get("generatePoForBackOrder"),
                toPrimaryCompanyJoin.get("maxSoLines"), toPrimaryCompanyJoin.get("maxQtyFactorMonthlyAvgPerSoLine"),
                toPrimaryCompanyJoin.get("minQtyFactorMonthlyAvgPerSoLine"), toPrimaryCompanyJoin.get("maxPoLines"),
                toPrimaryCompanyJoin.get("maxQtyFactorMonthlyAvgPerPoLine"),
                toPrimaryCompanyJoin.get("minQtyFactorMonthlyAvgPerPoLine"), toPrimaryCompanyJoin.get("lcfMaxLimit"),
                toPrimaryCompanyJoin.get("decimalTolerance"), toPrimaryCompanyJoin.get("gstNo"),
                toPrimaryCompanyJoin.get("billTo"), toPrimaryCompanyJoin.get("shipTo"),
                toPrimaryCompanyJoin.get("taxInvoiceLabel"), toPrimaryCompanyJoin.get("taxInvoiceNumber"),
                toPrimaryCompanyJoin.get("weekStartDate"), toPrimaryCompanyJoin.get("panNo"),
                toPrimaryCompanyJoin.get("createdAt"), toPrimaryCompanyJoin.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            String arrSearchIds[] = {};
            if (request.getSearchFor().length() == 1) {
                arrSearchIds = new String[] { request.getSearchFor() };
            } else {
                arrSearchIds = request.getSearchFor().split(",");
            }

            In<Long> inClause = criteriaBuilder.in(toPrimaryCompanyJoin.get("id"));
            for (String theId : arrSearchIds) {
                inClause.value(Long.parseLong(theId));
            }

            predicates.add(inClause);
        } else {
            predicates = getPredicate(request, toPrimaryCompanyJoin);

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyCountry.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyState.get("id"), request.getStateId()));
            }

            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyCiy.get("id"), request.getCityId()));

            }
            if (Objects.nonNull(request.getCurrencyId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyCurrency.get("id"), request.getCurrencyId()));
            }

        }

        predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }


    public Page<PrimaryCompany> findAllWithFilters(PageHelper page, PrimaryCompanyRequest request) {
        CriteriaQuery<PrimaryCompany> criteriaQuery = criteriaBuilder.createQuery(PrimaryCompany.class);
        Root<PrimaryCompany> rootObj = criteriaQuery.from(PrimaryCompany.class);
        Join<PrimaryCompany, Country> primaryCompanyCountry = rootObj.join("country");
        Join<PrimaryCompany, Currency> primaryCompanyCurrency = rootObj.join("currency");
        Join<PrimaryCompany, State> primaryCompanyState = rootObj.join("state", JoinType.LEFT);
        Join<PrimaryCompany, City> primaryCompanyCiy = rootObj.join("city", JoinType.LEFT);
        Join<PrimaryCompany, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<PrimaryCompany, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("name"), rootObj.get("code"),
                rootObj.get("docNoPrefix"), primaryCompanyCurrency.get("name"),
                primaryCompanyCurrency.get("id"), rootObj.get("addressLine1"), rootObj.get("addressLine2"),
                rootObj.get("contactName"), primaryCompanyCountry.get("id"), primaryCompanyCountry.get("countryName"),
                primaryCompanyState.get("id"), primaryCompanyState.get("stateName"), primaryCompanyCiy.get("id"),
                primaryCompanyCiy.get("cityName"), rootObj.get("otherState"), rootObj.get("otherCity"),
                rootObj.get("postBox"), rootObj.get("postalCode"), rootObj.get("intlDialCode"),
                rootObj.get("areaDialingCode"), rootObj.get("phone"), rootObj.get("extensionNo"), rootObj.get("fax"),
                rootObj.get("mobile"), rootObj.get("email"), rootObj.get("maintainBackOrder"),
                rootObj.get("generatePoForBackOrder"), rootObj.get("maxSoLines"),
                rootObj.get("maxQtyFactorMonthlyAvgPerSoLine"), rootObj.get("minQtyFactorMonthlyAvgPerSoLine"),
                rootObj.get("maxPoLines"), rootObj.get("maxQtyFactorMonthlyAvgPerPoLine"),
                rootObj.get("minQtyFactorMonthlyAvgPerPoLine"), rootObj.get("lcfMaxLimit"),
                rootObj.get("decimalTolerance"), rootObj.get("gstNo"), rootObj.get("billTo"), rootObj.get("shipTo"),
                rootObj.get("taxInvoiceLabel"), rootObj.get("taxInvoiceNumber"), rootObj.get("weekStartDate"),
                rootObj.get("panNo"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

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

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyCountry.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyState.get("id"), request.getStateId()));
            }

            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyCiy.get("id"), request.getCityId()));

            }
            if (Objects.nonNull(request.getCurrencyId())) {
                predicates.add(criteriaBuilder.equal(primaryCompanyCurrency.get("id"), request.getCurrencyId()));
            }

        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<PrimaryCompany> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    private List<Predicate> getPredicate(PrimaryCompanyRequest request, Root<PrimaryCompany> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }

        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }
        if (Objects.nonNull(request.getDocNoPrefix())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("docNoPrefix"), request.getDocNoPrefix()));
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

        if (Objects.nonNull(request.getMaxSoLines())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxSoLines"), request.getMaxSoLines()));
        }
        if (Objects.nonNull(request.getMaxQtyFactorMonthlyAvgPerSoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxQtyFactorMonthlyAvgPerSoLine"),
                    request.getMaxQtyFactorMonthlyAvgPerSoLine()));
        }
        if (Objects.nonNull(request.getMinQtyFactorMonthlyAvgPerSoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minQtyFactorMonthlyAvgPerSoLine"),
                    request.getMinQtyFactorMonthlyAvgPerSoLine()));
        }
        if (Objects.nonNull(request.getMaxPoLines())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxPoLines"), request.getMaxPoLines()));
        }
        if (Objects.nonNull(request.getMaxQtyFactorMonthlyAvgPerPoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxQtyFactorMonthlyAvgPerPoLine"),
                    request.getMaxQtyFactorMonthlyAvgPerPoLine()));
        }
        if (Objects.nonNull(request.getMinQtyFactorMonthlyAvgPerPoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minQtyFactorMonthlyAvgPerPoLine"),
                    request.getMinQtyFactorMonthlyAvgPerPoLine()));
        }
        if (Objects.nonNull(request.getLcfMaxLimit())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("lcfMaxLimit"), request.getLcfMaxLimit()));
        }
        if (Objects.nonNull(request.getDecimalTolerance())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("decimalTolerance"), request.getDecimalTolerance()));
        }
        if (Objects.nonNull(request.getGstNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("gstNo"), "%" + request.getGstNo() + "%"));
        }
        if (Objects.nonNull(request.getBillTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("billTo"), "%" + request.getBillTo() + "%"));
        }

        if (Objects.nonNull(request.getShipTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("shipTo"), "%" + request.getShipTo() + "%"));
        }
        if (Objects.nonNull(request.getTaxInvoiceLabel())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("taxInvoiceLabel"), "%" + request.getTaxInvoiceLabel() + "%"));
        }
        if (Objects.nonNull(request.getTaxInvoiceNumber())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("taxInvoiceNumber"), "%" + request.getTaxInvoiceNumber() + "%"));
        }
        if (Objects.nonNull(request.getWeekStartDate())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("weekStartDate"), request.getWeekStartDate()));
        }
        if (Objects.nonNull(request.getPanNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("panNo"), "%" + request.getPanNo() + "%"));
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

        if (Objects.nonNull(request.getMaintainBackOrder())) {
            if (request.getMaintainBackOrder().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("maintainBackOrder"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("maintainBackOrder"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getMaintainBackOrder().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("maintainBackOrder"), true));
            } else if (request.getMaintainBackOrder().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("maintainBackOrder"), false));
            }
        }

        if (Objects.nonNull(request.getGeneratePoForBackOrder())) {
            if (request.getGeneratePoForBackOrder().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getGeneratePoForBackOrder().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), true));
            } else if (request.getGeneratePoForBackOrder().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), false));
            }
        }

        return predicates;
    }

    private List<Predicate> getPredicate(PrimaryCompanyRequest request, Join<WarehouseCompanyMapping,PrimaryCompany> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }

        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }
        if (Objects.nonNull(request.getDocNoPrefix())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("docNoPrefix"), request.getDocNoPrefix()));
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

        if (Objects.nonNull(request.getMaxSoLines())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxSoLines"), request.getMaxSoLines()));
        }
        if (Objects.nonNull(request.getMaxQtyFactorMonthlyAvgPerSoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxQtyFactorMonthlyAvgPerSoLine"),
                    request.getMaxQtyFactorMonthlyAvgPerSoLine()));
        }
        if (Objects.nonNull(request.getMinQtyFactorMonthlyAvgPerSoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minQtyFactorMonthlyAvgPerSoLine"),
                    request.getMinQtyFactorMonthlyAvgPerSoLine()));
        }
        if (Objects.nonNull(request.getMaxPoLines())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxPoLines"), request.getMaxPoLines()));
        }
        if (Objects.nonNull(request.getMaxQtyFactorMonthlyAvgPerPoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("maxQtyFactorMonthlyAvgPerPoLine"),
                    request.getMaxQtyFactorMonthlyAvgPerPoLine()));
        }
        if (Objects.nonNull(request.getMinQtyFactorMonthlyAvgPerPoLine())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("minQtyFactorMonthlyAvgPerPoLine"),
                    request.getMinQtyFactorMonthlyAvgPerPoLine()));
        }
        if (Objects.nonNull(request.getLcfMaxLimit())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("lcfMaxLimit"), request.getLcfMaxLimit()));
        }
        if (Objects.nonNull(request.getDecimalTolerance())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("decimalTolerance"), request.getDecimalTolerance()));
        }
        if (Objects.nonNull(request.getGstNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("gstNo"), "%" + request.getGstNo() + "%"));
        }
        if (Objects.nonNull(request.getBillTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("billTo"), "%" + request.getBillTo() + "%"));
        }

        if (Objects.nonNull(request.getShipTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("shipTo"), "%" + request.getShipTo() + "%"));
        }
        if (Objects.nonNull(request.getTaxInvoiceLabel())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("taxInvoiceLabel"), "%" + request.getTaxInvoiceLabel() + "%"));
        }
        if (Objects.nonNull(request.getTaxInvoiceNumber())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("taxInvoiceNumber"), "%" + request.getTaxInvoiceNumber() + "%"));
        }
        if (Objects.nonNull(request.getWeekStartDate())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("weekStartDate"), request.getWeekStartDate()));
        }
        if (Objects.nonNull(request.getPanNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("panNo"), "%" + request.getPanNo() + "%"));
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

        if (Objects.nonNull(request.getMaintainBackOrder())) {
            if (request.getMaintainBackOrder().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("maintainBackOrder"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("maintainBackOrder"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getMaintainBackOrder().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("maintainBackOrder"), true));
            } else if (request.getMaintainBackOrder().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("maintainBackOrder"), false));
            }
        }

        if (Objects.nonNull(request.getGeneratePoForBackOrder())) {
            if (request.getGeneratePoForBackOrder().equals("both")) {
                Predicate predicateWithTrue = criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), true);
                Predicate predicateWithFalse = criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), false);
                predicates.add(criteriaBuilder.or(predicateWithTrue, predicateWithFalse));
            } else if (request.getGeneratePoForBackOrder().equals("true")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), true));
            } else if (request.getGeneratePoForBackOrder().equals("false")) {
                predicates.add(criteriaBuilder.equal(rootObj.get("generatePoForBackOrder"), false));
            }
        }

        return predicates;
    }

    private void setOrder(PageHelper page, CriteriaQuery<PrimaryCompany> criteriaQuery, Root<PrimaryCompany> rootObj) {

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
        Root<PrimaryCompany> countRoot = countQuery.from(PrimaryCompany.class);
        countRoot.join("country");
        countRoot.join("state", JoinType.LEFT);
        countRoot.join("city", JoinType.LEFT);
        countRoot.join("currency");
        countRoot.join("createdBy");
        countRoot.join("modifiedBy");

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
