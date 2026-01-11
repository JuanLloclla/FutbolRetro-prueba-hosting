package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

import DAO.ClienteDAO;
import Modelo.Cliente;
import Documentos.*;
import com.itextpdf.text.DocumentException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
@WebServlet(name = "srvCliente", urlPatterns = {"/srvCliente"})
public class srvCliente extends HttpServlet {
    
    int idCliente;
    ClienteDAO clientedao = new ClienteDAO();
    GenerarExcel generarexcel = new GenerarExcel();
    GenerarPDF generarpdf = new GenerarPDF();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws com.itextpdf.text.DocumentException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        
        if (accion != null) {
            switch (accion) {
                case "Listar":
                    List lista = clientedao.obtenerTodosLosClientes();
                    request.setAttribute("listaClientes", lista);
                    request.setAttribute("paginaActual", "cliente");
                    request.getRequestDispatcher("clientes.jsp").forward(request, response);
                    break;
                    
                case "agregar":
                    int dni = Integer.parseInt(request.getParameter("dni"));
                    String nombre = request.getParameter("nombre");
                    String apellido = request.getParameter("apellido");
                    int telefono = Integer.parseInt(request.getParameter("telefono"));
                    String correo = request.getParameter("correo");
                    cliente.setIdCliente(dni);
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setTelefono(telefono);
                    cliente.setCorreo(correo);
                    clienteDAO.agregarCliente(cliente);
                    request.getRequestDispatcher("srvCliente?accion=Listar").forward(request, response);
                    break;
                    
                case "editar":
                    idCliente = Integer.parseInt(request.getParameter("id"));
                    Cliente clienteid = clienteDAO.obtenerCliente(idCliente);
                    request.setAttribute("clienteSeleccionado", clienteid);
                    request.getRequestDispatcher("srvCliente?accion=Listar").forward(request, response);
                    break;
                
                case "actualizar":
                    int dniUpdate = Integer.parseInt(request.getParameter("dni"));
                    String nombreUpdate = request.getParameter("nombre");
                    String apellidoUpdate = request.getParameter("apellido");
                    int telefonoUpdate = Integer.parseInt(request.getParameter("telefono"));
                    String correoUpdate = request.getParameter("correo");
                    cliente.setDni(dniUpdate);
                    cliente.setNombre(nombreUpdate);
                    cliente.setApellido(apellidoUpdate);
                    cliente.setTelefono(telefonoUpdate);
                    cliente.setCorreo(correoUpdate);
                    cliente.setIdCliente(idCliente);
                    clienteDAO.actualizarCliente(cliente);
                    request.getRequestDispatcher("srvCliente?accion=Listar").forward(request, response);
                    break;
                
                case "eliminar":
                    idCliente = Integer.parseInt(request.getParameter("id"));
                    clienteDAO.eliminarCliente(idCliente);
                    request.getRequestDispatcher("srvCliente?accion=Listar").forward(request, response);
                    break;
                    
                case "exportarExcel":
                    List<Cliente> clientesExcel = clientedao.obtenerTodosLosClientes();
                    String nombreArchivo = "clientes.xlsx";

                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

                    try (OutputStream outputStream = response.getOutputStream()) {
                        generarexcel.crearExcelClientes(clientesExcel, outputStream); // Llamada al generador de Excel
                    }

                    break;

                case "exportarPDF":
                    List<Cliente> clientesPDF = clientedao.obtenerTodosLosClientes();
                    String nombreArchivoPDF = "cliente.pdf";

                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivoPDF);

                    try (OutputStream outputStream = response.getOutputStream()) {
                        generarpdf.crearPDFClientes(clientesPDF, outputStream); // Llamada al generador de PDF
                    }
                    break;
                    
                default:
                    request.getRequestDispatcher("srvCliente?accion=Listar").forward(request, response);
            }
            
        } else {
            response.sendRedirect("clientes.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(srvCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(srvCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
