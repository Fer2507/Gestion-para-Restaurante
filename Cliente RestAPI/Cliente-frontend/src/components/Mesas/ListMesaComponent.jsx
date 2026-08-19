import React, { useState, useEffect} from "react";
import { listMesa, deleteMesa } from "../../service/MesaService";
import { useNavigate } from "react-router-dom";

export const ListMesaComponent = () => {
    const [mesas, setMesas] = useState([]);
    useEffect(() => {
        listMesa().then((response) => {
            setMesas(response.data);
        }).catch(error =>{
            console.error(error);
        })
    }, [])
    const navegar = useNavigate();
    function actualizarMesa(idMesa){
        navegar(`/mesa/edita/${idMesa}`)
    }
    function nuevaMesa(idMesa){
        navegar(`/mesa/nueva`)
    }
    function eliminarMesa(idMesa){
    const confirmar = window.confirm("¿Estás seguro de que quieres eliminar esta Mesa?");
         if (!confirmar) return;
            console.log(idMesa);
            deleteMesa(idMesa).then((response)=>{
                console.log("Mesa eliminado correctamente");
            }).catch(error=>{
                console.error(error);
            })
    }
    function verDetalle(idMesa){
        navegar(`/mesa/detalle/${idMesa}`);
    }
    console.log("Mesas recibidas desde el backend: ", mesas);
    console.log("Mesas recibidas desde el backend: ", JSON.stringify(mesas, null, 2));

    return(
      <div className="mesas">
            <h2>Lista de Mesas</h2>
            <button aria-disabled="page" className="btn btn-primary mb-2" onClick={nuevaMesa}>
                Nueva Mesa
            </button>

            <table className="table table-striped">
                <thead>
                    <tr>
                        <th>Id Mesa</th>
                        <th>Numero de Mesa</th>
                        <th>Capacidad (Cantidad de Personas)</th>
                        <th>Ubicacion</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {mesas.map((mesa) => (
                        <tr key={mesa.idMesa}>
                            <td>{mesa.idMesa}</td>
                            <td>{mesa.numero}</td>
                            <td>{mesa.capacidad}</td>
                            <td>{mesa.ubicacion}</td>
                            <td>{mesa.estado}</td>
                            <td>
                                <button className="btn btn-info me-2" onClick={() => verDetalle(mesa.idMesa)}>
                                    Detalles
                                </button>
                                <button aria-disabled="page" className="btn btn-info me-2" onClick={() => actualizarMesa(mesa.idMesa)}>
                                    Actualizar
                                </button>
                                <button aria-disabled="page" className="btn btn-danger" onClick={() => eliminarMesa(mesa.idMesa)}>
                                    Eliminar
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );

}