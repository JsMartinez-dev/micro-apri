/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import com.google.gson.Gson;
import dto.DtoMatEducativo;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import modelo.Libro;
import modelo.Usuario;
import servicio.LibroServicio;

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
            e.printStackTrace();
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
        
        HttpSession sesion = request.getSession(false);
        System.out.println("LLEGO SERVLET");
        
        String nombreLibro = request.getParameter("nombreLibro");
        String descripcion = request.getParameter("Descripcion");
        String categoria = request.getParameter("Categoria");
        int edicion = Integer.parseInt(request.getParameter("edicion"));
        String año_publi = request.getParameter("AñoPublicacion");
        LocalDate año_publicacion = LocalDate.parse(año_publi);
        String editorial = request.getParameter("Editorial");
        int cantPaginas = Integer.parseInt(request.getParameter("cantPaginas"));
   
        InputStream inputStream = null;
        Part filePart = request.getPart("archivoPDF");
        if(filePart != null){
            inputStream = filePart.getInputStream();
            System.out.println("NO ESTA VACIO ");
        } else {
            System.out.println("ESTA VACIO ");
        }
   
        Usuario user = (Usuario) sesion.getAttribute("usuario");
        Libro librito = new Libro(edicion, editorial, cantPaginas, 1, categoria, nombreLibro, 
                                   año_publicacion, "libro", descripcion, true, user);
     
        try {
            if(subirMatServicio.subirLibro(librito, user, inputStream)){
                request.setAttribute("mensaje", "Ingreso exitoso");
                request.getRequestDispatcher("RegistrarLibro.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No se pudo agregar libro");
                request.getRequestDispatcher("RegistrarLibro.jsp").forward(request, response);                
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Error en formato de números: " + ex.getMessage());
            request.getRequestDispatcher("RegistrarLibro.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Error al procesar: " + ex.getMessage());
            request.getRequestDispatcher("RegistrarLibro.jsp").forward(request, response);
            ex.printStackTrace();
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Controlador de Libros con soporte JSON";
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
