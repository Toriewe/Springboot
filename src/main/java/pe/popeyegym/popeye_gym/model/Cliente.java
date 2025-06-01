package pe.popeyegym.popeye_gym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer clienteid;

    private String nombre;
    private String apellido;
    private Integer telef;
    private String email;
    private Integer dni;

    @Column(name = "fe_inscripcion")
    private LocalDate feinscripcion;

    private String detalles;
    private Boolean estado;
    private Integer renovacion;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Membresia membresia;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Deuda deuda;

    public Integer getClienteid() {
        return clienteid;
    }

    public void setClienteid(Integer clienteid) {
        this.clienteid = clienteid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelef() {
        return telef;
    }

    public void setTelef(Integer telef) {
        this.telef = telef;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public LocalDate getFeinscripcion() {
        return feinscripcion;
    }

    public void setFeinscripcion(LocalDate feinscripcion) {
        this.feinscripcion = feinscripcion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public Deuda getDeuda() {
        return deuda;
    }

    public void setDeuda(Deuda deuda) {
        this.deuda = deuda;
    }

    public Integer getRenovacion() {
        return renovacion;
    }

    public void setRenovacion(Integer renovacion) {
        this.renovacion = renovacion;
    }
}
