package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "Autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.nacimiento = datosAutor.nacimiento();
        this.fallecimiento = datosAutor.fallecimiento();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Integer getNacimiento(){
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento){
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento(){
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento){
        this.fallecimiento = fallecimiento;
    }

    public String toString(){
        return "Autor [id=" + id + ", nombre=" + nombre + ", nacimiento=" + nacimiento + ", fallecimiento=" + fallecimiento + ", libro" + "]";
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
