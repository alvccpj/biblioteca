package br.recife.biblioteca.modelo;

public class Aluno extends Usuario {
    public Aluno(String id, String nome, String documento) { super(id, nome, documento); }
        @Override public int prazoDiasPadrao() { return 14; }
        @Override public double fatorMulta() { return 1.0; }
}