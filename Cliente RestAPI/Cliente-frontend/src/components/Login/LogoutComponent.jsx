// src/components/Auth/LogoutComponent.jsx
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { logout } from "../../service/AuthService";

export const LogoutComponent = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // Ejecutar logout al entrar a la página
    logout();

    // Redirigir después de unos segundos
    const timer = setTimeout(() => {
      navigate("/");
    }, 2000);

    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div
      style={{
        textAlign: "center",
        marginTop: "100px",
        color: "#1e3a8a",
        fontFamily: "Segoe UI, Arial, sans-serif",
      }}
    >
      <h2>Cerrando sesión...</h2>
      <p>Serás redirigido al Inicio en unos segundos.</p>
    </div>
  );
};
