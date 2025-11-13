/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import java.util.List;
import modelo.Usuario;
import persistencia.DaoUsuario;
import persistencia.DaoUsuarioImpPostgres;

/**
 *
 * @author ACER-A315-59
 */
public class UsuarioServicio {
    
    DaoUsuario daoUser;
    
    public UsuarioServicio(){
        daoUser = new DaoUsuarioImpPostgres();
    }
    
    public List<Usuario> listUser() throws Exception{
                    System.out.println("PASO LA sERVICIO");

        if(daoUser.listar()!=null){
            
            return daoUser.listar();
        }
        return null;
    }
    
    
}
