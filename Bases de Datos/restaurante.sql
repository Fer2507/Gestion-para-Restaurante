CREATE DATABASE restaurante;
USE restaurante;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100)
);
ALTER TABLE cliente
ADD COLUMN usuario_id INT;

SELECT * FROM Cliente;

UPDATE cliente
SET usuario_id = 5
WHERE id = 1;


