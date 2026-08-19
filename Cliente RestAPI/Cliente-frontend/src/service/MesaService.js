import { axiosReservaciones } from "./AuthService";

const Mesas_API = "http://localhost:7072/api/mesa"

export const listMesa = () => axiosReservaciones.get(Mesas_API);

export const nuevaMesa  = (mesa) => axiosReservaciones.post(Mesas_API, mesa);

export const getMesa = (idmesa) => axiosReservaciones.get(`${Mesas_API}/${idmesa}`);

//export const getMesaNumero = (numero) => axios.get(`${Mesas_API}/numero/${numero}`)

export const updateMesa = (idmesa, mesa) => axiosReservaciones.put(`${Mesas_API}/${idmesa}`, mesa);

export const deleteMesa = (idmesa) => axiosReservaciones.delete(`${Mesas_API}/${idmesa}`);
