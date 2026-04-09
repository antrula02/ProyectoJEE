package controller;

import dao.DaoReserva;
import modelo.Reserva;
import modelo.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet encargado de mostrar las reservas del usuario autenticado.
 * 
 * <p>Este servlet obtiene el usuario de la sesión y, si está autenticado,
 * recupera todas sus reservas asociadas a su email mediante la clase
 * {@link DaoReserva}. Posteriormente, envía la lista de reservas a la vista
 * {@code misReservas.jsp} para su visualización.</p>
 * 
 * <p>Si el usuario no ha iniciado sesión, será redirigido a la página de login.</p>
 * 
 * URL asociada: {@code /mis-reservas}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/mis-reservas")
public class MisReservasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP GET para mostrar las reservas del usuario.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Obtiene el usuario desde la sesión.</li>
     *   <li>Comprueba si el usuario está autenticado.</li>
     *   <li>Recupera las reservas asociadas al email del usuario.</li>
     *   <li>Guarda la lista de reservas en la petición.</li>
     *   <li>Redirige a {@code misReservas.jsp} para mostrar los datos.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} que contiene la petición del cliente
     * @param response objeto {@link HttpServletResponse} que contiene la respuesta del servlet
     * @throws ServletException si ocurre un error durante el procesamiento
     * @throws IOException si ocurre un error de redirección o entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene el usuario de la sesión
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        // Verifica si el usuario está autenticado
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Obtiene las reservas del usuario mediante su email
        DaoReserva daoReserva = new DaoReserva();
        List<Reserva> listaReservas = daoReserva.obtenerReservasPorEmail(usuario.getEmail());

        // Envía la lista a la vista
        request.setAttribute("listaReservas", listaReservas);
        request.getRequestDispatcher("misReservas.jsp").forward(request, response);
    }
}