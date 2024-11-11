package io.prueba.test.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;


public class CompraDTO {

    private Integer id;

    private LocalDateTime fechaCompra;

    @Size(max = 255)
    private String estado;

    private Double total;

    @Size(max = 255)
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(final LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(final Double total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

}
