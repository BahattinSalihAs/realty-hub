package realtyhub.common.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**", "/api/realty-management/customer/v1/customers-verification", "/api/realty-management/realtor/v1/realtors-verification","/api/realty-management/admin /v1/admins-verification").permitAll()
                        .requestMatchers("/api/public/**", "/api/realty-management/customer/v1/customers", "/api/realty-management/realtor/v1/realtors","/api/realty-management/admin /v1/admins").permitAll()
                        .requestMatchers("/api/realty-management/realtor/v1/**").hasRole("REALTOR")
                        .requestMatchers("/api/realty-management/realtor/v1/**", "/api/realty-management/customer/v1/**", "/api/realty-management/admin/v1/**").hasRole("ADMIN")
                        .requestMatchers("/api/realty-management/customer/v1/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
