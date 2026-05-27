<%@page import="dto.DtoLeccionLista"%>
<%@page import="utilidad.Ruta"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Lecciones del Módulo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/styleUser.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        .lessons-container {
            display: grid;
            gap: 15px;
            margin-bottom: 30px;
        }

        .lesson-card {
            background: white;
            border-radius: 12px;
            padding: 18px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border: 1px solid #e2e8f0;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: all 0.3s;
        }

        .lesson-card:hover {
            box-shadow: 0 4px 12px rgba(0,0,0,0.12);
            transform: translateY(-2px);
        }

        .lesson-info {
            flex: 1;
        }

        .lesson-title {
            font-size: 16px;
            font-weight: 600;
            color: #2d3748;
            margin-bottom: 5px;
        }

        .lesson-description {
            font-size: 14px;
            color: #718096;
            margin-bottom: 8px;
        }

        .lesson-url {
            font-size: 12px;
            color: #4299e1;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }

        .lesson-url:hover {
            text-decoration: underline;
        }

        .lesson-number {
            background: #667eea;
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 500;
            min-width: 90px;
            text-align: center;
        }

        .section-divider {
            margin: 40px 0;
            border-top: 2px solid #e2e8f0;
        }

        .section-title {
            font-size: 24px;
            font-weight: 600;
            color: #2d3748;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .module-info-badge {
            background: #f7fafc;
            padding: 12px 20px;
            border-radius: 8px;
            border: 1px solid #e2e8f0;
            margin-bottom: 30px;
        }

        .module-info-badge h3 {
            font-size: 18px;
            color: #405370;
            margin: 0;
        }

        .empty-state {
            text-align: center;
            padding: 40px;
            color: #718096;
            background: #f7fafc;
            border-radius: 12px;
            border: 2px dashed #cbd5e0;
        }

        .empty-state-icon {
            font-size: 48px;
            margin-bottom: 10px;
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

        .back-btn:hover {
            background: #4a5568;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <%

        
        String idModulo = request.getParameter("idModulo");
        String tituloModulo = request.getParameter("tituloModulo");
        String idCurso = request.getParameter("idCurso");
        String listaJson = request.getParameter("lista");
        String tituloJson = request.getParameter("titulo");
        System.out.println("ListajSON: "+listaJson);
            
        Gson gson = new Gson();


        Type tipoLista = new TypeToken<List<DtoLeccionLista>>(){}.getType();

        List<DtoLeccionLista> listaLeccion = gson.fromJson(listaJson,tipoLista);
    %>
    
    <div class="container">
        <aside class="sidebar">
            <div class="logo">
                <img src="img/Logo2.png" width="190" height="150" alt="Logo" />
            </div>
            
        </aside>

        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1>Gestionar Lecciones</h1>
                    <p>Agrega y organiza las lecciones de este módulo</p>
                </div>
                <div class="header-right">
                    <a href="<%=Ruta.MS_MATEDU_URL%>/ModuloControll?accion=listarModulos&idCurso=<%=idCurso%>" 
                       class="add-cancel-btn" 
                       style="text-decoration: none;">
                        ← Volver a Módulos
                    </a>
                </div>
            </header>

            <div class="module-info-badge">
                <h3>Módulo:  <%=tituloJson%></h3>
            </div>

            <div class="section-title">
                🎥 Lecciones del Módulo
            </div>
           
            <div class="lessons-container" id="leccionesContainer">
              <%
                int cont=0;
                for (DtoLeccionLista leccion : listaLeccion) {          
                %>
                    <%
                        cont++;
                    %>
                        <div class="lesson-card">
                        <div class="lesson-info">
                            <div class="lesson-title"><%= leccion.nombre()%></div>
                            <div class="lesson-description"><%= leccion.descripcion()%></div>
                            <a href="<%= leccion.url_video()%>" target="_blank" class="lesson-url">
                                🎬 Ver video
                            </a>
                        </div>
                        <div class="lesson-number">Lección <%= cont %></div>
                        </div>
                <%
                    }
                %>
                <%
                    if(cont==0){
                    
                %>
                    <div class="empty-state">
                        <div class="empty-state-icon">🎬</div>
                        <p>Aún no hay lecciones en este módulo</p>
                        <small>Agrega la primera lección usando el formulario de abajo</small>
                    </div>
                <%
                    }
                %>
                
            </div>

            <div class="section-divider"></div>

            <div class="section-title">Agregar Nueva Lección</div>
            
            <form action="<%=Ruta.MS_MATEDU_URL%>/LeccionControll?accion=register&idModulo=<%=idModulo%>&idCurso=<%=idCurso%>&tituloModulo=<%=tituloJson%>" 
                  method="POST" 
                  class="form-container">

                <div class="form-group">
                    <label for="nombre">Nombre de la Lección *</label>
                    <input type="text" 
                           id="nombre" 
                           name="nombre" 
                           placeholder="Ej: Introducción a las Variables"
                           required>
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción *</label>
                    <textarea id="descripcion" 
                              name="descripcion" 
                              rows="4" 
                              placeholder="Describe brevemente el contenido de esta lección y lo que los estudiantes aprenderán..."
                              required></textarea>
                </div>

                <div class="form-group">
                    <label for="url_video">URL del Video *</label>
                    <input type="url" 
                           id="url_video" 
                           name="url_video" 
                           placeholder="https://www.youtube.com/watch?v=..."
                           required>
                    <small style="color: #718096; font-size: 12px; margin-top: 5px; display: block;">
                        Ingresa la URL completa del video (YouTube, Vimeo, etc.)
                    </small>
                </div>
                
                <button type="submit" class="submit-btn">
                    Agregar Lección
                </button>
            </form>

            
        </main>
    </div>

    <%
        String exito = request.getParameter("mensaje");
        String fallo = request.getParameter("error");
        String titulo = null, text = null, icon = null;

        if (exito != null) {
            titulo = "¡Éxito!";
            text = exito;
            icon = "success";
        } else if (fallo != null) {
            titulo = "Error";
            text = fallo;
            icon = "error";
        }

        if (titulo != null) {
    %>
        <script>
            Swal.fire({
                title: '<%= titulo %>',
                text: '<%= text %>',
                icon: '<%= icon %>',
                confirmButtonText: 'Aceptar',
                confirmButtonColor: '#405370'
            }).then((result) => {
                if (result.isConfirmed && '<%= icon %>' === 'success') {
                    location.reload();
                }
            });
        </script>
    <%}%>
</body>
</html>