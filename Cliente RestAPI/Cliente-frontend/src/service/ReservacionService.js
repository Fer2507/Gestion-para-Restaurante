import { axiosReservaciones } from "./AuthService";

const RESERVAS_API = "http://localhost:7072/api/reservas"

export const listReservas = () => axiosReservaciones.get(RESERVAS_API)

export const crearReserva = (reservas) => axiosReservaciones.post(RESERVAS_API, reservas);

export const buscarPorFecha = (fecha) => axiosReservaciones.get(`${RESERVAS_API}/fecha`, { params: { fecha }});

export const getReserva = (idReserva) => axiosReservaciones.get(`${RESERVAS_API}/${idReserva}`);

export const updateReserva = (idReserva, reservas) => axiosReservaciones.put(`${RESERVAS_API}/${idReserva}`, reservas);

export const deleteReserva = (idReserva) => axiosReservaciones.delete(`${RESERVAS_API}/${idReserva}`);

export const listReservaPorCliente = (idCliente) => axiosReservaciones.get(`${RESERVAS_API}/cliente/${idCliente}`);

export const confirmarReserva = (idReserva) => axiosReservaciones.put(`${RESERVAS_API}/confirmar/${idReserva}`);

export const pendienteReserva = (idReserva) => axiosReservaciones.put(`${RESERVAS_API}/pendiente/${idReserva}`);

export const cancelarReserva = (idReserva) => axiosReservaciones.delete(`${RESERVAS_API}/cancelar/${idReserva}`);

export const validarDisponibilidad = async (fechaCompleta) => {
  const response = await axiosReservaciones.get(`${RESERVAS_API}/validar`, {
    params: { fecha: fechaCompleta },
  });
  return response.data; // true = NO disponible, false = disponible
};