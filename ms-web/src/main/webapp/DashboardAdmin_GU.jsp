<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="dto.DtoAdminLogin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Administrador"%>
<%@page import="modelo.Usuario"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html> 
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración-Gestion usuarios</title>
    <link rel="stylesheet" href="css/styleDashboardAdmin.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
      integrity="sha512-SnH5WK+bZxgIk9lKMdQXWf5fL8pT..." 
      crossorigin="anonymous" referrerpolicy="no-referrer" />


</head>
<body>
    <%
        HttpSession sesion = request.getSession(false);
        if(sesion == null || sesion.getAttribute("admin") == null){
            response.sendRedirect("InicioSesionUsuario.jsp");
            return;
        }
        
        List<DtoUsuarioLogin> listaUsuarios;
        listaUsuarios = (List) sesion.getAttribute("listaU");

        DtoAdminLogin user_login = (DtoAdminLogin) sesion.getAttribute("admin");
        String success = request.getParameter("success");
        
        if(success != null && success.equals("eliminado")){
            String listaJson = request.getParameter("listaJson2");
            if(listaJson != null && !listaJson.isEmpty()){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<DtoUsuarioLogin>>(){}.getType();    
                listaUsuarios = gson.fromJson(listaJson, tipoLista);
            }
        }
    %>

    <div class="container">
        <aside class="sidebar">
            <div class="logo">
                <img src="img/logoAdmin.png" width="190" height="150" alt="Logo pagina" />
            </div>
            <nav>
                <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardAdmin" class="menu-item">Dashboard</a>
                
                <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion?accion=admin" class="menu-item">Cerrar Sesión</a>
            </nav>
        </aside>

        <main class="main-content">
            <div class="header-actions">
                <header class="header">
                    <div class="welcome-text">
                        <h1>Gestionar usuarios</h1>
                        <p>Bienvenido, <%=user_login.primerNombre()%> - Gestiona tu plataforma</p>
                    </div>
                </header>
                <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardAdmin" class="btn-salir">
                    <i class="fa-solid fa-left-long"></i> Regresar
                </a>
            </div>

            <!-- Mensaje de éxito -->
            <% if(success != null && success.equals("eliminado")){ %>
                <div class="alert alert-success">
                    <i class="fa-regular fa-circle-check"></i> Usuario eliminado exitosamente
                </div>
            <% } %>

            <div class="search-bar">
                <input type="text" class="search-input" placeholder="Buscar por nombre">
                <button class="search-btn"><i class="fa-solid fa-magnifying-glass"></i></button>
            </div>

            <div class="table-container">
                <div class="table-header">
                    <h2 class="table-title">Lista de Usuarios <i class="fa-solid fa-users"></i></h2>
                    <span class="table-info">Mostrando <%=listaUsuarios.size()%> usuarios</span>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre Completo</th>
                            <th>Email</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        for (DtoUsuarioLogin user : listaUsuarios) {%>
                        <tr>
                            <td><%=user.id_persona()%></td>
                            <td><%=user.primerNombre()%></td>
                            <td><%=user.correo()%></td>
                            <%if(!user.estado()){%>
                             <td><span class="status-badge status-inactive">Inactivo</span></td>
                            <%}else{%>
                             <td><span class="status-badge status-active">Activo</span></td>
                            <%}%>
                            <td>
                                <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=eliminarUsuario&id=<%=user.id_persona()%>" 
                                   class="action-btn delete-btn" 
                                   onclick="return confirm('¿Estás seguro de eliminar este usuario?')">
                                  <i class="fa-solid fa-trash"></i> Eliminar
                                </a>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</body>
</html>