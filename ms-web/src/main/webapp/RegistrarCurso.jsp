<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Curso</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/styleUserDash1.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
           integrity="sha512-SnH5WK+bZxgIk9lKMdQXWf5fL8pT..." 
           crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <%
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
                <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion?accion=user" class="menu-item">
                    Cerrar sesión
                </a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1>Registrar Curso <i class="fa-solid fa-graduation-cap"></i></h1>
                    <p>Crea un nuevo curso para la comunidad</p>
                </div>
                <div class="header-right">
                    <a href="ElegirMaterial.jsp" 
                       class="add-cancel-btn" 
                       style="text-decoration: none;">
                        <i class="fa-solid fa-left-long"></i> Cancelar
                    </a>
                </div>
            </header>

            <h2 class="page-title">Información del Curso</h2>

            <form action="<%=Ruta.MS_MATEDU_URL%>/CursoControll?accion=register&idUsuario=<%=userDto.id_persona()%>" 
                  method="POST" 
                  class="form-container">

                <div class="form-group">
                    <label for="nombre">Nombre del Curso *</label>
                    <input type="text" 
                           id="nombre" 
                           name="nombre" 
                           placeholder="Ej: Introducción a la Programación en Java"
                           required>
                </div>

                <div class="form-group">
                    <label for="categoria">Categoría *</label>
                    <select id="categoria" name="categoria" required>
                        <option value="" disabled selected>Selecciona una categoría</option>
                        <option value="Fisica">Física</option>
                        <option value="Matematicas">Matemáticas</option>
                        <option value="Ciencias">Ciencias</option>
                        <option value="Programacion">Programación</option>
                        <option value="Informatica">Informática</option>
                        <option value="Electronica">Electrónica</option>
                        <option value="Ingenieria">Ingeniería</option>
                        <option value="Historia">Historia</option>
                        <option value="Medicina">Medicina</option>
                        <option value="Biologia">Biología</option>
                        <option value="Quimica">Química</option>
                        <option value="Filosofia">Filosofía</option>
                        <option value="Economia">Economía</option>
                        <option value="Otros">Otros</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción *</label>
                    <textarea id="descripcion" 
                              name="descripcion" 
                              rows="5" 
                              placeholder="Describe el contenido del curso, objetivos de aprendizaje y qué aprenderán los estudiantes..."
                              required></textarea>
                </div>

                <div class="form-group">
                    <label for="año_publicacion">Fecha de Publicación *</label>
                    <input type="date" 
                           id="año_publicacion" 
                           name="año_publicacion" 
                           required>
                </div>
                <div class="form-group">
                    <label for="duracion">Duración (en horas) *</label>
                    <input type="number" 
                           id="duracion" 
                           name="duracion" 
                           min="1" 
                           step="0.5"
                           placeholder="Ej: 20"
                           required>
                </div>

                <div class="form-group">
                    <label for="tipo">Tipo de Curso *</label>
                    <select id="nivel" name="nivel" required>
                        <option value="" disabled selected>Selecciona el tipo</option>
                        <option value="Basico">Básico</option>
                        <option value="Intermedio">Intermedio</option>
                        <option value="Avanzado">Avanzado</option>
                        <option value="Especializado">Especializado</option>
                    </select>
                </div>
                
                <button type="submit" class="submit-btn">
                    Crear Curso
                </button>
            </form>
        </main>
    </div>

    <%
        String exito = request.getParameter("mensaje");
        String fallo = request.getParameter("error");
        String idCurso = request.getParameter("idCurso");
        String titulo = null, text = null, icon = null;

        if (exito != null && idCurso != null) {
            titulo = "¡Éxito!";
            text = exito;
            icon = "success";
        } else if (fallo != null) {
            titulo = "Error";
            text = fallo;
            icon = "error";
        }

        if (titulo != null) {
    %>
        <script>
            Swal.fire({
                title: '<%= titulo %>',
                text: '<%= text %>',
                icon: '<%= icon %>',
                confirmButtonText: 'Aceptar',
                confirmButtonColor: '#405370'
            }).then((result) => {
                if (result.isConfirmed && '<%= icon %>' === 'success') {
                    window.location.href = '<%=Ruta.MS_MATEDU_URL%>/ModuloControll?accion=listarModulos&idCurso=<%=idCurso%>';
                }
            });
        </script>
    <%}%>
</body>
</html>