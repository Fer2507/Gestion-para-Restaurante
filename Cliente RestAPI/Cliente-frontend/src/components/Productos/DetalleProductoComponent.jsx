import React, { useEffect, useState } from "react";
import { getProducto } from "../../service/ProductoService";
import { useParams, useNavigate, useLocation } from "react-router-dom";

export const DetalleProductoComponent = () => {
  const { idPro } = useParams();
  const [producto, setProducto] = useState(null);
  const navegar = useNavigate();
  const location = useLocation();
  const origen = location.state?.origen || "home";

  useEffect(() => {
    if (idPro) {
      getProducto(idPro)
        .then((response) => setProducto(response.data))
        .catch((error) => console.error("Error al cargar el producto:", error));
    }
  }, [idPro]);

  const volver = () => {
    if (origen === "home") navegar("/");
    else if (origen === "listapro") navegar("/producto/listapro");
  };

  if (!producto) return <p>Cargando producto...</p>;

  const imagenUrl = producto.nombreFoto ? `http://localhost:7071/api/producto/img/${producto.nombreFoto}`
  : "/no_imagen.png";
  return (
     <div className="productos">
            <h2>Detalle del Producto</h2>
            <div className="producto-detalle-card">
                <h3>{producto.nombreProducto}</h3>
                <img src={imagenUrl} alt={producto.nombreFoto} width="200"
                style={{borderRadius: "10px", marginBottom: "10px"}}/>
                <p><strong>Descripción:</strong> {producto.descripcionProducto}</p>
                <p><strong>Precio:</strong> ${producto.precioProducto}</p>
                <p><strong>Tipo:</strong> {producto.idTipo}</p>
                <button onClick={volver}>Volver</button>
            </div>
        </div>
  );
};