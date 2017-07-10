package com.softserve.teamproject.config;

import com.softserve.teamproject.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao
        .DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders
        .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration
        .EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
        .WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/welcome").authenticated()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .and()
                .formLogin()//.loginPage("/signin").permitAll()
                .defaultSuccessUrl("/welcome")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/signin-error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                .rememberMe().key("token").tokenValiditySeconds(3600);
    }

////We don't need a password encoder while we don't have registration
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
////We don't need a password encoder while we don't have registration
//        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
