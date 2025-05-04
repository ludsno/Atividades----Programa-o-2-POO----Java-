package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaIdoloException extends RuntimeException {
    public UsuarioJaIdoloException() {
        super("Usuário já está adicionado como ídolo.");
    }
}
