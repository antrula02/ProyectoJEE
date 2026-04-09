package controller;

import dao.DaoComentario;
import dao.DaoReserva;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;

import java.io.IOException;

/**
 * Servlet encargado de gestionar el acceso al panel de administración.
 * 
 * <p>Este servlet comprueba si existe una sesión activa y si el usuario
 * autenticado tiene rol de administrador. En caso contrario, redirige
 * a la página principal.</p>
 * 
 * <p>Si el usuario es administrador, recupera la lista de comentarios
 * y la lista de reservas desde la capa DAO, las guarda como atributos
 * de la petición y finalmente redirige al archivo {@code admin.jsp}
 * para mostrar la información.</p>
 * 
 * URL asociada: {@code /admin}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP GET al panel de administración.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Obtiene la sesión actual del usuario.</li>
     *   <li>Comprueba si existe un usuario autenticado en la sesión.</li>
     *   <li>Verifica si el usuario tiene permisos de administrador.</li>
     *   <li>Obtiene los comentarios y reservas desde la base de datos.</li>
     *   <li>Envía la información al archivo {@code admin.jsp}.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} que contiene la petición del cliente
     * @param response objeto {@link HttpServletResponse} que contiene la respuesta del servlet
     * @throws ServletException si ocurre un error al procesar la petición
     * @throws IOException si ocurre un error de entrada/salida durante la redirección o el forward
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Verifica si existe una sesión activa y un usuario autenticado
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("index.html");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Comprueba si el usuario tiene permisos de administrador
        if (!usuario.esAdmin()) {
            response.sendRedirect("index.html");
            return;
        }

        // Crea los objetos DAO para recuperar la información
        DaoComentario daoComentario = new DaoComentario();
        DaoReserva daoReserva = new DaoReserva();

        // Guarda las listas de comentarios y reservas en la petición
        request.setAttribute("listaComentarios", daoComentario.obtenerComentarios());
        request.setAttribute("listaReservas", daoReserva.obtenerReservas());

        // Redirige al panel de administración
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}