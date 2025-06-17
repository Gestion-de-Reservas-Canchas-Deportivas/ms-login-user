package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.ReporteReservaDTO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReporteExportService {

    public byte[] generarExcel(List<ReporteReservaDTO> datos) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reporte Canchas");

            // Encabezados
            String[] columnas = {"Cancha", "Tipo", "Reservas", "Horas", "% Ocupación"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Filas de datos
            int rowIdx = 1;
            for (ReporteReservaDTO dto : datos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getNombreCancha());
                row.createCell(1).setCellValue(dto.getTipoCancha());
                row.createCell(2).setCellValue(dto.getTotalReservas());
                row.createCell(3).setCellValue(dto.getHorasUtilizadas());
                row.createCell(4).setCellValue(dto.getPorcentajeOcupacion() + "%");
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al generar Excel", e);
        }
    }

    public byte[] generarPdf(List<ReporteReservaDTO> datos) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            Paragraph title = new Paragraph("Reporte de Uso de Canchas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2, 1, 1, 1});

            // Encabezados sin color
            Stream.of("Cancha", "Tipo", "Reservas", "Horas", "% Ocupación").forEach(col -> {
                PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            });

            // Filas
            for (ReporteReservaDTO dto : datos) {
                table.addCell(new PdfPCell(new Phrase(dto.getNombreCancha(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(dto.getTipoCancha(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(dto.getTotalReservas()), cellFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(dto.getHorasUtilizadas()), cellFont)));
                table.addCell(new PdfPCell(new Phrase(dto.getPorcentajeOcupacion() + "%", cellFont)));
            }

            doc.add(table);
            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }
}
