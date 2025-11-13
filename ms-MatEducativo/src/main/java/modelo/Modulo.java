
package modelo;

import java.util.ArrayList;
import java.util.List;

public class Modulo {
    
    private int id_modulo;
    private String titulo;
    private List<Leccion> lista_lecciones;

    public Modulo(int id_modulo, String titulo) {
        this.id_modulo = id_modulo;
        this.titulo = titulo;
        this.lista_lecciones  = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Modulo{" + "id_modulo=" + id_modulo + ", titulo=" + titulo + ", lista_lecciones=" + lista_lecciones + '}';
    }
    

    
    
    
    /**
     * @return the id_modulo
     */
    public int getId_modulo() {
        return id_modulo;
    }

    /**
     * @param id_modulo the id_modulo to set
     */
    public void setId_modulo(int id_modulo) {
        this.id_modulo = id_modulo;
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
     * @return the lista_lecciones
     */
    public List<Leccion> getLista_lecciones() {
        return lista_lecciones;
    }

    /**
     * @param lista_lecciones the lista_lecciones to set
     */
    public void setLista_lecciones(List<Leccion> lista_lecciones) {
        this.lista_lecciones = lista_lecciones;
    }
    
    
    
    
}
