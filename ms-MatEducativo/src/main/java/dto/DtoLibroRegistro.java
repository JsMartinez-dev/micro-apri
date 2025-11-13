/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package dto;

/**
 *
 * @author ACER-A315-59
 */
public record DtoLibroRegistro(int id_libro,int id_usuario,String nombre, String descripcion,String categoria,int edicion, String año_publicacion,int cantPaginas, String tipo, String editorial) {

}
