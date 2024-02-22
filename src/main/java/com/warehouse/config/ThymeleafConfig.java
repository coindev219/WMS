package com.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {

	@Bean
	public ClassLoaderTemplateResolver allHtmlTemplateResolver() {

		ClassLoaderTemplateResolver allHtmlTemplateResolver = new ClassLoaderTemplateResolver();

		allHtmlTemplateResolver.setPrefix("templates/");

		allHtmlTemplateResolver.setTemplateMode("HTML5");

		allHtmlTemplateResolver.setSuffix(".html");

		allHtmlTemplateResolver.setTemplateMode("XHTML");

		allHtmlTemplateResolver.setCharacterEncoding("UTF-8");

		allHtmlTemplateResolver.setOrder(1);

		return allHtmlTemplateResolver;

	}
}
