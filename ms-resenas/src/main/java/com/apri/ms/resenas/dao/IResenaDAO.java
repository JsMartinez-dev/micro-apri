package com.apri.ms.resenas.dao;

import com.apri.ms.resenas.modelo.Resena;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para Reseñas
 */
public interface IResenaDAO {
    Resena crear(Resena resena) throws SQLException;
    Resena obtenerPorId(Integer id) throws SQLException;
    List<Resena> obtenerPorMaterial(Integer idMaterial) throws SQLException;
    List<Resena> obtenerPorUsuario(Integer idUsuario) throws SQLException;
    List<Resena> obtenerPorEstrellas(Integer cantidadEstrellas) throws SQLException;
    List<Resena> obtenerTodas() throws SQLException;
    boolean actualizar(Resena resena) throws SQLException;
    boolean eliminar(Integer id) throws SQLException;
    Double obtenerPromedioEstrellas(Integer idMaterial) throws SQLException;
}
