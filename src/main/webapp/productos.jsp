<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Modelo.Producto" %>
<%@page import="DAO.*" %>
<%@page import="java.util.*" %>
<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = usuario.getRol();

    // Obtener la lista de los productos
    List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaproductos");

    // Obtener el prodcuto selecionado para editar
    Producto productoSeleccionado = (Producto) request.getAttribute("productoSeleccionado");

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
        <link href="css/productosCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Productos</title>
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

                    <div class="container-productos">
                        <div class="ventana-productos">
                            <!-- Lado izquierdo: Formulario de registro de productos -->
                            <div class="izquierda">
                                <h3 class="text-center">Registro de Productos</h3>
                                <form id="formulario-producto" action="srvProducto" method="post">
                                    <div class="form-group">
                                        <label for="descripcion">Descripción:</label>
                                        <input type="text" class="form-control" id="descripcion" name="descripcion" value="<%= (productoSeleccionado != null ? productoSeleccionado.getDescripcion() : "")%>"  required>
                                    </div>
                                    <div class="form-group">
                                        <label for="precio">Precio:</label>
                                        <input type="number" class="form-control" id="precio" name="precio" value="<%= (productoSeleccionado != null ? productoSeleccionado.getPrecio() : "")%>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="stock">Stock:</label>
                                        <input type="number" class="form-control" id="stock" name="stock" value="<%= (productoSeleccionado != null ? productoSeleccionado.getStock() : "")%>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="categoria">Categoría:</label>
                                        <select class="form-control" id="categoria" name="categoria" value="<%= (productoSeleccionado != null ? productoSeleccionado.getCategoria() : "")%>" required>
                                            <option value="CAMISETA CLUB ANTIGUA" <%= (productoSeleccionado != null ? (productoSeleccionado.getCategoria().equalsIgnoreCase("CAMISETA CLUB ANTIGUA") ? "selected" : "") : "")%>>CAMISETA CLUB ANTIGUA</option>
                                            <option value="CAMISETA CLUB ACTUAL" <%= (productoSeleccionado != null ? (productoSeleccionado.getCategoria().equalsIgnoreCase("CAMISETA CLUB ACTUAL") ? "selected" : "") : "")%>>CAMISETA CLUB ACTUAL</option>
                                            <option value="BALON" <%= (productoSeleccionado != null ? (productoSeleccionado.getCategoria().equalsIgnoreCase("BALON") ? "selected" : "") : "")%>>BALON</option>
                                            <!-- Agrega más opciones de categorías aquí si es necesario -->
                                        </select>
                                    </div>
                                    <div class="btn-group">
                                        <button type="submit" class="btn btn-primary" name="accion" value="agregar" id="agregar">Agregar</button>
                                        <button type="submit" class="btn btn-danger" name="accion" value="actualizar" id="actualizar">Actualizar</button>
                                    </div>
                                </form>
                                            <div class="container-generador">   
                                                <a class="btnExcel" href="srvProducto?accion=exportarExcel"><img src="img/excel.png" alt="excel.png"/></a>
                                                <a class="btnPDF" href="srvProducto?accion=exportarPDF"><img src="img/pdf.png" alt="pdf.png"/></a>
                                            </div>
                            </div>
                            <!-- Lado derecho: Tabla de productos -->
                            <div class="derecha">
                                <h3 class="text-center" id="titulotabla">Lista de Productos</h3>
                                <div class="scroll">
                                    <table class="table"> 
                                        <thead class="cabecera">
                                            <tr>
                                                <th>CODIGO</th>
                                                <th>DESCRIPCION</th>
                                                <th>P. SIN IGV</th>
                                                <th>STOCK</th>
                                                <th>CATEGORIA</th>
                                                <th>ACCIONES</th>
                                            </tr>
                                        </thead>
                                        <tbody class="contenido">
                                            <%
                                                if (listaProductos != null) {
                                                    for (Producto productos : listaProductos) {%>
                                            <tr>
                                                <td><%= productos.getIdProd()%></td>
                                                <td><%= productos.getDescripcion()%></td>
                                                <td>S/. <%= productos.getPrecio()%></td>
                                                <td><%= productos.getStock()%></td>
                                                <td><%= productos.getCategoria()%></td>                                           
                                                <td class="btn-group">
                                                    <!-- Aquí puedes agregar botones para acciones como editar o eliminar -->
                                                    <a class="btn btn-primary" style="width: 56px;" href="srvProducto?accion=editar&id=<%= productos.getIdProd()%>"><i class="fa-solid fa-pen-to-square" style="color: #ffffff;"></i></a>
                                                    <a class="btn btn-danger" style="width: 56px;" href="srvProducto?accion=eliminar&id=<%= productos.getIdProd()%>"><i class="fa-solid fa-trash-can" style="color: #ffffff;"></i></a>
                                                </td>
                                            </tr>
                                            <% }
                                        } else { %>
                                            <tr>
                                                <td colspan="6">No hay usuarios disponibles</td>
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