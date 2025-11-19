package com.apri.ms.resenas.dao;

import com.apri.ms.resenas.modelo.RespuestaReporte;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para RespuestaReporte
 */
public interface IRespuestaReporteDAO {
    RespuestaReporte crear(RespuestaReporte respuesta) throws SQLException;
    RespuestaReporte obtenerPorReporte(Integer idReporte) throws SQLException;
    List<RespuestaReporte> obtenerTodas() throws SQLException;
    boolean actualizar(RespuestaReporte respuesta) throws SQLException;
    boolean eliminar(Integer idReporte, Integer idAdministrador) throws SQLException;
}
