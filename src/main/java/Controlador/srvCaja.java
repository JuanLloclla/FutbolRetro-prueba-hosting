package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.*;
import Modelo.*;
import Config.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;

/**
 *
 * @author Juan
 */
@WebServlet(name = "srvCaja", urlPatterns = {"/srvCaja"})
public class srvCaja extends HttpServlet {
    
    Cliente c = new Cliente();
    ClienteDAO cdao = new ClienteDAO();

    Producto p = new Producto();
    ProductoDAO pdao = new ProductoDAO();

    Venta v = new Venta();
    VentaDAO vdao = new VentaDAO();

    List<Venta> lista = new ArrayList<>();

    ComprobantePago cp = new ComprobantePago();
    ComprobantePagoDAO cpdao = new ComprobantePagoDAO();

    int item;
    int cod;
    String descripcion;
    double precio;
    double igv;
    double precioFinal;
    int cant;
    double subtotal;
    double totalpagar;

    String comprobantePago;

    String numeroserie;
    String numeroComprobantePago;

    DecimalFormat df = new DecimalFormat("#.##");
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // Accion
        String accion = request.getParameter("accion");
        
        // Usuario activo
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        int idEmpleado = usuario.getId();
        
        
        if (accion != null) {
            switch (accion) {
                case "BuscarCliente":
                    
                    
                    String dniClienteStr = request.getParameter("dnicliente");
                    if (dniClienteStr != null && !dniClienteStr.isEmpty())  {
                        int dni = Integer.parseInt(dniClienteStr);
                        c.setDni(dni);
                        c = cdao.buscarDni(dni);
                        request.setAttribute("c", c);

                        
                    }
                    numeroserie = vdao.GenerarSerie();
                    if (numeroserie == null) {
                        numeroserie = "00000001";
                        request.setAttribute("nserie", numeroserie);
                    } else {
                        try {
                            int incrementar = Integer.parseInt(numeroserie);
                            GenerarSerie gs = new GenerarSerie();
                            numeroserie = gs.NumeroSerie(incrementar);
                            request.setAttribute("nserie", numeroserie);
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Error al generar el número de serie.");
                            request.setAttribute("nserie", numeroserie);
                            numeroserie = "00000001"; // Asignar un valor predeterminado en caso de error
                        }
                    }

                    request.setAttribute("nserie", numeroserie);
                    request.setAttribute("paginaActual", "caja");
                    request.getRequestDispatcher("caja.jsp").forward(request, response);
                    break;

                case "BuscarProducto":
                    int id = Integer.parseInt(request.getParameter("codigoproducto"));
                    p = pdao.MostrarProductoEditar(id);
                    request.setAttribute("c", c);
                    request.setAttribute("listaVentas", lista);
                    request.setAttribute("producto", p);
                    
                    
                    // Formatear el total a pagar con solo dos decimales
                    df.setMaximumFractionDigits(2);
                    String totalpagarFormateado = df.format(totalpagar);
                    // Convertir el igv formateado de nuevo a un número double
                    totalpagar = Double.parseDouble(totalpagarFormateado);
                    request.setAttribute("totalpagar", totalpagar);

                    numeroserie = vdao.GenerarSerie();
                    if (numeroserie == null) {
                        numeroserie = "00000001";
                        request.setAttribute("nserie", numeroserie);
                    } else {
                        try {
                            int incrementar = Integer.parseInt(numeroserie);
                            GenerarSerie gs = new GenerarSerie();
                            numeroserie = gs.NumeroSerie(incrementar);
                            request.setAttribute("nserie", numeroserie);
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Error al generar el número de serie.");
                            request.setAttribute("nserie", numeroserie);
                            numeroserie = "00000001"; // Asignar un valor predeterminado en caso de error
                        }
                    }
                    request.setAttribute("paginaActual", "caja");
                    request.setAttribute("nserie", numeroserie);
                    request.getRequestDispatcher("caja.jsp").forward(request, response);
                    break;

                case "Agregar":
                    totalpagar = 0.0;
                    item = item + 1;
                    cod = p.getIdProd();
                    descripcion = request.getParameter("nombreproducto");
                    precio = Double.parseDouble(request.getParameter("precio"));

                    igv = precio * 0.18;
                    // Formatear el igv con solo dos decimales
                    df.setMaximumFractionDigits(2);
                    String igvFormateado = df.format(igv);
                    // Convertir el igv formateado de nuevo a un número double
                    igv = Double.parseDouble(igvFormateado);

                    precioFinal = precio + igv;
                    // Formatear el precio final con solo dos decimales
                    df.setMaximumFractionDigits(2);
                    String preciofinalFormateado = df.format(precioFinal);
                    // Convertir el precio final formateado de nuevo a un número double
                    precioFinal = Double.parseDouble(preciofinalFormateado);
                    
                    cant = Integer.parseInt(request.getParameter("cant"));
                    
                    subtotal = precioFinal * cant;
                    // Formatear el subtotal con solo dos decimales
                    df.setMaximumFractionDigits(2);
                    String subtotalFormateado = df.format(subtotal);
                    // Convertir el subtotal formateado de nuevo a un número double
                    subtotal = Double.parseDouble(subtotalFormateado);

                    v = new Venta();
                    v.setItem(item);
                    v.setIdProducto(cod);
                    v.setDescripcionProd(descripcion);
                    v.setPrecio(precio);
                    v.setIgv(igv);
                    v.setPrecioFinal(precioFinal);
                    v.setCantProd(cant);
                    v.setSubtotal(subtotal);
                    lista.add(v);
                    for (int i = 0; i < lista.size(); i++) {
                        totalpagar = totalpagar + lista.get(i).getSubtotal();
                    }
                    
                    
                    request.setAttribute("c", c);
                    
                    // Formatear el total a pagar con solo dos decimales
                    df.setMaximumFractionDigits(2);
                    String totalpagarFormateado2 = df.format(totalpagar);
                    // Convertir el igv formateado de nuevo a un número double
                    totalpagar = Double.parseDouble(totalpagarFormateado2);
                    request.setAttribute("totalpagar", totalpagar);
                    
                    request.setAttribute("listaVentas", lista);

                    numeroserie = vdao.GenerarSerie();
                    if (numeroserie == null) {
                        numeroserie = "00000001";
                        request.setAttribute("nserie", numeroserie);
                    } else {
                        try {
                            int incrementar = Integer.parseInt(numeroserie);
                            GenerarSerie gs = new GenerarSerie();
                            numeroserie = gs.NumeroSerie(incrementar);
                            request.setAttribute("nserie", numeroserie);
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Error al generar el número de serie.");
                            request.setAttribute("nserie", numeroserie);
                            numeroserie = "00000001"; // Asignar un valor predeterminado en caso de error
                        }
                    }
                    request.setAttribute("paginaActual", "caja");
                    request.setAttribute("nserie", numeroserie);
                    request.getRequestDispatcher("caja.jsp").forward(request, response);
                    break;
                case "Eliminar":
                    int idItem = Integer.parseInt(request.getParameter("idItem"));
                    if (idItem >= 0 && idItem < lista.size()) {
                        lista.remove(idItem);
                        totalpagar = 0.0;
                        for (Venta venta : lista) {
                            totalpagar += venta.getSubtotal();
                        }
                    }
                    request.setAttribute("c", c);
                    
                    // Formatear el total a pagar con solo dos decimales
                    df.setMaximumFractionDigits(2);
                    String totalpagarFormateado3 = df.format(totalpagar);
                    // Convertir el igv formateado de nuevo a un número double
                    totalpagar = Double.parseDouble(totalpagarFormateado3);
                    request.setAttribute("totalpagar", totalpagar);
                    
                    request.setAttribute("listaVentas", lista);
                    numeroserie = vdao.GenerarSerie();
                    if (numeroserie == null) {
                        numeroserie = "00000001";
                        request.setAttribute("nserie", numeroserie);
                    } else {
                        try {
                            int incrementar = Integer.parseInt(numeroserie);
                            GenerarSerie gs = new GenerarSerie();
                            numeroserie = gs.NumeroSerie(incrementar);
                            request.setAttribute("nserie", numeroserie);
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Error al generar el número de serie.");
                            request.setAttribute("nserie", numeroserie);
                            numeroserie = "00000001"; // Asignar un valor predeterminado en caso de error
                        }
                    }
                    request.setAttribute("paginaActual", "caja");
                    request.setAttribute("nserie", numeroserie);
                    request.getRequestDispatcher("caja.jsp").forward(request, response);

                    break;
                case "GenerarVenta":
                    //Actualizar el Stock
                    for (int i = 0; i < lista.size(); i++) {
                        Producto pr = new Producto();
                        int cantidad = lista.get(i).getCantProd();
                        int idproducto = lista.get(i).getIdProducto();
                        ProductoDAO pd = new ProductoDAO();
                        pr = pd.buscar(idproducto);
                        int stockAct = pr.getStock() - cantidad;
                        pd.actualizarStock(idproducto, stockAct);
                    }

                    //guardar venta
                    v.setIdClienteVenta(c.getIdCliente());
                    v.setIdEmpleadoVenta(idEmpleado);
                    v.setNumeroserie(numeroserie);
                    v.setTotalventa(totalpagar);
                    vdao.GuardarVenta(v);

                    //guardar detalle venta
                    int idv = Integer.parseInt(vdao.idVentas());
                    for (int i = 0; i < lista.size(); i++) {
                        v = new Venta();
                        v.setIdVenta(idv);
                        v.setIdProducto(lista.get(i).getIdProducto());
                        v.setDescripcionProd(lista.get(i).getDescripcionProd());
                        v.setCantProd(lista.get(i).getCantProd());
                        v.setPrecio(lista.get(i).getPrecio());
                        v.setIgv(lista.get(i).getIgv());
                        v.setPrecioFinal(lista.get(i).getPrecioFinal());
                        v.setSubtotal(lista.get(i).getSubtotal());
                        vdao.GuardarDetallerVenta(v);
                    }

                    //guardar comprobanteFactura
                    int idVenta = cpdao.obtenerIdVentaPorNumeroSerie(numeroserie);
                    comprobantePago = request.getParameter("tipoComprobante");
                    cp.setTipoComprobantePago(comprobantePago);

                    if (comprobantePago.equalsIgnoreCase("boleta")) {
                        numeroComprobantePago = cpdao.GenerarComprobanteBoleta();
                        if (numeroComprobantePago == null) {
                            numeroComprobantePago = "00000001";
                        } else {
                            try {
                                int incrementar = Integer.parseInt(numeroComprobantePago);
                                GenerarSerie gs = new GenerarSerie();
                                numeroComprobantePago = gs.NumeroSerie(incrementar);
                            } catch (NumberFormatException e) {
                                numeroComprobantePago = "00000001"; // Asignar un valor predeterminado en caso de error
                            }
                        }
                        cp.setNumComprobantePago(numeroComprobantePago);
                    } else {
                        numeroComprobantePago = cpdao.GenerarComprobanteFactura();
                        if (numeroComprobantePago == null) {
                            numeroComprobantePago = "00000001";
                        } else {
                            try {
                                int incrementar = Integer.parseInt(numeroComprobantePago);
                                GenerarSerie gs = new GenerarSerie();
                                numeroComprobantePago = gs.NumeroSerie(incrementar);
                            } catch (NumberFormatException e) {
                                numeroComprobantePago = "00000001"; // Asignar un valor predeterminado en caso de error
                            }
                        }
                        cp.setNumComprobantePago(numeroComprobantePago);
                    }

                    cp.setIdVenta(idVenta);

                    cpdao.GuardarComprobantePago(cp);
                    
                    request.setAttribute("paginaActual", "caja");
                    // Enviar a la comprobacion de venta
                    // El idVenta lo convertimos a String
                    String idVentaStr = String.valueOf(idVenta);
                    
                    String correo = cdao.obtenerCorreoCliente(c.getIdCliente());
                    
                    // Nos Llevamos los parametros comprobantePago y idVenta
                    request.setAttribute("comprobantePago", comprobantePago);
                    request.setAttribute("accion", "gmail");
                    request.setAttribute("correo", correo);
                    request.setAttribute("idVenta", idVentaStr);
                    request.getRequestDispatcher("reportes/ComprobantePago.jsp").forward(request, response);
                    break;

                case "ComprobantePago":
                    // Traemos los parametros
                    String idVentaComprobante = request.getParameter("idVenta");
                    String comprobantePago2 = request.getParameter("comprobantePago");
                    
                    //Enviamos los parametros
                    request.setAttribute("idVenta", idVentaComprobante);
                    request.setAttribute("comprobantePago", comprobantePago2);
                    request.setAttribute("accion", "mostrar");
                    
                    request.getRequestDispatcher("reportes/ComprobantePago.jsp").forward(request, response);
                    break;
                default:
                    v = new Venta();
                    lista = new ArrayList<>();
                    item = 0;
                    totalpagar = 0.0;

                    numeroserie = vdao.GenerarSerie();
                    if (numeroserie == null) {
                        numeroserie = "00000001";
                        request.setAttribute("nserie", numeroserie);
                    } else {
                        try {
                            int incrementar = Integer.parseInt(numeroserie);
                            GenerarSerie gs = new GenerarSerie();
                            numeroserie = gs.NumeroSerie(incrementar);
                            request.setAttribute("nserie", numeroserie);
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Error al generar el número de serie.");
                            request.setAttribute("nserie", numeroserie);
                            numeroserie = "00000001"; // Asignar un valor predeterminado en caso de error
                        }
                    }
                    
                    request.setAttribute("totalpagar", totalpagar);
                    request.setAttribute("paginaActual", "caja");
                    request.setAttribute("nserie", numeroserie);
                    request.getRequestDispatcher("caja.jsp").forward(request, response);
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}