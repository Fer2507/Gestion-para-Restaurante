package itch.fonda.service.impl;


import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import itch.fonda.dto.VentaDetalleDto;
import itch.fonda.dto.VentaDto;
import itch.fonda.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	@Override
	public byte[] generarTicket(VentaDto  venta) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Document doc = new Document(PageSize.A6);
	    PdfWriter.getInstance(doc, baos);

	    doc.open();

	    Font tituloFont = new Font(Font.HELVETICA, 14, Font.BOLD);
	    Paragraph titulo = new Paragraph("RESTAURANTE FONDA\n\n", tituloFont);
	    titulo.setAlignment(Element.ALIGN_CENTER);
	    doc.add(titulo);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    doc.add(new Paragraph("Fecha: " + venta.getFechaCompra().format(formatter)));
	    doc.add(new Paragraph("ID Venta: " + venta.getIdVenta()));

	    String nombreCliente = (venta.getNombreCliente() != null && !venta.getNombreCliente().isEmpty())
	            ? venta.getNombreCliente()
	            : "Cliente no registrado";
	    doc.add(new Paragraph("Cliente: " + nombreCliente));

	    String nombreEmpleado = (venta.getNombreEmpleado() != null && !venta.getNombreEmpleado().isEmpty())
	            ? venta.getNombreEmpleado()
	            : "Empleado desconocido";
	    doc.add(new Paragraph("Atendido por: " + nombreEmpleado));

	    if (venta.getIdReserva() != null) {
	        doc.add(new Paragraph("Reserva Asociada: #" + venta.getIdReserva()));
	    } else {
	        doc.add(new Paragraph("Reserva: No aplica"));
	    }

	    doc.add(new Paragraph("----------------------------------------"));

	    PdfPTable table = new PdfPTable(3);
	    table.setWidthPercentage(100);
	    table.setWidths(new float[]{3, 1, 2});

	    Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);
	    table.addCell(new Phrase("Producto", headerFont));
	    table.addCell(new Phrase("Cant", headerFont));
	    table.addCell(new Phrase("Subtotal", headerFont));

	    Font cellFont = new Font(Font.HELVETICA, 9);

	    for (VentaDetalleDto det : venta.getDetalles()) {
	        String nombreProducto = (det.getProducto() != null && det.getProducto().getNombre() != null)
	                ? det.getProducto().getNombre()
	                : "Producto desconocido";

	        table.addCell(new Phrase(nombreProducto, cellFont));
	        table.addCell(new Phrase(String.valueOf(det.getCantidad()), cellFont));
	        table.addCell(new Phrase(String.format("$%.2f", det.getSubtotal()), cellFont));
	    }

	    doc.add(table);
	    doc.add(new Paragraph("----------------------------------------"));

	    doc.add(new Paragraph("TOTAL: $" + String.format("%.2f", venta.getTotal()),
	            new Font(Font.HELVETICA, 12, Font.BOLD)));

	    doc.add(new Paragraph("\n¡Gracias por su compra!",
	            new Font(Font.HELVETICA, 10, Font.ITALIC)));

	    doc.close();
	    return baos.toByteArray();
	}
}
