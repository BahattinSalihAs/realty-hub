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
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
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
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/customer/**").permitAll() // all customer endpoint permit all.
                        .requestMatchers("/admin/v1/admins-verification").permitAll() // email verification permit all.
                        .requestMatchers(HttpMethod.POST,"/admin/v1/admins",
                                "/realtor/v1/realtors").permitAll() //register permit all
                        .requestMatchers(HttpMethod.GET,"/admin/v1/admins").permitAll()
                        .requestMatchers("api/realty-management/admin/v1/login").permitAll()
                        .requestMatchers("/admin/v1/login-success").permitAll()
                        .requestMatchers("/admin/v1/auth",
                                "/realtor/v1/auth").permitAll() //authenticate permit all
                        .requestMatchers("/realtor/systems").hasAnyRole("ADMIN","REALTOR")
                        .requestMatchers("/advert/**").hasRole("REALTOR")
                        .requestMatchers("/admin/leaders").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/admin/v1/login")
                                .loginProcessingUrl("/api/realty-management/admin/v1/process-login")
                                .successHandler(customLoginSuccessHandler)
                                .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(exception ->
                        exception.accessDeniedPage("/admin/accessDenied"))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
