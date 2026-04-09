package controller;

import dao.DaoUsuario;
import dao.DaoComentario;
import modelo.Comentario;
import modelo.Usuario;

public class TestConexion {

    public static void main(String[] args) {

        DaoUsuario nuevoUsuario = new DaoUsuario();

        Usuario usuario = nuevoUsuario.validarUsuario("antrula02@gmail.com", "1111");

        if (usuario != null) {
            System.out.println("Login correcto");

            if (usuario.esAdmin()) {
                System.out.println("Es admin");
            } else {
                System.out.println("No es admin");
            }

        } else {
            System.out.println("Login incorrecto");
        }

        // PRUEBA COMENTARIO
        int idUsuario = 1; // Debe existir en la BD
        String nombre = "Antonio";
        int modo = 0;
        String opinion = "Comentario de prueba desde Eclipse";

        // ID y fecha no se pasan porque los genera la BD
        Comentario comentario = new Comentario();
        comentario.setIdUsuario(idUsuario);
        comentario.setNombreUser(nombre);
        comentario.setModo(modo);
        comentario.setOpinion(opinion);

        DaoComentario dao = new DaoComentario();
        boolean ok = dao.insertarComentario(comentario);

        if (ok) {
            System.out.println("Comentario guardado correctamente en la base de datos");
        } else {
            System.out.println("No se pudo guardar el comentario");
        }
    }
}