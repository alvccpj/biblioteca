package br.recife.biblioteca.excecao;

public class ItemIndisponivelException extends RuntimeException {
    public ItemIndisponivelException(String msg) { super(msg); }
}