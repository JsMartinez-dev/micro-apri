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
    <link rel="stylesheet" href="css/newcss.css.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />


</head>
<body>
    <%
            String usuarioJson = request.getParameter("admin");
            String listaJson = request.getParameter("lista");
            
            System.out.println("Admin "+usuarioJson);
            System.out.println("ListajSON: "+listaJson);
            
            Gson gson = new Gson();
            
            DtoAdminLogin dtoUser = gson.fromJson(usuarioJson,DtoAdminLogin.class);
            
            Type tipoLista = new TypeToken<List<DtoUsuarioLogin>>(){}.getType();
            
            List<DtoUsuarioLogin> listaU = gson.fromJson(listaJson,tipoLista);
            
            HttpSession sesion = request.getSession();
            sesion.setAttribute("admin", dtoUser);
            sesion.setAttribute("listaU", listaU); 
       
            String success = request.getParameter("success");
        
        
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
                        <p>Bienvenido, <%=dtoUser.primerNombre()%> - Gestiona tu plataforma</p>
                    </div>
                </header>
                <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardAdmin" class="btn-salir">
                    <i class="fa-solid fa-left-long"></i> Regresar
                </a>
            </div>

            <% if(success != null && success.equals("eliminado")){ %>
                <div class="alert alert-success">
                    <i class="fa-regular fa-circle-check"></i> Usuario eliminado exitosamente
                </div>
            <% } %>
                <input type="hidden" name="accion" value="buscarUsuario">
                <div class="search-bar">
                    <input type="text" id="buscar" placeholder="Buscar por nombre o correo" class="search-input">
                </div>  
                <script>
                    document.getElementById("buscar").addEventListener("keyup", function() {
                        let filtro = this.value.toLowerCase();
                        let filas = document.querySelectorAll("tbody tr");

                        filas.forEach(fila => {
                            let nombre = fila.children[1].textContent.toLowerCase();
                            let correo = fila.children[2].textContent.toLowerCase();

                            let coincide = nombre.includes(filtro) || correo.includes(filtro);

                            fila.style.display = coincide ? "" : "none";
                        });
                    });
                </script>



            <div class="table-container">
                <div class="table-header">
                    <h2 class="table-title">Lista de Usuarios <i class="fa-solid fa-users"></i></h2>
                    <span class="table-info">Mostrando <%=listaU.size()%> usuarios</span>
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
                        for (DtoUsuarioLogin user : listaU) {%>
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