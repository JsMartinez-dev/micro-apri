package com.apri.ms.resenas.presentacion;

import com.apri.ms.resenas.dto.DtoReporteCrear;
import com.apri.ms.resenas.dto.DtoReporteRespuesta;
import com.apri.ms.resenas.dto.DtoRespuestaReporteCrear;
import com.apri.ms.resenas.modelo.Reporte;
import com.apri.ms.resenas.modelo.RespuestaReporte;
import com.apri.ms.resenas.servicio.ReporteServicio;
import com.apri.ms.resenas.servicio.RespuestaReporteServicio;
import com.apri.ms.resenas.utilidad.GsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet para gestionar reportes y respuestas a reportes
 */
@WebServlet(name = "ReporteServlet", urlPatterns = {"/reporte/*"})
public class ReporteServlet extends HttpServlet {
    
    private ReporteServicio reporteServicio;
    private RespuestaReporteServicio respuestaServicio;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        reporteServicio = new ReporteServicio();
        respuestaServicio = new RespuestaReporteServicio();
        gson = GsonUtil.getGson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        try {
            // Verificar query parameters primero
            String idReporteParam = request.getParameter("idReporte");
            String idResenaParam = request.getParameter("idResena");
            String estadoParam = request.getParameter("estado");
            String idUsuarioParam = request.getParameter("idUsuario");
            
            // Si hay query parameters, procesarlos primero
            if (idReporteParam != null) {
                // GET /reporte?idReporte=X - Obtener reporte por ID
                obtenerPorId(Integer.parseInt(idReporteParam), response);
                return;
            } else if (idResenaParam != null) {
                // GET /reporte?idResena=X - Obtener reportes por reseña
                obtenerPorResena(Integer.parseInt(idResenaParam), response);
                return;
            } else if (estadoParam != null) {
                // GET /reporte?estado=X - Obtener reportes por estado
                obtenerPorEstado(estadoParam, response);
                return;
            } else if (idUsuarioParam != null) {
                // GET /reporte?idUsuario=X - Obtener reportes por usuario
                obtenerPorUsuario(Integer.parseInt(idUsuarioParam), response);
                return;
            }
            
            // Si no hay query parameters, procesar pathInfo
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /reporte - Obtener todos los reportes
                obtenerTodos(response);
            } else if (pathInfo.startsWith("/resena/")) {
                // GET /reporte/resena/{id} - Obtener reportes de una reseña
                String idResena = pathInfo.substring(8);
                obtenerPorResena(Integer.parseInt(idResena), response);
            } else if (pathInfo.startsWith("/respuesta/")) {
                // GET /reporte/respuesta/{idReporte} - Obtener respuesta de un reporte
                String idReporte = pathInfo.substring(11);
                obtenerRespuesta(Integer.parseInt(idReporte), response);
            } else {
                // GET /reporte/{id} - Obtener reporte por ID
                String id = pathInfo.substring(1);
                obtenerPorId(Integer.parseInt(id), response);
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error de base de datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        // Debug: log del pathInfo
        System.out.println("POST pathInfo: '" + pathInfo + "'");
        
        BufferedReader reader = request.getReader();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // POST /reporte - Crear nuevo reporte
                DtoReporteCrear dto = gson.fromJson(reader, DtoReporteCrear.class);
                DtoReporteRespuesta reporteCreado = reporteServicio.crearReporte(dto);
                
                response.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(reporteCreado));
                out.flush();
                
            } else if (pathInfo.equals("/respuesta")) {
                // POST /reporte/respuesta - Crear respuesta a reporte
                System.out.println("Entrando a crear respuesta");
                DtoRespuestaReporteCrear dto = gson.fromJson(reader, DtoRespuestaReporteCrear.class);
                System.out.println("DTO parseado: " + dto.getIdReporte());
                RespuestaReporte respuestaCreada = respuestaServicio.crearRespuesta(dto);
                
                response.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(respuestaCreada));
                out.flush();
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida: " + pathInfo);
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al crear: " + e.getMessage());
        } catch (Exception e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error inesperado: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        BufferedReader reader = request.getReader();
        
        try {
            if (pathInfo != null && !pathInfo.equals("/")) {
                // PUT /reporte/{id} - Actualizar estado del reporte
                String id = pathInfo.substring(1);
                
                // Leer el JSON del body
                com.google.gson.JsonObject jsonObject = gson.fromJson(reader, com.google.gson.JsonObject.class);
                String nuevoEstado = jsonObject.get("estado").getAsString();
                
                // Obtener el reporte existente
                Reporte reporte = reporteServicio.obtenerPorId(Integer.parseInt(id));
                if (reporte != null) {
                    System.out.println("Reporte antes de actualizar - ID: " + reporte.getIdReporte() + ", Estado actual: " + reporte.getEstado());
                    reporte.setEstado(nuevoEstado);
                    System.out.println("Nuevo estado: " + nuevoEstado);
                    boolean actualizado = reporteServicio.actualizarReporte(reporte);
                    System.out.println("¿Se actualizó?: " + actualizado);
                    
                    if (actualizado) {
                        // Obtener el reporte actualizado para devolverlo
                        Reporte reporteActualizado = reporteServicio.obtenerPorId(Integer.parseInt(id));
                        System.out.println("Estado después de actualizar: " + reporteActualizado.getEstado());
                        
                        PrintWriter out = response.getWriter();
                        out.print(gson.toJson(reporteActualizado));
                        out.flush();
                    } else {
                        enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                                   "Error al actualizar el reporte");
                    }
                } else {
                    enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reporte no encontrado");
                }
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al actualizar reporte: " + e.getMessage());
        } catch (NumberFormatException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo != null && !pathInfo.equals("/")) {
                // DELETE /reporte/{id} - Eliminar reporte
                String id = pathInfo.substring(1);
                boolean eliminado = reporteServicio.eliminarReporte(Integer.parseInt(id));
                
                if (eliminado) {
                    enviarExito(response, "Reporte eliminado correctamente");
                } else {
                    enviarError(response, HttpServletResponse.SC_NOT_FOUND, 
                               "Reporte no encontrado");
                }
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al eliminar reporte: " + e.getMessage());
        } catch (NumberFormatException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
    
    // Métodos auxiliares
    private void obtenerTodos(HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerTodos();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void obtenerPorId(Integer id, HttpServletResponse response) 
            throws IOException, SQLException {
        Reporte reporte = reporteServicio.obtenerPorId(id);
        if (reporte != null) {
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(reporte));
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reporte no encontrado");
        }
    }
    
    private void obtenerPorResena(Integer idResena, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerPorResena(idResena);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void obtenerPorEstado(String estado, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerPorEstado(estado);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void obtenerPorUsuario(Integer idUsuario, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoReporteRespuesta> reportes = reporteServicio.obtenerPorUsuario(idUsuario);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(reportes));
        out.flush();
    }
    
    private void obtenerRespuesta(Integer idReporte, HttpServletResponse response) 
            throws IOException, SQLException {
        RespuestaReporte respuesta = respuestaServicio.obtenerPorReporte(idReporte);
        if (respuesta != null) {
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(respuesta));
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, 
                       "No hay respuesta para este reporte");
        }
    }
    
    private void enviarError(HttpServletResponse response, int codigo, String mensaje) 
            throws IOException {
        response.setStatus(codigo);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new ErrorResponse(mensaje)));
        out.flush();
    }
    
    private void enviarExito(HttpServletResponse response, String mensaje) 
            throws IOException {
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new SuccessResponse(mensaje)));
        out.flush();
    }
    
    // Clases auxiliares
    private static class ErrorResponse {
        private final String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
        
        public String getError() {
            return error;
        }
    }
    
    private static class SuccessResponse {
        private final String mensaje;
        
        public SuccessResponse(String mensaje) {
            this.mensaje = mensaje;
        }
        
        public String getMensaje() {
            return mensaje;
        }
    }
}
