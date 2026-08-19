import React, { useEffect, useState } from 'react';
import {  listProductos, getProductosInactivos, softDeleteProducto, 
    deleteProducto,  activarProducto, buscarEntrePrecios, buscarPorTipo, BuscarProducto} from '../../service/ProductoService';
import { useNavigate } from 'react-router-dom';

export const ListProductoComponent = () => {
    const [productos, setProductos] = useState([]);
    const [mostrandoInactivos, setMostrandoInactivos] = useState(false);
    const [nombreProducto, setNombreProducto] = useState("");
    const [tipo, setTipo] = useState("");
    const [min, setMin] = useState("");
    const [max, setMax] = useState("");
    const navegar = useNavigate();

    // Cargar productos
    useEffect(() => {
        cargarProductos();
    }, []);

    const cargarProductos = () => {
        listProductos()
            .then((response) => {
                setProductos(response.data);
                setMostrandoInactivos(false);
            })
            .catch((error) => {
                console.error("Error al cargar productos:", error);
            });
    };

    const cargarInactivos = () => {
        getProductosInactivos()
            .then((response) => {
                setProductos(response.data);
                setMostrandoInactivos(true);
            })
            .catch((error) => {
                console.error("Error al cargar productos inactivos:", error);
            });
    };

    function actualizarProductos(idPro) {
        navegar(`/producto/editar/${idPro}`);
    }

    function nuevoProducto() {
        navegar(`/producto/crear`);
    }

    const verDetalle = (idPro) => {
    navegar(`/producto/detalle/${idPro}`, { state: { origen: "listapro" } });
  };

    // Ocultar producto (soft delete)
    function ocultarProducto(idPro) {
        if (!window.confirm("¿Quieres Eliminar este producto?")) return;
        softDeleteProducto(idPro)
            .then(() => {
                setProductos(productos.filter(p => p.idProducto !== idPro));
            })
            .catch(error => console.error("Error al ocultar producto:", error));
    }

    // Eliminar producto definitivamente
    function eliminarProducto(idPro) {
        const confirmar = window.confirm("¿Estás seguro de que quieres eliminar definitivamente este producto?");
        if (!confirmar) return;
        deleteProducto(idPro)
            .then(() => {
                setProductos(productos.filter(p => p.idProducto !== idPro));
            })
            .catch(error => console.error("Error al eliminar producto:", error));
    }

    // Reactivar producto
    function reactivarProducto(idPro) {
        if (!window.confirm("¿Deseas volver a activar este producto?")) return;
        activarProducto(idPro)
            .then(() => {
                setProductos(productos.filter(p => p.idProducto !== idPro));
            })
            .catch(error => console.error("Error al reactivar producto:", error));
    }
    //BUSQUEDAS
    const BuscarPorNombre = async () => {
        if (nombreProducto.trim() === "") {
            cargarTodos();
            return;
        }
        try {
            const response = await BuscarProducto(nombreProducto);
            setProductos(response.data);
        } catch (error) {
            console.error("Error al buscar productos por nombre:", error);
        }
    };

    const BuscarPorTipo = async () => {
        if (tipo.trim() === "") {
            cargarTodos();
            return;
        }
        try {
            const response = await buscarPorTipo(tipo);
            setProductos(response.data);
        } catch (error) {
            console.error("Error al buscar productos por tipo:", error);
        }
    };

    const BuscarPorPrecios = async () => {
        if (min === "" || max === "" || parseFloat(min) > parseFloat(max)) {
            alert("Por favor ingresa un rango de precios válido (min <= max).");
            cargarTodos();
            return;
        }
        try {
            const response = await buscarEntrePrecios(min, max);
            setProductos(response.data);
        } catch (error) {
            console.error("Error al buscar productos entre precios:", error);
        }
    };
    const cargarTodos = () => {
        listProductos()
            .then(response => setProductos(response.data))
            .catch(error => console.error("Error al cargar productos:", error));
    };
    console.log("Productos recibidos desde el backend: ", JSON.stringify(productos, null, 1));


    return (
        <div className="productos" style={{ padding: "20px" }}>
            <div className="productos-header">
            <h2 style={{ textAlign: "center", marginBottom: "20px" }}>
            {mostrandoInactivos ? "Productos Inactivos" : "Lista de Productos Activos"}
            </h2> 
             <div className="productos-header-izq">
                    <button className="btn-principal" onClick={nuevoProducto}>
                    Nuevo Producto
                    </button>
                    {!mostrandoInactivos ? (
                    <button className="btn-secundario" onClick={cargarInactivos}>
                        Mostrar Inactivos
                    </button>
                    ) : (
                    <button className="btn-secundario" onClick={cargarProductos}>
                        Mostrar Activos
                    </button>
                    )}
                </div>

                {/* ====== PANEL DE BÚSQUEDA ====== */}
                <div className="productos-busqueda">
                <h2>Buscar Productos</h2>

                <div className="productos-busqueda-linea">
                        <input
                        type="text"
                        placeholder="Nombre o Letra del Producto..."
                        value={nombreProducto}
                        onChange={(e) => setNombreProducto(e.target.value)}
                        />
                        <button onClick={BuscarPorNombre}>Buscar</button>
                    </div>
                    <div className="productos-busqueda-linea">
                        <input
                        type="text"
                        placeholder="Tipo de Producto..."
                        value={tipo}
                        onChange={(e) => setTipo(e.target.value)}
                        />
                        <button onClick={BuscarPorTipo}>Buscar</button>
                    </div>
                    <div className="productos-busqueda-linea">
                        <input
                        type="text"
                        placeholder="Precio Min..."
                        value={min}
                        onChange={(e) => setMin(e.target.value)}
                        />
                        <input
                        type="text"
                        placeholder="Precio Max..."
                        value={max}
                        onChange={(e) => setMax(e.target.value)}
                        />
                        <button onClick={BuscarPorPrecios}>Buscar</button>
                    </div>
                    <div className="search-form ver-todo">
                    <button onClick={cargarTodos}>Ver Todos</button>
                    </div>
                </div>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Id Producto</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                        <th>Tipo</th>
                        <th>Imagen</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {productos.length > 0 ? (
                        productos.map((producto) => (
                            <tr key={producto.idProducto}>
                                <td>{producto.idProducto}</td>
                                <td>{producto.nombreProducto}</td>
                                <td>{producto.descripcionProducto}</td>
                                <td>${producto.precioProducto?.toFixed(2)}</td>
                                <td>{producto.nombreTipo}</td>
                                <td><img src={producto.nombreFoto ? `http://localhost:7071/api/producto/img/${producto.nombreFoto}` : "/no_imagen.jpg"} 
                                        alt={producto.nombreProducto} width="120" /></td>
                                <td>
                                    {!mostrandoInactivos ? (
                                        <>
                                            <button className="btn btn-info me-2" onClick={() => actualizarProductos(producto.idProducto)}>
                                                Actualizar
                                            </button>
                                            <button className="btn btn-info" onClick={() => verDetalle(producto.idProducto)}>
                                                Ver Detalles
                                                </button>
                                            <button className="btn btn-warning me-2" onClick={() => ocultarProducto(producto.idProducto)}>
                                                Eliminar
                                            </button>
                                        </>
                                    ) : (
                                        <button className="btn btn-success" onClick={() => reactivarProducto(producto.idProducto)}>
                                            Reactivar
                                        </button>
                                        
                                    )}
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="7" style={{ textAlign: "center", padding: "15px" }}>
                                No hay productos para mostrar.
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
            </div>
    );
};
