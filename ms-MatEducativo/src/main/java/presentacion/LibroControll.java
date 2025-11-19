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
import java.net.URLEncoder;
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
        
        switch (accion) {
            case "buscarMaterialPorUsuario" -> buscarMaterialPorUsuarioJSON(request, response);
            case "matEducativos" -> { listarMateriales(request,response);}
            case "eliminar" -> eliminarLibro(request, response);
             case "descargar" -> descargarLibro(request, response); // NUEVO
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
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
            System.out.println("Lista de materialitos: "+listaMateriales);
            if (listaMateriales != null) {
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

    private void enviarError(HttpServletResponse response, String mensaje) throws IOException {
        ErrorResponse error = new ErrorResponse(false, mensaje);
        String jsonError = gson.toJson(error);
        
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonError);
            out.flush();
        }
    }
    
    private void eliminarLibro(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        String idMatStr = request.getParameter("id");
        
        if (idMatStr == null || idMatStr.isEmpty()) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID no proporcionado");
            return;
        }
        
        int idMaterial = Integer.parseInt(idMatStr);
        
        boolean eliminado = subirMatServicio.eliminarLibro(idMaterial);
        
        if (eliminado) {
            System.out.println("Libro eliminado exitosamente con ID: " + idMaterial);
            // Redirigir al dashboard del usuario
            response.sendRedirect(Ruta.MS_USUARIO_URL + "/UsuarioControll?accion=dashboardUser");
        } else {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=No se pudo eliminar el libro");
        }
        
    } catch (NumberFormatException e) {
        response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID inválido");
    } catch (Exception e) {
        response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=Error al eliminar: " + e.getMessage());
    }
}
    
    
private void descargarLibro(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
    InputStream pdfStream = null;
    
    try {
        String idMatStr = request.getParameter("id");
        
        if (idMatStr == null || idMatStr.isEmpty()) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID no proporcionado");
            return;
        }
        
        int idMaterial;
        try {
            idMaterial = Integer.parseInt(idMatStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID inválido");
            return;
        }
        
        String nombreLibro = subirMatServicio.obtenerNombreLibro(idMaterial);
        if (nombreLibro == null) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=Libro no encontrado");
            return;
        }
        
        String nombreArchivo = nombreLibro.replaceAll("[^a-zA-Z0-9.-]", "_") + ".pdf";
        
        pdfStream = subirMatServicio.descargarLibroPDF(idMaterial);
        if (pdfStream == null) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=PDF no disponible");
            return;
        }
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
        
        byte[] buffer = new byte[4096];
        int bytesRead;
        
        while ((bytesRead = pdfStream.read(buffer)) != -1) {
            response.getOutputStream().write(buffer, 0, bytesRead);
        }
        
        response.getOutputStream().flush();
        System.out.println("Libro descargado exitosamente: " + nombreArchivo);
        
    } catch (Exception e) {
        
        System.err.println("Error al descargar libro: " + e.getMessage());
        e.printStackTrace();
        
       
        if (!response.isCommitted()) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=Error al descargar");
        }
    } finally {
        if (pdfStream != null) {
            try {
                pdfStream.close();
            } catch (IOException e) {
                System.err.println("Error cerrando stream: " + e.getMessage());
            }
        }
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
   private void listarMateriales(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<DtoMatEducativo> listaMateriales = subirMatServicio.listarMateriales();

            System.out.println("Lista de materiales obtenida: " + (listaMateriales != null ? listaMateriales.size() : "null"));

            System.out.println("Lista de materialitos: "+listaMateriales);
            if (listaMateriales != null) {
                String jsonResponse = gson.toJson(listaMateriales);
                
                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonResponse);
                    out.flush(); 
                }
                
                System.out.println("Material educativo enviado como JSONNNNNNNNNNNNN: ");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                enviarError(response, "No se encontraron materiales para el usuario");
            }

        } catch (IOException ex) {
            System.err.println("Error de IO en listarMateriales: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                enviarError(response, "Error al procesar la solicitud: " + ex.getMessage());
            } catch (IOException e) {
            }
        } catch (Exception ex) {
            System.err.println("Error general en listarMateriales: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                enviarError(response, "Error interno del servidor");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
