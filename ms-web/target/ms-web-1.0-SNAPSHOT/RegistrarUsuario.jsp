<%@page import="utilidad.Ruta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro de Usuario</title>
        <link rel="stylesheet" href="css/styleSesion_1.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        

    </head>
    <body>
        <%
            System.out.println("PUERTO USUARIO: "+Ruta.MS_USUARIO_URL);
        %>
        
        <div class="form-container">
            <div style="text-align: center;">
                <img src="img/Logo2.png" width="180" height="150" alt="Logo"/>
            </div>
            <h1>Registrar</h1>
            <form action="<%=Ruta.MS_USUARIO_URL%>/registro" method="POST">


                <div class="form-group">
                    <label for="primerNombre">Primer Nombre</label>
                    <input type="text" id="primerNombre" name="primerNombre" required>
                </div>

                <div class="form-group">
                    <label for="primerApellido">Primer Apellido</label>
                    <input type="text" id="primerApellido" name="primerApellido" required>
                </div>

                <div class="form-group">
                    <label for="correo">Correo Electrónico</label>
                    <input type="email" id="correo" name="correo" required>
                </div>

                <div class="form-group">
                    <label for="institucion">Institución</label>
                    <select id="institucion" name="institucion" required>
                        <option value="">Seleccione una institución</option>
                        <option value="Universidad del Magdalena">Universidad del Magdalena</option>
                        <option value="Universidad Nacional de Colombia">Universidad Nacional de Colombia</option>
                        <option value="Universidad de los Andes">Universidad de los Andes</option>
                        <option value="Pontificia Universidad Javeriana">Pontificia Universidad Javeriana</option>
                        <option value="Universidad del Norte">Universidad del Norte</option>
                        <option value="Universidad del Valle">Universidad del Valle</option>
                        <option value="Universidad EAFIT">Universidad EAFIT</option>
                        <option value="Universidad de Antioquia">Universidad de Antioquia</option>
                        <option value="Universidad Industrial de Santander">Universidad Industrial de Santander</option>
                        <option value="Universidad Sergio Arboleda">Universidad Sergio Arboleda</option>
                        <option value="Universidad Cooperativa de Colombia">Universidad Cooperativa de Colombia</option>
                        <option value="Universidad Libre">Universidad Libre</option>
                        <option value="Otra">Otra</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="fechaNacimiento">Fecha de Nacimiento</label>
                    <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
                </div>

                <div class="form-group">
                    <label for="contrasena">Contraseña</label>
                    <input type="password" id="contrasena" name="contrasena" required>
                </div>

                <button type="submit">Registrar Usuario</button>

                <a href="InicioSesionUsuario.jsp">Iniciar Sesión</a>

            </form>

        </div>


        <%
            String error = request.getParameter("error");
            String mensaje = request.getParameter("mensaje");

            // Si el parámetro "error" viene con el valor "fallido", mostramos la alerta de SweetAlert
            if ("fallido".equals(error) && mensaje != null) {
        %>
            <script>
                Swal.fire({
                    title: '¡Error!',
                    text: '<%= mensaje %>',
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });
            </script>
        <%
            }
        %>

    </body>
</html>             