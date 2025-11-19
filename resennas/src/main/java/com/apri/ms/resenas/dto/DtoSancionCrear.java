package com.apri.ms.resenas.dto;

/**
 * DTO para crear una sanción
 */
public class DtoSancionCrear {
    private Integer idUsuario;
    private String tipoSancion; // advertencia, suspension, baneo
    private String motivo;
    private Integer diasSuspension; // Solo para suspensiones
    private Integer idAdministrador;
    private Integer idReporte;

    public DtoSancionCrear() {
    }

    public DtoSancionCrear(Integer idUsuario, String tipoSancion, String motivo, 
                          Integer idAdministrador, Integer idReporte) {
        this.idUsuario = idUsuario;
        this.tipoSancion = tipoSancion;
        this.motivo = motivo;
        this.idAdministrador = idAdministrador;
        this.idReporte = idReporte;
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

    public Integer getDiasSuspension() {
        return diasSuspension;
    }

    public void setDiasSuspension(Integer diasSuspension) {
        this.diasSuspension = diasSuspension;
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
