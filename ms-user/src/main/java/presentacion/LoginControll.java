/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import dto.DtoPersonaLogin;
import servicio.LoginServicio;
import utilidad.Ruta;

/**
 *
 * @author ACER-A315-59
 */
@WebServlet(name = "LoginControll", urlPatterns = {"/login"})
public class LoginControll extends HttpServlet {

    
    LoginServicio loginServicio;
    
    @Override
    public void init(){
        loginServicio = new LoginServicio();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
          
        try {
            String correo = request.getParameter("correo");
            String contrasena = request.getParameter("contrasena");
            
            System.out.println("-_-DATOS DEL LOGIN-__-");
            System.out.println("correo: "+correo);
            System.out.println("contraseña: "+contrasena);
            DtoPersonaLogin u = loginServicio.iniciarSesion(correo,contrasena);
            System.out.println("Usuario encontrado: "+u);
            if(u!=null){
                HttpSession miSesion = request.getSession();                
                miSesion.setAttribute("usuario",u);

                if(u.tipo().equals("usuario")){
                    System.out.println("Es usuario el DTO");
                    response.sendRedirect("UsuarioControll?accion=dashboardUser");
                }else{
                    System.out.println("Es Administrador el DTO");
                   response.sendRedirect("UsuarioControll?accion=dashboardAdmin");
                }
                
            }else{
                response.sendRedirect(Ruta.MS_WEB+"/InicioSesionUsuario.jsp?mensajeF=" + 
                    java.net.URLEncoder.encode("Correo o contraseña incorrecta", "UTF-8"));
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginControll.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
