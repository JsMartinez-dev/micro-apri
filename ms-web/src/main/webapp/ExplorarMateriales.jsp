<%@page import="dto.DtoMatEducativo"%>
%@page import="dto.DtoMatEducativo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.Gson"%>
<%@ page import="java.net.URLEncoder" %>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Explorar Materiales - Sistema Educativo</title>
    
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/styleUserDash.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
    
    <style>
        .explorar-container {
            padding: 20px;
            max-width: 1400px;
            margin: 0 auto;
        }
        
        .page-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 12px;
            margin-bottom: 30px;
        }
        
        .page-header h1 {
            margin: 0 0 10px 0;
            font-size: 28px;
        }
        
        .page-header p {
            margin: 0;
            opacity: 0.9;
        }
        
        .stats-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            color: white;
            padding: 25px;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        
        .stat-card.libros { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .stat-card.articulos { background: linear-gradient(135deg, #48bb78 0%, #38a169 100%); }
        .stat-card.cursos { background: linear-gradient(135deg, #ed8936 0%, #dd6b20 100%); }
        
        .stat-card i {
            font-size: 36px;
            margin-bottom: 10px;
        }
        
        .stat-card h3 {
            margin: 0;
            font-size: 36px;
            font-weight: bold;
        }
        
        .stat-card p {
            margin: 5px 0 0 0;
            font-size: 14px;
            opacity: 0.9;
        }
        .back-btn {
            background: linear-gradient(135deg, #c13737 0%, #a02929 100%);
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            width: 100%;
            margin-top: 20px;
            transition: all 0.3s;
        }
        
        .table-section {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-bottom: 30px;
        }
        
        .table-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }
        
        .table-title {
            font-size: 22px;
            font-weight: 600;
            color: #333;
            margin: 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .table-title i {
            font-size: 24px;
        }
        
        .table-title.libros { color: #667eea; }
        .table-title.articulos { color: #48bb78; }
        .table-title.cursos { color: #ed8936; }
        
        .table-info {
            font-size: 14px;
            color: #666;
            font-weight: 500;
        }
        
        .search-bar {
            margin-bottom: 20px;
        }
        
        .search-input {
            width: 100%;
            padding: 12px 20px;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            font-size: 15px;
            font-family: 'Inter', sans-serif;
            transition: all 0.3s ease;
        }
        
        .search-input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        thead {
            background-color: #f8f9fa;
        }
        
        thead th {
            padding: 12px 15px;
            text-align: left;
            font-weight: 600;
            color: #495057;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            border-bottom: 2px solid #dee2e6;
        }
        
        tbody tr {
            border-bottom: 1px solid #e9ecef;
            transition: background-color 0.2s ease;
        }
        
        tbody tr:hover {
            background-color: #f8f9fa;
        }
        
        tbody td {
            padding: 15px;
            color: #495057;
            font-size: 14px;
            vertical-align: middle;
        }
        
        .descripcion-cell {
            max-width: 350px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        
        .type-badge {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .badge-libro {
            background-color: #e3e8ff;
            color: #667eea;
        }
        
        .badge-articulo {
            background-color: #c6f6d5;
            color: #48bb78;
        }
        
        .badge-curso {
            background-color: #fed7d7;
            color: #ed8936;
        }
        
        .action-btn {
            display: inline-flex;
            align-items: center;
            gap: 5px;
            padding: 6px 12px;
            margin: 0 3px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.2s ease;
            border: none;
            cursor: pointer;
        }
        
        .view-btn {
            background-color: #e3e8ff;
            color: #667eea;
        }
        
        .view-btn:hover {
            background-color: #d1d8ff;
            transform: translateY(-1px);
        }
        
        .download-btn {
            background-color: #c6f6d5;
            color: #48bb78;
        }
        
        .download-btn:hover {
            background-color: #9ae6b4;
            transform: translateY(-1px);
        }
        
        .empty-state {
            text-align: center;
            padding: 40px 20px;
            color: #718096;
        }
        
        .empty-state i {
            font-size: 48px;
            opacity: 0.3;
            margin-bottom: 15px;
        }
        
        .empty-state p {
            margin: 0;
            font-size: 16px;
        }
        
        @media (max-width: 768px) {
            .table-section {
                overflow-x: auto;
            }
            
            table {
                min-width: 800px;
            }
            
            .action-btn {
                padding: 5px 8px;
                font-size: 11px;
            }
        }
    </style>
</head>
<body>
    <%
        String usuarioJson = request.getParameter("usuario");
        String listaJson = request.getParameter("lista");
        
        System.out.println("usuario: "+usuarioJson);
        System.out.println("ListajSON: "+listaJson);
        
        Gson gson = new Gson();
        
        DtoUsuarioLogin dtoUser = gson.fromJson(usuarioJson, DtoUsuarioLogin.class);
        
        Type tipoLista = new TypeToken<List<DtoMatEducativo>>(){}.getType();
        
        List<DtoMatEducativo> listaMat = gson.fromJson(listaJson, tipoLista);
        
        HttpSession sesion = request.getSession();
        sesion.setAttribute("usuario", dtoUser);
        sesion.setAttribute("listaMat", listaMat);
        
        // Separar materiales por tipo
        List<DtoMatEducativo> libros = new ArrayList<>();
        List<DtoMatEducativo> articulos = new ArrayList<>();
        List<DtoMatEducativo> cursos = new ArrayList<>();
        
        for (DtoMatEducativo mat : listaMat) {
            if (mat.tipo().equals("libro")) {
                libros.add(mat);
            } else if (mat.tipo().equals("articulo")) {
                articulos.add(mat);
            } else {
                cursos.add(mat);
            }
        }
    %>
    
    <div class="explorar-container">
        <!-- Header de la página -->
        <div class="page-header">
            <h1><i class="fa-solid fa-book-open"></i> Explorar Materiales Educativos</h1>
            <p>Descubre y gestiona todos los recursos disponibles en la plataforma</p>
        </div>
        
        <!-- Tarjetas de estadísticas -->
        <div class="stats-cards">
            <div class="stat-card libros">
                <i class="fa-solid fa-book-bookmark"></i>
                <h3><%=libros.size()%></h3>
                <p>Libros Disponibles</p>
            </div>
            <div class="stat-card articulos">
                <i class="fa-solid fa-newspaper"></i>
                <h3><%=articulos.size()%></h3>
                <p>Artículos Disponibles</p>
            </div>
            <div class="stat-card cursos">
                <i class="fa-solid fa-graduation-cap"></i>
                <h3><%=cursos.size()%></h3>
                <p>Cursos Disponibles</p>
            </div>
        </div>
        
        <!-- TABLA DE LIBROS -->
        <div class="table-section">
            <div class="table-header">
                <h2 class="table-title libros">
                    <i class="fa-solid fa-book-bookmark"></i> Libros
                </h2>
                <span class="table-info"><%=libros.size()%> libros</span>
            </div>
            
            <div class="search-bar">
                <input type="text" id="buscarLibros" placeholder="Buscar libros por título..." class="search-input">
            </div>
            
            <% if (libros.isEmpty()) { %>
                <div class="empty-state">
                    <i class="fa-solid fa-book"></i>
                    <p>No hay libros disponibles en este momento</p>
                </div>
            <% } else { %>
                <table id="tablaLibros">
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
                        <% for (DtoMatEducativo libro : libros) { %>
                        <tr>
                            <td><%=libro.id_materialEducativo()%></td>
                            <td><strong><%=libro.nombre()%></strong></td>
                            <td>
                                <span class="type-badge badge-libro">
                                    <i class="fa-solid fa-book-bookmark"></i> Libro
                                </span>
                            </td>
                            <td class="descripcion-cell"><%=libro.descripcion()%></td>
                            <td>
                                <a href="#" class="action-btn view-btn">
                                    <i class="fa-solid fa-eye"></i> Ver
                                </a>
                                <a href="#" class="action-btn download-btn">
                                    <i class="fa-solid fa-download"></i> Descargar
                                </a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
        
        <!-- TABLA DE ARTÍCULOS -->
        <div class="table-section">
            <div class="table-header">
                <h2 class="table-title articulos">
                    <i class="fa-solid fa-newspaper"></i> Artículos
                </h2>
                <span class="table-info"><%=articulos.size()%> artículos</span>
            </div>
            
            <div class="search-bar">
                <input type="text" id="buscarArticulos" placeholder="Buscar artículos por título..." class="search-input">
            </div>
            
            <% if (articulos.isEmpty()) { %>
                <div class="empty-state">
                    <i class="fa-solid fa-newspaper"></i>
                    <p>No hay artículos disponibles en este momento</p>
                </div>
            <% } else { %>
                <table id="tablaArticulos">
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
                        <% for (DtoMatEducativo articulo : articulos) { %>
                        <tr>
                            <td><%=articulo.id_materialEducativo()%></td>
                            <td><strong><%=articulo.nombre()%></strong></td>
                            <td>
                                <span class="type-badge badge-articulo">
                                    <i class="fa-solid fa-newspaper"></i> Artículo
                                </span>
                            </td>
                            <td class="descripcion-cell"><%=articulo.descripcion()%></td>
                            <td>
                                <a href="#" class="action-btn view-btn">
                                    <i class="fa-solid fa-eye"></i> Ver
                                </a>
                                <a href="#" class="action-btn download-btn">
                                    <i class="fa-solid fa-download"></i> Descargar
                                </a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
        
        <!-- TABLA DE CURSOS -->
        <div class="table-section">
            <div class="table-header">
                <h2 class="table-title cursos">
                    <i class="fa-solid fa-graduation-cap"></i> Cursos
                </h2>
                <span class="table-info"><%=cursos.size()%> cursos</span>
            </div>
            
            <div class="search-bar">
                <input type="text" id="buscarCursos" placeholder="Buscar cursos por título..." class="search-input">
            </div>
            
            <% if (cursos.isEmpty()) { %>
                <div class="empty-state">
                    <i class="fa-solid fa-graduation-cap"></i>
                    <p>No hay cursos disponibles en este momento</p>
                </div>
            <% } else { %>
                <table id="tablaCursos">
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
                        <% for (DtoMatEducativo curso : cursos) { %>
                        <tr>
                            <td><%=curso.id_materialEducativo()%></td>
                            <td><strong><%=curso.nombre()%></strong></td>
                            <td>
                                <span class="type-badge badge-curso">
                                    <i class="fa-solid fa-graduation-cap"></i> Curso
                                </span>
                            </td>
                            <td class="descripcion-cell"><%=curso.descripcion()%></td>
                            <td>
                                <a href="#" class="action-btn view-btn">
                                    <i class="fa-solid fa-eye"></i> Ver
                                </a>
                                <a href="#" class="action-btn download-btn">
                                    <i class="fa-solid fa-download"></i> Descargar
                                </a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
        <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser">
                        <button class="back-btn">
                            <i class="fa-solid fa-arrow-left"></i>
                            Regresar al Dashboard
                        </button>
        </a>
    </div>
    
    <!-- Scripts de búsqueda -->
    <script>
        // Función genérica de búsqueda
        function setupSearch(inputId, tableId) {
            document.getElementById(inputId).addEventListener("keyup", function() {
                let filtro = this.value.toLowerCase();
                let tabla = document.querySelector("#" + tableId + " tbody");
                
                if (tabla) {
                    let filas = tabla.querySelectorAll("tr");
                    
                    filas.forEach(fila => {
                        let titulo = fila.children[1].textContent.toLowerCase();
                        let descripcion = fila.children[3].textContent.toLowerCase();
                        
                        let coincide = titulo.includes(filtro) || descripcion.includes(filtro);
                        
                        fila.style.display = coincide ? "" : "none";
                    });
                }
            });
        }
        
        // Inicializar búsquedas
        setupSearch("buscarLibros", "tablaLibros");
        setupSearch("buscarArticulos", "tablaArticulos");
        setupSearch("buscarCursos", "tablaCursos");
    </script>
</body>
</html>