import { axiosReservaciones } from "./AuthService";

const EMPLEADO_API = "http://localhost:7072/api/empleado"

export const listEmpleado = () => axiosReservaciones.get(EMPLEADO_API);

export const nuevoEmpleado  = (empleado) => axiosReservaciones.post(EMPLEADO_API, empleado);

export const getEmpleado = (idEmpleado) => axiosReservaciones.get(`${EMPLEADO_API}/${idEmpleado}`);

export const updateEmpleado = (idEmpleado, venta) => axiosReservaciones.put(`${EMPLEADO_API}/${idEmpleado}`, venta);

export const deleteEmpleado = (idEmpleado) => axiosReservaciones.delete(`${EMPLEADO_API}/${idEmpleado}`);

export const buscarNombre = (nombreEmp) => axiosReservaciones.get(`${EMPLEADO_API}/buscar`, { params: { nombreEmp } });

export const buscarPuesto = (puesto) => axiosReservaciones.get(`${EMPLEADO_API}/puesto`, { params: { puesto } });

export const obtenerEmpleadoPorUsuario = () => axiosReservaciones.get(`${EMPLEADO_API}/por-usuario`);