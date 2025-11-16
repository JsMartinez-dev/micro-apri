/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import com.google.gson.Gson;
import dto.DtoAdminLogin;
import dto.DtoMatEducativo;
import dto.DtoUsuarioLogin;
import integracion.MatEducativoClienteHttp;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicio.UsuarioServicio;
import utilidad.Ruta;

/**
 *
 * @author ACER-A315-59
 */
@WebServlet(name = "UsuarioControll", urlPatterns = {"/UsuarioControll"})
    public class UsuarioControll extends HttpServlet {

     UsuarioServicio userServicio;
     MatEducativoClienteHttp matEduCliente;
     Gson gson;
     
     @Override
    public void init(){
        userServicio=new UsuarioServicio();
        matEduCliente = new MatEducativoClienteHttp();
        gson = new Gson();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        String accion = request.getParameter("accion");
        
        System.out.println("GUARDAMOS LA ACCION: "+accion);
        
        if(accion==null){
            response.sendRedirect(Ruta.MS_WEB+"/InicioSesionUsuario.jsp");
            return;
        }
        
        switch (accion) {
            case "dashboardUser" -> dashboardUser(request,response);
            case "dashboardAdmin" -> dashboardAdmin(request,response);
            case "gestionUsuario" -> gestionUsuarios(request,response);
            case "matEducativos" -> listarMaterialesEducativos(request,response);
            case "eliminarUsuario"->{
                int id_user = Integer.parseInt(request.getParameter("id"));
                eliminarUser(request,response,id_user);
            }
            case "buscarUsuario" ->{
                String nombreUser = request.getParameter("nombreUser");
                System.out.println("Nombre del usuario a buscar: "+nombreUser);
                buscarUser(request,response,nombreUser);
            }
            default -> throw new AssertionError();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void dashboardUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession miSesion = request.getSession(false);
        
        
        if(miSesion==null || miSesion.getAttribute("usuario")==null){
            request.getRequestDispatcher(Ruta.MS_WEB+"/InicioSesionUsuario.jsp?dashUser=fallo");
        }
        
        DtoUsuarioLogin dtoUser=(DtoUsuarioLogin) miSesion.getAttribute("usuario");
        System.out.println("DTO desde el UsuarioControl: "+dtoUser);
        
        
        List<DtoMatEducativo> listMat = matEduCliente.buscarMaterialPorUsuario(dtoUser.id_persona());  
        System.out.println("Lista de materiales desde Usuario controll: "+listMat);
        if(listMat!=null){
            System.out.println("NOT NULLL");
            String jsonUsuario = gson.toJson(dtoUser);
            String jsonLista = gson.toJson(listMat);
            
            String url = Ruta.MS_WEB+"/DashboardUser.jsp"+"?usuario="+ 
                    URLEncoder.encode(jsonUsuario, "UTF-8")
                    +"&lista=" + URLEncoder.encode(jsonLista, "UTF-8");
            

            response.sendRedirect(url);

        }else{
            System.out.println("NULLL");
            request.getRequestDispatcher("InicioSesionUsuario.jsp?dashUser=fallo");
        }
    }

    private void dashboardAdmin(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession miSesion = request.getSession(false);
      
        if(miSesion==null || miSesion.getAttribute("usuario")==null){
            request.getRequestDispatcher(Ruta.MS_WEB+"/InicioSesionUsuario.jsp?dashUser=fallo");
        }
        
        DtoAdminLogin dtoAdmin=(DtoAdminLogin) miSesion.getAttribute("usuario");
        System.out.println("DTO desde el dashboard admin: "+dtoAdmin);

        try {
            List<DtoUsuarioLogin> listaU = userServicio.listUser();
            
            if(listaU !=null){
                System.out.println("NOT NULLL");
                String jsonAdmin = gson.toJson(dtoAdmin);
                String listaJson = gson.toJson(listaU);
                String url = Ruta.MS_WEB+"/DashboardAdmin.jsp"+"?admin="+URLEncoder.encode(jsonAdmin, "UTF-8")
                        +"&lista=" + URLEncoder.encode(listaJson, "UTF-8");              
                
           
                response.sendRedirect(url);
            }else{
                request.getRequestDispatcher(Ruta.MS_WEB+"/InicioSesionUsuario.jsp?dashUser=fallo");
            }
        } catch (Exception ex) {
            System.out.println("ERROR EN EL DoGett, Error: "+ex.getMessage());
        }
    }
    private void gestionUsuarios(HttpServletRequest request, HttpServletResponse response){
        HttpSession miSesion = request.getSession(false);
      
        if(miSesion==null || miSesion.getAttribute("usuario")==null){
            request.getRequestDispatcher(Ruta.MS_WEB+"/InicioSesionUsuario.jsp?dashUser=fallo");
        }     
        DtoAdminLogin dtoAdmin=(DtoAdminLogin) miSesion.getAttribute("usuario");
        System.out.println("DTO desde el gestion uruarios: "+dtoAdmin);
        try {
            List<DtoUsuarioLogin> listaU = userServicio.listUser();
            
            if(listaU !=null){
                System.out.println("NOT NULLL");
                String jsonAdmin = gson.toJson(dtoAdmin);
                String listaJson = gson.toJson(listaU);
                String url = Ruta.MS_WEB+"/DashboardAdmin_GU.jsp"+"?admin="+URLEncoder.encode(jsonAdmin, "UTF-8")
                        +"&lista=" + URLEncoder.encode(listaJson, "UTF-8");                                    
                response.sendRedirect(url);
            }else{
                request.getRequestDispatcher(Ruta.MS_WEB+"/InicioSesionUsuario.jsp?dashUser=fallo");
            }
        } catch (Exception ex) {
            System.out.println("ERROR EN EL Do Get, Error: "+ex.getMessage());
        }
    }

    private void eliminarUser(HttpServletRequest request, HttpServletResponse response, int id_user) {
        System.out.println("Antes del eliminar usuario");
        System.out.println("Id del usuario: "+id_user);
        if(userServicio.eliminarUsuario(id_user)){
            gestionUsuarios(request, response);
        }else{
            System.out.println("Usuario inexistente");
        }
    }

    private void buscarUser(HttpServletRequest request, HttpServletResponse response, String nombreUser) {
        
        HttpSession miSesion = request.getSession(false);
        DtoAdminLogin dtoAdmin = (DtoAdminLogin) miSesion.getAttribute("admin");
        System.out.println("Administrador: "+dtoAdmin);
        
        if (nombreUser == null || nombreUser.trim().isEmpty()) {
            gestionUsuarios(request, response);
            return;
        }
        
       
        
    }

   private void listarMaterialesEducativos(HttpServletRequest request, HttpServletResponse response) {
    
    HttpSession miSesion = request.getSession(false);
    
    // Validar sesión
    if(miSesion == null || miSesion.getAttribute("usuario") == null){
        try {
            response.sendRedirect(Ruta.MS_WEB + "/InicioSesionUsuario.jsp?error=sesionExpirada");
            return; // IMPORTANTE: detener ejecución
        } catch (IOException ex) {
            System.err.println("Error al redirigir: " + ex.getMessage());
            return;
        }
    }
    
    DtoUsuarioLogin dtoUser = (DtoUsuarioLogin) miSesion.getAttribute("usuario");
    System.out.println("DTO desde el UsuarioControl: " + dtoUser);
    
    try {
        // Obtener lista de materiales
        List<DtoMatEducativo> listMat = matEduCliente.listarMateriales();  
        System.out.println("Lista de materiales desde Usuario controll: " + listMat);
        
        if(listMat != null && !listMat.isEmpty()) {
            System.out.println("Materiales encontrados: " + listMat.size());
            
            // Convertir a JSON
            String jsonUsuario = gson.toJson(dtoUser);
            String jsonLista = gson.toJson(listMat);
            
            // Construir URL con parámetros codificados
            String url = Ruta.MS_WEB + "/ExplorarMateriales.jsp" + 
                        "?usuario=" + URLEncoder.encode(jsonUsuario, "UTF-8") +
                        "&lista=" + URLEncoder.encode(jsonLista, "UTF-8");
            
            response.sendRedirect(url);
            
        } else {
            System.out.println("No se encontraron materiales o la lista está vacía");
            // Redirigir con mensaje de error
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=noMateriales");
        }
        
    } catch (UnsupportedEncodingException ex) {
        System.err.println("Error de codificación: " + ex.getMessage());
        ex.printStackTrace();
        try {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=codificacion");
        } catch (IOException e) {
            System.err.println("Error al redirigir después de error de codificación: " + e.getMessage());
        }
    } catch (IOException ex) {
        System.err.println("Error de IO: " + ex.getMessage());
        ex.printStackTrace();
        try {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=conexion");
        } catch (IOException e) {
            System.err.println("Error al redirigir después de error de IO: " + e.getMessage());
        }
    } catch (Exception ex) {
        System.err.println("Error general: " + ex.getMessage());
        ex.printStackTrace();
        try {
            response.sendRedirect(Ruta.MS_WEB + "/DashboardUser.jsp?error=general");
        } catch (IOException e) {
            System.err.println("Error al redirigir después de error general: " + e.getMessage());
        }
    }
    }


}
