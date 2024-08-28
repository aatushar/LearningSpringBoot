package com.khajana.setting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
public class SettingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SettingServiceApplication.class, args);
    }

    /*
     * @Bean public CorsConfigurationSource corsConfigurationSource() {
     * CorsConfiguration configuration = new CorsConfiguration();
     * configuration.setAllowedOrigins(Arrays.asList("*"));
     * configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
     * "DELETE", "OPTIONS")); configuration.setAllowedHeaders(
     * Arrays.asList("authorization", "content-type", "x-auth-token",
     * "Access-Control-Expose-Headers","Access-Control-Allow-Origin",
     * "Access-Control-Request-Method","Access-Control-Allow-Credentials"));
     * configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
     * UrlBasedCorsConfigurationSource source = new
     * UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",
     * configuration); return source; }
     */

    /*
     * @Bean public CorsFilter corsFilter() { UrlBasedCorsConfigurationSource source
     * = new UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
     * CorsConfiguration(); config.setAllowCredentials(true);
     * config.addAllowedOrigin("*"); config.addAllowedHeader("*");
     * config.addAllowedMethod("*"); source.registerCorsConfiguration("/**",
     * config); return new CorsFilter(source); }
     */

    /*
     * @Bean public CorsFilter corsFilter() { final UrlBasedCorsConfigurationSource
     * source = new UrlBasedCorsConfigurationSource(); final CorsConfiguration
     * config = new CorsConfiguration(); config.setAllowCredentials(true);
     * config.addAllowedOrigin("*"); // this allows all origin
     * config.addAllowedHeader("*"); // this allows all headers
     * config.addAllowedMethod("OPTIONS"); config.addAllowedMethod("HEAD");
     * config.addAllowedMethod("GET"); config.addAllowedMethod("PUT");
     * config.addAllowedMethod("POST"); config.addAllowedMethod("DELETE");
     * config.addAllowedMethod("PATCH"); source.registerCorsConfiguration("/**",
     * config); return new CorsFilter(source); }
     */

    /*
     * @Bean public CorsFilter corsFilter() {
     *
     * CorsConfiguration corsConfiguration = new CorsConfiguration();
     * corsConfiguration.setAllowCredentials(true);
     * corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
     * corsConfiguration.setAllowedHeaders(Arrays.asList("Origin",
     * "Access-Control-Allow-Origin","Access-Control-Allow-Credentials",
     * "Content-Type", "Accept", "Authorization", "Origin, Accept",
     * "X-Requested-With", "Access-Control-Request-Method",
     * "Access-Control-Request-Headers","Access-Control-Expose-Headers"));
     *
     * corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type",
     * "Accept", "Authorization", "Access-Control-Allow-Origin",
     * "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
     *
     * corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT",
     * "DELETE", "OPTIONS")); UrlBasedCorsConfigurationSource
     * urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
     * urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",
     * corsConfiguration); return new CorsFilter(urlBasedCorsConfigurationSource);
     *
     * }
     */

}
