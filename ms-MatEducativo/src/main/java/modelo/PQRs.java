
package modelo;

import java.util.ArrayList;
import java.util.List;


public class PQRs {
    
    
    private int id_PQRs;
    private String fecha_creacion;
    private String tipo;
    private boolean estado;
    private Usuario usuario;
    private List<ComentarioPQRs> lista_comentariosPQRs;

    public PQRs(int id_PQRs, String fecha_creacion, String tipo, boolean estado, Usuario usuario) {
        this.id_PQRs = id_PQRs;
        this.fecha_creacion = fecha_creacion;
        this.tipo = tipo;
        this.estado = estado;
        this.usuario = usuario;
        this.lista_comentariosPQRs = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "PQRs{" + "id_PQRs=" + id_PQRs + ", fecha_creacion=" + fecha_creacion + ", tipo=" + tipo + ", estado=" + estado + ", usuario=" + usuario + ", lista_comentariosPQRs=" + lista_comentariosPQRs + '}';
    }

    
    
    
    
    /**
     * @return the id_PQRs
     */
    public int getId_PQRs() {
        return id_PQRs;
    }

    /**
     * @param id_PQRs the id_PQRs to set
     */
    public void setId_PQRs(int id_PQRs) {
        this.id_PQRs = id_PQRs;
    }

    /**
     * @return the fecha_creacion
     */
    public String getFecha_creacion() {
        return fecha_creacion;
    }

    /**
     * @param fecha_creacion the fecha_creacion to set
     */
    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
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

    /**
     * @return the lista_comentariosPQRs
     */
    public List<ComentarioPQRs> getLista_comentariosPQRs() {
        return lista_comentariosPQRs;
    }

    /**
     * @param lista_comentariosPQRs the lista_comentariosPQRs to set
     */
    public void setLista_comentariosPQRs(List<ComentarioPQRs> lista_comentariosPQRs) {
        this.lista_comentariosPQRs = lista_comentariosPQRs;
    }
    
    
    
    
}
