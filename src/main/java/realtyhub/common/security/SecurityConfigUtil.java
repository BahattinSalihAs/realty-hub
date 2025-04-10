package realtyhub.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import realtyhub.common.security.filter.JwtAuthenticationFilter;
import realtyhub.common.security.handler.CustomLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigUtil {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/realty-management/customer/**").permitAll() // all customer endpoint permit all.
                        .requestMatchers("/api/realty-management/admin/v1/admins-verification",
                                "/api/realty-management/realtor/v1/realtors-verification").permitAll() // email verification permit all.
                        .requestMatchers(HttpMethod.POST,"api/realty-management/admin/v1/admins",
                                "/api/realty-management/realtor/v1/realtors").permitAll() //register permit all
                        .requestMatchers(HttpMethod.GET,"/api/realty-management/admin/v1/admins").permitAll()
                        .requestMatchers("/api/realty-management/admin/v1/login").permitAll()
                        .requestMatchers("/api/realty-management/admin/v1/login-success").permitAll()
                        .requestMatchers("/api/realty-management/admin/v1/auth",
                                "/api/realty-management/realtor/v1/auth").permitAll() //authenticate permit all
                        .requestMatchers("api/realty-management/realtor/systems").hasAnyRole("ADMIN","REALTOR")
                        .requestMatchers("/api/realty-management/admin/leaders").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/api/realty-management/admin/v1/login")
                                .loginProcessingUrl("/api/realty-management/admin/v1/process-login")
                                .successHandler(customLoginSuccessHandler)
                                .permitAll())
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exception ->
                        exception.accessDeniedPage("/api/realty-management/admin/accessDenied"))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
