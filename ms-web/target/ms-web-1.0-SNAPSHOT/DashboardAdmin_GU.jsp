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
    <link rel="stylesheet" href="css/styleDashboardAdmin_1.css"/>

</head>
<body>
    <%
        // Validamos que el usuario llegó correctamente
        HttpSession sesion = request.getSession(false);
        if(sesion == null || sesion.getAttribute("usuario") == null){
            response.sendRedirect("InicioSesionUsuario.jsp");
            return;
        }
        
        Administrador user_login = (Administrador) sesion.getAttribute("usuario");
    %>
    
    <div class="container">
        <aside class="sidebar">
            <div class="logo">
                <img src="img/logoAdmin.png" width="190" height="150" alt="Logo pagina" />
            </div>
            <nav>
                <a href="UsuarioControll?accion=dashboardAdmin" class="menu-item">Dashboard</a>
                <a href="UsuarioControll?accion=GestionUsuario" class="menu-item active">Gestionar Usuarios</a>               
                <a href="DashboardAdmin_GR.jsp" class="menu-item">Gestionar Reportes</a>
                <a href="CerrarSesion" class="menu-item">Cerrar Sesión</a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1>Panel de Administración</h1>
                    <p>Bienvenido, <%=user_login.getPrimer_nombre()%> - Gestiona tu plataforma</p>
                </div>
            </header>

               <div class="search-bar">
                    <input type="text" class="search-input" placeholder="Buscar por nombre, email o ID de usuario...">
                    <button class="search-btn">🔍 Buscar</button>
                </div>

                <%
                    List<Usuario> listaUsuarios = (List) request.getAttribute("listU");
                %>
                <div class="table-container">
                    <div class="table-header">
                        <h2 class="table-title">Lista de Usuarios</h2>
                        <span class="table-info">Mostrando <%=listaUsuarios.size()%> usuarios</span>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre Completo</th>
                                <th>Email</th>
                                <th>Fecha de Registro</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>

                            <%
                            for (Usuario user : listaUsuarios) {%>
                            <tr>
                                
                                <td><%=user.getId_persona()%></td>
                                <td><%=user.getPrimer_nombre()%></td>
                                <td><%=user.getCorreo()%></td>
                                <td><%=user.getFecha_registro()%></td>                                
                                <%if(!user.isEstado()){%>
                                 <td><span class="status-badge status-inactive">Inactivo</span></td>
                                <%}else{%>
                                 <td><span class="status-badge status-active">Activo</span></td>
                                <%}%>
                                <td>
                                    <a href="EditarUsuario?id=1" class="action-btn edit-btn">✏️ Editar</a>
                                    <a href="CerrarSesion" class="action-btn delete-btn" onclick="return confirm('¿Estás seguro de eliminar este usuario?')">🗑️ Eliminar</a>
                                </td>
                            </tr>
                            <%}%>
                                
                        </tbody>
                    </table>
                </div>
            </div>

        </main>
    </div>
</body>
</html>