package com.apri.ms.resenas.dao.factory;

import com.apri.ms.resenas.dao.*;
import com.apri.ms.resenas.dao.impl.*;

/**
 * Factory para crear instancias de los DAOs
 * Patrón Factory Method
 */
public class DAOFactory {
    
    private static DAOFactory instance;
    
    private DAOFactory() {
        // Constructor privado para Singleton
    }
    
    /**
     * Obtiene la instancia única del Factory
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }
    
    /**
     * Crea una instancia del DAO de Reseñas
     */
    public IResenaDAO getResenaDAO() {
        return new ResenaDAOImpl();
    }
    
    /**
     * Crea una instancia del DAO de Reportes
     */
    public IReporteDAO getReporteDAO() {
        return new ReporteDAOImpl();
    }
    
    /**
     * Crea una instancia del DAO de RespuestaReporte
     */
    public IRespuestaReporteDAO getRespuestaReporteDAO() {
        return new RespuestaReporteDAOImpl();
    }
    
    /**
     * Crea una instancia del DAO de Sanciones
     */
    public ISancionDAO getSancionDAO() {
        return new SancionDAOImpl();
    }
}
