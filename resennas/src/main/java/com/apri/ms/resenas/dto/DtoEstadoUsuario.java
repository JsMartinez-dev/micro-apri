package com.apri.ms.resenas.dto;

/**
 * DTO para verificar el estado de sanción de un usuario
 */
public class DtoEstadoUsuario {
    private Integer idUsuario;
    private Boolean tieneAcceso;
    private String estado; // activo, advertido, suspendido, baneado
    private Integer totalAdvertencias;
    private String mensaje;

    public DtoEstadoUsuario() {
    }

    public DtoEstadoUsuario(Integer idUsuario, Boolean tieneAcceso, String estado, 
                           Integer totalAdvertencias, String mensaje) {
        this.idUsuario = idUsuario;
        this.tieneAcceso = tieneAcceso;
        this.estado = estado;
        this.totalAdvertencias = totalAdvertencias;
        this.mensaje = mensaje;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getTieneAcceso() {
        return tieneAcceso;
    }

    public void setTieneAcceso(Boolean tieneAcceso) {
        this.tieneAcceso = tieneAcceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTotalAdvertencias() {
        return totalAdvertencias;
    }

    public void setTotalAdvertencias(Integer totalAdvertencias) {
        this.totalAdvertencias = totalAdvertencias;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
