
package modelo;


public class Descarga {
    
    private int id_descarga;
    private String fecha_descarga;
    private MaterialEducativo material_educativo;

    public Descarga(int id_descarga, String fecha_descarga, MaterialEducativo material_educativo) {
        this.id_descarga = id_descarga;
        this.fecha_descarga = fecha_descarga;
        this.material_educativo = material_educativo;
    }

    @Override
    public String toString() {
        return "Descarga{" + "id_descarga=" + id_descarga + ", fecha_descarga=" + fecha_descarga + ", material_educativo=" + material_educativo + '}';
    }

    
    
    
    
    public int getId_descarga() {
        return id_descarga;
    }

    public void setId_descarga(int id_descarga) {
        this.id_descarga = id_descarga;
    }

  
    public String getFecha_descarga() {
        return fecha_descarga;
    }

    /**
     * @param fecha_descarga the fecha_descarga to set
     */
    public void setFecha_descarga(String fecha_descarga) {
        this.fecha_descarga = fecha_descarga;
    }

    /**
     * @return the material_educativo
     */
    public MaterialEducativo getMaterial_educativo() {
        return material_educativo;
    }

    /**
     * @param material_educativo the material_educativo to set
     */
    public void setMaterial_educativo(MaterialEducativo material_educativo) {
        this.material_educativo = material_educativo;
    }
    
    
    
}
