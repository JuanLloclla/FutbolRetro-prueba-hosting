<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Cliente"%>
<%@page import="DAO.*" %>
<%@page import="java.util.*" %>
<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = usuario.getRol();

    // Obtener la lista de los usuarios
    List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");

    // Obtener el usuario selecionado para editar
    Cliente clienteSeleccionado = (Cliente) request.getAttribute("clienteSeleccionado");

%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="//code.tidio.co/h3vn9dwczska9xxz82te7gjkkgbdyhn8.js" async></script>
        <link href="css/navegadorCSS.css" rel="stylesheet" type="text/css"/>
        <link href="css/clienteCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Clientes</title>
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
                <!-- Aca puedes cambiar el nombre del class y hacer un css nuevo para este div que sera la ventana cliente -->
                <div class="info">

                    <div class="container-cliente">
                        <div class="ventana-cliente">
                            <!-- Lado izquierdo: Formulario de registro -->
                            <div class="izquierda">
                                <h3 class="text-center">Registro de Clientes</h3>
                                <form id="formulario-cliente" action="srvCliente" method="post">
                                    <div class="form-group">
                                        <label for="dni">Dni:</label>
                                        <input type="number" class="form-control" id="dni" name="dni" value="<%= (clienteSeleccionado != null ? clienteSeleccionado.getDni() : "")%>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="nombre">Nombre:</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre" value="<%= (clienteSeleccionado != null ? clienteSeleccionado.getNombre() : "")%>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="apellido">Apellido:</label>
                                        <input type="text" class="form-control" id="apellido" name="apellido" value="<%= (clienteSeleccionado != null ? clienteSeleccionado.getApellido() : "")%>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="telefono">Teléfono:</label>
                                        <input type="tel" class="form-control" id="telefono" name="telefono" value="<%= (clienteSeleccionado != null ? clienteSeleccionado.getTelefono() : "")%>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="correo">Correo:</label>
                                        <input type="email" class="form-control" id="correo" name="correo" value="<%= (clienteSeleccionado != null ? clienteSeleccionado.getCorreo() : "")%>" required>
                                    </div>
                                    <div class="btn-group">
                                        <button type="submit" class="btn btn-primary" name="accion" value="agregar" id="agregar">Agregar</button>
                                        <button type="submit" class="btn btn-danger" name="accion" value="actualizar" id="actualizar">Actualizar</button>
                                    </div>
                                </form>
                                
                                    <div class="container-generador">   
                                        <a class="btnExcel" href="srvCliente?accion=exportarExcel"><img src="img/excel.png" alt="excel.png"/></a>
                                        <a class="btnPDF" href="srvCliente?accion=exportarPDF"><img src="img/pdf.png" alt="pdf.png"/></a>
                                    </div>
                            </div>
                            <!-- Lado derecho: Tabla de clientes -->
                            <div class="derecha">
                                <h3 class="text-center" id="titulotabla">Lista de Clientes</h3>
                                <div class="scroll">
                                    <table class="table">
                                        <thead class="cabecera">
                                            <tr>
                                                <th>DNI</th>
                                                <th>NOMBRE</th>
                                                <th>APELLIDO</th>
                                                <th>TELÉFONO</th>
                                                <th>CORREO</th>
                                                <th>ACCIONES</th>
                                            </tr>
                                        </thead>
                                        <tbody class="contenido">

                                            <%
                                                if (listaClientes != null) {
                                                    for (Cliente clientes : listaClientes) {%>
                                            <tr>
                                                <td><%= clientes.getDni()%></td>
                                                <td><%= clientes.getNombre()%></td>
                                                <td><%= clientes.getApellido()%></td>
                                                <td><%= clientes.getTelefono()%></td>
                                                <td><%= clientes.getCorreo()%></td>
                                                <td class="btn-group">
                                                    <!-- Aquí puedes agregar botones para acciones como editar o eliminar -->
                                                    <a class="btn btn-primary" href="srvCliente?accion=editar&id=<%= clientes.getIdCliente()%>"><i class="fa-solid fa-pen-to-square" style="color: #ffffff;"></i></a>
                                                    <a class="btn btn-danger" href="srvCliente?accion=eliminar&id=<%= clientes.getIdCliente()%>"><i class="fa-solid fa-trash-can" style="color: #ffffff;"></i></a>
                                                </td>
                                            </tr>
                                            <% }
                                            } else { %>
                                            <tr>
                                                <td colspan="6">No hay clientes disponibles</td>

                                            </tr>
                                            <% }%>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
