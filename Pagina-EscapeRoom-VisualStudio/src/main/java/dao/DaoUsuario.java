package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Usuario;

/**
 * Clase DAO encargada de gestionar las operaciones relacionadas con los usuarios en la base de datos.
 */
public class DaoUsuario {

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param user objeto Usuario con los datos a insertar
     * @return true si el usuario se inserta correctamente, false en caso contrario
     */
    public boolean insertarUsuario(Usuario user) {
        boolean insertado = false;

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "INSERT INTO usuarios (NOMBRE_USER, EMAIL_USER, PASSWORD, ADMIN) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getNombre());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getAdmin());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                insertado = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return insertado;
    }

    /**
     * Valida un usuario comprobando su email y contraseña.
     *
     * @param email correo del usuario
     * @param password contraseña del usuario
     * @return objeto Usuario si las credenciales son correctas, null en caso contrario
     */
    public Usuario validarUsuario(String email, String password) {
        Usuario usuario = null;

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "SELECT * FROM usuarios WHERE EMAIL_USER = ? AND PASSWORD = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                usuario.setNombre(rs.getString("NOMBRE_USER"));
                usuario.setEmail(rs.getString("EMAIL_USER"));
                usuario.setPassword(rs.getString("PASSWORD"));
                usuario.setAdmin(rs.getInt("ADMIN"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Obtiene el ID de un usuario a partir de su email.
     *
     * @param email correo del usuario
     * @return id del usuario o -1 si no se encuentra
     */
    public int obtenerIdPorEmail(String email) {
        int id = -1;

        try {
            Connection con = ConexionBD.getConnection();
            String sql = "SELECT ID_USUARIO FROM usuarios WHERE EMAIL_USER = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("ID_USUARIO");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Obtiene el ID de un usuario a partir de su email.
     *
     * @param email correo del usuario
     * @return id del usuario o 0 si no se encuentra
     */
    public int obtenerIdUsuario(String email) {
        int id = 0;

        try {
            Connection con = ConexionBD.getConnection();
            String sql = "SELECT ID_USUARIO FROM usuarios WHERE EMAIL_USER = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("ID_USUARIO");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Obtiene el nombre de un usuario a partir de su email.
     *
     * @param email correo del usuario
     * @return nombre del usuario o cadena vacía si no existe
     */
    public String obtenerNombreUsuario(String email) {
        String nombre = "";

        try {
            Connection con = ConexionBD.getConnection();
            String sql = "SELECT NOMBRE_USER FROM usuarios WHERE EMAIL_USER = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("NOMBRE_USER");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nombre;
    }

    /**
     * Comprueba si un email ya existe en la base de datos.
     *
     * @param email correo a comprobar
     * @return true si el email ya está registrado, false en caso contrario
     */
    public boolean existeEmail(String email) {
        boolean existe = false;

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "SELECT * FROM usuarios WHERE EMAIL_USER = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                existe = true;
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }
}