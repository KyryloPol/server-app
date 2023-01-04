package com.trainme.treainmeapp.security;

import com.trainme.treainmeapp.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * We're extending the WebSecurityConfigurerAdapter class and overriding the configure(HttpSecurity http) method.
 * <p>
 * This method is used to configure the security of the web application.
 * <p>
 * We're also overriding the configure(AuthenticationManagerBuilder auth) method.
 * <p>
 * This method is used to configure the authentication manager.
 * <p>
 * The authentication manager is used to authenticate the user when he logs in.
 * <p>
 * We're also overriding the authenticationManager() method.
 * <p>
 * This method is used to expose the authentication manager as a bean.
 * <p>
 * We're also overriding the configure(HttpSecurity http) method.
 * <p>
 * This method is used to configure the security of the web application.
 * <p>
 * We're also overriding the configure(AuthenticationManagerBuilder auth) method.
 * <p>
 * This method is used to configure the authentication manager.
 * <p>
 * The authentication manager is used to authenticate the user when he logs in.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        proxyTargetClass = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthentificationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * If the request is not to the signup URL, then check if the user is authenticated.
     *
     * @param http This is the main object that contains all the configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.SING_UP_URLS).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * This function is used to configure the AuthenticationManagerBuilder object, which is used to create an
     * AuthenticationManager object. The AuthenticationManager object is used to authenticate a user in the login()
     * function
     *
     * @param auth This is the authentication manager builder.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * The `authenticationManager()` function is used to authenticate the user credentials
     *
     * @return The authentication manager.
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    /**
     * It creates a new instance of BCryptPasswordEncoder.
     *
     * @return A new instance of BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * This function is used to create a new instance of the JWTAuthentificationFilter class
     *
     * @return A JWTAuthentificationFilter object.
     */
    @Bean
    public JWTAuthentificationFilter jwtAuthenticationFilter() {
        return new JWTAuthentificationFilter();
    }
}

