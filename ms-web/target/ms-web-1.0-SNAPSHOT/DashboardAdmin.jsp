<%@page import="java.util.List"%>
<%@page import="modelo.Administrador"%>
<%@page import="modelo.Usuario"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html> 
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración</title>
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
        List<Usuario> listU = (List) request.getAttribute("listU");
        int n=0;
        
        for (Usuario usr : listU) {
                if(usr.isEstado()){
                    n++;
               } 
        }
    %>
    
    <div class="container">
        <aside class="sidebar">
            <div class="logo">
                <img src="img/logoAdmin.png" width="190" height="150" alt="Logo pagina" />
            </div>
            <nav>
  
                <a href="UsuarioControll?accion=dashboardAdmin" class="menu-item active">Dashboard</a>
                <a href="UsuarioControll?accion=GestionUsuario" class="menu-item">Gestionar Usuarios</a>                </form>
                <a href="DashboardAdmin_GR.jsp" class="menu-item">Gestionar Reportes</a>
                <a href="CerrarSesion"  class="menu-item">Cerrar Sesión</a>                    
            </nav>
        </aside>

        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1>Panel de Administración</h1>
                    <p>Bienvenido, <%=user_login.getPrimer_nombre()%> - Gestiona tu plataforma</p>
                </div>
            </header>

            <!-- SECCIÓN: GESTIONAR USUARIOS -->
            <div id="usuarios-section" class="content-section">
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-icon blue">👥</div>
                        <div class="stat-info">
                            <h3>Total Usuarios</h3>
                            <p><%=listU.size()%></p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon green">✓</div>
                        <div class="stat-info">
                            <h3>Usuarios Activos</h3>
                            <p><%=n%></p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon red">✗</div>
                        <div class="stat-info">
                            <h3>Usuarios Inactivos</h3>
                            <p><%=listU.size()-n%></p>
                        </div>
                    </div>
                </div>

        </main>
    </div>
</body>
</html>