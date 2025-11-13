
package servicio;

import dto.DtoMatEducativo;
import java.io.InputStream;
import java.util.List;
import modelo.Libro;
import modelo.MaterialEducativo;
import modelo.Usuario;
import persistencia.ApriException;
import persistencia.DaoLibro;
import persistencia.DaoLibroImp;



public class LibroServicio {
    
    DaoLibro daoLib;
    
    
    public LibroServicio(){
        daoLib = new DaoLibroImp();
    }
    
    
    
    
    
        public boolean subirLibro(Libro librito, Usuario user, InputStream inputStream) throws Exception {
    
    if(librito != null && user != null && inputStream != null){
        if(librito.isEstado()){
                try {
                    return daoLib.registrar(librito, user, inputStream);
                } catch (Exception ex) {
                    System.err.println("Error en DAO: " + ex.getMessage());
                    ex.printStackTrace();
                    throw new ApriException("Fallo el uso del DAOLIBRO: " + ex.getMessage());
                }
            
        } else {
            System.out.println("FALLO: Libro no tiene estado activo");
        }
    } else {
        System.out.println("FALLO: Algún parámetro es null");
    }
    return false;
    }

    public List<DtoMatEducativo> buscarMaterialPorUsuario(int idPersona) throws Exception {

        
        if(idPersona>0){
            return daoLib.buscarListUser(idPersona);
            
        }


        return null;
    }
}
