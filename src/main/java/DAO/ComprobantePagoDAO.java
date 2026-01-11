package DAO;

import Config.*;
import Modelo.ComprobantePago;

public class ComprobantePagoDAO extends Conexion{
    
    public ComprobantePagoDAO() {
    }

    public int obtenerIdVentaPorNumeroSerie(String numeroSerie) {
        int idVenta = -1;
        String SQL = "SELECT idVenta FROM ventas WHERE numeroSerie = ?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, numeroSerie);
            rs = ps.executeQuery();
            if(rs.next()){
                idVenta = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return idVenta;
    }
    
    public String GenerarComprobanteBoleta() {
        String numComprobanteBoleta = "";
        String SQL = "SELECT max(numComprobantePago) from comprobantePago where tipoComprobantePago='boleta';";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                numComprobanteBoleta = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("ERROR al generar numero de serie... " + e);
        }
        return numComprobanteBoleta;
    }
    
    public String GenerarComprobanteFactura() {
        String numComprobanteFactura = "";
        String SQL = "SELECT max(numComprobantePago) from comprobantePago where tipoComprobantePago='factura';";
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                numComprobanteFactura = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("ERROR al generar numero de serie... " + e);
        }
        return numComprobanteFactura;
    }
    
    public void GuardarComprobantePago(ComprobantePago cp) {
        String SQL = "insert into comprobantePago(tipoComprobantePago, numComprobantePago, idVenta) values(?,?,?);";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, cp.getTipoComprobantePago());
            ps.setString(2, cp.getNumComprobantePago());
            ps.setInt(3, cp.getIdVenta());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
