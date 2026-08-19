import React from "react";
import { Navigate } from "react-router-dom";
import { getToken } from "../../service/AuthService";

export const PrivateRoute = ({ children, requiredRoles = [], requiredPermisos = [] }) => {
  const token = getToken();
  const userRol = localStorage.getItem("rol");
  const permisosStored = localStorage.getItem("permisos");
  const userPermisos = permisosStored ? JSON.parse(permisosStored) : [];

  if (!token) return <Navigate to="/" replace />;

  // Roles requeridos
  if (requiredRoles.length > 0 && !requiredRoles.includes(userRol)) {
    return <Navigate to="/no-autorizado" replace />;
  }

  // Permisos requeridos
  if (requiredPermisos.length > 0) {
    const tienePermiso = requiredPermisos.some(p => userPermisos.includes(p));
    if (!tienePermiso) return <Navigate to="/no-autorizado" replace />;
  }

  return children; // Todo ok
};