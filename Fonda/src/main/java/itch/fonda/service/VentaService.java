package itch.fonda.service;

import java.time.LocalDate;
import java.util.List;
import itch.fonda.dto.VentaDto;

public interface VentaService {

    VentaDto crearVenta(VentaDto ventaDto, Integer idEmpleado);

    VentaDto actualizarVenta(Integer idVenta, VentaDto ventaDto, Integer idEmpleado);

    VentaDto obtenerVentaPorId(Integer idVenta);

    List<VentaDto> obtenerVentasPorCliente(Integer idCliente);

    List<VentaDto> obtenerTodasVentas();

    void eliminarVenta(Integer idVenta);
    
    List<VentaDto> obtenerPorIdReserva(Integer idReserva);
    
    void eliminarPorIdReserva(Integer idReserva);
    
    List<VentaDto> buscarPorFecha(LocalDate fecha);
    
}
