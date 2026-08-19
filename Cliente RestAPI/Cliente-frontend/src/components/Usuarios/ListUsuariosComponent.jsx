import React, { useEffect, useState } from "react";
import { listUsuarios } from "../../service/UsuarioService";
import { useNavigate } from "react-router-dom";

export const ListUsuariosComponent = () => {
  const [usuarios, setUsuarios] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    cargarUsuarios();
  }, []);

  const cargarUsuarios = () => {
    listUsuarios()
      .then((resp) => {
        console.log("Usuarios recibidos:", resp.data);
        setUsuarios(resp.data);
      })
      .catch((err) => console.error("Error cargando usuarios:", err));
  };

  function nuevoUsuario() {
    navigate("/usuarios/crear");
  }

  function editarUsuario(idUsuario) {
    navigate(`/usuarios/editar/${idUsuario}`);
  }

  return (
   <div className="usuarios-container">
  <div className="usuarios-header">
    <h2>Lista de Usuarios</h2>
    <button className="btn-usuario" onClick={nuevoUsuario}>
      Nuevo Usuario
    </button>
  </div>

  <table className="usuarios-table">
    <thead>
      <tr>
        <th>ID</th>
        <th>Usuario</th>
        <th>Email</th>
        <th>Estado</th>
        <th>Rol</th>
        <th>Acciones</th>
      </tr>
    </thead>

    <tbody>
      {usuarios.map((u) => (
        <tr key={u.idUsuario}>
          <td>{u.idUsuario}</td>
          <td>{u.username}</td>
          <td>{u.email}</td>
          <td>{u.estado ? "Activo" : "Inactivo"}</td>
          <td>{u.rol}</td>
          <td>
            <button
              className="btn-usuario"
              onClick={() => editarUsuario(u.idUsuario)}
            >
              Editar
            </button>
            <button
            className="btn-usuario"
            onClick={() => navigate(`/usuarios/detalle/${u.idUsuario}`)} >
            Detalles
          </button>
          </td>
        </tr>
      ))}
    </tbody>
  </table>
</div>

  );
};
