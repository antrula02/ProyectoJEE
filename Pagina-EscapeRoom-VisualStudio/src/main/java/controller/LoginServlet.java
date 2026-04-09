package controller;

import java.io.IOException;
import java.io.PrintWriter;

import dao.DaoUsuario;
import modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de gestionar el inicio de sesión de los usuarios.
 * Valida las credenciales introducidas y crea la sesión si son correctas.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Procesa las peticiones POST para el login de usuario.
     *
     * @param request contiene los datos enviados (email y contraseña)
     * @param response devuelve el resultado de la validación
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        DaoUsuario dao = new DaoUsuario();
        Usuario usuario = dao.validarUsuario(email, password);

        PrintWriter out = response.getWriter();

        if (usuario != null) {
            request.getSession().setAttribute("usuario", usuario);
            out.print("OK|" + usuario.getIdUsuario() + "|" + usuario.getNombre() + "|" + usuario.getEmail() + "|" + usuario.getAdmin());
        } else {
            out.print("Correo o contraseña incorrectos");
        }
    }
}