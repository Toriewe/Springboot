package pe.popeyegym.popeye_gym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membresia_id")
    private Integer membresiaid;

    private String tipo;

    @Column(name = "fe_inicio")
    private LocalDate feinicio;

    @Column(name = "fe_fin")
    private LocalDate fefin;

    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFefin() {
        return fefin;
    }

    public void setFefin(LocalDate fefin) {
        this.fefin = fefin;
    }

    public LocalDate getFeinicio() {
        return feinicio;
    }

    public void setFeinicio(LocalDate feinicio) {
        this.feinicio = feinicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getMembresiaid() {
        return membresiaid;
    }

    public void setMembresiaid(Integer membresiaid) {
        this.membresiaid = membresiaid;
    }
}
