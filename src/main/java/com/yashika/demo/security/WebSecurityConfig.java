package com.yashika.demo.security;

import com.yashika.demo.services.UserDetailsServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        return new UserDetailsServiceImplementation();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/events/list/*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/events/register/*").hasAnyRole("USER")
                .antMatchers("/api/events/showFormForUpdate/*", "/api/events/delete/*", "/api/events/showFormForAdd/*").hasRole("ADMIN")
                .antMatchers("/api/users/showFormForUpdate/*", "/api/users/delete/*", "/api/users/showFormForAdd/*", "/api/users/list/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout()  // logout of the session
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }
}
