package modelo;

/**
 * Clase que representa un usuario del sistema.
 * Contiene los datos básicos necesarios para el registro, login y control de roles.
 */
public class Usuario {

    private int idUsuario;
    private String nombre;
    private String email;
    private String password;
    private int admin;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor para el registro de un usuario.
     *
     * @param nombre nombre del usuario
     * @param email correo electrónico del usuario
     * @param password contraseña del usuario
     */
    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.admin = 0;
    }

    /**
     * Constructor completo.
     *
     * @param idUsuario identificador del usuario
     * @param nombre nombre del usuario
     * @param email correo electrónico
     * @param password contraseña
     * @param admin indica si el usuario es administrador (1) o no (0)
     */
    public Usuario(int idUsuario, String nombre, String email, String password, int admin) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    /**
     * @return id del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return 1 si es admin, 0 si no lo es
     */
    public int getAdmin() {
        return admin;
    }

    /**
     * Establece el id del usuario.
     *
     * @param idUsuario identificador del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
     * Establece el email del usuario.
     *
     * @param email correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Establece si el usuario es administrador.
     *
     * @param admin 1 si es admin, 0 si no
     */
    public void setAdmin(int admin) {
        this.admin = admin;
    }

    /**
     * Indica si el usuario tiene rol de administrador.
     *
     * @return true si es admin, false en caso contrario
     */
    public boolean esAdmin() {
        return admin == 1;
    }
}