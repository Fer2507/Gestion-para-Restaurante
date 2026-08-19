import React, {useState, useEffect} from "react";
import { listReservas, confirmarReserva, pendienteReserva, cancelarReserva, updateReserva, buscarPorFecha } from "../../service/ReservacionService";
import { useNavigate } from "react-router-dom";

export const ListReservacionComponent = ( )=> {
    const [reservasPendientes, setReservasPendientes] = useState([]);
    const [reservasConfirmadas, setReservasConfirmadas] = useState([]);
    const [fechaReserva, setFechaReserva] = useState("");
    const navegar = useNavigate();

    useEffect(() => {
        cargarReservas();
    }, [])

    const cargarReservas = () => {
        listReservas().then((response) => {
            const todas = response.data;
            setReservasPendientes(todas.filter((r) => r.estatus == "Pendiente"));
            setReservasConfirmadas(todas.filter((r) => r.estatus == "Confirmada"));
        }).catch((error) => console.error("Error al cargar reservas: ", error));
    };
   const confirmar = async (idReserva) => {
    try {
        await confirmarReserva(idReserva);
        cargarReservas();
    } catch (error) {
        console.error("Error al confirmar:", error);

        // Mensaje que manda el backend
        const mensaje =
            error.response?.data ||
            "No se pudo confirmar la reservación porque es anterior a la fecha actual.";

        alert(mensaje);
    }
    };
    const volverPendiente = (idReserva) => {
        pendienteReserva(idReserva).then(cargarReservas);
    };
    const cancelar = (idReserva) => {
        if (!window.confirm("Desea cancelar esta Reserva???")) return;
        cancelarReserva(idReserva).then(cargarReservas);
    };
     const nuevaReserva = () => {
        navegar("/reservas/crear");
     };
     const editarReserva = (idReserva) => {
        navegar(`/reservas/editar/${idReserva}`);
     };
     const verDetalles = (idReserva) => {
        navegar(`/reservas/detalle/${idReserva}`);
     };

     const BuscarPorFecha = async () => {
    if (!fechaReserva) return;
    try {
        const response = await buscarPorFecha(fechaReserva);
        setReservasConfirmadas(response.data.filter(r => r.estatus === "Confirmada"));
        setReservasPendientes(response.data.filter(r => r.estatus === "Pendiente"));
    } catch (error) {
        console.error("Error al Buscar reservaciones por fecha...", error);
    }
    
  };

     console.log("Fecha enviada: ", fechaReserva);
     console.log("Rservas recibidos desde el backend: ", JSON.stringify(reservasPendientes, null, 1));
     console.log("Reservas recibidos desde el backend: ", JSON.stringify(reservasConfirmadas, null, 1));

     return(
     <div className="reservas">
      
      {/* --- BOTONES DE NAVEGACIÓN Y PANEL DE BÚSQUEDA --- */}
      <div className="reservas-header">
        {/* IZQUIERDA: Botones principales */}
        <div className="reservas-header-izq">
          <h2>Lista de Reservas</h2>
          <div className="reservas-botones">
            <button className="btn-nueva" onClick={nuevaReserva}>
              Nueva Reserva
            </button>
          </div>
        </div>

  {/* DERECHA: Panel de búsqueda */}
  <div className="reservas-busqueda">
    <h2>Buscar Reservaciones por Fecha</h2>
    <input
      type="date"
      value={fechaReserva}
      onChange={(e) => setFechaReserva(e.target.value)}
    />
    <button onClick={BuscarPorFecha}>Buscar</button>
    <button onClick={cargarReservas}>Ver Todos</button>
  </div>
</div>

       {/* --- RESERVAS CONFIRMADAS --- */}
      <h2>Reservas Confirmadas</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Mesa</th>
            <th>Cliente</th>
            <th>Fecha</th>
            <th>Pedidos</th>
            <th>Estatus</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {reservasConfirmadas.length > 0 ? (
            reservasConfirmadas.map((r) => (
              <tr key={r.idReserva}>
                <td>{r.idReserva}</td>
                <td> {r.idMesa ? `Mesa #${r.idMesa} - ${r.descripcionMesa}` : "-"}</td>
                <td>{r.nombreCliente || "Desconocido"}</td>
                <td>
                  {r.fechaReserva
                    ? new Date(r.fechaReserva).toLocaleString()
                    : "Sin fecha"}
                </td>
                <td>
                  {r.ventas && r.ventas.length > 0 ? (
                    r.ventas.map((v) => (
                        <div key={v.idVenta}>Venta #{v.idVenta} - Total: ${v.total}</div>
                    ))
                  ) : (
                    "Sin pedidos"
                  )}
                </td>
                <td>{r.estatus}</td>
                <td>
                  <button
                    className="btn btn-secondary me-2"
                    onClick={() => volverPendiente(r.idReserva)}
                  >
                    Pendiente
                  </button>
                  <button
                    className="btn btn-primary me-2"
                    onClick={() => navegar(`/ventas/crear?idReserva=${r.idReserva}`)}
                  >
                    Crear Pedido
                  </button>
                  <button
                    className="btn btn-info me-2"
                    onClick={() => verDetalles(r.idReserva)}
                  >
                    Detalles
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => eliminar(r.idReserva)}
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={7} className="text-center">
                No hay reservas confirmadas.
              </td>
            </tr>
          )}
        </tbody>
      </table>

      {/* --- RESERVAS PENDIENTES --- */}
      <h2>Reservas Pendientes</h2>
      <table >
        <thead>
          <tr>
            <th>ID</th>
            <th>Mesa</th>
            <th>Cliente</th>
            <th>Fecha</th>
            <th>Estatus</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
            {reservasPendientes.length > 0 ? (
                reservasPendientes.map((r) => (
                <tr key={r.idReserva}>
                    <td>{r.idReserva}</td>
                    <td> {r.idMesa ? `Mesa #${r.idMesa} - ${r.descripcionMesa}` : "-"}</td>
                    <td>{r.nombreCliente || "Desconocido"}</td>
                    <td>{r.fechaReserva ? new Date(r.fechaReserva).toLocaleString() : "Sin fecha"}</td>
                    <td>{r.estatus}</td>
                    <td>
                    <button className="btn btn-success me-2" onClick={() => confirmar(r.idReserva)}>Confirmar</button>
                    <button className="btn btn-warning me-2" onClick={() => editarReserva(r.idReserva)}>Modificar</button>
                    <button className="btn btn-info me-2" onClick={() => verDetalles(r.idReserva)}>Detalles</button>
                    <button className="btn btn-danger" onClick={() => cancelar(r.idReserva)}>Cancelar</button>
                    </td>
                </tr>
                ))
            ) : (
                <tr>
                <td colSpan={6} className="text-center">No hay reservas pendientes.</td>
                </tr>
            )}
            </tbody>

      </table>
    </div>
  );
};