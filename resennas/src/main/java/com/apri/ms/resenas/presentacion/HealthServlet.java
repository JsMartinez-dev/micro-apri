package com.apri.ms.resenas.presentacion;

import com.apri.ms.resenas.utilidad.ConexionDB;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet para verificar el estado del microservicio
 */
@WebServlet(name = "HealthServlet", urlPatterns = {"/health"})
public class HealthServlet extends HttpServlet {
    
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        gson = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> health = new HashMap<>();
        health.put("service", "ms-resenas");
        health.put("version", "1.0.0");
        health.put("timestamp", System.currentTimeMillis());
        
        // Verificar conexión a la base de datos
        try (Connection conn = ConexionDB.getConnection()) {
            health.put("database", "Connected");
            health.put("status", "UP");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            health.put("database", "Disconnected: " + e.getMessage());
            health.put("status", "DOWN");
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(health));
        out.flush();
    }
}
