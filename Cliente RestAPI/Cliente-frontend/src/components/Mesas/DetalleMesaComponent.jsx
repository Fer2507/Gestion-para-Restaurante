import React, {useState, useEffect} from "react";
import { getMesa } from "../../service/MesaService";
import { useParams, useNavigate } from "react-router-dom";

export const DetalleMesa = () => {
     const { idMesa } = useParams();
      const navegar = useNavigate();
    
      const [mesa, setMesa] = useState({
        numero: "", 
        capacidad: "", 
        ubicacion: "",
        estado: ","
      });
    
      useEffect(() => {
        if (idMesa) {
          getMesa(idMesa)
            .then((response) => setMesa(response.data))
            .catch((error) => console.error(error));
        }
      }, [idMesa]);
    
      return (
       <div className="mesas">
          <h2>Detalle de la Mesa</h2>
          <div>
            <p><strong>Numero:</strong> {mesa.numero}</p>
            <p><strong>Capacidad:</strong> {mesa.capacidad}</p>
             <p><strong>Ubicacion:</strong> {mesa.ubicacion}</p>
            <p><strong>Estado:</strong> {mesa.estado}</p>
            <button className="btn btn-secondary" onClick={() => navegar("/mesa/listMesa")}>
              Volver a la Lista
            </button>
          </div>
        </div>
      );
}