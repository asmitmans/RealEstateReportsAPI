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