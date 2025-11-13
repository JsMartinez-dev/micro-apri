
package modelo;


public class RespuestaReporte {
    
    private Administrador administrador;
    private Reporte reporte;
    private String accion;
    private String respuesta;
    private String fecha_solucion;

    public RespuestaReporte(Administrador administrador, Reporte reporte, String accion, String respuesta, String fecha_solucion) {
        this.administrador = administrador;
        this.reporte = reporte;
        this.accion = accion;
        this.respuesta = respuesta;
        this.fecha_solucion = fecha_solucion;
    }

    @Override
    public String toString() {
        return "RespuestaReporte{" + "administrador=" + administrador + ", reporte=" + reporte + ", accion=" + accion + ", respuesta=" + respuesta + ", fecha_solucion=" + fecha_solucion + '}';
    }
    
    

    /**
     * @return the administrador
     */
    public Administrador getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the reporte
     */
    public Reporte getReporte() {
        return reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    /**
     * @return the accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * @return the fecha_solucion
     */
    public String getFecha_solucion() {
        return fecha_solucion;
    }

    /**
     * @param fecha_solucion the fecha_solucion to set
     */
    public void setFecha_solucion(String fecha_solucion) {
        this.fecha_solucion = fecha_solucion;
    }
    
    
}
