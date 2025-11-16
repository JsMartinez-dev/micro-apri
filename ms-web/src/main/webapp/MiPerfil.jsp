<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mi Perfil</title>
        <link rel="stylesheet" href="css/styleUserDash.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
           integrity="sha512-SnH5WK+bZxgIk9lKMdQXWf5fL8pT..." 
           crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            .profile-container {
                background: white;
                border-radius: 12px;
                padding: 2rem;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }

            .profile-header {
                display: flex;
                align-items: center;
                gap: 2rem;
                margin-bottom: 3rem;
                padding-bottom: 2rem;
                border-bottom: 2px solid #f0f0f0;
            }

            .profile-avatar {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 3rem;
                color: white;
                font-weight: 600;
            }

            .profile-header-info h2 {
                margin: 0 0 0.5rem 0;
                font-size: 1.8rem;
                color: #2d3748;
            }

            .profile-header-info p {
                margin: 0;
                color: #718096;
                font-size: 1rem;
            }

            .profile-section {
                margin-bottom: 2rem;
            }

            .profile-section h3 {
                color: #2d3748;
                font-size: 1.3rem;
                margin-bottom: 1.5rem;
                font-weight: 600;
            }

            .profile-field {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 1.2rem;
                background: #f7fafc;
                border-radius: 8px;
                margin-bottom: 1rem;
                transition: all 0.3s ease;
            }

            .profile-field:hover {
                background: #edf2f7;
                transform: translateX(5px);
            }

            .field-info {
                flex: 1;
            }

            .field-label {
                font-size: 0.85rem;
                color: #718096;
                margin-bottom: 0.3rem;
                font-weight: 500;
            }

            .field-value {
                font-size: 1.1rem;
                color: #2d3748;
                font-weight: 500;
            }

            .field-input {
                display: none;
                padding: 0.6rem;
                border: 2px solid #667eea;
                border-radius: 6px;
                font-size: 1rem;
                width: 100%;
                max-width: 400px;
                margin-top: 0.5rem;
                font-family: 'Inter', sans-serif;
                transition: all 0.3s ease;
            }

            .field-input:focus {
                outline: none;
                border-color: #5568d3;
                box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            }

            .field-input.active {
                display: block;
            }

            .modify-btn {
                background: #667eea;
                color: white;
                border: none;
                padding: 0.6rem 1.2rem;
                border-radius: 6px;
                cursor: pointer;
                font-size: 0.9rem;
                font-weight: 500;
                transition: all 0.3s ease;
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }

            .modify-btn:hover {
                background: #5568d3;
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
            }

            .save-btn {
                background: #48bb78;
                color: white;
                border: none;
                padding: 0.6rem 1.2rem;
                border-radius: 6px;
                cursor: pointer;
                font-size: 0.9rem;
                font-weight: 500;
                transition: all 0.3s ease;
                display: none;
                align-items: center;
                gap: 0.5rem;
                margin-left: 0.5rem;
            }

            .save-btn:hover {
                background: #38a169;
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(72, 187, 120, 0.4);
            }

            .save-btn.active {
                display: flex;
            }

            .cancel-btn {
                background: #e53e3e;
                color: white;
                border: none;
                padding: 0.6rem 1.2rem;
                border-radius: 6px;
                cursor: pointer;
                font-size: 0.9rem;
                font-weight: 500;
                transition: all 0.3s ease;
                display: none;
                align-items: center;
                gap: 0.5rem;
                margin-left: 0.5rem;
            }

            .cancel-btn:hover {
                background: #c53030;
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(229, 62, 62, 0.4);
            }

            .cancel-btn.active {
                display: flex;
            }

            .back-btn {
                background: #48bb78;
                color: white;
                border: none;
                padding: 0.8rem 1.5rem;
                border-radius: 8px;
                cursor: pointer;
                font-size: 1rem;
                font-weight: 600;
                transition: all 0.3s ease;
                display: inline-flex;
                align-items: center;
                gap: 0.5rem;
                margin-top: 1rem;
            }

            .back-btn:hover {
                background: #38a169;
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(72, 187, 120, 0.4);
            }

            .icon-wrapper {
                width: 40px;
                height: 40px;
                border-radius: 8px;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-right: 1rem;
            }

            .icon-wrapper.blue {
                background: #e6f2ff;
                color: #3182ce;
            }

            .icon-wrapper.green {
                background: #e6ffed;
                color: #38a169;
            }

            .icon-wrapper.purple {
                background: #f3e8ff;
                color: #805ad5;
            }

            .icon-wrapper.orange {
                background: #fff5e6;
                color: #dd6b20;
            }

            .field-content {
                display: flex;
                align-items: center;
                flex: 1;
            }

            .button-group {
                display: flex;
                align-items: center;
            }
        </style>
    </head>

    <body>
        <%
            HttpSession sesion = request.getSession();
            DtoUsuarioLogin dtoUser = (DtoUsuarioLogin) sesion.getAttribute("usuario");
            
            if (dtoUser == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            
            String inicial = dtoUser.primerNombre().substring(0, 1).toUpperCase();
        %>
        
        <div class="container">
            <aside class="sidebar">
                <div class="logo">
                    <img src="img/Logo2.png" width="190" height="150" alt="Logo pagina" />
                </div>
                <nav>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser" class="menu-item">Dashboard</a>
                    <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion?accion=user" class="menu-item">Cerrar sesión</a>
                </nav>
            </aside>

            <main class="main-content">
                <header class="header">
                    <div class="welcome-text">
                        <h1>Mi Perfil</h1>
                        <p>Gestiona tu información personal</p>
                    </div>
                </header>

                <div class="profile-container">
                    <div class="profile-header">
                        <div class="profile-avatar"><%=inicial%></div>
                        <div class="profile-header-info">
                            <h2><%=dtoUser.primerNombre()%> <%=dtoUser.primerApellido()%></h2>
                            <p><%=dtoUser.correo()%></p>
                        </div>
                    </div>

                    <form action="<%=Ruta.MS_USUARIO_URL%>/ActualizarPerfil" method="post" id="profileForm">
                        <div class="profile-section">
                            <h3>Información Personal</h3>
                            
                            <!-- Primer Nombre -->
                            <div class="profile-field">
                                <div class="field-content">
                                    <div class="icon-wrapper blue">
                                        <i class="fa-solid fa-user"></i>
                                    </div>
                                    <div class="field-info">
                                        <div class="field-label">Primer Nombre</div>
                                        <div class="field-value" id="value-nombre"><%=dtoUser.primerNombre()%></div>
                                        <input type="text" class="field-input" id="input-nombre" name="primerNombre" value="<%=dtoUser.primerNombre()%>">
                                    </div>
                                </div>
                                <div class="button-group">
                                    <button type="button" class="modify-btn" onclick="enableEdit('nombre')">
                                        <i class="fa-solid fa-pen"></i>
                                        Modificar
                                    </button>
                                    <button type="button" class="save-btn" id="save-nombre" onclick="saveField('nombre')">
                                        <i class="fa-solid fa-check"></i>
                                        Guardar
                                    </button>
                                    <button type="button" class="cancel-btn" id="cancel-nombre" onclick="cancelEdit('nombre')">
                                        <i class="fa-solid fa-times"></i>
                                        Cancelar
                                    </button>
                                </div>
                            </div>

                            <!-- Primer Apellido -->
                            <div class="profile-field">
                                <div class="field-content">
                                    <div class="icon-wrapper green">
                                        <i class="fa-solid fa-user-tag"></i>
                                    </div>
                                    <div class="field-info">
                                        <div class="field-label">Primer Apellido</div>
                                        <div class="field-value" id="value-apellido"><%=dtoUser.primerApellido()%></div>
                                        <input type="text" class="field-input" id="input-apellido" name="primerApellido" value="<%=dtoUser.primerApellido()%>">
                                    </div>
                                </div>
                                <div class="button-group">
                                    <button type="button" class="modify-btn" onclick="enableEdit('apellido')">
                                        <i class="fa-solid fa-pen"></i>
                                        Modificar
                                    </button>
                                    <button type="button" class="save-btn" id="save-apellido" onclick="saveField('apellido')">
                                        <i class="fa-solid fa-check"></i>
                                        Guardar
                                    </button>
                                    <button type="button" class="cancel-btn" id="cancel-apellido" onclick="cancelEdit('apellido')">
                                        <i class="fa-solid fa-times"></i>
                                        Cancelar
                                    </button>
                                </div>
                            </div>

                            <!-- Correo -->
                            <div class="profile-field">
                                <div class="field-content">
                                    <div class="icon-wrapper purple">
                                        <i class="fa-solid fa-envelope"></i>
                                    </div>
                                    <div class="field-info">
                                        <div class="field-label">Correo Electrónico</div>
                                        <div class="field-value" id="value-correo"><%=dtoUser.correo()%></div>
                                        <input type="email" class="field-input" id="input-correo" name="correo" value="<%=dtoUser.correo()%>">
                                    </div>
                                </div>
                                <div class="button-group">
                                    <button type="button" class="modify-btn" onclick="enableEdit('correo')">
                                        <i class="fa-solid fa-pen"></i>
                                        Modificar
                                    </button>
                                    <button type="button" class="save-btn" id="save-correo" onclick="saveField('correo')">
                                        <i class="fa-solid fa-check"></i>
                                        Guardar
                                    </button>
                                    <button type="button" class="cancel-btn" id="cancel-correo" onclick="cancelEdit('correo')">
                                        <i class="fa-solid fa-times"></i>
                                        Cancelar
                                    </button>
                                </div>
                            </div>

                            <!-- Institución -->
                            <div class="profile-field">
                                <div class="field-content">
                                    <div class="icon-wrapper orange">
                                        <i class="fa-solid fa-building-columns"></i>
                                    </div>
                                    <div class="field-info">
                                        <div class="field-label">Institución</div>
                                        <div class="field-value" id="value-institucion"><%=dtoUser.institucion()%></div>
                                        <input type="text" class="field-input" id="input-institucion" name="institucion" value="<%=dtoUser.institucion()%>">
                                    </div>
                                </div>
                                <div class="button-group">
                                    <button type="button" class="modify-btn" onclick="enableEdit('institucion')">
                                        <i class="fa-solid fa-pen"></i>
                                        Modificar
                                    </button>
                                    <button type="button" class="save-btn" id="save-institucion" onclick="saveField('institucion')">
                                        <i class="fa-solid fa-check"></i>
                                        Guardar
                                    </button>
                                    <button type="button" class="cancel-btn" id="cancel-institucion" onclick="cancelEdit('institucion')">
                                        <i class="fa-solid fa-times"></i>
                                        Cancelar
                                    </button>
                                </div>
                            </div>

                            <!-- Fecha de Nacimiento -->
                            <div class="profile-field">
                                <div class="field-content">
                                    <div class="icon-wrapper blue">
                                        <i class="fa-solid fa-calendar-days"></i>
                                    </div>
                                    <div class="field-info">
                                        <div class="field-label">Fecha de Nacimiento</div>
                                        <div class="field-value" id="value-fecha"><%=dtoUser.fecha_nacimiento()%></div>
                                        <input type="date" class="field-input" id="input-fecha" name="fecha_nacimiento" value="<%=dtoUser.fecha_nacimiento()%>">
                                    </div>
                                </div>
                                <div class="button-group">
                                    <button type="button" class="modify-btn" onclick="enableEdit('fecha')">
                                        <i class="fa-solid fa-pen"></i>
                                        Modificar
                                    </button>
                                    <button type="button" class="save-btn" id="save-fecha" onclick="saveField('fecha')">
                                        <i class="fa-solid fa-check"></i>
                                        Guardar
                                    </button>
                                    <button type="button" class="cancel-btn" id="cancel-fecha" onclick="cancelEdit('fecha')">
                                        <i class="fa-solid fa-times"></i>
                                        Cancelar
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <a href="<%=Ruta.MS_USUARIO_URL%>/UsuarioControll?accion=dashboardUser">
                        <button class="back-btn">
                            <i class="fa-solid fa-arrow-left"></i>
                            Regresar al Dashboard
                        </button>
                    </a>
                </div>
            </main>
        </div>

        <script>
            // Guardar valores originales
            const originalValues = {
                nombre: '<%=dtoUser.primerNombre()%>',
                apellido: '<%=dtoUser.primerApellido()%>',
                correo: '<%=dtoUser.correo()%>',
                institucion: '<%=dtoUser.institucion()%>',
                fecha: '<%=dtoUser.fecha_nacimiento()%>'
            };

            function enableEdit(field) {
                // Ocultar el valor y mostrar el input
                document.getElementById('value-' + field).style.display = 'none';
                document.getElementById('input-' + field).classList.add('active');
                
                // Ocultar botón modificar y mostrar guardar/cancelar
                event.target.closest('.button-group').querySelector('.modify-btn').style.display = 'none';
                document.getElementById('save-' + field).classList.add('active');
                document.getElementById('cancel-' + field).classList.add('active');
                
                // Enfocar el input
                document.getElementById('input-' + field).focus();
            }

            function cancelEdit(field) {
                // Restaurar valor original
                document.getElementById('input-' + field).value = originalValues[field];
                
                // Mostrar el valor y ocultar el input
                document.getElementById('value-' + field).style.display = 'block';
                document.getElementById('input-' + field).classList.remove('active');
                
                // Mostrar botón modificar y ocultar guardar/cancelar
                event.target.closest('.button-group').querySelector('.modify-btn').style.display = 'flex';
                document.getElementById('save-' + field).classList.remove('active');
                document.getElementById('cancel-' + field).classList.remove('active');
            }

            function saveField(field) {
                const newValue = document.getElementById('input-' + field).value;
                
                // Validar que no esté vacío
                if (!newValue.trim()) {
                    alert('El campo no puede estar vacío');
                    return;
                }
                
                // Actualizar el valor mostrado
                document.getElementById('value-' + field).textContent = newValue;
                originalValues[field] = newValue;
                
                // Mostrar el valor y ocultar el input
                document.getElementById('value-' + field).style.display = 'block';
                document.getElementById('input-' + field).classList.remove('active');
                
                // Mostrar botón modificar y ocultar guardar/cancelar
                event.target.closest('.button-group').querySelector('.modify-btn').style.display = 'flex';
                document.getElementById('save-' + field).classList.remove('active');
                document.getElementById('cancel-' + field).classList.remove('active');
                
                // Aquí puedes agregar la lógica para enviar los datos al servidor
                updateProfile(field, newValue);
            }

            function updateProfile(field, value) {
                // Crear un FormData con el campo actualizado
                const formData = new FormData();
                
                // Mapear el nombre del campo al nombre del parámetro
                const fieldNames = {
                    'nombre': 'primerNombre',
                    'apellido': 'primerApellido',
                    'correo': 'correo',
                    'institucion': 'institucion',
                    'fecha': 'fecha_nacimiento'
                };
                
                formData.append(fieldNames[field], value);
                formData.append('accion', 'actualizarCampo');
                
                // Enviar al servidor
                fetch('<%=Ruta.MS_USUARIO_URL%>/ActualizarPerfil', {
                    method: 'POST',
                    body: formData
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        console.log('Campo actualizado correctamente');
                    } else {
                        alert('Error al actualizar: ' + data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error al actualizar el perfil');
                });
            }
        </script>
    </body>
</html>