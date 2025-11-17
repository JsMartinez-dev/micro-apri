
package servicio;

import dto.DtoArticuloRegistro;
import java.io.InputStream;
import persistencia.ApriException;
import persistencia.DaoArticulo;
import modelo.Articulo;
import persistencia.FabConexion;


public class ArticuloServicio {
    
    DaoArticulo daoArticulo;
    FabConexion fab;
    
    
    public ArticuloServicio(){
        fab = new  FabConexion();
        daoArticulo = fab.getConexionBD("POSTGRES");
    }
    
    
    
    public boolean subirArticulo(DtoArticuloRegistro articulo, InputStream inputStream) throws Exception {
    
        if(articulo != null && articulo.id_usuario()>0 && articulo.cantPaginas()>0 && inputStream != null){
            System.out.println("Paso los filtros del articulo servicio");
                    try {
                        //Libro libro = new Libro(librito.edicion(), librito.editorial(), librito.cantPaginas(),1, librito.categoria(), librito.nombre(),LocalDate.parse(librito.año_publicacion()),librito.tipo(),librito.descripcion(), true, null);
                        return daoArticulo.registrar(articulo,inputStream);
                    } catch (Exception ex) {
                        System.err.println("Error en DAO: " + ex.getMessage());
                        throw new ApriException("Fallo el uso del DAOARTICULO: " + ex.getMessage());
                    }
        } else {
            System.out.println("FALLO: Algún parámetro es null");
        }
        return false;
        }

       public boolean eliminarArticulo(int idMaterialEducativo) throws Exception {
    if(idMaterialEducativo > 0) {
        try {
            Articulo articulo = new Articulo(0, 0, idMaterialEducativo, "", "", null, "", "", false);
            return daoArticulo.eliminar(articulo);
        } catch (Exception ex) {
            System.err.println("Error en servicio al eliminar artículo: " + ex.getMessage());
            throw new ApriException("Fallo al eliminar el artículo: " + ex.getMessage());
        }
    }
    return false;
}
    
 public InputStream descargarArticuloPDF(int idMaterial) throws Exception {
    if(idMaterial > 0) {
        try {
            return daoArticulo.obtenerPDF(idMaterial);
        } catch (Exception ex) {
            System.err.println("Error en servicio al descargar artículo: " + ex.getMessage());
            throw new ApriException("Fallo al descargar el artículo: " + ex.getMessage());
        }
    }
    throw new ApriException("ID de material inválido");
}

public String obtenerNombreArticulo(int idMaterial) throws Exception {
    if(idMaterial > 0) {
        try {
            return daoArticulo.obtenerNombreArticulo(idMaterial);
        } catch (Exception ex) {
            return "articulo";
        }
    }
    return "articulo";
}      
       
       
       
       
}
