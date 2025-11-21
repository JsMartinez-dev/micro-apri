
package servicio;

import java.time.LocalDate;
import java.time.Month;
import dto.DtoUsuarioRegistro;
import modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.DaoUsuario;
import persistencia.FabConexion;


public class RegistroServicio {
    
    
    private final DaoUsuario daoUser;
    FabConexion fab ;

    public RegistroServicio() {
        fab = new FabConexion();
        this.daoUser = fab.getConexionBD("POSTGRES");
    }
    
    public String hashearPassword(String password){
    
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
    
    public boolean registrarUsuario(DtoUsuarioRegistro u) throws Exception{
        System.out.println("ENTRO AL SERVICIO");
        if(u!=null){       
                if(daoUser.validarCorreo(u.correo())){
                    if(!u.fecha_nacimiento().isBefore(LocalDate.of(1940,Month.JANUARY,1)) && !u.fecha_nacimiento().isAfter(LocalDate.of(2025,Month.NOVEMBER,12))){
                        Usuario user = new Usuario(1, u.primer_nombre(), u.primer_apellido(),u.fecha_nacimiento(), u.fecha_Registro(),u.correo(), u.institucion(),u.contrasena_plana(), false,"usuario");
                        user.setContraseña(hashearPassword(user.getContraseña()));
                        
                        return daoUser.registrar(user);
                    }
                }
            
        }
        
        return false;
    }
    
    
    
}
