
package modelo;

import java.util.ArrayList;
import java.util.List;


public class Reseña {
    
    
    private int id_reseña;
    private int cantidad_estrellas;
    private String comentario;
    private Usuario usuario;
    private List<Reporte> lista_reportes;

    public Reseña(int id_reseña, int cantidad_estrellas, String comentario, Usuario usuario) {
        this.id_reseña = id_reseña;
        this.cantidad_estrellas = cantidad_estrellas;
        this.comentario = comentario;
        this.usuario = usuario;
        this.lista_reportes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Rese\u00f1a{" + "id_rese\u00f1a=" + id_reseña + ", cantidad_estrellas=" + cantidad_estrellas + ", comentario=" + comentario + ", usuario=" + usuario + ", lista_reportes=" + lista_reportes + '}';
    }

    
    
    
    
    /**
     * @return the id_reseña
     */
    public int getId_reseña() {
        return id_reseña;
    }

    /**
     * @param id_reseña the id_reseña to set
     */
    public void setId_reseña(int id_reseña) {
        this.id_reseña = id_reseña;
    }

    /**
     * @return the cantidad_estrellas
     */
    public int getCantidad_estrellas() {
        return cantidad_estrellas;
    }

    /**
     * @param cantidad_estrellas the cantidad_estrellas to set
     */
    public void setCantidad_estrellas(int cantidad_estrellas) {
        this.cantidad_estrellas = cantidad_estrellas;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
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
     * @return the lista_reportes
     */
    public List<Reporte> getLista_reportes() {
        return lista_reportes;
    }

    /**
     * @param lista_reportes the lista_reportes to set
     */
    public void setLista_reportes(List<Reporte> lista_reportes) {
        this.lista_reportes = lista_reportes;
    }
    
    
    
}
