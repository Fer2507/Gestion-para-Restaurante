package itch.reservaciones.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import itch.reservaciones.dto.ReservaDto;

public interface ReservaService {
	//REGISTAR NUEVA RESERVA
	ReservaDto registrarReserva(ReservaDto dto);
	//OBTENER TODAS LAS RESERVAS
    List<ReservaDto> obtenerTodas();
    //OBTENER RESERVA POR ID
    ReservaDto obtenerPorId(Integer idReserva);
    //OBTENER LISTA POR IDCLIENTE
    List<ReservaDto> obtenerPorCliente(Integer idCliente);
    //ACTUALIZAR RESERVA
    ReservaDto actualizarReserva(Integer idReserva, ReservaDto dto);
    //ELIMINAR RESERVA
    void eliminarReserva(Integer idReserva);
    //LISTAR RESERVAS POR ESATUS
    List<ReservaDto> obtenerPorEstatus(String estatus);
    //CAMBIAR ESTATUS
    ReservaDto cambiarEstatus(Integer idReserva, String nuevoEstatus);
    //CANCELAR RESERVA Y ELIMINAR PEDIDOS ASOCIADOS
    void cancelarReservaConPedidos(Integer idReserva);
    
    List<ReservaDto> buscarPorFecha(LocalDate fecha);
    
}
