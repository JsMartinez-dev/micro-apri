
package dto;

import java.time.LocalDate;

public record DtoUsuarioRegistro(String primer_nombre,String primer_apellido,String correo,String institucion,LocalDate fecha_nacimiento,LocalDate fecha_Registro,String contrasena_plana){

}
