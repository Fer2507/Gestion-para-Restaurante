import { useState } from 'react'

import './App.css'
import { HeaderComponent } from './components/HeaderComponent'
import { FooterComponent } from './components/FooterComponent'
import {BrowserRouter,Route,Routes} from 'react-router-dom'
import { HomeComponent } from './components/HomeComponent'
import { LoginComponent } from "./components/Login/LoginComponent";
import { PrivateRoute } from "./components/Login/PrivateRoute";
import { LogoutComponent } from './components/Login/LogoutComponent'

import { ListProductoComponent } from "./components/Productos/ListProductoComponent"
import { ProductoComponent } from './components/Productos/ProductoComponent'
import { DetalleProductoComponent } from './components/Productos/DetalleProductoComponent'

import { ListClienteComponent } from "./components/Clientes/ListClienteComponent"
import { ClienteComponent } from './components/Clientes/ClienteComponent'
import { ClienteDetalleComponent } from './components/Clientes/DetalleClienteComponent'

import { ListTiposComponent } from './components/Tipos/ListTiposComponent'
import { TipoComponent } from './components/Tipos/TipoComponent'
import { DetalleTipoComponent } from './components/Tipos/DetalleTipoComponent'

import { VentasComponent } from './components/Ventas/VentasComponent'
import { ListVentaComponent } from './components/Ventas/ListVentasComponent'
import { DetalleVentasComponent } from './components/Ventas/DetalleVentasComponent'

import { EmpleadoComponent } from './components/Empleado/EmpleadoComponent'
import { ListEmpleadoComponent } from './components/Empleado/ListEmpleadoComponent'
import { DetalleEmpleado } from './components/Empleado/DetalleEmpleado'

import { MesaComponent } from './components/Mesas/MesaComponent'
import { ListMesaComponent } from './components/Mesas/ListMesaComponent'
import { DetalleMesa } from './components/Mesas/DetalleMesaComponent'

import { ReservacionComponents } from './components/Reservaciones/ReservacionComponent'
import { ListReservacionComponent } from './components/Reservaciones/ListReservacionComponent'
import { DetalleReservacion } from './components/Reservaciones/DetalleReservacion'

import { UsuarioComponent } from './components/Usuarios/UsuarioComponent'
import { UsuarioDetalleComponent } from './components/Usuarios/DetalleUsuarioComponent'
import { ListUsuariosComponent } from './components/Usuarios/ListUsuariosComponent'
import MisVentasComponent from './components/Empleado/MisVentasComponent'

function App() {
  const [count, setCount] = useState(0)

  return (
    <BrowserRouter>
      <HeaderComponent />
      <Routes>

        {/* Public */}
        <Route path="/" element={<HomeComponent/>}/>
        <Route path="/login" element={<LoginComponent />} />
        <Route path="/logout" element={<LogoutComponent />} />
        <Route path='/cliente/crear' element={<ClienteComponent/>} />
        {/** CLIENTES - Taller (puerto 7071) */}
        <Route path='/cliente/lista'element={
           <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","MESERO","SUPERVISOR"]} requiredPermisos={["LISTAR_CLIENTES"]}><ListClienteComponent/></PrivateRoute> } />
        {/**<Route path='/cliente/crear' element={
       <PrivateRoute requiredRoles={["ADMINISTRADOR", "CAJERO","SUPERVISOR"]} requiredPermisos={["CREAR_RESERVA", "GESTIONAR_CLIENTES"]}> <ClienteComponent/> </PrivateRoute>} />*/}
       <Route path='/cliente/edita/:id' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR", "CAJERO","SUPERVISOR"]} requiredPermisos={["GESTIONAR_CLIENTES"]}> <ClienteComponent/>  </PrivateRoute> } /> 
        <Route path='/cliente/detalle/:id' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","MESERO", "CAJERO","SUPERVISOR"]} requiredPermisos={["LISTAR_CLIENTES"]}> <ClienteDetalleComponent/> </PrivateRoute> } />

        {/** PRODUCTOS - Fonda (puerto 7070) */}
        <Route path='/producto/crear' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_PRODUCTOS"]}> <ProductoComponent/> </PrivateRoute> } />
        <Route path='/producto/listapro' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_PRODUCTOS"]}> <ListProductoComponent /> </PrivateRoute>  } />
        <Route path='/producto/editar/:idPro' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_PRODUCTOS"]}> <ProductoComponent />  </PrivateRoute> } />
        <Route path='/producto/detalle/:idPro' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR","MESERO"]} requiredPermisos={["GESTIONAR_PRODUCTOS"]}> <DetalleProductoComponent /> </PrivateRoute>}/>

        {/** TIPOS - Fonda (puerto 7070) */}
        <Route path='/tipo/crear' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_TIPOS"]}> <TipoComponent /> </PrivateRoute> } />
        <Route path='/tipo/listati' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_TIPOS"]}> <ListTiposComponent /> </PrivateRoute>} />
        <Route path='/tipo/edita/:idTipo' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_TIPOS"]}> <TipoComponent /> </PrivateRoute>} />
        <Route path='/tipo/detalle/:idTipo' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_TIPOS"]}> <DetalleTipoComponent />  </PrivateRoute> } />
        
        {/** VENTAS - Fonda (puerto 7070) */}
        <Route path='/ventas/crear' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","MESERO"]} requiredPermisos={["REALIZAR_VENTAS","GESTIONAR_RESERVAS"]}> <VentasComponent /></PrivateRoute> } />
        <Route path='/ventas/listaVen' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO"]} requiredPermisos={["REALIZAR_VENTAS","GESTIONAR_RESERVAS"]}> <ListVentaComponent /></PrivateRoute>} />
        <Route path='/ventas/editar/:idVenta' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO"]} requiredPermisos={["REALIZAR_VENTAS","GESTIONAR_RESERVAS"]}> <VentasComponent /> </PrivateRoute> } />
        <Route path='/ventas/detalle/:idVenta' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","MESERO"]} requiredPermisos={["REALIZAR_VENTAS","GESTIONAR_RESERVAS"]}> <DetalleVentasComponent /></PrivateRoute> } />
        <Route path='/ventas/mesero/mis-ventas' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","MESERO"]} requiredPermisos={["REALIZAR_VENTAS","GESTIONAR_RESERVAS"]}> <MisVentasComponent /></PrivateRoute> } />
        
        {/** EMPLEADOS - Reservaciones (puerto 7072) */}
        <Route path='/empleado/crear' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","SUPERVISOR"]} requiredPermisos={["GESTIONAR_EMPLEADOS"]}> <EmpleadoComponent /> </PrivateRoute> } />
        <Route path='/empleado/listaEmp' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","MESERO","SUPERVISOR"]} requiredPermisos={["GESTIONAR_EMPLEADOS","LISTAR_CLIENTES", "GESTIONAR_MESAS", "LISTAR_EMPLEADOS"]}> <ListEmpleadoComponent />  </PrivateRoute> } />
        <Route path='/empleado/edita/:idEmpleado' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","MESERO","SUPERVISOR"]} requiredPermisos={["GESTIONAR_EMPLEADOS", "LISTAR_EMPLEADOS"]}> <EmpleadoComponent /> </PrivateRoute> } />
        <Route  path='/empleado/detalle/:idEmpleado' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","MESERO","SUPERVISOR"]} requiredPermisos={["GESTIONAR_EMPLEADOS", "LISTAR_EMPLEADOS"]}> <DetalleEmpleado /> </PrivateRoute>  } />

        {/** MESAS - Reservaciones (puerto 7072) */}
        <Route path='/mesa/nueva' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","MESERO"]} requiredPermisos={["GESTIONAR_MESAS"]}> <MesaComponent />  </PrivateRoute> }  />
        <Route path='/mesa/listMesa' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","MESERO","CAJERO"]} requiredPermisos={["GESTIONAR_MESAS"]}> <ListMesaComponent /> </PrivateRoute>  } />
        <Route path='/mesa/edita/:idMesa' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR"]} requiredPermisos={["GESTIONAR_MESAS"]}> <MesaComponent /> </PrivateRoute> } />
        <Route path='/mesa/detalle/:idMesa' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","MESERO"]} requiredPermisos={["GESTIONAR_MESAS"]}> <DetalleMesa />  </PrivateRoute>   } />

        {/** RESERVACIONES - Reservaciones (puerto 7072) */}
        <Route  path='/reservas/crear' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CLIENTE","CAJERO"]} requiredPermisos={["CREAR_RESERVA","GESTIONAR_RESERVAS"]}> <ReservacionComponents /> </PrivateRoute> } />
        <Route path='/reservas/listReserva' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO"]} requiredPermisos={["GESTIONAR_RESERVAS"]}> <ListReservacionComponent /> </PrivateRoute> } />
        <Route path='/reservas/editar/:idReserva' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO"]} requiredPermisos={["GESTIONAR_RESERVAS"]}> <ReservacionComponents /> </PrivateRoute> } />
        <Route path='/reservas/detalle/:idReserva' element={
            <PrivateRoute requiredRoles={["ADMINISTRADOR","CAJERO","CLIENTE"]} requiredPermisos={["GESTIONAR_RESERVAS"]}> <DetalleReservacion />  </PrivateRoute> }  />
      
        {/** SEGURIDAD - Usuarios (puerto 7073) */}
        <Route path='/usuarios' element={
             <PrivateRoute requiredRoles={["ADMINISTRADOR"]}> <ListUsuariosComponent /> </PrivateRoute> } />
        <Route path='/usuarios/crear' element={ 
            <PrivateRoute requiredRoles={["ADMINISTRADOR"]}> <UsuarioComponent /> </PrivateRoute>} />
        <Route path='/usuarios/editar/:idUsuario' element={ 
            <PrivateRoute requiredRoles={["ADMINISTRADOR"]}> <UsuarioComponent /> </PrivateRoute> } />
        <Route path='/usuarios/detalle/:idUsuario' element={
             <PrivateRoute requiredRoles={["ADMINISTRADOR"]}> <UsuarioDetalleComponent /> </PrivateRoute> } />
      </Routes>
      <FooterComponent />
    </BrowserRouter>
  )
}

export default App