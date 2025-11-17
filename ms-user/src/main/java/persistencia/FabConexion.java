/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author ACER-A315-59
 */
public class FabConexion {
    
    
        public DaoUsuario getConexionBD(String motor) {

        switch (motor) {
            case "POSTGRES" -> {
                return new DaoUsuarioImpPostgres();
            }
            case "MONGODB" ->{
                return new DaoUsuarioImpMongo();
            }
            default ->  throw new AssertionError();
        }

    }
}
