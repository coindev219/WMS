package com.warehouse.rest;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.warehouse.additionalsetup.uom.Uom;
import com.warehouse.additionalsetup.uom.UomRepository;
import com.warehouse.administration.user.User;
import com.warehouse.administration.user.UserRepository;
import com.warehouse.city.City;
import com.warehouse.city.CityService;
import com.warehouse.country.Country;
import com.warehouse.country.CountryService;
import com.warehouse.currency.Currency;
import com.warehouse.currency.CurrencyService;
import com.warehouse.enums.SavedSearchFilterTypeEnum;
import com.warehouse.exception.ResourceNotFoundException;
import com.warehouse.helper.GeneralResponse;
import com.warehouse.helper.ZXingHelper;
import com.warehouse.savedsearch.SavedSearch;
import com.warehouse.savedsearch.SavedSearchService;
import com.warehouse.setup.company.primarycompany.PrimaryCompany;
import com.warehouse.setup.company.primarycompany.PrimaryCompanyRequest;
import com.warehouse.setup.company.primarycompany.PrimaryCompanySearchRepository;
import com.warehouse.setup.company.warehousecompanymapping.WarehouseCompanyMappingRepository;
import com.warehouse.setup.warehouse.Warehouse;
import com.warehouse.setup.warehouse.WarehouseRequest;
import com.warehouse.setup.warehouse.WarehouseSearchRepository;
import com.warehouse.state.State;
import com.warehouse.state.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/api")
public class AppRestController {

	@Autowired
	private CountryService countryService;

	@Autowired
	private StateService stateService;

	@Autowired
	private CityService cityService;

	@Autowired
	private SavedSearchService savedSearchService;;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private WarehouseCompanyMappingRepository warehouseCompanyMappingRepository;

	@Autowired
	private WarehouseSearchRepository warehouseSearchRepository;

	@Autowired
	private PrimaryCompanySearchRepository primaryCompanySearchRepository;

	@Autowired
	private UomRepository uomRepository;

	@Autowired
	private TemplateEngine templateEngine;



	// Country Api
	@GetMapping("/countries")
	public List<Country> getAllCountries() {
		return countryService.findAll();
	}

	// Country Api
	@GetMapping("/mobile-app/countries")
	public List<Country> getAllCountriesForMobileApp() {
		return countryService.findAllForMoibleApps();
	}

	@GetMapping("/countries/{id}")
	public Country getCountryById(@PathVariable(value = "id") Long countryId) {
		return countryService.findById(countryId);
	}

	@PostMapping("/countries")
	public Country addCountry(@Valid @RequestBody Country theCountry) {
		countryService.save(theCountry);
		return theCountry;
	}

	@PutMapping("/countries/{id}")
	public Country updateCountry(@PathVariable(value = "id") Long countryId, @Valid @RequestBody Country theCountry) {
		Country countryObj = countryService.findById(countryId);
		countryObj.setCountryName(theCountry.getCountryName());
		countryObj = countryService.save(countryObj);
		return countryObj;
	}

	@DeleteMapping("/countries/{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable(value = "id") Long countryId) {
		countryService.findById(countryId);
		countryService.deleteById(countryId);
		return ResponseEntity.ok().build();
	}

	// State Api
	@GetMapping("/countries/{countryId}/states")
	public List<State> getAllStates(@PathVariable(value = "countryId") Long theCountryId) {
		return stateService.findAllStatesByCountry(theCountryId);
	}

	@GetMapping("/mobile-app/countries/{countryId}/states")
	public List<State> getAllStatesForMobileApps(@PathVariable(value = "countryId") Long theCountryId) {
		return stateService.findAllForMoibleApps(theCountryId);
	}

	@GetMapping("/countries/{countryId}/states/{stateId}")
	public State getStateById(@PathVariable(value = "countryId") Long countryId,
			@PathVariable(value = "stateId") Long stateId) {
		if (!countryService.isCountryExists(countryId)) {
			throw new ResourceNotFoundException("Country", "id", countryId);
		}
		State stateObj = stateService.findById(stateId);
		return stateObj;
	}

	@PostMapping("/countries/{countryId}/states")
	public State addState(@PathVariable(value = "countryId") Long theCountryId, @Valid @RequestBody State theState) {
		Country theCountry = countryService.findById(theCountryId);
		theState.setCountry(theCountry);
		return stateService.save(theState);
	}

	@PutMapping("/countries/{countryId}/states/{stateId}")
	public State updateState(@PathVariable(value = "countryId") Long countryId,
			@PathVariable(value = "stateId") Long stateId, @Valid @RequestBody State theState) {

		if (!countryService.isCountryExists(countryId)) {
			throw new ResourceNotFoundException("Country", "id", countryId);
		}
		State stateObj = stateService.findById(stateId);
		stateObj.setStateName(theState.getStateName());
		stateObj = stateService.save(stateObj);
		return stateObj;
	}

	@DeleteMapping("/countries/{countryId}/states/{stateId}")
	public ResponseEntity<?> deleteState(@PathVariable(value = "countryId") Long countryId,
			@PathVariable(value = "stateId") Long stateId) {
		return stateService.findByIdAndCountryId(stateId, countryId).map(state -> {
			this.stateService.deleteById(stateId);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("State", "id", stateId));
	}

	// City Api
	@GetMapping("/states/{stateId}/cities")
	public List<City> getAllCities(@PathVariable(value = "stateId") Long theStateId) {
		return cityService.findAllCityByState(theStateId);
	}

	@GetMapping("/mobile-app//states/{stateId}/cities")
	public List<City> getAllCitiesForMobileApp(@PathVariable(value = "stateId") Long theStateId) {
		return cityService.findAllForMoibleApps(theStateId);
	}

	@GetMapping("/states/{stateId}/cities/{cityId}")
	public City getCityById(@PathVariable(value = "stateId") Long stateId,
			@PathVariable(value = "cityId") Long cityId) {
		if (!stateService.isStateExists(stateId)) {
			throw new ResourceNotFoundException("State", "id", stateId);
		}
		City cityObj = cityService.findById(cityId);
		return cityObj;
	}

	@PostMapping("/states/{stateId}/cities")
	public City addCity(@PathVariable(value = "stateId") Long theStateId, @Valid @RequestBody City theCity) {
		State theState = stateService.findById(theStateId);
		theCity.setState(theState);
		return cityService.save(theCity);
	}

	@PutMapping("/states/{stateId}/cities/{cityId}")
	public City updateState(@PathVariable(value = "stateId") Long stateId, @PathVariable(value = "cityId") Long cityId,
			@Valid @RequestBody City theCity) {

		if (!stateService.isStateExists(stateId)) {
			throw new ResourceNotFoundException("State", "id", stateId);
		}
		City cityObj = cityService.findById(cityId);
		cityObj.setCityName(theCity.getCityName());
		cityObj = cityService.save(cityObj);
		return cityObj;
	}

	@DeleteMapping("/states/{stateId}/cities/{cityId}")
	public ResponseEntity<?> deleteCity(@PathVariable(value = "stateId") Long stateId,
			@PathVariable(value = "cityId") Long cityId) {

		return cityService.findByIdAndStateId(cityId, stateId).map(city -> {
			this.cityService.deleteById(cityId);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("City", "id", cityId));
	}

	@GetMapping("/countries/states/cities")
	public List<Country> getAllCountriesAndItsStatesAndCities() {
		List<Country> countries = countryService.findAll();
		for (Country country : countries) {
			List<State> states = stateService.findAllStatesByCountry(country.getId());
			for (State state : states) {
				List<City> cities = cityService.findAllCityByState(state.getId());
				state.setCities(cities);
			}
			country.setStates(states);
		}
		return countries;
	}

	// Save and Get Search

	@GetMapping("/saved-searches/{filterType}")
	public List<SavedSearch> getAllSavedSearches(@PathVariable(value = "filterType") String theFilterType) {
		SavedSearchFilterTypeEnum filterType = SavedSearchFilterTypeEnum.fromShortName(theFilterType);
		return savedSearchService.findAllSavedSearchByFilterType(filterType);
	}

	@PostMapping("/saved-searches")
	public SavedSearch addToSavedSearches(@Valid @RequestBody SavedSearch theSavedSearch) {

		User user = userRepository.findByUsername("admin")
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + "admin"));

		theSavedSearch.setUser(user);

		System.out.println(theSavedSearch);

		theSavedSearch = savedSearchService.save(theSavedSearch);
		return theSavedSearch;
	}

	@GetMapping("/saved-searches/detail/{savedSearchId}")
	public SavedSearch getSavedSearchDetailById(@PathVariable(value = "savedSearchId") Long savedSearchId) {
		SavedSearch savedSearchObj = savedSearchService.findById(savedSearchId);
		return savedSearchObj;
	}

	@DeleteMapping("/saved-searches/remove-all/{filterType}")
	public ResponseEntity<?> deleteAllSavedSearchesForFilterType(
			@PathVariable(value = "filterType") String theFilterType) {
		SavedSearchFilterTypeEnum filterType = SavedSearchFilterTypeEnum.fromShortName(theFilterType);
		savedSearchService.deleteAllSavedSearchs(filterType);
		return ResponseEntity.ok().build();
	}

	// Currency Api
	@GetMapping("/currency-list")
	public List<Currency> getAvailableCurrencies() {
		return currencyService.findAllCurrency();
	}

	@GetMapping("/company/{companyId}/warehouse/{warehouseId}/check")
	public ResponseEntity<GeneralResponse> checkCompanyWarehouseExists(
			@PathVariable(value = "companyId") Long companyId, @PathVariable(value = "warehouseId") Long warehouseId) {
		if (warehouseCompanyMappingRepository.findByPrimaryCompanyIdAndWarehouseId(companyId, warehouseId)
				.isPresent()) {
			GeneralResponse generalResponse = new GeneralResponse(new Date(), "success", "");
			return new ResponseEntity<GeneralResponse>(generalResponse, HttpStatus.OK);
		}
		throw new ResourceNotFoundException("NotFound", "id", companyId);
	}

	@PostMapping("/company-list-by-warehouse")
	public ResponseEntity<List<PrimaryCompany>> companyListByWarehouse(@RequestBody PrimaryCompanyRequest request) {
		return new ResponseEntity<>(primaryCompanySearchRepository.findPrimaryCompanyByWarehouse(request),
				HttpStatus.OK);
	}

	@PostMapping("/warehouse-list-by-company")
	public ResponseEntity<List<Warehouse>> warehouseListByCompanyId(@RequestBody WarehouseRequest request) {
		return new ResponseEntity<>(warehouseSearchRepository.findWarehouseListByPrimaryCompany(request),
				HttpStatus.OK);
	}
	
	@GetMapping(value = "/barcode/{forText}", produces = MediaType.IMAGE_PNG_VALUE)
	public BufferedImage getTestingBarcode(@PathVariable("forText") String forText) {
		return ZXingHelper.getBarCodeImage(forText, 200, 100);
	}

	@GetMapping(value = "/pdf/{uomId}")
	public ResponseEntity<?> getPDF(@PathVariable("uomId") Long uomId, HttpServletRequest request) {

		/* Do Business Logic */

		Uom uom = uomRepository.getById(uomId);

		/* Create HTML using Thymeleaf template Engine */

		Context context = new Context();

		// context.setVariable("name", uom.getName());

		context.setVariable("uom", uom);
		String orderHtml = templateEngine.process("sku-labelling-sku-wise", context);

		/* Setup Source and target I/O streams */

		ByteArrayOutputStream target = new ByteArrayOutputStream();
		ConverterProperties converterProperties = new ConverterProperties();

		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();

		converterProperties.setBaseUri(baseUrl);
		/* Call convert method */
		HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

		/* extract output as bytes */
		byte[] bytes = target.toByteArray();

		/* Send the response as downloadable PDF */

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(bytes);

	}
	

}
