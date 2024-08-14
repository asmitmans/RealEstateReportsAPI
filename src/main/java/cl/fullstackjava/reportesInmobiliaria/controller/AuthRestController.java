package cl.fullstackjava.reportesInmobiliaria.controller;

import cl.fullstackjava.reportesInmobiliaria.model.dto.AuthRequest;
import cl.fullstackjava.reportesInmobiliaria.model.dto.AuthResponse;
import cl.fullstackjava.reportesInmobiliaria.model.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthRestController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String jwt = jwtService.generateToken(userDetails);
         return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);
    }
}
