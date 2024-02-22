package com.warehouse.setup.supplier;

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

import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.administration.user.User;
import com.warehouse.city.City;
import com.warehouse.country.Country;
import com.warehouse.currency.Currency;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMappingListResponse;
import com.warehouse.setup.sku.suppliermapping.SkuSupplierMappingSearchRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.state.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierSearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    private SkuSupplierMappingSearchRepository skuSupplierMappingSearchRepository;

    public SupplierSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Supplier> findAllWithFilters(PageHelper page, SupplierRequest request) {
        CriteriaQuery<Supplier> criteriaQuery = criteriaBuilder.createQuery(Supplier.class);
        Root<Supplier> rootObj = criteriaQuery.from(Supplier.class);

        Join<Supplier, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<Supplier, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Supplier, Country> toCountryJoin = rootObj.join("country");
        Join<Supplier, OrderType> toOrderTypeJoin = rootObj.join("defaultPoType");
        Join<Supplier, Freighter> toFreighterJoin = rootObj.join("freighter", JoinType.LEFT);
        Join<Supplier, Currency> toCurrencyJoin = rootObj.join("currency");
        Join<Supplier, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<Supplier, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        Join<Supplier, User> toCreatedByUserJoin = rootObj.join("createdBy");
        Join<Supplier, User> toModifiedByUserJoin = rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("name"),
                toPrimaryCompanyJoin.get("code"), toWarehoueJoin.get("id"), toWarehoueJoin.get("name"),
                toWarehoueJoin.get("code"), rootObj.get("name"), rootObj.get("code"), rootObj.get("supplierCategory"),
                rootObj.get("addressLine1"), rootObj.get("addressLine2"), rootObj.get("contactName"),
                toCountryJoin.get("id"), toCountryJoin.get("countryName"), toStateJoin.get("id"),
                toStateJoin.get("stateName"), toCityJoin.get("id"), toCityJoin.get("cityName"),
                rootObj.get("otherState"), rootObj.get("otherCity"), rootObj.get("postBox"), rootObj.get("postalCode"),
                rootObj.get("intlDialCode"), rootObj.get("areaDialingCode"), rootObj.get("phone"),
                rootObj.get("extensionNo"), rootObj.get("fax"), rootObj.get("mobile"), rootObj.get("email"),
                rootObj.get("tradeTerm"), rootObj.get("forecastOrderShipMode"), rootObj.get("backOrderShipMode"),
                toFreighterJoin.get("id"), toFreighterJoin.get("name"), toFreighterJoin.get("code"),
                toOrderTypeJoin.get("id"), toOrderTypeJoin.get("name"), toOrderTypeJoin.get("code"),
                toCurrencyJoin.get("id"), toCurrencyJoin.get("name"),
                rootObj.get("shipTo"), rootObj.get("gstNo"), rootObj.get("createdAt"), rootObj.get("modifiedAt"),
                toCreatedByUserJoin.get("username"), toModifiedByUserJoin.get("username"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            System.out.println("Inside Search by keword");

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

            if (Objects.nonNull(request.getFreighterId())) {
                predicates.add(criteriaBuilder.equal(toFreighterJoin.get("id"), request.getFreighterId()));
            }

            if (Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCurrencyId())) {
                predicates.add(criteriaBuilder.equal(toCurrencyJoin.get("id"), request.getCurrencyId()));
            }

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }
            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));
            }
            if (Objects.nonNull(request.getDefaultPoTypeId())) {
                predicates.add(criteriaBuilder.equal(toOrderTypeJoin.get("id"), request.getDefaultPoTypeId()));
            }
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        setOrder(page, criteriaQuery, rootObj);
        TypedQuery<Supplier> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long recordsCount = getAllRecordsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, recordsCount);
    }

    public List<SkuSupplierMappingListResponse> findAllWithFilters(SupplierRequest request) {
        CriteriaQuery<SkuSupplierMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(SkuSupplierMappingListResponse.class);
        Root<Supplier> rootObj = criteriaQuery.from(Supplier.class);

        Join<Supplier, Warehouse> toWarehoueJoin = rootObj.join("warehouse");
        Join<Supplier, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        Join<Supplier, OrderType> toOrderTypeJoin = rootObj.join("defaultPoType");
        Join<Supplier, Country> toCountryJoin = rootObj.join("country");
        Join<Supplier, Freighter> toFreighterJoin = rootObj.join("freighter", JoinType.LEFT);
        Join<Supplier, Currency> toCurrencyJoin = rootObj.join("currency");
        Join<Supplier, State> toStateJoin = rootObj.join("state", JoinType.LEFT);
        Join<Supplier, City> toCityJoin = rootObj.join("city", JoinType.LEFT);
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("code"), rootObj.get("name"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("code"), toPrimaryCompanyJoin.get("name"));

        if (Objects.nonNull(request.getSearchFor()) && request.getSearchFor().trim().length() > 0) {

            System.out.println("Inside Search by keword");

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

            if (Objects.nonNull(request.getFreighterId())) {
                predicates.add(criteriaBuilder.equal(toFreighterJoin.get("id"), request.getFreighterId()));
            }

            if (Objects.nonNull(request.getWarehouseId())) {
                predicates.add(criteriaBuilder.equal(toWarehoueJoin.get("id"), request.getWarehouseId()));
            }

            if (Objects.nonNull(request.getPrimaryCompanyId())) {
                predicates.add(criteriaBuilder.equal(toPrimaryCompanyJoin.get("id"), request.getPrimaryCompanyId()));
            }

            if (Objects.nonNull(request.getCurrencyId())) {
                predicates.add(criteriaBuilder.equal(toCurrencyJoin.get("id"), request.getCurrencyId()));
            }

            if (Objects.nonNull(request.getCountryId())) {
                predicates.add(criteriaBuilder.equal(toCountryJoin.get("id"), request.getCountryId()));
            }

            if (Objects.nonNull(request.getStateId())) {
                predicates.add(criteriaBuilder.equal(toStateJoin.get("id"), request.getStateId()));
            }
            if (Objects.nonNull(request.getCityId())) {
                predicates.add(criteriaBuilder.equal(toCityJoin.get("id"), request.getCityId()));
            }
            if (Objects.nonNull(request.getDefaultPoTypeId())) {
                predicates.add(criteriaBuilder.equal(toOrderTypeJoin.get("id"), request.getDefaultPoTypeId()));
            }
        }

        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<SkuSupplierMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        List<SkuSupplierMappingListResponse> skuSupplierMappingList = typedQuery.getResultList();
        for (SkuSupplierMappingListResponse skuSupplierMappingListResponse : skuSupplierMappingList) {
            skuSupplierMappingListResponse.setSkuSupplierMappingList(skuSupplierMappingSearchRepository
                    .findAllWithFilters(skuSupplierMappingListResponse.getSupplierId(),
                            skuSupplierMappingListResponse.getPrimaryCompanyId()));
        }

        return skuSupplierMappingList;
    }

    public List<SkuSupplierMappingListResponse> findAll(Long mapToSupplierId,Boolean isMapToSuppliers) {
        CriteriaQuery<SkuSupplierMappingListResponse> criteriaQuery = criteriaBuilder
                .createQuery(SkuSupplierMappingListResponse.class);
        Root<Supplier> rootObj = criteriaQuery.from(Supplier.class);

        rootObj.join("warehouse");
        Join<Supplier, PrimaryCompany> toPrimaryCompanyJoin = rootObj.join("primaryCompany");
        rootObj.join("country");
        rootObj.join("defaultPoType");
        rootObj.join("freighter", JoinType.LEFT);
        rootObj.join("currency");
        rootObj.join("state", JoinType.LEFT);
        rootObj.join("city", JoinType.LEFT);
        rootObj.join("createdBy");
        rootObj.join("modifiedBy");

        List<Predicate> predicates = new ArrayList<Predicate>();

        criteriaQuery.multiselect(rootObj.get("id"), rootObj.get("code"), rootObj.get("name"),
                toPrimaryCompanyJoin.get("id"), toPrimaryCompanyJoin.get("code"), toPrimaryCompanyJoin.get("name"));

        
        if (!isMapToSuppliers){
            predicates.add(criteriaBuilder.equal(rootObj.get("id"), mapToSupplierId));
        }
        
        Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicate);

        TypedQuery<SkuSupplierMappingListResponse> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }


    private List<Predicate> getPredicate(SupplierRequest request, Root<Supplier> rootObj) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("id"), request.getId()));
        }

        if (Objects.nonNull(request.getShipTo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("shipTo"), "%" + request.getShipTo() + "%"));
        }
        if (Objects.nonNull(request.getPostalCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postalCode"), "%" + request.getPostalCode() + "%"));
        }

        if (Objects.nonNull(request.getPostBox())) {
            predicates.add(criteriaBuilder.like(rootObj.get("postBox"), "%" + request.getPostBox() + "%"));
        }

        if (Objects.nonNull(request.getPhone())) {
            predicates.add(criteriaBuilder.like(rootObj.get("phone"), "%" + request.getPhone() + "%"));
        }
        if (Objects.nonNull(request.getOtherState())) {
            predicates.add(criteriaBuilder.like(rootObj.get("otherState"), "%" + request.getOtherState() + "%"));
        }

        if (Objects.nonNull(request.getOtherCity())) {
            predicates.add(criteriaBuilder.like(rootObj.get("otherCity"), "%" + request.getOtherCity() + "%"));
        }

        if (Objects.nonNull(request.getMobile())) {
            predicates.add(criteriaBuilder.like(rootObj.get("mobile"), "%" + request.getMobile() + "%"));
        }
        if (Objects.nonNull(request.getIntlDialCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("intlDialCode"), "%" + request.getIntlDialCode() + "%"));
        }
        if (Objects.nonNull(request.getGstNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("gstNo"), "%" + request.getGstNo() + "%"));
        }
        if (Objects.nonNull(request.getFax())) {
            predicates.add(criteriaBuilder.like(rootObj.get("fax"), "%" + request.getFax() + "%"));
        }

        if (Objects.nonNull(request.getEmail())) {
            predicates.add(criteriaBuilder.like(rootObj.get("email"), "%" + request.getEmail() + "%"));
        }
        if (Objects.nonNull(request.getContactName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("contactName"), "%" + request.getContactName() + "%"));
        }
        if (Objects.nonNull(request.getAreaDialingCode())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("areaDialingCode"), "%" + request.getAreaDialingCode() + "%"));
        }
        if (Objects.nonNull(request.getAddressLine2())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine2"), "%" + request.getAddressLine2() + "%"));
        }

        if (Objects.nonNull(request.getAddressLine1())) {
            predicates.add(criteriaBuilder.like(rootObj.get("addressLine1"), "%" + request.getAddressLine1() + "%"));
        }
        if (Objects.nonNull(request.getName())) {
            predicates.add(criteriaBuilder.like(rootObj.get("name"), "%" + request.getName() + "%"));
        }
        if (Objects.nonNull(request.getCode())) {
            predicates.add(criteriaBuilder.like(rootObj.get("code"), "%" + request.getCode() + "%"));
        }
        if (Objects.nonNull(request.getSupplierCategory())) {
            predicates.add(
                    criteriaBuilder.like(rootObj.get("supplierCategory"), "%" + request.getSupplierCategory() + "%"));
        }
        if (Objects.nonNull(request.getExtensionNo())) {
            predicates.add(criteriaBuilder.like(rootObj.get("extensionNo"), "%" + request.getExtensionNo() + "%"));
        }
        if (Objects.nonNull(request.getTradeTerm())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("tradeTerm"), request.getTradeTerm()));
        }
        if (Objects.nonNull(request.getForecastOrderShipMode())) {
            predicates.add(
                    criteriaBuilder.equal(rootObj.get("forecastOrderShipMode"), request.getForecastOrderShipMode()));
        }
        if (Objects.nonNull(request.getBackOrderShipMode())) {
            predicates.add(criteriaBuilder.equal(rootObj.get("backOrderShipMode"), request.getBackOrderShipMode()));
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

    private void setOrder(PageHelper page, CriteriaQuery<Supplier> criteriaQuery, Root<Supplier> rootObj) {

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
        Root<Supplier> countRoot = countQuery.from(Supplier.class);

        countRoot.join("warehouse");
        countRoot.join("primaryCompany");
        countRoot.join("country");
        countRoot.join("defaultPoType");
        countRoot.join("freighter", JoinType.LEFT);
        countRoot.join("currency");
        countRoot.join("state", JoinType.LEFT);
        countRoot.join("city", JoinType.LEFT);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
