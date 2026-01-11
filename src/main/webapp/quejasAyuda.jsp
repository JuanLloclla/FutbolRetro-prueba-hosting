<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Cliente"%>
<%@page import="DAO.*" %>
<%@page import="java.util.*" %>
<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = usuario.getRol();
    int idUsuario = usuario.getId();
    String nombre = usuario.getNombre();
    String apellido = usuario.getApellido();
    

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="//code.tidio.co/h3vn9dwczska9xxz82te7gjkkgbdyhn8.js" async></script>
        <link href="css/navegadorCSS.css" rel="stylesheet" type="text/css"/>
        <link href="css/informeCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Informes</title>
        <link rel="icon" href="img/logo(Nuevo).png" type="image/x-icon">


    </head>
    <body>
        <div class="container-fuera">
            <div class="container-dentro">

                <!-- Esto es el encabezado (navegador y el boton salir) -->
                <div class="navegador">
                    <%                        if (rol.equals("administrador")) {
                    %>
                    <!-- Esto es el menu de administrador -->
                    <%@ include file="navegador/menuAdmin.jsp" %>
                    <!-- -->
                    <%
                    } else {
                    %>
                    <!-- Esto es el menu de empleado -->
                    <%@ include file="navegador/menuEmpleado.jsp" %>
                    <!-- -->
                    <%
                        }
                    %>

                    <div class="exit">
                        <a href="login.jsp">
                            <i class="fa-solid fa-arrow-right-from-bracket fa-flip-horizontal"></i>
                            <p>SALIR</p>
                        </a>
                    </div>
                </div>
                <!-- -->


                <!-- Aqui irán las diferentes ventanas (solo es copiar el mismo formato, crear un jsp con nombre "Productos" y aqui empezar a programar-->
                <!-- Aca puedes cambiar el nombre del class y hacer un css nuevo para este div que sera la ventana productos -->
                <div class="info">

                    <div class="container">
                        <h1 class="titulo">Quejas y Ayuda</h1>
                        <p class="texto">Escribe tu queja o solicitud de ayuda:</p>
                        <form action="srvInforme" method="post">
                            <input type="hidden" name="accion" value="agregar">
                            <input type="hidden" name="idUsuario" value="<%= idUsuario %>">
                            <input type="hidden" name="nomUsuario" value="<%= nombre %>">
                            <input type="hidden" name="apelUsuario" value="<%= apellido %>">
                            <input type="email" name="email" class="gmail" placeholder="Ingresa tu correo electrónico" required>
                            <textarea name="descripcion" placeholder="Escribe aquí..."></textarea>
                            <button class="enviarQueja" type="submit">Enviar</button>
                        </form>
                    </div>
                    <div class="support container">
                        <h2>Soporte Técnico</h2>
                        <p>Si encuentras algún problema o tienes alguna duda, por favor contacta al soporte técnico enviando un correo a <a href="mailto:soporte@futbolretro.com">futbolretrosoporte@gmail.com</a>.</p>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>