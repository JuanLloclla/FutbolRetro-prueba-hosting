<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Obtener el parámetro idVenta de la solicitud
    String idVentaParam = (String) request.getAttribute("idVenta");
    String comprobantePago = (String) request.getAttribute("comprobantePago");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link href="css/ventaCompletaCSS.css" rel="stylesheet" type="text/css"/>
        <title>Venta Completada</title>
        <link rel="icon" href="img/logo(Nuevo).png" type="image/x-icon">
    </head>
    <body>
        <div class="container py-4">
            <div class="modal fade" id="mi-modal" data-bs-backdrop="static">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header d-flex justify-content-end align-items-center">
                            <a href="srvCaja?accion=default" class="containerX">
                                <button class="btn-close" data-bs-dismiss="modal" style="margin: 0;"></button>
                            </a>
                        </div>
                        <div class="d-flex justify-content-center align-items-center">
                            <i class="fa-solid fa-circle-check" id="iconoVenta" style="color: #1bc073;"></i>
                        </div>
                        <div class="modal-body">
                            <p>Su compra ha sido exitosa</p>
                        </div>
                        <div class="modal-footer d-flex justify-content-center">
                            <a href="srvCaja?accion=ComprobantePago&idVenta=<%=idVentaParam%>&comprobantePago=<%=comprobantePago%>" target="_blank">
                                <button class="btn btn-dark btn-spacing">Ver Comprobante Pago</button>
                            </a>
                            <a href="srvCaja?accion=default">
                                <button class="btn btn-secondary btn-spacing">Volver</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        
        <script>
            // script para mostar el modal ni bien la pagina se carga
            document.addEventListener("DOMContentLoaded", function () {
                var myModal = new bootstrap.Modal(document.getElementById('mi-modal'), {});
                myModal.show();
            });
        </script>
    </body>
</html>
