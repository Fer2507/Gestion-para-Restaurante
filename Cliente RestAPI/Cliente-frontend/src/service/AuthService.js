import axios from "axios";

const API_URL = "http://localhost:7073/auth"; // microservicio de seguridad

// Login: envía usuario y contraseña
export const login = async (username, password) => {
  try {
    const response = await axios.post(`${API_URL}/login`, {
      username,
      password,
    });

    // Guardar token y datos del usuario
    const { token, rol, username: userName, permisos } = response.data;
    localStorage.setItem("token", token);
    localStorage.setItem("rol", rol);
    localStorage.setItem("username", userName);
    localStorage.setItem("permisos", JSON.stringify(permisos));

    return response.data;
  } catch (error) {
    console.error("Error en el login:", error);
    throw error;
  }
};

// Obtener token guardado
export const getToken = () => localStorage.getItem("token");

// Logout
export const logout = () => {
  localStorage.clear();
};

// === Clientes Axios con JWT ===
export const axiosPublic = axios.create({
  baseURL: "http://localhost:7070" 
});

export const axiosSeguridad = axios.create({
  baseURL: "http://localhost:7073",
});

export const axiosFonda = axios.create({
  baseURL: "http://localhost:7071",
});

export const axiosTaller = axios.create({
  baseURL: "http://localhost:7070",
});

export const axiosReservaciones = axios.create({
  baseURL: "http://localhost:7072",
});

export const axiosUsuarios = axios.create({
  baseURL: "http://localhost:7073",
});

// Interceptor para agregar token
const clientes = [axiosSeguridad, axiosTaller, axiosFonda, axiosReservaciones, axiosUsuarios];

clientes.forEach((cliente) => {
  cliente.interceptors.request.use((config) => {
    const token = getToken();
    if (token) config.headers.Authorization = `Bearer ${token}`;
    console.log(config.headers)
    return config;
  });
});
