/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

/**
 *
 * @author ACER-A315-59
 */
@WebServlet(name = "ApiGateway", urlPatterns = {"/ApiGateway/*"})
public class ApiGateway extends HttpServlet {
    
    
    public static final String MS_USUARIO_URL = "http://localhost:8091/ms-Apri-Usuario";
    public static final String MS_MATEDU_URL = "http://localhost:8092/ms-APRI-MatEdu";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        routeRequest(request, response,"GET");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        routeRequest(request, response,"POST");
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        routeRequest(request, response,"PUT");
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        routeRequest(request, response,"DELETE");
    }    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void routeRequest(HttpServletRequest request, HttpServletResponse response, String method) throws IOException {

        String pathInfo =request.getPathInfo();     
        System.out.println("LLEGÓ PETICIÓN AL API GATEWAY: " + request.getPathInfo());

        
        if(pathInfo==null){
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ruta no especificada");
           
          return;
        }
        
        String targetUrl= determinarServicioDestino(pathInfo);
        
        if(targetUrl==null){
          response.sendError(HttpServletResponse.SC_NOT_FOUND, "Servicio no encontrado");
          return;
        }       
        
        //Construimos la URL completa para el micro servicio
        String queryString = request.getQueryString();
        
        String urlCompleta= targetUrl+pathInfo+ (queryString!=null ? "?"+queryString : "");
        
        System.out.println("RUTA COMPLETA: "+urlCompleta);
        //Le realizamos la peticion al micro servicio  
        forwardRequest(urlCompleta,method,request,response);
        
    }

  private String determinarServicioDestino(String pathInfo) {
        // Microservicio Usuario - Incluye registro y cerrar sesión
        if (pathInfo.startsWith("/usuario") || 
            pathInfo.startsWith("/login") || 
            pathInfo.startsWith("/registro")) {
            return MS_USUARIO_URL;
        } 
        // Microservicio MatEdu
        else if (pathInfo.startsWith("/libro") || 
                 pathInfo.startsWith("/agregarLibro") ||
                 pathInfo.startsWith("/material")) {
            return MS_MATEDU_URL;
        }
        return null;
    }
    
    /**
     * Reenvía la petición al microservicio y retorna la respuesta
     */
    private void forwardRequest(String targetUrl, String method, HttpServletRequest request, HttpServletResponse response) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoOutput(true);

        // Copiamos los headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            conn.setRequestProperty(headerName, request.getHeader(headerName));
        }

        // Si es POST o PUT, mandamos el body del formulario
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
            StringBuilder postData = new StringBuilder();
            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = params.nextElement();
                String value = request.getParameter(name);
                if (postData.length() != 0) postData.append('&');
                postData.append(name).append('=').append(java.net.URLEncoder.encode(value, "UTF-8"));
            }

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.toString().getBytes());
            }
        }

        // Leemos la respuesta del microservicio
        int statusCode = conn.getResponseCode();
        response.setStatus(statusCode);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                statusCode >= 200 && statusCode < 300
                        ? conn.getInputStream()
                        : conn.getErrorStream()))) {

            String line;
            PrintWriter out = response.getWriter();
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        }
    }



    
}   