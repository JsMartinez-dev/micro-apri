package com.apri.ms.resenas.controlador;

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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet de compatibilidad con ms-web
 * Maneja peticiones form-urlencoded desde el frontend
 */
@WebServlet(name = "ResenaControlServlet", urlPatterns = {"/ResenaControl"})
public class ResenaControlServlet extends HttpServlet {
    
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
        
        // Configurar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String accion = request.getParameter("accion");
        
        System.out.println("[ResenaControl] GET - Acción: " + accion);
        
        try {
            if (accion == null) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }
            
            switch (accion) {
                case "listarPorMaterial":
                    listarPorMaterial(request, response);
                    break;
                case "listarPorUsuario":
                    listarPorUsuario(request, response);
                    break;
                case "obtenerPromedio":
                    obtenerPromedio(request, response);
                    break;
                case "obtener":
                    obtenerPorId(request, response);
                    break;
                default:
                    enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
            }
        } catch (Exception e) {
            System.err.println("[ResenaControl] Error en GET: " + e.getMessage());
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
        
        System.out.println("[ResenaControl] POST - Acción: " + accion);
        
        try {
            if (accion == null) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
                return;
            }
            
            switch (accion) {
                case "crear":
                    crearResena(request, response);
                    break;
                case "actualizar":
                    actualizarResena(request, response);
                    break;
                case "eliminar":
                    eliminarResena(request, response);
                    break;
                default:
                    enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
            }
        } catch (Exception e) {
            System.err.println("[ResenaControl] Error en POST: " + e.getMessage());
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
    private void listarPorMaterial(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idMaterialParam = request.getParameter("idMaterial");
        
        System.out.println("[ResenaControl] Listar por material: " + idMaterialParam);
        
        if (idMaterialParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idMaterial no proporcionado");
            return;
        }
        
        int idMaterial = Integer.parseInt(idMaterialParam);
        List<DtoResenaRespuesta> resenas = resenaServicio.obtenerPorMaterial(idMaterial);
        
        System.out.println("[ResenaControl] Reseñas encontradas: " + resenas.size());
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(resenas));
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
        List<DtoResenaRespuesta> resenas = resenaServicio.obtenerPorUsuario(idUsuario);
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(resenas));
        out.flush();
    }
    
    private void obtenerPromedio(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idMaterialParam = request.getParameter("idMaterial");
        
        System.out.println("[ResenaControl] Obtener promedio de material: " + idMaterialParam);
        
        if (idMaterialParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idMaterial no proporcionado");
            return;
        }
        
        int idMaterial = Integer.parseInt(idMaterialParam);
        Double promedio = resenaServicio.obtenerPromedioEstrellas(idMaterial);
        
        System.out.println("[ResenaControl] Promedio calculado: " + promedio);
        
        PrintWriter out = response.getWriter();
        out.print("{\"promedio\":" + (promedio != null ? promedio : 0) + "}");
        out.flush();
    }
    
    private void obtenerPorId(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idResenaParam = request.getParameter("idResena");
        
        if (idResenaParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idResena no proporcionado");
            return;
        }
        
        int idResena = Integer.parseInt(idResenaParam);
        DtoResenaRespuesta resena = resenaServicio.obtenerPorId(idResena);
        
        if (resena != null) {
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(resena));
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reseña no encontrada");
        }
    }
    
    private void crearResena(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String comentario = request.getParameter("comentario");
        String estrellasParam = request.getParameter("cantidadEstrellas");
        String idUsuarioParam = request.getParameter("idUsuario");
        String idMaterialParam = request.getParameter("idMaterialEducativo");
        
        System.out.println("[ResenaControl] Crear reseña - comentario: " + comentario);
        System.out.println("[ResenaControl] estrellas: " + estrellasParam);
        System.out.println("[ResenaControl] idUsuario: " + idUsuarioParam);
        System.out.println("[ResenaControl] idMaterial: " + idMaterialParam);
        
        if (comentario == null || estrellasParam == null || 
            idUsuarioParam == null || idMaterialParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, 
                       "Faltan parámetros requeridos");
            return;
        }
        
        try {
            DtoResenaCrear dto = new DtoResenaCrear(
                comentario,
                Integer.parseInt(estrellasParam),
                Integer.parseInt(idUsuarioParam),
                Integer.parseInt(idMaterialParam)
            );
            
            DtoResenaRespuesta resenaCreada = resenaServicio.crearResena(dto);
            
            System.out.println("[ResenaControl] Reseña creada exitosamente: " + resenaCreada.getIdResena());
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"success\":true,\"resena\":" + gson.toJson(resenaCreada) + "}");
            out.flush();
            
        } catch (IllegalArgumentException e) {
            System.err.println("[ResenaControl] Validación fallida: " + e.getMessage());
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
    
    private void actualizarResena(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idResenaParam = request.getParameter("idResena");
        String comentario = request.getParameter("comentario");
        String estrellasParam = request.getParameter("cantidadEstrellas");
        
        if (idResenaParam == null || comentario == null || estrellasParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, 
                       "Faltan parámetros requeridos");
            return;
        }
        
        try {
            int idResena = Integer.parseInt(idResenaParam);
            int estrellas = Integer.parseInt(estrellasParam);
            
            boolean actualizado = resenaServicio.actualizarResena(idResena, comentario, estrellas);
            
            if (actualizado) {
                PrintWriter out = response.getWriter();
                out.print("{\"success\":true,\"mensaje\":\"Reseña actualizada correctamente\"}");
                out.flush();
            } else {
                enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reseña no encontrada");
            }
        } catch (IllegalArgumentException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
    
    private void eliminarResena(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String idResenaParam = request.getParameter("idResena");
        
        if (idResenaParam == null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST, "idResena no proporcionado");
            return;
        }
        
        int idResena = Integer.parseInt(idResenaParam);
        boolean eliminado = resenaServicio.eliminarResena(idResena);
        
        if (eliminado) {
            PrintWriter out = response.getWriter();
            out.print("{\"success\":true,\"mensaje\":\"Reseña eliminada correctamente\"}");
            out.flush();
        } else {
            enviarError(response, HttpServletResponse.SC_NOT_FOUND, "Reseña no encontrada");
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
