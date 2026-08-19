import React, {useState, useEffect} from "react";
import { crearReserva, getReserva, updateReserva, validarDisponibilidad } from "../../service/ReservacionService";
import { useNavigate, useParams } from "react-router-dom";
import { listCliente } from "../../service/ClienteService";
import { listMesa } from "../../service/MesaService";

export const ReservacionComponents = () => {
  const navigate = useNavigate();
  const { idReserva } = useParams();

  const [idCliente, setIdCliente] = useState("");
  const [idMesa, setIdMesa] = useState("");
  const [fechaReserva, setFechaReserva] = useState("");

  const [clientes, setClientes] = useState([]);
  const [mesas, setMesas] = useState([]);

  useEffect(() => {
    listCliente().then((res) => setClientes(res.data));
    listMesa().then((res) => setMesas(res.data));
  }, []);

  // -- Cargar datos si es edición --
  useEffect(() => {
    if (idReserva) {
      getReserva(idReserva)
        .then((res) => {
          const r = res.data;
          setIdCliente(r.idCliente);
          setIdMesa(r.idMesa.idMesa);
          setFechaReserva(r.fechaReserva.slice(0, 16));
        })
        .catch((err) => console.error("Error cargando reserva:", err));
    }
  }, [idReserva]);
};

  // -- Guardar nueva o actualizar --
  const guardarReserva = (e) => {
    e.preventDefault();

      if (!disponible) {
    alert("Ya existe una reserva en este horario. Selecciona otro.");
    return;
  }

    if (!idCliente || !idMesa) {
      alert("Debe seleccionar un Cliente y una Mesa");
      return;
    }

    const fechaISO = new Date(fechaReserva).toISOString();

    const reserva = {
      idCliente: Number(idCliente),
      idMesa: Number(idMesa),
      fechaReserva: fechaISO,
      estatus: "Pendiente",
    };

    if (idReserva) {
      // -- ACTUALIZAR --
      updateReserva(idReserva, reserva)
        .then(() => {
          alert("Reserva actualizada correctamente.");
          navigate("/reservas/listReserva");
        })
        .catch(() => alert("Error al actualizar la reserva"));
    } else {
      // -- CREAR NUEVA --
      crearReserva({ ...reserva, estatus: "Pendiente" })
        .then((res) => {
          alert(`Reserva creada correctamente (ID: ${res.data.idReserva})`);
          navigate("/reservas/listReserva");
        })
        .catch(() => alert("Error al crear la reserva"));
    }
  };

  return (
    <div className="reservas">
      <h2>{idReserva ? "Editar Reserva" : "Nueva Reserva"}</h2>

      <form onSubmit={guardarReserva}>
        <p>
          <label>
            Cliente:
            <br />
            <select
              value={idCliente}
              onChange={(e) => setIdCliente(e.target.value)}
              required
            >
              <option value="">-- Selecciona un cliente --</option>
              {clientes.map((c) => (
                <option key={c.idCliente} value={c.idCliente}>
                  {c.nombreCliente}
                </option>
              ))}
            </select>
          </label>
        </p>

        <p>
          <label>
            Mesa:
            <br />
            <select
              value={idMesa}
              onChange={(e) => setIdMesa(e.target.value)}
              required
            >
              <option value="">-- Selecciona una Mesa --</option>
              {mesas.map((m) => (
                <option key={m.idMesa} value={m.idMesa}>
                  Mesa: {m.numero} - Capacidad: {m.capacidad}
                </option>
              ))}
            </select>
          </label>
        </p>

        <p>
          <label>
            Fecha y hora de reserva:
            <br />
            <input
              type="datetime-local"
              value={fechaReserva}
              onChange={handleFechaChange}
              required
            />
          </label>
        </p>

        {mensajeDisponibilidad && (
          <p
            style={{
              color: disponible ? "green" : "red",
              fontWeight: "bold",
            }}
          >
            {mensajeDisponibilidad}
          </p>
        )}

        <button type="submit" className="btn btn-success">
          {idReserva ? "Actualizar Reserva" : "Guardar Reserva"}
        </button>
        <button type="button" className="btn btn-secondary ms-2"
            onClick={() => navigate("/reservas/listReserva")} >
            Cancelar
          </button>
      </form>
    </div>
  );
};

{/*Reservaciones:
- Puede aparecer en mi formualrio de venta como Reserva y aparesca en null, con una opcion donde se pueda ver las reservaciones confirmadas
en la fecha actual
- Rserva fecha,hora,IdCliente, idReserva, Id pedido
- La reserva debe tener una opcion donde se pueda ver confrimar(se guarde y muestre en mi formualrio de venta),pendiente(este en una lista),cancelar(Se elimine la reservacion y pedido)
- Tambien pueda haber ventas sin necesidad de tener una reservacion,
el idReserva se muestra solamente que la reservacion este confirmada
- */}

