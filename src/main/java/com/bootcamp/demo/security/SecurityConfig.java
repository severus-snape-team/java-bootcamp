package com.bootcamp.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/firebase/addScooter").permitAll()
            .antMatchers("/firebase/testMoneyCalculation").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .failureUrl("/login-error")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var inMemoryAuth = auth.inMemoryAuthentication();
        inMemoryAuth.withUser("severus.snape.team@gmail.com").password("{noop}xxx").roles("USER");
        inMemoryAuth.withUser("albus.dumbledore.team@gmail.com").password("{noop}xxx").roles("USER");
        // Spring Security 5 requires specifying the password storage format
    }

}
