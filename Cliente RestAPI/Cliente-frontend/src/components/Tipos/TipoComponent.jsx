import React, { useEffect, useState } from "react";
import { crearTipo, getTipo, updateTipo } from "../../service/TipoService";
import { useNavigate, useParams } from "react-router-dom";

export const TipoComponent = () => {
    const [nombreTipo, setNombreTipo] = useState("");
    const [descripcionTipo, setDescripcionTipo] = useState("");
    const { idTipo } = useParams();
    const navegar = useNavigate();

    const actualizarTipo = (e) => setNombreTipo(e.target.value);
    const actualizarDescripcion = (e) => setDescripcionTipo(e.target.value);

    const validaForm = () => {
        if (!nombreTipo.trim()) { alert("El Nombre del Tipo es obligatorio"); return false; }
        if (!descripcionTipo.trim()) { alert("La Descripción del Tipo es obligatoria"); return false; }
        return true;
    }

    const saveTipo = (e) => {
        e.preventDefault();
        if (!validaForm()) return;

        const tipo = { nombreTipo, descripcionTipo };

        if (idTipo) {
            updateTipo(idTipo, tipo)
                .then(() => navegar("/tipo/listati"))
                .catch(error => console.error(error));
        } else {
            crearTipo(tipo)
                .then(() => navegar("/tipo/listati"))
                .catch(error => console.error(error));
        }
    }

    useEffect(() => {
        if (idTipo) {
            getTipo(idTipo)
                .then(response => {
                    setNombreTipo(response.data.nombreTipo);
                    setDescripcionTipo(response.data.descripcionTipo);
                })
                .catch(error => console.error(error));
        }
    }, [idTipo]);

    return (
        <div className="tipo">
            <h2 className="text-center">{idTipo ? "Modificar Tipo" : "Añadir Tipo"}</h2>
            <form onSubmit={saveTipo}>
                <p>
                    <label>
                        Nombre del Tipo:<br />
                        <input type="text" value={nombreTipo} onChange={actualizarTipo} placeholder="Ingresa el tipo de comida" required />
                    </label>
                </p>
                <p>
                    <label>
                        Descripción:<br />
                        <input type="text" value={descripcionTipo} onChange={actualizarDescripcion} placeholder="Ingresa la descripción" required />
                    </label>
                </p>
                <p>
                    <button type="submit" className="btn btn-success" aria-disabled="page">Guardar</button>
                    <button type="button" className='btn btn-secondary ms-2' 
        onClick={() => navegar("/tipo/listati")}> Cancelar </button>
                </p>
            </form>
        </div>
    );
}
