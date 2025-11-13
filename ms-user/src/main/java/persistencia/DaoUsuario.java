
package persistencia;

import java.util.List;
import modelo.Persona;
import modelo.Usuario;


public interface DaoUsuario {
     
    public boolean registrar(Usuario usuario) throws Exception;
    public boolean actualizar(Usuario usuario) throws Exception;
    public boolean eliminar(Usuario usuario) throws Exception;
    public List<Usuario> listar() throws Exception; 
    public boolean validarCorreo(String correo) throws Exception;
    public Persona   correoExistente(String correo) throws Exception;
    public boolean cambiarEstadoF(int id) throws Exception;
    public boolean cambiarEstadoT(int id) throws Exception;
}
