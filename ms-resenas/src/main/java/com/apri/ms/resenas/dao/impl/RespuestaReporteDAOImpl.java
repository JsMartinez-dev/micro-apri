package com.apri.ms.resenas.dao.impl;

import com.apri.ms.resenas.dao.IRespuestaReporteDAO;
import com.apri.ms.resenas.modelo.RespuestaReporte;
import com.apri.ms.resenas.utilidad.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de RespuestaReporte
 */
public class RespuestaReporteDAOImpl implements IRespuestaReporteDAO {

    @Override
    public RespuestaReporte crear(RespuestaReporte respuesta) throws SQLException {
        String sql = "INSERT INTO respuestas_reporte (id_reporte, id_administrador, accion, respuesta, fecha_solucion) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, respuesta.getIdReporte());
            stmt.setInt(2, respuesta.getIdAdministrador());
            stmt.setString(3, respuesta.getAccion());
            stmt.setString(4, respuesta.getRespuesta());
            stmt.setDate(5, Date.valueOf(respuesta.getFechaSolucion()));
            
            stmt.executeUpdate();
            return respuesta;
        }
    }

    @Override
    public RespuestaReporte obtenerPorReporte(Integer idReporte) throws SQLException {
        String sql = "SELECT * FROM respuestas_reporte WHERE id_reporte = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idReporte);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearRespuesta(rs);
            }
            
            return null;
        }
    }

    @Override
    public List<RespuestaReporte> obtenerTodas() throws SQLException {
        String sql = "SELECT * FROM respuestas_reporte ORDER BY fecha_solucion DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<RespuestaReporte> respuestas = new ArrayList<>();
            while (rs.next()) {
                respuestas.add(mapearRespuesta(rs));
            }
            
            return respuestas;
        }
    }

    @Override
    public boolean actualizar(RespuestaReporte respuesta) throws SQLException {
        String sql = "UPDATE respuestas_reporte SET accion = ?, respuesta = ?, fecha_solucion = ? " +
                    "WHERE id_reporte = ? AND id_administrador = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, respuesta.getAccion());
            stmt.setString(2, respuesta.getRespuesta());
            stmt.setDate(3, Date.valueOf(respuesta.getFechaSolucion()));
            stmt.setInt(4, respuesta.getIdReporte());
            stmt.setInt(5, respuesta.getIdAdministrador());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(Integer idReporte, Integer idAdministrador) throws SQLException {
        String sql = "DELETE FROM respuestas_reporte WHERE id_reporte = ? AND id_administrador = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idReporte);
            stmt.setInt(2, idAdministrador);
            return stmt.executeUpdate() > 0;
        }
    }

    private RespuestaReporte mapearRespuesta(ResultSet rs) throws SQLException {
        RespuestaReporte respuesta = new RespuestaReporte();
        respuesta.setIdReporte(rs.getInt("id_reporte"));
        respuesta.setIdAdministrador(rs.getInt("id_administrador"));
        respuesta.setAccion(rs.getString("accion"));
        respuesta.setRespuesta(rs.getString("respuesta"));
        respuesta.setFechaSolucion(rs.getDate("fecha_solucion").toLocalDate());
        return respuesta;
    }
}
