<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.Usuario" %>
<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario2 = (Usuario) session.getAttribute("usuario");
    String rol = usuario2.getRol();
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="//code.tidio.co/h3vn9dwczska9xxz82te7gjkkgbdyhn8.js" async></script>
        <link href="css/navegadorCSS.css" rel="stylesheet" type="text/css"/>
        <link href="css/indexCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro</title>
        <link rel="icon" href="img/logo(Nuevo).png" type="image/x-icon">
        
    </head>
    <body>
        <div class="container-fuera">
            <div class="container-dentro">

                <!-- Esto es el encabezado (navegador y el boton salir) -->
                <div class="navegador">
                    <%
                        if (rol.equals("administrador")) {
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
                        <a href="srvLogin?accion=cerrar">
                            <i class="fa-solid fa-arrow-right-from-bracket fa-flip-horizontal"></i>
                            <p>SALIR</p>
                        </a>
                    </div>
                </div>
                <!-- -->


                <!-- Aqui irán las diferentes ventanas (solo es copiar el mismo formato, crear un jsp con nombre "Productos" y aqui empezar a programar-->
               <div class="info" style="text-align: center;">
                    <div class="container">
                        <br><h1 style="font-size: 54px; margin-bottom: 30px; font-weight: bold;">Bienvenido a fútbol retro</h1>
                        <hr style="border: 1px solid black; margin-bottom: 20px;">
                        <p>Aquí es donde realizarás los diversos controles, como productos, ventas, usuarios. Si hay problemas o tienes dudas, ve a guías o informes. Gracias</p>
                        <img src="img/sergioramos.gif" alt=""/>
                    </div>
                </div>





            </div>
        </div>
    </body>
</html>
