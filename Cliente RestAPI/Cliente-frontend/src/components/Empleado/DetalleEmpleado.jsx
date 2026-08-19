import React, {useState, useEffect} from "react";
import { getEmpleado } from "../../service/EmpleadoService";
import { useParams, useNavigate } from "react-router-dom";

export const DetalleEmpleado = () => {
     const { idEmpleado } = useParams();
      const navegar = useNavigate();
    
      const [empleado, setEmpleado] = useState({
        nombreEmp: "",
        puesto: "",
        clave:"",
        idUsuario: "",
        email: ""
      });
    
      useEffect(() => {
        if (idEmpleado) {
          getEmpleado(idEmpleado)
            .then((response) => setEmpleado(response.data))
            .catch((error) => console.error(error));
        }
      }, [idEmpleado]);
    
      return (
       <div className="empleados">
          <h2>Detalle del Empleado</h2>
          <div>
            <p><strong>Nombre:</strong> {empleado.nombreEmp}</p>
            <p><strong>Email:</strong> {empleado.email}</p>
            <p><strong>Puesto:</strong> {empleado.puesto}</p>
            <p><strong>Clave:</strong> {empleado.clave}</p>
            <p><strong>Id Usuario:</strong> {empleado.idUsuario}</p>
            <button className="btn btn-secondary" onClick={() => navegar("/empleado/listaEmp")}>
              Volver a la Lista
            </button>
          </div>
        </div>
      );
}