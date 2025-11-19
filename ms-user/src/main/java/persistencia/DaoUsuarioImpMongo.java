package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Administrador;
import modelo.Persona;
import modelo.Usuario;
import org.bson.Document;

public class DaoUsuarioImpMongo implements DaoUsuario {
    
    private MongoCollection<Document> collection;
    
    public DaoUsuarioImpMongo() {
        MongoDatabase database = ConexionMongoDB.getInstancia().getDatabase();
        this.collection = database.getCollection("usuarios");
    }

    @Override
    public boolean registrar(Usuario usuario) throws Exception {
        try {
            System.out.println("ANTES DE GUARDAR - Datos del usuario:");
            System.out.println("- Nombre: " + usuario.getPrimer_nombre());
            System.out.println("- Apellido: " + usuario.getPrimer_apellido());
            System.out.println("- Correo: " + usuario.getCorreo());

            int idGenerado = usuario.getId_persona();
            if (idGenerado <= 0) {
                idGenerado = (int) System.currentTimeMillis();
            }

            Document doc = new Document()
                    .append("id_persona", idGenerado) 
                    .append("primer_nombre", usuario.getPrimer_nombre())
                    .append("primer_apellido", usuario.getPrimer_apellido())
                    .append("correo", usuario.getCorreo())
                    .append("institucion", usuario.getInstitucion())
                    .append("fecha_nacimiento", convertirLocalDateADate(usuario.getFecha_nacimiento()))
                    .append("fecha_registro", convertirLocalDateADate(usuario.getFecha_registro()))
                    .append("contrasena", usuario.getContraseña()) 
                    .append("estado", usuario.isEstado())
                    .append("tipo", usuario.getTipo());

            collection.insertOne(doc);
            System.out.println("INSERT ejecutado en MongoDB, ID generado: " + doc.getObjectId("_id"));

            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar usuario en MongoDB: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) throws Exception {
        try {
            Document filtro = new Document("correo", usuario.getCorreo());
            
            Document actualizacion = new Document("$set", new Document()
                    .append("primer_nombre", usuario.getPrimer_nombre())
                    .append("primer_apellido", usuario.getPrimer_apellido())
                    .append("institucion", usuario.getInstitucion())
                    .append("fecha_nacimiento", convertirLocalDateADate(usuario.getFecha_nacimiento()))
                    .append("contrasena", usuario.getContraseña())
                    .append("estado", usuario.isEstado())
                    .append("tipo", usuario.getTipo())
            );
            
            return collection.updateOne(filtro, actualizacion).getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            throw e;
        }
    }

    public boolean eliminar(Usuario usuario) throws Exception {
        try {
            Document filtro = new Document("correo", usuario.getCorreo());
            return collection.deleteOne(filtro).getDeletedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Usuario> listar() throws Exception {
        List<Usuario> listU = new ArrayList<>();
        
        try {
            System.out.println("PASO LA CONEXIONNN");
            
            for (Document doc : collection.find()) {
                Usuario u = documentoAUsuario(doc);
                listU.add(u);
            }
            
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            throw e;
        }
        
        return listU;
    }

    @Override
    public boolean validarCorreo(String correo) throws Exception {
        try {
            Document filtro = new Document("correo", correo);
            Document resultado = collection.find(filtro).first();
            
            // Si no encuentra el correo, retorna true (correo disponible)
            return resultado == null;
        } catch (Exception e) {
            System.err.println("Error al validar correo: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Persona correoExistente(String correo) throws Exception {
        try {
            Document filtro = new Document("correo", correo);
            Document doc = collection.find(filtro).first();
            
            if (doc != null) {
                System.out.println("--__--Encontramos al usuario-__--");
                return crearPersonaDesdeDocumento(doc);
            }
            
            return null;
        } catch (Exception e) {
            System.err.println("Error al buscar correo: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean cambiarEstadoF(int id) throws Exception {
        try {
            Document filtro = new Document("id_persona", id);
            Document actualizacion = new Document("$set", new Document("estado", false));
            
            return collection.updateOne(filtro, actualizacion).getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al cambiar estado a false: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean cambiarEstadoT(int id) throws Exception {
        try {
            Document filtro = new Document("id_persona", id);
            Document actualizacion = new Document("$set", new Document("estado", true));
            
            return collection.updateOne(filtro, actualizacion).getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error al cambiar estado a true: " + e.getMessage());
            throw e;
        }
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    private Usuario documentoAUsuario(Document doc) {
        // Generar un ID si no existe (para compatibilidad)
        int idPersona = doc.getInteger("id_persona", 0);
        if (idPersona == 0 && doc.getObjectId("_id") != null) {
            // Usar el timestamp del ObjectId como ID temporal
            idPersona = (int) (doc.getObjectId("_id").getTimestamp() & 0xFFFFFFFF);
        }
        
        String primerNombre = doc.getString("primer_nombre");
        String primerApellido = doc.getString("primer_apellido");
        String correo = doc.getString("correo");
        String institucion = doc.getString("institucion");
        LocalDate fechaNacimiento = convertirDateALocalDate(doc.getDate("fecha_nacimiento"));
        LocalDate fechaRegistro = convertirDateALocalDate(doc.getDate("fecha_registro"));
        String contrasenaHasheada = doc.getString("contrasena");
        boolean estado = doc.getBoolean("estado", true);
        String tipo = doc.getString("tipo");
        
        return new Usuario(idPersona, primerNombre, primerApellido, 
                          fechaNacimiento, fechaRegistro, correo, 
                          institucion, contrasenaHasheada, estado, tipo);
    }
    
    private Persona crearPersonaDesdeDocumento(Document doc) throws Exception {
        int idPersona = doc.getInteger("id_persona", 0);
        if (idPersona == 0 && doc.getObjectId("_id") != null) {
            idPersona = (int) (doc.getObjectId("_id").getTimestamp() & 0xFFFFFFFF);
        }
        
        String primerNombre = doc.getString("primer_nombre");
        String primerApellido = doc.getString("primer_apellido");
        String correo = doc.getString("correo");
        String institucion = doc.getString("institucion");
        LocalDate fechaNacimiento = convertirDateALocalDate(doc.getDate("fecha_nacimiento"));
        LocalDate fechaRegistro = convertirDateALocalDate(doc.getDate("fecha_registro"));
        String contrasenaHasheada = doc.getString("contrasena");
        boolean estado = doc.getBoolean("estado", true);
        String tipo = doc.getString("tipo");
        
        if ("usuario".equals(tipo)) {
            return new Usuario(idPersona, primerNombre, primerApellido, 
                              fechaNacimiento, fechaRegistro, correo, 
                              institucion, contrasenaHasheada, estado, tipo);
        } else {
            String rol = doc.getString("rol") != null ? doc.getString("rol") : "general";
            return new Administrador(rol, idPersona, primerNombre, 
                                    primerApellido, fechaNacimiento, fechaRegistro, 
                                    correo, institucion, contrasenaHasheada, estado, tipo);
        }
    }
    
    private Date convertirLocalDateADate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    private LocalDate convertirDateALocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public boolean eliminar(int id_usuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}