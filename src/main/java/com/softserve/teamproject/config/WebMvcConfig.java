package com.softserve.teamproject.config;

import com.softserve.teamproject.service.MessageByLocaleService;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Use for :
 * - init i18n in app.
 * - store error messages in property files
 */
@Configuration
@PropertySource(value = "classpath:i18n/messages.properties", encoding = "UTF-8")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  private static final String DEFAULT_LANG = "en";
  private static final String LANG_PARAM = "lang";

  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    cookieLocaleResolver.setDefaultLocale(new Locale(DEFAULT_LANG));
    return cookieLocaleResolver;
  }


  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new
        LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName(LANG_PARAM);
    return localeChangeInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }
}
