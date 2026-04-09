package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dao.DaoComentario;
import modelo.Comentario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de proporcionar la lista de comentarios en formato JSON.
 * 
 * <p>Este servlet recupera los comentarios almacenados en la base de datos
 * mediante la clase {@link DaoComentario} y los devuelve al cliente en formato JSON.
 * Es utilizado principalmente para cargar dinámicamente los comentarios en el frontend.</p>
 * 
 * URL asociada: {@code /comentarios}
 * 
 * @author TuNombre
 * @version 1.0
 */
@WebServlet("/comentarios")
public class SvComentarios extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Atiende las peticiones HTTP GET para obtener la lista de comentarios.
     * 
     * <p>El método realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Configura la respuesta en formato JSON.</li>
     *   <li>Obtiene la lista de comentarios desde la base de datos.</li>
     *   <li>Construye manualmente una respuesta JSON con los datos.</li>
     *   <li>Escapa caracteres especiales para evitar errores de formato.</li>
     * </ul>
     * 
     * @param request  objeto {@link HttpServletRequest} que contiene la petición del cliente
     * @param response objeto {@link HttpServletResponse} que contiene la respuesta en formato JSON
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configura la respuesta como JSON
        response.setContentType("application/json;charset=UTF-8");

        // Obtiene los comentarios desde la base de datos
        DaoComentario dao = new DaoComentario();
        List<Comentario> listaComentarios = dao.listarComentarios();

        PrintWriter out = response.getWriter();
        out.print("[");

        // Construye manualmente el JSON
        for (int i = 0; i < listaComentarios.size(); i++) {
            Comentario c = listaComentarios.get(i);

            out.print("{");
            out.print("\"idComentario\":" + c.getIdComentario() + ",");
            out.print("\"idUsuario\":" + c.getIdUsuario() + ",");
            out.print("\"nombreUser\":\"" + escaparJson(c.getNombreUser()) + "\",");
            out.print("\"fechaComentario\":\"" + escaparJson(c.getFechaComentario()) + "\",");
            out.print("\"modo\":" + c.getModo() + ",");
            out.print("\"opinion\":\"" + escaparJson(c.getOpinion()) + "\"");
            out.print("}");

            if (i < listaComentarios.size() - 1) {
                out.print(",");
            }
        }

        out.print("]");
    }

    /**
     * Escapa caracteres especiales para evitar errores en el formato JSON.
     * 
     * <p>Reemplaza barras invertidas y comillas dobles para que el texto
     * sea seguro al incluirlo en una cadena JSON.</p>
     * 
     * @param texto cadena a procesar
     * @return cadena escapada lista para JSON
     */
    private String escaparJson(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}