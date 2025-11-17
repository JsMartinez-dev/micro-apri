
package persistencia;

import dto.DtoCursoRegistro;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import modelo.Curso;


public class DaoCursoImpPostgres implements DaoCurso{

    @Override
    public int registrar(DtoCursoRegistro cursoDto) throws Exception {
        String sql = "INSERT INTO cursos (categoria, descripcion, nombre,"
                + " anio_publicacion, estado, tipo, duracion, id_usuario, nivel) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            System.out.println("Iniciando registro de curso: " + cursoDto.nombre());

            stmt.setString(1, cursoDto.categoria());
            stmt.setString(2, cursoDto.descripcion());
            stmt.setString(3, cursoDto.nombre());
            stmt.setDate(4, Date.valueOf(cursoDto.fecha_publicacion()));
            stmt.setBoolean(5, true);
            stmt.setString(6, cursoDto.tipo());
            stmt.setInt(7,cursoDto.duracion()); 
            stmt.setInt(8, cursoDto.id_usuario());
            stmt.setString(9, cursoDto.nivel());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idCursoGenerado = generatedKeys.getInt(1);
                        System.out.println("Curso registrado con ID: " + idCursoGenerado);
                        return idCursoGenerado;
                    } else {
                        throw new ApriException("No se pudo obtener el ID del curso creado");
                    }
                }
            } else {
                throw new ApriException("No se pudo insertar el curso");
            }

        } catch (SQLException ex) {
            throw new ApriException("Error al insertar el curso a la BD: " + ex.getMessage());
        }
    }

    @Override
    public boolean actualizar(Curso libro) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Curso curso) throws Exception {
    String sql = "UPDATE cursos SET estado = FALSE WHERE id_material_educativo = ?";
    
    try(Connection conn = ConexionBD.getInstancia().getConexion();
        PreparedStatement stmt = conn.prepareStatement(sql)){
        
        stmt.setInt(1, curso.getId_materialEducativo());
        
        int filasAfectadas = stmt.executeUpdate();
        System.out.println("Curso eliminado (soft delete), filas afectadas: " + filasAfectadas);
        
        return filasAfectadas > 0;
        
    } catch (SQLException ex) {
        throw new ApriException("Error al eliminar el curso: " + ex.getMessage());
    }
}
    @Override
    public List<Curso> listar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarEstadoF(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarEstadoT(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
