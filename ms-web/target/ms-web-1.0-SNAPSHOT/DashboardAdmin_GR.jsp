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
                <a href="UsuarioControll?accion=GestionUsuario" class="menu-item">Gestionar Usuarios</a>
                <a href="DashboardAdmin_GR.jsp" class="menu-item active">Gestionar Reportes</a>
                <a href="CerrarSesion" class="menu-item">Cerrar Sesión</a>
            </nav>
        </aside>

        <main class="main-content">
            <div id="reportes-section" class="content-section">
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-icon yellow">📋</div>
                        <div class="stat-info">
                            <h3>Total Reportes</h3>
                            <p>87</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon red">⏳</div>
                        <div class="stat-info">
                            <h3>Pendientes</h3>
                            <p>23</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon green">✓</div>
                        <div class="stat-info">
                            <h3>Resueltos</h3>
                            <p>64</p>
                        </div>
                    </div>
                </div>

                <div class="search-bar">
                    <input type="text" class="search-input" placeholder="Buscar reportes por usuario, tipo o descripción...">
                    <button class="search-btn">🔍 Buscar</button>
                </div>

                <div class="table-container">
                    <div class="table-header">
                        <h2 class="table-title">Lista de Reportes</h2>
                        <span class="table-info">Mostrando 10 de 87 reportes</span>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Usuario</th>
                                <th>Tipo de Reporte</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>#R001</td>
                                <td>Juan Pérez</td>
                                <td>Contenido inapropiado</td>
                                <td>06/10/2025</td>
                                <td><span class="status-badge status-pending">Pendiente</span></td>
                                <td>
                                    <a href="VerReporte?id=1" class="action-btn view-btn">👁️ Ver</a>
                                    <a href="EliminarReporte?id=1" class="action-btn delete-btn" onclick="return confirm('¿Estás seguro de eliminar este reporte?')">🗑️ Eliminar</a>
                                </td>
                            </tr>
                            <tr>
                                <td>#R002</td>
                                <td>María López</td>
                                <td>Error técnico</td>
                                <td>05/10/2025</td>
                                <td><span class="status-badge status-resolved">Resuelto</span></td>
                                <td>
                                    <a href="VerReporte?id=2" class="action-btn view-btn">👁️ Ver</a>
                                    <a href="EliminarReporte?id=2" class="action-btn delete-btn" onclick="return confirm('¿Estás seguro de eliminar este reporte?')">🗑️ Eliminar</a>
                                </td>
                            </tr>
                            <tr>
                                <td>#R003</td>
                                <td>Carlos Rodríguez</td>
                                <td>Spam</td>
                                <td>04/10/2025</td>
                                <td><span class="status-badge status-pending">Pendiente</span></td>
                                <td>
                                    <a href="VerReporte?id=3" class="action-btn view-btn">👁️ Ver</a>
                                    <a href="EliminarReporte?id=3" class="action-btn delete-btn" onclick="return confirm('¿Estás seguro de eliminar este reporte?')">🗑️ Eliminar</a>
                                </td>
                            </tr>
                            <tr>
                                <td>#R004</td>
                                <td>Ana Martínez</td>
                                <td>Sugerencia</td>
                                <td>03/10/2025</td>
                                <td><span class="status-badge status-resolved">Resuelto</span></td>
                                <td>
                                    <a href="VerReporte?id=4" class="action-btn view-btn">👁️ Ver</a>
                                    <a href="EliminarReporte?id=4" class="action-btn delete-btn" onclick="return confirm('¿Estás seguro de eliminar este reporte?')">🗑️ Eliminar</a>
                                </td>
                            </tr>
                            <tr>
                                <td>#R005</td>
                                <td>Pedro Gómez</td>
                                <td>Plagio</td>
                                <td>02/10/2025</td>
                                <td><span class="status-badge status-pending">Pendiente</span></td>
                                <td>
                                    <a href="VerReporte?id=5" class="action-btn view-btn">👁️ Ver</a>
                                    <a href="EliminarReporte?id=5" class="action-btn delete-btn" onclick="return confirm('¿Estás seguro de eliminar este reporte?')">🗑️ Eliminar</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>   

         </main>
    </div>
     
    
</body>
</html>