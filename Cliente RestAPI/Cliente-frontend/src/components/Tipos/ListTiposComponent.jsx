import React, { useEffect, useState } from 'react';
import { listTipos, deleteTipo } from '../../service/TipoService';
import { useNavigate } from 'react-router-dom';

export const ListTiposComponent = () => {
    const [tipos, setTipos] = useState([]);
    const navegar = useNavigate();

    useEffect(() => {
        cargarTipos();
    }, []);

    const cargarTipos = () => {
        listTipos()
            .then(response => setTipos(response.data))
            .catch(error => console.error(error));
    };

    function actualizarTipo(id){
        navegar(`/tipo/edita/${id}`);
    }

    function nuevoTipo(){
        navegar(`/tipo/crear`);
    }

    function eliminarTipo(id){
        const confirmar = window.confirm("¿Estás seguro de que quieres eliminar este Tipo?");
        if (!confirmar) return;

        deleteTipo(id)
            .then(() => {
                console.log("Tipo eliminado correctamente");
                cargarTipos(); // recargar lista
            })
            .catch(error => console.error(error));
    }

    console.log("Reservas recibidos desde el backend: ", JSON.stringify(tipos, null, 1));

    return (
        <div className="tipo">
            <h2>Lista de Tipos</h2>
            <button className="btn btn-primary mb-2" onClick={nuevoTipo}>Nuevo</button>
            <table className="table table-striped">
                <thead>
                    <tr>
                        <th>Id Tipo</th>
                        <th>Nombre Tipo</th>
                        <th>Descripción</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {tipos.map(tipo => (
                        <tr key={tipo.id}>
                            <td>{tipo.id}</td>
                            <td>{tipo.nombreTipo}</td>
                            <td>{tipo.descripcionTipo}</td>
                            <td>
                                <button className="btn btn-info me-2" onClick={() => actualizarTipo(tipo.id)}>Actualizar</button>
                                <button className="btn btn-info me-2" onClick={() => navegar(`/tipo/detalle/${tipo.id}`)}>Detalles</button>
                                <button className="btn btn-danger" onClick={() => eliminarTipo(tipo.id)}>Eliminar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
