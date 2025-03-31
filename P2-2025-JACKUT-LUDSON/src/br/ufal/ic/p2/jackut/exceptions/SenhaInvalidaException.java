package br.ufal.ic.p2.jackut.exceptions;

public class SenhaInvalidaException extends Exception {
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
