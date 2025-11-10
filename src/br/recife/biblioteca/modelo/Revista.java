package br.recife.biblioteca.modelo;

import java.time.LocalDate;

public class Revista extends Recurso implements Emprestavel {
    private String edicao;
    private String periodicidade;
    private boolean disponivel = true;
    private LocalDate dataPrevista;

public Revista(String id, String titulo, int anoPublicacao, String edicao, String periodicidade) {
    super(id, titulo, anoPublicacao);
    this.edicao = edicao;
    this.periodicidade = periodicidade;
}

public String getEdicao() { return edicao; }
public String getPeriodicidade() { return periodicidade; }

@Override
public double calcularMulta(long diasAtraso, Usuario user) {
    long d = Math.max(0, diasAtraso - 2);
    return d * 0.5 * user.fatorMulta();
}

@Override
public boolean emprestar(Usuario u) {
    if (!disponivel) return false;
    this.disponivel = false;
    this.dataPrevista = LocalDate.now().plusDays(u.prazoDiasPadrao() - 2); // revistas devolvem 2 dias antes por política
    return true;
}

@Override
public void devolver(LocalDate dataDevolucaoEfetiva) {
    this.disponivel = true;
    this.dataPrevista = null;
}

@Override
public LocalDate getDataPrevistaDevolucao() { return dataPrevista; }


@Override
public boolean isDisponivel() { return disponivel; }
}