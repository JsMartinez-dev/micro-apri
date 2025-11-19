package com.apri.ms.resenas.dto;

/**
 * DTO para crear respuesta a reporte
 */
public class DtoRespuestaReporteCrear {
    private Integer idReporte;
    private Integer idAdministrador;
    private String accion;
    private String respuesta;

    public DtoRespuestaReporteCrear() {
    }

    public DtoRespuestaReporteCrear(Integer idReporte, Integer idAdministrador, 
                                   String accion, String respuesta) {
        this.idReporte = idReporte;
        this.idAdministrador = idAdministrador;
        this.accion = accion;
        this.respuesta = respuesta;
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
