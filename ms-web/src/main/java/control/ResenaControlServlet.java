package control;

import com.google.gson.Gson;
import dto.DtoResenaCrear;
import dto.DtoResenaRespuesta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilidad.Ruta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ResenaControlServlet", urlPatterns = {"/ResenaControl"})
public class ResenaControlServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion) {
                case "crear":
                    crearResena(request, response);
                    break;
                case "actualizar":
                    actualizarResena(request, response);
                    break;
                case "eliminar":
                    eliminarResena(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error procesando solicitud: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion) {
                case "listarPorMaterial":
                    listarResenasPorMaterial(request, response);
                    break;
                case "listarPorUsuario":
                    listarResenasPorUsuario(request, response);
                    break;
                case "obtenerPromedio":
                    obtenerPromedioEstrellas(request, response);
                    break;
                case "obtenerPorId":
                    obtenerResenaPorId(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error procesando solicitud: " + e.getMessage());
        }
    }

    private void crearResena(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String comentario = request.getParameter("comentario");
            String cantidadEstrellasStr = request.getParameter("cantidadEstrellas");
            String idUsuarioStr = request.getParameter("idUsuario");
            String idMaterialEducativoStr = request.getParameter("idMaterialEducativo");

            if (comentario == null || cantidadEstrellasStr == null ||
                    idUsuarioStr == null || idMaterialEducativoStr == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Parámetros faltantes\"}");
                return;
            }

            Integer cantidadEstrellas = Integer.parseInt(cantidadEstrellasStr);
            Integer idUsuario = Integer.parseInt(idUsuarioStr);
            Integer idMaterialEducativo = Integer.parseInt(idMaterialEducativoStr);

            String urlParams = String.format("accion=crear&comentario=%s&cantidadEstrellas=%d&idUsuario=%d&idMaterialEducativo=%d",
                    java.net.URLEncoder.encode(comentario, "UTF-8"), cantidadEstrellas, idUsuario, idMaterialEducativo);

            URL url = new URL(Ruta.MS_RESENAS_URL + "/ResenaControl");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(urlParams.getBytes("UTF-8"));
                os.flush();
            }

            int statusCode = conn.getResponseCode();

            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            if (statusCode == HttpServletResponse.SC_CREATED || statusCode == HttpServletResponse.SC_OK) {
                Map<String, Object> ok = new HashMap<>();
                ok.put("success", true);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(ok));
            } else {
                String errorMsg = leerRespuesta(conn);
                String friendly = errorMsg;
                if (statusCode == HttpServletResponse.SC_CONFLICT ||
                        (errorMsg != null && errorMsg.toLowerCase().contains("existe") && errorMsg.toLowerCase().contains("rese"))) {
                    friendly = "Ya existe una reseña para este material";
                }
                Map<String, Object> err = new HashMap<>();
                err.put("success", false);
                err.put("error", friendly);
                response.setStatus(statusCode);
                response.getWriter().write(gson.toJson(err));
            }

        } catch (Exception e) {
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("error", "No se pudo crear la reseña");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(err));
        }
    }

    private void actualizarResena(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idResena = Integer.parseInt(request.getParameter("idResena"));
        String comentario = request.getParameter("comentario");
        Integer cantidadEstrellas = Integer.parseInt(request.getParameter("cantidadEstrellas"));

        DtoResenaCrear dto = new DtoResenaCrear();
        dto.setComentario(comentario);
        dto.setCantidadEstrellas(cantidadEstrellas);

        String jsonBody = gson.toJson(dto);

        URL url = new URL(Ruta.MS_RESENAS_URL + "/api/resena/" + idResena);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes("UTF-8"));
        }

        int statusCode = conn.getResponseCode();

        if (statusCode == HttpServletResponse.SC_OK) {
            String redirectUrl = request.getParameter("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                String sep = redirectUrl.contains("?") ? "&" : "?";
                response.sendRedirect(redirectUrl + sep + "success=resena_actualizada");
            } else {
                response.sendRedirect("DashboardUser.jsp?success=resena_actualizada");
            }
        } else {
            String errorMsg = leerRespuesta(conn);
            response.sendError(statusCode, "Error al actualizar reseña: " + errorMsg);
        }
    }

    private void eliminarResena(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idResena = Integer.parseInt(request.getParameter("idResena"));

        URL url = new URL(Ruta.MS_RESENAS_URL + "/ResenaControl");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conn.setDoOutput(true);

        String body = "accion=eliminar&idResena=" + idResena;
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes("UTF-8"));
            os.flush();
        }

        int statusCode = conn.getResponseCode();

        if (statusCode == HttpServletResponse.SC_OK) {
            String redirectUrl = request.getParameter("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                String sep = redirectUrl.contains("?") ? "&" : "?";
                response.sendRedirect(redirectUrl + sep + "success=resena_eliminada");
            } else {
                response.sendRedirect("DashboardUser.jsp?success=resena_eliminada");
            }
        } else {
            String errorMsg = leerRespuesta(conn);
            response.sendError(statusCode, "Error al eliminar reseña: " + errorMsg);
        }
    }

    private void listarResenasPorMaterial(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

        URL url = new URL(Ruta.MS_RESENAS_URL + "/ResenaControl?accion=listarPorMaterial&idMaterial=" + idMaterial);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void listarResenasPorUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

        URL url = new URL(Ruta.MS_RESENAS_URL + "/ResenaControl?accion=listarPorUsuario&idUsuario=" + idUsuario);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void obtenerPromedioEstrellas(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

        URL url = new URL(Ruta.MS_RESENAS_URL + "/ResenaControl?accion=obtenerPromedio&idMaterial=" + idMaterial);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void obtenerResenaPorId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idResena = Integer.parseInt(request.getParameter("idResena"));

        URL url = new URL(Ruta.MS_RESENAS_URL + "/ResenaControl?accion=obtener&idResena=" + idResena);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private String leerRespuesta(HttpURLConnection conn) throws IOException {
        int statusCode = conn.getResponseCode();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                statusCode >= 200 && statusCode < 300
                        ? conn.getInputStream()
                        : conn.getErrorStream(), "UTF-8"))) {

            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
