package br.ufal.ic.p2.jackut.exceptions;

public class ContaJaExistenteException extends RuntimeException {
    public ContaJaExistenteException() {
        super("Conta com esse nome já existe.");
    }
}
