package servlet;

import conexion.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("=== DATOS RECIBIDOS EN REGISTRO ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.getConnection();

            if (con == null) {
                System.out.println("La conexión es NULL");
                out.print("Error: no hay conexión con la base de datos");
                return;
            }

            System.out.println("Conexión OK");
            System.out.println("Base de datos actual: " + con.getCatalog());
            System.out.println("AutoCommit: " + con.getAutoCommit());

            String sql = "INSERT INTO usuarios (NOMBRE_USER, EMAIL_USER, PASSWORD, ADMIN) VALUES (?, ?, ?, 0)";
            System.out.println("SQL: " + sql);

            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, password);

            int filas = ps.executeUpdate();

            System.out.println("Filas insertadas: " + filas);

            if (filas > 0) {
                out.print("Usuario registrado correctamente");
            } else {
                out.print("No se pudo registrar el usuario");
            }

        } catch (Exception e) {
            System.out.println("ERROR EN REGISTRO:");
            e.printStackTrace();
            out.print("Error al registrar usuario");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}