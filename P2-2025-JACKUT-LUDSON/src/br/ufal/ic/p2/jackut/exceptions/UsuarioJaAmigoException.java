package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaAmigoException extends Exception {
    public UsuarioJaAmigoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
