package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Comentario;

/**
 * Clase DAO encargada de gestionar las operaciones relacionadas con los comentarios en la base de datos.
 */
public class DaoComentario {

    /**
     * Obtiene la lista de comentarios ordenados por fecha descendente.
     *
     * @return lista de comentarios
     */
    public List<Comentario> listarComentarios() {
        List<Comentario> lista = new ArrayList<>();

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "SELECT ID_COMENTARIO, ID_USUARIO, NOMBRE_USER, FECHA_COMENTARIO, MODO, OPINION FROM comentarios ORDER BY FECHA_COMENTARIO DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comentario c = new Comentario();

                c.setIdComentario(rs.getInt("ID_COMENTARIO"));
                c.setIdUsuario(rs.getInt("ID_USUARIO"));
                c.setNombreUser(rs.getString("NOMBRE_USER"));
                c.setFechaComentario(rs.getString("FECHA_COMENTARIO"));
                c.setModo(rs.getInt("MODO"));
                c.setOpinion(rs.getString("OPINION"));

                lista.add(c);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Inserta un nuevo comentario en la base de datos.
     *
     * @param c objeto Comentario con los datos a insertar
     * @return true si el comentario se inserta correctamente, false en caso contrario
     */
    public boolean insertarComentario(Comentario c) {
        boolean resultado = false;

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "INSERT INTO comentarios (ID_USUARIO, NOMBRE_USER, MODO, OPINION) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, c.getIdUsuario());
            ps.setString(2, c.getNombreUser());
            ps.setInt(3, c.getModo());
            ps.setString(4, c.getOpinion());

            ps.executeUpdate();

            resultado = true;

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    /**
     * Obtiene todos los comentarios ordenados por ID descendente.
     *
     * @return lista de comentarios
     */
    public List<Comentario> obtenerComentarios() {
        List<Comentario> lista = new ArrayList<>();

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "SELECT * FROM comentarios ORDER BY ID_COMENTARIO DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comentario c = new Comentario();
                c.setIdComentario(rs.getInt("ID_COMENTARIO"));
                c.setIdUsuario(rs.getInt("ID_USUARIO"));
                c.setNombreUser(rs.getString("NOMBRE_USER"));
                c.setFechaComentario(rs.getString("FECHA_COMENTARIO"));
                c.setModo(rs.getInt("MODO"));
                c.setOpinion(rs.getString("OPINION"));

                lista.add(c);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Elimina un comentario de la base de datos.
     *
     * @param id identificador del comentario
     * @return true si se elimina correctamente, false en caso contrario
     */
    public boolean eliminarComentario(int id) {
        boolean eliminado = false;

        try {
            Connection con = ConexionBD.getConnection();

            String sql = "DELETE FROM comentarios WHERE ID_COMENTARIO = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                eliminado = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return eliminado;
    }
}