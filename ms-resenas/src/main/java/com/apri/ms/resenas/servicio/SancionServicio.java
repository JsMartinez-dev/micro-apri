package com.apri.ms.resenas.servicio;

import com.apri.ms.resenas.dao.ISancionDAO;
import com.apri.ms.resenas.dao.factory.DAOFactory;
import com.apri.ms.resenas.dto.DtoEstadoUsuario;
import com.apri.ms.resenas.dto.DtoSancionCrear;
import com.apri.ms.resenas.dto.DtoSancionRespuesta;
import com.apri.ms.resenas.modelo.Sancion;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar sanciones de usuarios
 */
public class SancionServicio {
    
    private final ISancionDAO sancionDAO;
    
    public SancionServicio() {
        this.sancionDAO = DAOFactory.getInstance().getSancionDAO();
    }
    
    /**
     * Crea una advertencia para un usuario
     */
    public DtoSancionRespuesta crearAdvertencia(DtoSancionCrear dto) throws SQLException {
        Sancion sancion = new Sancion();
        sancion.setIdUsuario(dto.getIdUsuario());
        sancion.setTipoSancion("advertencia");
        sancion.setMotivo(dto.getMotivo());
        sancion.setFechaInicio(LocalDateTime.now());
        sancion.setFechaFin(null);
        sancion.setActiva(true);
        sancion.setIdAdministrador(dto.getIdAdministrador());
        sancion.setIdReporte(dto.getIdReporte());
        
        Sancion sancionCreada = sancionDAO.crear(sancion);
        return convertirADto(sancionCreada);
    }
    
    /**
     * Suspende a un usuario por un número determinado de días
     */
    public DtoSancionRespuesta suspenderUsuario(DtoSancionCrear dto) throws SQLException {
        if (dto.getDiasSuspension() == null || dto.getDiasSuspension() <= 0) {
            throw new IllegalArgumentException("Debe especificar los días de suspensión");
        }
        
        Sancion sancion = new Sancion();
        sancion.setIdUsuario(dto.getIdUsuario());
        sancion.setTipoSancion("suspension");
        sancion.setMotivo(dto.getMotivo());
        sancion.setFechaInicio(LocalDateTime.now());
        sancion.setFechaFin(LocalDateTime.now().plusDays(dto.getDiasSuspension()));
        sancion.setActiva(true);
        sancion.setIdAdministrador(dto.getIdAdministrador());
        sancion.setIdReporte(dto.getIdReporte());
        
        Sancion sancionCreada = sancionDAO.crear(sancion);
        return convertirADto(sancionCreada);
    }
    
    /**
     * Banea permanentemente a un usuario
     */
    public DtoSancionRespuesta banearUsuario(DtoSancionCrear dto) throws SQLException {
        Sancion sancion = new Sancion();
        sancion.setIdUsuario(dto.getIdUsuario());
        sancion.setTipoSancion("baneo");
        sancion.setMotivo(dto.getMotivo());
        sancion.setFechaInicio(LocalDateTime.now());
        sancion.setFechaFin(null); // null = permanente
        sancion.setActiva(true);
        sancion.setIdAdministrador(dto.getIdAdministrador());
        sancion.setIdReporte(dto.getIdReporte());
        
        Sancion sancionCreada = sancionDAO.crear(sancion);
        return convertirADto(sancionCreada);
    }
    
    /**
     * Obtiene el historial de sanciones de un usuario
     */
    public List<DtoSancionRespuesta> obtenerHistorialSanciones(Integer idUsuario) throws SQLException {
        List<Sancion> sanciones = sancionDAO.obtenerPorUsuario(idUsuario);
        return convertirListaADto(sanciones);
    }
    
    /**
     * Obtiene las sanciones activas de un usuario
     */
    public List<DtoSancionRespuesta> obtenerSancionesActivas(Integer idUsuario) throws SQLException {
        List<Sancion> sanciones = sancionDAO.obtenerSancionesActivas(idUsuario);
        return convertirListaADto(sanciones);
    }
    
    /**
     * Obtiene todas las sanciones del sistema
     */
    public List<DtoSancionRespuesta> obtenerTodasLasSanciones() throws SQLException {
        List<Sancion> sanciones = sancionDAO.obtenerTodas();
        return convertirListaADto(sanciones);
    }
    
    /**
     * Obtiene todas las sanciones activas del sistema
     */
    public List<DtoSancionRespuesta> obtenerTodasSancionesActivas() throws SQLException {
        List<Sancion> sanciones = sancionDAO.obtenerTodasActivas();
        return convertirListaADto(sanciones);
    }
    
    /**
     * Verifica si un usuario tiene acceso al sistema
     */
    public DtoEstadoUsuario verificarAccesoUsuario(Integer idUsuario) throws SQLException {
        // Primero desactivar sanciones expiradas
        sancionDAO.desactivarSancionesExpiradas();
        
        DtoEstadoUsuario estado = new DtoEstadoUsuario();
        estado.setIdUsuario(idUsuario);
        
        if (sancionDAO.estaBaneado(idUsuario)) {
            estado.setTieneAcceso(false);
            estado.setEstado("baneado");
            estado.setMensaje("Usuario baneado permanentemente del sistema");
        } else if (sancionDAO.estaSuspendido(idUsuario)) {
            estado.setTieneAcceso(false);
            estado.setEstado("suspendido");
            estado.setMensaje("Usuario temporalmente suspendido");
        } else {
            int advertencias = sancionDAO.contarAdvertencias(idUsuario);
            estado.setTieneAcceso(true);
            estado.setTotalAdvertencias(advertencias);
            
            if (advertencias > 0) {
                estado.setEstado("advertido");
                estado.setMensaje("Usuario con " + advertencias + " advertencia(s)");
            } else {
                estado.setEstado("activo");
                estado.setMensaje("Usuario activo sin sanciones");
            }
        }
        
        return estado;
    }
    
    /**
     * Levanta/desactiva una sanción
     */
    public boolean levantarSancion(Integer idSancion) throws SQLException {
        return sancionDAO.desactivarSancion(idSancion);
    }
    
    /**
     * Obtiene una sanción por ID
     */
    public DtoSancionRespuesta obtenerPorId(Integer id) throws SQLException {
        Sancion sancion = sancionDAO.obtenerPorId(id);
        return sancion != null ? convertirADto(sancion) : null;
    }
    
    // Métodos auxiliares
    private DtoSancionRespuesta convertirADto(Sancion sancion) {
        return new DtoSancionRespuesta(
            sancion.getIdSancion(),
            sancion.getIdUsuario(),
            sancion.getTipoSancion(),
            sancion.getMotivo(),
            sancion.getFechaInicio(),
            sancion.getFechaFin(),
            sancion.getActiva(),
            sancion.getIdAdministrador(),
            sancion.getIdReporte()
        );
    }
    
    private List<DtoSancionRespuesta> convertirListaADto(List<Sancion> sanciones) {
        List<DtoSancionRespuesta> dtos = new ArrayList<>();
        for (Sancion sancion : sanciones) {
            dtos.add(convertirADto(sancion));
        }
        return dtos;
    }
}
