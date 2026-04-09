package controller;

import java.io.IOException;

import dao.DaoReserva;
import modelo.Reserva;
import modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de gestionar el guardado de reservas realizadas por los usuarios.
 * 
 * <p>Este servlet recibe los datos de la reserva mediante una petición HTTP POST,
 * comprueba que el usuario esté autenticado y crea un objeto {@link Reserva}
 * con la información recibida. Posteriormente, almacena la reserva en la base
 * de datos utilizando {@link DaoReserva}.</p>
 * 
 * <p>Devuelve una respuesta en texto plano indicando si la operación ha sido
 * exitosa o si ha ocurrido un error.</p>
 * 
 * URL asociada: {@code /guardarReserva}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/guardarReserva")
public class GuardarReservaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP POST para guardar una reserva.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Configura la codificación de la petición y la respuesta.</li>
     *   <li>Verifica que exista un usuario autenticado en sesión.</li>
     *   <li>Recoge los datos del formulario de reserva.</li>
     *   <li>Crea un objeto {@link Reserva} con los datos recibidos.</li>
     *   <li>Inserta la reserva en la base de datos mediante {@link DaoReserva}.</li>
     *   <li>Devuelve un mensaje indicando el resultado de la operación.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} que contiene los datos enviados por el cliente
     * @param response objeto {@link HttpServletResponse} que contiene la respuesta al cliente
     * @throws ServletException si ocurre un error durante el procesamiento del servlet
     * @throws IOException si ocurre un error de entrada/salida al enviar la respuesta
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configuración de codificación
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        // Obtiene el usuario de la sesión
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        // Verifica que el usuario esté logueado
        if (usuario == null) {
            response.getWriter().write("Debes iniciar sesión para reservar");
            return;
        }

        try {
            // Crea el objeto Reserva y asigna los valores del formulario
            Reserva r = new Reserva();
            r.setNombreRes(request.getParameter("nombre"));
            r.setTelf(request.getParameter("telefono"));
            r.setFechaRes(request.getParameter("fecha"));
            r.setEmailRes(usuario.getEmail()); // Email del usuario logueado
            r.setNumPlayer(Integer.parseInt(request.getParameter("numPlayer")));
            r.setMode(Integer.parseInt(request.getParameter("modo")));
            r.setNotas(request.getParameter("notas"));

            // Inserta la reserva en la base de datos
            DaoReserva dao = new DaoReserva();
            boolean ok = dao.insertarReserva(r);

            // Devuelve el resultado al cliente
            if (ok) {
                response.getWriter().write("Reserva guardada correctamente");
            } else {
                response.getWriter().write("No se pudo guardar la reserva");
            }

            // Mensajes de depuración en consola
            System.out.println("Resultado insertar reserva: " + ok);
            System.out.println("nombre: " + request.getParameter("nombre"));
            System.out.println("telefono: " + request.getParameter("telefono"));
            System.out.println("fecha: " + request.getParameter("fecha"));
            System.out.println("email sesión: " + usuario.getEmail());
            System.out.println("numPlayer: " + request.getParameter("numPlayer"));
            System.out.println("modo: " + request.getParameter("modo"));
            System.out.println("notas: " + request.getParameter("notas"));

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error al guardar la reserva");
        }
    }
}