import React, { useState, useEffect } from "react";
import { nuevoEmpleado, getEmpleado, updateEmpleado } from "../../service/EmpleadoService";
import { useNavigate, useParams } from "react-router-dom";

export const EmpleadoComponent = () => {
  const [nombreEmp, setNombreEmp] = useState("");
  const [puesto, setPuesto] = useState("");
  const { idEmpleado } = useParams();
  const [clave, setClave] = useState("");
  const [email, setEmail] = useState("");
  const [idUsuario, setIdUsuario] = useState(null);
  
  const navegar = useNavigate();

  useEffect(() => {
    if (idEmpleado) {
      getEmpleado(idEmpleado)
        .then((response) => {
          setNombreEmp(response.data.nombreEmp || "");
          setPuesto(response.data.puesto || "");
          setClave(response.data.clave || "")
           if (response.data.usuarioId?.idUsuario) {
            setIdUsuario(response.data.usuarioId);
          }
          setEmail(response.data.email || "");
        })
        .catch((error) => console.error(error));
    }
  }, [idEmpleado]);

  const validaForm = (empleado) => {
    if (!empleado.nombreEmp?.trim()) {
      alert("El nombre es obligatorio");
      return false;
    }
    if (!empleado.puesto?.trim()) {
      alert("El puesto es obligatorio");
      return false;
    }
     if (!empleado.clave?.trim()) {
      alert("La clave es obligatorio");
      return false;
    }
    if (!empleado.email?.trim()) {
      alert("el Email es obligatorio");
      return false;
    }
    return true;
  };

  const saveEmpleado = (e) => {
    e.preventDefault();
    const empleado = { nombreEmp, puesto, clave,
    usuario: {
    username: nombreEmp,
    password: clave,
    rol: puesto,
    email: email
  },
  email
     };

    if (validaForm(empleado)) {
      if (idEmpleado) {
        updateEmpleado(idEmpleado, empleado)
          .then((res) => {
            console.log(res.data);
            navegar("/empleado/listaEmp");
          })
          .catch((err) => console.error(err));
      } else {
        nuevoEmpleado(empleado)
          .then((res) => {
            console.log("Empleado creado: ", res.data);
            navegar("/empleado/listaEmp");
          })
          .catch((err) => console.error(err));
      }
    }
  };

  const pagTitulo = () => idEmpleado ? "Modificar Empleado" : "Nuevo Empleado";

  return (
    <div className="empleados">
      <h2 className="text-center">{pagTitulo()}</h2>
      <form onSubmit={saveEmpleado}>
        <p>
          <label>
            Nombre del Empleado:<br />
            <input
              type="text"
              value={nombreEmp}
              onChange={(e) => setNombreEmp(e.target.value)}
              placeholder="Ingresa el nombre"
              required
            />
          </label>
        </p>
         <p>
          <label>
            Email del Empleado:<br />
            <input
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Ingresa el email"
              required
            />
          </label>
        </p>
        <p>
          <label>
            Puesto:<br />
            <input
              type="text"
              value={puesto}
              onChange={(e) => setPuesto(e.target.value)}
              placeholder="Ingresa el puesto"
              required
            />
          </label>
        </p>

        <p>
          <label>
            Clave (contraseña del usuario):<br />
            <input
              type="password"
              value={clave}
              onChange={(e) => setClave(e.target.value)}
              placeholder="Ingresa contraseña"
              required
            />
          </label>
        </p>

        <p>
          <button type="submit" className="btn btn-success">Guardar</button>
          <button
            type="button"
            className="btn btn-secondary ms-2"
            onClick={() => navegar("/empleado/listaEmp")}
          >
            Cancelar
          </button>
        </p>
      </form>
    </div>
  );
};