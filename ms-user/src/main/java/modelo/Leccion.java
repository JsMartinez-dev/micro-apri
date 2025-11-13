
package modelo;

import java.util.ArrayList;
import java.util.List;


public class Leccion {
    
    private int id_leccion;
    private String titulo;
    private String url_video;
    private String descripcion;
    private List<PQRs> lista_PQRs;

    public Leccion(int id_leccion, String titulo, String url_video, String descripcion) {
        this.id_leccion = id_leccion;
        this.titulo = titulo;
        this.url_video = url_video;
        this.descripcion = descripcion;
        this.lista_PQRs = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Leccion{" + "id_leccion=" + id_leccion + ", titulo=" + titulo + ", url_video=" + url_video + ", descripcion=" + descripcion + ", lista_PQRs=" + lista_PQRs + '}';
    }

    
    
    
    /**
     * @return the id_leccion
     */
    public int getId_leccion() {
        return id_leccion;
    }

    /**
     * @param id_leccion the id_leccion to set
     */
    public void setId_leccion(int id_leccion) {
        this.id_leccion = id_leccion;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the url_video
     */
    public String getUrl_video() {
        return url_video;
    }

    /**
     * @param url_video the url_video to set
     */
    public void setUrl_video(String url_video) {
        this.url_video = url_video;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the lista_PQRs
     */
    public List<PQRs> getLista_PQRs() {
        return lista_PQRs;
    }

    /**
     * @param lista_PQRs the lista_PQRs to set
     */
    public void setLista_PQRs(List<PQRs> lista_PQRs) {
        this.lista_PQRs = lista_PQRs;
    }
    
    
}
