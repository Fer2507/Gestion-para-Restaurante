CREATE DATABASE reserva;
Use reserva;
 
 CREATE TABLE Empleado(
	id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_Emp VARCHAR(100) NOT NULL,
    puesto VARCHAR(20),
    clave VARCHAR(20),
     usuario_id INT
    );
ALTER TABLE Empleado
ADD COLUMN email VARCHAR(150);
SELECT * FROM Empleado;

CREATE TABLE Mesa(
	id_mesa INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    capacidad INT NOT NULL,
    ubicacion VARCHAR(100) NOT NULL
)

ALTER TABLE Mesa
ADD COLUMN estado VARCHAR(50) DEFAULT 'Disponible';
-- Ejemplo: cargar mesas iniciales
INSERT INTO Mesa (numero, capacidad, ubicacion) VALUES
(1, 4, 'Terraza'),
(2, 2, 'Interior'),
(3, 6, 'Salón principal'),
(4, 4, 'Terraza'),
(5, 2, 'Interior');

SELECT * FROM Mesa;

CREATE TABLE Reservar (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
	id_mesa INT NOT NULL,
    id_cliente INT NOT NULL,   -- ID del cliente (FK lógica, no física)
    id_venta INT NULL, -- este sigue viniendo de FondaMS
    estatus VARCHAR(80) NOT NULL DEFAULT 'Pendiente',
    FOREIGN KEY (id_mesa) REFERENCES Mesa(id_mesa),
    fecha_reserva DATETIME NOT NULL
);
SELECT * FROM Reservar;

CREATE TABLE Atender (
    id_atender INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT NOT NULL,
	FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado),
    id_venta INT NOT NULL -- este sigue viniendo de Fonda
);
SELECT * FROM Atender
