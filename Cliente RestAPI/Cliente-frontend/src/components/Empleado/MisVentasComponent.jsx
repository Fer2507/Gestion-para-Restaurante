import React, { useEffect, useState } from "react";
import { listventasEmpleado } from "../../service/VentaService";

export const MisVentasComponent = () => {
  const [ventas, setVentas] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const cargarVentas = async () => {
      try {
        const data = await listventasEmpleado();
        setVentas(data);
      } catch (error) {
        console.error("Error cargando ventas:", error);
      } finally {
        setLoading(false);
      }
    };

    cargarVentas();
  }, []);

  if (loading) return <p>Cargando ventas...</p>;

  return (
    <div className="container mt-4">
      <h2>🧾 Mis ventas atendidas</h2>

      {ventas.length === 0 ? (
        <p>No has atendido ninguna venta todavía.</p>
      ) : (
        <table className="table table-striped mt-3">
          <thead>
            <tr>
              <th>ID Venta</th>
              <th>Fecha</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            {ventas.map((v) => (
              <tr key={v.idVenta}>
                <td>{v.idVenta}</td>
                <td>{v.fecha}</td>
                <td>${v.total}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default MisVentasComponent;
