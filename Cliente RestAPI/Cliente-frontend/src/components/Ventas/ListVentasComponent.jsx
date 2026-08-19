import React, { useEffect, useState } from "react";
import { listVentas, deleteVenta, verTicket, buscarPorFecha } from "../../service/VentaService";
import { useNavigate } from "react-router-dom";

export const ListVentaComponent = () => {
  const [ventas, setVentas] = useState([]);
  const [fechaCompra, setFechaCompra] = useState("");
  const navegar = useNavigate();

  // Cargar todas las ventas al montar el componente
  useEffect(() => {
    cargarVentas();
  }, []);

  const cargarVentas = () => {
    listVentas()
      .then((response) => {
        setVentas(response.data);
      })
      .catch((error) => console.error("Error al cargar ventas:", error));
  };

  // Navegar a crear nueva venta
  const nuevaVenta = () => {
    navegar("/ventas/crear");
  };

  // Navegar a editar venta
  const editarVenta = (idVenta) => {
    navegar(`/ventas/editar/${idVenta}`);
  };

  // Ver detalles de venta
  const verDetalles = (idVenta) => {
    navegar(`/ventas/detalle/${idVenta}`);
  };
  //Ver y Crear Ticket
  const verTicketVenta = async (idVenta) => {
  try {
    const response = await verTicket(idVenta);
    const blob = new Blob([response.data], { type: "application/pdf" });

    const url = window.URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = `ticket_venta_${idVenta}.pdf`;
    link.click();

    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error("Error al generar el Ticket...", error);
    alert("No se pudo generar el Ticket de la venta");
  }
};


  // Eliminar venta
  const eliminarVenta = (idVenta) => {
    if (!window.confirm("¿Deseas eliminar esta venta?")) return;
    deleteVenta(idVenta)
      .then(() => {
        setVentas(ventas.filter((v) => v.idVenta !== idVenta));
        console.log("Venta eliminada correctamente");
      })
      .catch((error) => console.error("Error al eliminar venta:", error));
  };

  const BuscarPorFecha = async () => {
        if (!fechaCompra) return;
        try {
            const response = await buscarPorFecha(fechaCompra);
            setVentas(response.data);
        } catch (error) {
            console.error("Error al buscar ventas por fecha...", error);
        }
        console.log("Fecha enviada al backend:", fechaCompra);
    };
  console.log("Ventas recibidos desde el backend: ", ventas);
  console.log("Ventas recibidos desde el backend: ", JSON.stringify(ventas, null, 1));


  return (
    <div className="ventas">
      <div className="ventas-header">
        <div className="ventas-heeader-izq">
          <h2>Lista de Ventas</h2>
      <div  className="ventas-botones">
        <button className="btn btn-primary me-2" onClick={nuevaVenta}>
          Nueva Venta
        </button>
        </div>
      </div>
      <div className="ventas-busqueda">
        <h2>Buscar Ventas por Fecha</h2>
            <input type="date" value={fechaCompra} onChange={(e) => setFechaCompra(e.target.value)} />
            <button onClick={BuscarPorFecha}>Buscar</button>
            <button onClick={cargarVentas}>Ver Todos</button>
      </div>
      </div>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>ID Venta</th>
            <th>ID Reserva</th>
            <th>Cliente</th>
            <th>Empleado</th>
            <th>Fecha Compra</th>
            <th>Total</th>
            <th>Acciones</th>
          </tr>
        </thead>
         <tbody>
          {ventas.length > 0 ? (
            ventas.map((venta) => (
              <tr key={venta.idVenta}>
                <td>{venta.idVenta}</td>
                <td>{venta.idReserva ? venta.idReserva: "Sin Reserva"}</td>
                <td>{venta.nombreCliente || "Desconocido"}</td>
                <td>{venta.nombreEmpleado || "Desconocido"}</td>
                <td>
                  {venta.fechaCompra
                    ? new Date(venta.fechaCompra).toLocaleString()
                    : "-"}
                </td>
                <td>{venta.total != null ? venta.total.toFixed(2) : "0.00"}</td>
                <td>
                  <button
                    className="btn btn-info me-2"
                    onClick={() => verDetalles(venta.idVenta)}
                  >
                    Detalles
                  </button>
                  <button
                    className="btn btn-warning me-2"
                    onClick={() => editarVenta(venta.idVenta)}
                  >
                    Editar
                  </button>
                  <button onClick={() => verTicketVenta(venta.idVenta)}>
                   Ticket
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => eliminarVenta(venta.idVenta)}
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={6} className="text-center">
                No hay ventas registradas.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};
