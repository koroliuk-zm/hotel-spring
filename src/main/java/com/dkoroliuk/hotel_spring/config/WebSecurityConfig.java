package com.dkoroliuk.hotel_spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.dkoroliuk.hotel_spring.entity.AppUserDetails;
import com.dkoroliuk.hotel_spring.handler.AppAuthentificationSuccessHandler;
import com.dkoroliuk.hotel_spring.service.impl.AppUserDetailsService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private AppUserDetailsService appUserDetailsService;
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration for user authentication. Overridden to work with {@link AppUserDetails} - custom implementation of {@link UserDetails}.
     * Password encoder sets to {@link BCryptPasswordEncoder} via bean.
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception if something goes wrong
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(encoder());
    }

    /**
     * Configuration of {@link HttpSecurity}
     * @param http {@link HttpSecurity}
     * @throws Exception if something goes wrong
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/registration", "/error", "/error_404").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/waiter/**").hasRole("WAITER")
                    .antMatchers("/user/**").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .failureUrl("/login?error")
                    .successHandler(appAuthenticationSuccessHandler())
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/");
    }

    /**
     * Bean for custom realization {@link AppAuthenticationSuccessHandler} of {@link AuthenticationSuccessHandler}.
     * @return instance of {@link AppAuthenticationSuccessHandler}
     */
    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler() {
        return new AppAuthentificationSuccessHandler();
    }
}