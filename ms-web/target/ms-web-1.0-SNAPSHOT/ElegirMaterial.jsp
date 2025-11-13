<%-- 
    Document   : ElegirMaterial
    Created on : 17 oct 2025, 9:21:39 a.m.
    Author     : ACER-A315-59
--%>

<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Elige tu material educativo a subir</title>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="stylesheet" href="css/styleUserDash_1.css"/>
    </head>
    <body>
       
        <%
            //Validamos que el usuario llego correctamente
            HttpSession sesion = request.getSession(false);
            if(sesion ==null || sesion.getAttribute("usuario") ==null){
                response.sendRedirect("InicioSesionUsuario.jsp");
                return;
            }
            
            Usuario user_login = (Usuario) sesion.getAttribute("usuario");

        %>
        
        <div class="container">
  
            <aside class="sidebar">
                <div class="logo">
                        <img src="img/Logo2.png" width="190" height="150" alt="Logo pagina" />
                </div>
                <nav>
                    <a href="UsuarioControll?accion=dashboardUser"  class="menu-item active" >Dashboard</a>
                    <div class="menu-item ">Mi aprendizaje</div>
                    <div class="menu-item">Cursos populares</div>
                    <div class="menu-item">Material educativo disponible</div>
                    <div class="menu-item">Mi perfil</div>
                    <div class="menu-item">Opciones</div>
                    <a href="CerrarSesion"  class="menu-item" >Cerrar sesión </a>
  
                </nav>
            </aside>

            <main class="main-content">
                <header class="header">
                    <div class="header-right">
                        <a href="UsuarioControll?accion=dashboardUser" class="add-cancel-btn" style="display: inline-block; text-decoration: none;">Cancelar</a>
                    </div>
                </header>

                <h1 style="text-align: center; padding: 20px">¡<%=user_login.getPrimer_nombre()%>, elige qué material educativo subir!</h1>

                <div class="upload-options">
                    <div class="upload-card">
                        <div class="upload-icon">📖</div>
                        <h2>Libro</h2>
                        <p class="upload-desc">Comparte un libro para ayudar a los demás.</p>
                 
                        <form action="RegistrarLibro.jsp">
                            <button class="upload-btn">Subir</button>
                        </form>
                    </div>

                    <div class="upload-card">
                        <div class="upload-icon">📝</div>
                        <h2>Artículo</h2>
                        <p class="upload-desc">Publica un artículo para compartir tus ideas.</p>
                        <button class="upload-btn">Subir</button>
                    </div>

                    <div class="upload-card">
                        <div class="upload-icon">🎓</div>
                        <h2>Curso</h2>
                        <p class="upload-desc">Crea un curso y enseña a otros.</p>
                        <button class="upload-btn">Subir</button>
                    </div>
                </div>
         </main>
        </div>
    </body>
</html>
