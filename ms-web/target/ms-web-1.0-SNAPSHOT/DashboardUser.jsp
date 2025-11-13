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
        <link rel="stylesheet" href="css/styleUserDash_2.css">
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
                    <div class="menu-item ">Mi aprendizaje</div>
                    <div class="menu-item">Cursos populares</div>
                    <div class="menu-item">Material educativo disponible</div>
                    <div class="menu-item">Mi perfil</div>
                    <div class="menu-item">Opciones</div>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion" class="menu-item ">Cerrar sesión</a>
  
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
                        <button class="add-resource-btn">+ Agregar Recurso Educativo</button>
                        </form>
                        
                    </div>
                </header>

       
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-icon red">📖</div>
                        <div class="stat-info">
                            <h3>Tus libros</h3>
                            <p><%=cantLibros%></p>
                            <button class="add-resource-btn2">Ver</button>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon blue">📝</div>
                        <div class="stat-info">
                            <h3>Tus artículos</h3>
                            <p><%=cantArticulos%></p>
                            <button class="add-resource-btn2">Ver</button>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon yellow">🎓</div>
                        <div class="stat-info">
                            <h3>Tus cursos</h3>
                            <p><%=cantCursos%></p>
                            <button class="add-resource-btn2">Ver</button>
                        </div>
                    </div>

                </div>

            </main>
        </div>
    </body>
</html>
