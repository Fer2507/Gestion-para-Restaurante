import React, { useEffect, useState } from "react";
import { crearCliente, getCliente, updateCliente } from "../../service/ClienteService";
import { useNavigate, useParams } from "react-router-dom";

export const ClienteComponent = () => {

  const [nombreCliente, setNombreCliente] = useState("");
  const [telefonoCliente, setTelefonoCliente] = useState("");
  const [correoCliente, setCorreoCliente] = useState("");
  const [clave, setClave] = useState("");

  const { id } = useParams();
  const navegar = useNavigate();

  const validaForm = () => {
    if (!nombreCliente.trim()) return alert("El nombre es obligatorio");
    if (!correoCliente.trim()) return alert("El email es obligatorio");
    if (!telefonoCliente.trim()) return alert("El teléfono es obligatorio");

    // La clave solo se pide al crear
    if (!id && !clave.trim()) return alert("La contraseña es obligatoria");

    return true;
  };

  const saveCliente = (e) => {
    e.preventDefault();

    if (!validaForm()) return;

    const cliente = {
      nombreCliente,
      telefonoCliente,
      correoCliente,
      clave
    };

    const token = localStorage.getItem("token");
    const headers = token ? { Authorization: `Bearer ${token}` } : {};

    if (id) {
      // Editar
      updateCliente(id, cliente, { headers })
        .then(() => navegar("/cliente/lista"))
        .catch((error) => console.error(error));

    } else {
      // Crear (público o logueado)
      crearCliente(cliente, { headers })
        .then(() => {
          if (!token) navegar("/"); // público
          else navegar("/cliente/lista"); // con sesión
        })
        .catch((error) => console.error("Error al guardar el cliente:", error));
    }
  };

  useEffect(() => {
    if (id) {
      getCliente(id)
        .then((response) => {
          setNombreCliente(response.data.nombreCliente);
          setTelefonoCliente(response.data.telefonoCliente);
          setCorreoCliente(response.data.correoCliente);

          // Si ya tiene usuario asociado,
          // puedes dejar clave vacía para no modificarla
          setClave("");
        })
        .catch((error) => console.error(error));
    }
  }, [id]);

  return (
    <div className="clientes">
      <h2 className="text-center">{id ? "Modificar Cliente" : "Nuevo Cliente"}</h2>

      <form onSubmit={saveCliente}>
        <p>
          <label>Nombre:<br />
            <input
              type="text"
              value={nombreCliente}
              onChange={(e) => setNombreCliente(e.target.value)}
              required
            />
          </label>
        </p>

        <p>
          <label>Teléfono:<br />
            <input
              type="tel"
              value={telefonoCliente}
              onChange={(e) => setTelefonoCliente(e.target.value)}
              required
            />
          </label>
        </p>

        <p>
          <label>Email:<br />
            <input
              type="email"
              value={correoCliente}
              onChange={(e) => setCorreoCliente(e.target.value)}
              required
            />
          </label>
        </p>

        <p>
          <label>Contraseña:<br />
            <input
              type="password"
              value={clave}
              onChange={(e) => setClave(e.target.value)}
              placeholder={id ? "Deja vacío si no deseas cambiarla" : "Ingresa una contraseña"}
            />
          </label>
        </p>

        <button type="submit" className="btn btn-success">Guardar</button>
        <button
          type="button"
          className="btn btn-secondary ms-2"
          onClick={() => navegar("/cliente/lista")}
        >
          Cancelar
        </button>
      </form>
    </div>
  );
};
