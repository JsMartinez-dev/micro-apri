package control;

import com.google.gson.Gson;
import dto.DtoReporteCrear;
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

@WebServlet(name = "ReporteControlServlet", urlPatterns = {"/ReporteControl"})
public class ReporteControlServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion) {
                case "crear":
                    crearReporte(request, response);
                    break;
                case "resolverReporte":
                    resolverReporte(request, response);
                    break;
                case "actualizarEstado":
                    actualizarEstadoReporte(request, response);
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
                case "crear":
                    crearReporte(request, response);
                    break;
                case "listarTodos":
                    listarTodosReportes(request, response);
                    break;
                case "listarPorEstado":
                    listarReportesPorEstado(request, response);
                    break;
                case "listarPorResena":
                    listarReportesPorResena(request, response);
                    break;
                case "obtenerPorId":
                    obtenerReportePorId(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error procesando solicitud: " + e.getMessage());
        }
    }

    private void crearReporte(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String motivo = request.getParameter("motivo");
        String idUsuario = request.getParameter("idUsuario");
        String idResena = request.getParameter("idResena");

        if (motivo == null || idUsuario == null || idResena == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros requeridos");
            return;
        }

        String urlParams = String.format("accion=crear&motivo=%s&idUsuario=%s&idResena=%s",
                java.net.URLEncoder.encode(motivo, "UTF-8"), idUsuario, idResena);

        URL url = new URL(Ruta.MS_RESENAS_URL + "/ReporteControl");
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
        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(jsonResponse);
    }

    private void resolverReporte(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idReporte = Integer.parseInt(request.getParameter("idReporte"));
        Integer idAdministrador = Integer.parseInt(request.getParameter("idAdministrador"));
        String accion = request.getParameter("accionTomada");
        String respuesta = request.getParameter("respuesta");

        String jsonBody = String.format(
                "{\"idReporte\":%d,\"idAdministrador\":%d,\"accion\":\"%s\",\"respuesta\":\"%s\"}",
                idReporte, idAdministrador, accion, respuesta != null ? respuesta : "");

        URL url = new URL(Ruta.MS_RESENAS_URL + "/reporte/respuesta");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes("UTF-8"));
        }

        int statusCode = conn.getResponseCode();

        if (statusCode == HttpServletResponse.SC_CREATED) {
            String redirectUrl = request.getParameter("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                response.sendRedirect(redirectUrl + "?success=reporte_resuelto");
            } else {
                response.sendRedirect("DashboardAdmin_GR.jsp?success=reporte_resuelto");
            }
        } else {
            String errorMsg = leerRespuesta(conn);
            response.sendError(statusCode, "Error al resolver reporte: " + errorMsg);
        }
    }

    private void actualizarEstadoReporte(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idReporte = Integer.parseInt(request.getParameter("idReporte"));
        String nuevoEstado = request.getParameter("estado");

        String jsonBody = String.format("{\"estado\":\"%s\"}", nuevoEstado);

        URL url = new URL(Ruta.MS_RESENAS_URL + "/reporte/" + idReporte);
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
                response.sendRedirect(redirectUrl + "?success=estado_actualizado");
            } else {
                response.sendRedirect("DashboardAdmin_GR.jsp?success=estado_actualizado");
            }
        } else {
            String errorMsg = leerRespuesta(conn);
            response.sendError(statusCode, "Error al actualizar estado: " + errorMsg);
        }
    }

    private void listarTodosReportes(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        URL url = new URL(Ruta.MS_RESENAS_URL + "/reporte");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void listarReportesPorEstado(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String estado = request.getParameter("estado");

        URL url = new URL(Ruta.MS_RESENAS_URL + "/reporte?estado=" + estado);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void listarReportesPorResena(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idResena = Integer.parseInt(request.getParameter("idResena"));

        URL url = new URL(Ruta.MS_RESENAS_URL + "/reporte/resena/" + idResena);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String jsonResponse = leerRespuesta(conn);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private void obtenerReportePorId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer idReporte = Integer.parseInt(request.getParameter("idReporte"));

        URL url = new URL(Ruta.MS_RESENAS_URL + "/reporte/" + idReporte);
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
