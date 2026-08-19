import React, { useEffect, useState } from "react";
import { getCliente } from "../../service/ClienteService";
import { useParams, useNavigate } from "react-router-dom";

export const ClienteDetalleComponent = () => {
  const { id } = useParams();
  const navegar = useNavigate();

  const [cliente, setCliente] = useState({
    nombreCliente: "",
    telefonoCliente: "",
    correoCliente: "",
    clave:"",
    idUsuario: " ",
  });

  useEffect(() => {
    if (id) {
      getCliente(id)
        .then((response) => setCliente(response.data))
        .catch((error) => console.error(error));
    }
  }, [id]);

  return (
   <div className="clientes">
      <h2 className="text-center">Detalle del Cliente</h2>
      <div className="cliente-detalle-card">
        <p><strong>Nombre:</strong> {cliente.nombreCliente}</p>
        <p><strong>Teléfono:</strong> {cliente.telefonoCliente}</p>
        <p><strong>Email:</strong> {cliente.correoCliente}</p>
        <p><strong>Clave:</strong> {cliente.clave}</p>
        <p><strong>Id Usuario:</strong> {cliente.idUsuario}</p>
        <button className="btn btn-secondary" onClick={() => navegar("/cliente/lista")}>
          Volver a la Lista
        </button>
      </div>
    </div>
  );
};
