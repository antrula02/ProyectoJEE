package modelo;

public class Comentario {
	private int id;
    private int idComentario;
    private int idUsuario;
    private String nombreUser;
    private String fechaComentario;
    private int modo;
    private String opinion;

    public Comentario() {
    }

    public Comentario(int idComentario, int idUsuario, String nombreUser, String fechaComentario, int modo, String opinion) {
        this.idComentario = idComentario;
        this.idUsuario = idUsuario;
        this.nombreUser = nombreUser;
        this.fechaComentario = fechaComentario;
        this.modo = modo;
        this.opinion = opinion;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(String fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}