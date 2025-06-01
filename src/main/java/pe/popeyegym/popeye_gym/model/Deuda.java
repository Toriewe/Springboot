package pe.popeyegym.popeye_gym.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deuda_id")
    private Integer deudaid;

    private Boolean estado;
    private Integer cantidad;

    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;

    public Integer getDeudaid() {
        return deudaid;
    }

    public void setDeudaid(Integer deudaid) {
        this.deudaid = deudaid;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
