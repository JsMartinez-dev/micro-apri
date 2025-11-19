<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Módulos del Curso</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/styleUserDash.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />
    <style>
        .modulos-stats {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 25px;
            text-align: center;
            margin: 20px 0;
            border: 2px solid #e9ecef;
        }

        .modulos-numero {
            font-size: 3em;
            font-weight: 700;
            color: #405370;
            margin: 10px 0;
        }

        .modulos-container {
            display: grid;
            gap: 20px;
            margin-bottom: 30px;
        }

        .modulo-card {
            background: white;
            border: 2px solid #e9ecef;
            border-radius: 10px;
            padding: 20px;
            margin: 15px 0;
            transition: all 0.3s;
        }

        .modulo-card:hover {
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            transform: translateY(-2px);
        }

        .modulo-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #e9ecef;
            flex-wrap: wrap;
            gap: 10px;
        }

        .modulo-titulo {
            font-weight: 600;
            color: #405370;
            font-size: 18px;
        }

        .modulo-numero-badge {
            background: #667eea;
            color: white;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
        }

        .leccion-item {
            background: #f7fafc;
            border-left: 4px solid #667eea;
            border-radius: 6px;
            padding: 15px;
            margin: 10px 0;
            transition: all 0.2s;
        }

        .leccion-item:hover {
            background: #edf2f7;
            transform: translateX(5px);
        }

        .leccion-nombre {
            font-weight: 600;
            color: #2d3748;
            font-size: 15px;
            margin-bottom: 5px;
        }

        .leccion-descripcion {
            color: #666;
            font-size: 14px;
            margin-bottom: 8px;
            line-height: 1.5;
        }

        .leccion-url {
            display: inline-flex;
            align-items: center;
            gap: 5px;
            padding: 8px 14px;
            background: #667eea;
            color: white;
            border-radius: 5px;
            text-decoration: none;
            font-size: 12px;
            font-weight: 500;
            transition: all 0.3s;
        }

        .leccion-url:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }

        .empty-state {
            text-align: center;
            padding: 40px;
            color: #999;
        }

        .empty-state i {
            font-size: 3em;
            margin-bottom: 15px;
            color: #ddd;
        }

        .loading {
            text-align: center;
            padding: 30px;
            color: #405370;
        }

        .loading i {
            font-size: 2em;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            from { transform: rotate(0deg); }
            to { transform: rotate(360deg); }
        }
    </style>
</head>
<body>
    <%
        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("InicioSesionUsuario.jsp");
            return;
        }
        DtoUsuarioLogin userDto = (DtoUsuarioLogin) sesion.getAttribute("usuario");
        String idCursoParam = request.getParameter("idCurso");
        String nombreCurso = request.getParameter("nombreCurso");
        if (idCursoParam == null) {
            response.sendRedirect("ExplorarMateriales.jsp");
            return;
        }
    %>

    <div class="container">
        <aside class="sidebar">
            <div class="logo">
                <img src="img/Logo2.png" width="190" height="150" alt="Logo" />
            </div>
            <nav>
                <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser" class="menu-item">Dashboard</a>
                <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=matEducativos" class="menu-item active">Materiales</a>
                <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion?accion=user" class="menu-item">Cerrar sesión</a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1><i class="fas fa-graduation-cap"></i> <%= nombreCurso != null ? nombreCurso : "Curso" %></h1>
                    <p>Módulos y lecciones disponibles en este curso</p>
                </div>
                <div class="header-right">
                    <button onclick="history.back()" class="add-cancel-btn">
                        <i class="fa-solid fa-arrow-left"></i> Volver
                    </button>
                </div>
            </header>

            <div class="modulos-stats">
                <h3>Módulos disponibles</h3>
                <div class="modulos-numero" id="modulosNumero">-</div>
                <p id="totalModulos">Cargando...</p>
            </div>

            <div id="modulosContainer" class="modulos-container">
                <div class="loading">
                    <i class="fas fa-spinner"></i>
                    <p>Cargando módulos...</p>
                </div>
            </div>
        </main>
    </div>

    <script>
        const idCurso = <%= idCursoParam %>;
        const idUsuario = <%= userDto.id_persona() %>;

        window.onload = function() {
            cargarModulos();
        };

        async function cargarModulos() {
            try {
                console.log('Cargando módulos para curso ID: ' + idCurso);
                
                const response = await fetch('<%=Ruta.MS_MATEDU_URL%>/CursoControll?accion=obtenerModulos&idCurso=' + idCurso);
                
                console.log('Status: ' + response.status);
                
                if (!response.ok) {
                    const errorData = await response.json();
                    mostrarError(errorData.message || 'Error al cargar los módulos');
                    return;
                }

                const modulos = await response.json();
                console.log('Módulos recibidos:', modulos);
                mostrarModulos(modulos);
                
            } catch (error) {
                console.error('Error al cargar módulos:', error);
                mostrarError('Error de conexión: ' + error.message);
            }
        }

        function mostrarModulos(modulos) {
            const container = document.getElementById('modulosContainer');
            
            if (!modulos || modulos.length === 0) {
                document.getElementById('modulosNumero').textContent = '0';
                document.getElementById('totalModulos').textContent = 'Sin módulos disponibles';
                container.innerHTML = '<div class="empty-state">' +
                    '<i class="fas fa-folder-open"></i>' +
                    '<p>Este curso no tiene módulos aún</p>' +
                    '</div>';
                return;
            }

            document.getElementById('modulosNumero').textContent = modulos.length;
            document.getElementById('totalModulos').textContent = modulos.length + ' módulo(s) disponible(s)';

            let html = '';
            modulos.forEach((modulo, index) => {
                const lecciones = modulo.lista_lecciones || [];
                
                html += '<div class="modulo-card">' +
                    '<div class="modulo-header">' +
                    '<span class="modulo-titulo"><i class="fas fa-book"></i> ' + (modulo.titulo || 'Módulo sin título') + '</span>' +
                    '<span class="modulo-numero-badge">Módulo ' + (index + 1) + '</span>' +
                    '</div>';

                if (lecciones.length > 0) {
                    html += '<div style="margin-top: 15px;">';
                    lecciones.forEach((leccion, leccionIndex) => {
                        html += '<div class="leccion-item">' +
                            '<div class="leccion-nombre"><i class="fas fa-play-circle"></i> Lección ' + (leccionIndex + 1) + ': ' + (leccion.titulo || leccion.nombre || 'Sin título') + '</div>' +
                            '<div class="leccion-descripcion">' + (leccion.descripcion || 'Sin descripción') + '</div>' +
                            '<a href="' + (leccion.url_video || '#') + '" target="_blank" class="leccion-url"><i class="fas fa-external-link-alt"></i> Ver Video</a>' +
                            '</div>';
                    });
                    html += '</div>';
                } else {
                    html += '<div style="background: #fff5f5; padding: 12px; border-radius: 6px; color: #e53e3e; margin-top: 10px;">' +
                        '<i class="fas fa-info-circle"></i> Este módulo no tiene lecciones aún' +
                        '</div>';
                }

                html += '</div>';
            });

            container.innerHTML = html;
        }

        function mostrarError(mensaje) {
            const container = document.getElementById('modulosContainer');
            document.getElementById('modulosNumero').textContent = '0';
            document.getElementById('totalModulos').textContent = 'Error al cargar';
            container.innerHTML = '<div class="empty-state">' +
                '<i class="fas fa-exclamation-circle"></i>' +
                '<p>' + mensaje + '</p>' +
                '</div>';
        }
    </script>
</body>
</html>