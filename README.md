# Prueba - Desarrollo de aplicaciones JEE con Spring framework
**URL**  
http://localhost:8080

**Config BD**  
src/main/resources/application.properties

**Credenciales por defecto**
- admin@mail.com / 12345
- user@mail.com / 12345

**User Roles Tables**
```sql
DROP TABLE IF EXISTS t_user_t_role;

DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;


CREATE TABLE t_user (
	user_id SERIAL PRIMARY KEY,
	username VARCHAR(100),
	password VARCHAR(500),
	first_name VARCHAR(100),
	last_name VARCHAR(100)
);

CREATE TABLE t_role (
	role_id SERIAL PRIMARY KEY,
	name VARCHAR(100)
);

CREATE TABLE t_user_t_role (
	user_id INT,
	role_id INT,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES t_user(user_id),
	FOREIGN KEY (role_id) REFERENCES t_role(role_id)
);


INSERT INTO t_role (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO t_user (username, password, first_name, last_name) VALUES
('admin@mail.com','$2a$12$ktKMhSvcRK4D9KzIFzljquwrVpUXNgkie2kaBrciVh9He4Y1E9mjK','Juan','Perez'),
('user@mail.com','$2a$12$ktKMhSvcRK4D9KzIFzljquwrVpUXNgkie2kaBrciVh9He4Y1E9mjK','Pedro','Gonzales');

INSERT INTO t_user_t_role (user_id, role_id) VALUES
((SELECT user_id FROM t_user WHERE username = 'admin@mail.com'), (SELECT role_id FROM t_role WHERE name = 'ROLE_ADMIN')),
((SELECT user_id FROM t_user WHERE username = 'admin@mail.com'), (SELECT role_id FROM t_role WHERE name = 'ROLE_USER')),
((SELECT user_id FROM t_user WHERE username = 'user@mail.com'), (SELECT role_id FROM t_role WHERE name = 'ROLE_USER'));
```

**Projects Table**
```sql
DROP TABLE IF EXISTS proyecto_inmobiliario;

CREATE TABLE proyecto_inmobiliario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    presupuesto BIGINT NOT NULL,
    fecha_inicio DATE,
    fecha_entrega DATE
);


INSERT INTO proyecto_inmobiliario (nombre, direccion, presupuesto, fecha_inicio, fecha_entrega) VALUES
('Torre Providencia', 'Av. Providencia 123, Providencia', 20000000000, '2024-01-15', '2025-12-20'),
('Hotel Las Palmas', 'Costanera 456, Viña del Mar', 2500000000, '2023-03-01', '2024-09-30'),
('Edificio Los Sauces', 'Av. Arturo Prat 789, Temuco', 1500000000, '2022-06-10', '2024-04-15'),
('Condominio El Arrayán', 'El Rodeo 123, Lo Barnechea', 7256000000, '2023-08-05', '2025-01-25'),
('Complejo Eco Verde', 'Barrio Eco 789, La Serena', 1800000000, '2024-02-20', '2026-06-30');
```

**Use**
- admin@mail.com / 12345  
    Can make: GET, POST, PUT, DELETE

* Auth: 
  * POST localhost:8080/api/auth
  * BODY:
```json
{
  "username": "admin@mail.com",
  "password": "12345"
}
```  

* GET: read projects
    * GET localhost:8080/api/projects
    * Auth: Bearer Token : Token : <token_generated in Auth>  
  
  
* GET: read project
    * GET localhost:8080/api/projects/id
    * Auth: Bearer Token : Token : <token_generated in Auth>
    * example id = 3  
  
  
* POST: create project
    * POST localhost:8080/api/projects
    * Auth: Bearer Token : Token : <token_generated in Auth>
    * BODY (example):
```json
{
  "name": "Edificio Los Alamos",
  "address": "Av. Arturo Prat 789, Temuco",
  "budget": 1500000000,
  "startDate": "2022-06-10",
  "completionDate": "2024-04-15"
}
```  
  
* PUT: update project
    * PUT localhost:8080/api/projects/id
    * Auth: Bearer Token : Token : <token_generated in Auth>
    * example id = 5
    * BODY (example): 
```json
{
  "name": "Edificio Los Alamos",
  "address": "Av. Arturo Prat 789, Temuco",
  "budget": 1500000000,
  "startDate": "2022-06-10",
  "completionDate": "2024-04-15"
}
```
  
* DELETE: delete project
    * DELETE localhost:8080/api/projects/id
    * Auth: Bearer Token : Token : <token_generated in Auth>
    * example id = 5  

  
  
- user@mail.com / 12345  
  Can make only: GET