package com.apri.ms.resenas.dao.impl;

import com.apri.ms.resenas.dao.IResenaDAO;
import com.apri.ms.resenas.modelo.Resena;
import com.apri.ms.resenas.utilidad.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de Reseñas
 */
public class ResenaDAOImpl implements IResenaDAO {

    @Override
    public Resena crear(Resena resena) throws SQLException {
        String sql = "INSERT INTO resenas (comentario, cantidad_estrellas, id_usuario, id_material_educativo) " +
                    "VALUES (?, ?, ?, ?) RETURNING id_resenas";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, resena.getComentario());
            stmt.setInt(2, resena.getCantidadEstrellas());
            stmt.setInt(3, resena.getIdUsuario());
            stmt.setInt(4, resena.getIdMaterialEducativo());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resena.setIdResena(rs.getInt("id_resenas"));
            }
            
            return resena;
        }
    }

    @Override
    public Resena obtenerPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM resenas WHERE id_resenas = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearResena(rs);
            }
            
            return null;
        }
    }

    @Override
    public List<Resena> obtenerPorMaterial(Integer idMaterial) throws SQLException {
        String sql = "SELECT * FROM resenas WHERE id_material_educativo = ? ORDER BY id_resenas DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idMaterial);
            ResultSet rs = stmt.executeQuery();
            
            List<Resena> resenas = new ArrayList<>();
            while (rs.next()) {
                resenas.add(mapearResena(rs));
            }
            
            return resenas;
        }
    }

    @Override
    public List<Resena> obtenerPorUsuario(Integer idUsuario) throws SQLException {
        String sql = "SELECT * FROM resenas WHERE id_usuario = ? ORDER BY id_resenas DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            List<Resena> resenas = new ArrayList<>();
            while (rs.next()) {
                resenas.add(mapearResena(rs));
            }
            
            return resenas;
        }
    }

    @Override
    public List<Resena> obtenerPorEstrellas(Integer cantidadEstrellas) throws SQLException {
        String sql = "SELECT * FROM resenas WHERE cantidad_estrellas = ? ORDER BY id_resenas DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cantidadEstrellas);
            ResultSet rs = stmt.executeQuery();
            
            List<Resena> resenas = new ArrayList<>();
            while (rs.next()) {
                resenas.add(mapearResena(rs));
            }
            
            return resenas;
        }
    }

    @Override
    public List<Resena> obtenerTodas() throws SQLException {
        String sql = "SELECT * FROM resenas ORDER BY id_resenas DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Resena> resenas = new ArrayList<>();
            while (rs.next()) {
                resenas.add(mapearResena(rs));
            }
            
            return resenas;
        }
    }

    @Override
    public boolean actualizar(Resena resena) throws SQLException {
        String sql = "UPDATE resenas SET comentario = ?, cantidad_estrellas = ? " +
                    "WHERE id_resenas = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, resena.getComentario());
            stmt.setInt(2, resena.getCantidadEstrellas());
            stmt.setInt(3, resena.getIdResena());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(Integer id) throws SQLException {
        String sql = "DELETE FROM resenas WHERE id_resenas = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Double obtenerPromedioEstrellas(Integer idMaterial) throws SQLException {
        String sql = "SELECT AVG(cantidad_estrellas) as promedio FROM resenas " +
                    "WHERE id_material_educativo = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idMaterial);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("promedio");
            }
            
            return 0.0;
        }
    }

    private Resena mapearResena(ResultSet rs) throws SQLException {
        Resena resena = new Resena();
        resena.setIdResena(rs.getInt("id_resenas"));
        resena.setComentario(rs.getString("comentario"));
        resena.setCantidadEstrellas(rs.getInt("cantidad_estrellas"));
        resena.setIdUsuario(rs.getInt("id_usuario"));
        resena.setIdMaterialEducativo(rs.getInt("id_material_educativo"));
        return resena;
    }
}
