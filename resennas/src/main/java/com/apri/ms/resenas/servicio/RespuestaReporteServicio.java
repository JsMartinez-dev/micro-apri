package com.apri.ms.resenas.servicio;

import com.apri.ms.resenas.dao.IReporteDAO;
import com.apri.ms.resenas.dao.IRespuestaReporteDAO;
import com.apri.ms.resenas.dao.factory.DAOFactory;
import com.apri.ms.resenas.dto.DtoRespuestaReporteCrear;
import com.apri.ms.resenas.modelo.Reporte;
import com.apri.ms.resenas.modelo.RespuestaReporte;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para gestionar respuestas a reportes
 */
public class RespuestaReporteServicio {
    
    private final IRespuestaReporteDAO respuestaDAO;
    private final IReporteDAO reporteDAO;
    
    public RespuestaReporteServicio() {
        this.respuestaDAO = DAOFactory.getInstance().getRespuestaReporteDAO();
        this.reporteDAO = DAOFactory.getInstance().getReporteDAO();
    }
    
    /**
     * Crea una respuesta a un reporte y actualiza el estado del reporte
     */
    public RespuestaReporte crearRespuesta(DtoRespuestaReporteCrear dto) throws SQLException {
        RespuestaReporte respuesta = new RespuestaReporte();
        respuesta.setIdReporte(dto.getIdReporte());
        respuesta.setIdAdministrador(dto.getIdAdministrador());
        respuesta.setAccion(dto.getAccion());
        respuesta.setRespuesta(dto.getRespuesta());
        respuesta.setFechaSolucion(LocalDate.now());
        
        // Crear la respuesta
        RespuestaReporte respuestaCreada = respuestaDAO.crear(respuesta);
        
        // Actualizar el estado del reporte según la acción
        Reporte reporte = reporteDAO.obtenerPorId(dto.getIdReporte());
        if (reporte != null) {
            String nuevoEstado = determinarEstadoReporte(dto.getAccion());
            reporte.setEstado(nuevoEstado);
            reporteDAO.actualizar(reporte);
        }
        
        return respuestaCreada;
    }
    
    /**
     * Determina el nuevo estado del reporte según la acción tomada
     */
    private String determinarEstadoReporte(String accion) {
        switch (accion) {
            case "eliminar_resena":
            case "suspender_usuario":
            case "advertir_usuario":
                return "resuelto";
            case "rechazar_reporte":
                return "rechazado";
            default:
                return "resuelto";
        }
    }
    
    /**
     * Obtiene la respuesta de un reporte
     */
    public RespuestaReporte obtenerPorReporte(Integer idReporte) throws SQLException {
        return respuestaDAO.obtenerPorReporte(idReporte);
    }
    
    /**
     * Obtiene todas las respuestas
     */
    public List<RespuestaReporte> obtenerTodas() throws SQLException {
        return respuestaDAO.obtenerTodas();
    }
    
    /**
     * Actualiza una respuesta
     */
    public boolean actualizarRespuesta(RespuestaReporte respuesta) throws SQLException {
        return respuestaDAO.actualizar(respuesta);
    }
}
