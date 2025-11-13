<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Elige tu material educativo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/styleUserDash_2.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <%
        // Validamos que el usuario llegó correctamente
        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("InicioSesionUsuario.jsp");
            return;
        }
        
        DtoUsuarioLogin userDto = (DtoUsuarioLogin) sesion.getAttribute("usuario");
    %>
    
    <div class="container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="logo">
                <img src="img/Logo2.png" width="190" height="150" alt="Logo" />
            </div>
            <nav>
                <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser" class="menu-item active">
                     Dashboard
                </a>
                <div class="menu-item ">Mi aprendizaje</div>
                <div class="menu-item">Cursos populares</div>
                <div class="menu-item ">Material disponible</div>
                <div class="menu-item">Mi perfil</div>
                <div class="menu-item">️Configuración</div>
                <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion" class="menu-item">
                    Cerrar sesión
                </a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1>Agregar Recurso Educativo</h1>
                    <p>Comparte tu conocimiento con la comunidad</p>
                </div>
                <div class="header-right">
                    <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser" 
                       class="add-cancel-btn" 
                       style="text-decoration: none;">
                        ← Cancelar
                    </a>
                </div>
            </header>

            <h2 class="page-title">
                ¡<%=userDto.primerNombre()%>, elige qué material educativo subir! 
            </h2>

            <div class="upload-options">
                <div class="upload-card">
                    <div class="upload-icon">📖</div>
                    <h2>Libro</h2>
                    <p class="upload-desc">
                        Comparte un libro para ayudar a los demás a aprender.
                    </p>
                    <form action="RegistrarLibro.jsp" method="post">
                        <button type="submit" class="upload-btn">
                            Subir Libro
                        </button>
                    </form>
                </div>

                <div class="upload-card">
                    <div class="upload-icon">📝</div>
                    <h2>Artículo</h2>
                    <p class="upload-desc">
                        Publica un artículo para compartir tus ideas y experiencias.
                    </p>
                    <form action="RegistrarArticulo.jsp" method="post">
                        <button type="submit" class="upload-btn">
                            Subir Artículo
                        </button>
                    </form>
                </div>

                <div class="upload-card">
                    <div class="upload-icon">🎓</div>
                    <h2>Curso</h2>
                    <p class="upload-desc">
                        Crea un curso completo y enseña a otros paso a paso.
                    </p>
                    <form action="RegistrarCurso.jsp" method="post">
                        <button type="submit" class="upload-btn">
                            Subir Curso
                        </button>
                    </form>
                </div>
            </div>
        </main>
    </div>
</body>
</html>