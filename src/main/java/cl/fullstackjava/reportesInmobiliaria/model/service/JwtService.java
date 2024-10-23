/**
 * Servicio para manejar la generación y validación de tokens JWT.
 *
 * - Genera tokens JWT firmados con una clave secreta usando el algoritmo HS256.
 * - Valida los tokens, verifica su expiración y extrae datos como el nombre de usuario.
 * - Utilizado por `JwtFilterRequest` para validar los tokens en cada solicitud.
 *
 * Implementación del **Patrón de Diseño Strategy**:
 * - El método `extractClaim` aplica el **Patrón Strategy** al recibir una función como argumento.
 * - Esto permite extraer de manera flexible cualquier claim (dato) del token JWT, como el nombre de usuario o la fecha de expiración.
 * - Facilita la reutilización y flexibilidad, ya que el mismo método puede usarse para extraer distintos datos del token sin duplicar código.
 */
package cl.fullstackjava.reportesInmobiliaria.model.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Clave secreta generada para firmar los tokens JWT
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Métodos generales para la generación y validación de tokens JWT

    //  Genera un token JWT para el usuario proporcionado.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    //  Valida un token JWT verificando el nombre de usuario y la expiración del token.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    // Métodos específicos para la extracción de claims
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrae la fecha de expiración del token JWT.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrae un dato específico (claim) del token JWT utilizando una función de resolución.
    // Los claims son datos almacenados en el token JWT como el nombre de usuario, roles, etc.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    // Métodos auxiliares para la manipulación interna del token

    // Extrae todos los claims del token JWT.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifica si el token JWT ha expirado.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Crea un token JWT utilizando los claims proporcionados y el nombre de usuario (subject).
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token válido por 10 horas
                .signWith(key)
                .compact();
    }
}