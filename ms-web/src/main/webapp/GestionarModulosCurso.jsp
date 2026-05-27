<%@page import="dto.DtoModuloLista"%>
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
    <title>Gestionar Módulos del Curso</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/styleUser.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        .modules-container {
            display: grid;
            gap: 20px;
            margin-bottom: 30px;
        }

        .module-card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            border: 1px solid #e2e8f0;
        }

        .module-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }

        .module-title {
            font-size: 18px;
            font-weight: 600;
            color: #2d3748;
        }

        .module-number {
            background: #405370;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 500;
        }

        .add-lesson-btn {
            background: #48bb78;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            transition: all 0.3s;
        }

        .add-lesson-btn:hover {
            background: #38a169;
            transform: translateY(-2px);
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

        .finish-btn {
            background: #405370;
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

        .finish-btn:hover {
            background: #2d3e52;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <%
        
       
        String idCurso = request.getParameter("idCurso");
        String listaJson = request.getParameter("lista");
        System.out.println("ListajSON: "+listaJson);
            
        Gson gson = new Gson();


        Type tipoLista = new TypeToken<List<DtoModuloLista>>(){}.getType();

        List<DtoModuloLista> listaModulo = gson.fromJson(listaJson,tipoLista);
        
    %>
    
    <div class="container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="logo">
                <img src="img/Logo2.png" width="190" height="150" alt="Logo" />
            </div>

        </aside>

        <!-- Main Content -->
        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1>Gestionar Módulos del Curso</h1>
                    <p>Organiza el contenido de tu curso en módulos y lecciones</p>
                </div>
            </header>

            <!-- Módulos Existentes -->
            <div class="section-title">Módulos del Curso</div>
            
            <div class="modules-container" id="modulosContainer">
                <%
                int cont=0;
                for (DtoModuloLista modulo : listaModulo) {          
                %>
                    <%
                        cont++;
                    %>
                    <div class="module-card">
                        <div class="module-header">
                            <div>
                                <span class="module-number" >Módulo <%= cont%></span>
                                <h3 class="module-title"style="padding: 20px"><%= modulo.titulo() %></h3>
                            </div>
                            <button class="add-lesson-btn" 
                                    onclick="window.location.href='<%=Ruta.MS_MATEDU_URL%>/LeccionControll?accion=listarLecciones&idModulo=<%=modulo.id_modulo()%>&idCurso=<%=idCurso%>&tituloModulo=<%=modulo.titulo()%>'">
                                 Agregar Lecciones
                            </button>
                        </div>
                    </div>
                <%
                    }
                %>
                <%
                    if(cont==0){
                    
                %>
                    <div class="empty-state">
                        <div class="empty-state-icon">📝</div>
                        <p>Aún no hay módulos en este curso</p>
                        <small>Agrega el primer módulo usando el formulario de abajo</small>
                    </div>
                <%
                    }
                %>
            </div>

            <div class="section-divider"></div>

            <!-- Formulario para Agregar Nuevo Módulo -->
            <div class="section-title">Agregar Nuevo Módulo</div>
            
            <form action="<%=Ruta.MS_MATEDU_URL%>/ModuloControll?accion=register&idCurso=<%=idCurso%>" 
                  method="POST" 
                  class="form-container">

                <div class="form-group">
                    <label for="nombre">Nombre del Módulo *</label>
                    <input type="text" 
                           id="nombre" 
                           name="nombre" 
                           placeholder="Ej: Fundamentos de Programación"
                           required>
                </div>
                
                <button type="submit" class="submit-btn">
                    Agregar Módulo
                </button>
            </form>

            <!-- Botón para Finalizar -->
            <button class="finish-btn" onclick="finalizarCurso()">
                 Finalizar y Publicar Curso
            </button>
        </main>
    </div>

    <script>
        function finalizarCurso() {
            Swal.fire({
                title: '¿Finalizar curso?',
                text: 'El curso será publicado y estará disponible para los estudiantes',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Sí, publicar',
                cancelButtonText: 'Cancelar',
                confirmButtonColor: '#405370',
                cancelButtonColor: '#718096'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser';
                }
            });
        }
    </script>

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