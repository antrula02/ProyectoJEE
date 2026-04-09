package controller;

import java.io.IOException;

import dao.DaoReserva;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Usuario;

/**
 * Servlet encargado de gestionar la eliminación de reservas desde el panel de administración.
 * 
 * <p>Este servlet solo permite la eliminación de reservas a usuarios con rol de administrador.
 * Si el usuario no está autenticado o no tiene permisos, será redirigido a la página principal.</p>
 * 
 * <p>Recibe el identificador de la reserva a eliminar mediante una petición HTTP POST
 * y utiliza la clase {@link DaoReserva} para eliminarla de la base de datos.</p>
 * 
 * URL asociada: {@code /eliminarReserva}
 * 
 * @author Antrula02
 * @version 1.0
 */
@WebServlet("/eliminarReserva")
public class EliminarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP POST para eliminar una reserva.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Obtiene el usuario de la sesión.</li>
     *   <li>Comprueba que el usuario esté autenticado y sea administrador.</li>
     *   <li>Recoge el identificador de la reserva a eliminar.</li>
     *   <li>Elimina la reserva de la base de datos mediante {@link DaoReserva}.</li>
     *   <li>Redirige nuevamente al panel de administración.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} con los datos enviados por el cliente
     * @param response objeto {@link HttpServletResponse} para enviar la respuesta al cliente
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     * @throws IOException si ocurre un error de redirección o entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene el usuario de la sesión
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        // Verifica que el usuario esté autenticado y sea administrador
        if (usuario == null || usuario.getAdmin() != 1) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            // Obtiene el ID de la reserva a eliminar
            int idReserva = Integer.parseInt(request.getParameter("idReserva"));

            // Elimina la reserva de la base de datos
            DaoReserva daoReserva = new DaoReserva();
            daoReserva.eliminarReserva(idReserva);

        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
        }

        // Redirige al panel de administración tras la operación
        response.sendRedirect("admin");
    }
}