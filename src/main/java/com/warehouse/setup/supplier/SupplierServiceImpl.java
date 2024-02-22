package com.warehouse.setup.supplier;

import java.util.Objects;

import com.warehouse.additionalsetup.freighter.Freighter;
import com.warehouse.additionalsetup.freighter.FreighterRepository;
import com.warehouse.additionalsetup.ordertype.OrderType;
import com.warehouse.additionalsetup.ordertype.OrderTypeRepository;
import com.warehouse.additionalsetup.skutype.SkuType;
import com.warehouse.additionalsetup.skutype.SkuTypeRepository;
import com.warehouse.city.City;
import com.warehouse.city.CityRepository;
import com.warehouse.country.Country;
import com.warehouse.country.CountryRepository;
import com.warehouse.currency.Currency;
import com.warehouse.currency.CurrencyRepository;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRepository;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTime;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTimeRepository;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTimeRequest;
import com.warehouse.setup.supplier.leadtime.SupplierLeadTimeSearchRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRepository;
import com.warehouse.state.State;
import com.warehouse.state.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

        @Autowired
        private SupplierRepository supplierRepository;

        @Autowired
        private SupplierSearchRepository supplierSearchRepository;

        @Autowired
        private CountryRepository countryRepository;

        @Autowired
        private FreighterRepository freighterRepository;

        @Autowired
        private PrimaryCompanyRepository primaryCompanyRepository;

        @Autowired
        private WarehouseRepository warehouseRepository;

        @Autowired
        private CurrencyRepository currencyRepository;

        @Autowired
        private StateRepository stateRepository;

        @Autowired
        private CityRepository cityRepository;

        @Autowired
        private SupplierLeadTimeRepository supplierLeadTimeRepository;

        @Autowired
        private SupplierLeadTimeSearchRepository supplierLeadTimeSearchRepository;

        @Autowired
        private SkuTypeRepository skuTypeRepository;

        @Autowired
        private OrderTypeRepository orderTypeRepository;

        @Override
        public String validateSupplierRequest(Supplier theSupplier) {

                PrimaryCompany primaryCompany = primaryCompanyRepository.findById(theSupplier.getPrimaryCompanyId())
                                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                                theSupplier.getPrimaryCompanyId()));
                theSupplier.setPrimaryCompany(primaryCompany);

                Warehouse warehouse = warehouseRepository.findById(theSupplier.getWarehouseId()).orElseThrow(
                                () -> new ResourceNotFoundException("Warehouse", "id", theSupplier.getWarehouseId()));
                theSupplier.setWarehouse(warehouse);

                Currency currency = currencyRepository.findById(theSupplier.getCurrencyId()).orElseThrow(
                                () -> new ResourceNotFoundException("Currency", "id", theSupplier.getCurrencyId()));
                theSupplier.setCurrency(currency);

                Country country = countryRepository.findById(theSupplier.getCountryId()).orElseThrow(
                                () -> new ResourceNotFoundException("Country", "id", theSupplier.getCountryId()));
                theSupplier.setCountry(country);

                OrderType ordertype = orderTypeRepository.findById(theSupplier.getDefaultPoTypeId()).orElseThrow(
                                () -> new ResourceNotFoundException("PO Type", "id", theSupplier.getDefaultPoTypeId()));
                theSupplier.setDefaultPoType(ordertype);

                if (Objects.nonNull(theSupplier.getFreighterId())) {
                        Freighter freighter = freighterRepository.findById(theSupplier.getFreighterId()).orElseThrow(
                                () -> new ResourceNotFoundException("Freighter", "id", theSupplier.getFreighterId()));
                        theSupplier.setFreighter(freighter);
                }
                if (Objects.nonNull(theSupplier.getStateId())) {
                        State state = stateRepository.findById(theSupplier.getStateId()).orElseThrow(
                                        () -> new ResourceNotFoundException("State", "id", theSupplier.getStateId()));
                        theSupplier.setState(state);
                }
                if (Objects.nonNull(theSupplier.getCityId())) {
                        City city = cityRepository.findById(theSupplier.getCityId()).orElseThrow(
                                        () -> new ResourceNotFoundException("City", "id", theSupplier.getCityId()));
                        theSupplier.setCity(city);
                }

                if (theSupplier.getId() == null) {
                        if (supplierRepository.findByNameAndPrimaryCompanyId(theSupplier.getName(),
                                        theSupplier.getPrimaryCompanyId()).isPresent()) {
                                return "Duplicate value found for Supplier with name:" + theSupplier.getName()
                                                + " and Primary Company:" + primaryCompany.getName();
                        }
                        if (supplierRepository.findByCodeAndPrimaryCompanyId(theSupplier.getCode(),
                                        theSupplier.getPrimaryCompanyId()).isPresent()) {
                                return "Duplicate value found for Supplier with code:" + theSupplier.getCode()
                                                + " and Primary Company:" + primaryCompany.getName();
                        }
                        if (supplierRepository.findByContactNameAndPrimaryCompanyId(theSupplier.getContactName(),
                                        theSupplier.getPrimaryCompanyId()).isPresent()) {
                                return "Duplicate value found for Supplier with contactName:"
                                                + theSupplier.getContactName() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                } else {
                        if (supplierRepository
                                        .findByNameAndPrimaryCompanyIdAndIdNot(theSupplier.getName(),
                                                        theSupplier.getPrimaryCompanyId(), theSupplier.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Supplier with Id:" + theSupplier.getId()
                                                + " and name:" + theSupplier.getName() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                        if (supplierRepository
                                        .findByCodeAndPrimaryCompanyIdAndIdNot(theSupplier.getCode(),
                                                        theSupplier.getPrimaryCompanyId(), theSupplier.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Supplier with Id:" + theSupplier.getId()
                                                + " and code:" + theSupplier.getCode() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                        if (supplierRepository
                                        .findByContactNameAndPrimaryCompanyIdAndIdNot(theSupplier.getContactName(),
                                                        theSupplier.getPrimaryCompanyId(), theSupplier.getId())
                                        .isPresent()) {
                                return "Duplicate value found for Supplier with Id:" + theSupplier.getId()
                                                + " and code:" + theSupplier.getContactName() + " and Primary Company:"
                                                + primaryCompany.getName();
                        }
                }
                return null;
        }

        @Override
        public Supplier saveSupplier(Supplier theSupplier) {
                return supplierRepository.save(theSupplier);
        }

        @Override
        public Supplier findBySupplierId(Long theId) {
                return supplierRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", theId));
        }

        @Override
        public Page<Supplier> findAllSupplierList(PageHelper page, SupplierRequest request) {
                return supplierSearchRepository.findAllWithFilters(page, request);
        }

        @Override
        public void deleteBySupplierId(Long theId) {
                supplierRepository.deleteById(theId);
        }

        @Override
        public String validateSupplierLeadTimeRequest(SupplierLeadTime theSupplierLeadTime) {

                PrimaryCompany primaryCompany = primaryCompanyRepository
                                .findById(theSupplierLeadTime.getPrimaryCompanyId())
                                .orElseThrow(() -> new ResourceNotFoundException("PrimaryCompany", "id",
                                                theSupplierLeadTime.getPrimaryCompanyId()));
                theSupplierLeadTime.setPrimaryCompany(primaryCompany);

                Warehouse warehouse = warehouseRepository.findById(theSupplierLeadTime.getWarehouseId())
                                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id",
                                                theSupplierLeadTime.getWarehouseId()));
                theSupplierLeadTime.setWarehouse(warehouse);

                Supplier supplier = supplierRepository.findById(theSupplierLeadTime.getSupplierId())
                                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id",
                                                theSupplierLeadTime.getSupplierId()));
                theSupplierLeadTime.setSupplier(supplier);

                SkuType skutype = skuTypeRepository.findById(theSupplierLeadTime.getSkuTypeId())
                                .orElseThrow(() -> new ResourceNotFoundException("Sku Type", "id",
                                                theSupplierLeadTime.getSkuTypeId()));
                theSupplierLeadTime.setSkuType(skutype);

                if (theSupplierLeadTime.getId() == null) {
                        if (supplierLeadTimeRepository.findByWarehouseIdAndPrimaryCompanyIdAndSupplierId(
                                        warehouse.getId(), supplier.getId(), primaryCompany.getId()).isPresent()) {
                                return "Duplicate value found for Supplier Lead Time with Supplier Code:"
                                                + supplier.getCode() + " and Primary Company:"
                                                + primaryCompany.getName() + " and warehouse:" + warehouse.getName();
                        }

                } else {
                        if (supplierLeadTimeRepository.findByWarehouseIdAndPrimaryCompanyIdAndSupplierIdAndIdNot(
                                        warehouse.getId(), supplier.getId(), primaryCompany.getId(),
                                        theSupplierLeadTime.getId()).isPresent()) {
                                return "Duplicate value found for Supplier Lead Time with Supplier Code:"
                                                + supplier.getCode() + " and Primary Company:"
                                                + primaryCompany.getName() + " and warehouse:" + warehouse.getName();
                        }
                }
                return null;
        }

        @Override
        public SupplierLeadTime saveSupplierLeadTime(SupplierLeadTime theSupplierLeadTime) {
                return supplierLeadTimeRepository.save(theSupplierLeadTime);
        }

        @Override
        public SupplierLeadTime findBySupplierLeadTimeId(Long theId) {
                return supplierLeadTimeRepository.findById(theId)
                                .orElseThrow(() -> new ResourceNotFoundException("SupplierLeadTime", "id", theId));
        }

        @Override
        public Page<SupplierLeadTime> findAllSupplierLeadTimeList(PageHelper page, SupplierLeadTimeRequest request) {
                return supplierLeadTimeSearchRepository.findAllWithFilters(page, request);
        }

        @Override
        public void deleteBySupplierLeadTimeId(Long theId) {
                supplierLeadTimeRepository.deleteById(theId);

        }

}
