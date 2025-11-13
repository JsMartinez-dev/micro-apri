
package modelo;


public class Reporte {
    
    private int id_reporte;
    private String motivo;
    private String fecha_reporte;
    private Usuario usuario;

    public Reporte(int id_reporte, String motivo, String fecha_reporte, Usuario usuario) {
        this.id_reporte = id_reporte;
        this.motivo = motivo;
        this.fecha_reporte = fecha_reporte;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Reporte{" + "id_reporte=" + id_reporte + ", motivo=" + motivo + ", fecha_reporte=" + fecha_reporte + ", usuario=" + usuario + '}';
    }

    
    
    
    /**
     * @return the id_reporte
     */
    public int getId_reporte() {
        return id_reporte;
    }

    /**
     * @param id_reporte the id_reporte to set
     */
    public void setId_reporte(int id_reporte) {
        this.id_reporte = id_reporte;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the fecha_reporte
     */
    public String getFecha_reporte() {
        return fecha_reporte;
    }

    /**
     * @param fecha_reporte the fecha_reporte to set
     */
    public void setFecha_reporte(String fecha_reporte) {
        this.fecha_reporte = fecha_reporte;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    
}
