/**
 * Filtro personalizado para validar tokens JWT en cada solicitud.
 *
 * - Extrae el token JWT del encabezado de la solicitud.
 * - Valida el token y establece la autenticación en el contexto de seguridad de Spring Security.
 * - Este filtro se ejecuta antes del filtro estándar de autenticación.
 */
package cl.fullstackjava.reportesInmobiliaria.configure;

import cl.fullstackjava.reportesInmobiliaria.model.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilterRequest(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("JWT Token invalid: " + e.getMessage());
            }
        }

        // Si se obtuvo el nombre de usuario y no hay autenticación previa en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Verificar y mostrar los roles
            String finalUsername = username; // Crear una variable local final
            userDetails.getAuthorities().forEach(grantedAuthority -> {
                System.out.println("User: " + finalUsername + " has role: " + grantedAuthority.getAuthority());
            });

            // Si el token es válido, configura la autenticación en el contexto de seguridad
            if (jwtService.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}