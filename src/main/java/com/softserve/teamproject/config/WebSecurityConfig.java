package com.softserve.teamproject.config;

import com.softserve.teamproject.controller.authentication.CustomSavedRequestAwareAuthenticationSuccessHandler;
import com.softserve.teamproject.controller.authentication.JsonUsernamePasswordAuthenticationFilter;
import com.softserve.teamproject.controller.authentication.RestAuthenticationEntryPoint;
import com.softserve.teamproject.service.impl.UserDetailsServiceImpl;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * The class provides basic security configurations, configures log-in and log-out process as well
 * as permissions given to the users to access certain urls.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsServiceImpl userDetailsService;
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Autowired
  public void setUserDetailsService(
      UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Autowired
  public void setRestAuthenticationEntryPoint(
      RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
    this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
  }

  /**
   * Method provides configuration with request authorization and granting permissions to users.
   *
   * @param http HttpSecurity
   * @throws Exception when configuration failed to be successfully executed.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //@formatter:off
    http
        .csrf().disable()
        .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
        .authorizeRequests()
            .antMatchers("/**").authenticated().and()
        .addFilter(authenticationFilter())
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).and()
        .rememberMe().key("token").tokenValiditySeconds(3600);
        //@formatter:on
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**",
            "/v2/api-docs", "/configuration/ui", "/configuration/security");
  }

  @Bean
  public JsonUsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
    JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager());
    return filter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws AuthenticationException {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider
        = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    return authProvider;
  }

  @Bean
  public CustomSavedRequestAwareAuthenticationSuccessHandler getSuccessHandler() {
    return new CustomSavedRequestAwareAuthenticationSuccessHandler();
  }
}
