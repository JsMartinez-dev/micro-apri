
package servicio;

import dto.DtoCursoRegistro;
import modelo.Curso;
import persistencia.ApriException;
import persistencia.DaoCurso;
import persistencia.DaoCursoImpPostgres;



public class CursoServicio {
    
    DaoCurso daoCurso;
    
    
    public CursoServicio(){
        daoCurso = new DaoCursoImpPostgres();
    }
    
    
    
    
    
    public int subirCurso(DtoCursoRegistro cursoDto) throws Exception {
    
        if(cursoDto != null && cursoDto.id_usuario()>0 && cursoDto.duracion()>0){
            System.out.println("Paso los filtros del curso servicio");
                    try {
                        return daoCurso.registrar(cursoDto);
                    } catch (Exception ex) {
                        System.err.println("Error en DAO: " + ex.getMessage());
                        throw new ApriException("Fallo el uso del DAOCURSO: " + ex.getMessage());
                    }
        } else {
            System.out.println("FALLO: Algún parámetro es null");
        }
        return -1;
        }

     public boolean eliminarCurso(int idMaterialEducativo) throws Exception {
    if(idMaterialEducativo > 0) {
        try {
            Curso curso = new Curso(0, 0, idMaterialEducativo, "", "", null, "", false, null, "");
            return daoCurso.eliminar(curso);
        } catch (Exception ex) {
            System.err.println("Error en servicio al eliminar curso: " + ex.getMessage());
            throw new ApriException("Fallo al eliminar el curso: " + ex.getMessage());
        }
    }
    return false;
}
    
}
