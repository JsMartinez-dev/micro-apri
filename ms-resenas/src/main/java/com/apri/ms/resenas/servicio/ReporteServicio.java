package com.apri.ms.resenas.servicio;

import com.apri.ms.resenas.dao.IReporteDAO;
import com.apri.ms.resenas.dao.factory.DAOFactory;
import com.apri.ms.resenas.dto.DtoReporteCrear;
import com.apri.ms.resenas.dto.DtoReporteRespuesta;
import com.apri.ms.resenas.modelo.Reporte;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar reportes
 */
public class ReporteServicio {
    
    private final IReporteDAO reporteDAO;
    
    public ReporteServicio() {
        this.reporteDAO = DAOFactory.getInstance().getReporteDAO();
    }
    
    /**
     * Crea un nuevo reporte
     */
    public DtoReporteRespuesta crearReporte(DtoReporteCrear dto) throws SQLException {
        Reporte reporte = new Reporte();
        reporte.setMotivo(dto.getMotivo());
        reporte.setFechaReporte(LocalDate.now());
        reporte.setIdUsuario(dto.getIdUsuario());
        reporte.setIdResena(dto.getIdResena());
        
        Reporte reporteCreado = reporteDAO.crear(reporte);
        return mapearADto(reporteCreado);
    }
    
    /**
     * Mapea un Reporte a DtoReporteRespuesta
     */
    private DtoReporteRespuesta mapearADto(Reporte reporte) {
        return new DtoReporteRespuesta(
            reporte.getIdReporte(),
            reporte.getMotivo(),
            reporte.getEstado(),
            reporte.getFechaReporte(),
            reporte.getIdUsuario(),
            reporte.getIdResena()
        );
    }
    
    /**
     * Obtiene un reporte por ID
     */
    public Reporte obtenerPorId(Integer id) throws SQLException {
        return reporteDAO.obtenerPorId(id);
    }
    
    /**
     * Obtiene un reporte por ID como DTO
     */
    public DtoReporteRespuesta obtenerPorIdDto(Integer id) throws SQLException {
        Reporte reporte = reporteDAO.obtenerPorId(id);
        return reporte != null ? mapearADto(reporte) : null;
    }
    
    /**
     * Actualiza el estado de un reporte
     */
    public boolean actualizarEstado(Integer id, String nuevoEstado) throws SQLException {
        Reporte reporte = reporteDAO.obtenerPorId(id);
        if (reporte != null) {
            reporte.setEstado(nuevoEstado);
            return reporteDAO.actualizar(reporte);
        }
        return false;
    }
    
    /**
     * Obtiene todos los reportes
     */
    public List<DtoReporteRespuesta> obtenerTodos() throws SQLException {
        return reporteDAO.obtenerTodos().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene reportes de una reseña específica
     */
    public List<DtoReporteRespuesta> obtenerPorResena(Integer idResena) throws SQLException {
        return reporteDAO.obtenerPorResena(idResena).stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene reportes por estado
     */
    public List<DtoReporteRespuesta> obtenerPorEstado(String estado) throws SQLException {
        return reporteDAO.obtenerPorEstado(estado).stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene reportes de un usuario específico
     */
    public List<DtoReporteRespuesta> obtenerPorUsuario(Integer idUsuario) throws SQLException {
        return reporteDAO.obtenerPorUsuario(idUsuario).stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza un reporte
     */
    public boolean actualizarReporte(Reporte reporte) throws SQLException {
        return reporteDAO.actualizar(reporte);
    }
    
    /**
     * Elimina un reporte
     */
    public boolean eliminarReporte(Integer id) throws SQLException {
        return reporteDAO.eliminar(id);
    }
}
