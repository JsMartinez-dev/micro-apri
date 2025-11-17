package com.apri.ms.resenas.dao.impl;

import com.apri.ms.resenas.dao.ISancionDAO;
import com.apri.ms.resenas.modelo.Sancion;
import com.apri.ms.resenas.utilidad.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de Sanciones
 */
public class SancionDAOImpl implements ISancionDAO {

    @Override
    public Sancion crear(Sancion sancion) throws SQLException {
        String sql = "INSERT INTO sanciones (id_usuario, tipo_sancion, motivo, fecha_inicio, " +
                    "fecha_fin, activa, id_administrador, id_reporte) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id_sancion";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sancion.getIdUsuario());
            stmt.setString(2, sancion.getTipoSancion());
            stmt.setString(3, sancion.getMotivo());
            stmt.setTimestamp(4, Timestamp.valueOf(sancion.getFechaInicio()));
            
            if (sancion.getFechaFin() != null) {
                stmt.setTimestamp(5, Timestamp.valueOf(sancion.getFechaFin()));
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }
            
            stmt.setBoolean(6, sancion.getActiva());
            stmt.setInt(7, sancion.getIdAdministrador());
            
            if (sancion.getIdReporte() != null) {
                stmt.setInt(8, sancion.getIdReporte());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sancion.setIdSancion(rs.getInt("id_sancion"));
            }
            
            return sancion;
        }
    }

    @Override
    public Sancion obtenerPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM sanciones WHERE id_sancion = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearSancion(rs);
            }
            return null;
        }
    }

    @Override
    public List<Sancion> obtenerPorUsuario(Integer idUsuario) throws SQLException {
        String sql = "SELECT * FROM sanciones WHERE id_usuario = ? ORDER BY fecha_inicio DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            List<Sancion> sanciones = new ArrayList<>();
            while (rs.next()) {
                sanciones.add(mapearSancion(rs));
            }
            return sanciones;
        }
    }

    @Override
    public List<Sancion> obtenerSancionesActivas(Integer idUsuario) throws SQLException {
        String sql = "SELECT * FROM sanciones WHERE id_usuario = ? AND activa = true " +
                    "ORDER BY fecha_inicio DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            List<Sancion> sanciones = new ArrayList<>();
            while (rs.next()) {
                sanciones.add(mapearSancion(rs));
            }
            return sanciones;
        }
    }

    @Override
    public boolean estaBaneado(Integer idUsuario) throws SQLException {
        String sql = "SELECT esta_baneado FROM v_estado_sanciones_usuarios WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("esta_baneado") == 1;
            }
            return false;
        }
    }

    @Override
    public boolean estaSuspendido(Integer idUsuario) throws SQLException {
        String sql = "SELECT esta_suspendido FROM v_estado_sanciones_usuarios WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("esta_suspendido") == 1;
            }
            return false;
        }
    }

    @Override
    public int contarAdvertencias(Integer idUsuario) throws SQLException {
        String sql = "SELECT total_advertencias FROM v_estado_sanciones_usuarios WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total_advertencias");
            }
            return 0;
        }
    }

    @Override
    public boolean desactivarSancion(Integer idSancion) throws SQLException {
        String sql = "UPDATE sanciones SET activa = false WHERE id_sancion = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idSancion);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public void desactivarSancionesExpiradas() throws SQLException {
        String sql = "SELECT desactivar_sanciones_expiradas()";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public List<Sancion> obtenerTodas() throws SQLException {
        String sql = "SELECT * FROM sanciones ORDER BY fecha_inicio DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Sancion> sanciones = new ArrayList<>();
            while (rs.next()) {
                sanciones.add(mapearSancion(rs));
            }
            return sanciones;
        }
    }

    @Override
    public List<Sancion> obtenerTodasActivas() throws SQLException {
        String sql = "SELECT * FROM sanciones WHERE activa = true ORDER BY fecha_inicio DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Sancion> sanciones = new ArrayList<>();
            while (rs.next()) {
                sanciones.add(mapearSancion(rs));
            }
            return sanciones;
        }
    }

    private Sancion mapearSancion(ResultSet rs) throws SQLException {
        Sancion sancion = new Sancion();
        sancion.setIdSancion(rs.getInt("id_sancion"));
        sancion.setIdUsuario(rs.getInt("id_usuario"));
        sancion.setTipoSancion(rs.getString("tipo_sancion"));
        sancion.setMotivo(rs.getString("motivo"));
        
        Timestamp fechaInicio = rs.getTimestamp("fecha_inicio");
        if (fechaInicio != null) {
            sancion.setFechaInicio(fechaInicio.toLocalDateTime());
        }
        
        Timestamp fechaFin = rs.getTimestamp("fecha_fin");
        if (fechaFin != null) {
            sancion.setFechaFin(fechaFin.toLocalDateTime());
        }
        
        sancion.setActiva(rs.getBoolean("activa"));
        sancion.setIdAdministrador(rs.getInt("id_administrador"));
        
        int idReporte = rs.getInt("id_reporte");
        if (!rs.wasNull()) {
            sancion.setIdReporte(idReporte);
        }
        
        return sancion;
    }
}
