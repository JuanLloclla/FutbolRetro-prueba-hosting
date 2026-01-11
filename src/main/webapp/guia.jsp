<%@page import="Modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = usuario.getRol();


%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="//code.tidio.co/h3vn9dwczska9xxz82te7gjkkgbdyhn8.js" async></script>
        <link href="css/navegadorCSS.css" rel="stylesheet" type="text/css"/>
        <link href="css/guiaCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Guia</title>
        <link rel="icon" href="img/logo(Nuevo).png" type="image/x-icon">
    </head>
    <body>
        <div class="container-fuera">
            <div class="container-dentro">
                <!-- Esto es el encabezado (navegador y el boton salir) -->
                <div class="navegador">
                    <% if (rol.equals("administrador")) { %>
                    <!-- Esto es el menu de administrador -->
                    <%@ include file="navegador/menuAdmin.jsp" %>
                    <% } else { %>
                    <!-- Esto es el menu de empleado -->
                    <%@ include file="navegador/menuEmpleado.jsp" %>
                    <% }%>
                    <div class="exit">
                        <a href="login.jsp">
                            <i class="fa-solid fa-arrow-right-from-bracket fa-flip-horizontal"></i>
                            <p>SALIR</p>
                        </a>
                    </div>
                </div>


                <!-- Aqui irán las diferentes ventanas -->

                <div class="info">
                    <h1 class="titulo">Guía de Uso del Sistema de Control de Ventas</h1>
                    <p class="resumen">Bienvenido al sistema de control de ventas para apuestas. Esta guía te ayudará a navegar y utilizar las diferentes funcionalidades del sistema. Sigue las instrucciones a continuación según tu rol (Administrador o Empleado).</p>

                    <div class="section">
                        <h2 class="subtitulo">Funciones para Administradores</h2>
                        <ul class="lista">
                            <li><strong>Gestión de Usuarios:</strong> Permite agregar, editar y eliminar usuarios del sistema. Para acceder a esta función, navega a Gestionar Usuarios en el menú de administrador.</li>
                            <li><strong>Reportes:</strong> Genera reportes detallados de ventas y actividad del sistema. Esta opción está disponible en Reportes.</li>
                            <li><strong>Configuración del Sistema:</strong> Modifica las configuraciones generales del sistema, como parámetros de seguridad y preferencias de usuario.</li>
                        </ul>
                    </div>

                    <div class="section">
                        <h2 class="subtitulo">Funciones para Empleados</h2>
                        <ul class="lista">
                            <li><strong>Registro de Ventas:</strong> Permite registrar nuevas ventas en el sistema. Esta función está disponible en Ventas en el menú de empleado.</li>
                            <li><strong>Consulta de Ventas:</strong> Consulta el historial de ventas realizadas. Accede a esta función en el apartado Historial de Ventas.</li>
                        </ul>
                    </div>

                    <div class="section instructions">
                        <h2 class="subtitulo">Instrucciones Generales</h2>
                        <ul class="lista">
                            <li><strong>Inicio de Sesión:</strong> Utiliza tus credenciales proporcionadas para iniciar sesión en el sistema.</li>
                            <li><strong>Navegación:</strong> Usa el menú de la parte superior para acceder a las diferentes secciones según tu rol.</li>
                            <li><strong>Cerrar Sesión:</strong> Para salir del sistema, haz clic en el botón SALIR ubicado en la esquina superior derecha.</li>
                        </ul>
                    </div>
                </div>




            </div>
        </div>
    </body>
</html>
