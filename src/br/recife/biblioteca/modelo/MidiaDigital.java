package br.recife.biblioteca.modelo;

import java.time.LocalDate;

public class MidiaDigital extends Recurso implements Emprestavel {
    private double tamanhoMB;
    private String tipoArquivo; 
    private boolean disponivel = true;
    private LocalDate dataPrevista;

public MidiaDigital(String id, String titulo, int anoPublicacao, double tamanhoMB, String tipoArquivo) {
    super(id, titulo, anoPublicacao);
    if (tamanhoMB <= 0) throw new IllegalArgumentException("tamanhoMB deve ser > 0");
    this.tamanhoMB = tamanhoMB;
    this.tipoArquivo = tipoArquivo;
}

public double getTamanhoMB() { return tamanhoMB; }
public String getTipoArquivo() { return tipoArquivo; }

@Override
public double calcularMulta(long diasAtraso, Usuario user) {
    if (diasAtraso <= 0) return 0.0;
    return diasAtraso * 0.75 * user.fatorMulta();
}

@Override
public boolean emprestar(Usuario u) {
    if (!disponivel) return false;
    this.disponivel = false;
    int prazo = Math.min(u.prazoDiasPadrao(), 7); 
    this.dataPrevista = LocalDate.now().plusDays(prazo);
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