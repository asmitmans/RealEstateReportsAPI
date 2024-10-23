/**
 * Clase de configuración de seguridad para la API RESTful.
 *
 * - Configura las rutas protegidas y las políticas de acceso.
 * - Usa Spring Security para gestionar la autenticación y autorización.
 * - Integra el filtro `JwtFilterRequest` para manejar la autenticación sin estado con JWT.
 * - Deshabilita CSRF y establece la política de manejo de sesiones como STATELESS.
 */
package cl.fullstackjava.reportesInmobiliaria.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilterRequest jwtFilterRequest;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtFilterRequest jwtFilterRequest, UserDetailsService userDetailsService) {
        this.jwtFilterRequest = jwtFilterRequest;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Bean para codificar contraseñas utilizando BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Bean para gestionar la autenticación en la aplicación
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Bean para configurar las reglas de seguridad y el manejo de sesiones en la aplicación
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No mantener sesiones, JWT se usa en cada solicitud
                );

        // Agregar el filtro JWT antes del filtro de autenticación estándar

        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
