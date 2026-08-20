-- Crear base de datos
CREATE DATABASE fonda;
USE fonda;

-- Tabla Tipo (platillo, bebida, postre)
CREATE TABLE Tipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
);
SELECT * FROM Tipo;
-- Tabla Producto (platillo/bebida)
CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL,
    id_Tipo INT,
    FOREIGN KEY (id_Tipo) REFERENCES Tipo(id)
);
SELECT * FROM Producto;
ALTER TABLE Producto ADD COLUMN activo BOOLEAN DEFAULT TRUE;
ALTER TABLE Producto CHANGE nombreFoto nombre_foto VARCHAR(255);

-- Ventas / Compras
CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,        -- Referencia a Taller.cliente
    fecha_compra DATETIME NOT NULL DEFAULT NOW(),
    total DECIMAL(10,2) NOT NULL
);
SELECT * FROM venta;

ALTER TABLE venta
ADD COLUMN id_Reserva INT NULL;

CREATE TABLE Venta_Detalle (
	id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,   
    id_producto INT NOT NULL,          
    cantidad INT DEFAULT 1,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,       -- cantidad * precio_unitario
    FOREIGN KEY (id_venta) REFERENCES Venta(id_venta),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);
SELECT * FROM Venta_Detalle;
