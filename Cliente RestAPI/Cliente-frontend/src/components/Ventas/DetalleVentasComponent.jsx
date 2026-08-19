import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getVenta } from "../../service/VentaService";

export const DetalleVentasComponent = () => {
  const { idVenta } = useParams();
  const [venta, setVenta] = useState(null);
  const navegar = useNavigate();

  useEffect(() => {
    getVenta(idVenta)
      .then(res => setVenta(res.data))
      .catch(err => console.error(err));
  }, [idVenta]);

  if (!venta) return <div>Cargando...</div>;

  return (
    <div className="ventas">
      <h2>Detalle de la Venta #{venta.idVenta}</h2>
      
      <div>
        <p><strong>IDReserva:</strong>{venta.idReserva ? venta.idReserva : "Sin reserva"}</p>
        <p><strong>Cliente:</strong> {venta.nombreCliente}</p>
        <p><strong>Empleado:</strong> {venta.nombreEmpleado || "Desconocido"}</p>
        <p><strong>Fecha:</strong> {new Date(venta.fechaCompra).toLocaleString()}</p>
      </div>

      <table>
        <thead>
          <tr>
            <th>ID Producto</th>
            <th>Nombre</th>
            <th>Cantidad</th>
            <th>Precio Unitario</th>
            <th>Subtotal</th>
          </tr>
        </thead>
        <tbody>
          {venta.detalles.map((d, index) => (
            <tr key={index} style={{ backgroundColor: index % 2 === 0 ? "#f9f9f9" : "#fff" }}>
              <td>{d.idProducto}</td>
              <td>{d.producto?.nombre || "Sin nombre"}</td>
              <td>{d.cantidad}</td>
              <td>${d.producto?.precio?.toFixed(2) || 0}</td>
              <td>${d.subtotal.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
        <tfoot>
          <tr>
            <td >Total:</td>
            <td>${venta.total.toFixed(2)}</td>
          </tr>
        </tfoot>
      </table>

        <button
          className="btn btn-info me-2"
          onClick={() => {
            if(venta.idReserva) {
              navegar(`/reservas/detalle/${venta.idReserva}`);
            }else{
              navegar("/ventas/listaVen");
            }
          }}
          >
          Volver a la Lista
          </button>
    </div>
  );
};
