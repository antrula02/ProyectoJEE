package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.ConexionBD;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de gestionar el registro de nuevos usuarios.
 * 
 * <p>Este servlet recibe los datos del formulario de registro (nombre, email y contraseña),
 * establece conexión con la base de datos y crea un nuevo usuario en la tabla {@code usuarios}.</p>
 * 
 * <p>El usuario se registra con rol estándar (ADMIN = 0).</p>
 * 
 * <p>Devuelve una respuesta en texto plano indicando si el registro se ha realizado
 * correctamente o si ha ocurrido algún error.</p>
 * 
 * URL asociada: {@code /registro}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP POST para registrar un nuevo usuario.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Recoge los datos enviados desde el formulario (nombre, email y contraseña).</li>
     *   <li>Establece conexión con la base de datos.</li>
     *   <li>Inserta el nuevo usuario en la tabla {@code usuarios}.</li>
     *   <li>Asigna por defecto el rol de usuario normal (ADMIN = 0).</li>
     *   <li>Devuelve un mensaje indicando el resultado de la operación.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} con los datos del cliente
     * @param response objeto {@link HttpServletResponse} para enviar la respuesta
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Obtiene los datos del formulario
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
            // Obtiene conexión a la base de datos
            con = ConexionBD.getConnection();

            if (con == null) {
                System.out.println("La conexión es NULL");
                out.print("Error: no hay conexión con la base de datos");
                return;
            }

            System.out.println("Conexión OK");
            System.out.println("Base de datos actual: " + con.getCatalog());
            System.out.println("AutoCommit: " + con.getAutoCommit());

            // Consulta SQL para insertar usuario
            String sql = "INSERT INTO usuarios (NOMBRE_USER, EMAIL_USER, PASSWORD, ADMIN) VALUES (?, ?, ?, 0)";
            System.out.println("SQL: " + sql);

            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, password);

            // Ejecuta la inserción
            int filas = ps.executeUpdate();

            System.out.println("Filas insertadas: " + filas);

            // Devuelve resultado al cliente
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
            // Cierra recursos
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}