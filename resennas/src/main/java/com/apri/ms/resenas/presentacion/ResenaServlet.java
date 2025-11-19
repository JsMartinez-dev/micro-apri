package com.apri.ms.resenas.presentacion;

import com.apri.ms.resenas.dto.DtoResenaCrear;
import com.apri.ms.resenas.dto.DtoResenaRespuesta;
import com.apri.ms.resenas.servicio.ResenaServicio;
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
 * Servlet para gestionar reseñas
 * Endpoints REST para el CRUD de reseñas
 */
@WebServlet(name = "ResenaServlet", urlPatterns = {"/api/resena/*"})
public class ResenaServlet extends HttpServlet {
    
    private ResenaServicio resenaServicio;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        resenaServicio = new ResenaServicio();
        gson = GsonUtil.getGson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        try {
            // Verificar primero si hay query parameters
            String idResenaParam = request.getParameter("idResena");
            String idMaterialParam = request.getParameter("idMaterial");
            String idUsuarioParam = request.getParameter("idUsuario");
            String estrellasParam = request.getParameter("cantidadEstrellas");
            
            // Si hay query parameters, procesarlos y retornar
            if (idResenaParam != null) {
                // GET /resena?idResena=X - Obtener reseña por ID
                obtenerPorId(Integer.parseInt(idResenaParam), response);
                return;
            } else if (idMaterialParam != null) {
                // GET /resena?idMaterial=X - Obtener reseñas por material
                obtenerPorMaterial(Integer.parseInt(idMaterialParam), response);
                return;
            } else if (idUsuarioParam != null) {
                // GET /resena?idUsuario=X - Obtener reseñas por usuario
                obtenerPorUsuario(Integer.parseInt(idUsuarioParam), response);
                return;
            } else if (estrellasParam != null) {
                // GET /resena?cantidadEstrellas=X - Obtener reseñas por estrellas
                obtenerPorEstrellas(Integer.parseInt(estrellasParam), response);
                return;
            }
            
            // Si no hay query parameters, procesar pathInfo
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /resena - Obtener todas las reseñas
                obtenerTodas(request, response);
            } else if (pathInfo.startsWith("/material/")) {
                // GET /resena/material/{id} - Obtener reseñas por material
                String idMaterial = pathInfo.substring(10);
                obtenerPorMaterial(Integer.parseInt(idMaterial), response);
            } else if (pathInfo.startsWith("/usuario/")) {
                // GET /resena/usuario/{id} - Obtener reseñas por usuario
                String idUsuario = pathInfo.substring(9);
                obtenerPorUsuario(Integer.parseInt(idUsuario), response);
            } else if (pathInfo.startsWith("/promedio/")) {
                // GET /resena/promedio/{idMaterial} - Obtener promedio de estrellas
                String idMaterial = pathInfo.substring(10);
                obtenerPromedio(Integer.parseInt(idMaterial), response);
            } else {
                // GET /resena/{id} - Obtener reseña por ID
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
        
        try {
            // POST /resena - Crear nueva reseña
            BufferedReader reader = request.getReader();
            DtoResenaCrear dto = gson.fromJson(reader, DtoResenaCrear.class);
            
            DtoResenaRespuesta resenaCreada = resenaServicio.crearResena(dto);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(resenaCreada));
            out.flush();
            
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al crear reseña: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo != null && !pathInfo.equals("/")) {
                // PUT /resena/{id} - Actualizar reseña
                String id = pathInfo.substring(1);
                BufferedReader reader = request.getReader();
                DtoResenaCrear dto = gson.fromJson(reader, DtoResenaCrear.class);
                
                boolean actualizado = resenaServicio.actualizarResena(
                    Integer.parseInt(id), 
                    dto.getComentario(), 
                    dto.getCantidadEstrellas()
                );
                
                if (actualizado) {
                    enviarExito(response, "Reseña actualizada correctamente");
                } else {
                    enviarError(response, HttpServletResponse.SC_NOT_FOUND, 
                               "Reseña no encontrada");
                }
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al actualizar reseña: " + e.getMessage());
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
                // DELETE /resena/{id} - Eliminar reseña
                String id = pathInfo.substring(1);
                boolean eliminado = resenaServicio.eliminarResena(Integer.parseInt(id));
                
                if (eliminado) {
                    enviarExito(response, "Reseña eliminada correctamente");
                } else {
                    enviarError(response, HttpServletResponse.SC_NOT_FOUND, 
                               "Reseña no encontrada");
                }
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al eliminar reseña: " + e.getMessage());
        } catch (NumberFormatException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
    
    // Métodos auxiliares
    private void obtenerTodas(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoResenaRespuesta> resenas = resenaServicio.obtenerTodas();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(resenas));
        out.flush();
    }
    
    private void obtenerPorId(Integer id, HttpServletResponse response) 
            throws IOException, SQLException {
        DtoResenaRespuesta resena = resenaServicio.obtenerPorId(id);
        if (resena != null) {
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(resena));
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reseña no encontrada");
        }
    }
    
    private void obtenerPorMaterial(Integer idMaterial, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoResenaRespuesta> resenas = resenaServicio.obtenerPorMaterial(idMaterial);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(resenas));
        out.flush();
    }
    
    private void obtenerPorUsuario(Integer idUsuario, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoResenaRespuesta> resenas = resenaServicio.obtenerPorUsuario(idUsuario);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(resenas));
        out.flush();
    }
    
    private void obtenerPorEstrellas(Integer cantidadEstrellas, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoResenaRespuesta> resenas = resenaServicio.obtenerPorEstrellas(cantidadEstrellas);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(resenas));
        out.flush();
    }
    
    private void obtenerPromedio(Integer idMaterial, HttpServletResponse response) 
            throws IOException, SQLException {
        Double promedio = resenaServicio.obtenerPromedioEstrellas(idMaterial);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new PromedioResponse(promedio)));
        out.flush();
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
    
    // Clases auxiliares para respuestas
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
    
    private static class PromedioResponse {
        private final Double promedio;
        
        public PromedioResponse(Double promedio) {
            this.promedio = promedio;
        }
        
        public Double getPromedio() {
            return promedio;
        }
    }
}
