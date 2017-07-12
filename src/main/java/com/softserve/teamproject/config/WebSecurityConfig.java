package com.softserve.teamproject.config;

import com.softserve.teamproject.controller.CustomSavedRequestAwareAuthenticationSuccessHandler;
import com.softserve.teamproject.controller.RestAuthenticationEntryPoint;
import com.softserve.teamproject.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  @Autowired
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  @Autowired
  private CustomSavedRequestAwareAuthenticationSuccessHandler successHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //@formatter:off

        http
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
            .authorizeRequests()
                .antMatchers("/**").authenticated().and()
            .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(successHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(new SimpleUrlAuthenticationFailureHandler()).and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/").and()
            .rememberMe().key("token").tokenValiditySeconds(3600);
        //@formatter:on
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }


  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    return authProvider;
  }

  @Bean
  public CustomSavedRequestAwareAuthenticationSuccessHandler getSuccessHandler() {
    return new CustomSavedRequestAwareAuthenticationSuccessHandler();
  }
}
