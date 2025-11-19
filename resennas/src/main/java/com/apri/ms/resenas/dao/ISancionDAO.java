package com.apri.ms.resenas.dao;

import com.apri.ms.resenas.modelo.Sancion;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para Sanciones
 */
public interface ISancionDAO {
    Sancion crear(Sancion sancion) throws SQLException;
    Sancion obtenerPorId(Integer id) throws SQLException;
    List<Sancion> obtenerPorUsuario(Integer idUsuario) throws SQLException;
    List<Sancion> obtenerSancionesActivas(Integer idUsuario) throws SQLException;
    boolean estaBaneado(Integer idUsuario) throws SQLException;
    boolean estaSuspendido(Integer idUsuario) throws SQLException;
    int contarAdvertencias(Integer idUsuario) throws SQLException;
    boolean desactivarSancion(Integer idSancion) throws SQLException;
    void desactivarSancionesExpiradas() throws SQLException;
    List<Sancion> obtenerTodas() throws SQLException;
    List<Sancion> obtenerTodasActivas() throws SQLException;
}
