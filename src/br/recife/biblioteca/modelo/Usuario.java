package br.recife.biblioteca.modelo;

public abstract class Usuario {
    private final String id;
    private String nome;
    private String documento;

protected Usuario(String id, String nome, String documento) {
    this.id = id;
    setNome(nome);
    setDocumento(documento);
}

public String getId() { return id; }
public String getNome() { return nome; }
public void setNome(String nome) {
    if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome vazio");
    this.nome = nome.trim();
}

public String getDocumento() { return documento; }
public void setDocumento(String documento) {
    if (documento == null || documento.isBlank()) throw new IllegalArgumentException("documento vazio");
    this.documento = documento.trim();
}

public abstract int prazoDiasPadrao();
public abstract double fatorMulta();

@Override
public String toString() { return getClass().getSimpleName()+"{"+nome+"}"; }
}