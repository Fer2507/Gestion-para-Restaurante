import { axiosTaller, axiosPublic } from "./AuthService";
import { getToken } from "./AuthService";

const REST_API_BASE_URL='http://localhost:7070/api/cliente';

export const listCliente = () => axiosTaller.get(REST_API_BASE_URL);

export const crearCliente = (cliente) => {
     const token = getToken();

  // Si hay token -> usa axios con token (taller)
  if (token) {
    return axiosTaller.post(REST_API_BASE_URL, cliente);
  }
  // Si NO hay token -> endpoint público sin token
  return axiosPublic.post(REST_API_BASE_URL, cliente);
};

export const getCliente = (clienteid)=>axiosTaller.get(REST_API_BASE_URL + '/' + clienteid);

export const updateCliente = (clienteid, cliente) => axiosTaller.put(REST_API_BASE_URL + '/' + clienteid, cliente);

export const deleteCliente = (clienteid) => axiosTaller.delete(REST_API_BASE_URL + '/' + clienteid);

export const BuscarCliente = (nombreCliente) => axiosTaller.get(`${REST_API_BASE_URL}/buscar?nombreCliente=${nombreCliente}`);
