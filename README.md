# RealEstateReportsAPI - API para la Gestión de Proyectos Inmobiliarios

## Descripción del Proyecto
RealEstateReportsAPI es una API RESTful desarrollada en Java utilizando Spring
Boot y JWT para la autenticación. Esta API permite gestionar proyectos
inmobiliarios y acceder a reportes de los mismos. Los roles de usuario
determinan los permisos y accesos, permitiendo que solo ciertos roles accedan
a funcionalidades específicas. Los scripts SQL necesarios para crear las
tablas y datos de prueba están incluidos en la carpeta `src/sql/`.


## Características
- **Autenticación con JWT**: Los usuarios deben autenticarse con JWT para acceder
  a las rutas protegidas.
- **Gestión de Proyectos Inmobiliarios**: CRUD de proyectos.
- **Roles y Permisos**: Los roles de usuario administran los permisos de acceso
  a las distintas funcionalidades de la API.
- **API RESTful**: La API sigue los principios REST y utiliza PostgreSQL como
  base de datos.


## Configuración de la Base de Datos
Para que la API funcione correctamente, es necesario configurar la base de
datos en el archivo `application.properties`, que se encuentra en
`src/main/resources`. Cambia los siguientes valores según tu configuración:

- **Nombre de la base de datos**: `spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_basedatos`
- **Usuario de la base de datos**: `spring.datasource.username=usuario_bd`
- **Contraseña del usuario**: `spring.datasource.password=password_usuario_bd`

Luego, ejecuta los scripts SQL que se encuentran en `src/sql/` para crear las
tablas necesarias:

1. `roles_tables.sql`: Crea las tablas de roles de usuario.
2. `projects_table.sql`: Crea las tablas relacionadas con los proyectos inmobiliarios.


## Uso de la API
### Autenticación
- Para autenticarse, los usuarios deben enviar sus credenciales a `/auth/login`.
  Esto devolverá un token JWT, que se debe incluir en los encabezados de las
  peticiones para acceder a las rutas protegidas.

### Endpoints principales
- **Proyectos**:
  - `GET /api/projects`: Listar todos los proyectos (solo para usuarios autenticados).
  - `POST /api/projects`: Crear un nuevo proyecto (solo para administradores).
  - `PUT /api/projects/{id}`: Actualizar un proyecto existente.
  - `DELETE /api/projects/{id}`: Eliminar un proyecto existente.

### Roles y Permisos
Los usuarios pueden tener uno de los siguientes roles:
- **Admin**: Tiene acceso a todas las funcionalidades, incluyendo la creación
  y eliminación de proyectos.
- **User**: Solo puede visualizar los proyectos.


## Instalación y Uso
1. Clona este repositorio:
  ```bash
  git clone https://github.com/asmitmans/RealEstateReportsAPI.git
  ```
2. Configura la base de datos siguiendo los pasos de la sección anterior.
3. Ejecuta los scripts SQL para crear las tablas.
4. Importa el proyecto en **IntelliJ** o cualquier IDE compatible con **Spring Boot**.
5. Ejecuta el proyecto y accede a:
  ```bash
  http://localhost:8080/
  ```


## Autor
Este proyecto fue desarrollado por [Agustin Smitmans](https://github.com/asmitmans) 
como parte de un desafío técnico en Spring Boot. Puedes contactarme a través de 
mi perfil en GitHub para más información.
