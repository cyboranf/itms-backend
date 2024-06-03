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


                // User Controller
                .antMatchers(HttpMethod.PUT,"/api/users/edit/{userId}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/api/user/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/api/users/{userId}/join/tasks/{taskId}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/users").hasAnyAuthority("ADMIN", "USER")


                // TaskType Controller
                .antMatchers(HttpMethod.POST,"/api/tasks/types").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/tasks/types").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/api/tasks/types/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/tasks/types/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/api/tasks/types/{id}").hasAnyAuthority("ADMIN", "USER")

                // Task Controller
                .antMatchers(HttpMethod.POST,"/api/tasks").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/tasks/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/tasks").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/api/tasks/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/api/tasks/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/api/tasks/{taskId}/join/products/{productId}").hasAnyAuthority("ADMIN", "USER")

                // Product Controller
                .antMatchers(HttpMethod.POST,"/api/products").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/products").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/api/products/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/api/products/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/products/{id}").hasAnyAuthority("ADMIN", "USER")

                // Warehouse Controller
                .antMatchers(HttpMethod.POST,"/api/warehouse").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/warehouse").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/api/warehouse/{id}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/api/warehouse/{id}").hasAnyAuthority("ADMIN", "USER")

                // PDF-Report Controller
                .antMatchers(HttpMethod.GET,"/api/generate-user-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/generate-warehouse-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/generate-items-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/generate-task-report").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/api/generate-task-report/{id}").hasAnyAuthority("ADMIN", "USER")

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