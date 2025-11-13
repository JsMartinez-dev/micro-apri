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
        System.out.println("❌ [ERROR] Ocurrió una excepción en doPost Registro:");
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
    
    
    /*
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    System.out.println(">>> [REGISTRO] LLEGÓ PETICIÓN AL SERVLET DE REGISTRO <<<");
    System.out.println("Método: " + request.getMethod());
    System.out.println("Ruta: " + request.getRequestURI());
    System.out.println("QueryString: " + request.getQueryString());

    try {
        // Ver todos los parámetros recibidos
        System.out.println("---- Parámetros recibidos ----");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = request.getParameter(name);
            System.out.println("  " + name + " = " + value);
        }
        System.out.println("------------------------------");

        // Obtener parámetros específicos
        String primer_nombre = request.getParameter("primerNombre");
        String primer_apellido = request.getParameter("primerApellido");
        String correo = request.getParameter("correo");
        String institucion = request.getParameter("institucion");
        String fecha_nacimiento = request.getParameter("fechaNacimiento");
        String contrasena_plana = request.getParameter("contrasena");

        System.out.println(">>> Datos del formulario <<<");
        System.out.println("Primer nombre: " + primer_nombre);
        System.out.println("Primer apellido: " + primer_apellido);
        System.out.println("Correo: " + correo);
        System.out.println("Institución: " + institucion);
        System.out.println("Fecha nacimiento (string): " + fecha_nacimiento);
        System.out.println("Contraseña: " + contrasena_plana);
        System.out.println("------------------------------");

        // Validar que no vengan nulos
        if (primer_nombre == null || primer_apellido == null || correo == null ||
            institucion == null || fecha_nacimiento == null || contrasena_plana == null) {

            System.out.println("❌ Uno o más parámetros vienen nulos.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Faltan parámetros obligatorios");
            return;
        }

        // Convertir fechas
        LocalDate fecha_naci = LocalDate.parse(fecha_nacimiento);
        LocalDate fecha_registro = LocalDate.now();

        // Crear usuario
        Usuario u = new Usuario(1, primer_nombre, primer_apellido, fecha_naci, fecha_registro, correo, institucion, contrasena_plana, false, "usuario");
        System.out.println("Usuario creado: " + u);

        // Registrar usuario
        boolean registrado = registroServicio.registrarUsuario(u);
        System.out.println("Resultado de registroServicio.registrarUsuario(u): " + registrado);

        if (registrado) {
            System.out.println("✅ Usuario registrado exitosamente.");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Usuario registrado exitosamente");
        } else {
            System.out.println("❌ Error al registrar usuario en el servicio.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error al registrar usuario");
        }

    } catch (Exception e) {
        System.out.println("❌ [ERROR] Ocurrió una excepción en doPost Registro:");
        e.printStackTrace(); // Esto imprime todo el stack trace completo
        String errorMsg = "Error en el servidor: " + e.getMessage();

        response.sendRedirect(
            "http://localhost:8090/proyecto-APRI-microservicios/RegistroUsuario.jsp?error=excepcion&mensaje=" +
            java.net.URLEncoder.encode(errorMsg, "UTF-8")
        );
    }
    */
}
