package DAO;

import Config.*;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends Conexion{
    
    public UsuarioDAO(){}

    //metodo para verificar si existe el usuario en LOGIN
    public Usuario ValidarUsuario(Usuario user){
        Usuario usuarioActivo = null;
        String SQL = "select*from usuario where usuario=? and clave=?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, user.getUsuario());
            ps.setString(2, user.getClave());
            rs = ps.executeQuery();
            if (rs.next()) {
                usuarioActivo = new Usuario();
                usuarioActivo.setId(rs.getInt(1));
                usuarioActivo.setNombre(rs.getString(2));
                usuarioActivo.setApellido(rs.getString(3));
                usuarioActivo.setDni(rs.getInt(4));
                usuarioActivo.setUsuario(rs.getString(5));
                usuarioActivo.setClave(rs.getString(6));
                usuarioActivo.setRol(rs.getString(7));
            }
        } catch (Exception ex) {
            System.out.println("ERROR al recuperar usuario: " + ex);
        }

        return usuarioActivo;
    }

    //método que recupera en una coleccion todo los registro de una tabla
    public List RecuperarRegistrosUsuario() {
        String SQL = "select idUsuario, nombre, apellido, dni, usuario, clave, rol from usuario;";
        List<Usuario> lista = new ArrayList();
        try {
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();//ejecutamos la consulta
            while (rs.next()) { //recorremos todos los registros
                Usuario user = new Usuario();//creamos el objeto
                user.setId(rs.getInt(1));
                user.setNombre(rs.getString(2));
                user.setApellido(rs.getString(3));
                user.setDni(rs.getInt(4));
                user.setUsuario(rs.getString(5));
                user.setClave(rs.getString(6));
                user.setRol(rs.getString(7));
                lista.add(user); //agregamos el objeto a la lista
            }
        } catch (Exception ex) {
            System.out.println("ERROR no se puede traer la lista de usuarios a la base de datos. " + ex);
        }
        return lista; // retornamos la lista con todos los registros
    }

    //metodo para agregar usuarios
    public void InsertarUsuarios(Usuario user){
        String SQL = "insert into usuario(nombre, apellido, dni, usuario, clave, rol) values (?, ?, ?, ?, ?, ?);";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setInt(3, user.getDni());
            ps.setString(4, user.getUsuario());
            ps.setString(5, user.getClave());
            ps.setString(6, user.getRol());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("ERROR al insertar Usuarios. " + ex);
        }
    }

    //metodo para eliminar usuario
    public void EliminarRegistroUsuario(int idUsuario){
        String SQL = "DELETE FROM usuario WHERE idUsuario = ?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println("ERROR al eliminar usuario... " + ex);
        }
    }

    //metodo para listar el usuario a editar por id
    public Usuario MostrarUsuarioEditar(int idUsuario){
        Usuario usuario = new Usuario();
        String SQL = "SELECT * FROM usuario WHERE idUsuario = ?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setDni(rs.getInt(4));
                usuario.setUsuario(rs.getString(5));
                usuario.setClave(rs.getString(6));
                usuario.setRol(rs.getString(7));
            }
        } catch (Exception ex) {
            System.out.println("ERROR al obtener el usuario por ID... "+ex);
        }
        return usuario;
    }

    //metodo para actualizar usuario
    public void ActualizarUsuario(Usuario user){
        String SQL = "UPDATE usuario SET nombre = ?, apellido = ?, dni = ?, usuario = ?, clave = ?, rol = ? WHERE idUsuario = ?;";
        try {
            ps = con.prepareStatement(SQL);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setInt(3, user.getDni());
            ps.setString(4, user.getUsuario());
            ps.setString(5, user.getClave());
            ps.setString(6, user.getRol());
            ps.setInt(7, user.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("ERROR al actualizar usuario... " + ex);
        }
    }
}
