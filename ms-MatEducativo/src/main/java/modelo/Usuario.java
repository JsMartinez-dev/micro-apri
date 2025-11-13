
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona{
    
    private List<Descarga> lista_descargas;
    private List<Inscripcion> lista_inscripciones;

    public Usuario() {
    }

    public Usuario(int id_persona, String primer_nombre, String primer_apellido, LocalDate fecha_nacimiento, LocalDate fecha_registro, String correo, String institucion, String contraseña, boolean estado, String tipo) {
        super(id_persona, primer_nombre, primer_apellido, fecha_nacimiento, fecha_registro, correo, institucion, tipo, contraseña, estado);
        this.lista_descargas = new ArrayList<>();
        this.lista_inscripciones = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Usuario{" +super.toString()+", lista_descargas=" + lista_descargas + ", lista_inscripciones=" + lista_inscripciones + '}';
    }

    
    
    
    /**
     * @return the lista_descargas
     */
    public List<Descarga> getLista_descargas() {
        return lista_descargas;
    }

    /**
     * @param lista_descargas the lista_descargas to set
     */
    public void setLista_descargas(List<Descarga> lista_descargas) {
        this.lista_descargas = lista_descargas;
    }

    /**
     * @return the lista_inscripciones
     */
    public List<Inscripcion> getLista_inscripciones() {
        return lista_inscripciones;
    }

    /**
     * @param lista_inscripciones the lista_inscripciones to set
     */
    public void setLista_inscripciones(List<Inscripcion> lista_inscripciones) {
        this.lista_inscripciones = lista_inscripciones;
    }
    
    
}
