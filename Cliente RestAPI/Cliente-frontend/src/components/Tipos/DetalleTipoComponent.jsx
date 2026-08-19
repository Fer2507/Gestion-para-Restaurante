import React, { useEffect, useState } from "react";
import { getTipo } from "../../service/TipoService";
import { useParams, useNavigate } from "react-router-dom";

export const DetalleTipoComponent = () => {
  const { idTipo } = useParams();
  const [tipo, setTipo] = useState(null);
  const navegar = useNavigate();

  useEffect(() => {
    if (idTipo) {
      getTipo(idTipo)
        .then((response) => setTipo(response.data))
        .catch((error) => console.error("Error al cargar el tipo:", error));
    }
  }, [idTipo]);

  if (!tipo) return <p>Cargando tipo...</p>;

 return (
    <div className="tipo">
            <h2>Detalle del Tipo</h2>
            <div className="tipo-detalle-card">
                <h3>{tipo.nombreTipo}</h3>
                <p><strong>Descripción:</strong> {tipo.descripcionTipo}</p>
                <button onClick={() => navegar("/tipo/listati")}>Volver</button>
            </div>
        </div>
  );
};