package com.tutorial.receipt_service.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.tutorial.receipt_service.entity.Receipt;
import com.tutorial.receipt_service.model.Client;
import com.tutorial.receipt_service.model.Reservation;
import com.tutorial.receipt_service.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;

@Service
public class ReceiptService {

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    RestTemplate restTemplate;

    public Receipt getById(int id) {
        return receiptRepository.findById(id).orElse(null);
    }

    private byte[] createPDF(Receipt receipt, Reservation reservation, Client client) {
        try {
            // Crear un documento
            Document document = new Document();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, byteArrayOutputStream);

            // Abrir documento
            document.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Detalle de la Reserva", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Información de la Reserva
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("Información de la Reserva:", sectionFont));
            document.add(new Paragraph("Código de la reserva: " + reservation.getId()));
            document.add(new Paragraph("Fecha y hora de la reserva: " +
                    reservation.getYear() + "-" + reservation.getMonth() + "-" + reservation.getDay() + " " + reservation.getStart()));
            document.add(new Paragraph("Número de vueltas o tiempo máximo reservado: " + reservation.getTrackTime()));
            document.add(new Paragraph("Cantidad de personas incluidas: " + reservation.getPeopleQuantity()));
            document.add(new Paragraph("Nombre de persona que hizo la reserva: " + reservation.getClientName()));
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Detalle de pago
            document.add(new Paragraph("Detalle de pago de cada integrante de la reserva:", sectionFont));

            // Crear la tabla para el detalle de pagos
            PdfPTable table = new PdfPTable(6); // 6 columnas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Encabezados de la tabla
            String[] headers = new String[]{
                    "Nombre del Cliente", "Tarifa Base", "Descuento por Tamaño de Grupo",
                    "Descuento por Fidelidad/Promoción", "Valor del IVA", "Monto Total"
            };
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Añadir los datos del cliente de la reserva
            PdfPCell cellName = new PdfPCell(new Phrase(client.getName()));
            table.addCell(cellName);

            PdfPCell cellTarifaBase = new PdfPCell(new Phrase(reservation.getBasePrice().toString()));
            table.addCell(cellTarifaBase);

            PdfPCell cellDescuentoGrupo = new PdfPCell(new Phrase(reservation.getDiscountQuantity() + "%"));
            table.addCell(cellDescuentoGrupo);

            PdfPCell cellDescuentoFidelidad = new PdfPCell(new Phrase((reservation.getDiscountFidelity() + reservation.getDiscountBirthday()) + "%"));
            table.addCell(cellDescuentoFidelidad);

            PdfPCell cellIVA = new PdfPCell(new Phrase(reservation.getIva().toString()));
            table.addCell(cellIVA);

            PdfPCell cellMontoTotal = new PdfPCell(new Phrase(reservation.getTotalPrice().toString()));
            table.addCell(cellMontoTotal);

            // Añadir la tabla al documento
            document.add(table);

            // Cerrar documento
            document.close();

            return byteArrayOutputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Receipt save(Receipt receipt) {
        Client client = restTemplate.getForObject("http://client-service/Client/" + receipt.getClientId(), Client.class);
        Reservation reservation = restTemplate.getForObject("http://reservation-service/Reservation/" + receipt.getReservationId(), Reservation.class);
        receipt.setPdf(createPDF(receipt, reservation, client));
        receipt.setEmailSended(true);
        return receiptRepository.save(receipt);
    }

    public Receipt delete(int id) {
        Receipt receipt = receiptRepository.findById(id).orElse(null);
        if(receipt != null) {
            receiptRepository.deleteById(id);
            return receipt;
        }
        return null;
    }
}
