
package dto;


public record DtoAdminLogin(int id_persona,String correo,String primerNombre,String primerApellido, String tipo)implements DtoPersonaLogin {

}
