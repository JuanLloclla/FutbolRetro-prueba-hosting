<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="paginaActual" value="${requestScope.paginaActual}" />
<c:set var="paginaActualSinControlador" value="${param.paginaActualSinControlador}" />
<!DOCTYPE html>
<div class="menu">
    <a href="srvCaja?accion=default" class="${paginaActual eq 'caja' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-cash-register"></i>
            <p>CAJA</p>
        </div>
    </a>

    <a href="srvCliente?accion=Listar" class="${paginaActual eq 'cliente' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-address-book"></i>
            <p>CLIENTES</p>
        </div>
    </a>

    <a href="srvUsuario?accion=Listar" class="${paginaActual eq 'usuario' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-user"></i>
            <p>USUARIOS</p>
        </div>
    </a>

    <a href="srvProducto?accion=Listar" class="${paginaActual eq 'producto' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-shop"></i>
            <p>PRODUCTOS</p>
        </div>
    </a>

    <a href="srvVenta?accion=Listar" class="${paginaActual eq 'ventas' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-file-lines"></i>
            <p>VENTAS</p>
        </div>
    </a>

    <a href="reportes.jsp?paginaActualSinControlador=reportes" class="${paginaActualSinControlador eq 'reportes' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-chart-column"></i>
            <p>REPORTES</p>
        </div>
    </a>
    
    <a href="guia.jsp?paginaActualSinControlador=guia" class="${paginaActualSinControlador eq 'guia' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-circle-info"></i>
            <p>GUIA</p>
        </div>
        
    </a><a href="quejasAyuda.jsp?paginaActualSinControlador=quejasAyuda" class="${paginaActualSinControlador eq 'quejasAyuda' ? 'activo' : ''}">
        <div class="menu-tarjetas">
            <i class="fa-solid fa-envelope"></i>
            <p>INFORMES</p>
        </div>
    </a>

</div>

