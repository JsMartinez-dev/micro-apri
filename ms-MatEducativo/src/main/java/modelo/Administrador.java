/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author ACER-A315-59
 */
public class Administrador extends Persona {
    
    private String rol;

    public Administrador(String rol) {
        this.rol = rol;
    }

    public Administrador(String rol, int id_persona, String primer_nombre, String primer_apellido, LocalDate fecha_nacimiento, LocalDate fecha_registro, String correo, String institucion, String contraseña, boolean estado, String tipo) {
        super(id_persona, primer_nombre, primer_apellido, fecha_nacimiento, fecha_registro, correo, institucion, tipo, contraseña, estado);
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Administrador{" +super.toString()+ "rol=" + rol + '}';
    }

    
    
    
    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
