package br.recife.biblioteca.modelo;

public class Visitante extends Usuario {
    public Visitante(String id, String nome, String documento) { super(id, nome, documento); }
        @Override public int prazoDiasPadrao() { return 7; }
        @Override public double fatorMulta() { return 1.2; }
}