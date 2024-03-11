package com.system.expenseTracker.security;

import com.system.expenseTracker.configuration.CustomAuthSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration{

    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    public WebSecurityConfiguration(CustomAuthSuccessHandler customAuthSuccessHandler) {
        this.customAuthSuccessHandler = customAuthSuccessHandler;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/cover","/login", "/sign-up").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            // Handle success authentication and redirect based on role
                            //**--------No need to send request if not going to redirect to the
                            //**------URL request came from
                            String targetUrl = determineTargetUrl(request,authentication);
                            response.sendRedirect(targetUrl);
                        }))

                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))

                .exceptionHandling(exception -> exception.accessDeniedPage("/403"));
        return http.build();
    }

    private String determineTargetUrl(HttpServletRequest request, Authentication authentication) {
        // Get the user's authorities/roles
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check if the user has admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        // Determine the target URL based on the user's role
        if (isAdmin) {
            return "/admin";
        } else {
            return "/find-all";
        }
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers("/resources/**","/static/**"));
    }
    }


