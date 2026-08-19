import { axiosFonda } from "./AuthService";
const TIPOS_API = "http://localhost:7071/api/tipo";

// Listar todos los tipos
export const listTipos = () => axiosFonda.get(TIPOS_API);

// Crear un tipo
export const crearTipo = (tipo) => axiosFonda.post(TIPOS_API, tipo);

// Obtener un tipo por id
export const getTipo = (idTipo) => axiosFonda.get(`${TIPOS_API}/${idTipo}`);

// Actualizar un tipo
export const updateTipo = (idTipo, tipo) => axiosFonda.put(`${TIPOS_API}/${idTipo}`, tipo);

// Eliminar un tipo
export const deleteTipo = (idTipo) => axiosFonda.delete(`${TIPOS_API}/${idTipo}`);
