package com.apri.ms.resenas.dto;

/**
 * DTO para respuesta de reseña
 */
public class DtoResenaRespuesta {
    private Integer idResena;
    private String comentario;
    private Integer cantidadEstrellas;
    private Integer idUsuario;
    private Integer idMaterialEducativo;

    public DtoResenaRespuesta() {
    }

    public DtoResenaRespuesta(Integer idResena, String comentario, Integer cantidadEstrellas, 
                             Integer idUsuario, Integer idMaterialEducativo) {
        this.idResena = idResena;
        this.comentario = comentario;
        this.cantidadEstrellas = cantidadEstrellas;
        this.idUsuario = idUsuario;
        this.idMaterialEducativo = idMaterialEducativo;
    }

    public Integer getIdResena() {
        return idResena;
    }

    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCantidadEstrellas() {
        return cantidadEstrellas;
    }

    public void setCantidadEstrellas(Integer cantidadEstrellas) {
        this.cantidadEstrellas = cantidadEstrellas;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdMaterialEducativo() {
        return idMaterialEducativo;
    }

    public void setIdMaterialEducativo(Integer idMaterialEducativo) {
        this.idMaterialEducativo = idMaterialEducativo;
    }
}
