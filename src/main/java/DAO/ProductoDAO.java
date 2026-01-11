package DAO;

import Config.*;
import Modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO extends Conexion{
    
    int r;
    
    public ProductoDAO(){}
    
    //método que recupera en una coleccion todo los productos de una tabla
    public List RecuperarRegistrosProducto() {
        String SQL = "select idProd, descripcion, precio, stock, categoria from productos;";
        List<Producto> lista = new ArrayList();
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();//ejecutamos la consulta
            while (rs.next()) { //recorremos todos los registros
                Producto prod = new Producto();//creamos el objeto
                prod.setIdProd(rs.getInt(1));
                prod.setDescripcion(rs.getString(2));
                prod.setPrecio(rs.getDouble(3));
                prod.setStock(rs.getInt(4));
                prod.setCategoria(rs.getString(5));
                lista.add(prod); //agregamos el objeto a la lista
            }
        } catch (Exception ex) {
            System.out.println("ERROR no se puede traer la lista de productos a la base de datos. " + ex);
        }
        return lista; // retornamos la lista con todos los registros
    }
    
    //metodo para agregar producto
    public void InsertarProducto(Producto prod) {
        String SQL = "insert into productos(descripcion, precio, stock, categoria) values (?, ?, ?, ?);";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, prod.getDescripcion());
            ps.setDouble(2, prod.getPrecio());
            ps.setInt(3, prod.getStock());
            ps.setString(4, prod.getCategoria());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("ERROR al insertar productos. " + ex);
        }
    }
    
    
    //metodo para eliminar producto
    public void EliminarProducto(int idProducto) {
        String SQL = "DELETE FROM productos  WHERE idProd = ?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, idProducto);
            ps.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println("ERROR al eliminar producto... " + ex);
        }
    }

    //metodo para listar el producto a editar por id
    public Producto MostrarProductoEditar(int idProducto) {
        Producto producto = new Producto();
        String SQL = "SELECT * FROM productos WHERE idProd = ?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setIdProd(rs.getInt(1));
                producto.setDescripcion(rs.getString(2));
                producto.setPrecio(rs.getDouble(3));
                producto.setStock(rs.getInt(4));
                producto.setCategoria(rs.getString(5));
            }
        } catch (Exception ex) {
            System.out.println("ERROR al obtener el productos por ID... " + ex);
        }
        return producto;
    }
    
    
    //METODO PARA ACTULIZAR PRODUCTO
    public void ActualizarProducto(Producto prod) {
        String SQL = "UPDATE productoS SET descripcion = ?, precio = ?, stock = ?, categoria = ? WHERE idProd = ?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, prod.getDescripcion());
            ps.setDouble(2, prod.getPrecio());
            ps.setInt(3, prod.getStock());
            ps.setString(4, prod.getCategoria());
            ps.setInt(5, prod.getIdProd());
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("ERROR al actualizar producto... " + ex);
        }
    }
    
    
    //METODOS PARA BUSCAR ID PARA CAJA.JSP
    public Producto buscar (int id){
        Producto p = new Producto();
        String SQL = "SELECT * FROM productos WHERE idProd ="+id;
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setIdProd(rs.getInt(1));
                p.setDescripcion(rs.getString(2));
                p.setPrecio(rs.getDouble(3));
                p.setStock(rs.getInt(4));
                p.setCategoria(rs.getString(5));
                
            }
        } catch (Exception e) {
        }
        return p;
    }
    
    //METODO PARA ACTUALIZAR STOCK EN LA BD
    public int actualizarStock(int id, int stock){
        String SQL = "UPDATE productos set stock=? where idProd=?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, stock);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
}
