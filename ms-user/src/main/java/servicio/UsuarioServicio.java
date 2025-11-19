/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dto.DtoUsuarioLogin;
import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;
import persistencia.DaoUsuario;
import persistencia.FabConexion;

/**
 *
 * @author ACER-A315-59
 */
public class UsuarioServicio {
    
    DaoUsuario daoUser;
    FabConexion fabrica;
    
    public UsuarioServicio(){
        fabrica = new FabConexion();
        daoUser = fabrica.getConexionBD("MONGODB");
    }
    
    public List<DtoUsuarioLogin> listUser() throws Exception{
                    System.out.println("PASO LA sERVICIO");

        List<DtoUsuarioLogin> listaDto = new ArrayList<>();
        if(daoUser.listar()!=null){
            
            List<Usuario> listU= daoUser.listar();
            if(listU!=null){
                for (Usuario usuario : listU) {
                    listaDto.add(new DtoUsuarioLogin(usuario.getId_persona(), usuario.getCorreo(), usuario.getPrimer_nombre(), 
                                    usuario.getPrimer_apellido(), usuario.getTipo(),usuario.isEstado(),usuario.getInstitucion(),String.valueOf(usuario.getFecha_nacimiento())));
                }
                return listaDto;
            }
        }
        return null;
    }

    public boolean eliminarUsuario(int id_user) {
        if(id_user>0){
            try {
                   System.out.println("Paso capa servicio de eliminacion");
                return daoUser.eliminar(id_user);
            } catch (Exception ex) {
                System.out.println("Error en la capa servicio: "+ex.getMessage());
            }
        
        }
        return false;
    }

    public List<DtoUsuarioLogin> buscarUsuarioPorNombre(String nombreUser) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
