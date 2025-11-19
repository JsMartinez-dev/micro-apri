/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import com.google.gson.Gson;
import dto.DtoLeccionLista;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import servicio.LeccionServicio;
import utilidad.Ruta;


@WebServlet(name = "LeccionControll", urlPatterns = {"/LeccionControll"})
public class LeccionControll extends HttpServlet {
    private LeccionServicio leccionServicio;
    private Gson gson;
    
    @Override
    public void init(){
        leccionServicio = new LeccionServicio();
        gson = new Gson();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        System.out.println("GUARDAMOS LA ACCION: "+accion);
        int id_modulo= Integer.parseInt(request.getParameter("idModulo"));
        int id_curso= Integer.parseInt(request.getParameter("idCurso"));
        String tituloModulo = request.getParameter("tituloModulo");

        if(accion==null || id_modulo<0 || id_curso<0){
            response.sendRedirect(Ruta.MS_WEB+"/InicioSesionUsuario.jsp");
            return;
        }
        
        switch (accion) {
            case "listarLecciones" -> listarLecciones(request,response,id_modulo,id_curso,tituloModulo);
            
            default -> throw new AssertionError();
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("accion")==null){
            response.sendRedirect(Ruta.MS_WEB+"/RegistrarUsuario.jsp?error=No se pudo establecer que accion realizar");
            return;
        }
        
        String accion = request.getParameter("accion");
        System.out.println("Accion del DOPOST libro : "+accion);
        int idModulo= Integer.parseInt(request.getParameter("idModulo"));
        int idCurso= Integer.parseInt(request.getParameter("idCurso"));
        String tituloModulo = request.getParameter("tituloModulo");
        switch (accion) {
            case "register" -> registrarLeccion(request,response,idModulo,idCurso,tituloModulo);
            default -> throw new AssertionError();
        }
       
    }
    private void listarLecciones(HttpServletRequest request, HttpServletResponse response, int id_modulo, int id_curso, String tituloModulo) {
        
        List<DtoLeccionLista> listLeccion = leccionServicio.obtenerListaLeccin(id_modulo);
        if(listLeccion!=null){
            try {
                System.out.println("LISTA DE LECCION NO NULA");
                String listaJson = gson.toJson(listLeccion);
                response.sendRedirect(Ruta.MS_WEB+"/GestionarLecciones.jsp"+"?idModulo="+id_modulo
                        +"&lista=" + URLEncoder.encode(listaJson, "UTF-8")
                        +"&idCurso="+id_curso+"&titulo="+tituloModulo);
                
            } catch (UnsupportedEncodingException ex) {
                System.out.println("Error : "+ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Error 2: "+ex.getMessage());
            }
         }
    }
        
    @Override
    public String getServletInfo() {
        return "";
    }

    private void registrarLeccion(HttpServletRequest request, HttpServletResponse response, int idModulo, int idCurso, String tituloModulo) throws IOException, ServletException 
    {
        
        
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String url_video = request.getParameter("url_video");
        System.out.println("Nombre de la leccion: " + nombre);
        System.out.println("descripcion de la leccion: " + descripcion);
        System.out.println("url_video de la leccion: " + url_video);
        
        DtoLeccionLista dtoLeccion= new DtoLeccionLista(1, nombre, url_video, descripcion, idModulo);
        
        try {

            if( leccionServicio.subirLeccion(dtoLeccion)){            
                listarLecciones(request, response, idModulo, idCurso, tituloModulo);
            } else {
                System.out.println("NO PUDO CREAR EL MODULO");
                listarLecciones(request, response, idModulo, idCurso, tituloModulo);
            }
        } catch (NumberFormatException ex) {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarCurso.jsp?error=En formato de numero");

        } catch (Exception ex) {
                response.sendRedirect(Ruta.MS_WEB+"/RegistrarCurso.jsp?error=Error general");

        }
        
        
    }



  
}
