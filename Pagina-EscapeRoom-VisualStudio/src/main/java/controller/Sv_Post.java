package controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet de prueba encargado de recibir y mostrar datos enviados mediante una petición POST.
 * 
 * <p>Este servlet recoge un título y un contenido desde un formulario,
 * y genera una respuesta HTML mostrando dichos datos.</p>
 * 
 * <p>También permite comprobar su funcionamiento mediante una petición GET.</p>
 * 
 * URL asociada: {@code /post}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/post")
public class Sv_Post extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor del servlet.
     */
    public Sv_Post() {
        super();
    }

    /**
     * Atiende las peticiones HTTP POST.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Recoge los parámetros "titulo" y "contenido" enviados desde el cliente.</li>
     *   <li>Genera una respuesta HTML mostrando los datos recibidos.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} con los datos enviados por el cliente
     * @param response objeto {@link HttpServletResponse} para enviar la respuesta
     * @throws ServletException si ocurre un error durante el procesamiento
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene los parámetros del formulario
        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");

        // Configura la respuesta como HTML
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Muestra los datos recibidos
        out.println("<h1>Post recibido</h1>");
        out.println("<p><strong>Título:</strong> " + titulo + "</p>");
        out.println("<p><strong>Contenido:</strong> " + contenido + "</p>");
    }

    /**
     * Atiende las peticiones HTTP GET.
     * 
     * <p>Se utiliza principalmente para comprobar que el servlet está activo.</p>
     * 
     * @param request  objeto {@link HttpServletRequest}
     * @param response objeto {@link HttpServletResponse}
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().println("Servlet Sv_Post funcionando");
    }
}