package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT b FROM Author a JOIN a.books b WHERE b.title ILIKE %:title%")
    List<Libro> buscarLibroPorTitulo( String title );

    @Query("SELECT b FROM Author a JOIN a.books b")
    List<Libro> todosLosLibros();

    @Query("SELECT b FROM Auhor a JOIN a.books b WHERE b.language ILIKE %:language%")
    List<Libro> buscarPorIdioma(String language);

    @Query("SELECT a FROM Autor a WHERE WHERE :anio between a.nacimiento AND a.fallecimiento")
    List<Autor> autorVivo(int anio);

    @Query("SELECT b FROM Author a JOIN a.books b ORDER BY b.downloadCount DESC LIMIT 10")
    List<Libro> top10Books();
}
