CREATE TABLE resenas (
  id_resenas serial PRIMARY KEY,
  comentario varchar,
  cantidad_estrellas integer NOT NULL CHECK (cantidad_estrellas >= 1 AND cantidad_estrellas <= 5),
  id_usuario integer NOT NULL,
  id_material_educativo integer NOT NULL,
  fecha_creacion timestamp DEFAULT CURRENT_TIMESTAMP,
  estado varchar DEFAULT 'activa' CHECK (estado IN ('activa', 'oculta', 'eliminada')),
  CONSTRAINT unique_resena_usuario_material UNIQUE (id_usuario, id_material_educativo)
);

CREATE INDEX idx_resenas_usuario ON resenas(id_usuario);
CREATE INDEX idx_resenas_material ON resenas(id_material_educativo);
CREATE INDEX idx_resenas_estrellas ON resenas(cantidad_estrellas);
CREATE INDEX idx_resenas_estado ON resenas(estado);

CREATE TABLE reportes (
  id_reporte serial PRIMARY KEY,
  motivo varchar NOT NULL,
  fecha_reporte date NOT NULL DEFAULT CURRENT_DATE,
  id_usuario integer NOT NULL,
  id_resena integer NOT NULL,
  estado varchar DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'en_revision', 'resuelto', 'rechazado')),
  CONSTRAINT fk_reporte_resena FOREIGN KEY (id_resena) 
    REFERENCES resenas(id_resenas) ON DELETE CASCADE
);

CREATE INDEX idx_reportes_resena ON reportes(id_resena);
CREATE INDEX idx_reportes_usuario ON reportes(id_usuario);
CREATE INDEX idx_reportes_fecha ON reportes(fecha_reporte);
CREATE INDEX idx_reportes_estado ON reportes(estado);

CREATE TABLE respuestas_reporte (
  id_reporte integer NOT NULL,
  id_administrador integer NOT NULL,
  accion varchar NOT NULL CHECK (accion IN ('eliminar_resena', 'ocultar_resena', 'rechazar_reporte', 'advertir_usuario', 'suspender_usuario', 'banear_usuario')),
  respuesta varchar,
  fecha_solucion timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_reporte, id_administrador),
  CONSTRAINT fk_respuesta_reporte FOREIGN KEY (id_reporte) 
    REFERENCES reportes(id_reporte) ON DELETE CASCADE
);

CREATE INDEX idx_respuestas_admin ON respuestas_reporte(id_administrador);
CREATE INDEX idx_respuestas_fecha ON respuestas_reporte(fecha_solucion);

CREATE TABLE sanciones (
  id_sancion serial PRIMARY KEY,
  id_usuario integer NOT NULL,
  tipo_sancion varchar NOT NULL CHECK (tipo_sancion IN ('advertencia', 'suspension', 'baneo')),
  motivo varchar NOT NULL,
  fecha_inicio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_fin timestamp,
  activa boolean DEFAULT true,
  id_administrador integer NOT NULL,
  id_reporte integer,
  CONSTRAINT fk_sancion_reporte FOREIGN KEY (id_reporte) 
    REFERENCES reportes(id_reporte) ON DELETE SET NULL
);

CREATE INDEX idx_sanciones_usuario ON sanciones(id_usuario);
CREATE INDEX idx_sanciones_tipo ON sanciones(tipo_sancion);
CREATE INDEX idx_sanciones_activa ON sanciones(activa);
CREATE INDEX idx_sanciones_fecha_fin ON sanciones(fecha_fin);