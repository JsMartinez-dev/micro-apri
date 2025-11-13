
package persistencia;

import dto.DtoLibroRegistro;
import dto.DtoMatEducativo;
import edu.apri.collections.CircularList;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import modelo.Libro;
import modelo.Usuario;


public class DaoLibroImp implements DaoLibro{

   @Override
    public boolean registrar(DtoLibroRegistro libro, InputStream inputStream) throws ApriException{

        String sql = "INSERT INTO libros( categoria, descripcion, nombre, anio_publicacion, estado, tipo, edicion, editorial, cantidad_paginas, id_usuario, archivopdf ) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try(Connection conn = ConexionBD.getInstancia().getConexion();
                PreparedStatement stmt = conn.prepareCall(sql)){
         System.out.println("Iniciando registro de libro: " + libro.nombre());
           
            stmt.setString(1, libro.categoria());
            stmt.setString(2, libro.descripcion());
            stmt.setString(3,libro.nombre());
            stmt.setDate(4, Date.valueOf(libro.año_publicacion()));
            stmt.setBoolean(5,true);
            stmt.setString(6, libro.tipo());
            stmt.setInt(7,libro.edicion());
        stmt.setString(8,libro.editorial());
            stmt.setInt(9,libro.cantPaginas());
            stmt.setInt(10,libro.id_usuario());
            stmt.setBinaryStream(11, inputStream);
            
            if(stmt.executeUpdate()>0){
                return true;
            }
        }catch (SQLException ex) {
        throw new ApriException("Error al insertar el libro a la BD: " + ex.getMessage());
    }
        
        return false;
    }
    
    @Override
    public List<DtoMatEducativo> buscarListUser(int user) throws Exception {
        
        List<DtoMatEducativo> listaM = new CircularList<>();
        
        String sql = "SELECT * FROM materiales_educativos WHERE id_usuario = ?";
        
        try(Connection conn =  ConexionBD.getInstancia().getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,user);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                
               int idMaterialEducativo = rs.getInt("id_material_educativo");
               String categoria = rs.getString("categoria");
               String descripcion = rs.getString("descripcion");
               String nombre = rs.getString("nombre");
               LocalDate anioPublicacion = rs.getDate("anio_publicacion").toLocalDate();
               String anio_String = String.valueOf(anioPublicacion);
               boolean estado = rs.getBoolean("estado");
               String tipo = rs.getString("tipo");               
               listaM.add(new DtoMatEducativo(idMaterialEducativo,nombre, categoria, anio_String,descripcion,tipo));
               
                
            }
            
        } catch (SQLException e) {
            System.err.println("Error SQL al consultar materiales educativos: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error: algún valor obtenido del ResultSet es nulo. " + e.getMessage());
        }catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
        return listaM;
    }
    
    @Override
    public boolean actualizar(Libro libro) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Libro libro) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Libro> listar() throws Exception {
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
