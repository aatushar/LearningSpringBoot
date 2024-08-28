package com.khajana.setting.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig /* implements WebMvcConfigurer */ {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /*
     * @Override public void addCorsMappings(CorsRegistry registry) {
     * registry.addMapping("/**") .allowedOrigins("*") .allowedMethods("GET",
     * "POST", "PUT", "DELETE", "HEAD"); }
     */

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // Set unauthorized requests exception handler
        http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }).and();
        // Set permissions on endpoints
        // http.authorizeRequests()
        // Our public endpoints
        // .antMatchers("/setting/**").permitAll()
        // .antMatchers(HttpMethod.GET, "/employee/all**").permitAll()
        // .requestMatchers(HttpMethod.POST,
        // "/inventory/api/v1/posts/hello").permitAll()
        // .antMatchers(HttpMethod.GET, "/api/book/**").permitAll()
        // .antMatchers(HttpMethod.POST, "/api/book/search").permitAll()
        // Our private endpoints
        // .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * @Bean CorsConfigurationSource corsConfigurationSource() { CorsConfiguration
     * configuration = new CorsConfiguration();
     * configuration.setAllowedOrigins(Arrays.asList("*"));
     * configuration.setAllowedMethods(Arrays.asList("*"));
     * configuration.setAllowedHeaders(Arrays.asList("*"));
     * configuration.setAllowCredentials(true); UrlBasedCorsConfigurationSource
     * source = new UrlBasedCorsConfigurationSource();
     * source.registerCorsConfiguration("/**", configuration); return source; }
     */
    /*
     * @Bean public CorsWebFilter corsWebFilter() { CorsConfiguration corsConfig =
     * new CorsConfiguration();
     * corsConfig.setAllowedOrigins(Arrays.asList("http://192.168.10.10:3000"));
     * corsConfig.setMaxAge(3600L); corsConfig.addAllowedMethod("*");
     * corsConfig.addAllowedHeader("Requestor-Type");
     * corsConfig.addExposedHeader("X-Get-Header");
     *
     * UrlBasedCorsConfigurationSource source = new
     * UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",
     * corsConfig);
     *
     * return new CorsWebFilter(source); }
     */

    /*
     * @Bean CorsConfigurationSource corsConfigurationSource() { CorsConfiguration
     * configuration = new CorsConfiguration();
     * configuration.setAllowedOrigins(Arrays.asList("http://192.168.10.10:3000"));
     * configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT",
     * "DELETE", "OPTIONS", "HEAD")); configuration.setAllowCredentials(true);
     * configuration.setAllowedHeaders(Arrays.asList("Authorization",
     * "Requestor-Type"));
     * configuration.setExposedHeaders(Arrays.asList("X-Get-Header"));
     * configuration.setMaxAge(3600L); UrlBasedCorsConfigurationSource source = new
     * UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",
     * configuration); return source; }
     */

    /*
     * @Bean CorsConfigurationSource corsConfigurationSource() { CorsConfiguration
     * configuration = new CorsConfiguration();
     * configuration.setAllowedOrigins(Arrays.asList("http://192.168.10.10:3000",
     * "192.168.10.10:3000"));
     * configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE"));
     * configuration.setAllowedHeaders(Arrays.asList("*"));
     * configuration.setAllowCredentials(true); UrlBasedCorsConfigurationSource
     * source = new UrlBasedCorsConfigurationSource();
     * source.registerCorsConfiguration("/**", configuration); return source; }
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // @Override
    /*
     * protected void registerAuthentication(AuthenticationManagerBuilder
     * authManagerBuilder) throws Exception { authManagerBuilder
     * .inMemoryAuthentication()
     * .withUser("user").password("password").roles("ADMIN"); }
     */
}
