# Notas sobre la seguridad en RealEstateReportsAPI

Este documento explica cómo se implementó la seguridad en la API REST 
**RealEstateReportsAPI**, usando **Spring Security** en combinación con **JWT** 
para la autenticación sin estado (stateless).

## 1. Dependencias necesarias
El proyecto utiliza las siguientes dependencias en el archivo `pom.xml`:
- **spring-boot-starter-security**: Proporciona la infraestructura básica para 
la seguridad en Spring Boot.
- **jjwt-api**: Librería utilizada para generar y validar tokens **JWT**.
- **spring-security-test**: Para pruebas de seguridad.

## 2. Spring Security y JWT
El proyecto utiliza **Spring Security** para proteger las rutas y gestionar los 
roles y permisos. Al mismo tiempo, usa **JWT** como mecanismo de autenticación 
sin estado. Estos son los pasos clave:
- **Spring Security**: Configura las rutas protegidas y gestiona la autorización 
mediante roles (por ejemplo, `hasRole('ADMIN')`).
- **JWT**: Se encarga de la autenticación sin estado, reemplazando el manejo de 
sesiones por el uso de tokens.

## 3. Clases clave
### SecurityConfig.java
- Configura las rutas protegidas de la API.
- Usa `SessionCreationPolicy.STATELESS` para que no se mantengan sesiones, y JWT 
sea utilizado en cada solicitud.
- Añade el filtro `JwtFilterRequest` para validar el token JWT antes de permitir 
el acceso a las rutas.

### JwtFilterRequest.java
- Extrae y valida el token JWT de cada solicitud HTTP.
- Establece la autenticación en el contexto de **Spring Security** si el token 
es válido.

### JwtService.java
- Se encarga de generar y validar los tokens JWT.
- Usa una clave secreta generada internamente para firmar los tokens con el 
algoritmo **HS256**.
- El token tiene una validez de 10 horas (configurable).

### UserDetailsImpl.java
- Implementa la interfaz `UserDetails` de Spring Security.
- Carga los roles y permisos de los usuarios desde la base de datos.

## 4. Proceso de autenticación y autorización
1. El cliente envía sus credenciales (usuario y contraseña) a `/api/auth/login`.
2. La API genera un token JWT si las credenciales son correctas y lo devuelve al 
cliente.
3. En cada solicitud posterior, el cliente incluye el token JWT en el encabezado 
`Authorization` con el formato `Bearer <token>`.
4. **JwtFilterRequest** valida el token y, si es válido, establece la 
autenticación en el contexto de seguridad de **Spring Security**.
5. **Spring Security** verifica los roles y permisos del usuario antes de 
conceder acceso a las rutas protegidas.

## 5. Configuración adicional
- **Deshabilitar CSRF**: En APIs REST, no es necesario habilitar **CSRF** cuando 
se usa **JWT**.
- **Política sin estado**: `SessionCreationPolicy.STATELESS` asegura que no se 
mantengan sesiones entre solicitudes, ya que se usa JWT para cada una.

---

Este archivo sirve como referencia interna sobre cómo está configurada la 
seguridad en este proyecto.
