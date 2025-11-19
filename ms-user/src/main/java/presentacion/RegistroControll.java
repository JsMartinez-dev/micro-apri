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
import java.time.LocalDate;
import dto.DtoUsuarioRegistro;
import servicio.RegistroServicio;
import utilidad.Ruta;

@WebServlet(name = "registro", urlPatterns = {"/registro"})
public class RegistroControll extends HttpServlet {


    private RegistroServicio registroServicio;
    
    
    @Override
    public void init(){
        registroServicio = new RegistroServicio();
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


        String primer_nombre = request.getParameter("primerNombre");
        String primer_apellido = request.getParameter("primerApellido");
        String correo = request.getParameter("correo");
        String institucion = request.getParameter("institucion");
        String fecha_nacimiento = request.getParameter("fechaNacimiento");
        String contrasena_plana = request.getParameter("contrasena");

        
        System.out.println("Datos del formulario: ");
        System.out.println("Primer nombre: " + primer_nombre);
        System.out.println("Primer apellido: " + primer_apellido);
        System.out.println("Correo: " + correo);
        System.out.println("Institución: " + institucion);
        System.out.println("Fecha nacimiento (string): " + fecha_nacimiento);
        System.out.println("Contraseña: " + contrasena_plana); 
        
        LocalDate fecha_registro = LocalDate.now();
        LocalDate fecha_naci = LocalDate.parse(fecha_nacimiento);
        

        DtoUsuarioRegistro dtoUser = new DtoUsuarioRegistro(primer_nombre, primer_apellido, correo, institucion, fecha_naci,fecha_registro,contrasena_plana);
        System.out.println("Usuario creado: " + dtoUser);

        // Registrar usuario
        boolean registrado = registroServicio.registrarUsuario(dtoUser);
        System.out.println("Resultado de registroServicio.registrarUsuario(u): " + registrado);

       if(registrado){
            response.sendRedirect(Ruta.MS_WEB+"/InicioSesionUsuario.jsp?registro=exitoso");
        } else {
            response.sendRedirect(Ruta.MS_WEB+"/RegistrarUsuario.jsp?error=fallido&mensaje=" + 
                java.net.URLEncoder.encode("No se pudo completar el registro", "UTF-8"));
        }
            

    } catch (Exception e) {
        System.out.println("❌ [ERROR] Ocurrió una excepción en doPost Registro:"+e.getMessage());
        String errorMsg = "Error en el servidor: " + e.getMessage();
        response.sendRedirect(Ruta.MS_WEB+"/RegistroUsuario.jsp?error=excepcion&mensaje="+
            java.net.URLEncoder.encode(errorMsg, "UTF-8")
        );
    }
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }
    

}
