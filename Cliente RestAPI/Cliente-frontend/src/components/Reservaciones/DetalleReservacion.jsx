import React, {useEffect, useState} from "react";
import { getReserva } from "../../service/ReservacionService";
import { useParams, useNavigate } from "react-router-dom";

export const DetalleReservacion = () => {
    const {idReserva} = useParams();
    const [reserva, setReserva] = useState(null);
    const navegar = useNavigate();

    useEffect(() => {
        cargarReserva();
        }, [idReserva]);

    const cargarReserva = () => {
        getReserva(idReserva)
        .then((response) => {
            setReserva(response.data);
        })
        .catch((error) => console.error("Error al caragar la reserva", error));
    };
    if(!reserva) return<p>Cargando reserva...</p>;

    console.log("Detalles recibidos desde el backend: ", JSON.stringify(reserva, null, 1));


    return(
       <div className="reservas">
      <h2>Detalle de la Reserva</h2>

      <div>
        <p>
          <strong>ID Reserva: </strong> {reserva.idReserva}
        </p>
        <p>
          <strong>Cliente: </strong> {reserva.nombreCliente || "Desconocido"}
        </p>
        <p>
          <strong>Mesa: </strong> {reserva.idMesa || "-"} </p>
        <p>
          <strong>Fecha y Hora: </strong>{" "}
          {reserva.fechaReserva
            ? new Date(reserva.fechaReserva).toLocaleString()
            : "Sin fecha"}
        </p>
        <p>
          <strong>Estatus: </strong> {reserva.estatus}
        </p>
      </div>

      <h3>Pedidos asociados a esta reserva</h3>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>ID Venta</th>
            <th>Fecha</th>
            <th>Total</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {reserva.ventas && reserva.ventas.length > 0 ? (
            reserva.ventas.map((p) => (
              <tr key={p.idVenta}>
                <td>{p.idVenta}</td>
                <td>
                  {p.fechaCompra ? new Date(p.fechaCompra).toLocaleString() : "Sin fecha"}
                </td>
                <td>${p.total?.toFixed(2) || "0.00"}</td>
                <td>
                  <button
                    className="btn btn-info"
                    onClick={() => navegar(`/ventas/detalle/${p.idVenta}`)}
                  >
                    Ver Detalle
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4" className="text-center">
                No hay pedidos asociados a esta reserva.
              </td>
            </tr>
          )}
        </tbody>
      </table>

      <div className="mt-3">
        <button
          className="btn btn-success me-2"
          onClick={() => navegar(`/ventas/crear?idReserva=${r.idReserva}`)}
        >
          Añadir Pedido para esta Reserva
        </button>
        <button className="btn btn-secondary" onClick={() => navegar("/reservas/listReserva")}>
          Volver a la lista
        </button>
      </div>
    </div>
  );
};