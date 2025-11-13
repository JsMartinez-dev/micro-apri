<%-- 
    Document   : RegistrarLibro
    Created on : 17 oct 2025, 9:23:14 a.m.
    Author     : ACER-A315-59
--%>

<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
         <link rel="stylesheet" href="css/styleUserDash_2.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    </head>
    <body>
        
        <%
            HttpSession sesion = request.getSession(false);
            if(sesion ==null || sesion.getAttribute("usuario") ==null){
                response.sendRedirect("InicioSesionUsuario.jsp");
                return;
            }
            
            DtoUsuarioLogin userDto = (DtoUsuarioLogin) sesion.getAttribute("usuario");

        %>
        
        <div class="container">
  
            <aside class="sidebar">
                <div class="logo">
                        <img src="img/Logo2.png" width="190" height="150" alt="Logo pagina" />
                </div>
                <nav>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser"  class="menu-item active" >Dashboard</a>
                    <div class="menu-item ">Mi aprendizaje</div>
                    <div class="menu-item">Cursos populares</div>
                    <div class="menu-item">Material educativo disponible</div>
                    <div class="menu-item">Mi perfil</div>
                    <div class="menu-item">Opciones</div>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion"  class="menu-item" >Cerrar sesión </a>
  
                </nav>
            </aside>

            <main class="main-content">
                <header class="header">
                    <div class="header-right">
                     <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser" class="add-cancel-btn" style="display: inline-block; text-decoration: none;">Cancelar</a>
                    </div>
                </header>

                <h1 style="text-align: center; padding: 20px">Digita información del libro</h1>
                <form action="<%=Ruta.MS_MATEDU_URL%>/LibroControll?accion=register&idUsuario=<%=userDto.id_persona()%>" method="POST" enctype="multipart/form-data" class="form-container">

                <div class="form-group">
                    <label for="nombreLibro">Nombre</label>
                    <input type="text" id="nombreLibro" name="nombreLibro" required>
                </div>

                <div class="form-group">
                    <label for="Descripcion">Descripción</label>
                    <textarea id="Descripcion" name="Descripcion" rows="5" cols="30" placeholder="Escribe una breve descripción del libro..." required></textarea>
                </div>

                <div class="form-group">
                    <label for="Categoria">Categoría</label>
                    <select id="Categoria" name="Categoria" required>
                        <option value="" disabled selected>Selecciona una categoría</option>
                        <option value="Fisica">Física</option>
                        <option value="Matematicas">Matemáticas</option>
                        <option value="Ciencias">Ciencias</option>
                        <option value="Programacion">Programación</option>
                        <option value="Informatica">Informática</option>
                        <option value="Electronica">Electrónica</option>
                        <option value="Ingenieria">Ingeniería</option>
                        <option value="Historia">Historia</option>
                        <option value="Otros">Otros</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="edicion">Edición</label>
                    <input type="number" id="edicion" name="edicion" min="1" required>
                </div>

                <div class="form-group">
                    <label for="AñoPublicacion">Año de publicación</label>
                    <input type="date" id="AñoPublicacion" name="AñoPublicacion" required>
                </div>

                <div class="form-group">
                    <label for="Editorial">Editorial</label>
                    <input type="text" id="Editorial" name="Editorial" required>
                </div>

                <div class="form-group">
                    <label for="cantPaginas">Cantidad de páginas</label>
                    <input type="number" id="cantPaginas" name="cantPaginas" min="1" required>
                </div>
                
                <div class="form-group">
                    <label for="archivoPDF">Subir archivo (PDF)</label>
                    <input type="file" id="archivoPDF" name="archivoPDF"  required>
                </div>
                
                <button type="submit" class="submit-btn">Subir Libro</button>
            </form>

         </main>
        <%
            String exito = request.getParameter("mensaje");
            String fallo = request.getParameter("error");
            String titulo = null, text = null, icon = null;

            if(exito != null){
                titulo = "Éxito";
                text = exito;
                icon = "success";
            } else if(fallo != null){
                titulo = "Error";
                text = fallo;
                icon = "error";
            }

            if(titulo != null){
        %>
            <script>
                Swal.fire({
                    title: '<%= titulo %>',
                    text: '<%= text %>',
                    icon: '<%= icon %>',
                    confirmButtonText: 'Aceptar'
                });
            </script> 
        <%}%>


        </div>
    </body>
</html>
