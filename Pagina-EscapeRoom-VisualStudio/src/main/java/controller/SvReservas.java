package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dao.DaoReserva;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de proporcionar las fechas de reservas en formato JSON.
 * 
 * <p>Este servlet obtiene desde la base de datos las fechas ya reservadas
 * mediante la clase {@link DaoReserva} y las devuelve en formato JSON.
 * Se utiliza principalmente para bloquear fechas en el calendario del frontend.</p>
 * 
 * URL asociada: {@code /reservas}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/reservas")
public class SvReservas extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP GET para obtener las fechas reservadas.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Configura la respuesta en formato JSON.</li>
     *   <li>Obtiene las fechas reservadas desde la base de datos.</li>
     *   <li>Construye manualmente una lista JSON con las fechas.</li>
     *   <li>Envía la respuesta al cliente.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} que contiene la petición del cliente
     * @param response objeto {@link HttpServletResponse} que contiene la respuesta en formato JSON
     * @throws ServletException si ocurre un error durante el procesamiento del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configura la respuesta como JSON
        response.setContentType("application/json;charset=UTF-8");

        // Obtiene las fechas reservadas desde la base de datos
        DaoReserva dao = new DaoReserva();
        List<String> fechas = dao.obtenerFechasReservadas();

        PrintWriter out = response.getWriter();
        out.print("[");

        // Construye manualmente el JSON
        for (int i = 0; i < fechas.size(); i++) {
            out.print("\"" + fechas.get(i) + "\"");
            if (i < fechas.size() - 1) {
                out.print(",");
            }
        }

        out.print("]");
    }
}