package DAO;

import Config.*;
import Modelo.DetalleVenta;
import Modelo.Venta;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO extends Conexion{
    
    public VentaDAO() {
    }
    int r;

    public String GenerarSerie() {
        String numeroserie = "";
        String SQL = "SELECT max(numeroSerie) from ventas";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                numeroserie = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("ERROR al generar numero de serie... " + e);
        }
        return numeroserie;
    }

    public String idVentas() {
        String idventas = "";
        String SQL = "SELECT max(idVenta) from ventas;";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                idventas = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("ERROR al encontrer el id maximo de venta..." + e);
        }
        return idventas;
    }

    public int GuardarVenta(Venta ve) {
        String SQL = "insert into ventas(idCliente, idUsuario, numeroSerie, fecha, precioTotal) values(?,?,?,NOW(),?);";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, ve.getIdClienteVenta());
            ps.setInt(2, ve.getIdEmpleadoVenta());
            ps.setString(3, ve.getNumeroserie());
            ps.setDouble(4, ve.getTotalventa());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public int GuardarDetallerVenta(Venta venta) {
        String SQL = "insert into detalleVenta(idVenta, idProd, descripcionProd, cantidadProd, precio, igv, precioFinal, subTotal) values (?,?,?,?,?,?,?,?);";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, venta.getIdVenta());
            ps.setInt(2, venta.getIdProducto());
            ps.setString(3, venta.getDescripcionProd());
            ps.setInt(4, venta.getCantProd());
            ps.setDouble(5, venta.getPrecio());
            ps.setDouble(6, venta.getIgv());
            ps.setDouble(7, venta.getPrecioFinal());
            ps.setDouble(8, venta.getSubtotal());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
    
    
   
    // METODOS PARA LA VENTANA VENTA
    public List RecuperarRegistrosVentas() {
        String SQL = "SELECT v.idVenta, v.numeroSerie, v.fecha, c.nombre, c.apellido, v.precioTotal, dv.descripcionProd, p.categoria, dv.cantidadProd, dv.precioFinal, dv.subTotal "+
                     "FROM ventas v INNER JOIN detalleVenta dv ON v.idVenta = dv.idVenta INNER JOIN clientes c ON v.idCliente = c.idCliente INNER JOIN productos p ON dv.idProd = p.idProd "+
                     "ORDER BY dv.idDetalleVenta;";
        List<DetalleVenta> listaVentas = new ArrayList();
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();//ejecutamos la consulta
            while (rs.next()) { //recorremos todos los registros
                DetalleVenta detventa = new DetalleVenta();//creamos el objeto
                detventa.setIdVenta(rs.getInt(1));
                detventa.setNumSerie(rs.getString(2));
                detventa.setFecha(rs.getString(3));
                detventa.setNomCliente(rs.getString(4));
                detventa.setApellCliente(rs.getString(5));
                detventa.setMontoTotal(rs.getDouble(6));
                detventa.setDescripcion(rs.getString(7));
                detventa.setCategoria(rs.getString(8));
                detventa.setCantidadProd(rs.getInt(9));
                detventa.setPrecioProd(rs.getDouble(10));
                detventa.setSubtotalProd(rs.getDouble(11));
                listaVentas.add(detventa); //agregamos el objeto a la lista
            }
        } catch (Exception ex) {
            System.out.println("ERROR no se puede traer la lista de registro de ventas a la base de datos. " + ex);
        }
        return listaVentas; // retornamos la lista con todos los registros
    }
    
    //metodo para eliminar registro de venta
    public void EliminarRegistroVenta(int id) {
        String SQL = "DELETE FROM ventas WHERE idVenta  =?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("ERROR al eliminar registro producto... " + ex);
        }
    }
    
}
