package Documentos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import Modelo.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GenerarPDF {
    public void crearPDFClientes(List <Cliente> listaClientes, OutputStream outputStream)throws DocumentException, IOException{
        Document documento = new Document();
        PdfWriter.getInstance(documento, outputStream);
        
        documento.open();
        
        // Añadir título
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph titulo = new Paragraph("Lista de Clientes", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(Chunk.NEWLINE);
        
        // Crear la tabla
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{2, 2, 2, 2, 2, 5});
        
        // Añadir la cabecera de la tabla
        String[] cabecera = {"ID Cliente", "Dni", "Nombre", "Apellido", "Telefono", "Correo"};
        for (String tituloColumna : cabecera) {
            PdfPCell celda = new PdfPCell(new Phrase(tituloColumna));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(celda);
        }
        
        // Añadir los datos de los productos
        for (Cliente cliente : listaClientes) {
            tabla.addCell(String.valueOf(cliente.getIdCliente()));
            tabla.addCell(String.valueOf(cliente.getDni()));
            tabla.addCell(cliente.getNombre());
            tabla.addCell(cliente.getApellido());
            tabla.addCell(String.valueOf(cliente.getTelefono()));
            tabla.addCell(cliente.getCorreo());
        }

        documento.add(tabla);
        documento.close();
    }
    
    public void crearPDFUsuarios(List <Usuario> listaUsuarios, OutputStream outputStream)throws DocumentException, IOException{
        Document documento = new Document();
        PdfWriter.getInstance(documento, outputStream);
        
        documento.open();
        
        // Añadir título
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph titulo = new Paragraph("Lista de Usuarios", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(Chunk.NEWLINE);
        
        // Crear la tabla
        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{2, 2, 2, 2, 2, 3, 3});
        
        // Añadir la cabecera de la tabla
        String[] cabecera = {"ID Usuario", "Nombre", "Apellido", "Dni", "Usuario", "Clave", "Rol"};
        for (String tituloColumna : cabecera) {
            PdfPCell celda = new PdfPCell(new Phrase(tituloColumna));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(celda);
        }
        
        // Añadir los datos de los productos
        for (Usuario usuario : listaUsuarios) {
            tabla.addCell(String.valueOf(usuario.getId()));
            tabla.addCell(usuario.getNombre());
            tabla.addCell(usuario.getApellido());
            tabla.addCell(String.valueOf(usuario.getDni()));
            tabla.addCell(usuario.getUsuario());
            tabla.addCell(usuario.getClave());
            tabla.addCell(usuario.getRol());
        }

        documento.add(tabla);
        documento.close();
    }
    
    public void crearPDFProductos(List<Producto> listaProductos, OutputStream outputStream) throws DocumentException, IOException {
        Document documento = new Document();
        PdfWriter.getInstance(documento, outputStream);

        documento.open();

        // Añadir título
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph titulo = new Paragraph("Lista de Productos", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(Chunk.NEWLINE);

        // Crear la tabla
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{1, 3, 2, 1, 2});

        // Añadir la cabecera de la tabla
        String[] cabecera = {"ID Producto", "Descripción", "Precio sin IGV", "Stock", "Categoría"};
        for (String tituloColumna : cabecera) {
            PdfPCell celda = new PdfPCell(new Phrase(tituloColumna));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(celda);
        }

        // Añadir los datos de los productos
        for (Producto producto : listaProductos) {
            tabla.addCell(String.valueOf(producto.getIdProd()));
            tabla.addCell(producto.getDescripcion());
            tabla.addCell(String.valueOf(producto.getPrecio()));
            tabla.addCell(String.valueOf(producto.getStock()));
            tabla.addCell(producto.getCategoria());
        }

        documento.add(tabla);
        documento.close();
    }
}
