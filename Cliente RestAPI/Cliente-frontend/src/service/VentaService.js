import { axiosFonda } from "./AuthService";

const VENTAS_API = "http://localhost:7071/api/ventas";

// Listar todos las Ventas
export const listVentas = () => axiosFonda.get(VENTAS_API);

// Crear una Venta
export const crearVenta = (venta) => axiosFonda.post(VENTAS_API, venta);

// Obtener un venta por id
export const getVenta = (idVenta) => axiosFonda.get(`${VENTAS_API}/${idVenta}`);

// Actualizar una venta
export const updateVenta = (idVenta, venta) => axiosFonda.put(`${VENTAS_API}/${idVenta}`, venta);

//Ver Venta por idCliente
export const listVentasPorCliente = (idCliente) => axiosFonda.get(`${VENTAS_API}/cliente/${idCliente}`);

// Eliminar una venta
export const deleteVenta = (idVenta) => axiosFonda.delete(`${VENTAS_API}/${idVenta}`);

export const verTicket = async (idVenta) => axiosFonda.get(`${VENTAS_API}/ticket/${idVenta}`, {  responseType: "arraybuffer",})

export const buscarPorFecha = (fecha) => axiosFonda.get(`${VENTAS_API}/buscar` , {params: { fecha }});

export const listventasEmpleado = () => axiosFonda.get(`${VENTAS_API}/mesero/mis-ventas`);
