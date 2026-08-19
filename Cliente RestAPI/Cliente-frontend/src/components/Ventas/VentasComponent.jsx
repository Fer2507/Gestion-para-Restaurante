import React, { useState, useEffect } from "react";
import { crearVenta, getVenta, updateVenta } from "../../service/VentaService";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { listCliente } from "../../service/ClienteService";
import { listProductos } from "../../service/ProductoService";
import { listEmpleado, obtenerEmpleadoPorUsuario } from "../../service/EmpleadoService";
import { listReservas } from "../../service/ReservacionService";

export const VentasComponent = () => {
  const [idCliente, setIdCliente] = useState("");
  const [idEmpleado, setIdEmpleado] = useState("");
  const [rolUsuario, setRolUsuario] = useState("");

  const [detalles, setDetalles] = useState([]);
  const [idReserva, setIdReserva] = useState("");
  const { idVenta } = useParams();
  const navegar = useNavigate();
  const location = useLocation();

  const [clientes, setClientes] = useState([]);
  const [empleados, setEmpleados] = useState([]);
  const [productosDisponibles, setProductosDisponibles] = useState([]);
  const [reservas, setReservas] = useState([]);

   // --- Nuevo: empleado del usuario logueado ---
  const [empleadoActual, setEmpleadoActual] = useState(null);
  const [esEmpleadoLogueado, setEsEmpleadoLogueado] = useState(false);

  // --- Cargar datos iniciales ---
  useEffect(() => {
    const rol = localStorage.getItem("rol"); 
    setRolUsuario(rol);

    obtenerEmpleadoPorUsuario()
    .then(res => {
      if (res.data) {
        setEmpleadoActual(res.data);
        setIdEmpleado(res.data.idEmpleado);  // Asigna automáticamente
        setEsEmpleadoLogueado(true);
      }
    })
    .catch(err => console.error("Error obteniendo empleado del usuario:", err));
    
    listReservas()
    .then((res) => setReservas(res.data))
    .catch((err) => console.error(err));

    listCliente()
      .then((res) => setClientes(res.data))
      .catch((err) => console.error(err));

    listEmpleado()
      .then((res) => setEmpleados(res.data))
      .catch((err) => console.error(err));

    listProductos()
      .then((res) => setProductosDisponibles(res.data))
      .catch((err) => console.error(err));
  }, []);

  useEffect(() => {
  const param = new URLSearchParams(location.search);
  const idReservaParam = param.get("idReserva");

  if (idReservaParam && reservas.length > 0) {
    const reservaSeleccionada = reservas.find(
      (r) => r.idReserva === Number(idReservaParam)
    );

    if (reservaSeleccionada) {
      setIdReserva(reservaSeleccionada.idReserva);
      setIdCliente(reservaSeleccionada.idCliente); // Preselecciona cliente
    } else {
      setIdReserva(Number(idReservaParam));
    }
  }
}, [location.search, reservas]);

   // --- Cargar venta al editar ---
  useEffect(() => {
    if (idVenta) {
      getVenta(idVenta)
        .then((res) => {
          setIdCliente(res.data.idCliente ?? "");
          setIdEmpleado(res.data.idEmpleado ?? "");
          setDetalles(res.data.detalles || []);
          setIdReserva(res.data.idReserva || "");
        })
        .catch((error) => console.error(error));
    }
  }, [idVenta]);

  // --- Manejadores de cambio ---
  const actualizarCliente = (e) => setIdCliente(e.target.value);
  const actualizarEmpleado = (e) => setIdEmpleado(e.target.value);

  const agregarProducto = () =>
    setDetalles([...detalles, { idProducto: "", cantidad: 1 }]);

  const actualizarDetalle = (index, campo, valor) => {
    const nuevosDetalles = [...detalles];
    nuevosDetalles[index][campo] =
      campo === "cantidad" ? Number(valor) : valor;
    setDetalles(nuevosDetalles);
  };

  const eliminarDetalle = (index) => {
    const nuevosDetalles = detalles.filter((_, i) => i !== index);
    setDetalles(nuevosDetalles);
  };

  const calcularSubtotal = (detalle) => {
    const producto = productosDisponibles.find(
      (p) => p.idProducto === Number(detalle.idProducto)
    );
    return producto ? producto.precioProducto * detalle.cantidad : 0;
  };

  // --- Guardar o actualizar venta ---
 const saveVenta = (e) => {
  e.preventDefault();

  if (!idCliente || !idEmpleado) {
    alert("Debes seleccionar un cliente y un empleado");
    return;
  }

  if (
    detalles.length === 0 ||
    detalles.some((d) => !d.idProducto || d.cantidad <= 0)
  ) {
    alert("Todos los productos deben estar seleccionados y tener cantidad mayor a 0");
    return;
  }

  const total = detalles.reduce((acc, d) => acc + calcularSubtotal(d), 0);

  const detallesNumericos = detalles.map((d) => ({
    idProducto: Number(d.idProducto),
    cantidad: Number(d.cantidad),
  }));
    
  const venta = {
    idCliente: Number(idCliente),
    idEmpleado: Number(idEmpleado),
    idReserva: idReserva ? Number(idReserva) : null,
    total,
    detalles: detallesNumericos,
  };

  // Determinar si es creación o actualización
  const accion = idVenta ? updateVenta(idVenta, venta) : crearVenta(venta);

  accion
    .then(() =>{
      if(idReserva) {
         navegar("/reservas/listReserva");
      }else{
         navegar("/ventas/listaVen");
      }
    })
    .catch((error) => {
      console.error("Error guardando venta:", error);

      // Opcional: mostrar alerta si el backend devuelve error
      alert("Ocurrió un error al guardar la venta. Revisa la consola.");
    });
};

  return (
    <div className="ventas">
      <h2 className="text-center">
        {idVenta ? "Modificar Venta" : "Nueva Venta"}
      </h2>

      <form onSubmit={saveVenta}>
        <p>
          <label>
            Cliente:
            <br />
            <select value={idCliente || ""} onChange={actualizarCliente} required>
              <option value="">-- Selecciona un cliente --</option>
              {clientes.map((c) => (
                <option key={c.idCliente} value={c.idCliente}>
                  {c.nombreCliente}
                </option>
              ))}
            </select>
          </label>
        </p>

        {/* Selección de Empleado */}
        <p>
          <label>
            Empleado:
            <select 
              value={idEmpleado || ""} 
              onChange={actualizarEmpleado} 
              required
              disabled={esEmpleadoLogueado}   // ← bloqueo si viene del login
            >
              <option value="">-- Selecciona un empleado --</option>
              {empleados.map(e => (
                <option key={e.idEmpleado} value={e.idEmpleado}>
                  {e.nombreEmp}
                </option>
              ))}
            </select>

            {esEmpleadoLogueado && (
              <span style={{ marginLeft: "8px", color: "green" }}>
                (Empleado asignado automáticamente)
              </span>
            )}
          </label>
        </p>
        <h4>Detalles de la Venta</h4>
        {detalles.map((detalle, index) => (
          <div key={index} style={{ marginBottom: "10px" }}>
            <select
              value={detalle.idProducto || ""}
              onChange={(e) =>
                actualizarDetalle(index, "idProducto", e.target.value)
              }
              required
            >
              <option value="">-- Selecciona un producto --</option>
              {productosDisponibles.map((p) => (
                <option key={p.idProducto} value={p.idProducto}>
                  {p.nombreProducto} - ${p.precioProducto}
                </option>
              ))}
            </select>

            <input
              type="number"
              min="1"
              value={detalle.cantidad || 1}
              onChange={(e) =>
                actualizarDetalle(index, "cantidad", e.target.value)
              }
              required
              style={{ width: "60px", marginLeft: "10px" }}
            />

            <span style={{ marginLeft: "10px" }}>
              Subtotal: ${calcularSubtotal(detalle).toFixed(2)}
            </span>

            <button
              type="button"
              onClick={() => eliminarDetalle(index)}
              style={{ marginLeft: "10px" }}
            >
              Eliminar
            </button>
          </div>
        ))}

        <button type="button" onClick={agregarProducto}>
          Agregar Producto
        </button>

        <p style={{ marginTop: "20px" }}>
          <button type="submit" className="btn btn-success">
            Guardar Venta
          </button>
          <button
            type="button"
            className="btn btn-secondary ms-2"
            onClick={() => navegar("/ventas/listaVen")}
          >
            Cancelar
          </button>
        </p>

        <h4>
          Total de la Venta: $
          {detalles
            .reduce((acc, d) => acc + calcularSubtotal(d), 0)
            .toFixed(2)}
        </h4>
      </form>
    </div>
  );
};
