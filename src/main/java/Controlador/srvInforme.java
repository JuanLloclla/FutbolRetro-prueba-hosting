package Controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.InformeDAO;
import Modelo.Informe;

import java.util.Properties;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;

@WebServlet(name = "srvInforme", urlPatterns = {"/srvInforme"})
public class srvInforme extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        InformeDAO informeDAO = new InformeDAO();

        if (accion != null) {
            switch (accion) {
                case "agregar":
                    String descripcion = request.getParameter("descripcion");
                    int idusuario = Integer.parseInt(request.getParameter("idUsuario"));
                    String nombreusuario = request.getParameter("nomUsuario");
                    String apellidousuario = request.getParameter("apelUsuario");
                    String correoUsuario = request.getParameter("email");

                    Informe informe = new Informe();
                    informe.setDescripcion(descripcion);
                    informe.setIdUsuario(idusuario);
                    informeDAO.agregarInforme(informe);
                    
                    // Configuración de JavaMail para enviar correo
                    String host = "smtp.gmail.com";
                    String port = "587"; // Puerto para TLS
                    String user = "futbolretrosoporte@gmail.com"; // Cambiar por tu dirección de correo
                    String password = "ATGFLKAOOQJMPTBW"; // Cambiar por tu contraseña de Gmail

                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", host);
                    props.put("mail.smtp.port", port);

                    final String finalUser = user;
                    final String finalPassword = password;

                    Session session = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(finalUser, finalPassword);
                        }
                    });
                    
                    try {
                        // Crear el mensaje de correo
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(user));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user)); // Dirección de correo destinatario
                        message.setSubject("NUEVA QUEJAS O AYUDA");
                        message.setText("Datos del Empleado/Usuario\nCorreo: "+ correoUsuario + "\nNombre: "+nombreusuario+"\nApellido: "+apellidousuario+"\n\nDescripción:\n"+descripcion);
                        

                        // Enviar el mensaje
                        Transport.send(message);

                    } catch (MessagingException e) {
                        System.out.println("Error al enviar el correo: " + e.getMessage());
                    }
                
                    response.sendRedirect("quejasAyuda.jsp");
                    break;

                default:
                    response.sendRedirect("quejasAyuda.jsp");
                    break;
            }
        } else {
            response.sendRedirect("quejasAyuda.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
