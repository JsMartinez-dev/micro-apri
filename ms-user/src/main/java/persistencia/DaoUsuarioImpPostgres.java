
package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.Administrador;
import modelo.Persona;
import modelo.Usuario;

public class DaoUsuarioImpPostgres  implements DaoUsuario{

@Override
public boolean registrar(Usuario usuario) throws Exception {
    String sql = "INSERT INTO usuarios(primer_nombre, primer_apellido, correo, institucion, fecha_nacimiento, fecha_registro, contrasena, estado, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    
    try(Connection conn = ConexionBD.getInstancia().getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)){

        
        System.out.println("ANTES DE GUARDAR - Datos del usuario:");
        System.out.println("- Nombre: " + usuario.getPrimer_nombre());
        System.out.println("- Apellido: " + usuario.getPrimer_apellido());
        System.out.println("- Correo: " + usuario.getCorreo());
        System.out.println("- Institución: " + usuario.getInstitucion());
        System.out.println("- Fecha nacimiento: " + usuario.getFecha_nacimiento());
        System.out.println("- Fecha registro: " + usuario.getFecha_registro());
        System.out.println("- Estado: " + usuario.isEstado());
        System.out.println("- Tipo: " + usuario.getTipo());
        
        stmt.setString(1, usuario.getPrimer_nombre());
        stmt.setString(2, usuario.getPrimer_apellido());
        stmt.setString(3, usuario.getCorreo());
        stmt.setString(4, usuario.getInstitucion());
        stmt.setDate(5, Date.valueOf(usuario.getFecha_nacimiento()));
        stmt.setDate(6, Date.valueOf(usuario.getFecha_registro()));
        stmt.setString(7, usuario.getContraseña());
        stmt.setBoolean(8, usuario.isEstado());
        stmt.setString(9, usuario.getTipo());
        
        
        int filasAfectadas = stmt.executeUpdate();
        System.out.println("INSERT ejecutado, filas afectadas: " + filasAfectadas);
        
        return filasAfectadas > 0;
        
    }
    
}
    @Override
    public boolean actualizar(Usuario usuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Usuario usuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> listar() throws Exception {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> listU =new ArrayList<>();
        try(Connection conn =ConexionBD.getInstancia().getConexion();
            Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("PASO LA CONEXIONNN");
            while(rs.next()){
                int idPersona = rs.getInt("id_persona");
                String primerNombre = rs.getString("primer_nombre");
                String primerApellido = rs.getString("primer_apellido");
                String correo = rs.getString("correo");
                String institucion = rs.getString("institucion");
                LocalDate fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
                LocalDate fechaRegistro = rs.getDate("fecha_registro").toLocalDate();
                String contrasenaHasheada = rs.getString("contrasena");
                boolean estado = rs.getBoolean("estado");
                String tipo = rs.getString("tipo");
                Usuario u= new Usuario(idPersona, primerNombre, primerApellido, 
                              fechaNacimiento, fechaRegistro, correo, 
                              institucion, contrasenaHasheada, estado, tipo);
                listU.add(u);
            }
            
            
        }
        return listU;
    }
    
    @Override
    public boolean validarCorreo(String correo) throws SQLException {

        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try(Connection conn = ConexionBD.getInstancia().getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1,correo);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return false;
            }
            
        }
        return true;
    }

    @Override
    public Persona correoExistente(String correo) throws Exception {
        String sql = "SELECT * FROM personas WHERE correo = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("--__--Encontramos al usuario-__--");
                return crearPersonaDesdeResultSet(rs);
            }
        }

        return null;
    }

    private Persona crearPersonaDesdeResultSet(ResultSet rs) throws Exception {
        int idPersona = rs.getInt("id_persona");
        String primerNombre = rs.getString("primer_nombre");
        String primerApellido = rs.getString("primer_apellido");
        String correo = rs.getString("correo");
        String institucion = rs.getString("institucion");
        LocalDate fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
        LocalDate fechaRegistro = rs.getDate("fecha_registro").toLocalDate();
        String contrasenaHasheada = rs.getString("contrasena");
        boolean estado = (cambiarEstadoT(idPersona));
        String tipo = rs.getString("tipo");

        if ("usuario".equals(tipo)) {
            return new Usuario(idPersona, primerNombre, primerApellido, 
                              fechaNacimiento, fechaRegistro, correo, 
                              institucion, contrasenaHasheada, estado, tipo);
        } else {
            return new Administrador("general", idPersona, primerNombre, 
                                    primerApellido, fechaNacimiento, fechaRegistro, 
                                    correo, institucion, contrasenaHasheada, estado, tipo);
        }
    }


    @Override
    public boolean cambiarEstadoF(int id) throws Exception {
        String sql = "UPDATE usuarios SET estado = FALSE WHERE  id_persona =?";
        
        try(Connection con = ConexionBD.getInstancia().getConexion();
                PreparedStatement stmt = con.prepareStatement(sql)){
            
            stmt.setInt(1,id);
            return stmt.executeUpdate() > 0;
        }    }

    @Override
    public boolean cambiarEstadoT(int id) throws Exception {
        String sql = "UPDATE usuarios SET estado = TRUE WHERE  id_persona =?";
        
        try(Connection con = ConexionBD.getInstancia().getConexion();
                PreparedStatement stmt = con.prepareStatement(sql)){
            
            stmt.setInt(1,id);
            return stmt.executeUpdate() > 0;
        }
            }
    
}
