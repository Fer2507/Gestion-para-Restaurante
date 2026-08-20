 CREATE DATABASE seguridad;
 USE seguridad;
 
CREATE TABLE Usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150),
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    rol_id BIGINT,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES roles(id_rol)
);
SELECT * FROM Usuarios;

CREATE TABLE tokens (
    id_token BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(500) NOT NULL,
    id_usuario BIGINT NOT NULL,
    revocado BOOLEAN DEFAULT FALSE,
    expira DATETIME,
    CONSTRAINT fk_token_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);
SELECT * FROM tokens;

CREATE TABLE roles (
    id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(150)
);
SELECT * FROM roles;

CREATE TABLE permisos (
    id_permiso BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(150)
);
SELECT * FROM permisos;

CREATE TABLE rol_permisos (
    id_rol BIGINT NOT NULL,
    id_permiso BIGINT NOT NULL,
    PRIMARY KEY (id_rol, id_permiso),
    CONSTRAINT fk_rol_permiso_rol FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE CASCADE,
    CONSTRAINT fk_rol_permiso_permiso FOREIGN KEY (id_permiso) REFERENCES permisos(id_permiso) ON DELETE CASCADE
);
SELECT * FROM rol_permisos;

INSERT INTO roles (nombre, descripcion) VALUES 
('COCINERO', 'Empleado encargado de cocinar'),
('CLIENTE', 'Usuario que puede hacer reservaciones y pedidos'),
('MESERO', 'Empleado encargado de atender pedidos'),
('CAJERO', 'Empleado encargado de ventas y clientes'),
('ADMINISTRADOR', 'Usuario con acceso completo a todos los microservicios'),
('SUPERVISOR', 'Usuario con permisos parciales de administración de productos, clientes y empleados');

INSERT INTO permisos (nombre, descripcion) VALUES 
('LISTAR_EMPLEADOS', 'Permite lisatar a os empleados'),
('GESTIONAR_Usuarios', 'Permite gestionar Usuarios'),
('GESTIONAR_EMPLEADOS', 'Permite gestionar empleados'),
('GESTIONAR_MESAS', 'Permite gestionar mesas'),
('GESTIONAR_TIPOS', 'Permite gestionar tipos'),
('CREAR_RESERVA', 'Permite crear una reservación'),
('VISTA_PRINCIPAL', 'Permite ver la lista de productos para el cliente'),
('REALIZAR_PEDIDOS', 'Permite realizar pedidos'),
('DAR_BAJA_CLIENTE', 'Permite darse de baja como cliente'),
('GESTIONAR_PEDIDOS', 'Permite gestionar pedidos de clientes'),
('GESTIONAR_RESERVAS', 'Permite gestionar reservaciones'),
('REALIZAR_VENTAS', 'Permite realizar ventas'),
('LISTAR_CLIENTES', 'Permite ver la lista de clientes'),
('GESTIONAR_PRODUCTOS', 'Permite gestionar productos'),
('GESTIONAR_EMPLEADOS', 'Permite gestionar empleados'),
('GESTIONAR_CLIENTES', 'Permite gestionar clientes');


-- CLIENTE
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
((SELECT id_rol FROM roles WHERE nombre='CLIENTE'), (SELECT id_permiso FROM permisos WHERE nombre='CREAR_RESERVA')),
((SELECT id_rol FROM roles WHERE nombre='CLIENTE'), (SELECT id_permiso FROM permisos WHERE nombre='VISTA_PRINCIPAL')),
((SELECT id_rol FROM roles WHERE nombre='CLIENTE'), (SELECT id_permiso FROM permisos WHERE nombre='DAR_BAJA_CLIENTE'));

-- MESERO
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
((SELECT id_rol FROM roles WHERE nombre='MESERO'), (SELECT id_permiso FROM permisos WHERE nombre='LISTAR_EMPLEADOS')),
((SELECT id_rol FROM roles WHERE nombre='MESERO'), (SELECT id_permiso FROM permisos WHERE nombre='REALIZAR_VENTAS')),
((SELECT id_rol FROM roles WHERE nombre='MESERO'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_RESERVAS')),
((SELECT id_rol FROM roles WHERE nombre='MESERO'), (SELECT id_permiso FROM permisos WHERE nombre='LISTAR_CLIENTES')),
((SELECT id_rol FROM roles WHERE nombre='MESERO'), (SELECT id_permiso FROM permisos WHERE nombre='REALIZAR_PEDIDOS')),
((SELECT id_rol FROM roles WHERE nombre='MESERO'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_MESAS')),
((SELECT id_rol FROM roles WHERE nombre='MESERO'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_PEDIDOS'));

-- CAJERO
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
((SELECT id_rol FROM roles WHERE nombre='CAJERO'), (SELECT id_permiso FROM permisos WHERE nombre='LISTAR_EMPLEADOS')),
((SELECT id_rol FROM roles WHERE nombre='CAJERO'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_MESAS')),
((SELECT id_rol FROM roles WHERE nombre='CAJERO'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_RESERVAS')),
((SELECT id_rol FROM roles WHERE nombre='CAJERO'), (SELECT id_permiso FROM permisos WHERE nombre='REALIZAR_VENTAS')),
((SELECT id_rol FROM roles WHERE nombre='CAJERO'), (SELECT id_permiso FROM permisos WHERE nombre='LISTAR_CLIENTES')),
((SELECT id_rol FROM roles WHERE nombre='CAJERO'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_CLIENTES'));

-- ADMINISTRADOR -> todos los permisos
INSERT INTO rol_permisos (id_rol, id_permiso)
SELECT r.id_rol, p.id_permiso FROM roles r, permisos p WHERE r.nombre='ADMINISTRADOR';

INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
((SELECT id_rol FROM roles WHERE nombre='ADMINISTRADOR'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_TIPOS')),
((SELECT id_rol FROM roles WHERE nombre='ADMINISTRADOR'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_MESAS'));
-- SUPERVISOR
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
((SELECT id_rol FROM roles WHERE nombre='SUPERVISOR'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_TIPOS')),
((SELECT id_rol FROM roles WHERE nombre='SUPERVISOR'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_PRODUCTOS')),
((SELECT id_rol FROM roles WHERE nombre='SUPERVISOR'), (SELECT id_permiso FROM permisos WHERE nombre='LISTAR_CLIENTES')),
((SELECT id_rol FROM roles WHERE nombre='SUPERVISOR'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_EMPLEADOS')),
((SELECT id_rol FROM roles WHERE nombre='SUPERVISOR'), (SELECT id_permiso FROM permisos WHERE nombre='GESTIONAR_CLIENTES'));

