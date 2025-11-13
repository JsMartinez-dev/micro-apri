
package modelo;

import java.time.LocalDate;

public class Persona {
    
    private int id_persona;
    private String primer_nombre;
    private String primer_apellido;
    private LocalDate fecha_nacimiento;
    private LocalDate fecha_registro;
    private String correo;
    private String institucion;
    private String tipo;
    private String contraseña;
    private boolean estado;

    public Persona() {
    }

    public Persona(int id_persona, String primer_nombre, String primer_apellido, LocalDate fecha_nacimiento, LocalDate fecha_registro, String correo, String institucion, String tipo, String contraseña, boolean estado) {
        this.id_persona = id_persona;
        this.primer_nombre = primer_nombre;
        this.primer_apellido = primer_apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_registro = fecha_registro;
        this.correo = correo;
        this.institucion = institucion;
        this.tipo = tipo;
        this.contraseña = contraseña;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "id_persona=" + id_persona + ", primer_nombre=" + primer_nombre + ", primer_apellido=" + primer_apellido + ", fecha_nacimiento=" + fecha_nacimiento + ", fecha_registro=" + fecha_registro + ", correo=" + correo + ", institucion=" + institucion + ", tipo=" + tipo + ", contrasenia=" + contraseña + ", estado=" + estado + '}';
    }
    
    

    /**
     * @return the id_persona
     */
    public int getId_persona() {
        return id_persona;
    }

    /**
     * @param id_persona the id_persona to set
     */
    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    /**
     * @return the primer_nombre
     */
    public String getPrimer_nombre() {
        return primer_nombre;
    }

    /**
     * @param primer_nombre the primer_nombre to set
     */
    public void setPrimer_nombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
    }

    /**
     * @return the primer_apellido
     */
    public String getPrimer_apellido() {
        return primer_apellido;
    }

    /**
     * @param primer_apellido the primer_apellido to set
     */
    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    /**
     * @return the fecha_nacimiento
     */
    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    /**
     * @param fecha_nacimiento the fecha_nacimiento to set
     */
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * @return the fecha_registro
     */
    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    /**
     * @param fecha_registro the fecha_registro to set
     */
    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
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
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
    
    
    
}
