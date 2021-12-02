package com.bootcamp.demo.security;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserAuthenticationProvider userAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/account/register").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/user/**").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null) {
                        Logger.getLogger(AccessDeniedHandler.class).warn("User: " + auth.getName()
                                + " attempted to access the protected URL: "
                                + httpServletRequest.getRequestURI());
                    }
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/accessDenied");
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var inMemoryAuth = auth.inMemoryAuthentication();
//        inMemoryAuth.withUser("severus.snape.team@gmail.com").password("{noop}xxx").authorities("ADMIN", "USER");
//        inMemoryAuth.withUser("albus.dumbledore.team@gmail.com").password("{noop}xxx").authorities("ADMIN", "USER");
//        inMemoryAuth.withUser("admin1@gmail.com").password("{noop}xxx").roles("ADMIN");
//        inMemoryAuth.withUser("admin2@gmail.com").password("{noop}xxx").roles("ADMIN");
        auth.authenticationProvider(userAuthenticationProvider);
    }

}
