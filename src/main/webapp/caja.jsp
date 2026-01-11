<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.*" %>

<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = usuario.getRol();

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
        <link href="css/cajaCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Caja</title>
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
                <!-- Aca puedes cambiar el nombre del class y hacer un css nuevo para este div que sera la ventana caja -->
                <div class="info">
                    <!--AVISAN PLIS SI ESTA BIEN :'( -->

                    <div class="container-caja">
                        <h2 class="text-center" id="titulo-caja">CAJA DE VENTA</h2>

                        <div class="ventana-caja">
                            <!-- Formulario de venta en el lado izquierdo -->
                            <div class="izquierda">
                                <form id="formulario-venta" action="srvCaja" method="post">
                                    <div class="body-datos">

                                        <label>Datos del Cliente</label>
                                        <div class="form-group">
                                            <div class="buscarCliente">
                                                <input type="text" name="dnicliente" value="${c.getDni()}" placeholder="DNI" class="form-control">
                                                <button type="submit" class="btn btn-outline-info" name="accion" value="BuscarCliente">Buscar</button>
                                            </div>
                                            <div class="cliente">
                                                <input type="text" name="nombrecliente" placeholder="Datos Cliente" value="${c.getNombre()} ${c.getApellido()}" class="form-control">
                                            </div>
                                        </div>

                                        <label>Datos del producto</label>
                                        <div class="form-group">
                                            <div class="buscarProducto">
                                                <input type="text" name="codigoproducto" value="${producto.getIdProd()}" placeholder="Codigo" class="form-control">
                                                <button type="submit" class="btn btn-outline-info" name="accion" value="BuscarProducto">Buscar</button>
                                            </div>
                                            <div class="producto">
                                                <input type="text" name="nombreproducto" value="${producto.getDescripcion()}" placeholder="Datos Producto" class="form-control">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="productoPrecio">
                                                <input type="text" name="precio" value="${producto.getPrecio()}" class="form-control" placeholder="S/. 0.00" >
                                            </div>
                                            <div class="productoCantidad">
                                                <input type="number" name="cant" value="1" placeholder="Cantidad" class="form-control">
                                            </div>
                                            <div class="productoStock">
                                                <input type="text" name="stock" value="Stock: ${producto.getStock()}" placeholder="Stock" class="form-control">
                                            </div>
                                        </div>
                                        <!-- BOTON PARA AGREGAR PRODUCTO AL REGISTRO -->
                                        <div class="boton-agregar">
                                            <button type="submit" class="btn btn-outline-info" name="accion" value="Agregar" id="agregarventa">Agregar</button>
                                        </div>
                                    </div>
                                </form>

                            </div>

                            <!-- Tabla para ver los productos-->
                            <!-- Tabla de ventas -->
                            <div class="derecha">
                                <div class="nroserie">
                                    <label>Nro. de Venta:</label>
                                    <input type="text" name="NroSerie" value="${nserie}" class="form-control">
                                </div>
                                <div class="scroll">
                                    <table class="table" id="tabla-ventas">
                                        <thead class="cabecera-venta">
                                            <tr>
                                                <th style="width: 55px;">Nro</th>
                                                <th style="width: 250px;">Descripcion</th>
                                                <th >Precio</th>
                                                <th>IGV</th>
                                                <th>Precio F.</th>
                                                <th>Cantidad</th>
                                                <th >SubTotal</th>
                                                <th class="accion">Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody class="contenido-venta">
                                            <c:forEach var="list" items="${listaVentas}" varStatus="status">
                                                <tr>
                                                    <td style="text-align: center; vertical-align: middle;">${status.index + 1}</td>
                                                    <td style="vertical-align: middle;">${list.getDescripcionProd()}</td>
                                                    <td style="text-align: center; vertical-align: middle;">${list.getPrecio()}</td>
                                                    <td style="text-align: center; vertical-align: middle;">${list.getIgv()}</td>
                                                    <td style="text-align: center; vertical-align: middle;">${list.getPrecioFinal()}</td>
                                                    <td style="text-align: center; vertical-align: middle;">${list.getCantProd()}</td>
                                                    <td style="text-align: center; vertical-align: middle;">${list.getSubtotal()}</td>
                                                    <td style="text-align: center; vertical-align: middle;">
                                                        <a class="btn btn-danger" href="srvCaja?accion=Eliminar&idItem=${status.index}"><i class="fa-solid fa-trash-can" style="color: #ffffff;"></i></a> 
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="card-footer">
                                    <div class="montoTotal">
                                        <input type="text" name="txtTotal" value="S/. ${totalpagar}0" class="form-control">
                                    </div>
                                    <form action="srvCaja" method="post">
                                        <div class="col-sm-5">
                                            <!-- Opciones de Boleta o Factura -->
                                            <div class="comprobantePago">
                                                <label>Tipo de Comprobante</label>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="tipoComprobante" id="boleta" value="boleta" checked>
                                                    <label class="form-check-label" for="boleta">Boleta</label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="tipoComprobante" id="factura" value="factura">
                                                    <label class="form-check-label" for="factura">Factura</label>
                                                </div>
                                            </div>
                                            <button type="submit" class="btn btn-success" name="accion" value="GenerarVenta">Generar Venta</button>
                                            <button type="submit" class="btn btn-danger" name="accion" value="default" >Cancelar</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
