package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modelo.Post;

/**
 * Clase DAO encargada de gestionar las operaciones relacionadas con los posts en la base de datos.
 */
public class DaoPost {

    /**
     * Inserta un nuevo post en la base de datos.
     *
     * @param post objeto Post que contiene el título y contenido a insertar
     */
    public void insertar(Post post) {
        try {
            Connection con = DBConection.getConnection();

            String sql = "INSERT INTO post (titulo, contenido) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, post.getTitulo());
            ps.setString(2, post.getContenido());

            ps.executeUpdate();

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}