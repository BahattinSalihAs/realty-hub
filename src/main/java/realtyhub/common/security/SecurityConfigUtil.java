package realtyhub.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import realtyhub.common.security.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigUtil {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/realty-management/customer/**").permitAll() // all customer endpoint permit all.
                        .requestMatchers("/api/realty-management/admin/v1/admins-verification",
                                "/api/realty-management/realtor/v1/realtors-verification").permitAll() // email verification permit all.
                        .requestMatchers("api/realty-management/admin/v1/admins",
                                "/api/realty-management/realtor/v1/realtors").permitAll() //register permit all
                        .requestMatchers("/api/realty-management/admin/v1/auth",
                                "/api/realty-management/realtor/v1/auth").permitAll() //authenticate permit all
                        .requestMatchers("/api/realty-management/admin/v1/login").hasRole("ADMIN")
                        .requestMatchers("/api/realty-management/realtor/v1/login").hasAnyRole("ADMIN","REALTOR")
                        .requestMatchers("/api/realty-management/advert/v1/adverts").hasAnyRole("ADMIN", "REALTOR")
                        .anyRequest().denyAll())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
