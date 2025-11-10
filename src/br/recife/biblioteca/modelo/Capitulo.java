package br.recife.biblioteca.modelo;

import java.util.Objects;

public class Capitulo {
    private final int numero;
    private String titulo;
    private int paginas;

    public Capitulo(int numero, String titulo, int paginas) {
        if (numero <= 0) throw new IllegalArgumentException("numero do capítulo deve ser > 0");
        this.numero = numero;
        setTitulo(titulo);
        setPaginas(paginas);
    }

    public int getNumero() { return numero; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("titulo vazio");
        this.titulo = titulo.trim();
    }

    public int getPaginas() { return paginas; }

    public void setPaginas(int paginas) {
        if (paginas <= 0) throw new IllegalArgumentException("paginas deve ser > 0");
        this.paginas = paginas;
    }

    @Override
    public String toString() {
        return "Cap." + numero + ": " + titulo + " (" + paginas + "p)";
    }
}