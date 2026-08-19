import React, { useEffect, useState } from "react";
import { getUsuario } from "../../service/UsuarioService";
import { useParams, useNavigate } from "react-router-dom";

export const UsuarioDetalleComponent = () => {

  const { idUsuario  } = useParams();   // asegúrate que la ruta usa :id
  const navegar = useNavigate();

  const [usuario, setUsuario] = useState(
    {username: "",
    email: "",
    rol: "",
    estado: "",
    password: "",
});
  const [error, setError] = useState("");

  useEffect(() => {
  if (idUsuario) {
    getUsuario(idUsuario)
      .then((response) => {
        console.log("Datos recibidos:", response.data);
        setUsuario(response.data);
      })
      .catch(() => setError("No se encontró el usuario solicitado"));
  }
}, [idUsuario]);


  if (error) {
    return (
      <div className="usuarios">
        <h2 className="text-center">Error</h2>
        <p className="text-danger text-center">{error}</p>
        <button className="btn btn-secondary" onClick={() => navegar("/usuarios/lista")}>
          Volver a la Lista
        </button>
      </div>
    );
  }

  return (
    <div className="usuarios-container">
      <h2 className="text-center">Detalle del Usuario</h2>
      <div className="usuario-detalle-card">
        <p><strong>ID:</strong> {usuario.idUsuario}</p>
        <p><strong>Username:</strong> {usuario.username}</p>
        <p><strong>Email:</strong> {usuario.email}</p>
        <p><strong>Rol:</strong> {usuario.rol}</p>
        <p><strong>Estado:</strong> {usuario.estado ? "Activo" : "Inactivo"}</p>
        <p><strong>Contraseña (Hash):</strong> {usuario.password}</p>
        <button className="btn-usuario" onClick={() => navegar("/usuarios")}>
          Volver a la Lista
        </button>
      </div>
    </div>
  );
};