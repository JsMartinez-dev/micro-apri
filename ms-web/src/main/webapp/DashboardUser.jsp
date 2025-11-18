<%@page import="java.net.URLEncoder"%>
<%@page import="utilidad.Ruta"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="dto.DtoMatEducativo"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Articulo"%>
<%@page import="modelo.Libro"%>
<%@page import="modelo.MaterialEducativo"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard Usuarios</title>
        <link rel="stylesheet" href="css/styleUserDash.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>

       
    
    <body>
        
        <%
            String usuarioJson = request.getParameter("usuario");
            String listaJson = request.getParameter("lista");
            
            System.out.println("Usuario: "+usuarioJson);
            System.out.println("ListajSON: "+listaJson);
            
            Gson gson = new Gson();
            
            DtoUsuarioLogin dtoUser = gson.fromJson(usuarioJson,DtoUsuarioLogin.class);
            
            Type tipoLista = new TypeToken<List<DtoMatEducativo>>(){}.getType();
            
            List<DtoMatEducativo> listaMat = gson.fromJson(listaJson,tipoLista);
            
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", dtoUser);
            sesion.setAttribute("listaMat", listaMat); 
            
            int cantLibros=0,cantArticulos=0,cantCursos=0;
            
            for (DtoMatEducativo mat : listaMat){
                    if(mat.tipo().equals("libro")){
                        cantLibros++;
                    }else if(mat.tipo().equals("articulo")){
                        cantArticulos++;     
                    }else{
                        cantCursos++; 
            }
                }

        %>
        
        <div class="container">
  
            <aside class="sidebar">
                <div class="logo">
                        <img src="img/Logo2.png" width="190" height="150" alt="Logo pagina" />
                </div>
                <nav>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser"  class="menu-item active">Dashboard</a>
                    <div class="menu-item">Mi aprendizaje</div>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=matEducativos"  class="menu-item ">Material educativo disponible</a>                    <a href="MiPerfil.jsp"  class="menu-item ">Mi perfil</a>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion?accion=user" class="menu-item ">Cerrar sesión</a>
  
                </nav>
            </aside>

            <main class="main-content">
                <header class="header">
                    <div class="welcome-text">
                        <h1>Bienvenido, <%=dtoUser.primerNombre()%></h1>
                        <p>A tu lugar para compartir conocimiento</p>
                    </div>
                    <div class="header-right">
                        <form action="ElegirMaterial.jsp">
                        <button class="add-resource-btn"><i class="fa-solid fa-circle-plus"></i> Agregar Recurso Educativo</button>
                        </form>
                        
                    </div>
                </header>

       
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-icon red"><i class="fa-solid fa-book-bookmark"></i></div>
                        <div class="stat-info">
                            <h3>Tus libros</h3>
                            <p><%=cantLibros%></p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon blue"><i class="fa-solid fa-newspaper"></i></div>
                        <div class="stat-info">
                            <h3>Tus artículos</h3>
                            <p><%=cantArticulos%></p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon yellow"><i class="fa-solid fa-graduation-cap"></i></div>
                        <div class="stat-info">
                            <h3>Tus cursos</h3>
                            <p><%=cantCursos%></p>
                        </div>
                    </div>

                </div>

                <div class="search-bar">
                    <input type="text" id="buscar" placeholder="Buscar por título o tipo" class="search-input">
                </div>

                <div class="table-container">
                    <div class="table-header">
                        <h2 class="table-title">Mis Materiales Educativos <i class="fa-solid fa-book"></i></h2>
                        <span class="table-info">Mostrando <%=listaMat.size()%> materiales</span>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Título</th>
                                <th>Tipo</th>
                                <th>Descripción</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            for (DtoMatEducativo mat : listaMat) {
                                String tipoIcon = "";
                                String tipoBadge = "";
                                
                                if(mat.tipo().equals("libro")){
                                    tipoIcon = "fa-book-bookmark";
                                    tipoBadge = "badge-libro";
                                } else if(mat.tipo().equals("articulo")){
                                    tipoIcon = "fa-newspaper";
                                    tipoBadge = "badge-articulo";
                                } else {
                                    tipoIcon = "fa-graduation-cap";
                                    tipoBadge = "badge-curso";
                                }
                            %>
                            <tr>
                                <td><%=mat.id_materialEducativo()%></td>
                                <td><%=mat.nombre()%></td>
                                <td>
                                    <span class="type-badge <%=tipoBadge%>">
                                        <i class="fa-solid <%=tipoIcon%>"></i> 
                                        <%=mat.tipo().substring(0,1).toUpperCase() + mat.tipo().substring(1)%>
                                    </span>
                                </td>
                                <td class="descripcion-cell"><%=mat.descripcion()%></td>
             <td>
    <% if(mat.tipo().equals("curso")) { %>
     <a href="VerResenas.jsp?idMaterial=<%=mat.id_materialEducativo()%>&nombreMaterial=<%=URLEncoder.encode(mat.nombre(), "UTF-8")%>" 
         class="action-btn reviews-btn">
        <i class="fa-solid fa-star"></i> Reseñas
    </a>
    <a href="<%=Ruta.MS_MATEDU_URL%>/CursoControll?accion=eliminar&id=<%=mat.id_materialEducativo()%>" 
       class="action-btn delete-btn" 
       onclick="return confirm('¿Estás seguro de eliminar este material?')">
        <i class="fa-solid fa-trash"></i> Eliminar
    </a>
    <% } else if(mat.tipo().equals("libro")) { %>
     <a href="VerResenas.jsp?idMaterial=<%=mat.id_materialEducativo()%>&nombreMaterial=<%=URLEncoder.encode(mat.nombre(), "UTF-8")%>" 
         class="action-btn reviews-btn">
        <i class="fa-solid fa-star"></i> Reseñas
    </a>
    <a href="<%=Ruta.MS_MATEDU_URL%>/LibroControll?accion=descargar&id=<%=mat.id_materialEducativo()%>" 
       class="action-btn view-btn">
        <i class="fa-solid fa-download"></i> Descargar
    </a>
    <a href="<%=Ruta.MS_MATEDU_URL%>/LibroControll?accion=eliminar&id=<%=mat.id_materialEducativo()%>" 
       class="action-btn delete-btn" 
       onclick="return confirm('¿Estás seguro de eliminar este material?')">
        <i class="fa-solid fa-trash"></i> Eliminar
    </a>
    <% } else { %>
     <a href="VerResenas.jsp?idMaterial=<%=mat.id_materialEducativo()%>&nombreMaterial=<%=URLEncoder.encode(mat.nombre(), "UTF-8")%>" 
         class="action-btn reviews-btn">
        <i class="fa-solid fa-star"></i> Reseñas
    </a>
    <a href="<%=Ruta.MS_MATEDU_URL%>/ArticuloControll?accion=descargar&id=<%=mat.id_materialEducativo()%>" 
       class="action-btn view-btn">
        <i class="fa-solid fa-download"></i> Descargar
    </a>
    <a href="<%=Ruta.MS_MATEDU_URL%>/ArticuloControll?accion=eliminar&id=<%=mat.id_materialEducativo()%>" 
       class="action-btn delete-btn" 
       onclick="return confirm('¿Estás seguro de eliminar este material?')">
        <i class="fa-solid fa-trash"></i> Eliminar
    </a>
    <% } %>
</td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>

                <script>
                    document.getElementById("buscar").addEventListener("keyup", function() {
                        let filtro = this.value.toLowerCase();
                        let filas = document.querySelectorAll("tbody tr");

                        filas.forEach(fila => {
                            let titulo = fila.children[1].textContent.toLowerCase();
                            let tipo = fila.children[2].textContent.toLowerCase();

                            let coincide = titulo.includes(filtro) || tipo.includes(filtro);

                            fila.style.display = coincide ? "" : "none";
                        });
                    });
                </script>

            </main>
        </div>
    
     <%
    String errorMsg = request.getParameter("error");
    String successMsg = request.getParameter("success");
%>

<% if (errorMsg != null) { %>
    <script>
        Swal.fire({
            title: 'Error',
            text: '<%= errorMsg %>',
            icon: 'error',
            confirmButtonText: 'Aceptar',
            confirmButtonColor: '#e53e3e'
        });
    </script>
<% } %>

<% if (successMsg != null) { %>
    <script>
        Swal.fire({
            title: '¡Éxito!',
            text: '<%= successMsg %>',
            icon: 'success',
            confirmButtonText: 'Aceptar',
            confirmButtonColor: '#48bb78',
            timer: 2000
        });
    </script>
    <% } %>
       
    </body>
</html>