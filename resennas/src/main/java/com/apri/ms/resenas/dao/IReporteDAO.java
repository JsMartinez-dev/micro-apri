package com.apri.ms.resenas.dao;

import com.apri.ms.resenas.modelo.Reporte;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para Reportes
 */
public interface IReporteDAO {
    Reporte crear(Reporte reporte) throws SQLException;
    Reporte obtenerPorId(Integer id) throws SQLException;
    List<Reporte> obtenerTodos() throws SQLException;
    List<Reporte> obtenerPorResena(Integer idResena) throws SQLException;
    List<Reporte> obtenerPorEstado(String estado) throws SQLException;
    List<Reporte> obtenerPorUsuario(Integer idUsuario) throws SQLException;
    boolean actualizar(Reporte reporte) throws SQLException;
    boolean eliminar(Integer id) throws SQLException;
}
