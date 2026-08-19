import React, { useState, useEffect } from "react";
import { nuevaMesa, getMesa, updateMesa } from "../../service/MesaService";
import { useNavigate, useParams } from "react-router-dom";

export const MesaComponent = () => {
    const [numero, setNumero] = useState(0);
    const [capacidad, setCapacidad] = useState(0);
    const [ubicacion, setubicacion] = useState("");
    const [estado, setEstado] = useState("Disponible");
    const { idMesa } = useParams();
    const navegar = useNavigate();

    useEffect(() => {
        if (idMesa) {
          getMesa(idMesa)
            .then((response) => {
              setNumero(response.data.numero);
              setCapacidad(response.data.capacidad);
              setubicacion(response.data.ubicacion);
              setEstado(response.data.estado || "Disponible");
            })
            .catch((error) => console.error(error));
        }
      }, [idMesa]);

       const validaForm = (mesa) => {
  if (!mesa.numero || mesa.numero <= 0) {
    alert("El número de mesa es obligatorio y debe ser mayor que 0");
    return false;
  }
  if (!mesa.capacidad || mesa.capacidad <= 0) {
    alert("La capacidad de la mesa es obligatoria y debe ser mayor que 0");
    return false;
  }
  if (!mesa.ubicacion?.trim()) {
    alert("La ubicación de la mesa es obligatoria");
    return false;
  }
  if (!mesa.estado?.trim()) {
    alert("El estado de la mesa es obligatorio");
    return false;
  }
  return true;
};

      
        const saveMesa = (e) => {
          e.preventDefault();
          const mesa = { numero, capacidad, ubicacion,estado};
      
          if (validaForm(mesa)) {
            if (idMesa) {
              updateMesa(idMesa, mesa)
                .then((res) => {
                  console.log(res.data);
                  navegar("/mesa/listMesa");
                })
                .catch((err) => console.error(err));
            } else {
              nuevaMesa(mesa)
                .then((res) => {
                  console.log("Mesa creada: ", res.data);
                  navegar("/mesa/listMesa");
                })
                .catch((err) => console.error(err));
            }
          }
        };
    const pagTitulo = () => idMesa ? "Modificar Mesa" : "Nueva Mesa";
    return(

        <div className="mesas">
        <h2 className="text-center">{pagTitulo()}</h2>
        <form onSubmit={saveMesa}>
            <p>
            <label>
                Nùmero de mesa:<br />
                <input
                type="number"
                value={numero}
                onChange={(e) => setNumero(e.target.value)}
                placeholder="Ingresa el Numero"
                required
                />
            </label>
            </p>
            <p>
            <label>
                Capacidad:<br />
                <input
                type="number"
                value={capacidad}
                onChange={(e) => setCapacidad(e.target.value)}
                placeholder="Numero de personas para la mesa"
                required
                />
            </label>
            </p>
             <label>
                Ubicacion:<br />
                <input
                type="text"
                value={ubicacion}
                onChange={(e) => setubicacion(e.target.value)}
                placeholder="Ingresa la Ubicaciond e la mesa"
                required
                />
            </label>
             <label>
                Estado:<br />
                <input
                type="text"
                value={estado}
                onChange={(e) => setEstado(e.target.value)}
                placeholder="Ingresa el Estado de la mesa"
                required
                />
            </label>
            <p>
            <button type="submit" className="btn btn-success">Guardar</button>
            <button type="button" className="btn btn-secondary ms-2" onClick={() => navegar("/mesa/listMesa")}>
                Cancelar
            </button>
            </p>
        </form>
        </div>
    );
}