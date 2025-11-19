package com.apri.ms.resenas.presentacion;

import com.apri.ms.resenas.dto.DtoReporteCrear;
import com.apri.ms.resenas.dto.DtoReporteRespuesta;
import com.apri.ms.resenas.servicio.ReporteServicio;
import com.apri.ms.resenas.utilidad.GsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet de compatibilidad para reportes
 * Maneja peticiones form-urlencoded desde el frontend
 */
@WebServlet(name = "ReporteControlServlet", urlPatterns = {"/ReporteControl"})
public class ReporteControlServlet extends HttpServlet {
    
    private ReporteServicio reporteServicio;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        reporteServicio = new ReporteServicio();
        gson = GsonUtil.getGson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String accion = request.getParameter("accion");
        
        System.out.println("[ReporteControl] GET - Acción: " + accion);
        
        try {
            if (accion == null) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }
            
            switch (accion) {
                case "listarPorResena":
                    listarPorResena(request, response);
                    break;
                case "listarPorUsuario":
                    listarPorUsuario(request, response);
                    break;
                case "listarPorEstado":
                    listarPorEstado(request, response);
                    break;
                case "obtener":
                    obtenerPorId(request, response);
                    break;
                case "listarTodos":
                    listarTodos(request, response);
                    break;
                default:
                    enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
            }
        } catch (Exception e) {
            System.err.println("[ReporteControl] Error en GET: " + e.getMessage());
            e.printStackTrace();
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error del servidor: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String accion = request.getParameter("accion");
        
        System.out.println("[ReporteControl] POST - Acción: " + accion);
        
        try {
            if (accion == null) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }
            
            switch (accion) {
                case "crear":
                    crearReporte(request, response);
                    break;
                case "actualizar":
                    actualizarEstado(request, response);
                    break;
                case "eliminar":
                    eliminarReporte(request, response);
                    break;
                default:
                    enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
            }
        } catch (Exception e) {
            System.err.println("[ReporteControl] Error en POST: " + e.getMessage());
            e.printStackTrace();
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error del servidor: " + e.getMessage());
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    // Métodos de acción
    private void crearReporte(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String motivo = request.getParameter("motivo");
        String idUsuarioParam = request.getParameter("idUsuario");
        String idResenaParam = request.getParameter("idResena");
        
        System.out.println("[ReporteControl] Crear reporte:");
        System.out.println("  - motivo: " + motivo);
        System.out.println("  - idUsuario: " + idUsuarioParam);
        System.out.println("  - idResena: " + idResenaParam);
        
        if (motivo == null || idUsuarioParam == null || idResenaParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, 
                       "Faltan parámetros requeridos (motivo, idUsuario, idResena)");
            return;
        }
        
        try {
            int idUsuario = Integer.parseInt(idUsuarioParam);
            int idResena = Integer.parseInt(idResenaParam);
            
            DtoReporteCrear dto = new DtoReporteCrear(motivo, idUsuario, idResena);
            DtoReporteRespuesta reporteCreado = reporteServicio.crearReporte(dto);
            
            System.out.println("[ReporteControl] Reporte creado exitosamente: " + reporteCreado.getIdReporte());
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"success\":true,\"reporte\":" + gson.toJson(reporteCreado) + "}");
            out.flush();
            
        } catch (NumberFormatException e) {
            System.err.println("[ReporteControl] Error de formato de número: " + e.getMessage());
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "IDs deben ser números válidos");
        }
    }
    
    private void listarPorResena(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idResenaParam = request.getParameter("idResena");
        
        if (idResenaParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idResena no proporcionado");
            return;
        }
        
        int idResena = Integer.parseInt(idResenaParam);
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerPorResena(idResena);
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void listarPorUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idUsuarioParam = request.getParameter("idUsuario");
        
        if (idUsuarioParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idUsuario no proporcionado");
            return;
        }
        
        int idUsuario = Integer.parseInt(idUsuarioParam);
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerPorUsuario(idUsuario);
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void listarPorEstado(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String estado = request.getParameter("estado");
        
        if (estado == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "estado no proporcionado");
            return;
        }
        
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerPorEstado(estado);
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void obtenerPorId(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idReporteParam = request.getParameter("idReporte");
        
        if (idReporteParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idReporte no proporcionado");
            return;
        }
        
        int idReporte = Integer.parseInt(idReporteParam);
        DtoReporteRespuesta reporte = reporteServicio.obtenerPorIdDto(idReporte);
        
        if (reporte != null) {
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(reporte));
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reporte no encontrado");
        }
    }
    
    private void listarTodos(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerTodos();
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void actualizarEstado(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idReporteParam = request.getParameter("idReporte");
        String estado = request.getParameter("estado");
        
        if (idReporteParam == null || estado == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, 
                       "Faltan parámetros requeridos (idReporte, estado)");
            return;
        }
        
        int idReporte = Integer.parseInt(idReporteParam);
        boolean actualizado = reporteServicio.actualizarEstado(idReporte, estado);
        
        if (actualizado) {
            PrintWriter out = response.getWriter();
            out.print("{\"success\":true,\"mensaje\":\"Estado actualizado correctamente\"}");
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reporte no encontrado");
        }
    }
    
    private void eliminarReporte(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idReporteParam = request.getParameter("idReporte");
        
        if (idReporteParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idReporte no proporcionado");
            return;
        }
        
        int idReporte = Integer.parseInt(idReporteParam);
        boolean eliminado = reporteServicio.eliminarReporte(idReporte);
        
        if (eliminado) {
            PrintWriter out = response.getWriter();
            out.print("{\"success\":true,\"mensaje\":\"Reporte eliminado correctamente\"}");
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reporte no encontrado");
        }
    }
    
    private void enviarError(HttpServletResponse response, int codigo, String mensaje) 
            throws IOException {
        response.setStatus(codigo);
        PrintWriter out = response.getWriter();
        out.print("{\"success\":false,\"error\":\"" + mensaje + "\"}");
        out.flush();
    }
}
