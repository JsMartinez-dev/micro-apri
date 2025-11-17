package com.apri.ms.resenas.dto;

/**
 * DTO para crear un reporte
 */
public class DtoReporteCrear {
    private String motivo;
    private Integer idUsuario;
    private Integer idResena;

    public DtoReporteCrear() {
    }

    public DtoReporteCrear(String motivo, Integer idUsuario, Integer idResena) {
        this.motivo = motivo;
        this.idUsuario = idUsuario;
        this.idResena = idResena;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdResena() {
        return idResena;
    }

    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }
}
