package com.warehouse.setup.warehouse;

import java.util.List;
import java.util.Objects;

import com.warehouse.city.City;
import com.warehouse.city.CityRepository;
import com.warehouse.country.Country;
import com.warehouse.country.CountryRepository;
import com.warehouse.exception.DuplicateValueFoundException;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.setup.location.Location;
import com.warehouse.setup.location.LocationRepository;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMapping;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMappingListResponse;
import com.warehouse.setup.warehouse.integrationmapping.IntegrationMappingRepository;
import com.warehouse.setup.warehouse.printers.ConfigurePrinter;
import com.warehouse.setup.warehouse.printers.ConfigurePrinterListResponse;
import com.warehouse.setup.warehouse.printers.ConfigurePrinterRepository;
import com.warehouse.setup.warehouse.runningcost.RunningCost;
import com.warehouse.setup.warehouse.runningcost.RunningCostListResponse;
import com.warehouse.setup.warehouse.runningcost.RunningCostRepository;
import com.warehouse.state.State;
import com.warehouse.state.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseSearchRepository warehouseSearchRepository;

    @Autowired
    private ConfigurePrinterRepository configurePrinterRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private IntegrationMappingRepository integrationMappingRepository;

    @Autowired
    private RunningCostRepository runningCostRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Page<Warehouse> findAll(PageHelper warehousePage, WarehouseRequest warehouseRequest) {
        return warehouseSearchRepository.findAllWithFilters(warehousePage, warehouseRequest);
    }

    @Override
    public Warehouse findById(Long theId) {
        return warehouseRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", theId));
    }

    @Override
    public String validateWarehouseRequest(Warehouse theWarehouse) {

        Country country = countryRepository.findById(theWarehouse.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country", "id", theWarehouse.getCountryId()));
        theWarehouse.setCountry(country);

        if (Objects.nonNull(theWarehouse.getStateId())) {
            State state = stateRepository.findById(theWarehouse.getStateId())
                    .orElseThrow(() -> new ResourceNotFoundException("State", "id", theWarehouse.getStateId()));
            theWarehouse.setState(state);
        }
        if (Objects.nonNull(theWarehouse.getCityId())) {
            City city = cityRepository.findById(theWarehouse.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City", "id", theWarehouse.getCityId()));
            theWarehouse.setCity(city);
        }

        if (theWarehouse.getId() == null) {
            if (warehouseRepository.findByName(theWarehouse.getName()).isPresent()) {
                throw new DuplicateValueFoundException("Warehouse", "name", theWarehouse.getName());
            }
            if (warehouseRepository.findByCode(theWarehouse.getCode()).isPresent()) {
                throw new DuplicateValueFoundException("Warehouse", "code", theWarehouse.getCode());
            }
            if (warehouseRepository.findByContactName(theWarehouse.getContactName()).isPresent()) {
                throw new DuplicateValueFoundException("Warehouse", "contactName", theWarehouse.getContactName());
            }
        } else {
            if (warehouseRepository.findByNameAndIdNot(theWarehouse.getName(), theWarehouse.getId()).isPresent()) {
                throw new DuplicateValueFoundException("Warehouse", "name", theWarehouse.getName());
            }
            if (warehouseRepository.findByCodeAndIdNot(theWarehouse.getCode(), theWarehouse.getId()).isPresent()) {
                throw new DuplicateValueFoundException("Warehouse", "code", theWarehouse.getCode());
            }
            if (warehouseRepository.findByContactNameAndIdNot(theWarehouse.getContactName(), theWarehouse.getId())
                    .isPresent()) {
                throw new DuplicateValueFoundException("Warehouse", "contactName", theWarehouse.getContactName());
            }
        }
        return null;
    }

    @Override
    public Warehouse save(Warehouse theWarehouse) {
        return warehouseRepository.save(theWarehouse);
    }

    @Override
    public void deleteById(Long theId) {
        warehouseRepository.deleteById(theId);
    }

    @Override
    public boolean isWarehouseExists(Long theId) {
        return warehouseRepository.existsById(theId);
    }

    // ConfigurePrinter Api

    @Override
    public List<ConfigurePrinterListResponse> findAllWarehouseWithConfigurePrinters(WarehouseRequest warehouseRequest) {
        return warehouseSearchRepository.findAllWithConfigurePrinterList(warehouseRequest);
    }

    @Override
    public String validateConfigurePrinterRequest(ConfigurePrinter theConfigurePrinter) {

        if (Objects.nonNull(theConfigurePrinter.getWarehouseId())) {
            Warehouse warehouse = warehouseRepository.findById(theConfigurePrinter.getWarehouseId()).orElseThrow(
                    () -> new ResourceNotFoundException("Warehouse", "id", theConfigurePrinter.getWarehouseId()));
            theConfigurePrinter.setWarehouse(warehouse);
        }

        if (Objects.nonNull(theConfigurePrinter.getFromDockId())) {
            Location locationObj = locationRepository.findById(theConfigurePrinter.getFromDockId()).orElseThrow(
                    () -> new ResourceNotFoundException("Location", "id", theConfigurePrinter.getFromDockId()));
            theConfigurePrinter.setFromDock(locationObj);
        }

        if (Objects.nonNull(theConfigurePrinter.getToDockId())) {
            Location locationObj = locationRepository.findById(theConfigurePrinter.getToDockId()).orElseThrow(
                    () -> new ResourceNotFoundException("Location", "id", theConfigurePrinter.getToDockId()));
            theConfigurePrinter.setToDock(locationObj);
        }

        if (theConfigurePrinter.getId() == null) {
            if (configurePrinterRepository
                    .findByNameAndWarehouseId(theConfigurePrinter.getName(), theConfigurePrinter.getWarehouseId())
                    .isPresent()) {
                return "Duplicate value found for Configure Printer with Printer Name:" + theConfigurePrinter.getName()
                        + " and Warehouse:" + theConfigurePrinter.getWarehouse().getName();
            }
        } else {
            if (configurePrinterRepository.findByNameAndWarehouseIdAndIdNot(theConfigurePrinter.getName(),
                    theConfigurePrinter.getWarehouseId(), theConfigurePrinter.getId()).isPresent()) {
                return "Duplicate value found for Configure Printer with ID:" + theConfigurePrinter.getId()
                        + " and Printer Name:" + theConfigurePrinter.getName() + " and Warehouse:"
                        + theConfigurePrinter.getWarehouse().getName();
            }
        }

        return null;
    }

    @Override
    public List<ConfigurePrinter> savePrinter(List<ConfigurePrinter> theConfigurePrinter) {
        return configurePrinterRepository.saveAll(theConfigurePrinter);
    }

    @Override
    public ConfigurePrinter findByConfigurePrinterId(Long theId) {
        return configurePrinterRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("ConfigurePrinter", "id", theId));
    }

    @Override
    public void deleteAllPrinters(List<ConfigurePrinter> theConfigurePrinter) {
        configurePrinterRepository.deleteAll(theConfigurePrinter);
    }

    @Override
    public String validateIntregrationMappingRequest(IntegrationMapping theIntregrationMapping) {
        if (Objects.nonNull(theIntregrationMapping.getWarehouseId())) {
            Warehouse warehouse = warehouseRepository.findById(theIntregrationMapping.getWarehouseId()).orElseThrow(
                    () -> new ResourceNotFoundException("Warehouse", "id", theIntregrationMapping.getWarehouseId()));
            theIntregrationMapping.setWarehouse(warehouse);
        }

        if (theIntregrationMapping.getId() == null) {
            if (integrationMappingRepository
                    .findByCodeAndWarehouseId(theIntregrationMapping.getCode(), theIntregrationMapping.getWarehouseId())
                    .isPresent()) {
                return "Duplicate value found for Intregration Mapping with Printer Name:"
                        + theIntregrationMapping.getCode() + " and Warehouse:"
                        + theIntregrationMapping.getWarehouse().getName();
            }
        } else {
            if (integrationMappingRepository.findByCodeAndWarehouseIdAndIdNot(theIntregrationMapping.getCode(),
                    theIntregrationMapping.getWarehouseId(), theIntregrationMapping.getId()).isPresent()) {
                return "Duplicate value found for Intregration Mapping with ID:" + theIntregrationMapping.getId()
                        + " and code:" + theIntregrationMapping.getCode() + " and Warehouse:"
                        + theIntregrationMapping.getWarehouse().getName();
            }
        }
        return null;
    }

    @Override
    public List<IntegrationMapping> saveIntregrationMapping(List<IntegrationMapping> theIntregrationMapping) {
        return integrationMappingRepository.saveAll(theIntregrationMapping);
    }

    @Override
    public IntegrationMapping findByIntregrationMappingId(Long theId) {
        return integrationMappingRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Intregration Mapping", "id", theId));
    }

    @Override
    public void deleteAllIntregrationMapping(List<IntegrationMapping> theIntregrationMapping) {
        integrationMappingRepository.deleteAll(theIntregrationMapping);

    }

    @Override
    public List<IntegrationMappingListResponse> findAllWarehouseWithIntegrationMapping(WarehouseRequest warehouseRequest) {
        return warehouseSearchRepository.findAllWithIntegrationMappingList(warehouseRequest);
    }

    @Override
    public List<RunningCostListResponse> findAllWarehouseWithRunningCost(WarehouseRequest warehouseRequest) {
        return warehouseSearchRepository.findAllWithRunningCostList(warehouseRequest);
    }

    @Override
    public String validateRunningCostRequest(RunningCost theRunningCost) {
        if (Objects.nonNull(theRunningCost.getWarehouseId())) {
            Warehouse warehouse = warehouseRepository.findById(theRunningCost.getWarehouseId()).orElseThrow(
                    () -> new ResourceNotFoundException("Warehouse", "id", theRunningCost.getWarehouseId()));
            theRunningCost.setWarehouse(warehouse);
        }

        if (theRunningCost.getId() == null) {
            if (runningCostRepository
                    .findByCostAndWarehouseId(theRunningCost.getCost(), theRunningCost.getWarehouseId()).isPresent()) {
                return "Duplicate value found for Intregration Mapping with Cost:" + theRunningCost.getCost()
                        + " and Warehouse:" + theRunningCost.getWarehouse().getName();
            }
        } else {
            if (runningCostRepository.findByCostAndWarehouseIdAndIdNot(theRunningCost.getCost(),
                    theRunningCost.getWarehouseId(), theRunningCost.getId()).isPresent()) {
                return "Duplicate value found for Intregration Mapping with ID:" + theRunningCost.getId() + " and cost:"
                        + theRunningCost.getCost() + " and Warehouse:" + theRunningCost.getWarehouse().getName();
            }
        }
        return null;
    }

    @Override
    public List<RunningCost> saveRunningCost(List<RunningCost> theRunningCost) {
        return runningCostRepository.saveAll(theRunningCost);
    }

    @Override
    public RunningCost findByRunningCostId(Long theId) {
        return runningCostRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("Running Cost", "id", theId));
    }

    @Override
    public void deleteAllRunningCost(List<RunningCost> theRunningCost) {
        runningCostRepository.deleteAll(theRunningCost);
    }
    
}
