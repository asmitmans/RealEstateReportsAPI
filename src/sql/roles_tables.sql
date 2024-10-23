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