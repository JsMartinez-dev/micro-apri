<%@page import="utilidad.Ruta"%>
<%@page import="dto.DtoUsuarioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reseñas del Material</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/styleUserDash.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />
    <style>
        .promedio-box { background: #f8f9fa; border-radius: 10px; padding: 25px; text-align: center; margin: 20px 0; border: 2px solid #e9ecef; }
        .promedio-numero { font-size: 3em; font-weight: 700; color: #405370; margin: 10px 0; }
        .estrellas { color: #ffc107; font-size: 1.8em; letter-spacing: 5px; }
        .rating-input { display: flex; gap: 10px; flex-direction: row-reverse; justify-content: flex-end; margin: 10px 0; }
        .rating-input input { display: none; }
        .rating-input label { cursor: pointer; font-size: 2em; color: #ddd; transition: color 0.2s; }
        .rating-input input:checked ~ label, .rating-input label:hover, .rating-input label:hover ~ label { color: #ffc107; }
        .resena-card { background: white; border: 2px solid #e9ecef; border-radius: 10px; padding: 20px; margin: 15px 0; transition: all 0.3s; }
        .resena-card:hover { box-shadow: 0 5px 15px rgba(0,0,0,0.1); transform: translateY(-2px); }
        .resena-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; padding-bottom: 10px; border-bottom: 1px solid #e9ecef; }
        .resena-usuario { font-weight: 600; color: #405370; }
        .resena-estrellas { color: #ffc107; }
        .resena-comentario { color: #666; line-height: 1.6; margin-bottom: 10px; }
        .resena-acciones { display: flex; gap: 10px; flex-wrap: wrap; }
        .btn-small { padding: 8px 15px; font-size: 14px; border: none; border-radius: 5px; cursor: pointer; transition: all 0.3s; font-weight: 500; }
        .btn-delete { background: #dc3545; color: white; }
        .btn-report { background: #ff9800; color: white; }
        .btn-small:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.2); }
        .no-resenas { text-align: center; padding: 40px; color: #999; }
        .no-resenas i { font-size: 3em; margin-bottom: 15px; color: #ddd; }
        .loading { text-align: center; padding: 30px; color: #405370; }
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
        String idMaterialParam = request.getParameter("idMaterial");
        String nombreMaterial = request.getParameter("nombreMaterial");
        if (idMaterialParam == null) {
            response.sendRedirect("DashboardUser.jsp");
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
                <a href="#" class="menu-item active">Reseñas</a>
                <a href="<%=Ruta.MS_USUARIO_URL%>/CerrarSesion?accion=user" class="menu-item">Cerrar sesión</a>
            </nav>
        </aside>

        <main class="main-content">
            <header class="header">
                <div class="welcome-text">
                    <h1><i class="fas fa-star"></i> Reseñas del Material</h1>
                    <p><%= nombreMaterial != null ? nombreMaterial : "Material Educativo" %></p>
                </div>
                <div class="header-right">
                    <button onclick="history.back()" class="add-cancel-btn">
                        <i class="fa-solid fa-arrow-left"></i> Volver
                    </button>
                </div>
            </header>

            <div class="promedio-box">
                <h3>Calificación Promedio</h3>
                <div class="promedio-numero" id="promedioNumero">-</div>
                <div class="estrellas" id="promedioEstrellas"></div>
                <p id="totalResenas">Cargando...</p>
            </div>

            <div class="form-container" style="margin-top: 30px;">
                <h2 style="margin-bottom: 20px;">Deja tu Reseña</h2>
                <form id="formResena" onsubmit="crearResena(event)">
                    <div class="form-group">
                        <label>Calificación:</label>
                        <div class="rating-input">
                            <input type="radio" id="star5" name="cantidadEstrellas" value="5" required>
                            <label for="star5"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star4" name="cantidadEstrellas" value="4">
                            <label for="star4"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star3" name="cantidadEstrellas" value="3">
                            <label for="star3"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star2" name="cantidadEstrellas" value="2">
                            <label for="star2"><i class="fas fa-star"></i></label>
                            <input type="radio" id="star1" name="cantidadEstrellas" value="1">
                            <label for="star1"><i class="fas fa-star"></i></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comentario">Comentario:</label>
                        <textarea name="comentario" id="comentario" placeholder="Comparte tu experiencia con este material..." rows="5" required></textarea>
                    </div>
                    <button type="submit" class="submit-btn">
                        <i class="fas fa-paper-plane"></i> Publicar Reseña
                    </button>
                </form>
            </div>

            <div style="margin-top: 40px;">
                <h2 style="margin-bottom: 20px;">Todas las Reseñas</h2>
                <div id="listaResenas" class="loading">
                    <i class="fas fa-spinner fa-spin"></i>
                    <p>Cargando reseñas...</p>
                </div>
            </div>
        </main>
    </div>

    <script>
        const idMaterial = <%= idMaterialParam %>;
        const idUsuario = <%= userDto.id_persona() %>;

        window.onload = function() {
            // Mostrar alertas de éxito si vienen por querystring y limpiar la URL
            try {
                const url = new URL(window.location.href);
                const success = url.searchParams.get('success');
                if (success === 'resena_eliminada') {
                    Swal.fire({ title: 'Eliminada', text: 'Reseña eliminada correctamente', icon: 'success', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
                } else if (success === 'resena_actualizada') {
                    Swal.fire({ title: 'Actualizada', text: 'Reseña actualizada correctamente', icon: 'success', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
                }
                if (success) {
                    url.searchParams.delete('success');
                    window.history.replaceState({}, '', url);
                }
            } catch(e) { /* ignore */ }

            cargarPromedio();
            cargarResenas();
        };

        async function cargarPromedio() {
            try {
                const response = await fetch('ResenaControl?accion=obtenerPromedio&idMaterial=' + idMaterial);
                const data = await response.json();
                if (data.promedio) {
                    const promedio = parseFloat(data.promedio).toFixed(1);
                    document.getElementById('promedioNumero').textContent = promedio;
                    let estrellasHtml = '';
                    for (let i = 1; i <= 5; i++) {
                        if (i <= Math.floor(promedio)) { estrellasHtml += '<i class="fas fa-star"></i> '; }
                        else if (i - promedio < 1) { estrellasHtml += '<i class="fas fa-star-half-alt"></i> '; }
                        else { estrellasHtml += '<i class="far fa-star"></i> '; }
                    }
                    document.getElementById('promedioEstrellas').innerHTML = estrellasHtml;
                } else {
                    document.getElementById('promedioNumero').textContent = 'Sin calificaciones';
                    document.getElementById('promedioEstrellas').innerHTML = '';
                }
            } catch (error) { console.error('Error al cargar promedio:', error); }
        }

        async function cargarResenas() {
            try {
                const response = await fetch('ResenaControl?accion=listarPorMaterial&idMaterial=' + idMaterial);
                const resenas = await response.json();
                const container = document.getElementById('listaResenas');
                if (resenas && resenas.length > 0) {
                    document.getElementById('totalResenas').textContent = resenas.length + ' reseña(s)';
                    let html = '';
                    resenas.forEach(resena => {
                        const estrellas = '<i class="fas fa-star"></i> '.repeat(resena.cantidadEstrellas);
                        const esPropia = resena.idUsuario == idUsuario;
                        const botonesHtml = esPropia
                            ? '<div class="resena-acciones">'
                                + '<button onclick="editarResena(' + resena.idResena + ',\'' + encodeURIComponent(resena.comentario) + '\',' + resena.cantidadEstrellas + ')" class="btn-small"><i class="fas fa-pen"></i> Editar</button>'
                                + '<button onclick="eliminarResena(' + resena.idResena + ')" class="btn-small btn-delete"><i class="fas fa-trash"></i> Eliminar</button>'
                              + '</div>'
                            : '<div class="resena-acciones"><button onclick="reportarResena(' + resena.idResena + ')" class="btn-small btn-report"><i class="fas fa-flag"></i> Reportar</button></div>';
                        html += '<div class="resena-card">' +
                                '<div class="resena-header">' +
                                '<span class="resena-usuario"><i class="fas fa-user-circle"></i> Usuario #' + resena.idUsuario + '</span>' +
                                '<span class="resena-estrellas">' + estrellas + '</span>' +
                                '</div>' +
                                '<p class="resena-comentario">' + resena.comentario + '</p>' +
                                botonesHtml +
                                '</div>';
                    });
                    container.innerHTML = html;
                } else {
                    document.getElementById('totalResenas').textContent = '0 reseñas';
                    container.innerHTML = '<div class="no-resenas">' +
                        '<i class="fas fa-comment-slash"></i>' +
                        '<p>Aún no hay reseñas. ¡Sé el primero en opinar!</p>' +
                        '</div>';
                }
            } catch (error) {
                console.error('Error al cargar reseñas:', error);
                document.getElementById('listaResenas').innerHTML =
                    '<div class="no-resenas">' +
                        '<i class="fas fa-exclamation-circle"></i>' +
                        '<p>Error al cargar las reseñas</p>' +
                    '</div>';
            }
        }

        async function crearResena(event) {
            event.preventDefault();
            const form = event.target;
            const formData = new FormData(form);
            formData.append('accion', 'crear');
            formData.append('idUsuario', idUsuario);
            formData.append('idMaterialEducativo', idMaterial);
            try {
                const params = new URLSearchParams(formData);
                const response = await fetch('ResenaControl', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: params
                });
                const data = await response.json();
                if (response.ok && data.success) {
                    Swal.fire({ title: '¡Éxito!', text: 'Reseña creada correctamente', icon: 'success', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' })
                        .then(() => { form.reset(); cargarPromedio(); cargarResenas(); });
                } else { throw new Error(data.error || 'Error al crear reseña'); }
            } catch (error) {
                Swal.fire({ title: 'Error', text: 'No se pudo crear la reseña: ' + error.message, icon: 'error', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
            }
        }

        async function eliminarResena(idResena) {
            const confirm = await Swal.fire({ title: '¿Estás seguro?', text: 'Esta acción no se puede deshacer', icon: 'warning', showCancelButton: true, confirmButtonColor: '#dc3545', cancelButtonColor: '#6c757d', confirmButtonText: 'Sí, eliminar', cancelButtonText: 'Cancelar' });
            if (!confirm.isConfirmed) return;

            try {
                const params = new URLSearchParams();
                params.append('accion', 'eliminar');
                params.append('idResena', idResena);
                params.append('ajax', 'true');
                const resp = await fetch('ResenaControl', { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded', 'X-Requested-With': 'XMLHttpRequest' }, body: params });
                const data = await resp.json();
                if (resp.ok && data.success) {
                    await Swal.fire({ title: 'Eliminada', text: 'Reseña eliminada correctamente', icon: 'success', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
                    await cargarPromedio();
                    await cargarResenas();
                } else {
                    throw new Error(data.error || 'No se pudo eliminar la reseña');
                }
            } catch (e) {
                Swal.fire({ title: 'Error', text: String(e.message || e), icon: 'error', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
            }
        }

                async function editarResena(idResena, comentarioEncoded, estrellas) {
                        const comentario = decodeURIComponent(comentarioEncoded || '');
                        const safe = comentario.replace(/</g,'&lt;').replace(/>/g,'&gt;');
                        const html = '<div class="form-group">'
                                + '<label>Comentario</label>'
                                + '<textarea id="swal-comentario" class="swal2-textarea" rows="5" style="width:100%">' + safe + '</textarea>'
                                + '</div>'
                                + '<div class="form-group" style="margin-top:10px;">'
                                + '<label>Estrellas</label>'
                                + '<select id="swal-estrellas" class="swal2-select">'
                                + '<option value="5">★★★★★ (5)</option>'
                                + '<option value="4">★★★★ (4)</option>'
                                + '<option value="3">★★★ (3)</option>'
                                + '<option value="2">★★ (2)</option>'
                                + '<option value="1">★ (1)</option>'
                                + '</select>'
                                + '</div>';
            const dlg = await Swal.fire({ title: 'Editar reseña', html, focusConfirm: false, showCancelButton: true, confirmButtonText: 'Guardar', cancelButtonText: 'Cancelar', didOpen: () => { const sel = document.getElementById('swal-estrellas'); if (sel) sel.value = String(estrellas || 5); } });
            if (!dlg.isConfirmed) return;
            const nuevoComentario = document.getElementById('swal-comentario').value.trim();
            const nuevaEstrellas = parseInt(document.getElementById('swal-estrellas').value, 10) || 5;
            if (!nuevoComentario) { await Swal.fire({ title: 'Requerido', text: 'El comentario no puede estar vacío', icon: 'warning', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' }); return; }
            try {
                const params = new URLSearchParams();
                params.append('accion', 'actualizar');
                params.append('idResena', idResena);
                params.append('comentario', nuevoComentario);
                params.append('cantidadEstrellas', nuevaEstrellas);
                params.append('ajax', 'true');
                const resp = await fetch('ResenaControl', { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded', 'X-Requested-With': 'XMLHttpRequest' }, body: params });
                const data = await resp.json();
                if (resp.ok && data.success) {
                    await Swal.fire({ title: 'Actualizada', text: 'Reseña actualizada correctamente', icon: 'success', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
                    await cargarPromedio();
                    await cargarResenas();
                } else {
                    throw new Error(data.error || 'No se pudo actualizar la reseña');
                }
            } catch (e) {
                Swal.fire({ title: 'Error', text: String(e.message || e), icon: 'error', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
            }
        }

        function reportarResena(idResena) {
            Swal.fire({
                title: 'Reportar Reseña', input: 'textarea', inputLabel: '¿Por qué deseas reportar esta reseña?', inputPlaceholder: 'Describe el motivo del reporte...',
                showCancelButton: true, confirmButtonText: 'Reportar', cancelButtonText: 'Cancelar', confirmButtonColor: '#405370',
                inputValidator: (value) => { if (!value) { return 'Debes escribir un motivo'; } }
            }).then(async (result) => {
                if (result.isConfirmed) {
                    try {
                        const response = await fetch('ReporteControl?accion=crear&idResena=' + idResena + '&idUsuario=' + idUsuario + '&motivo=' + encodeURIComponent(result.value));
                        const data = await response.json();
                        if (response.ok && data.success) {
                            Swal.fire({ title: '¡Reporte enviado!', text: 'Gracias por ayudarnos a mantener la comunidad segura', icon: 'success', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
                        } else { throw new Error(data.error || 'Error al enviar el reporte'); }
                    } catch (error) {
                        Swal.fire({ title: 'Error', text: 'No se pudo enviar el reporte: ' + error.message, icon: 'error', confirmButtonText: 'Aceptar', confirmButtonColor: '#405370' });
                    }
                }
            });
        }
    </script>
</body>
</html>
