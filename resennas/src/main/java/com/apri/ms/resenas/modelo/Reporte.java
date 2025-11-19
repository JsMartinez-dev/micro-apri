package com.apri.ms.resenas.modelo;

import java.time.LocalDate;

/**
 * Entidad Reporte
 */
public class Reporte {
    private Integer idReporte;
    private String motivo;
    private String estado;
    private LocalDate fechaReporte;
    private Integer idUsuario;
    private Integer idResena;

    public Reporte() {
    }

    public Reporte(Integer idReporte, String motivo, String estado, LocalDate fechaReporte, 
                   Integer idUsuario, Integer idResena) {
        this.idReporte = idReporte;
        this.motivo = motivo;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
        this.idUsuario = idUsuario;
        this.idResena = idResena;
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
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

    @Override
    public String toString() {
        return "Reporte{" +
                "idReporte=" + idReporte +
                ", motivo='" + motivo + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaReporte=" + fechaReporte +
                ", idResena=" + idResena +
                '}';
    }
}
