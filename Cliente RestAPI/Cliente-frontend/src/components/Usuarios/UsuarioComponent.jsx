import React, { useEffect, useState } from "react";
import { crearUsuario, updateUsuario, getUsuario } from "../../service/UsuarioService";
import { useNavigate, useParams } from "react-router-dom";

export const UsuarioComponent = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");   // Nueva contraseña (opcional)
  const [email, setEmail] = useState("");
  const [estado, setEstado] = useState(true);
  const [rol, setRol] = useState("");

  const { idUsuario } = useParams();
  const navegar = useNavigate();

  // ------------- Handlers -------------
  const actualizarUsername = (e) => setUsername(e.target.value);
  const actualizarPassword = (e) => setPassword(e.target.value);
  const actualizarEmail = (e) => setEmail(e.target.value);
  const actualizarEstado = (e) => setEstado(e.target.checked);
  const actualizarRol = (e) => setRol(e.target.value);

  // ------------- Validación -------------
  const validarForm = () => {
    if (!username.trim()) { 
      alert("El nombre de usuario es obligatorio"); return false; 
    }
    if (!email.trim()) { 
      alert("El email es obligatorio"); return false; 
    }
    if (!rol.trim()) { 
      alert("Debe seleccionar un rol"); return false; 
    }
    return true;
  };

  // ------------- Guardar -------------
  const saveUsuario = (e) => {
    e.preventDefault();
    const usuario = { username, password, email, estado, rol };
    if (!validarForm()) return;

    // Si NO hay nuevo password en edición → NO mandar password al backend
    let data = { username, email, estado, rol };
    if (!idUsuario || password.trim() !== "") {
      data.password = password;
    }
    console.log("ID recibido:", idUsuario);
  console.log("Datos enviados a update:", data);

    if (idUsuario) {
      updateUsuario(idUsuario, data)
        .then(() => navegar("/usuarios"))
        .catch((err) => console.error(err));

    } else {
      crearUsuario(data)
        .then(() => navegar("/usuarios"))
        .catch((err) => console.error(err));
    }
  };

  // ------------- Cargar datos en edición -------------
  useEffect(() => {
    if (idUsuario) {
      getUsuario(idUsuario).then((response) => {
        const u = response.data;
        setUsername(u.username || "");
        setEmail(u.email || "");
        setEstado(u.estado === true);
        setRol(u.rol || "");
        setPassword(""); // no se muestra la contraseña real
      });
    }
  }, [idUsuario]);

  const titulo = idUsuario ? "Modificar Usuario" : "Nuevo Usuario";

  return (
    <div className="usuarios-container">
  <h2 className="text-center">{titulo}</h2>

  <form className="usuario-form" onSubmit={saveUsuario}>
        
        <p>
          <label>Usuario:<br />
            <input type="text" value={username} onChange={actualizarUsername} required />
          </label>
        </p>

        <p>
          <label>Email:<br />
            <input type="email" value={email} onChange={actualizarEmail} required />
          </label>
        </p>

        <p>
          <label>Contraseña:<br />
            <input
              type="password"
              value={password}
              onChange={actualizarPassword}
              placeholder={idUsuario ? "Deja vacío para no cambiar contraseña" : "Ingresa contraseña"}
            />
          </label>
        </p>

        <p>
          <label>Estado:&nbsp;
            <input type="checkbox" checked={estado} onChange={actualizarEstado} />
            Activo
          </label>
        </p>

        <p>
          <label>Rol:<br />
            <select value={rol} onChange={actualizarRol} required>
              <option value="">-- Seleccionar Rol --</option>
              <option value="ADMINISTRADOR">Administrador</option>
              <option value="SUPERVISOR">Supervisor</option>
              <option value="MESERO">Mesero</option>
              <option value="COCINERO">Cocinero</option>
              <option value="CLIENTE">Cliente</option>
            </select>
          </label>
        </p>

       <button type="submit" className="btn-usuario">Guardar</button>
        <button type="button" className="btn-usuario" onClick={() => navegar("/usuarios")}>
        Cancelar
        </button>


      </form>
    </div>
  );
};
