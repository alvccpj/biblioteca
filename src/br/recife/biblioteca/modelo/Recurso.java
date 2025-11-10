package br.recife.biblioteca.modelo;

import java.util.Objects;

public abstract class Recurso {
    private final String id;
    private String titulo;
    private int anoPublicacao;

protected Recurso(String id, String titulo, int anoPublicacao) {
    this.id = Objects.requireNonNull(id, "id não pode ser nulo");
    setTitulo(titulo);
    setAnoPublicacao(anoPublicacao);
}

public String getId() { return id; }

public String getTitulo() { return titulo; }

public void setTitulo(String titulo) {
    if (titulo == null || titulo.isBlank()) {
        throw new IllegalArgumentException("titulo não pode ser vazio");
    }
    this.titulo = titulo.trim();
}

public int getAnoPublicacao() { return anoPublicacao; }

public void setAnoPublicacao(int anoPublicacao) {
    if (anoPublicacao <= 0) throw new IllegalArgumentException("anoPublicacao deve ser > 0");
    this.anoPublicacao = anoPublicacao;
}

public String getDescricao() {
    return String.format("[%s] %s (%d)", getClass().getSimpleName(), titulo, anoPublicacao);
}

public abstract double calcularMulta(long diasAtraso, Usuario user);

@Override
public String toString() { return getDescricao(); }
}