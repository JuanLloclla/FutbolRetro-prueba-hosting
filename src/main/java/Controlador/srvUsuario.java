package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UsuarioDAO;
import Modelo.Usuario;
import Documentos.*;
import com.itextpdf.text.DocumentException;

import java.util.List;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Juan
 */
@WebServlet(name = "srvUsuario", urlPatterns = {"/srvUsuario"})
public class srvUsuario extends HttpServlet {
    
    int idUsuario;
    UsuarioDAO usuariodao = new UsuarioDAO();
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
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        
        if (accion != null) {
            switch (accion) {
                case "Listar":
                    List lista = usuariodao.RecuperarRegistrosUsuario();
                    request.setAttribute("listausuarios", lista);
                    request.setAttribute("paginaActual", "usuario");
                    request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                    break;
                    
                case "agregar":
                    String nombre = request.getParameter("nombre");
                    String apellido = request.getParameter("apellido");
                    int dni = Integer.parseInt(request.getParameter("dni"));
                    String user = request.getParameter("usuario");
                    String clave = request.getParameter("contrasena");
                    String rol = request.getParameter("rol");
                    usuario.setNombre(nombre);
                    usuario.setApellido(apellido);
                    usuario.setDni(dni);
                    usuario.setUsuario(user);
                    usuario.setClave(clave);
                    usuario.setRol(rol);
                    usuarioDAO.InsertarUsuarios(usuario);
                    request.getRequestDispatcher("srvUsuario?accion=Listar").forward(request, response);
                    
                    break;
                    
                case "editar":
                    idUsuario = Integer.parseInt(request.getParameter("id"));
                    Usuario usuarioid = usuariodao.MostrarUsuarioEditar(idUsuario);
                    request.setAttribute("usuarioSeleccionado", usuarioid);
                    request.getRequestDispatcher("srvUsuario?accion=Listar").forward(request, response);
                    
                    break;
                
                case "actualizar":
                    String nombreUpdate = request.getParameter("nombre");
                    String apellidoUpdate = request.getParameter("apellido");
                    int dniUpdate = Integer.parseInt(request.getParameter("dni"));
                    String userUpdate = request.getParameter("usuario");
                    String claveUpdate = request.getParameter("contrasena");
                    String rolUpdate = request.getParameter("rol");
                    usuario.setNombre(nombreUpdate);
                    usuario.setApellido(apellidoUpdate);
                    usuario.setDni(dniUpdate);
                    usuario.setUsuario(userUpdate);
                    usuario.setClave(claveUpdate);
                    usuario.setRol(rolUpdate);
                    usuario.setId(idUsuario);
                    usuarioDAO.ActualizarUsuario(usuario);
                    request.getRequestDispatcher("srvUsuario?accion=Listar").forward(request, response);
                    
                    break;
                
                case "eliminar":
                    idUsuario = Integer.parseInt(request.getParameter("id"));
                    usuarioDAO.EliminarRegistroUsuario(idUsuario);
                    request.getRequestDispatcher("srvUsuario?accion=Listar").forward(request, response);
                    
                    break;
                    
                case "exportarExcel":
                    List<Usuario> usuariosExcel = usuariodao.RecuperarRegistrosUsuario();
                    String nombreArchivo = "usuarios.xlsx";

                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

                    try (OutputStream outputStream = response.getOutputStream()) {
                        generarexcel.crearExcelUsuarios(usuariosExcel, outputStream); // Llamada al generador de Excel
                    }

                    break;

                case "exportarPDF":
                    List<Usuario> usuariosPDF = usuariodao.RecuperarRegistrosUsuario();
                    String nombreArchivoPDF = "usuarios.pdf";

                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivoPDF);

                    try (OutputStream outputStream = response.getOutputStream()) {
                        generarpdf.crearPDFUsuarios(usuariosPDF, outputStream); // Llamada al generador de PDF
                    }
                    break;
                    
                default: request.getRequestDispatcher("srvUsuario?accion=Listar").forward(request, response);
            }
            
        } else {
            response.sendRedirect("usuarios.jsp");
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
            Logger.getLogger(srvUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(srvUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
