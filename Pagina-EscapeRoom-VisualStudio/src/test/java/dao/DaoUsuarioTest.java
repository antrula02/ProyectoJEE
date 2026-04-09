package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Usuario;

public class DaoUsuarioTest {

    private DaoUsuario dao;

    @BeforeEach
    public void setUp() {
        dao = new DaoUsuario();
    }

    @Test
    public void testInsertarUsuario() {
        Usuario user = new Usuario();
        user.setNombre("TestUser");
        user.setEmail("testuser@gmail.com");
        user.setPassword("1234");
        user.setAdmin(0);

        boolean resultado = dao.insertarUsuario(user);

        assertTrue(resultado, "El usuario debería insertarse correctamente");
    }


    @Test
    public void testValidarUsuarioCorrecto() {
        String email = "testuser@gmail.com";
        String password = "1234";

        Usuario usuario = dao.validarUsuario(email, password);

        assertNotNull(usuario, "El usuario debería existir");
        assertEquals(email, usuario.getEmail());
    }

    @Test
    public void testValidarUsuarioIncorrecto() {
        Usuario usuario = dao.validarUsuario("noexiste@gmail.com", "wrong");

        assertNull(usuario, "No debería devolver usuario");
    }

    @Test
    public void testObtenerIdUsuario() {
        String email = "testuser@gmail.com";

        int id = dao.obtenerIdUsuario(email);

        assertTrue(id > 0, "El ID debería ser mayor que 0");
    }

    @Test
    public void testObtenerNombreUsuario() {
        String email = "testuser@gmail.com";

        String nombre = dao.obtenerNombreUsuario(email);

        assertNotNull(nombre);
        assertEquals("TestUser", nombre);
    }
    @Test
    public void testBorrarUsuario() {
    	 String email = "testuser@gmail.com";
    	 
    }
}