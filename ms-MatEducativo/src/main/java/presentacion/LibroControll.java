/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import com.google.gson.Gson;
import dto.DtoLibroRegistro;
import dto.DtoMatEducativo;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import servicio.LibroServicio;
import utilidad.Ruta;

/**
 *
 * @author ACER-A315-59
 */
@WebServlet(name = "LibroControll", urlPatterns = {"/LibroControll"})
@MultipartConfig(maxFileSize = 16177215) //Decimos que el maximo de tamaño de un archivo es de 16 mb
public class LibroControll extends HttpServlet {
    private LibroServicio subirMatServicio;
    private Gson gson;
    
    @Override
    public void init(){
        subirMatServicio = new LibroServicio();
        gson = new Gson();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // Si la acción es buscarMaterialPorUsuario, devolver JSON
        if ("buscarMaterialPorUsuario".equals(accion)) {
            buscarMaterialPorUsuarioJSON(request, response);
        } else {
            // Otras acciones GET si las necesitas
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }
    
    /**
     * Método para buscar material por usuario y devolverlo en JSON
     * URL: http://localhost:8092/MsLibro/LibroControll?accion=buscarMaterialPorUsuario&idPersona=123
     */
    private void buscarMaterialPorUsuarioJSON(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String idPersonaStr = request.getParameter("idPersona");
            
            if (idPersonaStr == null || idPersonaStr.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                enviarError(response, "Parámetro idPersona es requerido");
                return;
            }
            
            int idPersona = Integer.parseInt(idPersonaStr);
            
            List<DtoMatEducativo> listaMateriales = subirMatServicio.buscarMaterialPorUsuario(idPersona);
            System.out.println("Lista de materiales: "+listaMateriales);
            if (listaMateriales != null) {
                // Convertir a JSON y enviar
                String jsonResponse = gson.toJson(listaMateriales);
                
                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonResponse);
                    out.flush(); //Muchachos aqui aseguro que se envie toda la lista encontrada del material educativo
                }
                
                System.out.println("Material educativo enviado como JSON para usuario: " + idPersona);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                enviarError(response, "No se encontraron materiales para el usuario");
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            enviarError(response, "ID de usuario inválido");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            enviarError(response, "Error interno: " + e.getMessage());
        }
    }
    
    /**
     * Envía un mensaje de error en formato JSON
     */
    private void enviarError(HttpServletResponse response, String mensaje) throws IOException {
        ErrorResponse error = new ErrorResponse(false, mensaje);
        String jsonError = gson.toJson(error);
        
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonError);
            out.flush();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("accion")==null){
            response.sendRedirect(Ruta.MS_WEB+"/RegistrarUsuario.jsp?error=No se pudo guardar la sesion");
            return;
        }
        
        String accion = request.getParameter("accion");
        System.out.println("Accion del DOPOST libro : "+accion);
        
        switch (accion) {
            case "register" -> registrarLibro(request,response);
            default -> throw new AssertionError();
        }
       
    }
    
    @Override
    public String getServletInfo() {
        return "Controlador de Libros con soporte JSON";
    }

    private void registrarLibro(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
        
        
        String nombreLibro = request.getParameter("nombreLibro");
        String descripcion = request.getParameter("Descripcion");
        String categoria = request.getParameter("Categoria");
        int edicion = Integer.parseInt(request.getParameter("edicion"));
        String año_publi = request.getParameter("AñoPublicacion");
        String editorial = request.getParameter("Editorial");
        int cantPaginas = Integer.parseInt(request.getParameter("cantPaginas"));

        InputStream inputStream = null;
        Part filePart = request.getPart("archivoPDF");

        System.out.println("Nombre del libro: " + nombreLibro);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Categoría: " + categoria);
        System.out.println("Edición: " + edicion);
        System.out.println("Año de publicación (String): " + año_publi);
        System.out.println("Editorial: " + editorial);
        System.out.println("Cantidad de páginas: " + cantPaginas);

        if (filePart != null) {
            System.out.println("Archivo PDF recibido: " + filePart.getSubmittedFileName());
            System.out.println("Tamaño del archivo (bytes): " + filePart.getSize());
            System.out.println("Tipo de contenido: " + filePart.getContentType());
            inputStream = filePart.getInputStream();
            System.out.println("NO ESTÁ VACÍO");
        } else {
            System.out.println("ESTÁ VACÍO");
        }


   
        int id_usuario = Integer.parseInt(request.getParameter("idUsuario"));
        System.out.println("Id del usuario que va a guardar el libro: "+id_usuario);
        DtoLibroRegistro dtoLibro = new DtoLibroRegistro(1, id_usuario, nombreLibro, descripcion, categoria, edicion, año_publi, cantPaginas,"libro",editorial);
     
        try {
            if(subirMatServicio.subirLibro(dtoLibro, inputStream)){
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarLibro.jsp?mensaje=Ingreso exitoso");

            } else {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarLibro.jsp?error=No se pudo subir el libro");
            }
        } catch (NumberFormatException ex) {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarLibro.jsp?error=En formato de numero");

        } catch (Exception ex) {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarUsuario.jsp?error=Error general");

        }
        

    }
    
    /**
     * Clase interna para respuestas de error
     */
    private static class ErrorResponse {
        private final boolean success;
        private final String message;

        public ErrorResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
