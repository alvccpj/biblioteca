package br.recife.biblioteca.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    public enum Status { ATIVO, DEVOLVIDO }

    private final Recurso recurso; 
    private final Usuario usuario; 
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private Status status = Status.ATIVO;
    private double multaCalculada;


public Emprestimo(Recurso recurso, Usuario usuario, LocalDate dataEmprestimo, LocalDate dataPrevista) {
    this.recurso = recurso;
    this.usuario = usuario;
    this.dataEmprestimo = dataEmprestimo;
    this.dataPrevista = dataPrevista;
}

public Recurso getRecurso() { return recurso; }
public Usuario getUsuario() { return usuario; }
public LocalDate getDataEmprestimo() { return dataEmprestimo; }
public LocalDate getDataPrevista() { return dataPrevista; }
public LocalDate getDataDevolucao() { return dataDevolucao; }
public Status getStatus() { return status; }
public double getMultaCalculada() { return multaCalculada; }


public void finalizarDevolucao(LocalDate dataDevolucaoEfetiva) {
    this.dataDevolucao = dataDevolucaoEfetiva;
    this.status = Status.DEVOLVIDO;
    long diasAtraso = Math.max(0, ChronoUnit.DAYS.between(dataPrevista, dataDevolucaoEfetiva));
    this.multaCalculada = recurso.calcularMulta(diasAtraso, usuario);
}

@Override
public String toString() {
    return String.format("Emprestimo{%s -> %s, prev=%s, status=%s, multa=%.2f}",
    usuario, recurso.getDescricao(), dataPrevista, status, multaCalculada);
}
}