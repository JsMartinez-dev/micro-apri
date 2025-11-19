
package integracion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.DtoMatEducativo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import utilidad.Ruta;


public class MatEducativoClienteHttp {
   
    private final Gson gson;
    
    public MatEducativoClienteHttp() {
        this.gson = new Gson();
    }
    
    
    
  
    public List<DtoMatEducativo> buscarMaterialPorUsuario(int idPersona) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        
        try {
            String urlString = Ruta.MS_MATEDU_URL +"/LibroControll?accion=buscarMaterialPorUsuario&idPersona=" + idPersona;
            System.out.println("Ruta para buscar la lista de Material: "+urlString);
            URL url = new URL(urlString);
            
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(2000); 
            connection.setReadTimeout(2000);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Codigo de respuesta: "+responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8")
                );
                
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                
                Type listType = new TypeToken<List<DtoMatEducativo>>(){}.getType();
                List<DtoMatEducativo> materiales = gson.fromJson(response.toString(), listType);
                
                System.out.println("Material educativo obtenido exitosamente del MsLibro");
                return materiales;
                
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("No se encontraron materiales para el usuario: " + idPersona);
                return null;
            } else {
                System.err.println("Error en MsLibro. Código: " + responseCode);
                return null;
            }
            
        } catch (Exception ex) {
            System.err.println("Error comunicándose con MsLibro: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public List<DtoMatEducativo> listarMateriales() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        
        try {
            String urlString = Ruta.MS_MATEDU_URL+"/LibroControll?accion=matEducativos";
            System.out.println("Ruta para buscar la lista de Material aaaaaaa: "+urlString);
            URL url = new URL(urlString);
            
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(2000); 
            connection.setReadTimeout(2000);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Codigo de respuesta: "+responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer respuesta
                reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8")
                );
                
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                
                Type listType = new TypeToken<List<DtoMatEducativo>>(){}.getType();
                List<DtoMatEducativo> materiales = gson.fromJson(response.toString(), listType);
                
                System.out.println("Material educativo obtenido exitosamente del MsLibro");
                return materiales;
                
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("No se encontraron materiales");
                return null;
            } else {
                System.err.println("Error en MsLibro. Código: " + responseCode);
                return null;
            }
            
        } catch (Exception ex) {
            System.err.println("Error comunicándose con MsLibro: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}
