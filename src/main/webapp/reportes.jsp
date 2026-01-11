<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Modelo.Usuario" %>
<%@page import="DAO.*" %>
<%@page import="java.util.*" %>
<%
    // Obtener el valor del rol desde el alcance de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String rol = usuario.getRol();
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
        <link href="css/reportesCSS.css" rel="stylesheet" type="text/css"/>
        <title>Futbol Retro - Reportes</title>
        <link rel="icon" href="img/logo(Nuevo).png" type="image/x-icon">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {'packages': ['corechart']});
            google.charts.setOnLoadCallback(drawVisualization);

            function drawVisualization() {
                // Some raw data (not necessarily accurate)
                var data = google.visualization.arrayToDataTable([
                    ['PRODUCTOS', 'Cam. Retro', 'Cam. Actuales', 'Balones'],
                    ['Junio', 1, 1, 1],
                    ['Julio', 11, 18, 15],
                    ['Agosto', 1, 1, 1]
                ]);

                var options = {
                    chartArea:{width:'40%',height:'45%'},
                    title: 'PRODUCTOS VENDIDOS MENSUALES',
                    fontSize: 20,
                    seriesType: 'bars',
                    series: {5: {type: 'line'}}
                };

                var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
                chart.draw(data, options);
            }
        </script>
        <script type="text/javascript">
            google.charts.load("current", {packages: ["corechart"]});
            google.charts.setOnLoadCallback(drawChart);
            function drawChart() {
                var data = google.visualization.arrayToDataTable([
                    ['Task', 'Hours per Day'],
                    ['Cam. Retro', 11],
                    ['Cam. Actuales', 8],
                    ['Balones', 15]
                ]);

                var options = {
                    title: 'MAYORES PRODUCTOS VENDIDOS',
                    backgroundColor: {fill: "white"},
                    fontSize: 20,
                    is3D: true,
                };

                var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
                chart.draw(data, options);
            }
        </script>
    </head>
    <body>
        <div id="chart_div" style="width: 900px; height: 500px;"></div>
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
                        <a href="login.jsp">
                            <i class="fa-solid fa-arrow-right-from-bracket fa-flip-horizontal"></i>
                            <p>SALIR</p>
                        </a>
                    </div>
                </div>
                <!-- -->


                <!-- Aqui irán las diferentes ventanas (solo es copiar el mismo formato, crear un jsp con nombre "Productos" y aqui empezar a programar-->
                <!-- Aca puedes cambiar el nombre del class y hacer un css nuevo para este div que sera la ventana reportes -->
                <div class="info">
                    <div id="piechart_3d" ></div>
                </div>
            </div>
        </div>
    </body>
</html>
