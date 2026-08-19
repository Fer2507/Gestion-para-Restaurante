import { axiosFonda } from "./AuthService";
const PRODUCTOS_API = 'http://localhost:7071/api/producto';

export const listProductos = () => axiosFonda.get(PRODUCTOS_API);

export const listProductosActivos = () => axiosFonda.get(`${PRODUCTOS_API}/activos`);


export const crearProductos = (producto) => {
  return axiosFonda.post(PRODUCTOS_API, producto, {
    headers: { "Content-Type": "application/json" },
  });
}
export const getProducto = (idProducto) => axiosFonda.get(`${PRODUCTOS_API}/${idProducto}`)

export const updateProducto = (idProducto, producto) => {
  return axiosFonda.put(`${PRODUCTOS_API}/${idProducto}`, producto, {
    headers: { "Content-Type": "application/json" },
  });
}

export const deleteProducto = (idProducto) => axiosFonda.delete(`${PRODUCTOS_API}/${idProducto}`)

export const softDeleteProducto = (idProducto) => axiosFonda.put(`${PRODUCTOS_API}/ocultar/${idProducto}`)

export const activarProducto = (idProducto) => axiosFonda.put(`${PRODUCTOS_API}/activar/${idProducto}`);

export const getProductosInactivos = () => axiosFonda.get(`${PRODUCTOS_API}/inactivos`);

export const subirImagen = (idProducto, formData) => {
  return axiosFonda.post(`${PRODUCTOS_API}/uploadImage/${idProducto}`, formData);
};

export const buscarPorTipo = (nombreTipo) => axiosFonda.get(`${PRODUCTOS_API}/buscarPorTipo?nombreTipo=${nombreTipo}`);


export const buscarEntrePrecios = (min, max) => axiosFonda.get(`${PRODUCTOS_API}/buscarEntrePrecios?min=${min}&max=${max}`);

export const BuscarProducto = (nombreProducto) => axiosFonda.get(`${PRODUCTOS_API}/buscar?nombreProducto=${nombreProducto}`);

