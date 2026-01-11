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

/**
 *
 * @author Juan
 */
@WebServlet(name = "srvLogin", urlPatterns = {"/srvLogin"})
public class srvLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        
        try {
            if(accion!=null){
                switch (accion) {
                    case "INGRESAR": VerificarLogin(request,response); break;
                    default: response.sendRedirect("login.jsp");
                }
            }else{
                response.sendRedirect("login.jsp"); //regresa al login
            }
        } catch (Exception e) {
            System.out.println("ERROR... "+e.getMessage());
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
        processRequest(request, response);
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
        processRequest(request, response);
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
    
    private void VerificarLogin(HttpServletRequest request, HttpServletResponse response){
        try {
            UsuarioDAO logindao;
            Usuario userLogin = null;
            userLogin = new Usuario();
            userLogin.setUsuario(request.getParameter("txtusuario"));
            userLogin.setClave(request.getParameter("txtclave"));
            logindao = new UsuarioDAO();
            Usuario usuarioLogin = logindao.ValidarUsuario(userLogin);
            if(usuarioLogin!=null){
                // Si el usuario es válido, redirigir a la página principal
                request.getSession().setAttribute("usuario", usuarioLogin);
                response.sendRedirect("index.jsp");
            }else{
                // Si el usuario no es válido, redirigir de vuelta a la página de inicio de sesión con un mensaje de error
                request.setAttribute("mensaje","Credenciales incorrectas...");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
        }
    }
}
