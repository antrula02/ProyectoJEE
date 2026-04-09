package modelo;

import dao.DaoPost;

/**
 * Clase que representa un post del sistema.
 * Contiene información del usuario y del contenido publicado.
 */
public class Post {

    // Constantes para el rol
    public static final int ROL_USUARIO = 0;
    public static final int ROL_ADMIN = 1;

    private int id;
    private String nombre;
    private String email;
    private String password;
    private String titulo;
    private String contenido;
    private int admin; // 0: Usuario normal, 1: Admin

    /**
     * Constructor vacío.
     */
    public Post() {
        this.admin = ROL_USUARIO;
    }

    /**
     * Constructor sin ID (para insertar).
     *
     * @param nombre nombre del usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     * @param admin rol del usuario (0 usuario, 1 admin)
     */
    public Post(String nombre, String email, String password, int admin) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    /**
     * Constructor completo.
     *
     * @param id identificador del post
     * @param nombre nombre del usuario
     * @param email email del usuario
     * @param password contraseña
     * @param admin rol del usuario
     */
    public Post(int id, String nombre, String email, String password, int admin) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    /**
     * @return título del post
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del post.
     *
     * @param titulo título del post
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return contenido del post
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido del post.
     *
     * @param contenido contenido del post
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * @return id del post
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id del post.
     *
     * @param id identificador del post
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     *
     * @param email correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return rol del usuario (0 usuario, 1 admin)
     */
    public int getAdmin() {
        return admin;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param admin 0 usuario, 1 admin
     */
    public void setAdmin(int admin) {
        this.admin = admin;
    }

    /**
     * Comprueba si el usuario es administrador.
     *
     * @return true si es admin, false en caso contrario
     */
    public boolean esAdmin() {
        return this.admin == ROL_ADMIN;
    }

    /**
     * Inserta el post en la base de datos.
     */
    public void insertar() {
        DaoPost dao = new DaoPost();
        dao.insertar(this);
    }

    /**
     * Devuelve una representación en texto del objeto.
     *
     * @return cadena con los datos del post
     */
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                '}';
    }
}