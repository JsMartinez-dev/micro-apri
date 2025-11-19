package com.apri.ms.resenas.modelo;

import java.time.LocalDate;

/**
 * Entidad RespuestaReporte
 */
public class RespuestaReporte {
    private Integer idReporte;
    private Integer idAdministrador;
    private String accion;
    private String respuesta;
    private LocalDate fechaSolucion;

    public RespuestaReporte() {
    }

    public RespuestaReporte(Integer idReporte, Integer idAdministrador, String accion, 
                           String respuesta, LocalDate fechaSolucion) {
        this.idReporte = idReporte;
        this.idAdministrador = idAdministrador;
        this.accion = accion;
        this.respuesta = respuesta;
        this.fechaSolucion = fechaSolucion;
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

    public LocalDate getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(LocalDate fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    @Override
    public String toString() {
        return "RespuestaReporte{" +
                "idReporte=" + idReporte +
                ", idAdministrador=" + idAdministrador +
                ", accion='" + accion + '\'' +
                ", fechaSolucion=" + fechaSolucion +
                '}';
    }
}
