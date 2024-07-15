package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Datos;
import com.aluracursos.literalura.model.DatosLibros;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    private AutorRepository autorRepositorio;
    private LibroRepository libroRepositorio;

    public Principal(LibroRepository librosRepositorio, AutorRepository autorRepositorio){
        this.libroRepositorio = librosRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while (opcion !=0){
        var menu = """
                Elija la opcion a traves de su numero:
                1 - Buscar libro por titulo.
                2 - Listar libros registrados.
                3 - Listar autores registrados.
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                0 - Salir
                """;
        System.out.println(menu);
        opcion = Integer.valueOf(teclado.nextLine());
        teclado.nextLine();

                switch (opcion){
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa");
                        break;
                    default:
                        System.out.println("Por favor ingrese una opcion valida");
                }
                }
        }

        private void buscarLibroPorTitulo(){
        Datos datos = getDatosSerie();

        if (!datos.results().isEmpty()){
            Libro libro = new Libro(datos.results().get(0));
            libro = libroRepositorio.save(libro);
        }

            System.out.println("Datos: ");
            System.out.println(datos);
        }

        private Datos getDatosSerie(){
            System.out.println("Ingrese el nombre del libro que desea buscar");
            var titulo = teclado.nextLine();
            titulo = titulo.replace("", "+");
            System.out.println("Titulo: " + titulo);
            System.out.println(URL_BASE + titulo);
            var json = consumoApi.obtenerDatos(URL_BASE + titulo);
            System.out.println(json);
            Datos datos = conversor.obtenerDatos(json, Datos.class);
            return datos;
        }

        private void listarLibrosRegistrados(){

        List<Libro> titulo = libroRepositorio.findAll();

        if (!titulo.isEmpty()){
            for (Libro libro: titulo) {
                System.out.println("\n\n---------- LIBROS -------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getIdioma());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("No se encontraron resultados");
        }
        }

        private void listarAutoresRegistrados(){
        List<Autor> autores = autorRepositorio.findAll();

            if (!autores.isEmpty()){

            for (Autor autor : autores) {
                System.out.println("\n\n---------- Autores -------\n");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de Nacimiento: " + autor.getNacimiento());
                System.out.println(" Fecha de Fallecimiento: " + autor.getFallecimiento());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
        System.out.println("\n\n No se encontraron resultados \n\n");

    }
        }

        private void listarAutoresVivos(){
            System.out.println("Ingrese el año vivo de autor(es) que desea buscar");
            var anio = teclado.nextInt();
            teclado.next();

            List<Autor> autores = autorRepositorio.autorVivo(anio);

            if (!autores.isEmpty()){
                for (Autor autor : autores){
                    System.out.println("Autor: " + autor.getNombre());
                    System.out.println("Fecha de nacimiento: " + autor.getNacimiento());
                    System.out.println("Fecha de fallecimiento: " + autor.getFallecimiento());
                }
            } else {
                System.out.println("No se encontraron autores");
            }
        }

        private void listarLibrosPorIdioma(){
        var menu = """
                Ingrese el idioma para buscar los libros
                1 - Español
                2 - Ingles
                """;
            System.out.println(menu);
            var idioma = teclado.nextInt();
            teclado.nextLine();

            String seleccion = "";
            if (idioma == 1){
                seleccion = "es";
            } else if (idioma == 2){
                seleccion = "en";
            }

            List<Libro> libros = libroRepositorio.findForLanguaje(seleccion);

            if (!libros.isEmpty()){
                for (Libro libro: libros){
                    System.out.println("Titulo: " + libro.getTitulo());
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                    System.out.println("Idioma:" + libro.getIdioma());
                    System.out.println("Descargas: " +libro.getDescargas());
                }
            } else {
                System.out.println("No se encontraron registros");
            }
        }
        }

