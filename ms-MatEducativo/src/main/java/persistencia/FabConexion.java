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
    
    
        public DaoArticulo getConexionBD(String motor) {

        switch (motor) {
            case "POSTGRES" -> {
                return new DaoArticuloImpPostgres();
            }
            case "MONGODB" ->{
                return new DaoArticuloImpMongo();
            }
            default ->  throw new AssertionError();
        }

    }
}
