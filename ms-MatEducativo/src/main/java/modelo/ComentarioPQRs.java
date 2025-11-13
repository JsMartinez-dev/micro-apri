
package modelo;


public class ComentarioPQRs {
    
    private int id_comentarioPQRs;
    private String contenido;
    private Usuario usuario;

    public ComentarioPQRs(int id_comentarioPQRs, String contenido, Usuario usuario) {
        this.id_comentarioPQRs = id_comentarioPQRs;
        this.contenido = contenido;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "ComentarioPQRs{" + "id_comentarioPQRs=" + id_comentarioPQRs + ", contenido=" + contenido + ", usuario=" + usuario + '}';
    }

    
    
    
    /**
     * @return the id_comentarioPQRs
     */
    public int getId_comentarioPQRs() {
        return id_comentarioPQRs;
    }

    /**
     * @param id_comentarioPQRs the id_comentarioPQRs to set
     */
    public void setId_comentarioPQRs(int id_comentarioPQRs) {
        this.id_comentarioPQRs = id_comentarioPQRs;
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
