package com.pink.itms.config.security;

import com.pink.itms.jwt.JwtTokenConfigurer;
import com.pink.itms.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs/**", "/webjars/**")
                .permitAll()

                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/register").permitAll()

                .antMatchers("/api/users").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/user/all").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/users/edit/{userId}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/users/{id}").hasAnyAuthority("ADMIN", "USER")

                .antMatchers("/api/tasks/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/tasks/").hasAnyAuthority("ADMIN", "USER")

                .antMatchers("/api/tasks/types").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/tasks/types/{id}").hasAnyAuthority("ADMIN", "USER")

                .antMatchers("/api/products").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/products/{id}").hasAnyAuthority("ADMIN", "USER")

                .antMatchers("/api/warehouse").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/warehouse/{id}").hasAnyAuthority("ADMIN", "USER")

                .antMatchers("/api//generate-user-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/generate-warehouse-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/generate-items-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/generate-task-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/generate-task-report/{id}").hasAnyAuthority("ADMIN", "USER")

                .anyRequest().authenticated()
                .and()
                .apply(new JwtTokenConfigurer(jwtTokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}