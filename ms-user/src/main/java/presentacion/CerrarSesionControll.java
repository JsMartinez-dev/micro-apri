/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package presentacion;

import dto.DtoUsuarioLogin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;
import servicio.LoginServicio;
import utilidad.Ruta;

/**
 *
 * @author ACER-A315-59
 */
@WebServlet(name = "CerrarSesion", urlPatterns = {"/CerrarSesion"})
public class CerrarSesionControll extends HttpServlet {
    
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

        HttpSession Misesion = request.getSession(false);

        try {
            if(Misesion != null && Misesion.getAttribute("usuario") != null){
                DtoUsuarioLogin u = (DtoUsuarioLogin) Misesion.getAttribute("usuario");
                System.out.println("-----------\nUsuario para cerrar sesion : "+u+"\n--------\n");
                try {
                    loginServicio.cerrarSesion(u);
                } catch (Exception ex) {
                    System.out.println("Error al actualizar estado en BD: " + ex.getMessage());
                }

                Misesion.invalidate();
            }

            response.sendRedirect(Ruta.MS_WEB+"/InicioSesionUsuario.jsp");

        } catch (IllegalStateException ex) {
            System.out.println("Sesión ya invalidada: " + ex.getMessage());
            response.sendRedirect(Ruta.MS_WEB+"/InicioSesionUsuario.jsp");
        } 
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
