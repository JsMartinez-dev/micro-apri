
package servicio;

import dto.DtoAdminLogin;
import modelo.Administrador;
import dto.DtoPersonaLogin;
import dto.DtoUsuarioLogin;
import modelo.Persona;
import modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.DaoUsuario;
import persistencia.FabConexion;


public class LoginServicio {
   
    
    DaoUsuario daoUser;
    FabConexion fab ;

    public LoginServicio() {
        fab = new FabConexion();
        daoUser = fab.getConexionBD("MONGODB");
    }
    
    public DtoPersonaLogin iniciarSesion(String correo, String contraseña) throws Exception{
        
        Persona u = daoUser.correoExistente(correo);
        
        if(u!=null){
            
            if(u instanceof Usuario){
                if(validarContraseñaUsuario(contraseña,u.getContraseña())){
                   return convertirUserDto(u);
                }
            }

            if(u instanceof Administrador){
                
                if(contraseña.equals(u.getContraseña())){
                   return convertirAdminDto(u);
                   
                }
            }
            
        }
        
        return null;
    }

    private boolean validarContraseñaUsuario(String contraseña, String contraseñaHash) {
        
        return BCrypt.checkpw(contraseña, contraseñaHash);
    }

   
    public boolean cerrarSesion(DtoUsuarioLogin u) throws Exception{
        
        return daoUser.cambiarEstadoF(u.id_persona());
    }

    private DtoPersonaLogin convertirUserDto(Persona u) {
        return new DtoUsuarioLogin(u.getId_persona(),u.getCorreo(),u.getPrimer_nombre(),u.getPrimer_apellido(),u.getTipo(),u.isEstado(),u.getInstitucion(),String.valueOf(u.getFecha_nacimiento()));

    }

    private DtoPersonaLogin convertirAdminDto(Persona u) {
        return new DtoAdminLogin(u.getId_persona(),u.getCorreo(),u.getPrimer_nombre(),u.getPrimer_apellido(),u.getTipo());
    }
    
    
}
