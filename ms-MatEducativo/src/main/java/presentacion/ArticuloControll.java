/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import com.google.gson.Gson;
import dto.DtoArticuloRegistro;
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
import servicio.ArticuloServicio;
import utilidad.Ruta;

/**
 *
 * @author ACER-A315-59
 */
@WebServlet(name = "ArticuloControll", urlPatterns = {"/ArticuloControll"})
@MultipartConfig(maxFileSize = 16177215) //Decimos que el maximo de tamaño de un archivo es de 16 mb
public class ArticuloControll extends HttpServlet {
    private ArticuloServicio articuloServicio;
    private Gson gson;
    
    @Override
    public void init(){
        articuloServicio = new ArticuloServicio();
        gson = new Gson();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    String accion = request.getParameter("accion");
    
    if (accion == null || accion.isEmpty()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no proporcionada");
        return;
    }
    
    switch (accion) {
        case "eliminar":
            eliminarArticulo(request, response);
            break;
            
        case "descargar":
            descargarArticulo(request, response);
            break;
            
        default:
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
    }
}

private void eliminarArticulo(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        String idMatStr = request.getParameter("id");
        
        if (idMatStr == null || idMatStr.isEmpty()) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID no proporcionado");
            return;
        }
        
        int idMaterial = Integer.parseInt(idMatStr);
        
        boolean eliminado = articuloServicio.eliminarArticulo(idMaterial);
        
        if (eliminado) {
            System.out.println("Artículo eliminado exitosamente con ID: " + idMaterial);
            response.sendRedirect(Ruta.MS_USUARIO_URL + "/UsuarioControll?accion=dashboardUser");
        } else {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=No se pudo eliminar el artículo");
        }
        
    } catch (NumberFormatException e) {
        response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID inválido");
    } catch (Exception e) {
        response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=Error al eliminar: " + e.getMessage());
    }
}

private void descargarArticulo(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
    InputStream pdfStream = null;
    
    try {
        String idMatStr = request.getParameter("id");
        
        if (idMatStr == null || idMatStr.isEmpty()) {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID no proporcionado");
            return;
        }
        
        int idMaterial = Integer.parseInt(idMatStr);
        
        String nombreArticulo = articuloServicio.obtenerNombreArticulo(idMaterial);
        String nombreArchivo = nombreArticulo.replaceAll("[^a-zA-Z0-9.-]", "_") + ".pdf";
        
        // Obtener el PDF
        pdfStream = articuloServicio.descargarArticuloPDF(idMaterial);
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
        
        byte[] buffer = new byte[4096];
        int bytesRead;
        
        while ((bytesRead = pdfStream.read(buffer)) != -1) {
            response.getOutputStream().write(buffer, 0, bytesRead);
        }
        
        response.getOutputStream().flush();
        System.out.println("Artículo descargado exitosamente: " + nombreArchivo);
        
    } catch (NumberFormatException e) {
        response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=ID inválido");
    } catch (Exception e) {
        System.err.println("Error al descargar artículo: " + e.getMessage());
        response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=Error al descargar: " + e.getMessage());
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
            case "register" -> registrarArticulo(request,response);
            case "eliminar" -> eliminarArticulo(request, response);
            default -> throw new AssertionError();
        }
       
    }
    
    @Override
    public String getServletInfo() {
        return "Controlador de Libros con soporte JSON";
    }

    private void registrarArticulo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
        
        
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String categoria = request.getParameter("categoria");
        int volumen = Integer.parseInt(request.getParameter("volumen"));
        int cantPaginas = Integer.parseInt(request.getParameter("cantidad_paginas"));
        String año_publi = request.getParameter("año_publicacion");

        InputStream inputStream = null;
        Part filePart = request.getPart("archivoPDF");

        System.out.println("Nombre del articulo: " + nombre);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Categoría: " + categoria);
        System.out.println("volumen: " + volumen);
        System.out.println("Año de publicación (String): " + año_publi);
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
        DtoArticuloRegistro dtoArti = new DtoArticuloRegistro(1, id_usuario, nombre, descripcion, categoria, volumen, año_publi, cantPaginas, "articulo");
        
        try {
            if(articuloServicio.subirArticulo(dtoArti, inputStream)){
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarArticulo.jsp?mensaje=Ingreso exitoso");

            } else {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarArticulo.jsp?error=No se pudo subir el libro");
            }
        } catch (NumberFormatException ex) {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarArticulo.jsp?error=En formato de numero");

        } catch (Exception ex) {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarArticulo.jsp?error=Error general");

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
