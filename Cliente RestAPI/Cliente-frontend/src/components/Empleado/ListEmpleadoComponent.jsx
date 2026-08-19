import React, { useState, useEffect} from "react";
import { listEmpleado, deleteEmpleado, buscarNombre, buscarPuesto } from "../../service/EmpleadoService";
import { useNavigate } from "react-router-dom";

export const ListEmpleadoComponent = () => {
    const [empleados, setEmpleados] =useState([]);
    const [nombreEmp, setNombreEmp] = useState("");
    const [puesto, setPuesto] = useState("");
    const [email, setEmail] = useState("");
    const [idUsuario, setIdUsuario] = useState("");

useEffect(()=>{
    listEmpleado().then((response)=>{
        setEmpleados(response.data);
    }).catch(error=> {
        console.error(error);
    })
}, [] )
 const navegar = useNavigate(); 
    
    function actualizaEmpleado(idEmpleado){
        navegar(`/empleado/edita/${idEmpleado}`)
    }
     function nuevoEmpleado(idEmpleado){
        navegar(`/empleado/crear`)
    }
    function eliminarEmpleado(idEmpleado){
        // Mostrar confirmación
    const confirmar = window.confirm("¿Estás seguro de que quieres eliminar este Empleado?");
    if (!confirmar) return;
        console.log(idEmpleado);
        deleteEmpleado(idEmpleado).then((response)=>{
            console.log("Empleado eliminado correctamente");
        }).catch(error=>{
            console.error(error);
        })
    }

    function verDetalle(idEmpleado){
        navegar(`/empleado/detalle/${idEmpleado}`);
    }

    const CargarTodos = () => {
        listEmpleado().then((response) => {
            setEmpleados(response.data);
        }).catch((error) => {
            console.error("Error al cargar los Empleados...", error);
        });
    };

    //BUSQUEDAS
    const BuscarPorNombre = async () => {
        if(nombreEmp.trim() === ""){
            CargarTodos();
            return;
        }
        try {
            const response = await buscarNombre(nombreEmp);
            setEmpleados(response.data);
        }catch(error){
            console.error("Error al buscar por Nombre los Empleados...", error);
        }
    };

    const BuscarPorPuesto = async () => {
        if(puesto.trim() === ""){
            CargarTodos();
            return;
        }
        try {
            const response = await buscarPuesto(puesto);
            setEmpleados(response.data);
        }catch(error){
            console.error("Error al buscar por Nombre los Empleados...", error);
        }
    };

    console.log("Empleado recibidos desde el backend: ", JSON.stringify(empleados, null, 2));

    return(
        <div className="empleados">
           <div className="empleados-header">
            {/* IZQUIERDA: título y botón */}
                <h2>Lista de Empleados</h2>
                <div className="empleados-header-izq">
                <button className="btn btn-primary mb-2" onClick={nuevoEmpleado}>
                Nuevo
                </button>
            </div>

            {/* DERECHA: panel de búsqueda */}
            <div className="empleados-busqueda">
                <h2>Búsquedas Empleados</h2>

                <div className="empleados-busqueda-linea">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Nombre o Letra..."
                    value={nombreEmp}
                    onChange={(e) => setNombreEmp(e.target.value)}
                />
                <button className="btn btn-primary" onClick={BuscarPorNombre}>
                    Buscar
                </button>
                </div>

                <div className="empleados-busqueda-linea">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Puesto..."
                    value={puesto}
                    onChange={(e) => setPuesto(e.target.value)}
                />
                <button className="btn btn-primary" onClick={BuscarPorPuesto}>
                    Buscar
                </button>
                 <button onClick={CargarTodos}>Ver Todos</button>
                </div>
            </div>
            </div>


            <table className="table table-striped">
                <thead>
                    <tr>
                        <th>Id Empleado</th>
                        <th>Nombre Empleado</th>
                        <th>Email</th>
                        <th>Puesto</th>
                        <th>Clave</th>
                        <th>Id Usuario</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {empleados.map((empleado) => (
                        <tr key={empleado.idEmpleado}>
                            <td>{empleado.idEmpleado}</td>
                            <td>{empleado.nombreEmp}</td>
                            <td>{empleado.email}</td>
                            <td>{empleado.puesto}</td>
                            <td>{empleado.clave}</td>
                            <td>{empleado.idUsuario}</td>
                            <td>
                                <button className="btn btn-info me-2" onClick={() => verDetalle(empleado.idEmpleado)}>
                                    Detalles
                                </button>
                                <button aria-disabled="page" className="btn btn-info me-2" onClick={() => actualizaEmpleado(empleado.idEmpleado)}>
                                    Actualizar
                                </button>
                                <button aria-disabled="page" className="btn btn-danger" onClick={() => eliminarEmpleado(empleado.idEmpleado)}>
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