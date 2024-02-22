package com.warehouse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.administration.role.UserRole;
import com.warehouse.administration.role.UserRoleRepository;
import com.warehouse.administration.role.UserRoleWarehouseScreenMapping;
import com.warehouse.administration.user.User;
import com.warehouse.administration.user.UserRepository;
import com.warehouse.administration.warehousescreen.WarehouseScreen;
import com.warehouse.administration.warehousescreen.WarehouseScreenRepository;
import com.warehouse.enums.UserTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@SpringBootApplication
public class WarehouseApplication extends SpringBootServletInitializer implements WebMvcConfigurer,CommandLineRunner{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private WarehouseScreenRepository warehouseScreenRepository;

    // @Autowired
    // private CurrencyRepository currencyRepository;

    // @Autowired
    // private CountryRepository countryRepository;

    // @Autowired
    // private StateRepository stateRepository;
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WarehouseApplication.class);
    }


	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
        
		//System.out.println("Password encodeed xy==== "+new BCryptPasswordEncoder().encode("admin@123"));
	}

	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:warehouse");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return  localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }

    public void createUser() {
        if (!userRepository.findByUsername("System").isPresent()){

            ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<WarehouseScreen>> typeReference = new TypeReference<List<WarehouseScreen>>(){};
            //TypeReference<List<Currency>> typeReferenceCurrency = new TypeReference<List<Currency>>(){};
            // TypeReference<List<Country>> typeReferenceCountry = new TypeReference<List<Country>>(){};
            // TypeReference<List<State>> typeReferenceState = new TypeReference<List<State>>(){};
            // TypeReference<List<City>> typeReferenceCity = new TypeReference<List<City>>(){};
			try {
                InputStream inputStream = TypeReference.class.getResourceAsStream("/json/warehouse_screen_by_user_type.json");
				List<WarehouseScreen> warehouseScreens = mapper.readValue(inputStream,typeReference);
				warehouseScreenRepository.saveAll(warehouseScreens);

                // inputStream = TypeReference.class.getResourceAsStream("/json/currency.json");
				// List<Currency> currencyList = mapper.readValue(inputStream,typeReferenceCurrency);
				// currencyRepository.saveAll(currencyList);

                // inputStream = TypeReference.class.getResourceAsStream("/json/country.json");
				// List<Country> countryList = mapper.readValue(inputStream,typeReferenceCountry);
				// countryRepository.saveAll(countryList);

                // inputStream = TypeReference.class.getResourceAsStream("/json/state.json");
				// List<State> stateList = mapper.readValue(inputStream,typeReferenceState);
				// stateRepository.saveAll(stateList);




				System.out.println("Users Saved!");

                List<WarehouseScreen> warehouseScreenList = warehouseScreenRepository.findAllByUserType(UserTypeEnum.PowerUser);
                ArrayList<UserRoleWarehouseScreenMapping> userRoleWarehouseScreenMappingList = new ArrayList<UserRoleWarehouseScreenMapping>();
                for (WarehouseScreen warehouseScreen : warehouseScreenList) {
                    UserRoleWarehouseScreenMapping userRoleWarehouseScreenMapping = new UserRoleWarehouseScreenMapping();
                    userRoleWarehouseScreenMapping.setWarehouseScreen(warehouseScreen);
                    userRoleWarehouseScreenMapping.setWarehouseScreenId(warehouseScreen.getId());
                    userRoleWarehouseScreenMapping.setCrateOption(true);
                    userRoleWarehouseScreenMapping.setActionButtonOption(true); 
                    userRoleWarehouseScreenMapping.setActionOption(true);
                    userRoleWarehouseScreenMapping.setHhtOption(true);
                    userRoleWarehouseScreenMapping.setNotificationOption(true);
                    userRoleWarehouseScreenMapping.setReportOption(true); 
                    userRoleWarehouseScreenMapping.setSearchOption(true); 
                    userRoleWarehouseScreenMappingList.add(userRoleWarehouseScreenMapping);
                }

                //System.out.println("Insert Into db");
                UserRole userRole = new UserRole("Admin", "Admin", UserTypeEnum.PowerUser);
                userRole.setUserRoleWarehouseScreenMappings(userRoleWarehouseScreenMappingList);
                userRole = userRoleRepository.save(userRole);
                User systemUser = new User("System", new BCryptPasswordEncoder().encode("system@123"), UserTypeEnum.PowerUser, userRole);
                systemUser.setAddressLine1("System");
                systemUser.setContactName("System");
                userRepository.save(systemUser);


			} catch (IOException e){
				System.out.println("Unable to save users: " + e.getMessage());
			}

            
        }
    }


    @Override
    public void run(String... args) throws Exception {
        createUser();
    }

}
