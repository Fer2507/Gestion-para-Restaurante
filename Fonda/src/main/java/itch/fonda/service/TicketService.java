package itch.fonda.service;

import itch.fonda.dto.VentaDto;

public interface TicketService {
	byte[] generarTicket(VentaDto  venta) throws Exception; 
}
