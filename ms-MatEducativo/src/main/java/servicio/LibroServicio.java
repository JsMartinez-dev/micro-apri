
package servicio;

import dto.DtoLibroRegistro;
import dto.DtoMatEducativo;
import java.io.InputStream;
import java.util.List;
import persistencia.ApriException;
import persistencia.DaoLibro;
import persistencia.DaoLibroImp;



public class LibroServicio {
    
    DaoLibro daoLib;
    
    
    public LibroServicio(){
        daoLib = new DaoLibroImp();
    }
    
    
    
    
    
    public boolean subirLibro(DtoLibroRegistro librito, InputStream inputStream) throws Exception {
    
        if(librito != null && librito.id_usuario()>0 && librito.cantPaginas()>0 && inputStream != null){
            System.out.println("Paso los filtros del libro servicio");
                    try {
                        //Libro libro = new Libro(librito.edicion(), librito.editorial(), librito.cantPaginas(),1, librito.categoria(), librito.nombre(),LocalDate.parse(librito.año_publicacion()),librito.tipo(),librito.descripcion(), true, null);
                        return daoLib.registrar(librito,inputStream);
                    } catch (Exception ex) {
                        System.err.println("Error en DAO: " + ex.getMessage());
                        throw new ApriException("Fallo el uso del DAOLIBRO: " + ex.getMessage());
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
