import React, { useState, useEffect } from "react";
import { crearProductos, getProducto, subirImagen, updateProducto } from "../../service/ProductoService";
import { useNavigate, useParams } from "react-router-dom";
import { listTipos } from "../../service/TipoService";

export const ProductoComponent = () => {
  const [nombreProducto, setNombreProducto] = useState("");
  const [descripcionProducto, setDescripcionProducto] = useState("");
  const [precioProducto, setPrecioProducto] = useState("");
  const [idTipo, setidTipo] = useState("");
  const [nombreFoto, setNombreFoto] = useState(null);
  const [ver, setVer] = useState(null);
  const { idPro } = useParams();
  const navegar = useNavigate();
  const [tipos, setTipos] = useState([]);   // Lista de tipos disponibles
  const[file, setFile] = useState(null);
  
  const actualizarNombreProducto = (e) => setNombreProducto(e.target.value);
  const actualizarDescripcionProducto = (e) => setDescripcionProducto(e.target.value);
  const actualizarPrecioProducto = (e) => setPrecioProducto(e.target.value);
  const actualizaridTipo = (e) => setidTipo(e.target.value);
  const actualizarFoto = (e) => setNombreFoto(e.target.value);

  const archivos = (e) => {
    const archivo = e.target.files[0];
    setFile(archivo);
    setNombreFoto(archivo);

    const reader = new FileReader();
    reader.onload = () => setVer(reader.result);
    reader.readAsDataURL(archivo);

  };

 const saveProducto = async (e) => {
  e.preventDefault();

   const producto = {
      nombreProducto,
      descripcionProducto,
      precioProducto,
      idTipo,
    };

  if (!validaForm(producto)) return;

  try {
    let idGuardado = idPro;

  if (idPro) {
    await updateProducto(idPro, producto);
    idGuardado = idPro;
     
  } else {
    const response = await crearProductos(producto);
    idGuardado = response.data.idProducto;
  }

  if(file && idGuardado){
    console.log("Archivo que se sube:", file);
    
    const formData = new FormData();
    formData.append("imagen", file);
    await subirImagen(idGuardado, formData);
  }
  
  navegar("/producto/listapro");
    } catch (error) {
      console.error("Error al guardar:", error);
    }
};
  const pagTituloPro = () => {
    if (idPro) {
      return <h2 className="text-center">Modificar Producto</h2>;
    } else {
      return <h2 className="text-center">Nuevo Producto</h2>;
    }
  };

const validaForm = (producto) => {
        if (!producto.nombreProducto || producto.nombreProducto.trim() === "") {
            alert("El nombre del producto es obligatorio");
            return false;
        }
        if (!producto.precioProducto || producto.precioProducto === "" || isNaN(producto.precioProducto)) {
            alert("El precio es obligatorio y debe ser un número");
            return false;
        }
        if (!producto.descripcionProducto || producto.descripcionProducto.trim() === "") {
            alert("La decripcion debe de ser obligatoria");
            return false;
        }
        if(!producto.idTipo || producto.idTipo === "" || isNaN(producto.idTipo) )
        {
          alert("El producto debe tener un Tipo");
          return false;
        }
        return true;
    };

  useEffect(() => {
    if (idPro) {
      getProducto(idPro)
        .then((response) => {
          setNombreProducto(response.data.nombreProducto);
          setDescripcionProducto(response.data.descripcionProducto);
          setPrecioProducto(response.data.precioProducto);
          setidTipo(response.data.idTipo);

          if(response.data.nombreFoto){
            setVer(`http://localhost:7071/api/producto/img/${response.data.nombreFoto}`);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [idPro]);

  //Cargar los tipos
  useEffect(() => {
    listTipos() // Servicio que devuelve todos los tipos de comida
      .then((response) => setTipos(response.data))
      .catch((error) => console.error("Error al cargar tipos:", error));
  }, []);

  return (
    <div  className="productos">
      {pagTituloPro()}
      <form onSubmit={saveProducto}>
        <h2>Registro de Producto</h2>

        <p>
          <label>
            Nombre del Producto:<br />
            <input
              type="text"
              value={nombreProducto}
              onChange={actualizarNombreProducto}
              placeholder="Ingresa el nombre"
              required
            />
          </label>
        </p>

        <p>
          <label>
            Descripción:<br />
            <input
              type="text"
              value={descripcionProducto}
              onChange={actualizarDescripcionProducto}
              placeholder="Ingresa la descripción"
              required
            />
          </label>
        </p>

        <p>
          <label>
            Precio:<br />
            <input
              type="number"
              value={precioProducto}
              onChange={actualizarPrecioProducto}
              placeholder="Ingresa el precio"
              required
            />
          </label>
        </p>

        <p>
          <label>
            Tipo:<br />
           <select
              value={idTipo}
              onChange={actualizaridTipo}
              required
            >
              <option value="">-- Selecciona un tipo --</option>
              {tipos.map((tipo) => (
                <option key={tipo.id} value={tipo.id}>
                  {tipo.nombreTipo} {/* Ej: Platillo, Bebida, Postre */}
                </option>
              ))}
            </select>
          </label>
        </p>
        <p>
          Imagen: <input type="file" name="imagen"  onChange={archivos} />
        </p>
        {ver && (
          <p>
            <strong> Vista Previa: </strong><br />
            <img src={ver} alt="Vista Previa" width="200" style={{borderRadius: "10px"}}/>
          </p>
        )}
        <p>
          <button type="submit" className="btn btn-success" aria-disabled="page">
            Guardar
          </button>
          <button type="button" className='btn btn-secondary ms-2' 
        onClick={() => navegar("/producto/listapro")}> Cancelar </button>
        </p>
      </form>
    </div>
  );
};
