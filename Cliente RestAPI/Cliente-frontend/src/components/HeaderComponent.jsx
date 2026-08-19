import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { getToken, logout } from '../service/AuthService';
import { decodeToken } from '../service/JwtDecoder';

export const HeaderComponent = () => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();
  const token = getToken();

  const [menuAbierto, setMenuAbierto] = useState(true);
  const location = useLocation(); 

  useEffect(() => {
    if (token) {
      const decode = decodeToken(token);
      if (decode) {
        setUser({
          username: decode.sub,
          rol: decode.rol,
        });
      }
    }
  }, [token]);

  useEffect(() => {
    setMenuAbierto(false);
  }, [location.pathname]);

  const handleLogout = () => {
    if (window.confirm("¿Seguro que deseas cerrar sesión?")) {
      logout();
      navigate("/logout");
    }
  };

  // Función para validar rol
  const hasRole = (requiredRole) => {
    if (!user) return false;
    return user.rol === requiredRole;  // Ajustable si usas varios roles
  };
  
  // Si no hay token → no mostrar menú
  if (!token) {
    return null;
  }
  return (
    <header className="header">
       {/* ----- Usuario logueado ----- */}
        <div className="menu-section user-info-card">
          {user ? (
            <>
              <p><strong>Usuario:</strong> {user.username}</p>
              <p><strong>Rol:</strong> {user.rol}</p>
            </>
          ) : (
            <p>No has iniciado sesión</p>
          )}
          <a href="/">Inicio</a>
          <button onClick={handleLogout} className="logout">Cerrar Sesión</button>
        </div>
      <nav className={`navbar ${menuAbierto ? "abierto" : "cerrado"}`}>
        <div className="menu-section">
          <h2>Cliente</h2>
          <a href="/cliente/lista">Lista</a>
          <a href="/cliente/crear">Nuevo</a>
        </div>

        <div className="menu-section">
          <h2>Productos</h2>
          <a href="/producto/listapro">Lista</a>
          <a href="/producto/crear">Nuevo</a>
        </div>

        <div className="menu-section">
          <h2>Tipos</h2>
          <a href="/tipo/listati">Lista</a>
          <a href="/tipo/crear">Nuevo</a>
        </div>

        <div className="menu-section">
          <h2>Ventas</h2>
          <a href="/ventas/listaVen">Lista</a>
          <a href="/ventas/crear">Nuevo</a>
        </div>

        <div className="menu-section">
          <h2>Empleados</h2>
          <a href="/empleado/listaEmp">Lista</a>
          <a href="/empleado/crear">Nuevo</a>
          <a href="/ventas/mesero/mis-ventas">Atiendo</a>
        </div>

        <div className="menu-section">
          <h2>Mesas</h2>
          <a href="/mesa/listMesa">Lista</a>
          <a href="/mesa/nueva">Nuevo</a>
        </div>

        <div className="menu-section">
          <h2>Reservas</h2>
          <a href="/reservas/listReserva">Lista</a>
          <a href="/reservas/crear">Nueva</a>
        </div>

        <div className="menu-section">
          <h2>Usuarios</h2>
          <a href="/usuarios">Lista</a>
          <a href="/usuarios/crear">Nueva</a>
        </div>
      </nav>
      <button className="menu-toggle" onClick={() => setMenuAbierto(!menuAbierto)} >
            {menuAbierto ? "Ocultar menú" : "Mostrar menú"} </button>
    </header>
  );
};
