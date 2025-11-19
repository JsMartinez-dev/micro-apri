package com.apri.ms.resenas.presentacion;

import com.apri.ms.resenas.dto.DtoEstadoUsuario;
import com.apri.ms.resenas.dto.DtoSancionCrear;
import com.apri.ms.resenas.dto.DtoSancionRespuesta;
import com.apri.ms.resenas.servicio.SancionServicio;
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
 * Servlet para gestionar sanciones de usuarios
 */
@WebServlet(name = "SancionServlet", urlPatterns = {"/sancion/*"})
public class SancionServlet extends HttpServlet {
    
    private SancionServicio sancionServicio;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        sancionServicio = new SancionServicio();
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
            String idUsuarioParam = request.getParameter("idUsuario");
            
            if (pathInfo != null) {
                if (pathInfo.equals("/verificar-acceso") && idUsuarioParam != null) {
                    // GET /sancion/verificar-acceso?idUsuario=X - Verificar estado del usuario
                    verificarAcceso(Integer.parseInt(idUsuarioParam), response);
                    return;
                } else if (pathInfo.equals("/activas")) {
                    if (idUsuarioParam != null) {
                        // GET /sancion/activas?idUsuario=X - Obtener sanciones activas de un usuario
                        obtenerSancionesActivas(Integer.parseInt(idUsuarioParam), response);
                    } else {
                        // GET /sancion/activas - Obtener todas las sanciones activas
                        obtenerTodasActivas(response);
                    }
                    return;
                }
            }
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /sancion?idUsuario=X - Obtener historial de sanciones
                if (idUsuarioParam != null) {
                    obtenerHistorialPorUsuario(Integer.parseInt(idUsuarioParam), response);
                    return;
                } else {
                    // GET /sancion - Obtener todas las sanciones
                    obtenerTodas(response);
                }
            } else if (pathInfo.startsWith("/activas/")) {
                // GET /sancion/activas/{idUsuario}
                String idUsuario = pathInfo.substring(9);
                obtenerSancionesActivas(Integer.parseInt(idUsuario), response);
            } else if (pathInfo.startsWith("/verificar/")) {
                // GET /sancion/verificar/{idUsuario} - Verificar estado
                String idUsuario = pathInfo.substring(11);
                verificarAcceso(Integer.parseInt(idUsuario), response);
            } else {
                // GET /sancion/{id}
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
        
        try {
            BufferedReader reader = request.getReader();
            DtoSancionCrear dto = gson.fromJson(reader, DtoSancionCrear.class);
            
            if (pathInfo == null || pathInfo.equals("/advertir")) {
                // POST /sancion/advertir - Crear advertencia
                DtoSancionRespuesta sancion = sancionServicio.crearAdvertencia(dto);
                response.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(sancion));
                out.flush();
                
            } else if (pathInfo.equals("/suspender")) {
                // POST /sancion/suspender - Suspender usuario
                DtoSancionRespuesta sancion = sancionServicio.suspenderUsuario(dto);
                response.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(sancion));
                out.flush();
                
            } else if (pathInfo.equals("/banear")) {
                // POST /sancion/banear - Banear usuario
                DtoSancionRespuesta sancion = sancionServicio.banearUsuario(dto);
                response.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(sancion));
                out.flush();
                
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al crear sanción: " + e.getMessage());
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
            if (pathInfo != null) {
                if (pathInfo.endsWith("/levantar")) {
                    // PUT /sancion/{id}/levantar
                    String id = pathInfo.substring(1, pathInfo.indexOf("/levantar"));
                    boolean levantada = sancionServicio.levantarSancion(Integer.parseInt(id));
                    
                    if (levantada) {
                        enviarExito(response, "Sanción levantada correctamente");
                    } else {
                        enviarError(response, HttpServletResponse.SC_NOT_FOUND, 
                                   "Sanción no encontrada");
                    }
                } else if (pathInfo.startsWith("/levantar/")) {
                    // PUT /sancion/levantar/{id}
                    String id = pathInfo.substring(10);
                    boolean levantada = sancionServicio.levantarSancion(Integer.parseInt(id));
                    
                    if (levantada) {
                        enviarExito(response, "Sanción levantada correctamente");
                    } else {
                        enviarError(response, HttpServletResponse.SC_NOT_FOUND, 
                                   "Sanción no encontrada");
                    }
                } else {
                    enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
                }
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
            }
        } catch (SQLException e) {
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                       "Error al levantar sanción: " + e.getMessage());
        } catch (NumberFormatException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
    
    // Métodos auxiliares
    private void obtenerTodas(HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoSancionRespuesta> sanciones = sancionServicio.obtenerTodasLasSanciones();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(sanciones));
        out.flush();
    }
    
    private void obtenerPorId(Integer id, HttpServletResponse response) 
            throws IOException, SQLException {
        DtoSancionRespuesta sancion = sancionServicio.obtenerPorId(id);
        if (sancion != null) {
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(sancion));
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Sanción no encontrada");
        }
    }
    
    private void obtenerHistorialPorUsuario(Integer idUsuario, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoSancionRespuesta> sanciones = sancionServicio.obtenerHistorialSanciones(idUsuario);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(sanciones));
        out.flush();
    }
    
    private void obtenerSancionesActivas(Integer idUsuario, HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoSancionRespuesta> sanciones = sancionServicio.obtenerSancionesActivas(idUsuario);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(sanciones));
        out.flush();
    }
    
    private void obtenerTodasActivas(HttpServletResponse response) 
            throws IOException, SQLException {
        List<DtoSancionRespuesta> sanciones = sancionServicio.obtenerTodasSancionesActivas();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(sanciones));
        out.flush();
    }
    
    private void verificarAcceso(Integer idUsuario, HttpServletResponse response) 
            throws IOException, SQLException {
        DtoEstadoUsuario estado = sancionServicio.verificarAccesoUsuario(idUsuario);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(estado));
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
