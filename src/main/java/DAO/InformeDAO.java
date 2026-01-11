package DAO;

import Config.Conexion;
import Modelo.Informe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InformeDAO {

    public void agregarInforme(Informe informe) {
        Conexion conexion = new Conexion();
        Connection con = conexion.con;
        PreparedStatement ps = null;
        
        try {
            ps = con.prepareStatement("INSERT INTO informe (informedescripcion, idUsuario) VALUES (?, ?)");
            ps.setString(1, informe.getDescripcion());
            ps.setInt(2, informe.getIdUsuario()); 
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
