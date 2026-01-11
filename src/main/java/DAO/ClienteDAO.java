package DAO;

import Config.*;
import Modelo.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends Conexion{
    
    public ClienteDAO() {}

    // Método para agregar cliente
    public void agregarCliente(Cliente cliente) {
        String SQL = "INSERT INTO clientes (dni, nombre, apellido, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setInt(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("ERROR al agregar cliente: " + e);
        }
    }

    // Método para actualizar cliente
    public void actualizarCliente(Cliente client) {
        String SQL = "UPDATE clientes SET dni = ?, nombre = ?, apellido = ?, telefono = ?, correo = ? WHERE idCliente = ?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, client.getDni());
            ps.setString(2, client.getNombre());
            ps.setString(3, client.getApellido());
            ps.setInt(4, client.getTelefono());
            ps.setString(5, client.getCorreo());
            ps.setInt(6, client.getIdCliente());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR al actualizar cliente: " + e);
        }
    }

    // Método para eliminar cliente
    public void eliminarCliente(int id) {
        String SQL = "DELETE FROM clientes WHERE idCliente = ?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR al eliminar cliente: " + e);
        }
    }

    // Método para obtener un cliente por ID
    public Cliente obtenerCliente(int id) {
        Cliente cliente = new Cliente();
        String SQL = "SELECT * FROM clientes WHERE idCliente = ?";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente.setDni(rs.getInt(2));
                cliente.setNombre(rs.getString(3));
                cliente.setApellido(rs.getString(4));
                cliente.setTelefono(rs.getInt(5));
                cliente.setCorreo(rs.getString(6));
            }
        } catch (Exception e) {
            System.out.println("ERROR al obtener cliente: " + e);
        }
        return cliente;
    }

    // Método para obtener todos los clientes
    public List obtenerTodosLosClientes() {
        String SQL = "SELECT idCliente, dni, nombre, apellido, telefono, correo FROM clientes";
        List<Cliente> lista = new ArrayList();
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt(1));
                cliente.setDni(rs.getInt(2));
                cliente.setNombre(rs.getString(3));
                cliente.setApellido(rs.getString(4));
                cliente.setTelefono(rs.getInt(5));
                cliente.setCorreo(rs.getString(6));
                lista.add(cliente);
            }
        } catch (Exception e) {
            System.out.println("ERROR al obtener todos los clientes: " + e);
        }
        return lista;
    }

    public Cliente buscarDni(int dni){
        Cliente c = new Cliente();
        String SQL = "SELECT * FROM clientes where dni="+dni;
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            if(rs.next()){
                c.setIdCliente(rs.getInt(1));
                c.setDni(rs.getInt(2));
                c.setNombre(rs.getString(3));
                c.setApellido(rs.getString(4));
                c.setTelefono(rs.getInt(5));
                c.setCorreo(rs.getString(6));
            }
        } catch (Exception e) {
            System.out.println("ERROR al obetener el dni del cliente a buscar... "+e);
        }
        return c;
    }
    
    //Encontrar un correo del cliente
    public String obtenerCorreoCliente(int id){
        String correo = null;
        String SQL = "select correo from clientes where idCliente="+id;
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            if (rs.next()) {
                correo = rs.getString("correo");
            }
        } catch (Exception e) {
            System.out.println("ERROR al obetener el correo del cliente..."+e);
        }
        return correo;
    }
}
