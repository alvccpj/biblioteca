package br.recife.biblioteca.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Livro extends Recurso implements Emprestavel {
    private final List<Capitulo> capitulos = new ArrayList<>();
    private boolean disponivel = true;
    private LocalDate dataPrevista;

    public Livro(String id, String titulo, int anoPublicacao) {
        super(id, titulo, anoPublicacao);
    }

    public void adicionarCapitulo(Capitulo c) { this.capitulos.add(c); }
    public void removerCapitulo(int numero) { this.capitulos.removeIf(c -> c.getNumero()==numero); }
    public List<Capitulo> getCapitulos() { return Collections.unmodifiableList(capitulos); }

    @Override
    public double calcularMulta(long diasAtraso, Usuario user) {
        if (diasAtraso <= 0) return 0.0;
        return diasAtraso * 1.0 * user.fatorMulta();
    }

    @Override
    public boolean emprestar(Usuario u) {
        if (!disponivel) return false;
        this.disponivel = false;
        this.dataPrevista = LocalDate.now().plusDays(u.prazoDiasPadrao());
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