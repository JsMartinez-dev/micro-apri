/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import com.google.gson.Gson;
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
import java.net.URLEncoder;
import java.util.List;
import modelo.Usuario;
import servicio.UsuarioServicio;
import utilidad.Ruta;

/**
 *
 * @author ACER-A315-59
 */
@WebServlet(name = "UsuarioControll", urlPatterns = {"/UsuarioControll"})
    public class UsuarioControll extends HttpServlet {

     UsuarioServicio listUServicio;
     MatEducativoClienteHttp matEduCliente;
     Gson gson;
     
     @Override
    public void init(){
        listUServicio=new UsuarioServicio();
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
            case "GestionUsuario" -> gestionUsuario(request,response);
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
        
        try {
            List<Usuario> listaU = listUServicio.listUser();
            
            if(listaU !=null){
                request.setAttribute("listU",listaU);
                request.getRequestDispatcher("DashboardAdmin.jsp").forward(request, response);
            }else{
                response.getWriter().print("ERROR EN EL GESTION USUARIOS");
            }
        } catch (Exception ex) {
            System.out.println("ERROR EN EL DoGett, Error: "+ex.getMessage());
        }
    }

    private void gestionUsuario(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            List<Usuario> listaU = listUServicio.listUser();
            
            if(listaU !=null){
                request.setAttribute("listU",listaU);
                request.getRequestDispatcher("DashboardAdmin_GU.jsp").forward(request, response);
            }else{
                response.getWriter().print("ERROR EN EL GESTION USUARIOS");
            }
        } catch (Exception ex) {
            System.out.println("ERROR EN EL DoGett, Error: "+ex.getMessage());
        }
    }

}
