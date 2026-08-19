import { axiosUsuarios } from "./AuthService";
const REST_API_BASE_URL = "/api/usuarios";

// === CRUD ===
export const listUsuarios = () => axiosUsuarios.get(REST_API_BASE_URL);

export const crearUsuario = (usuario) =>
  axiosUsuarios.post(REST_API_BASE_URL + "/crear", usuario);

export const getUsuario = (idUsuario) =>
  axiosUsuarios.get(`${REST_API_BASE_URL}/${idUsuario}`);

export const updateUsuario = (idUsuario, usuario) =>
  axiosUsuarios.put(`${REST_API_BASE_URL}/actualizar/${idUsuario}`, usuario);
