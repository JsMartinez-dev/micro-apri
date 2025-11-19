package persistencia;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoDB {
    
    private static ConexionMongoDB instancia;
    private MongoClient mongoClient;
    private MongoDatabase database;
    
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "educativo_db";
    
    private ConexionMongoDB() {
        try {
            ConnectionString connString = new ConnectionString(CONNECTION_STRING);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();
            
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(DATABASE_NAME);
            
            System.out.println("Conexión exitosa a MongoDB: " + DATABASE_NAME);
        } catch (Exception e) {
            System.err.println("Error al conectar con MongoDB: " + e.getMessage());
            throw new RuntimeException("No se pudo establecer conexión con MongoDB", e);
        }
    }
    
    public static synchronized ConexionMongoDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionMongoDB();
        }
        return instancia;
    }
    
    public MongoDatabase getDatabase() {
        return database;
    }
    
    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión a MongoDB cerrada");
        }
    }
}