package com.apri.ms.resenas.servicio;

import com.apri.ms.resenas.dao.IResenaDAO;
import com.apri.ms.resenas.dao.factory.DAOFactory;
import com.apri.ms.resenas.dto.DtoResenaCrear;
import com.apri.ms.resenas.dto.DtoResenaRespuesta;
import com.apri.ms.resenas.modelo.Resena;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar reseñas
 */
public class ResenaServicio {
    
    private final IResenaDAO resenaDAO;
    
    public ResenaServicio() {
        this.resenaDAO = DAOFactory.getInstance().getResenaDAO();
    }
    
    /**
     * Crea una nueva reseña
     */
    public DtoResenaRespuesta crearResena(DtoResenaCrear dto) throws SQLException {
        // Validaciones
        if (dto.getCantidadEstrellas() < 1 || dto.getCantidadEstrellas() > 5) {
            throw new IllegalArgumentException("La cantidad de estrellas debe estar entre 1 y 5");
        }
        
        Resena resena = new Resena();
        resena.setComentario(dto.getComentario());
        resena.setCantidadEstrellas(dto.getCantidadEstrellas());
        resena.setIdUsuario(dto.getIdUsuario());
        resena.setIdMaterialEducativo(dto.getIdMaterialEducativo());
        
        Resena resenaCreada = resenaDAO.crear(resena);
        return convertirADto(resenaCreada);
    }
    
    /**
     * Obtiene una reseña por ID
     */
    public DtoResenaRespuesta obtenerPorId(Integer id) throws SQLException {
        Resena resena = resenaDAO.obtenerPorId(id);
        return resena != null ? convertirADto(resena) : null;
    }
    
    /**
     * Obtiene todas las reseñas de un material educativo
     */
    public List<DtoResenaRespuesta> obtenerPorMaterial(Integer idMaterial) throws SQLException {
        List<Resena> resenas = resenaDAO.obtenerPorMaterial(idMaterial);
        return convertirListaADto(resenas);
    }
    
    /**
     * Obtiene todas las reseñas de un usuario
     */
    public List<DtoResenaRespuesta> obtenerPorUsuario(Integer idUsuario) throws SQLException {
        List<Resena> resenas = resenaDAO.obtenerPorUsuario(idUsuario);
        return convertirListaADto(resenas);
    }
    
    /**
     * Obtiene todas las reseñas
     */
    public List<DtoResenaRespuesta> obtenerTodas() throws SQLException {
        List<Resena> resenas = resenaDAO.obtenerTodas();
        return convertirListaADto(resenas);
    }
    
    /**
     * Obtiene todas las reseñas con una cantidad específica de estrellas
     */
    public List<DtoResenaRespuesta> obtenerPorEstrellas(Integer cantidadEstrellas) throws SQLException {
        if (cantidadEstrellas < 1 || cantidadEstrellas > 5) {
            throw new IllegalArgumentException("La cantidad de estrellas debe estar entre 1 y 5");
        }
        List<Resena> resenas = resenaDAO.obtenerPorEstrellas(cantidadEstrellas);
        return convertirListaADto(resenas);
    }
    
    /**
     * Actualiza una reseña
     */
    public boolean actualizarResena(Integer id, String comentario, Integer estrellas) throws SQLException {
        if (estrellas < 1 || estrellas > 5) {
            throw new IllegalArgumentException("La cantidad de estrellas debe estar entre 1 y 5");
        }
        
        Resena resena = resenaDAO.obtenerPorId(id);
        if (resena == null) {
            return false;
        }
        
        resena.setComentario(comentario);
        resena.setCantidadEstrellas(estrellas);
        
        return resenaDAO.actualizar(resena);
    }
    
    /**
     * Elimina una reseña
     */
    public boolean eliminarResena(Integer id) throws SQLException {
        return resenaDAO.eliminar(id);
    }
    
    /**
     * Obtiene el promedio de estrellas de un material
     */
    public Double obtenerPromedioEstrellas(Integer idMaterial) throws SQLException {
        return resenaDAO.obtenerPromedioEstrellas(idMaterial);
    }
    
    // Métodos auxiliares
    private DtoResenaRespuesta convertirADto(Resena resena) {
        return new DtoResenaRespuesta(
            resena.getIdResena(),
            resena.getComentario(),
            resena.getCantidadEstrellas(),
            resena.getIdUsuario(),
            resena.getIdMaterialEducativo()
        );
    }
    
    private List<DtoResenaRespuesta> convertirListaADto(List<Resena> resenas) {
        List<DtoResenaRespuesta> dtos = new ArrayList<>();
        for (Resena resena : resenas) {
            dtos.add(convertirADto(resena));
        }
        return dtos;
    }
}
