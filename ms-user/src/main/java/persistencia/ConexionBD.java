
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utilidad.Ruta;


public class ConexionBD {
    
    
    private static ConexionBD instancia= null;
    
    private ConexionBD() throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver encontrado");
        }catch(ClassNotFoundException e){
            System.out.println("Driver no encontrado: "+e.getMessage());
            throw new SQLException("No se pudo cargar el Driver de postgresql");
        }
        
    }
    
    public static ConexionBD getInstancia() throws SQLException{
        if(instancia==null){
            instancia = new ConexionBD();
        }
        return instancia;

    }
    
    public  Connection getConexion() throws SQLException { 
            Connection conn = DriverManager.getConnection(Ruta.BD_URL, Ruta.BD_USER, Ruta.BD_PASSWORD);
            System.out.println("Nueva conexion exitosa");
            return conn;

    }


    public static void cerrarConexion(Connection conn) throws SQLException{
        if(conn!=null){
            if(!conn.isClosed()){
                conn.close();
            }
        }
    }
    
    
    
}

   