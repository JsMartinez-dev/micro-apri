package com.apri.ms.resenas.dao.impl;

import com.apri.ms.resenas.dao.IReporteDAO;
import com.apri.ms.resenas.modelo.Reporte;
import com.apri.ms.resenas.utilidad.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de Reportes
 */
public class ReporteDAOImpl implements IReporteDAO {

    @Override
    public Reporte crear(Reporte reporte) throws SQLException {
        String sql = "INSERT INTO reportes (motivo, fecha_reporte, id_usuario, id_resena) " +
                    "VALUES (?, ?, ?, ?) RETURNING id_reporte, estado";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, reporte.getMotivo());
            stmt.setDate(2, Date.valueOf(reporte.getFechaReporte()));
            stmt.setInt(3, reporte.getIdUsuario());
            stmt.setInt(4, reporte.getIdResena());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reporte.setIdReporte(rs.getInt("id_reporte"));
                reporte.setEstado(rs.getString("estado"));
            }
            
            return reporte;
        }
    }

    @Override
    public Reporte obtenerPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM reportes WHERE id_reporte = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearReporte(rs);
            }
            
            return null;
        }
    }

    @Override
    public List<Reporte> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM reportes ORDER BY fecha_reporte DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Reporte> reportes = new ArrayList<>();
            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
            
            return reportes;
        }
    }

    @Override
    public List<Reporte> obtenerPorResena(Integer idResena) throws SQLException {
        String sql = "SELECT * FROM reportes WHERE id_resena = ? ORDER BY fecha_reporte DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idResena);
            ResultSet rs = stmt.executeQuery();
            
            List<Reporte> reportes = new ArrayList<>();
            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
            
            return reportes;
        }
    }

    @Override
    public List<Reporte> obtenerPorEstado(String estado) throws SQLException {
        String sql = "SELECT * FROM reportes WHERE estado = ? ORDER BY fecha_reporte DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            
            List<Reporte> reportes = new ArrayList<>();
            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
            
            return reportes;
        }
    }

    @Override
    public List<Reporte> obtenerPorUsuario(Integer idUsuario) throws SQLException {
        String sql = "SELECT * FROM reportes WHERE id_usuario = ? ORDER BY fecha_reporte DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            List<Reporte> reportes = new ArrayList<>();
            while (rs.next()) {
                reportes.add(mapearReporte(rs));
            }
            
            return reportes;
        }
    }

    @Override
    public boolean actualizar(Reporte reporte) throws SQLException {
        String sql = "UPDATE reportes SET motivo = ?, estado = ? WHERE id_reporte = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, reporte.getMotivo());
            stmt.setString(2, reporte.getEstado());
            stmt.setInt(3, reporte.getIdReporte());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(Integer id) throws SQLException {
        String sql = "DELETE FROM reportes WHERE id_reporte = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Reporte mapearReporte(ResultSet rs) throws SQLException {
        Reporte reporte = new Reporte();
        reporte.setIdReporte(rs.getInt("id_reporte"));
        reporte.setMotivo(rs.getString("motivo"));
        reporte.setEstado(rs.getString("estado"));
        reporte.setFechaReporte(rs.getDate("fecha_reporte").toLocalDate());
        reporte.setIdUsuario(rs.getInt("id_usuario"));
        reporte.setIdResena(rs.getInt("id_resena"));
        return reporte;
    }
}
