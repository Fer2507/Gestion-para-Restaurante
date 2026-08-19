import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../service/AuthService";

export const LoginComponent = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await login(username, password);
      setUsername('');
      setPassword('');
      navigate("/"); // Ruta de inicio
    }  catch (error) {
      console.error("Error al iniciar sesión:", error);
      alert("Usuario o contraseña incorrectos");
    }
  };
  return (
    <div className="login-container">
      <form onSubmit={handleLogin} className="login-form">
        <h2>Iniciar Sesión</h2>

        <input
          type="text"
          placeholder="Usuario"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          />

        <input
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
         />

        {error && ( <p> {error}</p>)}
        <button type="submit">Ingresar</button>
        <button type="button" className='btn btn-secondary ms-2' 
        onClick={() => navigate("/")}>Inicio</button>
      </form>
    </div>
  );
};
