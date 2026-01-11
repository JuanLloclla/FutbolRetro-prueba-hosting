<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.*" %>
<%@page import="DAO.*" %>
<%@page import="java.util.*" %>
<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = usuario.getRol();

    // Obtener la lista de los productos
    List<DetalleVenta> listaVentas = (List<DetalleVenta>) request.getAttribute("listaVentas");

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
        <link href="css/ventasCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Ventas</title>
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
                <!-- Aca puedes cambiar el nombre del class y hacer un css nuevo para este div que sera la ventana ventas -->
                <div class="info">
                    <h3 class="text-center" id="titulotabla">Lista de Ventas Realizadas</h3>
                    <div class="scroll">
                        <table class="table">
                            <thead>
                                <%
                                    if (listaVentas != null && !listaVentas.isEmpty()) {
                                        int item = 1;
                                        int currentIdVenta = -1; // Variable para rastrear el idVenta actual
                                        int idVent;
                                        for (DetalleVenta ventas : listaVentas) {
                                            
                                            if (ventas.getIdVenta() != currentIdVenta) {
                                                if (currentIdVenta != -1) { // Cierra el tbody si no es el primer idVenta
                                %>
                                </tbody>
                                <%
                                    }
                                    item = 1;
                                    currentIdVenta = ventas.getIdVenta();
                                    
                                %>
                                <tr class="cabecera">
                                    <th>IdVenta: <%= ventas.getIdVenta()%></th>
                                    <th>Nro.Serie: <%= ventas.getNumSerie()%></th>
                                    <th>Fecha: <%= ventas.getFecha()%></th>
                                    <th>Cliente: <%= ventas.getNomCliente()%> <%= ventas.getApellCliente()%></th>
                                    <th>Monto Total: <%= ventas.getMontoTotal()%></th>
                                    
                                    <th><a class="btn btn-danger" href="srvVenta?accion=eliminar&id=<%= ventas.getIdVenta()%>" <% if(rol.equals("empleado")){ %> id="disabled" <%}%>><i class="fa-solid fa-trash-can" style="color: #ffffff;"></i></a></th>
                                </tr>
                                <tr class="cabecera2">
                                    <th>Nro.</th>
                                    <th>Descripcion</th>
                                    <th>Categoria</th>
                                    <th>Cantidad</th>
                                    <th>Precio</th>
                                    <th>SubTotal</th>
                                </tr>
                            </thead>
                            <tbody class="contenido">
                                <%
                                    }
                                %>
                                <tr>
                                    <td><%= item++%></td>
                                    <td><%= ventas.getDescripcion()%></td>
                                    <td><%= ventas.getCategoria()%></td>
                                    <td><%= ventas.getCantidadProd()%></td>
                                    <td><%= ventas.getPrecioProd()%></td>
                                    <td><%= ventas.getSubtotalProd()%></td>
                                </tr>
                                <%
                                    } // Cierra el último tbody 
                                %>
                            </tbody>
                            <%
                            } else {
                            %>
                            <tr>
                                <td colspan="6">No hay registro de ventas disponibles</td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>