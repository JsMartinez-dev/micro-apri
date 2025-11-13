<%-- 
    Document   : InicioSesionUsuario
    Created on : 2 oct 2025, 12:10:21 p.m.
    Author     : ACER-A315-59
--%>

<%@page import="utilidad.Ruta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="stylesheet" href="css/styleSesion_1.css"/>
        <title>Inicio de sesión</title>
    </head>
    <body>
        <div class="form-container">
            <div style="text-align: center;">
                <img src="img/Logo2.png" width="180" height="150" alt="Logo"/>
            </div>
            <h1>Iniciar sesión</h1>
            
        <%String registro = request.getParameter("registro");
            
        if(registro !=null && registro.equals("exitoso")){
        %>
            <script>
                    Swal.fire({
                        title: '¡Exito!',
                        text: 'Registro de sesion exitoso',
                        icon: 'success',
                        confirmButtonText: 'Aceptar'
                    });
            </script> 

        <%}%>
            
           <form action="<%=Ruta.MS_USUARIO_URL%>/login" method="POST">
                
                <div class="form-group">
                    <label for="correo">Correo Electrónico</label>
                    <input type="email" id="correo" name="correo" required>
                </div>
                
                <div class="form-group">
                    <label for="contrasena">Contraseña</label>
                    <input type="password" id="contrasena" name="contrasena" required>
                </div>
                
                <button type="submit">Iniciar sesión</button>
                
                <a href="RegistrarUsuario.jsp">Registrar cuenta</a>
            </form>
        
        <%if(request.getAttribute("mensajeF")!=null){%>
        <script>
                Swal.fire({
                    title: '¡Error!',
                    text: '<%= request.getAttribute("mensajeF") %>',
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });
        </script> 
        
        <%}%>
        
        <%if(request.getAttribute("dashUser")!=null){%>
        <script>
                Swal.fire({
                    title: '¡Error!',
                    text: '<%= request.getAttribute("mensajeF") %>',
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });
        </script> 
        
        <%}%>
        
        </div>
    </body>
</html>
