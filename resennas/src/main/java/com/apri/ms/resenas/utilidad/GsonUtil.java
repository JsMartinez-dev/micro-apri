package com.apri.ms.resenas.utilidad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilidad para configurar Gson con adaptadores personalizados para tipos Java Time
 */
public class GsonUtil {
    
    private static final Gson GSON_INSTANCE;
    
    static {
        GsonBuilder builder = new GsonBuilder();
        
        // Adaptador para LocalDate
        builder.registerTypeAdapter(LocalDate.class, 
            (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> 
                context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        
        builder.registerTypeAdapter(LocalDate.class,
            (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE));
        
        // Adaptador para LocalDateTime
        builder.registerTypeAdapter(LocalDateTime.class,
            (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        
        builder.registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        GSON_INSTANCE = builder.create();
    }
    
    /**
     * Obtiene una instancia configurada de Gson
     * @return Instancia de Gson con adaptadores para java.time
     */
    public static Gson getGson() {
        return GSON_INSTANCE;
    }
}
