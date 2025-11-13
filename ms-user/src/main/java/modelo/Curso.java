
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Curso extends MaterialEducativo {
    
    private int id_curso;
    private int duracion;
    private List<Modulo> lista_modulos;

    public Curso(int id_curso, int duracion) {
        this.id_curso = id_curso;
        this.duracion = duracion;
    }

    public Curso(int id_curso, int duracion, int id_materialEducativo, String categoria, String nombre, LocalDate año_publicacion,  String descripcion, boolean estado, Usuario usuario) {
        super(id_materialEducativo, categoria, nombre, año_publicacion, "Curso", descripcion, estado, usuario);
        this.id_curso = id_curso;
        this.duracion = duracion;
        this.lista_modulos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Curso{" +super.toString()+  ", id_curso=" + id_curso + ", duracion=" + duracion + ", lista_modulos=" + lista_modulos + '}';
    }

    
    
    
    /**
     * @return the id_curso
     */
    public int getId_curso() {
        return id_curso;
    }

    /**
     * @param id_curso the id_curso to set
     */
    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    /**
     * @return the duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the lista_modulos
     */
    public List<Modulo> getLista_modulos() {
        return lista_modulos;
    }

    /**
     * @param lista_modulos the lista_modulos to set
     */
    public void setLista_modulos(List<Modulo> lista_modulos) {
        this.lista_modulos = lista_modulos;
    }
    
    
}
