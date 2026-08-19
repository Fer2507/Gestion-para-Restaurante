import React, { useEffect, useState  } from 'react';
import { listCliente, deleteCliente, BuscarCliente } from '../../service/ClienteService';
import { useNavigate } from 'react-router-dom';


export const ListClienteComponent = () => {
    const [clientes, setClientes] = useState([]);
    const [nombreCliente, setNombreCliente] = useState("");
    
useEffect(()=>{
    listCliente().then((response)=>{
        setClientes(response.data);
    }).catch(error=> {
        console.error(error);
    })
}, [] )

    const navegar = useNavigate(); 
    
    function actualizarCliente(id){
        navegar(`/cliente/edita/${id}`)
    }
     function nuevoCliente(id){
        navegar(`/cliente/crear`)
    }
    function eliminarCliente(id){
        // Mostrar confirmación
    const confirmar = window.confirm("¿Estás seguro de que quieres eliminar este cliente?");
    if (!confirmar) return;
        console.log(id);
        deleteCliente(id).then((response)=>{
            console.log("Cliente eliminado correctamente");
        }).catch(error=>{
            console.error(error);
        })
    }

    const BuscarLC = async () => {
    if (nombreCliente.trim() === "") {
      cargarClientes(); // Si no hay texto, muestra todos
      return;
    }
    try {
      const response = await BuscarCliente(nombreCliente);
      console.log("Clientes encontrados:", response.data);
      setClientes(response.data);
    } catch (error) {
      console.error("Error al buscar clientes:", error);
    }
  };
  const cargarClientes = () => {
    listCliente()
      .then((response) => {
        setClientes(response.data);
      })
      .catch((error) => {
        console.error("Error al cargar clientes:", error);
      })
      }

    function verDetalle(id){
        navegar(`/cliente/detalle/${id}`);
    }
    console.log("Clientes recibidos desde el backend: ", clientes);
    console.log("Clientes recibidos desde el backend: ", JSON.stringify(clientes, null, 2));

    return(
        <div className="clientes">
           <div className="clientes-header">
             <div className="clientes-header-izq">
                <h2>Lista de Clientes</h2>
            <button aria-disabled="page" className="btn btn-primary mb-2" onClick={nuevoCliente}>
                Nuevo
            </button>
            </div>
             <div className="clientes-busqueda">
                <h2>Buscar Cliente</h2>
                <input
                    type="text"
                    className="form-control mb-2"
                    placeholder="Nombre del cliente..."
                    value={nombreCliente}
                    onChange={(e) => setNombreCliente(e.target.value)}
                />
                <button className="btn btn-primary w-100 mb-2" onClick={BuscarLC}>
                    Buscar
                </button>
                <button className="btn btn-secondary w-100" onClick={cargarClientes}>
                    Ver Todos
                </button>
            </div>
           </div>
            <table className="table table-striped">
                <thead>
                    <tr>
                        <th>Id Cliente</th>
                        <th>Nombre Cliente</th>
                        <th>Teléfono</th>
                        <th>Correo</th>
                        <th>Clave</th>
                        <th>Id Usuario</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {clientes.map((cliente) => (
                        <tr key={cliente.idCliente}>
                            <td>{cliente.idCliente}</td>
                            <td>{cliente.nombreCliente}</td>
                            <td>{cliente.telefonoCliente}</td>
                            <td>{cliente.correoCliente}</td>
                            <td>{cliente.clave}</td>
                            <td>{cliente.idUsuario}</td>
                            <td>
                                <button className="btn btn-info me-2" onClick={() => verDetalle(cliente.idCliente)}>
                                    Detalles
                                </button>
                                <button aria-disabled="page" className="btn btn-info me-2" onClick={() => actualizarCliente(cliente.idCliente)}>
                                    Actualizar
                                </button>
                                <button aria-disabled="page" className="btn btn-danger" onClick={() => eliminarCliente(cliente.idCliente)}>
                                    Eliminar
                                </button>
                            </td>
                        </tr>
                    ))}

                </tbody>
            </table>
        </div>
    );
};