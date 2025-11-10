package br.recife.biblioteca.excecao;

public class EmprestimoNaoEncontradoException extends RuntimeException {
    public EmprestimoNaoEncontradoException(String msg) { super(msg); }
}