 
package persistencia;

import java.io.InputStream;
import java.util.List;
import modelo.*;



public interface DaoLibro {
     
    public boolean registrar(Libro libro, Usuario usuario,InputStream inputStream) throws Exception;
    public boolean actualizar(Libro libro) throws Exception;
    public boolean eliminar(Libro libro) throws Exception;
    public List<Libro> listar() throws Exception; 
    public boolean cambiarEstadoF(int id) throws Exception;
    public boolean cambiarEstadoT(int id) throws Exception;
    public List<MaterialEducativo> buscarListUser(Usuario user) throws Exception;
}
