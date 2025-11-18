package dto;

public class DtoResenaCrear {
    private String comentario;
    private Integer cantidadEstrellas;
    private Integer idUsuario;
    private Integer idMaterialEducativo;

    public DtoResenaCrear() {}

    public DtoResenaCrear(String comentario, Integer cantidadEstrellas,
                          Integer idUsuario, Integer idMaterialEducativo) {
        this.comentario = comentario;
        this.cantidadEstrellas = cantidadEstrellas;
        this.idUsuario = idUsuario;
        this.idMaterialEducativo = idMaterialEducativo;
    }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Integer getCantidadEstrellas() { return cantidadEstrellas; }
    public void setCantidadEstrellas(Integer cantidadEstrellas) { this.cantidadEstrellas = cantidadEstrellas; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdMaterialEducativo() { return idMaterialEducativo; }
    public void setIdMaterialEducativo(Integer idMaterialEducativo) { this.idMaterialEducativo = idMaterialEducativo; }
}
