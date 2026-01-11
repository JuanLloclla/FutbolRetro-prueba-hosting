<%@page import="net.sf.jasperreports.engine.*" %>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="Config.Conexion" %>

<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>
<%@ page import="java.util.Properties" %>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Comprobante Pago</title>
    <link rel="icon" href="img/logo(Nuevo).png" type="image/x-icon">
</head>
<body>
    <%
    // Obtener el parámetro idVenta de la solicitud
    String idVentaParam = (String) request.getAttribute("idVenta");
    String comprobantePago = (String) request.getAttribute("comprobantePago");
    String accion = (String) request.getAttribute("accion");
    String correo = (String) request.getAttribute("correo");
    %>
    <%
    if (idVentaParam != null && !idVentaParam.isEmpty()) {
        Connection conn = null;
        try {
            // Usar la clase Conexion del paquete Config para obtener la conexión a la base de datos
            Conexion conexion = new Conexion();
            conn = conexion.con;
            
            
            // Seleccionar el archivo de reporte basado en el valor de comprobantePago
            File reportFile;
            if ("boleta".equalsIgnoreCase(comprobantePago)) {
                reportFile = new File(application.getRealPath("reportes/Boleta.jasper"));
            } else {
                reportFile = new File(application.getRealPath("reportes/Factura.jasper"));
            }
            
            
            Map parameters = new HashMap();
              

            //parameters.put("nombre_del_parametro_jasper", "valor que se manda");
            parameters.put("idVentaParam",idVentaParam);
            
            
            byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);
            
            if ("gmail".equals(accion)) {
                // Guardar el PDF temporalmente
                File tempFile = File.createTempFile("report_", ".pdf");
                
                // Escribir el byte array al archivo temporal
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(bytes);
                fos.close();

                // Aquí puedes agregar el código para enviar el PDF por correo utilizando Gmail
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
                final String finalPassword  = password;
                
                Session session2 = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(finalUser, finalPassword );
                    }
                });

                try {
                    // Crear el mensaje de correo
                    Message message = new MimeMessage(session2);
                    message.setFrom(new InternetAddress(user));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo)); // Dirección de correo destinatario
                    message.setSubject("COMPRA REALIZADA");

                    // Cuerpo del mensaje
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setText("¡Gracias por comprar en nuestra tienda Futbol Retro!\n\nSu pedido ha sido procesado con éxito.\n\nAdjunto el comprobante de pago.");

                    // Adjuntar el archivo PDF
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(tempFile);
                    attachmentPart.setDataHandler(new DataHandler(source));
                    attachmentPart.setFileName(tempFile.getName());

                    // Combinar el cuerpo del mensaje con el archivo adjunto
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                    multipart.addBodyPart(attachmentPart);

                    // Configurar el contenido del mensaje
                    message.setContent(multipart);

                    // Enviar el mensaje
                    Transport.send(message);

                    // Eliminar el archivo temporal después de un tiempo, si es necesario
                    tempFile.deleteOnExit();

                    // Redireccionar después de enviar el correo
                    request.setAttribute("idVenta", idVentaParam);
                    request.setAttribute("comprobantePago", comprobantePago);
                    request.getRequestDispatcher("/ventaCompleta.jsp").forward(request, response);

                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                    out.println("Error al enviar el correo: " + e.getMessage());
                }
                
            } else {
                // Visualizar el PDF en el navegador
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } else {
        out.println("No se proporcionó el parámetro idVenta.");
    }
    %>
</body>
</html>