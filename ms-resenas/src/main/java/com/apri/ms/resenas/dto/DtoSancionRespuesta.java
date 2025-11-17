package com.apri.ms.resenas.dto;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de sanción
 */
public class DtoSancionRespuesta {
    private Integer idSancion;
    private Integer idUsuario;
    private String tipoSancion;
    private String motivo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean activa;
    private Integer idAdministrador;
    private Integer idReporte;

    public DtoSancionRespuesta() {
    }

    public DtoSancionRespuesta(Integer idSancion, Integer idUsuario, String tipoSancion, 
                              String motivo, LocalDateTime fechaInicio, LocalDateTime fechaFin, 
                              Boolean activa, Integer idAdministrador, Integer idReporte) {
        this.idSancion = idSancion;
        this.idUsuario = idUsuario;
        this.tipoSancion = tipoSancion;
        this.motivo = motivo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.idAdministrador = idAdministrador;
        this.idReporte = idReporte;
    }

    public Integer getIdSancion() {
        return idSancion;
    }

    public void setIdSancion(Integer idSancion) {
        this.idSancion = idSancion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }
}
