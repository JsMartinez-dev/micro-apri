package dto;

import java.time.LocalDate;

public class DtoReporteRespuesta {
    private Integer idReporte;
    private String motivo;
    private String estado;
    private LocalDate fechaReporte;
    private Integer idUsuario;
    private Integer idResena;

    public DtoReporteRespuesta() {}

    public DtoReporteRespuesta(Integer idReporte, String motivo, String estado,
                               LocalDate fechaReporte, Integer idUsuario, Integer idResena) {
        this.idReporte = idReporte;
        this.motivo = motivo;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
        this.idUsuario = idUsuario;
        this.idResena = idResena;
    }

    public Integer getIdReporte() { return idReporte; }
    public void setIdReporte(Integer idReporte) { this.idReporte = idReporte; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDate fechaReporte) { this.fechaReporte = fechaReporte; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdResena() { return idResena; }
    public void setIdResena(Integer idResena) { this.idResena = idResena; }
}
