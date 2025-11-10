package br.recife.biblioteca.modelo;

import java.time.LocalDate;

public interface Emprestavel {
    boolean emprestar(Usuario u);
    void devolver(LocalDate dataDevolucaoEfetiva);
    LocalDate getDataPrevistaDevolucao();
    boolean isDisponivel();
}W