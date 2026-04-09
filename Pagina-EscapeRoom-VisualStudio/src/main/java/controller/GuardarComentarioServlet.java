package controller;

import java.io.IOException;

import dao.DaoComentario;
import modelo.Comentario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de gestionar el guardado de comentarios enviados por los usuarios.
 * 
 * <p>Recibe los datos del comentario a través de una petición HTTP POST,
 * crea un objeto {@link Comentario} y lo almacena en la base de datos
 * mediante la clase {@link DaoComentario}.</p>
 * 
 * <p>Devuelve una respuesta en texto plano indicando si la operación
 * se ha realizado correctamente o si ha ocurrido un error.</p>
 * 
 * URL asociada: {@code /guardarComentario}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/guardarComentario")
public class GuardarComentarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP POST para guardar un comentario.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Recoge los parámetros enviados desde el cliente (idUsuario, nombre, modo y opinión).</li>
     *   <li>Crea un objeto {@link Comentario} con los datos recibidos.</li>
     *   <li>Inserta el comentario en la base de datos mediante {@link DaoComentario}.</li>
     *   <li>Devuelve un mensaje indicando si la operación fue exitosa o no.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} que contiene los datos enviados por el cliente
     * @param response objeto {@link HttpServletResponse} que contiene la respuesta al cliente
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     * @throws IOException si ocurre un error de entrada/salida al escribir la respuesta
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene los parámetros enviados desde el formulario
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String nombreUser = request.getParameter("nombreUser");
        int modo = Integer.parseInt(request.getParameter("modo"));
        String opinion = request.getParameter("opinion");

        // Crea el objeto Comentario y asigna los valores
        Comentario comentario = new Comentario();
        comentario.setIdUsuario(idUsuario);
        comentario.setNombreUser(nombreUser);
        comentario.setModo(modo);
        comentario.setOpinion(opinion);

        // Inserta el comentario en la base de datos
        DaoComentario dao = new DaoComentario();
        boolean ok = dao.insertarComentario(comentario);

        // Configura la respuesta como texto plano
        response.setContentType("text/plain;charset=UTF-8");

        // Envía mensaje de resultado al cliente
        if (ok) {
            response.getWriter().write("Comentario guardado correctamente");
        } else {
            response.getWriter().write("No se pudo guardar el comentario");
        }
    }
}