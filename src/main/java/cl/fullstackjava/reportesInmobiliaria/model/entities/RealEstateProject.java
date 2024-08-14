package cl.fullstackjava.reportesInmobiliaria.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="proyecto_inmobiliario")
public class RealEstateProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre")
    private String name;
    @Column(name="direccion")
    private String address;
    @Column(name="presupuesto")
    private long budget;
    @Column(name="fecha_inicio")
    private LocalDate startDate;
    @Column(name="fecha_entrega")
    private LocalDate completionDate;

    public RealEstateProject() {
    }

    public RealEstateProject(int id, String name, String address, long budget, LocalDate startDate, LocalDate completionDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.budget = budget;
        this.startDate = startDate;
        this.completionDate = completionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
