
package modelo;


public class Inscripcion {
    
    private int id_inscripcion;
    private boolean completado;
    private String fecha_inscripcion;
    private Curso curso;

    public Inscripcion(int id_inscripcion, boolean completado, String fecha_inscripcion, Curso curso) {
        this.id_inscripcion = id_inscripcion;
        this.completado = completado;
        this.fecha_inscripcion = fecha_inscripcion;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "id_inscripcion=" + id_inscripcion + ", completado=" + completado + ", fecha_inscripcion=" + fecha_inscripcion + ", curso=" + curso + '}';
    }

    
    
    
    /**
     * @return the id_inscripcion
     */
    public int getId_inscripcion() {
        return id_inscripcion;
    }

    /**
     * @param id_inscripcion the id_inscripcion to set
     */
    public void setId_inscripcion(int id_inscripcion) {
        this.id_inscripcion = id_inscripcion;
    }

    /**
     * @return the completado
     */
    public boolean isCompletado() {
        return completado;
    }

    /**
     * @param completado the completado to set
     */
    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    /**
     * @return the fecha_inscripcion
     */
    public String getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    /**
     * @param fecha_inscripcion the fecha_inscripcion to set
     */
    public void setFecha_inscripcion(String fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    
    
    
}
