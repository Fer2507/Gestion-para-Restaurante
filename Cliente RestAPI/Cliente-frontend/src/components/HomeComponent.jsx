import React, { useEffect, useState } from "react";
import { listProductosActivos, buscarEntrePrecios, buscarPorTipo, BuscarProducto } from "../service/ProductoService";
import { useNavigate } from "react-router-dom";

export const HomeComponent = () => {
  const [productos, setProductos] = useState([]);
  const [nombreProducto, setNombreProducto] = useState("");
  const [tipo, setTipo] = useState("");
  const [min, setMin] = useState("");
  const [max, setMax] = useState("");
  const navegar = useNavigate();

  // Cargar solo productos activos al montar el componente
  useEffect(() => {
    cargarProductosActivos();
  }, []);

  const cargarProductosActivos = () => {
    listProductosActivos()
      .then((response) => {
        // Si devuelve todos los productos, filtramos los activos
        const activos = response.data.filter((p) => p.estado === "Activo" || p.activo === true);
        setProductos(activos);
      })
      .catch((error) => {
        console.error("Error al cargar productos activos:", error);
      });
  };

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
      console.log("Productos recibidos desde el backend: ", JSON.stringify(productos, null, 1));

  return (
   <div className="productos-cards-container">
  <div className="productos-cards-header">
    <h2>Productos Disponibles</h2>
    <h2><a href="/login">Iniciar Sesión</a></h2>
    <h2><a href="/cliente/crear">Nuevo Cliente</a></h2>
  </div>

  <div className="productos-grid">
    {productos.length > 0 ? (
      productos.map((producto) => (
        <div className="producto-card" key={producto.idProducto}>
          
          <img
            src={
              producto.nombreFoto
                ? `http://localhost:7071/api/producto/img/${producto.nombreFoto}`
                : "/no_imagen.jpg"
            }
            alt={producto.nombreProducto}
            className="producto-card-img"
          />

          <h3 className="producto-card-title">{producto.nombreProducto}</h3>
          
          <p className="producto-card-desc">
            {producto.descripcionProducto}
          </p>

          <p className="producto-card-price">
            ${producto.precioProducto?.toFixed(2)}
          </p>

          <span className="producto-card-type">
            Tipo: {producto.idTipo}
          </span>
        </div>
      ))
    ) : (
      <p>No hay productos activos disponibles.</p>
    )}
  </div>
</div>
  );
};
